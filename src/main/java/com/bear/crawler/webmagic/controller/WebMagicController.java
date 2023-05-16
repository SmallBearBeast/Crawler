package com.bear.crawler.webmagic.controller;

import com.bear.crawler.webmagic.pojo.WechatConfig;
import com.bear.crawler.webmagic.processor.WebMagicTestProcessor;
import com.bear.crawler.webmagic.processor.WechatAccountProcessor;
import com.bear.crawler.webmagic.processor.WechatArticleDetailProcessor;
import com.bear.crawler.webmagic.processor.WechatArticleProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequestMapping("/webMagic")
@RestController
public class WebMagicController {

    @Autowired
    private WechatConfig wechatConfig;

    @Autowired
    private WebMagicTestProcessor webMagicTestProcessor;

    @Autowired
    private WechatArticleDetailProcessor wechatArticleDetailProcessor;

    @Autowired
    private WechatArticleProcessor wechatArticleProcessor;

    @Autowired
    private WechatAccountProcessor wechatAccountProcessor;

    @GetMapping("/testWebMagic")
    public String testWebMagic() {
        Spider.create(webMagicTestProcessor)
                .addUrl("https://mp.weixin.qq.com/s/BeYIAdsEZ6TVzFaOO3C-Pw")
                .thread(5)
                .run();
        // 会等processor处理完之后才回调testWebMagic success
        return "testWebMagic success";
    }

    @GetMapping("/loadWechatArticleDetail")
    public String loadWechatArticleDetail(@RequestParam("query") String query) {
        Spider.create(wechatArticleDetailProcessor)
                .addUrl("https://mp.weixin.qq.com/s/" + query)
                .thread(5)
                .run();
        return "loadWechatArticleDetail success";
    }

    @GetMapping("/watchWechatOfficialArticleList")
    public String watchWechatOfficialArticleList() {
        log.debug("watchWechatOfficialArticleList enter");
        String url = "https://mp.weixin.qq.com/cgi-bin/appmsg?action=list_ex&begin=0&count=5&fakeid=" + wechatConfig.getFakeid() + "&type=9&query=&token=" + wechatConfig.getToken() + "&lang=zh_CN&f=json&ajax=1";
        Request request = new Request(url);
        request.addHeader("cookie", wechatConfig.getCookie());
        request.addHeader("user-agent", wechatConfig.getUserAgent());
        Spider.create(wechatArticleProcessor)
                .addRequest(request)
                .thread(5)
                .run();
        return "watchWechatOfficialArticleList success";
    }

    @GetMapping("/loadWechatOfficialAccount")
    public String loadWechatOfficialAccount(@RequestParam("query") String query) {
        log.debug("loadWechatOfficialAccount : query = {}", query);
        String encodeQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = "https://mp.weixin.qq.com/cgi-bin/searchbiz?action=search_biz&begin=0&count=5&query=" + encodeQuery + "&token=" + wechatConfig.getToken() + "&lang=zh_CN&f=json&ajax=1";
        Request request = new Request(url);
        request.addHeader("cookie", wechatConfig.getCookie());
        request.addHeader("user-agent", wechatConfig.getUserAgent());
        Spider.create(wechatAccountProcessor)
                .addRequest(request)
                .thread(5)
                .run();
        return "loadWechatOfficialAccount success";
    }
}
