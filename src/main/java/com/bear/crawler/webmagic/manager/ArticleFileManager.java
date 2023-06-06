package com.bear.crawler.webmagic.manager;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.bear.crawler.webmagic.AppConstant;
import com.bear.crawler.webmagic.mybatis.generator.po.WAccountPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WArticleItemPO;
import com.bear.crawler.webmagic.provider.WAccountProvider;
import com.bear.crawler.webmagic.provider.WArticleProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class ArticleFileManager {

    private static final String DIVIDER = "------------------------------------------------------------------------\n";

    @Value("${wechat.fetchArticleDir}")
    private String fetchArticleDir;

    @Autowired
    private WArticleProvider wArticleProvider;

    @Autowired
    private WAccountProvider wAccountProvider;

    public void saveTotalTodayArticles(List<String> fakeIds) {
        String formatTodayStr = DateUtil.format(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
        String dir = fetchArticleDir + File.separator + formatTodayStr;
        File todayTotalTempFile = FileUtil.file(dir, "汇总记录_Temp.md");
        FileUtil.appendString(DIVIDER, todayTotalTempFile, "utf-8");
        for (String fakeId: fakeIds) {
            WAccountPO accountPO = wAccountProvider.findByFakeId(fakeId);
            List<WArticleItemPO> articleItemPOS = wArticleProvider.getTodayArticles(fakeId);
            if (!CollectionUtil.isEmpty(articleItemPOS)) {
                String content = getTodayArticlesContent(accountPO);
                FileUtil.appendString(content, todayTotalTempFile, "utf-8");
                FileUtil.appendString(DIVIDER, todayTotalTempFile, "utf-8");
            }
        }
        FileUtil.rename(todayTotalTempFile, "汇总记录.md", true);
    }

    // TODO: 5/21/23 格式markdown
    public void saveFetchArticles(WAccountPO accountPO, List<WArticleItemPO> articleItemPOS) {
        String content = getFetchArticlesContent(accountPO, articleItemPOS);
        String dir = getSaveArticlesDir(accountPO);
        File fetchRecordFile = FileUtil.file(dir, "抓取记录.md");
        FileUtil.appendString(content, fetchRecordFile, "utf-8");
    }

    public void saveTodayArticles(WAccountPO accountPO) {
        String content = getTodayArticlesContent(accountPO);
        String dir = getSaveArticlesDir(accountPO);
        File todayRecordFile = FileUtil.file(dir, "汇总记录.md");
        FileUtil.writeString(content, todayRecordFile, "utf-8");
    }

    private String getFetchArticlesContent(WAccountPO accountPO, List<WArticleItemPO> articleItemPOS) {
        StringBuilder builder = new StringBuilder();
        builder.append(DIVIDER).append("保存时间：").append(DateUtil.format(new Date(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))).append("\n");
        String accountName = accountPO == null ? "未知公众号" : accountPO.getNickname();
        String fakeId = accountPO == null ? "未知fakeId" : accountPO.getFakeId();
        builder.append("公众号：").append(accountName).append(" 公众号fakeId：").append(fakeId).append("\n");
        if (CollectionUtil.isEmpty(articleItemPOS)) {
            builder.append("没有抓到最新的文章").append("\n");
        } else {
            long lastLatestTime = wArticleProvider.getLastLatestTime(fakeId) * 1000;
            String formatLastLatestDateStr = DateUtil.format(new Date(lastLatestTime), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            builder.append("距离上一次时间").append(formatLastLatestDateStr).append("抓到了").append(articleItemPOS.size()).append("篇文章").append("\n\n");
            collectArticleInfo(builder, articleItemPOS);
        }
        log.debug("getFetchArticlesContent: content = {}", builder.toString());
        return builder.toString();
    }

    private String getSaveArticlesDir(WAccountPO accountPO) {
        String fakeId = accountPO == null ? "未知fakeId" : accountPO.getFakeId();
        String accountName = accountPO == null ? "未知公众号" : accountPO.getNickname();
        String formatCurDateStr = DateUtil.format(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
        if (AppConstant.MY_FAKE_ID.equals(fakeId)) {
            accountName = "我的";
        }
        return fetchArticleDir + File.separator + formatCurDateStr + File.separator + accountName;
    }

    private String getTodayArticlesContent(WAccountPO accountPO) {
        StringBuilder builder = new StringBuilder();
        String accountNickname = accountPO == null ? "未知公众号" : accountPO.getNickname();
        String fakeId = accountPO == null ? "未知fakeId" : accountPO.getFakeId();
        builder.append("公众号：").append(accountNickname).append(" 公众号fakeId：").append(fakeId).append("\n");
        List<WArticleItemPO> articleItemPOS = wArticleProvider.getTodayArticles(fakeId);
        articleItemPOS.sort((first, second) -> second.getUpdateTime().compareTo(first.getUpdateTime()));
        if (CollectionUtil.isEmpty(articleItemPOS)) {
            builder.append("当天尚未更新文章").append("\n");
        } else {
            String formatCurDateStr = DateUtil.format(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
            builder.append("当天").append(formatCurDateStr).append("发布了").append(articleItemPOS.size()).append("篇文章").append("\n\n");
            collectArticleInfo(builder, articleItemPOS);
        }
        log.debug("getTodayArticlesContent: content = {}", builder.toString());
        return builder.toString();
    }

    private void collectArticleInfo(StringBuilder builder, List<WArticleItemPO> articleItemPOS) {
        for (WArticleItemPO articleItemPO : articleItemPOS) {
            builder.append("标题：").append(articleItemPO.getTitle()).append("\n")
                    .append("文章链接：").append(articleItemPO.getLink()).append("\n")
                    .append("封面图片链接：").append(articleItemPO.getCover()).append("\n")
                    .append("发布日期：").append(DateUtil.format(articleItemPO.getUpdateTime(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))).append("\n\n");
        }
    }
}
