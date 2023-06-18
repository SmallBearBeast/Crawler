package com.bear.crawler.webmagic;

import cn.hutool.core.date.DateUtil;
import com.bear.crawler.webmagic.dao.WArticleDao;
import com.bear.crawler.webmagic.manager.ArticleFileManager;
import com.bear.crawler.webmagic.mybatis.generator.po.WArticleItemPO;
import com.bear.crawler.webmagic.provider.WArticleProvider;
import com.bear.crawler.webmagic.service.WArticleService;
import com.bear.crawler.webmagic.service.WMsgService;
import com.bear.crawler.webmagic.service.WUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

// 由于基于注解@Scheduled默认为单线程，开启多个任务时，任务的执行时机会受上一个任务执行时间的影响。所以这里使用 @Async 注解很关键。
@Slf4j
@Component
@EnableAsync
public class ScheduleComponent {

    // 0s到10s每隔两秒执行一次
    private static final String CRON_TEST = "0,2,4,6,8,10 * * * * ?";

    // 每天00:30, 06:30, 12:30, 18:30执行一次
    private static final String CRON_SYNC_USERS_AND_MSGS = "0 30 0,6,12,18 * * ?";

    // 每天8:00到22:00每隔两个小时执行一次
    private static final String CRON_SYNC_ARTICLES = "0 0 8,10,11,12,14,16,18,20,22 * * ?";

    // 每天22:45自动清除过期文章
    private static final String CRON_REMOVE_ARTICLES = "0 45 22 * * ?";

    @Autowired
    private WArticleService wArticleService;

    @Autowired
    private WMsgService wMsgService;

    @Autowired
    private WUserService wUserService;

    @Autowired
    private ArticleFileManager articleFileManager;

    @Autowired
    private WArticleProvider wArticleProvider;

    @Autowired
    private WArticleDao wArticleDao;

    /**
     * 定时任务 静态定时任务
     * 第一位，表示秒，取值0-59
     * 第二位，表示分，取值0-59
     * 第三位，表示小时，取值0-23
     * 第四位，日期天/日，取值1-31
     * 第五位，日期月份，取值1-12
     * 第六位，星期，取值1-7，1表示星期天，2表示星期一
     * 第七位，年份，可以留空，取值1970-2099
     */
    @Async
    @Scheduled(cron = ScheduleComponent.CRON_TEST)
    void printNowDate() {
        log.debug("syncUserInfoAndMsgs: current time is {}", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        log.debug("syncData: 0s到10s每隔两秒执行一次");
    }
    @Async
    @Scheduled(cron = ScheduleComponent.CRON_SYNC_USERS_AND_MSGS)
    void syncUserInfoAndMsgs() {
        log.debug("syncUserInfoAndMsgs: current time is {}", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        wUserService.syncUserInfos();
        wMsgService.syncRecentMsgs();
    }

    @Async
    @Scheduled(cron = ScheduleComponent.CRON_SYNC_ARTICLES)
    void syncNeedFetchArticles() {
        log.debug("syncArticles: current time is {}", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        wArticleService.syncNeedFetchArticle();
    }

    @Async
    @Scheduled(cron = ScheduleComponent.CRON_REMOVE_ARTICLES)
    public void removeArticlesBefore7Week() {
        log.debug("removeArticlesBefore7Week: current time is {}", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        articleFileManager.deleteArticlesBefore7Week();
        List<WArticleItemPO> articleItemPOS = wArticleDao.selectBefore7Week();
        wArticleDao.deleteBefore7Week();
        for (WArticleItemPO articleItemPO : articleItemPOS) {
            wArticleProvider.updateCache(articleItemPO, true);
        }
    }
}
