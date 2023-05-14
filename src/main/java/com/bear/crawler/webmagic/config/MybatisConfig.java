package com.bear.crawler.webmagic.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.bear.crawler.webmagic.dao", "com.bear.crawler.webmagic.mybatis.generator.mapper"})
public class MybatisConfig {

}
