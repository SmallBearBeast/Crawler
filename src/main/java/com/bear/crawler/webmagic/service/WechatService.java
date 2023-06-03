package com.bear.crawler.webmagic.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import com.bear.crawler.webmagic.dao.WPublicAccountDao;
import com.bear.crawler.webmagic.basic.http.OkHttp;
import com.bear.crawler.webmagic.mybatis.generator.po.WArticleItemPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WPublicAccountPO;
import com.bear.crawler.webmagic.pojo.WechatConfig;
import com.bear.crawler.webmagic.pojo.dto.WPublicAccountDto;
import com.bear.crawler.webmagic.pojo.dto.WPublicAccountsRespDto;
import com.bear.crawler.webmagic.provider.WArticleProvider;
import com.bear.crawler.webmagic.provider.WPublicAccountProvider;
import com.bear.crawler.webmagic.util.OtherUtil;
import com.bear.crawler.webmagic.util.TransformBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Service
public class WechatService {

    @Autowired
    private WechatConfig wechatConfig;

    @Autowired
    private WPublicAccountDao wPublicAccountDao;

    @Autowired
    private WPublicAccountProvider wPublicAccountProvider;

    @Autowired
    private WArticleProvider wArticleProvider;

    @Autowired
    private OkHttp okHttp;

    public void updateAccountNeedToFetchByName(@NonNull List<String> accountNames, boolean needToFetch) {
        log.debug("updateAccountNeedToFetchByName, accountNames = {}, needToFetch = {}", accountNames, needToFetch);
        for (String accountName : accountNames) {
            WPublicAccountPO accountPO = wPublicAccountProvider.findByNickname(accountName);
            if (accountPO != null && needToFetch != accountPO.getNeedFetch()) {
                accountPO.setNeedFetch(needToFetch);
                wPublicAccountDao.updateByFakeId(accountPO);
                wPublicAccountProvider.updateNeedToFetchCache(accountPO);
            }
        }
    }

    public void updateAccountNeedToFetchByFakeId(@NonNull List<String> fakeIds, boolean needToFetch) {
        log.debug("updateAccountNeedToFetchByFakeId, fakeIds = {}, needToFetch = {}", fakeIds, needToFetch);
        for (String fakeId : fakeIds) {
            WPublicAccountPO accountPO = wPublicAccountProvider.findByFakeId(fakeId);
            if (accountPO != null && needToFetch != accountPO.getNeedFetch()) {
                accountPO.setNeedFetch(needToFetch);
                wPublicAccountDao.updateByFakeId(accountPO);
                wPublicAccountProvider.updateNeedToFetchCache(accountPO);
            }
        }
    }

    public void findAndInsertWAccount(String url, String accountName, boolean needToFetch) {
        log.debug("findAndInsertWAccount: accountName = {}, needToFetch = {}", accountName, needToFetch);
        WPublicAccountPO existAccountPO = wPublicAccountProvider.findByNickname(accountName);
        if (existAccountPO != null) {
            log.debug("findAndInsertWAccount: exist accountPO, needToFetch = {}", existAccountPO.getNeedFetch());
            if (existAccountPO.getNeedFetch() != needToFetch) {
                existAccountPO.setNeedFetch(needToFetch);
                wPublicAccountDao.updateByFakeId(existAccountPO);
                wPublicAccountProvider.updateNeedToFetchCache(existAccountPO);
            }
        } else  {
            Map<String, String> headers = MapUtil.builder("Cookie", wechatConfig.getCookie())
                    .put("User-Agent", wechatConfig.getUserAgent()).build();
            WPublicAccountsRespDto accountsRespDto = okHttp.get(url, null, headers, WPublicAccountsRespDto.class);
            if (OtherUtil.checkCommonRespDto(accountsRespDto.getCommonRespDto(), "WechatService.searchAccountAndInsert()")) {
                List<WPublicAccountDto> accountDtos = accountsRespDto.getPublicAccountDtos();
                for (WPublicAccountDto accountDto : accountDtos) {
                    if (accountName.equals(accountDto.getNickname())) {
                        WPublicAccountPO accountPO = TransformBeanUtil.dtoToPo(accountDto);
                        accountPO.setNeedFetch(needToFetch);
                        log.debug("findAndInsertWAccount: insert or update accountPO, id = {}", accountPO.getId());
                        if (wPublicAccountProvider.isInAccountDB(accountPO)) {
                            wPublicAccountDao.updateByFakeId(accountPO);
                        } else {
                            wPublicAccountDao.insert(accountPO);
                        }
                        wPublicAccountProvider.updateCache(accountPO);
                        wPublicAccountProvider.updateNeedToFetchCache(accountPO);
                        return;
                    }
                }
                log.debug("findAndInsertWAccount: no found, accountName = {}", accountName);
            }
        }
    }

    // 动态改变target下的properties会触发springboot重启是因为加了spring-boot-devtools
    public void updateWechatProperties(String token, String cookie) {
        Properties properties = new Properties();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            String path = new ClassPathResource("wechat.properties").getURL().getFile();
            String pathTemp = new ClassPathResource("").getURL().getFile() + "wechat_temp.properties";
            inputStream = FileUtil.getInputStream(path);
            outputStream = FileUtil.getOutputStream(pathTemp);
            properties.load(inputStream);
            properties.setProperty("wechat.token", token);
            properties.setProperty("wechat.cookie", cookie);
            properties.store(outputStream, "");
            FileUtil.rename(FileUtil.file(pathTemp), path, true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void loadTodayArticles() {
        log.debug("loadTodayArticles");
        StringBuilder builder = new StringBuilder();
        List<WPublicAccountPO> accountPOS = wPublicAccountProvider.getNeedFetchAccounts();
        for (WPublicAccountPO accountPO : accountPOS) {
            List<WArticleItemPO> articlePOS = wArticleProvider.getCurDateArticles(accountPO.getFakeId());
        }
    }
}
