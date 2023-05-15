package com.bear.crawler.webmagic.pojo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class CommonRespDto {
    @JsonAlias("ret")
    private int ret;

    @JsonAlias("err_msg")
    private String errMsg;
}
