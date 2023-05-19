package com.bear.crawler.webmagic.util;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.net.url.UrlQuery;
import com.bear.crawler.webmagic.pojo.dto.CommonRespDto;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Page;

import java.util.Random;

@Slf4j
public class OtherUtil {

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
}
