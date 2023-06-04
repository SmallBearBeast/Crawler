package com.bear.crawler.webmagic.controller;

import com.bear.crawler.webmagic.service.WechatService;
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
    private WechatService wechatService;

    @GetMapping("/fetchWArticleDetail")
    public String fetchWArticleDetail(@RequestParam("query") String query) {
        wechatService.fetchWArticleDetail(query);
        return "fetchWArticleDetail success";
    }

    // TODO: 5/21/23 spider添加delay，可以继承QueueScheduler在poll方法加上随机sleep，先观察看看。
    @PostMapping("/listenWArticlesUpdate")
    public String listenWArticlesUpdate() {
        wechatService.listenWArticlesUpdate();
        return "listenWArticlesUpdate success";
    }

    @PostMapping("/listenWArticlesUpdateForAccounts")
    public String listenWArticlesUpdateForAccounts(@RequestParam("accountNames") List<String> accountNames) {
        wechatService.listenWArticlesUpdate(accountNames);
        return "listenWArticlesUpdateForAccounts success";
    }

    @PostMapping("/searchAndInsertWAccount")
    public String searchAndInsertWAccount(@RequestParam("searchContent") String searchContent) {
        wechatService.searchAndInsertWAccount(searchContent);
        return "searchAndInsertWAccount success";
    }

    @PostMapping("/updateWAccountNeedToFetchByName")
    public String updateWAccountNeedToFetchByName(@RequestParam("accountNames") List<String> accountNames, @RequestParam("needToFetch") boolean needToFetch) {
        wechatService.updateWAccountNeedToFetchByName(accountNames, needToFetch);
        return "updateWAccountNeedToFetchByName success";
    }

    @PostMapping("/updateWAccountNeedToFetchByFakeId")
    public String updateWAccountNeedToFetchByFakeId(@RequestParam("fakeIds") List<String> fakeIds, @RequestParam("needToFetch") boolean needToFetch) {
        wechatService.updateWAccountNeedToFetchByFakeId(fakeIds, needToFetch);
        return "updateWAccountNeedToFetchByFakeId success";
    }

    @PostMapping("/findAndInsertWAccount")
    public String findAndInsertWAccount(
            @RequestParam("accountName") String accountName,
            @RequestParam(name = "needToFetch", required = false, defaultValue = "false") boolean needToFetch) {
        wechatService.findAndInsertWAccount(accountName, needToFetch);
        return "findAndInsertWAccount success";
    }

    @PostMapping("/updateWechatConfig")
    public String updateWechatConfig(@RequestParam("token") String token, @RequestParam("cookie") String cookie) {
        wechatService.updateWechatProperties(token, cookie);
        return "updateWechatConfig success";
    }

    @GetMapping("/syncRecentConversions")
    public void syncRecentConversions() {
        wechatService.syncRecentConversions();
    }

    @PostMapping("/syncUserInfos")
    public String syncUserInfos() {
        wechatService.syncUserInfos();
        return "syncUserInfos success";
    }

    @PostMapping("/sendMessageToAllUser")
    public void sendMessageToAllUser() {
        wechatService.sendMessageToAllUser();
    }
}
