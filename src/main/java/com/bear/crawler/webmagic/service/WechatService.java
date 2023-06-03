package com.bear.crawler.webmagic.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import com.bear.crawler.webmagic.dao.WAccountDao;
import com.bear.crawler.webmagic.basic.http.OkHttp;
import com.bear.crawler.webmagic.mybatis.generator.po.WArticleItemPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WAccountPO;
import com.bear.crawler.webmagic.pojo.WechatConfig;
import com.bear.crawler.webmagic.pojo.dto.WAccountDto;
import com.bear.crawler.webmagic.pojo.dto.WAccountsRespDto;
import com.bear.crawler.webmagic.processor.WAccountProcessor;
import com.bear.crawler.webmagic.processor.WArticleDetailProcessor;
import com.bear.crawler.webmagic.processor.WArticleProcessor;
import com.bear.crawler.webmagic.provider.WArticleProvider;
import com.bear.crawler.webmagic.provider.WAccountProvider;
import com.bear.crawler.webmagic.util.OtherUtil;
import com.bear.crawler.webmagic.util.TransformBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Service
public class WechatService {

    @Autowired
    private WechatConfig wechatConfig;

    @Autowired
    private WAccountDao wAccountDao;

    @Autowired
    private WAccountProvider wAccountProvider;

    @Autowired
    private WArticleProvider wArticleProvider;

    @Autowired
    private OkHttp okHttp;

    @Autowired
    private WArticleDetailProcessor wArticleDetailProcessor;

    @Autowired
    private WArticleProcessor wArticleProcessor;

    @Autowired
    private WAccountProcessor wAccountProcessor;

    public void fetchWArticleDetail(String query) {
        Spider.create(wArticleDetailProcessor)
                .addUrl("https://mp.weixin.qq.com/s/" + query)
                .thread(5)
                .run();
    }

    public void listenWArticlesUpdate() {
        log.debug("listenWArticlesUpdate: enter");
        Map<String, WAccountPO> accountPOMap = wAccountProvider.getNeedFetchAccountMap();
        Spider spider = Spider.create(wArticleProcessor);
        for (WAccountPO accountPO : accountPOMap.values()) {
            addRequestToSpider(spider, accountPO);
        }
        spider.thread(5).start();
    }

    public void listenWArticlesUpdate(List<String> accountNames) {
        log.debug("listenWArticlesUpdate: accountNames = {}", accountNames);
        Spider spider = Spider.create(wArticleProcessor);
        for (String accountName : accountNames) {
            WAccountPO accountPO = wAccountProvider.findByNickname(accountName);
            if (accountPO == null) {
                log.info("listenWArticlesUpdate: {} do not exist", accountName);
            } else {
                addRequestToSpider(spider, accountPO);
            }
        }
        spider.thread(1).start();
    }

    private void addRequestToSpider(Spider spider, WAccountPO accountPO) {
        String url = "https://mp.weixin.qq.com/cgi-bin/appmsg?action=list_ex&begin=0&count=5&fakeid=" + accountPO.getFakeId() + "&type=9&query=&token=" + wechatConfig.getToken() + "&lang=zh_CN&f=json&ajax=1";
        Request request = new Request(url);
        request.addHeader("cookie", wechatConfig.getCookie());
        request.addHeader("user-agent", wechatConfig.getUserAgent());
        spider.addRequest(request);
    }

    public void searchAndInsertWAccount(String searchContent) {
        log.debug("searchAndInsertWAccount : searchContent = {}", searchContent);
        String encodeQuery = URLEncoder.encode(searchContent, StandardCharsets.UTF_8);
        String url = "https://mp.weixin.qq.com/cgi-bin/searchbiz?action=search_biz&begin=0&count=5&query=" + encodeQuery + "&token=" + wechatConfig.getToken() + "&lang=zh_CN&f=json&ajax=1";
        Request request = new Request(url);
        request.addHeader("cookie", wechatConfig.getCookie());
        request.addHeader("user-agent", wechatConfig.getUserAgent());
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
                wAccountProvider.updateNeedToFetchCache(accountPO);
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
                wAccountProvider.updateNeedToFetchCache(accountPO);
            }
        }
    }

    // 使用RestTemplate get请求没有添加header的方法
    // 使用RestTemplate拿不到list数据，为空，因此使用okhttp。
    public void findAndInsertWAccount(String accountName, boolean needToFetch) {
        log.debug("findAndInsertWAccount: accountName = {}, needToFetch = {}", accountName, needToFetch);
        String encodeAccountName = URLEncoder.encode(accountName, StandardCharsets.UTF_8);
        String url = "https://mp.weixin.qq.com/cgi-bin/searchbiz?action=search_biz&begin=0&count=5&query=" + encodeAccountName + "&token=" + wechatConfig.getToken() + "&lang=zh_CN&f=json&ajax=1";
        WAccountPO existAccountPO = wAccountProvider.findByNickname(accountName);
        if (existAccountPO != null) {
            log.debug("findAndInsertWAccount: exist accountPO, needToFetch = {}", existAccountPO.getNeedFetch());
            if (existAccountPO.getNeedFetch() != needToFetch) {
                existAccountPO.setNeedFetch(needToFetch);
                wAccountDao.updateByFakeId(existAccountPO);
                wAccountProvider.updateNeedToFetchCache(existAccountPO);
            }
        } else {
            Map<String, String> headers = MapUtil.builder("Cookie", wechatConfig.getCookie())
                    .put("User-Agent", wechatConfig.getUserAgent()).build();
            WAccountsRespDto accountsRespDto = okHttp.get(url, null, headers, WAccountsRespDto.class);
            if (OtherUtil.checkCommonRespDto(accountsRespDto.getCommonRespDto(), "WechatService.searchAccountAndInsert()")) {
                List<WAccountDto> accountDtos = accountsRespDto.getAccountDtos();
                for (WAccountDto accountDto : accountDtos) {
                    if (accountName.equals(accountDto.getNickname())) {
                        WAccountPO accountPO = TransformBeanUtil.dtoToPo(accountDto);
                        accountPO.setNeedFetch(needToFetch);
                        log.debug("findAndInsertWAccount: insert or update accountPO, id = {}", accountPO.getId());
                        if (wAccountProvider.isInAccountDB(accountPO)) {
                            wAccountDao.updateByFakeId(accountPO);
                        } else {
                            wAccountDao.insert(accountPO);
                        }
                        wAccountProvider.updateCache(accountPO);
                        wAccountProvider.updateNeedToFetchCache(accountPO);
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
        List<WAccountPO> accountPOS = wAccountProvider.getNeedFetchAccounts();
        for (WAccountPO accountPO : accountPOS) {
            List<WArticleItemPO> articlePOS = wArticleProvider.getCurDateArticles(accountPO.getFakeId());
        }
    }
}
