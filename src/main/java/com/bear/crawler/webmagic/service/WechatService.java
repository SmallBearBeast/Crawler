package com.bear.crawler.webmagic.service;

import cn.hutool.core.io.FileUtil;
import com.bear.crawler.webmagic.dao.WPublicAccountDao;
import com.bear.crawler.webmagic.mybatis.generator.po.WArticleItemPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WPublicAccountPO;
import com.bear.crawler.webmagic.provider.WArticleProvider;
import com.bear.crawler.webmagic.provider.WPublicAccountProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

@Slf4j
@Service
public class WechatService {

    @Autowired
    private WPublicAccountDao wPublicAccountDao;

    @Autowired
    private WPublicAccountProvider wPublicAccountProvider;

    @Autowired
    private WArticleProvider wArticleProvider;

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

    public void loadTodayArticles() {
        log.debug("loadTodayArticles");
        StringBuilder builder = new StringBuilder();
        List<WPublicAccountPO> accountPOS = wPublicAccountProvider.getNeedFetchAccounts();
        for (WPublicAccountPO accountPO : accountPOS) {
            List<WArticleItemPO> articlePOS = wArticleProvider.getCurDateArticles(accountPO.getFakeId());
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
}
