package com.bear.crawler.wechat.config;

import com.bear.crawler.wechat.interceptor.InitInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new InitInterceptor()).addPathPatterns(
                "/webMagic/*"
        );
    }
}
