package com.bear.crawler.webmagic.pojo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class WechatOfficialAccountListRespDto extends BaseRespDto {
//    @JsonAlias("base_resp")
//    private CommonRespDto commonRespDto;

    @JsonAlias("list")
    private List<WechatOfficialAccountDto> officialAccountDtoList;
}
