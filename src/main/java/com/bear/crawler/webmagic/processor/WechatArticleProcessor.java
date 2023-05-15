package com.bear.crawler.webmagic.processor;

import com.bear.crawler.webmagic.mybatis.generator.mapper.WechatArticleItemPOMapper;
import com.bear.crawler.webmagic.mybatis.generator.po.WechatArticleItemPO;
import com.bear.crawler.webmagic.pojo.dto.WechatArticleItemDto;
import com.bear.crawler.webmagic.pojo.dto.WechatArticleItemListRespDto;
import com.bear.crawler.webmagic.util.TransformBeanUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

@Component
@Slf4j
public class WechatArticleProcessor implements PageProcessor {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WechatArticleItemPOMapper wechatArticleItemPOMapper;

    @Override
    public void process(Page page) {
        try {
            WechatArticleItemListRespDto articleItemListRespDto = objectMapper.readValue(page.getRawText(), WechatArticleItemListRespDto.class);
            if (articleItemListRespDto != null) {
                List<WechatArticleItemDto> wechatArticleItemDtos = articleItemListRespDto.getAppMsgList();
                for (WechatArticleItemDto articleItemDto : wechatArticleItemDtos) {
                    WechatArticleItemPO articleItemPO = TransformBeanUtil.dtoToPo(articleItemDto);
                    articleItemPO.setOfficialAccountId(0);
                    articleItemPO.setOfficialAccountFakeId("FakeId");
                    articleItemPO.setOfficialAccountTitle("Title");
                    wechatArticleItemPOMapper.insert(articleItemPO);
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
}
