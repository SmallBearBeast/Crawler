package com.bear.crawler.webmagic.basic.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

// TODO: 6/3/23 可以转成starter试看看
@Slf4j
@Component
public class OkHttp {
    //定义接收类型
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Autowired
    private OkHttpClient okHttpClient;

    @Autowired
    private ObjectMapper objectMapper;

    public String get(String url, Map<String, String> params, Map<String, String> headers) {
        return get(url, params, headers, String.class);
    }

    public <T> T get(String url, Map<String, String> params, Map<String, String> headers, Class<T> clz) {
        StringBuilder sb = new StringBuilder(url);
        if (params != null && params.keySet().size() > 0) {
            boolean firstFlag = true;
            for (String key : params.keySet()) {
                if (firstFlag) {
                    sb.append("?").append(key).append("=").append(params.get(key));
                    firstFlag = false;
                } else {
                    sb.append("&").append(key).append("=").append(params.get(key));
                }
            }
        }
        Request.Builder builder = new Request.Builder();
        if (headers != null && headers.size() > 0) {
            for (String key : headers.keySet()) {
                builder.addHeader(key, headers.get(key));
            }
        }
        Request request = builder.url(sb.toString()).build();
        log.info("get: url = {}", sb.toString());
        return execute(request, clz);
    }

    public String post(String url, Map<String, String> params, Map<String, String> headers) {
        return post(url, params, headers, String.class);
    }

    public <T> T post(String url, Map<String, String> params, Map<String, String> headers, Class<T> clz) {
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        if (params != null && params.keySet().size() > 0) {
            for (String key : params.keySet()) {
                bodyBuilder.add(key, params.get(key));
            }
        }
        Request.Builder builder = new Request.Builder();
        if (headers != null && headers.size() > 0) {
            for (String key : headers.keySet()) {
                builder.addHeader(key, headers.get(key));
            }
        }
        Request request = new Request.Builder().url(url).post(bodyBuilder.build()).build();
        log.info("post: url = {}", url);
        return execute(request, clz);
    }

    public <T> T postJSON(String url, String json, Class<T> clz) {
        log.info("postJSON: url = {}", url);
        return executePost(url, json, JSON, null, clz);
    }

    public <T> T postJSON(String url, String json, Map<String, String> headers, Class<T> clz) {
        log.info("postJSON: url = {}", url);
        return executePost(url, json, JSON, headers, clz);
    }

    public <T> T executePost(String url, String data, MediaType contentType, Map<String, String> headers, Class<T> clz) {
        RequestBody requestBody = RequestBody.create(contentType, data);
        Request.Builder builder = new Request.Builder();
        if (headers != null && headers.size() > 0) {
            for (String key : headers.keySet()) {
                builder.addHeader(key, headers.get(key));
            }
        }
        Request request = builder.url(url).post(requestBody).build();
        return execute(request, clz);
    }

    private <T> T execute(Request request, Class<T> clz) {
        try (Response response = okHttpClient.newCall(request).execute()) {
            log.info("execute: code = {}", response.code());
            ResponseBody responseBody = response.body();
            //这里有个坑，response.body().string() string()方法只能调用一次！
            //string()方法获取到资源后就释放了，如果不调用这个方法，需要手动释放 response.close()
            //所以先取出来保存
            String body = responseBody != null ? responseBody.string() : "";
            return objectMapper.readValue(body, clz);
        } catch (Exception e) {
            log.info("execute：e = {}", e.getMessage());
        }
        return null;
    }
}
