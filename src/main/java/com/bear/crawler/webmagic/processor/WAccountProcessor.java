package com.bear.crawler.webmagic.processor;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import com.bear.crawler.webmagic.AppConstant;
import com.bear.crawler.webmagic.dao.WAccountDao;
import com.bear.crawler.webmagic.mybatis.generator.po.WAccountPO;
import com.bear.crawler.webmagic.pojo.dto.WAccountDto;
import com.bear.crawler.webmagic.pojo.dto.WAccountsRespDto;
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

import java.util.List;

@Slf4j
@Component
public class WAccountProcessor implements PageProcessor {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WAccountDao wAccountDao;

    @Autowired
    private WAccountProvider wAccountProvider;

    @Override
    public void process(Page page) {
        try {
            WAccountsRespDto accountsRespDto = objectMapper.readValue(page.getRawText(), WAccountsRespDto.class);
            if (OtherUtil.checkCommonRespDto(accountsRespDto, "WAccountProcessor.process()")) {
                int begin = getBegin(page);
                List<WAccountDto> accountDtos = accountsRespDto.getAccountDtos();
                if (accountDtos == null) {
                    log.info("accountDtos is null");
                } else {
                    if (accountDtos.isEmpty()) {
                        log.info("Load public account list to end, begin = {}", begin);
                    } else {
                        log.info("Load public account list successfully, begin = {}", begin);
                        saveAccountDtosToDB(accountDtos);
                        if (begin + accountDtos.size() > AppConstant.ACCOUNT_LIMIT) {
                            log.info("Load public account list more than {}", AppConstant.ACCOUNT_LIMIT);
                        } else {
                            addNextTargetRequest(page, begin + 5);
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
                .setSleepTime(RandomUtil.randomInt(3, 6) * 1000)
                .setRetrySleepTime(RandomUtil.randomInt(3, 6) * 1000);
    }

    private void saveAccountDtosToDB(List<WAccountDto> accountDtos) {
        for (WAccountDto accountDto : accountDtos) {
            WAccountPO accountPO = BeanConverterUtil.dtoToPo(accountDto);
            if (!wAccountProvider.isInAccountDB(accountPO)) {
                wAccountDao.insert(accountPO);
            } else {
                wAccountDao.updateByFakeId(accountPO);
            }
            wAccountProvider.updateCache(accountPO);
        }
    }

    private int getBegin(Page page) {
        return Integer.parseInt(OtherUtil.getQuery(page.getUrl().get(), AppConstant.BEGIN));
    }

    private void addNextTargetRequest(Page page, int begin) {
        String newUrl = OtherUtil.getNewUrlByParams(page.getUrl().get(), MapUtil.of(AppConstant.BEGIN, begin));
        page.addTargetRequest(newUrl);
    }

    // TODO: 5/15/23 private boolean checkChange()
}
