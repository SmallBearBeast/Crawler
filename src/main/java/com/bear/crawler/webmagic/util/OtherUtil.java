package com.bear.crawler.webmagic.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.net.url.UrlQuery;
import com.bear.crawler.webmagic.mybatis.generator.po.WArticleItemPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WPublicAccountPO;
import com.bear.crawler.webmagic.pojo.dto.CommonRespDto;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Page;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
public class OtherUtil {

    public static final String BEGIN = "begin";
    public static final String FAKE_ID = "fakeid";

    private static final Random random = new Random();

    public static void sleep(int second) {
        int sleepTime = (second + random.nextInt(second)) * 1000;
        try {
            Thread.sleep(sleepTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkCommonRespDto(CommonRespDto respDto, String tag) {
        if (respDto != null) {
            int ret = respDto.getRet();
            String errMsg = respDto.getErrMsg();
            if (ret == 0) {
                log.warn("Request is ok tag = {}", tag);
                return true;
            } else if (ret == 200013) {
                log.warn("The account has been blocked and needs to wait for a few hours to be unblocked, ret = {}, err_msg = {}, tag = {}", ret, errMsg, tag);
            } else if (ret == 200002) {
                log.warn("Parameter error, check the fakeid, ret = {}, err_msg = {}, tag = {}", ret, errMsg, tag);
            } else {
                log.warn("Other error, ret = {}, err_msg = {}, tag = {}", ret, errMsg, tag);
            }
        }
        return false;
    }

    public static String getQuery(Page page, String query) {
        String url = page.getUrl().get();
        UrlQuery urlQuery = UrlBuilder.of(url).getQuery();
        return String.valueOf(urlQuery.get(query));
    }

    public static void addNextTargetRequest(Page page, int begin) {
        String url = page.getUrl().get();
        UrlQuery urlQuery = UrlBuilder.of(url).getQuery();
        UrlQuery newUrlQuery = new UrlQuery();
        for (Map.Entry<CharSequence, CharSequence> entry : urlQuery.getQueryMap().entrySet()) {
            if (!BEGIN.contentEquals(entry.getKey())) {
                newUrlQuery.add(entry.getKey(), entry.getValue());
            }
        }
        newUrlQuery.add(BEGIN, begin + 5);
        String nextUrl = UrlBuilder.of(url).setQuery(newUrlQuery).build();
        page.addTargetRequest(nextUrl);
    }

    public static void saveSummaryContentToFile(WPublicAccountPO publicAccountPO, List<WArticleItemPO> articleItemPOS, String fetchArticleDir) {
        StringBuilder builder = new StringBuilder();
        String accountNickname = publicAccountPO == null ? "未知公众号" : publicAccountPO.getNickname();
        builder.append("公众号：").append(accountNickname).append(" 公众号fakeId：").append(publicAccountPO.getFakeId()).append("\n");
        articleItemPOS.sort((first, second) -> second.getUpdateTime().compareTo(first.getUpdateTime()));
        if (CollectionUtil.isEmpty(articleItemPOS)) {
            builder.append("当天尚未更新文章").append("\n");
        } else {
            String formatCurDateStr = DateUtil.format(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
            builder.append("当天").append(formatCurDateStr).append("发布了").append(articleItemPOS.size()).append("篇文章").append("\n\n");
            collectArticleInfo(builder, articleItemPOS);
        }
        log.debug("saveSummaryContentToFile: content = {}", builder.toString());

        String formatCurDateStr = DateUtil.format(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
        String dir = fetchArticleDir + File.separator + formatCurDateStr + File.separator + accountNickname;
        File summaryRecordFile = FileUtil.file(dir, "汇总记录.md");
        FileUtil.writeString(builder.toString(), summaryRecordFile, "utf-8");
    }

    public static void collectArticleInfo(StringBuilder builder, List<WArticleItemPO> articleItemPOS) {
        for (WArticleItemPO articleItemPO : articleItemPOS) {
            builder.append("标题：").append(articleItemPO.getTitle()).append("\n")
                    .append("文章链接：").append(articleItemPO.getLink()).append("\n")
                    .append("封面图片链接：").append(articleItemPO.getCover()).append("\n")
                    .append("发布日期：").append(DateUtil.format(articleItemPO.getUpdateTime(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))).append("\n\n");
        }
    }
}
