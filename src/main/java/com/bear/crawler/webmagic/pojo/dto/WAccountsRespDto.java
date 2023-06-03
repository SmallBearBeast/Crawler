package com.bear.crawler.webmagic.pojo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class WAccountsRespDto {
    @JsonAlias("list")
    private List<WAccountDto> accountDtos;

    @JsonAlias("base_resp")
    private CommonRespDto commonRespDto;
}
