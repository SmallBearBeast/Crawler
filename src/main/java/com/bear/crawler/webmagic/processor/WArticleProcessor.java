package com.bear.crawler.webmagic.processor;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.net.url.UrlQuery;
import com.bear.crawler.webmagic.mybatis.generator.mapper.WArticleItemPOMapper;
import com.bear.crawler.webmagic.mybatis.generator.mapper.WPublicAccountPOMapper;
import com.bear.crawler.webmagic.mybatis.generator.po.WArticleItemPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WArticleItemPOExample;
import com.bear.crawler.webmagic.pojo.dto.CommonRespDto;
import com.bear.crawler.webmagic.pojo.dto.WArticleItemDto;
import com.bear.crawler.webmagic.pojo.dto.WArticleItemsRespDto;
import com.bear.crawler.webmagic.provider.WPublicAccountProvider;
import com.bear.crawler.webmagic.util.OtherUtil;
import com.bear.crawler.webmagic.util.TransformBeanUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class WArticleProcessor implements PageProcessor, InitializingBean {

    private static final String BEGIN = "begin";
    private static final int ARTICLE_LIMIT = 20;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WArticleItemPOMapper wArticleItemPOMapper;

    @Autowired
    private WPublicAccountPOMapper wPublicAccountPOMapper;

    @Autowired
    private WPublicAccountProvider wPublicAccountProvider;

    private final List<WArticleItemPO> wArticleItemPOS = new ArrayList<>();

    @Override
    public void process(Page page) {
        try {
            WArticleItemsRespDto articleItemsRespDto = objectMapper.readValue(page.getRawText(), WArticleItemsRespDto.class);
            CommonRespDto commonRespDto = articleItemsRespDto.getCommonRespDto();
            if (OtherUtil.checkCommonRespDto(commonRespDto, "WArticleProcessor.process()")) {
                int begin = getBegin(page);
                List<WArticleItemDto> articleItemDtos = articleItemsRespDto.getArticleItemDtos();
                if (articleItemDtos == null) {
                    log.info("articleItemDtoList is null");
                } else {
                    if (articleItemDtos.isEmpty()) {
                        log.info("Load article list to end, begin = {}", begin);
                    } else {
                        log.info("Load article list successfully, begin = {}", begin);
                        saveAccountDtosToDB(articleItemDtos);
                        OtherUtil.sleep(3);
                        if (begin + articleItemDtos.size() > ARTICLE_LIMIT) {
                            log.info("Load public account list more than {}", ARTICLE_LIMIT);
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

    private void saveAccountDtosToDB(List<WArticleItemDto> articleItemDtos) {
        for (WArticleItemDto articleItemDto : articleItemDtos) {
            WArticleItemPO articleItemPO = TransformBeanUtil.dtoToPo(articleItemDto);
            articleItemPO.setOfficialAccountId(0);

            articleItemPO.setOfficialAccountFakeId("FakeId");
            articleItemPO.setOfficialAccountTitle("Title");
            wArticleItemPOMapper.insert(articleItemPO);
        }
    }

    private int getBegin(Page page) {
        String url = page.getUrl().get();
        UrlQuery urlQuery = UrlBuilder.of(url).getQuery();
        return Integer.parseInt(String.valueOf(urlQuery.get(BEGIN)));
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

    private boolean isInArticleDB(WArticleItemDto articleItemDto) {
        for (WArticleItemPO articleItemPO : wArticleItemPOS) {
            if (articleItemPO.getAid().equals(articleItemDto.getAid())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            WArticleItemPOExample example = new WArticleItemPOExample();
            List<WArticleItemPO> publicAccountPOS = wArticleItemPOMapper.selectByExample(example);
            wArticleItemPOS.addAll(publicAccountPOS);
        } catch (Exception e) {
            log.warn("Init the article list failed");
        }
    }
}
