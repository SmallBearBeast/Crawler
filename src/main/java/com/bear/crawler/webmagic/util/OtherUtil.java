package com.bear.crawler.webmagic.util;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.net.url.UrlQuery;
import com.bear.crawler.webmagic.AppConstant;
import com.bear.crawler.webmagic.pojo.dto.resp.BaseRespDto;
import com.bear.crawler.webmagic.pojo.dto.resp.CommonRespDto;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
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

    public static boolean checkCommonRespDto(BaseRespDto respDto, String tag) {
        CommonRespDto commonRespDto = respDto == null ? null : respDto.getCommonRespDto();
        if (commonRespDto != null) {
            int ret = commonRespDto.getRet();
            String errMsg = commonRespDto.getErrMsg();
            if (ret == 0) {
                log.warn("checkCommonRespDto: Request is ok tag = {}", tag);
                return true;
            } else if (ret == 200013) {
                log.warn("checkCommonRespDto: The account has been blocked and needs to wait for a few hours to be unblocked, ret = {}, err_msg = {}, tag = {}", ret, errMsg, tag);
            } else if (ret == 200002) {
                log.warn("checkCommonRespDto: Parameter error, check the fakeId, ret = {}, err_msg = {}, tag = {}", ret, errMsg, tag);
            } else {
                log.warn("checkCommonRespDto: Other error, ret = {}, err_msg = {}, tag = {}", ret, errMsg, tag);
            }
        } else {
            log.warn("checkCommonRespDto: commonRespDto is null");
        }
        return false;
    }

    public static String getQuery(String url, String query) {
        UrlQuery urlQuery = UrlBuilder.of(url).getQuery();
        return String.valueOf(urlQuery.get(query));
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

    public static boolean isMyFakeId(String fakeId) {
        return AppConstant.MY_FAKE_ID.equals(fakeId);
    }

    public static String articleStateToStr(int state) {
        switch (state) {
            case AppConstant.UNREAD: return "UNREAD";
            case AppConstant.READ: return "READ";
            case AppConstant.IN_PROGRESS: return "IN_PROGRESS";
            case AppConstant.PUBLISH: return "PUBLISH";
        }
        return "KNOWN";
    }
}
