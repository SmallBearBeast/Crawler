package com.bear.crawler.webmagic.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.bear.crawler.webmagic.dao.WArticleDao;
import com.bear.crawler.webmagic.mybatis.generator.po.WPublicAccountPO;
import com.bear.crawler.webmagic.pojo.WechatConfig;
import com.bear.crawler.webmagic.processor.WebMagicTestProcessor;
import com.bear.crawler.webmagic.processor.WPublicAccountProcessor;
import com.bear.crawler.webmagic.processor.WArticleDetailProcessor;
import com.bear.crawler.webmagic.processor.WArticleProcessor;
import com.bear.crawler.webmagic.provider.WPublicAccountProvider;
import com.bear.crawler.webmagic.service.WechatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/webMagic")
@RestController
public class WebMagicController {

    @Autowired
    private WechatConfig wechatConfig;

    @Autowired
    private WebMagicTestProcessor webMagicTestProcessor;

    @Autowired
    private WArticleDetailProcessor wArticleDetailProcessor;

    @Autowired
    private WArticleProcessor wArticleProcessor;

    @Autowired
    private WPublicAccountProcessor wPublicAccountProcessor;

    @Autowired
    private WPublicAccountProvider wPublicAccountProvider;

    @Autowired
    private WArticleDao wArticleDao;

    @Autowired
    private WechatService wechatService;

    @GetMapping("/testWebMagic")
    public String testWebMagic() {
        wArticleDao.selectLatest("FakeId");
//        Spider.create(webMagicTestProcessor)
//                .addUrl("https://mp.weixin.qq.com/s/BeYIAdsEZ6TVzFaOO3C-Pw")
//                .thread(5)
//                .run();
        // 会等processor处理完之后才回调testWebMagic success
        return "testWebMagic success";
    }

    @GetMapping("/loadWArticleDetail")
    public String loadWArticleDetail(@RequestParam("query") String query) {
        Spider.create(wArticleDetailProcessor)
                .addUrl("https://mp.weixin.qq.com/s/" + query)
                .thread(5)
                .run();
        return "loadWArticleDetail success";
    }

    // TODO: 5/21/23 spider添加delay，可以继承QueueScheduler在poll方法加上随机sleep，先观察看看。
    @GetMapping("/watchWArticles")
    public String watchWArticles() {
        log.debug("watchWArticles enter");
        Map<String, WPublicAccountPO> accountPOMap = wPublicAccountProvider.getNeedFetchAccountMap();
        Spider spider = Spider.create(wArticleProcessor);
        for (WPublicAccountPO accountPO : accountPOMap.values()) {
            String url = "https://mp.weixin.qq.com/cgi-bin/appmsg?action=list_ex&begin=0&count=5&fakeid=" + accountPO.getFakeId() + "&type=9&query=&token=" + wechatConfig.getToken() + "&lang=zh_CN&f=json&ajax=1";
            Request request = new Request(url);
            request.addHeader("cookie", wechatConfig.getCookie());
            request.addHeader("user-agent", wechatConfig.getUserAgent());
            spider.addRequest(request);
        }
//        WPublicAccountPO accountPO = wPublicAccountProvider.findByFakeId("MjM5NjM0MDEwNg==");
//        String url = "https://mp.weixin.qq.com/cgi-bin/appmsg?action=list_ex&begin=0&count=5&fakeid=" + accountPO.getFakeId() + "&type=9&query=&token=" + wechatConfig.getToken() + "&lang=zh_CN&f=json&ajax=1";
//        Request request = new Request(url);
//        request.addHeader("cookie", wechatConfig.getCookie());
//        request.addHeader("user-agent", wechatConfig.getUserAgent());
//        spider.addRequest(request);
        spider.thread(5).start();
        return "watchWArticles success";
    }

    @GetMapping("/loadWPublicAccount")
    public String loadWPublicAccount(@RequestParam("query") String query) {
        log.debug("loadWPublicAccount : query = {}", query);
        String encodeQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = "https://mp.weixin.qq.com/cgi-bin/searchbiz?action=search_biz&begin=0&count=5&query=" + encodeQuery + "&token=" + wechatConfig.getToken() + "&lang=zh_CN&f=json&ajax=1";
        Request request = new Request(url);
        request.addHeader("cookie", wechatConfig.getCookie());
        request.addHeader("user-agent", wechatConfig.getUserAgent());
        Spider.create(wPublicAccountProcessor)
                .addRequest(request)
                .thread(5)
                .run();
        return "loadWPublicAccount success";
    }

    @PostMapping("/updatePublicAccountNeedToFetchByName")
    public String updatePublicAccountNeedToFetchByName(@RequestParam("accountNames") List<String> accountNames, @RequestParam("needToFetch") boolean needToFetch) {
        if (!CollectionUtil.isEmpty(accountNames)) {
            wechatService.updateAccountNeedToFetchByName(accountNames, needToFetch);
        }
        return "updatePublicAccountNeedToFetchByName success";
    }

    @PostMapping("/updatePublicAccountNeedToFetchByFakeId")
    public String updatePublicAccountNeedToFetchByFakeId(@RequestParam("fakeIds") List<String> fakeIds, @RequestParam("needToFetch") boolean needToFetch) {
        if (!CollectionUtil.isEmpty(fakeIds)) {
            wechatService.updateAccountNeedToFetchByFakeId(fakeIds, needToFetch);
        }
        return "updatePublicAccountNeedToFetchByFakeId success";
    }

    @PostMapping("/findAndInsertWAccount")
    public String findAndInsertWAccount(
            @RequestParam("accountName") String accountName,
            @RequestParam(name = "needToFetch", required = false, defaultValue = "false") boolean needToFetch) {
        String encodeAccountName = URLEncoder.encode(accountName, StandardCharsets.UTF_8);
        String url = "https://mp.weixin.qq.com/cgi-bin/searchbiz?action=search_biz&begin=0&count=5&query=" + encodeAccountName + "&token=" + wechatConfig.getToken() + "&lang=zh_CN&f=json&ajax=1";
        // get请求没有添加header的方法
        // 使用RestTemplate拿不到list数据，为空，因此使用okhttp。
        wechatService.findAndInsertWAccount(url, accountName, needToFetch);
        return null;
    }

    @PostMapping("/updateWechatConfig")
    public String updateWechatConfig(@RequestParam("token") String token, @RequestParam("cookie") String cookie) {
        wechatService.updateWechatProperties(token, cookie);
        return "updateWechatConfig success";
    }

    public String updateArticleNeedToPublish() {
        return "";
    }

    public void loadTotalArticles() {

    }

    public void sendMessageToAllUser() {

    }
}
