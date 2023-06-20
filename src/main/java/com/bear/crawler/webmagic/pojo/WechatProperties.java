package com.bear.crawler.webmagic.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@PropertySource(value = {"classpath:wechat.properties"})
@ConfigurationProperties(prefix = "wechat", ignoreInvalidFields = true)
@Setter
@Getter
@ToString
public class WechatProperties {
    private String cookie= "";
    private String userAgent= "";
    private String fakeid= "";
    private String token= "";
}

