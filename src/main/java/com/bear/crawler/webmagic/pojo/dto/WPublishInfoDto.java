package com.bear.crawler.webmagic.pojo.dto;


import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class WPublishInfoDto {

    @JsonAlias("type")
    private int type;

    @JsonAlias("msgid")
    private long msgid;

    @JsonAlias("appmsg_info")
    private List<Object> appmsgInfo;

    @JsonAlias("publish_info")
    private Object publishInfo;

    @JsonAlias("appmsgex")
    private List<WArticleItemDto> appmsgex;
}
