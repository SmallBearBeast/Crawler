package com.bear.crawler.wechat.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {
        "com.bear.crawler.webmagic.mybatis.generator.mapper",
        "com.bear.crawler.webmagic.mybatis.custom"
})
public class MybatisConfig {

}
