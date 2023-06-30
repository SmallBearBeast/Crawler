package com.bear.crawler.gov.pojo.dto.resp;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public abstract class GovRespDto<T> {

    @JsonAlias("code")
    private int code; // 401,

    @JsonAlias("msg")
    private String msg; // "获取用户信息异常，未获取到用户信息",

    @JsonAlias("data")
    private T data; // null,

    @JsonAlias("total")
    private Object total; // null
}
