package com.bear.crawler.webmagic.pojo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class WPublishPageDto {

    @JsonAlias("total_count")
    private int totalCount;

    @JsonAlias("publish_count")
    private int publishCount;

    @JsonAlias("masssend_count")
    private int masssendCount;

    @JsonAlias("publish_list")
    private List<WPublishItemDto> publishList;

    @JsonAlias("featured_count")
    private int featuredCount;
}
