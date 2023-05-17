package com.bear.crawler.webmagic.processor;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.net.url.UrlQuery;
import com.bear.crawler.webmagic.mybatis.generator.mapper.WPublicAccountPOMapper;
import com.bear.crawler.webmagic.mybatis.generator.po.WPublicAccountPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WPublicAccountPOExample;
import com.bear.crawler.webmagic.pojo.dto.CommonRespDto;
import com.bear.crawler.webmagic.pojo.dto.WPublicAccountDto;
import com.bear.crawler.webmagic.pojo.dto.WPublicAccountsRespDto;
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

@Slf4j
@Component
public class WPublicAccountProcessor implements PageProcessor {

    private static final String BEGIN = "begin";
    private static final int ACCOUNT_LIMIT = 100;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WPublicAccountPOMapper wPublicAccountPOMapper;

    @Autowired
    private WPublicAccountProvider wPublicAccountProvider;

    @Override
    public void process(Page page) {
        try {
            WPublicAccountsRespDto accountsRespDto = objectMapper.readValue(page.getRawText(), WPublicAccountsRespDto.class);
            CommonRespDto commonRespDto = accountsRespDto.getCommonRespDto();
            if (OtherUtil.checkCommonRespDto(commonRespDto, "WPublicAccountProcessor.process()")) {
                int begin = getBegin(page);
                List<WPublicAccountDto> accountDtos = accountsRespDto.getPublicAccountDtos();
                if (accountDtos == null) {
                    log.info("accountDtos is null");
                } else {
                    if (accountDtos.isEmpty()) {
                        log.info("Load public account list to end, begin = {}", begin);
                    } else {
                        log.info("Load public account list successfully, begin = {}", begin);
                        saveAccountDtosToDB(accountDtos);
                        OtherUtil.sleep(3);
                        if (begin + accountDtos.size() > ACCOUNT_LIMIT) {
                            log.info("Load public account list more than {}", ACCOUNT_LIMIT);
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

    private void saveAccountDtosToDB(List<WPublicAccountDto> accountDtos) {
        for (WPublicAccountDto accountDto : accountDtos) {
            if (!isInAccountDB(accountDto)) {
                wPublicAccountPOMapper.insert(TransformBeanUtil.dtoToPo(accountDto));
            } else {
                WPublicAccountPO accountPO = TransformBeanUtil.dtoToPo(accountDto);
                WPublicAccountPOExample example = new WPublicAccountPOExample();
                example.createCriteria().andFakeIdEqualTo(accountPO.getFakeId());
                wPublicAccountPOMapper.updateByExampleSelective(accountPO, example);
            }
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

    private boolean isInAccountDB(WPublicAccountDto accountDto) {
        List<WPublicAccountPO> wPublicAccountPOS = wPublicAccountProvider.getWPublicAccountPOS();
        for (WPublicAccountPO accountPO : wPublicAccountPOS) {
            if (accountPO.getFakeId().equals(accountDto.getFakeId())) {
                return true;
            }
        }
        return false;
    }

    // TODO: 5/15/23 private boolean checkChange()
}