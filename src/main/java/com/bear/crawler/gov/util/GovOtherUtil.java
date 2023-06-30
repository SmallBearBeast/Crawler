package com.bear.crawler.gov.util;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.net.url.UrlQuery;
import com.bear.crawler.gov.pojo.dto.resp.GovRespDto;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Random;

@Slf4j
public class GovOtherUtil {
    private static final Random random = new Random();

    public static void sleep(int second) {
        int sleepTime = (second + random.nextInt(second)) * 1000;
        try {
            Thread.sleep(sleepTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkGovRespDto(GovRespDto respDto, String tag) {
        if (respDto != null) {
            int code = respDto.getCode();
            String msg = respDto.getMsg();
            if (code == 200) {
                log.info("checkGovRespDto: request successfully, tag = {}", tag);
                return true;
            } else {
                log.warn("checkGovRespDto: request fail, code = {}, msg = {}, tag = {}", code, msg, tag);
            }
        } else {
            log.warn("checkGovRespDto: respDto is null, tag = {}", tag);
        }
        return false;
    }

    public static String getNewUrlByParams(String url, Map<String, Object> params) {
        UrlQuery urlQuery = UrlBuilder.of(url).getQuery();
        UrlQuery newUrlQuery = new UrlQuery();
        for (Map.Entry<CharSequence, CharSequence> entry : urlQuery.getQueryMap().entrySet()) {
            if (!params.containsKey(entry.getKey().toString())) {
                newUrlQuery.add(entry.getKey(), entry.getValue());
            }
        }
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            newUrlQuery.add(entry.getKey(), entry.getValue());
        }
        return UrlBuilder.of(url).setQuery(newUrlQuery).setCharset(null).build();
    }
}
