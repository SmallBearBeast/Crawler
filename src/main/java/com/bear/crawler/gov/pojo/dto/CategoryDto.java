package com.bear.crawler.gov.pojo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class CategoryDto {

    @JsonAlias("unid")
    private String unid; // "B540998DF843DAA8BD94529DDC799BB0",

    @JsonAlias("bsfwCount")
    private int bsfwCount; // 1,

    @JsonAlias("name")
    private String name; // "证件办理",

    @JsonAlias("sxCount")
    private int sxCount; // 1,

    @JsonAlias("value")
    private String value; // "090",

    @JsonAlias("xzjgCount")
    private int xzjgCount; // 0
}
