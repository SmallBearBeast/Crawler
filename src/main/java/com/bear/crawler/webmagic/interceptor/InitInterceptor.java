package com.bear.crawler.webmagic.interceptor;

import com.bear.crawler.CrawlerApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class InitInterceptor implements AsyncHandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (CrawlerApplication.isInit()) {
            response.sendError(HttpStatus.OK.value(), "当前服务器正在初始化任务中");
            return false;
        }
        return true;
    }
}
