package com.bear.crawler.webmagic.basic.http;

import cn.hutool.core.util.RandomUtil;
import com.bear.crawler.webmagic.util.OtherUtil;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class WSleepInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!isNeedToIntercept(request)) {
            return chain.proceed(request);
        }
        randomSleep();
        return chain.proceed(request);
    }

    private boolean isNeedToIntercept(Request request) {
        HttpUrl httpUrl = request.url();
        return httpUrl.host().equals("mp.weixin.qq.com");
    }

    private void randomSleep() {
        OtherUtil.sleep(RandomUtil.randomInt(1, 4));
    }
}
