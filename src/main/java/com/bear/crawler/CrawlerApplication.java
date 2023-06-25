package com.bear.crawler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.bear.crawler.webmagic.service.WArticleService;
import com.bear.crawler.webmagic.service.WMsgService;
import com.bear.crawler.webmagic.service.WOtherService;
import com.bear.crawler.webmagic.service.WUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;

@Slf4j
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class CrawlerApplication implements ApplicationRunner {
    @Autowired
    private WMsgService wMsgService;

    @Autowired
    private WUserService wUserService;

    @Autowired
    private WArticleService wArticleService;

    @Autowired
    private WOtherService wOtherService;

    private static boolean inInit = false;

    public static void main(String[] args) {
        SpringApplication.run(CrawlerApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("run: sourceArgs = {}", Arrays.toString(args.getSourceArgs()));
        inInit = true;
        String token = CollectionUtil.getFirst(args.getOptionValues("token"));
        String cookie = CollectionUtil.getFirst(args.getOptionValues("cookie"));
        if (StrUtil.isNotEmpty(token) && StrUtil.isNotEmpty(cookie)) {
            log.info("run: refresh the token in initial");
            wOtherService.updateWechatProperties(token, cookie);
        }
        String sync = CollectionUtil.getFirst(args.getOptionValues("sync"));
        if ("1".equals(sync)) {
            log.info("run: sync the data in initial");
            wUserService.syncUserInfos();
            wMsgService.syncRecentMsgs();
            wArticleService.syncNeedFetchArticle();
        }
        inInit = false;
    }

    public static boolean isInit() {
        return inInit;
    }
}
