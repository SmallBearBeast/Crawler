package com.bear.crawler.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {
        "com.bear.crawler.wechat.mybatis.generator.mapper",
        "com.bear.crawler.wechat.mybatis.custom"
})
public class MybatisConfig {

}
