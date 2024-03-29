package com.bear.crawler.wechat.pojo.dto.resp;

import com.bear.crawler.wechat.pojo.dto.WAccountDto;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class WAccountsRespDto extends BaseRespDto {
    @JsonAlias("list")
    private List<WAccountDto> accountDtos;
}
