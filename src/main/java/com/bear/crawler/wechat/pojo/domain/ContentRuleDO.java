package com.bear.crawler.wechat.pojo.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.Set;

@Data
public class ContentRuleDO {

    @JsonAlias("baseInclude")
    private boolean baseInclude;

    @JsonAlias("include")
    private Set<String> include;

    @JsonAlias("baseExclude")
    private boolean baseExclude;

    @JsonAlias("exclude")
    private Set<String> exclude;
}
