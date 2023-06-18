package com.bear.crawler.webmagic.controller;

import com.bear.crawler.webmagic.service.WAccountService;
import com.bear.crawler.webmagic.service.WArticleService;
import com.bear.crawler.webmagic.service.WMsgService;
import com.bear.crawler.webmagic.service.WUserService;
import com.bear.crawler.webmagic.service.WOtherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/webMagic")
@RestController
public class WebMagicController {

    @Autowired
    private WOtherService wOtherService;

    @Autowired
    private WAccountService wAccountService;

    @Autowired
    private WArticleService wArticleService;

    @Autowired
    private WMsgService wMsgService;

    @Autowired
    private WUserService wUserService;

    @GetMapping("/fetchWArticleDetail")
    public String fetchWArticleDetail(@RequestParam("query") String query) {
        wArticleService.fetchWArticleDetail(query);
        return "fetchWArticleDetail successfully";
    }

    // TODO: 5/21/23 spider添加delay，可以继承QueueScheduler在poll方法加上随机sleep，先观察看看。
    @PostMapping("/listenWArticlesUpdate")
    public String listenWArticlesUpdate() {
        wArticleService.listenWArticlesUpdate();
        return "listenWArticlesUpdate successfully";
    }

    @PostMapping("/listenWArticlesUpdateForAccounts")
    public String listenWArticlesUpdateForAccounts(@RequestParam("accountNames") List<String> accountNames) {
        wArticleService.listenWArticlesUpdate(accountNames);
        return "listenWArticlesUpdateForAccounts successfully";
    }

    @PostMapping("/searchAndSyncWAccount")
    public String searchAndSyncWAccount(@RequestParam("searchContent") String searchContent) {
        wAccountService.searchAndSyncWAccount(searchContent);
        return "searchAndSyncWAccount successfully";
    }

    @PostMapping("/findAndSyncWAccount")
    public String findAndSyncWAccount(
            @RequestParam("accountName") String accountName,
            @RequestParam(name = "needToFetch", required = false, defaultValue = "false") boolean needToFetch) {
        wAccountService.findAndSyncWAccount(accountName, needToFetch);
        return "findAndSyncWAccount successfully";
    }

    @PostMapping("/updateWAccountNeedToFetchByName")
    public String updateWAccountNeedToFetchByName(@RequestParam("accountNames") List<String> accountNames, @RequestParam("needToFetch") boolean needToFetch) {
        wAccountService.updateWAccountNeedToFetchByName(accountNames, needToFetch);
        return "updateWAccountNeedToFetchByName successfully";
    }

    @PostMapping("/updateWAccountNeedToFetchByFakeId")
    public String updateWAccountNeedToFetchByFakeId(@RequestParam("fakeIds") List<String> fakeIds, @RequestParam("needToFetch") boolean needToFetch) {
        wAccountService.updateWAccountNeedToFetchByFakeId(fakeIds, needToFetch);
        return "updateWAccountNeedToFetchByFakeId successfully";
    }

    @PostMapping("/updateWechatConfig")
    public String updateWechatConfig(@RequestParam("token") String token, @RequestParam("cookie") String cookie) {
        wOtherService.updateWechatProperties(token, cookie);
        return "updateWechatConfig successfully";
    }

    @PostMapping("/updateHandleState")
    public String updateHandleState(@RequestParam("aids") List<String> aids, @RequestParam("state") int state) {
        wArticleService.updateHandleState(aids, state);
        return "updateHandleState successfully";
    }

    @PostMapping("/syncUserInfos")
    public String syncUserInfos() {
        wUserService.syncUserInfos();
        return "syncUserInfos successfully";
    }

    @PostMapping("/syncRecentMsgs")
    public String syncRecentMsgs() {
        wMsgService.syncRecentMsgs();
        return "syncRecentMsgs successfully";
    }

    @PostMapping("syncNeedFetchArticle")
    public String syncNeedFetchArticle() {
        wArticleService.syncNeedFetchArticle();
        return "syncNeedFetchArticle successfully";
    }

    @PostMapping("/syncMyArticle")
    public String syncMyArticle() {
        wArticleService.syncMyArticle();
        return "syncMyArticle successfully";
    }

    @PostMapping("/syncArticle")
    public String syncArticle(
            @RequestParam(name = "fakeIds", required = false, defaultValue = "") List<String> fakeIds,
            @RequestParam(name = "accountNames", required = false, defaultValue = "") List<String> accountNames) {
        wArticleService.syncArticle(fakeIds, accountNames);
        return "syncArticle successfully";
    }

    @PostMapping("/sendMsgToMe")
    public String sendMsgToMe(@RequestParam("aid") String aid) {
        wMsgService.sendMsgToMe(aid);
        return "sendMsgToMe successfully";
    }

    @PostMapping("/sendMsgToRecentUser")
    public String sendMsgToRecentUser(@RequestParam("aid") String aid) {
        wMsgService.sendMsgToRecentUser(aid);
        return "sendMsgToRecentUser successfully";
    }


    @PostMapping("/saveArticlesByState")
    public String saveArticlesByState() {
        wArticleService.saveArticlesByState();
        return "sendMsgToRecentUser successfully";
    }
}
