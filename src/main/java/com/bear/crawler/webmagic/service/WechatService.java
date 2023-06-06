package com.bear.crawler.webmagic.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import com.bear.crawler.webmagic.AppConstant;
import com.bear.crawler.webmagic.dao.WAccountDao;
import com.bear.crawler.webmagic.basic.http.OkHttp;
import com.bear.crawler.webmagic.dao.WArticleDao;
import com.bear.crawler.webmagic.dao.WUserInfoDao;
import com.bear.crawler.webmagic.mybatis.generator.po.WAccountPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WArticleItemPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WUserInfoPO;
import com.bear.crawler.webmagic.pojo.WechatConfig;
import com.bear.crawler.webmagic.pojo.dto.ConversationsRespDto;
import com.bear.crawler.webmagic.pojo.dto.SendMsgRespDto;
import com.bear.crawler.webmagic.pojo.dto.WArticleItemDto;
import com.bear.crawler.webmagic.pojo.dto.WArticleItemsRespDto;
import com.bear.crawler.webmagic.pojo.dto.WUserInfoDto;
import com.bear.crawler.webmagic.pojo.dto.UserInfosRespDto;
import com.bear.crawler.webmagic.pojo.dto.WAccountDto;
import com.bear.crawler.webmagic.pojo.dto.WAccountsRespDto;
import com.bear.crawler.webmagic.processor.WAccountProcessor;
import com.bear.crawler.webmagic.processor.WArticleDetailProcessor;
import com.bear.crawler.webmagic.processor.WArticleProcessor;
import com.bear.crawler.webmagic.provider.WAccountProvider;
import com.bear.crawler.webmagic.provider.WArticleProvider;
import com.bear.crawler.webmagic.provider.WUserInfoProvider;
import com.bear.crawler.webmagic.util.OtherUtil;
import com.bear.crawler.webmagic.util.BeanConverterUtil;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WechatService {

    @Autowired
    private WechatConfig wechatConfig;

    @Autowired
    private WArticleDao wArticleDao;

    @Autowired
    private WAccountDao wAccountDao;

    @Autowired
    private WArticleProvider wArticleProvider;

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

    public void searchAndSyncWAccount(String searchContent) {
        log.debug("searchAndSyncWAccount : searchContent = {}", searchContent);
        String encodeQuery = URLEncoder.encode(searchContent, StandardCharsets.UTF_8);
        String url = "https://mp.weixin.qq.com/cgi-bin/searchbiz?action=search_biz&begin=0&count=5&query=" + encodeQuery + "&token={{token}}&lang=zh_CN&f=json&ajax=1";
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
                wAccountProvider.updateNeedToFetchCache(existAccountPO);
            }
        } else {
            WAccountsRespDto accountsRespDto = okHttp.get(url, null, null, WAccountsRespDto.class);
            if (OtherUtil.checkCommonRespDto(accountsRespDto, "WechatService.searchAccountAndInsert()")) {
                List<WAccountDto> accountDtos = accountsRespDto.getAccountDtos();
                for (WAccountDto accountDto : accountDtos) {
                    if (accountName.equalsIgnoreCase(accountDto.getNickname())) {
                        WAccountPO accountPO = BeanConverterUtil.dtoToPo(accountDto);
                        accountPO.setNeedFetch(needToFetch);
                        log.debug("findAndSyncWAccount: insert or update accountPO, id = {}", accountPO.getId());
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
                log.debug("findAndSyncWAccount: no found, accountName = {}", accountName);
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
            WUserInfoPO userInfoPO = BeanConverterUtil.dtoToPo(dto);
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

    public void syncRecentConversions() {
        String url = "https://mp.weixin.qq.com/cgi-bin/message?frommsgid=&count=20&day=7&filtertype=0&getunreadmsg=0&withbizmsg=1&keyword=&filterivrmsg=1&filterspammsg=1&count_per_user=1&offset=&token={{token}}&lang=zh_CN&f=json&ajax=1";
        ConversationsRespDto respDto = okHttp.get(url, null, null, ConversationsRespDto.class);
        log.debug("syncRecentConversions: enter");
    }

    public void sendMsgToRecentUser(String aid) {
        log.debug("sendMsgToRecentUser: aid = {}", aid);
        String url = "https://mp.weixin.qq.com/cgi-bin/singlesend?t=ajax-response&f=json";
        String referer = "https://mp.weixin.qq.com/cgi-bin/message?t=message/list&count=20&day=7&token=" + wechatConfig.getToken() + "&lang=zh_CN";
        Map<String, String> headerMap = MapUtil.of(AppConstant.REFERER, referer);
        List<WUserInfoPO> userInfoPOS = wUserInfoProvider.getRecentUserInfos();
        for (WUserInfoPO userInfoPO : userInfoPOS) {
            Map<String, String> paramMap = MapUtil.builder("tofakeid", userInfoPO.getOpenid())
                    .put("quickreplyid", "")
                    .put("imgcode", "")
                    .put("type", "10")
                    .put("appmsgid", aid)
                    .put("token", wechatConfig.getToken())
                    .put("lang", "zh_CN")
                    .put("f", "json")
                    .put("ajax", "1")
                    .build();
            SendMsgRespDto respDto = okHttp.post(url, paramMap, headerMap, SendMsgRespDto.class);
            if (OtherUtil.checkCommonRespDto(respDto, "WechatService.sendMessageToAllUser()")) {
                log.info("sendMessageToAllUser: send message to {} success", userInfoPO.getName());
            } else {
                log.info("sendMessageToAllUser: send message to {} fail", userInfoPO.getName());
            }
        }
    }

    public void syncArticle(List<String> fakeIds, List<String> accountNames) {
        List<String> finalFakeIds = fakeIds;
        if (CollectionUtil.isEmpty(fakeIds)) {
            finalFakeIds = accountNames.stream().map(name -> {
                WAccountPO accountPO = wAccountProvider.findByNickname(name);
                return accountPO == null ? null : accountPO.getFakeId();
            }).filter(Objects::nonNull).collect(Collectors.toList());
        }
        String url = "https://mp.weixin.qq.com/cgi-bin/appmsg?action=list_ex&begin=0&count=5&fakeid={{fakeId}}&type=9&query=&token={{token}}&lang=zh_CN&f=json&ajax=1";
        for (String fakeId : finalFakeIds) {
            String newUrl = OtherUtil.getNewUrlByParams(url, MapUtil.of(AppConstant.FAKE_ID, fakeId));
            syncArticle(newUrl, fakeId, new ArrayList<>());
        }
    }

    private void syncArticle(String url, String fakeId, List<WArticleItemPO> saveArticleItemPOS) {
        WArticleItemsRespDto respDto = okHttp.get(url, null, null, WArticleItemsRespDto.class);
        if (OtherUtil.checkCommonRespDto(respDto, "WechatService.syncArticle()")) {
            List<WArticleItemDto> articleItemDtos = respDto.getArticleItemDtos();
            int begin = Integer.parseInt(OtherUtil.getQuery(url, AppConstant.BEGIN));
            if (CollectionUtil.isEmpty(articleItemDtos)) {
                log.info("syncArticle: articleItemDtos is empty, begin = {}", begin);
                onFetchArticlesEnd(fakeId, saveArticleItemPOS);
                return;
            }
            log.info("syncArticle: load article list successfully, begin = {}", begin);
            articleItemDtos.sort((first, second) -> (int) (second.getUpdateTime() - first.getUpdateTime()));
            long lastLatestTime = wArticleProvider.getLastLatestTime(fakeId);
            long curNewestTime = CollectionUtil.getFirst(articleItemDtos).getUpdateTime();
            if (curNewestTime > lastLatestTime) {
                saveArticleItemDtoToDB(articleItemDtos, fakeId, saveArticleItemPOS);
                int articleSize = saveArticleItemPOS.size();
                if (articleSize >= AppConstant.ARTICLE_LIMIT) {
                    log.info("syncArticle: load article list more than {}, begin = {}", AppConstant.ARTICLE_LIMIT, begin);
                    onFetchArticlesEnd(fakeId, saveArticleItemPOS);
                } else {
                    String newUrl = OtherUtil.getNewUrlByParams(url, MapUtil.of(AppConstant.BEGIN, begin + articleItemDtos.size()));
                    OtherUtil.sleep(RandomUtil.randomInt(3, 6));
                    syncArticle(newUrl, fakeId, saveArticleItemPOS);
                }
            } else {
                log.info("syncArticle: load the last latest article, begin = {}", begin);
                onFetchArticlesEnd(fakeId, saveArticleItemPOS);
            }
        }
    }

    private void saveArticleItemDtoToDB(List<WArticleItemDto> articleItemDtos, String fakeId, List<WArticleItemPO> articleItemPOS) {
        WAccountPO accountPO = wAccountProvider.findByFakeId(fakeId);
        for (WArticleItemDto itemDto : articleItemDtos) {
            WArticleItemPO itemPo = BeanConverterUtil.dtoToPo(itemDto);
            itemPo.setOfficialAccountId(accountPO.getId());
            itemPo.setOfficialAccountFakeId(accountPO.getFakeId());
            itemPo.setOfficialAccountTitle(accountPO.getNickname());
            if (wArticleProvider.isInDB(itemPo)) {
                wArticleDao.updateByAid(itemPo);
            } else {
                wArticleDao.insert(itemPo);
                articleItemPOS.add(itemPo);
            }
            wArticleProvider.updateCache(itemPo);
        }
    }

    private void onFetchArticlesEnd(String fakeId, List<WArticleItemPO> articleItemPOS) {
        WArticleItemPO articleItemPO = CollectionUtil.getFirst(articleItemPOS);
        if (articleItemPO != null) {
            wArticleProvider.setLastLatestTime(fakeId, articleItemPO.getUpdateTime().getTime() / 1000);
        }
    }

    // TODO: 6/5/23 把自己账号加入数据库
    // TODO: 6/5/23 article表新增isMe，state字段，或者关联出一张表出来。
    // TODO: 6/5/23 群发发布的article。 
    // TODO: 6/5/23 loadTodayWaitToPublishArticles 
    // TODO: 6/5/23 草稿？
}
