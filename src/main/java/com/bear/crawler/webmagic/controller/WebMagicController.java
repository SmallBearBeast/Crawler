package com.bear.crawler.webmagic.controller;

import com.bear.crawler.webmagic.mybatis.generator.po.WPublicAccountPO;
import com.bear.crawler.webmagic.pojo.WechatConfig;
import com.bear.crawler.webmagic.processor.WebMagicTestProcessor;
import com.bear.crawler.webmagic.processor.WPublicAccountProcessor;
import com.bear.crawler.webmagic.processor.WArticleDetailProcessor;
import com.bear.crawler.webmagic.processor.WArticleProcessor;
import com.bear.crawler.webmagic.provider.WPublicAccountProvider;
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
import java.util.List;

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

    @GetMapping("/testWebMagic")
    public String testWebMagic() {
        Spider.create(webMagicTestProcessor)
                .addUrl("https://mp.weixin.qq.com/s/BeYIAdsEZ6TVzFaOO3C-Pw")
                .thread(5)
                .run();
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

    @GetMapping("/watchWArticles")
    public String watchWArticles() {
        log.debug("watchWArticles enter");
        List<WPublicAccountPO> accountPOS = wPublicAccountProvider.getWNeedFetchPublicAccountPOS();
        Spider spider = Spider.create(wArticleProcessor);
        for (WPublicAccountPO accountPO : accountPOS) {
            String url = "https://mp.weixin.qq.com/cgi-bin/appmsg?action=list_ex&begin=0&count=5&fakeid=" + accountPO.getFakeId() + "&type=9&query=&token=" + wechatConfig.getToken() + "&lang=zh_CN&f=json&ajax=1";
            Request request = new Request(url);
            request.addHeader("cookie", wechatConfig.getCookie());
            request.addHeader("user-agent", wechatConfig.getUserAgent());
            spider.addRequest(request);
        }
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
}
