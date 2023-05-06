package com.bear.crawler.webmagic.controller;

import com.bear.crawler.webmagic.processor.WebMagicTestProcessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

@RequestMapping("/webMagic")
@RestController
public class WebMagicController {

    @GetMapping("/testWebMagic")
    public void testWebMagic() {
        Spider.create(new WebMagicTestProcessor())
                .addUrl("https://mp.weixin.qq.com/s/BeYIAdsEZ6TVzFaOO3C-Pw")
                .thread(5)
                .run();
    }
}
