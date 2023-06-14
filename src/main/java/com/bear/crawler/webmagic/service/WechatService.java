package com.bear.crawler.webmagic.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import com.bear.crawler.webmagic.AppConstant;
import com.bear.crawler.webmagic.dao.WAccountDao;
import com.bear.crawler.webmagic.basic.http.OkHttp;
import com.bear.crawler.webmagic.dao.WArticleDao;
import com.bear.crawler.webmagic.dao.WMsgItemDao;
import com.bear.crawler.webmagic.dao.WUserInfoDao;
import com.bear.crawler.webmagic.manager.ArticleFileManager;
import com.bear.crawler.webmagic.mybatis.generator.po.WAccountPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WArticleItemPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WMsgItemPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WUserInfoPO;
import com.bear.crawler.webmagic.pojo.WechatConfig;
import com.bear.crawler.webmagic.pojo.dto.MsgItemDto;
import com.bear.crawler.webmagic.pojo.dto.WConversationDto;
import com.bear.crawler.webmagic.pojo.dto.resp.BaseRespDto;
import com.bear.crawler.webmagic.pojo.dto.resp.SendMsgRespDto;
import com.bear.crawler.webmagic.pojo.dto.WArticleItemDto;
import com.bear.crawler.webmagic.pojo.dto.resp.WArticleItemsRespDto;
import com.bear.crawler.webmagic.pojo.dto.resp.WConversationRespDto;
import com.bear.crawler.webmagic.pojo.dto.WUserInfoDto;
import com.bear.crawler.webmagic.pojo.dto.resp.UserInfosRespDto;
import com.bear.crawler.webmagic.pojo.dto.WAccountDto;
import com.bear.crawler.webmagic.pojo.dto.resp.WAccountsRespDto;
import com.bear.crawler.webmagic.pojo.dto.resp.WMyPublishRespDto;
import com.bear.crawler.webmagic.processor.WAccountProcessor;
import com.bear.crawler.webmagic.processor.WArticleDetailProcessor;
import com.bear.crawler.webmagic.processor.WArticleProcessor;
import com.bear.crawler.webmagic.provider.WAccountProvider;
import com.bear.crawler.webmagic.provider.WArticleProvider;
import com.bear.crawler.webmagic.provider.WMsgItemProvider;
import com.bear.crawler.webmagic.provider.WUserInfoProvider;
import com.bear.crawler.webmagic.util.OtherUtil;
import com.bear.crawler.webmagic.util.BeanConverterUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
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

    @Autowired
    private WMsgItemProvider wMsgItemProvider;

    @Autowired
    private WMsgItemDao wMsgItemDao;

    @Autowired
    private ArticleFileManager articleFileManager;

    @Autowired
    private ObjectMapper objectMapper;

    public void fetchWArticleDetail(String query) {
        Spider.create(wArticleDetailProcessor)
                .addUrl("https://mp.weixin.qq.com/s/" + query)
                .thread(5)
                .run();
    }

    // TODO: 6/6/23 频率控制
    public void listenWArticlesUpdate() {
        log.debug("listenWArticlesUpdate: enter");
        Map<String, WAccountPO> accountPOMap = wAccountProvider.getNeedFetchAccountMap();
        Spider spider = Spider.create(wArticleProcessor);
        for (WAccountPO accountPO : accountPOMap.values()) {
            addRequestToSpider(spider, accountPO);
        }
        spider.thread(2).start();
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

    public void updateHandleState(List<String> aids, int state) {
        for (String aid : aids) {
            WArticleItemPO articleItemPO = wArticleProvider.findByAid(aid);
            if (articleItemPO == null) {
                log.info("updateHandleState: the article do not exist, aid = {}, state = {}", aid, OtherUtil.articleStateToStr(state));
            } else {
                log.debug("updateHandleState: the article exist, title = {}, state = {}", articleItemPO.getTitle(), OtherUtil.articleStateToStr(state));
                articleItemPO.setHandleState(state);
                wArticleDao.updateByAid(articleItemPO);
                wArticleProvider.updateCache(articleItemPO, false);
            }
        }
    }

    public void syncUserInfos() {
        Set<String> openIdSet = new HashSet<>();
        syncUserInfosInternal(0, openIdSet);
        removeUnFollowUserInfo(openIdSet);
    }

    private void syncUserInfosInternal(int offset, Set<String> openIdSet) {
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
            WUserInfoPO saveUserInfoPO = wUserInfoProvider.findByOpenId(userInfoPO.getOpenid());
            if (saveUserInfoPO != null) {
                userInfoPO.setId(saveUserInfoPO.getId());
                wUserInfoDao.updateByOpenId(userInfoPO);
            } else {
                wUserInfoDao.insert(userInfoPO);
            }
            wUserInfoProvider.updateCache(userInfoPO, false);
            openIdSet.add(userInfoPO.getOpenid());
        }
        syncUserInfosInternal(offset + userInfoDtos.size(), openIdSet);
    }

    private void removeUnFollowUserInfo(Set<String> openIdSet) {
        List<WUserInfoPO> userInfos = wUserInfoProvider.getAllUserInfos();
        for (WUserInfoPO userInfo : userInfos) {
            if (!openIdSet.contains(userInfo.getOpenid())) {
                wUserInfoDao.deleteByOpenId(userInfo);
                wUserInfoProvider.updateCache(userInfo, true);
            }
        }
    }

    public void syncRecentMsgs() {
        List<WUserInfoPO> userInfoPOS = wUserInfoProvider.getAllUserInfos();
        log.debug("syncRecentMsgs: unRecentUserInfos.size = {}", userInfoPOS.size());
        for (WUserInfoPO userInfoPO : userInfoPOS) {
            String fakeId = userInfoPO.getOpenid();
            if (!wMsgItemProvider.isRecentMsg(fakeId)) {
                WMsgItemPO msgItemPO = wMsgItemProvider.findMsgItemByFakeId(fakeId);
                String lastMsgId = String.valueOf(msgItemPO == null ? "" : msgItemPO.getMsgId());
                String url = "https://mp.weixin.qq.com/cgi-bin/singlesendpage?action=sync&tofakeid=" + fakeId + "&lastmsgfromfakeid=&lastmsgid=" + lastMsgId + "&createtime&token={{token}}&lang=zh_CN&f=json&ajax=1";
                WConversationRespDto respDto = okHttp.get(url, null, null, WConversationRespDto.class);
                if (OtherUtil.checkCommonRespDto(respDto, "WechatService.syncRecentMsgs()")) {
                    WConversationDto conversationDto = respDto.getConversationDto();
                    if (conversationDto.getCanReplay() == WConversationDto.CAN_REPLAY) {
                        List<MsgItemDto> msgItemDtos = conversationDto.getMsgItemDtos();
                        findLatestMsgAndSaveToDB(msgItemDtos, fakeId);
                    } else {

                    }
                }
            }
        }
    }

    private void findLatestMsgAndSaveToDB(List<MsgItemDto> msgItemDtos, String fakeId) {
        long latestTime = 0L;
        MsgItemDto latestMsgItemDto = null;
        for (MsgItemDto msgItemDto : msgItemDtos) {
            if (fakeId.equals(msgItemDto.getFakeId()) && latestTime < msgItemDto.getDateTime()) {
                latestTime = msgItemDto.getDateTime();
                latestMsgItemDto = msgItemDto;
            }
        }
        if (latestMsgItemDto != null) {
            log.debug("findLatestMsgAndSaveToDB: title = {}", latestMsgItemDto.getTitle());
            WMsgItemPO msgItemPO = BeanConverterUtil.dtoToPo(latestMsgItemDto);
            WMsgItemPO saveMsgItemPO = wMsgItemProvider.findMsgItemByFakeId(msgItemPO.getFakeId());
            msgItemPO.setCanReplay(true);
            if (saveMsgItemPO != null) {
                msgItemPO.setId(saveMsgItemPO.getId());
                wMsgItemDao.updateByFakeId(msgItemPO);
            } else {
                wMsgItemDao.insert(msgItemPO);
            }
            wMsgItemProvider.updateCache(msgItemPO);
        }
    }

    // TODO: 6/7/23 利用多线程抓取，asyncTaskTools控制异步任务
    public void syncNeedFetchArticle() {
        List<String> fakeIds = wAccountProvider.getNeedFetchAccountFakeIds();
        String url = "https://mp.weixin.qq.com/cgi-bin/appmsg?action=list_ex&begin=0&count=5&fakeid={{fakeId}}&type=9&query=&token={{token}}&lang=zh_CN&f=json&ajax=1";
        for (int i = 0; i < fakeIds.size(); i++) {
            String fakeId = fakeIds.get(i);
            log.debug("syncNeedFetchArticle: start fetch fakeId = {}, index = {}", fakeId, i);
            String newUrl = OtherUtil.getNewUrlByParams(url, MapUtil.of(AppConstant.FAKE_ID, fakeId));
            syncArticleInternal(newUrl, fakeId, new ArrayList<>());
            log.debug("syncNeedFetchArticle: end fetch fakeId = {}, index = {}", fakeId, i);
        }
        articleFileManager.saveTotalTodayArticles(fakeIds);
        saveArticlesByState();
    }

    public void syncMyArticle() {
        String url = "https://mp.weixin.qq.com/cgi-bin/appmsgpublish?sub=list&search_field=null&begin=0&count=5&query=&type=101_1&free_publish_type=1&sub_action=list_ex&token={{token}}&lang=zh_CN&f=json&ajax=1";
        syncArticleInternal(url, AppConstant.MY_FAKE_ID, new ArrayList<>());
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
            if (OtherUtil.isMyFakeId(fakeId)) {
                syncMyArticle();
            } else {
                String newUrl = OtherUtil.getNewUrlByParams(url, MapUtil.of(AppConstant.FAKE_ID, fakeId));
                syncArticleInternal(newUrl, fakeId, new ArrayList<>());
            }
        }
    }

    private void syncArticleInternal(String url, String fakeId, List<WArticleItemPO> saveArticleItemPOS) {
        BaseRespDto respDto;
        if (OtherUtil.isMyFakeId(fakeId)) {
            respDto = requestMyArticle(url);
        } else {
            respDto = okHttp.get(url, null, null, WArticleItemsRespDto.class);
        }
        if (OtherUtil.checkCommonRespDto(respDto, "WechatService.syncArticleInternal()")) {
            List<WArticleItemDto> articleItemDtos = new ArrayList<>();
            if (respDto instanceof WMyPublishRespDto) {
                articleItemDtos = ((WMyPublishRespDto) respDto).getMyArticleItemDtos();
            } else if (respDto != null) {
                articleItemDtos = ((WArticleItemsRespDto) respDto).getArticleItemDtos();
            }
            int begin = Integer.parseInt(OtherUtil.getQuery(url, AppConstant.BEGIN));
            if (CollectionUtil.isEmpty(articleItemDtos)) {
                log.info("syncArticleInternal: articleItemDtos is empty, begin = {}", begin);
                onFetchArticlesEnd(fakeId, saveArticleItemPOS);
                return;
            }
            log.info("syncArticleInternal: load article list successfully, begin = {}", begin);
            articleItemDtos.sort((first, second) -> (int) (second.getUpdateTime() - first.getUpdateTime()));
            long lastLatestTime = wArticleProvider.getLastLatestTime(fakeId);
            long curNewestTime = CollectionUtil.getFirst(articleItemDtos).getUpdateTime();
            if (curNewestTime > lastLatestTime) {
                saveArticleItemDtoToDB(articleItemDtos, fakeId, saveArticleItemPOS);
                int articleSize = saveArticleItemPOS.size();
                if (articleSize >= AppConstant.ARTICLE_LIMIT) {
                    log.info("syncArticleInternal: load article list more than {}, begin = {}", AppConstant.ARTICLE_LIMIT, begin);
                    onFetchArticlesEnd(fakeId, saveArticleItemPOS);
                } else {
                    String newUrl = OtherUtil.getNewUrlByParams(url, MapUtil.of(AppConstant.BEGIN, begin + 5));
                    syncArticleInternal(newUrl, fakeId, saveArticleItemPOS);
                }
            } else {
                log.info("syncArticleInternal: load the last latest article, begin = {}", begin);
                onFetchArticlesEnd(fakeId, saveArticleItemPOS);
            }
        }
    }

    private WMyPublishRespDto requestMyArticle(String url) {
        String respDtoStr = okHttp.get(url, null, null, String.class);
        WMyPublishRespDto respDto = null;
        try {
            respDtoStr = respDtoStr.replace("\\", "").replace("\"{", "{")
                    .replace("\"}", "}").replace("}\"", "}");
            respDto = objectMapper.readValue(respDtoStr, WMyPublishRespDto.class);
            respDto.getMyArticleItemDtos();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respDto;
    }

    private void saveArticleItemDtoToDB(List<WArticleItemDto> articleItemDtos, String fakeId, List<WArticleItemPO> articleItemPOS) {
        WAccountPO accountPO = wAccountProvider.findByFakeId(fakeId);
        if (accountPO == null) {
            log.info("saveArticleItemDtoToDB: accountPO is null, can not save to DB.");
            return;
        }
        for (WArticleItemDto itemDto : articleItemDtos) {
            WArticleItemPO itemPo = BeanConverterUtil.dtoToPo(itemDto);
            itemPo.setOfficialAccountId(accountPO.getId());
            itemPo.setOfficialAccountFakeId(accountPO.getFakeId());
            itemPo.setOfficialAccountTitle(accountPO.getNickname());
            WArticleItemPO saveItemPo = wArticleProvider.findByAid(itemDto.getAid());
            if (saveItemPo != null) {
                saveItemPo.setHandleState(itemPo.getHandleState());
                wArticleDao.updateByAid(itemPo);
            } else {
                wArticleDao.insert(itemPo);
                articleItemPOS.add(itemPo);
            }
            wArticleProvider.updateCache(itemPo, false);
        }
    }

    private void onFetchArticlesEnd(String fakeId, List<WArticleItemPO> articleItemPOS) {
        WAccountPO accountPO = wAccountProvider.findByFakeId(fakeId);
        articleFileManager.saveFetchArticles(accountPO, articleItemPOS);
        articleFileManager.saveTodayArticles(accountPO);
        WArticleItemPO articleItemPO = CollectionUtil.getFirst(articleItemPOS);
        if (articleItemPO != null) {
            wArticleProvider.setLastLatestTime(fakeId, articleItemPO.getUpdateTime().getTime() / 1000);
        }
    }

    public void sendMsgToRecentUser(String aid) {
        log.debug("sendMsgToRecentUser: aid = {}", aid);
        List<WMsgItemPO> recentMsgItems = wMsgItemProvider.getRecentMsgItems();
        Map<String, String> recentOpenIdMap = new HashMap<>();
        for (WMsgItemPO msgItem : recentMsgItems) {
            if (wUserInfoProvider.findByOpenId(msgItem.getFakeId()) != null) {
                recentOpenIdMap.put(msgItem.getFakeId(), msgItem.getNickname());
            }
        }
        for (Map.Entry<String, String> entry : recentOpenIdMap.entrySet()) {
            String openId = entry.getKey();
            String name = entry.getValue();
            sendMsgToRecentUserInternal(aid, openId, name);
        }
    }

    private void sendMsgToRecentUserInternal(String aid, String openId, String name) {
        String url = "https://mp.weixin.qq.com/cgi-bin/singlesend?t=ajax-response&f=json";
        String referer = "https://mp.weixin.qq.com/cgi-bin/message?t=message/list&count=20&day=7&token=" + wechatConfig.getToken() + "&lang=zh_CN";
        Map<String, String> headerMap = MapUtil.of(AppConstant.REFERER, referer);
        Map<String, String> paramMap = MapUtil.builder("tofakeid", openId)
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
        if (OtherUtil.checkCommonRespDto(respDto, "WechatService.sendMsgToRecentUserInternal()")) {
            log.info("sendMsgToRecentUserInternal: send message to {} success", name);
        } else {
            log.info("sendMsgToRecentUserInternal: send message to {} fail", name);
        }
    }

    public void saveArticlesByState() {
        List<WArticleItemPO> articleItemPOS = wArticleProvider.getTodayArticles();
        articleFileManager.saveArticlesByState(articleItemPOS);
    }

    // TODO: 6/5/23 群发发布的article。
    // TODO: 6/5/23 loadTodayWaitToPublishArticles 
    // TODO: 6/5/23 草稿？
    // send to me可以当做一个管理者通知渠道
}
