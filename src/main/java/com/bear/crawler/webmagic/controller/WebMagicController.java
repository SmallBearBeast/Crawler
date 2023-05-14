package com.bear.crawler.webmagic.controller;

import com.bear.crawler.webmagic.pojo.WechatConfig;
import com.bear.crawler.webmagic.processor.WebMagicTestProcessor;
import com.bear.crawler.webmagic.processor.WechatProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;

@Slf4j
@RequestMapping("/webMagic")
@RestController
public class WebMagicController {

    @Autowired
    private WechatConfig wechatConfig;

    @Autowired
    private WebMagicTestProcessor webMagicTestProcessor;

    @Autowired
    private WechatProcessor wechatProcessor;

    @GetMapping("/testWebMagic")
    public void testWebMagic() {
        Spider.create(webMagicTestProcessor)
                .addUrl("https://mp.weixin.qq.com/s/BeYIAdsEZ6TVzFaOO3C-Pw")
                .thread(5)
                .run();
    }

    @GetMapping("/watchWechatOfficialArticleList")
    public void watchWechatOfficialArticleList() {
        log.debug("watchWechatOfficialArticleList: wechatConfig = {}", wechatConfig);
        String url = "https://mp.weixin.qq.com/cgi-bin/appmsg?action=list_ex&begin=0&count=5&fakeid=" + wechatConfig.getFakeid() + "&type=9&query=&token=" + wechatConfig.getToken() + "&lang=zh_CN&f=json&ajax=1";
        Request request = new Request(url);
        request.addHeader("cookie", wechatConfig.getCookie());
        request.addHeader("user-agent", wechatConfig.getUserAgent());
        Spider.create(wechatProcessor)
                .addRequest(request)
                .thread(5)
                .run();
    }
}
