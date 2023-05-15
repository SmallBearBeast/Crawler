package com.bear.crawler.webmagic.pojo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public abstract class BaseRespDto {
    @JsonAlias("base_resp")
    private CommonRespDto commonRespDto;
}
