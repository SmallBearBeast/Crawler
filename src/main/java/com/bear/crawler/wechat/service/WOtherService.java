package com.bear.crawler.wechat.service;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class WOtherService {

    // TODO: 6/5/23 草稿？
    // send to me可以当做一个管理者通知渠道
    @Autowired
    private ContextRefresher contextRefresher;

    @Autowired
    private ConfigurableEnvironment environment;

    public void updateWechatProperties(String token, String cookie) {
        //修改配置文件中属性
        Map<String, Object> map = new HashMap<>();
        map.put("wechat.token", token);
        map.put("wechat.cookie", cookie);
        MapPropertySource propertySource = new MapPropertySource("dynamic", map);
        //将修改后的配置设置到environment中
        environment.getPropertySources().addFirst(propertySource);
        //异步调用refresh方法，避免阻塞一直等待无响应
        ThreadUtil.execute(() -> contextRefresher.refresh());
    }
}
