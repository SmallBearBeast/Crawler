package com.bear.crawler.webmagic.pojo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class WechatOfficialAccountDto {
    @JsonAlias("alias")
    private String alias;

    @JsonAlias("fakeid")
    private String fakeId;

    @JsonAlias("nickname")
    private String nickname;

    @JsonAlias("round_head_img")
    private String headImg;

    @JsonAlias("signature")
    private String signature;
}
