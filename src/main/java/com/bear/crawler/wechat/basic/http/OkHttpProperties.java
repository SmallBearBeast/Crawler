package com.bear.crawler.wechat.basic.http;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "okhttp")
public class OkHttpProperties {
    private int connectTimeout = 10_000;
    private int readTimeout = 10_000;
    private int writeTimeout = 10_000;
    //最大连接池大小
    private int maxIdleConnections = 5;
    private int keepAliveDuration = 300;
}
