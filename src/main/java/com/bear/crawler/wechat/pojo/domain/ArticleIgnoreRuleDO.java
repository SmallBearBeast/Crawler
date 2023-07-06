package com.bear.crawler.wechat.pojo.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class ArticleIgnoreRuleDO {

    @JsonAlias("name")
    private String name;

    @JsonAlias("titleRule")
    private ContentRuleDO titleRule;

    @JsonAlias("contentRule")
    private ContentRuleDO contentRule;

    public boolean isIncludeAll() {
        return titleRule.getInclude().contains("all");
    }
}
