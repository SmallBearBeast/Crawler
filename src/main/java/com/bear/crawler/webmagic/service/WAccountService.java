package com.bear.crawler.webmagic.service;

import cn.hutool.core.collection.CollectionUtil;
import com.bear.crawler.webmagic.basic.http.OkHttp;
import com.bear.crawler.webmagic.dao.WAccountDao;
import com.bear.crawler.webmagic.mybatis.generator.po.WAccountPO;
import com.bear.crawler.webmagic.pojo.WechatProperties;
import com.bear.crawler.webmagic.pojo.dto.WAccountDto;
import com.bear.crawler.webmagic.pojo.dto.resp.WAccountsRespDto;
import com.bear.crawler.webmagic.processor.WAccountProcessor;
import com.bear.crawler.webmagic.provider.WAccountProvider;
import com.bear.crawler.webmagic.util.BeanConverterUtil;
import com.bear.crawler.webmagic.util.OtherUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Service
public class WAccountService {

    @Autowired
    private WechatProperties wechatProperties;

    @Autowired
    private WAccountProcessor wAccountProcessor;

    @Autowired
    private WAccountProvider wAccountProvider;

    @Autowired
    private WAccountDao wAccountDao;

    @Autowired
    private OkHttp okHttp;

    public void searchAndSyncWAccount(String searchContent) {
        log.debug("searchAndSyncWAccount : searchContent = {}", searchContent);
        String encodeQuery = URLEncoder.encode(searchContent, StandardCharsets.UTF_8);
        String url = "https://mp.weixin.qq.com/cgi-bin/searchbiz?action=search_biz&begin=0&count=5&query=" + encodeQuery + "&token=" + wechatProperties.getToken() + "&lang=zh_CN&f=json&ajax=1";
        Request request = new Request(url);
        request.addHeader("cookie", wechatProperties.getCookie());
        request.addHeader("user-agent", wechatProperties.getUserAgent());
        Spider.create(wAccountProcessor)
                .addRequest(request)
                .thread(1)
                .run();
    }

    public void updateWAccountNeedToFetchByName(List<String> accountNames, boolean needToFetch) {
        log.debug("updateWAccountNeedToFetchByName, accountNames = {}, needToFetch = {}", accountNames, needToFetch);
        if (CollectionUtil.isEmpty(accountNames)) {
            return;
        }
        for (String accountName : accountNames) {
            WAccountPO accountPO = wAccountProvider.findByNickname(accountName);
            if (accountPO != null && needToFetch != accountPO.getNeedFetch()) {
                accountPO.setNeedFetch(needToFetch);
                wAccountDao.updateByFakeId(accountPO);
                wAccountProvider.updateCache(accountPO, false);
            }
        }
    }

    public void updateWAccountNeedToFetchByFakeId(List<String> fakeIds, boolean needToFetch) {
        log.debug("updateWAccountNeedToFetchByFakeId, fakeIds = {}, needToFetch = {}", fakeIds, needToFetch);
        if (CollectionUtil.isEmpty(fakeIds)) {
            return;
        }
        for (String fakeId : fakeIds) {
            WAccountPO accountPO = wAccountProvider.findByFakeId(fakeId);
            if (accountPO != null && needToFetch != accountPO.getNeedFetch()) {
                accountPO.setNeedFetch(needToFetch);
                wAccountDao.updateByFakeId(accountPO);
                wAccountProvider.updateCache(accountPO, false);
            }
        }
    }

    // 使用RestTemplate get请求没有添加header的方法
    // 使用RestTemplate拿不到list数据，为空，因此使用okhttp。
    public void findAndSyncWAccount(String accountName, boolean needToFetch) {
        log.debug("findAndSyncWAccount: accountName = {}, needToFetch = {}", accountName, needToFetch);
        String encodeAccountName = URLEncoder.encode(accountName, StandardCharsets.UTF_8);
        String url = "https://mp.weixin.qq.com/cgi-bin/searchbiz?action=search_biz&begin=0&count=5&query=" + encodeAccountName + "&token={{token}}&lang=zh_CN&f=json&ajax=1";
        WAccountPO existAccountPO = wAccountProvider.findByNickname(accountName);
        if (existAccountPO != null) {
            log.debug("findAndSyncWAccount: exist accountPO, needToFetch = {}", existAccountPO.getNeedFetch());
            if (existAccountPO.getNeedFetch() != needToFetch) {
                existAccountPO.setNeedFetch(needToFetch);
                wAccountDao.updateByFakeId(existAccountPO);
                wAccountProvider.updateCache(existAccountPO, false);
            }
        } else {
            WAccountsRespDto accountsRespDto = okHttp.get(url, null, null, WAccountsRespDto.class);
            if (OtherUtil.checkCommonRespDto(accountsRespDto, "WechatService.findAndSyncWAccount()")) {
                List<WAccountDto> accountDtos = accountsRespDto.getAccountDtos();
                for (WAccountDto accountDto : accountDtos) {
                    if (accountName.equalsIgnoreCase(accountDto.getNickname())) {
                        WAccountPO accountPO = BeanConverterUtil.dtoToPo(accountDto);
                        accountPO.setNeedFetch(needToFetch);
                        log.debug("findAndSyncWAccount: insert or update accountPO, accountName = {}", accountPO.getNickname());
                        if (wAccountProvider.isInAccountDB(accountPO)) {
                            wAccountDao.updateByFakeId(accountPO);
                        } else {
                            wAccountDao.insert(accountPO);
                        }
                        wAccountProvider.updateCache(accountPO, false);
                        return;
                    }
                }
                log.debug("findAndSyncWAccount: no found, accountName = {}", accountName);
            }
        }
    }
}
