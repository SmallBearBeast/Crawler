package com.bear.crawler.webmagic.processor;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import com.bear.crawler.webmagic.AppConstant;
import com.bear.crawler.webmagic.dao.WArticleDao;
import com.bear.crawler.webmagic.manager.ArticleFileManager;
import com.bear.crawler.webmagic.mybatis.generator.po.WArticleItemPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WAccountPO;
import com.bear.crawler.webmagic.pojo.dto.WArticleItemDto;
import com.bear.crawler.webmagic.pojo.dto.WArticleItemsRespDto;
import com.bear.crawler.webmagic.provider.WArticleProvider;
import com.bear.crawler.webmagic.provider.WAccountProvider;
import com.bear.crawler.webmagic.util.OtherUtil;
import com.bear.crawler.webmagic.util.BeanConverterUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class WArticleProcessor implements PageProcessor {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WArticleDao wArticleDao;

    @Autowired
    private WAccountProvider wAccountProvider;

    @Autowired
    private WArticleProvider wArticleProvider;

    @Autowired
    private ArticleFileManager articleFileManager;

    private final Map<String, List<WArticleItemPO>> fakeIdArticlesMap = new ConcurrentHashMap<>();

    @Override
    public void process(Page page) {
        try {
            WArticleItemsRespDto articleItemsRespDto = objectMapper.readValue(page.getRawText(), WArticleItemsRespDto.class);
            if (OtherUtil.checkCommonRespDto(articleItemsRespDto, "WArticleProcessor.process()")) {
                int begin = getBegin(page);
                String fakeId = getFakeId(page);
                List<WArticleItemDto> articleItemDtos = articleItemsRespDto.getArticleItemDtos();
                if (CollectionUtil.isEmpty(articleItemDtos)) {
                    log.info("process: articleItemDtos is empty, begin = {}", begin);
                    onFetchArticlesEnd(fakeId);
                    return;
                }
                log.info("process: load article list successfully, begin = {}", begin);
                articleItemDtos.sort((first, second) -> (int) (second.getUpdateTime() - first.getUpdateTime()));
                long lastLatestTime = wArticleProvider.getLastLatestTime(fakeId);
                long curNewestTime = CollectionUtil.getFirst(articleItemDtos).getUpdateTime();
                if (begin == 0) {
                    fakeIdArticlesMap.remove(fakeId);
                }
                if (curNewestTime > lastLatestTime) {
                    saveArticleItemDtoToDB(articleItemDtos, fakeId);
                    int articleSize = fakeIdArticlesMap.get(fakeId).size();
                    if (articleSize >= AppConstant.ARTICLE_LIMIT) {
                        log.info("process: load article list more than {}", AppConstant.ARTICLE_LIMIT);
                        onFetchArticlesEnd(fakeId);
                    } else {
                        addNextTargetRequest(page, begin + 5);
                    }
                } else {
                    log.info("process: load the last latest article");
                    onFetchArticlesEnd(fakeId);
                }
            }
        } catch (Exception e) {
            log.error("process fail, fakeId = {}, e = {}", e.getMessage(), getFakeId(page));
        }
    }

    @Override
    public Site getSite() {
        return Site.me()
                .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36 Edg/112.0.1722.48")
                .setCharset("utf-8")
                .setTimeOut(10 * 1000)
                .setRetryTimes(3)
                .setSleepTime(RandomUtil.randomInt(3, 6) * 1000)
                .setRetrySleepTime(RandomUtil.randomInt(3, 6) * 1000);
    }

    private void saveArticleItemDtoToDB(List<WArticleItemDto> articleItemDtos, String fakeId) {
        WAccountPO accountPO = wAccountProvider.findByFakeId(fakeId);
        for (WArticleItemDto articleItemDto : articleItemDtos) {
            WArticleItemPO articleItemPO = BeanConverterUtil.dtoToPo(articleItemDto);
            articleItemPO.setOfficialAccountId(accountPO.getId());
            articleItemPO.setOfficialAccountFakeId(accountPO.getFakeId());
            articleItemPO.setOfficialAccountTitle(accountPO.getNickname());
            if (wArticleProvider.isInDB(articleItemPO)) {
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
        List<WArticleItemPO> fetchArticleItemPOS = fakeIdArticlesMap.get(fakeId);
        WAccountPO accountPO = wAccountProvider.findByFakeId(fakeId);
        articleFileManager.saveFetchArticles(accountPO, fetchArticleItemPOS);
        articleFileManager.saveTodayArticles(accountPO);
        updateLatestTime(fakeId);
        fakeIdArticlesMap.remove(fakeId);
    }

    private void updateLatestTime(String fakeId) {
        List<WArticleItemPO> articleItemPOS = fakeIdArticlesMap.get(fakeId);
        WArticleItemPO articleItemPO = CollectionUtil.getFirst(articleItemPOS);
        if (articleItemPO != null) {
            wArticleProvider.setLastLatestTime(fakeId, articleItemPO.getUpdateTime().getTime() / 1000);
        }
    }

    private int getBegin(Page page) {
        return Integer.parseInt(OtherUtil.getQuery(page.getUrl().get(), AppConstant.BEGIN));
    }

    private String getFakeId(Page page) {
        return OtherUtil.getQuery(page.getUrl().get(), AppConstant.FAKE_ID);
    }

    private void addNextTargetRequest(Page page, int begin) {
        String newUrl = OtherUtil.getNewUrlByParams(page.getUrl().get(), MapUtil.of(AppConstant.BEGIN, begin));
        page.addTargetRequest(newUrl);
    }
}
