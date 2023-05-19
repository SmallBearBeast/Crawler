package com.bear.crawler.webmagic.processor;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.bear.crawler.webmagic.dao.WArticleDao;
import com.bear.crawler.webmagic.mybatis.generator.po.WArticleItemPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WPublicAccountPO;
import com.bear.crawler.webmagic.pojo.dto.CommonRespDto;
import com.bear.crawler.webmagic.pojo.dto.WArticleItemDto;
import com.bear.crawler.webmagic.pojo.dto.WArticleItemsRespDto;
import com.bear.crawler.webmagic.provider.WArticleProvider;
import com.bear.crawler.webmagic.provider.WPublicAccountProvider;
import com.bear.crawler.webmagic.util.OtherUtil;
import com.bear.crawler.webmagic.util.TransformBeanUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class WArticleProcessor implements PageProcessor {
    private static final int ARTICLE_LIMIT = 20;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WArticleDao wArticleDao;

    @Autowired
    private WPublicAccountProvider wPublicAccountProvider;

    @Autowired
    private WArticleProvider wArticleProvider;

    private final Map<String, List<WArticleItemPO>> fakeIdArticlesMap = new ConcurrentHashMap<>();

    @Override
    public void process(Page page) {
        try {
            WArticleItemsRespDto articleItemsRespDto = objectMapper.readValue(page.getRawText(), WArticleItemsRespDto.class);
            CommonRespDto commonRespDto = articleItemsRespDto.getCommonRespDto();
            if (OtherUtil.checkCommonRespDto(commonRespDto, "WArticleProcessor.process()")) {
                int begin = getBegin(page);
                String fakeId = getFakeId(page);
                List<WArticleItemDto> articleItemDtos = articleItemsRespDto.getArticleItemDtos();
                if (articleItemDtos == null) {
                    log.info("articleItemDtos is null");
                } else {
                    if (articleItemDtos.isEmpty()) {
                        log.info("Load article list to end, begin = {}", begin);
                    } else {
                        log.info("Load article list successfully, begin = {}", begin);
                        articleItemDtos.sort((first, second) -> (int) (second.getUpdateTime() - first.getUpdateTime()));
                        long lastLatestTime = wArticleProvider.getLastLatestTime(fakeId);
                        long curNewestTime = CollectionUtil.getFirst(articleItemDtos).getUpdateTime();
                        if (begin == 0) {
                            fakeIdArticlesMap.remove(fakeId);
                        }
                        if (curNewestTime > lastLatestTime) {
                            saveAccountDtosToDB(articleItemDtos, fakeId);
                            OtherUtil.sleep(3);
                            int articleSize = fakeIdArticlesMap.get(fakeId).size();
                            if (articleSize >= ARTICLE_LIMIT) {
                                log.info("Load article list more than {}", ARTICLE_LIMIT);
                                onFetchArticlesEnd(fakeId);
                            } else {
                                addNextTargetRequest(page, begin);
                            }
                        } else {
                            log.info("Load the last latest article");
                            onFetchArticlesEnd(fakeId);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("process fail, e = {}", e.getMessage());
        }
    }

    @Override
    public Site getSite() {
        return Site.me()
                .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36 Edg/112.0.1722.48")
                .setCharset("utf-8")
                .setTimeOut(10 * 1000)
                .setRetryTimes(3)
                .setRetrySleepTime(3000);
    }

    private void saveAccountDtosToDB(List<WArticleItemDto> articleItemDtos, String fakeId) {
        WPublicAccountPO publicAccountPO = wPublicAccountProvider.findByFakeId(fakeId);
        for (WArticleItemDto articleItemDto : articleItemDtos) {
            WArticleItemPO articleItemPO = TransformBeanUtil.dtoToPo(articleItemDto);
            articleItemPO.setOfficialAccountId(publicAccountPO.getId());
            articleItemPO.setOfficialAccountFakeId(publicAccountPO.getFakeId());
            articleItemPO.setOfficialAccountTitle(publicAccountPO.getNickname());
            if (wArticleProvider.isInArticleDB(articleItemPO)) {
                wArticleDao.updateByAid(articleItemPO);
            } else {
                wArticleDao.insert(articleItemPO);
                addToFakeIdArticlesMap(articleItemPO);
            }
            wArticleProvider.updateCache(articleItemPO);
        }
    }

    private void addToFakeIdArticlesMap(WArticleItemPO articleItemPO) {
        String fakeId = articleItemPO.getOfficialAccountFakeId();
        if (!fakeIdArticlesMap.containsKey(fakeId)) {
            fakeIdArticlesMap.put(fakeId, new ArrayList<>());
        }
        fakeIdArticlesMap.get(fakeId).add(articleItemPO);
    }

    private void onFetchArticlesEnd(String fakeId) {
        saveFetchContentToFile(fakeId);
        updateLatestTime(fakeId);
        fakeIdArticlesMap.remove(fakeId);
    }

    // TODO: 5/18/23 某个公众号的文章收集完毕，写文件收集数据
    private void saveFetchContentToFile(String fakeId) {
        StringBuilder builder = new StringBuilder();
        List<WArticleItemPO> fetchArticleItemPOS = fakeIdArticlesMap.get(fakeId);
        WPublicAccountPO publicAccountPO = wPublicAccountProvider.findByFakeId(fakeId);
        String accountNickname = publicAccountPO == null ? "未知公众号" : publicAccountPO.getNickname();
        builder.append("公众号：").append(accountNickname).append("\n");
        if (CollectionUtil.isEmpty(fetchArticleItemPOS)) {
            builder.append("没有抓到最新的文章").append("\n");
        } else {
            long lastLatestTime = wArticleProvider.getLastLatestTime(fakeId) * 1000;
            String formatLastLatestDate = DateUtil.format(new Date(lastLatestTime), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            builder.append("距离上一次时间").append(formatLastLatestDate).append("抓到了").append(fetchArticleItemPOS.size()).append("篇文章").append("\n");
            collectArticleInfo(builder, fetchArticleItemPOS);
        }

        List<WArticleItemPO> curDateArticleItemPOS = wArticleProvider.getCurDateArticles(fakeId);
        if (CollectionUtil.isEmpty(curDateArticleItemPOS)) {
            builder.append("当天尚未更新文章").append("\n");
        } else {
            String formatCurDate = DateUtil.format(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
            builder.append("当天").append(formatCurDate).append("发布了").append(curDateArticleItemPOS.size()).append("篇文章").append("\n");
            collectArticleInfo(builder, curDateArticleItemPOS);
        }
        log.debug("getFetchContent: content = {}", builder.toString());
    }

    private void collectArticleInfo(StringBuilder builder, List<WArticleItemPO> articleItemPOS) {
        for (WArticleItemPO articleItemPO : articleItemPOS) {
            builder.append("标题：").append(articleItemPO.getTitle()).append("\n")
                    .append("文章链接：").append(articleItemPO.getLink()).append("\n")
                    .append("封面图片链接：").append(articleItemPO.getCover()).append("\n")
                    .append("发布日期：").append(DateUtil.format(articleItemPO.getUpdateTime(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))).append("\n");
        }
    }

    private void updateLatestTime(String fakeId) {
        List<WArticleItemPO> articleItemPOS = fakeIdArticlesMap.get(fakeId);
        WArticleItemPO articleItemPO = CollectionUtil.getFirst(articleItemPOS);
        if (articleItemPO != null) {
            wArticleProvider.setLastLatestTime(fakeId, articleItemPO.getUpdateTime().getTime() / 1000);
        }
    }

    private int getBegin(Page page) {
        return Integer.parseInt(OtherUtil.getQuery(page, OtherUtil.BEGIN));
    }

    private String getFakeId(Page page) {
        return OtherUtil.getQuery(page, OtherUtil.FAKE_ID);
    }

    private void addNextTargetRequest(Page page, int begin) {
        OtherUtil.addNextTargetRequest(page, begin);
    }
}
