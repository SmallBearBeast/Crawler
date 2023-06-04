package com.bear.crawler.webmagic.basic.http;

import cn.hutool.core.map.MapUtil;
import com.bear.crawler.webmagic.pojo.WechatConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

public class WechatAuthInterceptor implements Interceptor {

    private final WechatConfig wechatConfig;

    public WechatAuthInterceptor(WechatConfig wechatConfig) {
        this.wechatConfig = wechatConfig;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!isNeedToIntercept(request)) {
            return chain.proceed(request);
        }
        String url = request.url().url().toString();
        String formatUrl = formatUrl(url);
        Request newRequest = request.newBuilder()
                .url(formatUrl)
                .header("Cookie", wechatConfig.getCookie())
                .header("User-Agent", wechatConfig.getUserAgent())
                .build();
        return chain.proceed(newRequest);
    }

    private String formatUrl(String xmlContent){
        if(!StringUtils.hasText(xmlContent)){
            return xmlContent;
        }
        Map<String, String> map = MapUtil.of("token", wechatConfig.getToken());
        //定义{{开头 ，}}结尾的占位符
        PropertyPlaceholderHelper propertyPlaceholderHelper = new PropertyPlaceholderHelper("{{", "}}");
        //调用替换
        return propertyPlaceholderHelper.replacePlaceholders(xmlContent, map::get);
    }

    private boolean isNeedToIntercept(Request request) {
        HttpUrl httpUrl = request.url();
        return httpUrl.host().equals("mp.weixin.qq.com");
    }
}
