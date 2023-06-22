package com.bear.crawler;

import cn.hutool.core.collection.CollectionUtil;
import com.bear.crawler.webmagic.service.WArticleService;
import com.bear.crawler.webmagic.service.WMsgService;
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

    private static boolean inInit = false;

    public static void main(String[] args) {
        SpringApplication.run(CrawlerApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("run: sourceArgs = {}", Arrays.toString(args.getSourceArgs()));
        String sync = CollectionUtil.getFirst(args.getOptionValues("sync"));
        if ("1".equals(sync)) {
            inInit = true;
            wUserService.syncUserInfos();
            wMsgService.syncRecentMsgs();
            wArticleService.syncNeedFetchArticle();
            inInit = false;
        }
    }

    public static boolean isInit() {
        return inInit;
    }
}
