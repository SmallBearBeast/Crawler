package com.bear.crawler.webmagic.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import com.bear.crawler.webmagic.dao.WAccountDao;
import com.bear.crawler.webmagic.basic.http.OkHttp;
import com.bear.crawler.webmagic.dao.WUserInfoDao;
import com.bear.crawler.webmagic.mybatis.generator.po.WAccountPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WUserInfoPO;
import com.bear.crawler.webmagic.pojo.WechatConfig;
import com.bear.crawler.webmagic.pojo.dto.BaseRespDto;
import com.bear.crawler.webmagic.pojo.dto.ConversationsRespDto;
import com.bear.crawler.webmagic.pojo.dto.SendMsgRespDto;
import com.bear.crawler.webmagic.pojo.dto.WUserInfoDto;
import com.bear.crawler.webmagic.pojo.dto.UserInfosRespDto;
import com.bear.crawler.webmagic.pojo.dto.WAccountDto;
import com.bear.crawler.webmagic.pojo.dto.WAccountsRespDto;
import com.bear.crawler.webmagic.processor.WAccountProcessor;
import com.bear.crawler.webmagic.processor.WArticleDetailProcessor;
import com.bear.crawler.webmagic.processor.WArticleProcessor;
import com.bear.crawler.webmagic.provider.WAccountProvider;
import com.bear.crawler.webmagic.provider.WUserInfoProvider;
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
    private OkHttp okHttp;

    @Autowired
    private WArticleDetailProcessor wArticleDetailProcessor;

    @Autowired
    private WArticleProcessor wArticleProcessor;

    @Autowired
    private WAccountProcessor wAccountProcessor;

    @Autowired
    private WUserInfoProvider wUserInfoProvider;

    @Autowired
    private WUserInfoDao wUserInfoDao;

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

    public void syncRecentConversions() {
        String url = "https://mp.weixin.qq.com/cgi-bin/message?frommsgid=&count=20&day=7&filtertype=0&getunreadmsg=0&withbizmsg=1&keyword=&filterivrmsg=1&filterspammsg=1&count_per_user=1&offset=&token={{token}}&lang=zh_CN&f=json&ajax=1";
        ConversationsRespDto respDto = okHttp.get(url, null, null, ConversationsRespDto.class);
        log.debug("syncRecentConversions: enter");
    }

    public void sendMessageToAllUser() {
        log.debug("sendMessageToAllUser: enter");
        String url = "https://mp.weixin.qq.com/cgi-bin/singlesend?t=ajax-response&f=json";
        String referer = "https://mp.weixin.qq.com/cgi-bin/message?t=message/list&count=20&day=7&token=" + wechatConfig.getToken() + "&lang=zh_CN";
        Map<String, String> headerMap = MapUtil.of("referer", referer);
        List<WUserInfoPO> userInfoPOS = wUserInfoProvider.getRecentUserInfos();
        for (WUserInfoPO userInfoPO : userInfoPOS) {
            Map<String, String> paramMap = MapUtil.builder("tofakeid", userInfoPO.getOpenid())
                    .put("quickreplyid", "")
                    .put("imgcode", "")
                    .put("type", "10")
                    .put("appmsgid", "2247483856")
                    .put("token", wechatConfig.getToken())
                    .put("lang", "zh_CN")
                    .put("f", "json")
                    .put("ajax", "1")
                    .build();
            SendMsgRespDto respDto = okHttp.post(url, paramMap, headerMap, SendMsgRespDto.class);
            if (OtherUtil.checkCommonRespDto(respDto.getCommonRespDto(), "WechatService.sendMessageToAllUser()")) {
                log.info("sendMessageToAllUser: send message to {} success", userInfoPO.getName());
            } else {
                log.info("sendMessageToAllUser: send message to {} fail", userInfoPO.getName());
            }
        }
    }

    public void syncUserInfos() {
        syncUserInfosInternal(0);
    }

    private void syncUserInfosInternal(int offset) {
        log.debug("syncUserInfosInternal: offset = {}", offset);
        String url = "https://mp.weixin.qq.com/cgi-bin/user_tag?action=get_user_list&groupid=-2&begin_openid=-1&begin_create_time=-1&limit=20&offset=0&backfoward=1&token={{token}}&lang=zh_CN&f=json&ajax=1&random=0.8786165673080111";
        String newUrl = OtherUtil.getNewUrlByParams(url, MapUtil.of("offset", offset));
        UserInfosRespDto respDto = okHttp.get(newUrl, null, null, UserInfosRespDto.class);
        List<WUserInfoDto> userInfoDtos = respDto.getUserInfos();
        if (CollectionUtil.isEmpty(userInfoDtos)) {
            log.debug("syncUserInfosInternal: sync end");
            return;
        }
        for (WUserInfoDto dto : userInfoDtos) {
            WUserInfoPO userInfoPO = TransformBeanUtil.dtoToPo(dto);
            if (wUserInfoProvider.isInDB(userInfoPO)) {
                wUserInfoDao.updateByOpenId(userInfoPO);
            } else {
                wUserInfoDao.insert(userInfoPO);
            }
            wUserInfoProvider.updateCache(userInfoPO);
        }
        OtherUtil.sleep(RandomUtil.randomInt(3, 6));
        syncUserInfosInternal(offset + userInfoDtos.size());
    }
}
