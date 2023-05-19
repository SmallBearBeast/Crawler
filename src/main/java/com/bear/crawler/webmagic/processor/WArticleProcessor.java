package com.bear.crawler.webmagic.processor;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.net.url.UrlQuery;
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

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class WArticleProcessor implements PageProcessor {

    private static final String BEGIN = "begin";
    private static final String FAKE_ID = "fakeid";
    private static final int ARTICLE_LIMIT = 20;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WArticleDao wArticleDao;

    @Autowired
    private WPublicAccountProvider wPublicAccountProvider;

    @Autowired
    private WArticleProvider wArticleProvider;

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
                        saveAccountDtosToDB(articleItemDtos, fakeId);
                        OtherUtil.sleep(3);
                        // TODO: 5/18/23 比较最近文章的时间
                        if (begin + articleItemDtos.size() > ARTICLE_LIMIT) {
                            log.info("Load article list more than {}", ARTICLE_LIMIT);
                            // TODO: 5/18/23 某个公众号的文章收集完毕，写文件收集数据
                        } else {
                            addNextTargetRequest(page, begin);
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
                // TODO: 5/18/23 打印出距离上次diff的条数 
                // TODO: 5/18/23 打印今日抓取的条数 
            }
            wArticleProvider.put(articleItemPO);
        }
    }

    private int getBegin(Page page) {
        return Integer.parseInt(OtherUtil.getQuery(page, BEGIN));
    }

    private String getFakeId(Page page) {
        return OtherUtil.getQuery(page, FAKE_ID);
    }

    private void addNextTargetRequest(Page page, int begin) {
        String url = page.getUrl().get();
        UrlQuery urlQuery = UrlBuilder.of(url).getQuery();
        UrlQuery newUrlQuery = new UrlQuery();
        for (Map.Entry<CharSequence, CharSequence> entry : urlQuery.getQueryMap().entrySet()) {
            if (!BEGIN.contentEquals(entry.getKey())) {
                newUrlQuery.add(entry.getKey(), entry.getValue());
            }
        }
        newUrlQuery.add(BEGIN, begin + 5);
        String nextUrl = UrlBuilder.of(url).setQuery(newUrlQuery).build();
        page.addTargetRequest(nextUrl);
    }
}
