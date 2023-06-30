package com.bear.crawler.wechat.pojo.dto.resp;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public abstract class BaseRespDto {
    @JsonAlias("base_resp")
    private CommonRespDto commonRespDto;
}
