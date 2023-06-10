package com.bear.crawler.webmagic.pojo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class WPublishItemDto {

    @JsonAlias("publish_type")
    private int publishType;

    @JsonAlias("publish_info")
    private WPublishInfoDto publishInfo;
}
