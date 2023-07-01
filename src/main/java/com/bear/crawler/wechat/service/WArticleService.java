package com.bear.crawler.wechat.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import com.bear.crawler.wechat.AppConstant;
import com.bear.crawler.basic.http.OkHttp;
import com.bear.crawler.wechat.dao.WArticleDao;
import com.bear.crawler.wechat.manager.ArticleFileManager;
import com.bear.crawler.wechat.mybatis.generator.po.WAccountPO;
import com.bear.crawler.wechat.mybatis.generator.po.WArticleItemPO;
import com.bear.crawler.wechat.pojo.WechatProperties;
import com.bear.crawler.wechat.pojo.dto.WArticleItemDto;
import com.bear.crawler.wechat.pojo.dto.resp.BaseRespDto;
import com.bear.crawler.wechat.pojo.dto.resp.WArticleItemsRespDto;
import com.bear.crawler.wechat.pojo.dto.resp.WMyPublishRespDto;
import com.bear.crawler.wechat.processor.WArticleDetailProcessor;
import com.bear.crawler.wechat.processor.WArticleProcessor;
import com.bear.crawler.wechat.provider.WAccountProvider;
import com.bear.crawler.wechat.provider.WArticleProvider;
import com.bear.crawler.wechat.util.BeanConverterUtil;
import com.bear.crawler.wechat.util.OtherUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WArticleService {

    @Autowired
    private WechatProperties wechatProperties;

    @Autowired
    private WArticleDetailProcessor wArticleDetailProcessor;

    @Autowired
    private WArticleProcessor wArticleProcessor;

    @Autowired
    private WArticleProvider wArticleProvider;

    @Autowired
    private WAccountProvider wAccountProvider;

    @Autowired
    private WArticleDao wArticleDao;

    @Autowired
    private OkHttp okHttp;

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
        String url = "https://mp.weixin.qq.com/cgi-bin/appmsg?action=list_ex&begin=0&count=5&fakeid=" + accountPO.getFakeId() + "&type=9&query=&token=" + wechatProperties.getToken() + "&lang=zh_CN&f=json&ajax=1";
        Request request = new Request(url);
        request.addHeader("cookie", wechatProperties.getCookie());
        request.addHeader("user-agent", wechatProperties.getUserAgent());
        spider.addRequest(request);
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
            respDtoStr = respDtoStr.replace("\\", "").replace("\"{", "{").replace("}\"", "}");
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
            WArticleItemPO itemPO = BeanConverterUtil.dtoToPo(itemDto);
            itemPO.setOfficialAccountId(accountPO.getId());
            itemPO.setOfficialAccountFakeId(accountPO.getFakeId());
            itemPO.setOfficialAccountTitle(accountPO.getNickname());
            WArticleItemPO saveItemPo = wArticleProvider.findByAid(itemDto.getAid());
            if (saveItemPo != null) {
                itemPO.setHandleState(saveItemPo.getHandleState());
                wArticleDao.updateByAid(itemPO);
                wArticleProvider.updateCache(itemPO, false);
            } else {
                boolean isLatest = true;
                List<WArticleItemPO> repeatTitleItemPOS = wArticleProvider.findByTitle(itemDto.getAid(), accountPO.getFakeId(), itemDto.getTitle());
                log.debug("saveArticleItemDtoToDB: repeatTitleItemPOS size is {}", repeatTitleItemPOS.size());
                for (WArticleItemPO repeatTitleItemPO : repeatTitleItemPOS) {
                    if (itemPO.getUpdateTime().compareTo(repeatTitleItemPO.getUpdateTime()) > 0) {
                        log.debug("saveArticleItemDtoToDB: remove repeat title article {}", repeatTitleItemPO.getTitle());
                        wArticleDao.deleteByAid(repeatTitleItemPO);
                        wArticleProvider.updateCache(repeatTitleItemPO, true);
                    } else {
                        isLatest = false;
                    }
                }
                if (isLatest) {
                    wArticleDao.insert(itemPO);
                    wArticleProvider.updateCache(itemPO, false);
                    articleItemPOS.add(itemPO);
                } else {
                    log.info("saveArticleItemDtoToDB: itemPO {} is not lastest", itemPO.getTitle());
                }
            }
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

    public void saveArticlesByState() {
        List<WArticleItemPO> articleItemPOS = wArticleProvider.getTodayArticles();
        articleFileManager.saveArticlesByState(articleItemPOS);
    }
}
