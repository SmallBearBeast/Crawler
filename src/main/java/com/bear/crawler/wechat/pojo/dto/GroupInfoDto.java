package com.bear.crawler.wechat.pojo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class GroupInfoDto {

    @JsonAlias("group_name")
    private String groupName;

    @JsonAlias("group_cnt")
    private int groupCnt;

    @JsonAlias("group_create_time")
    private long groupCreateTime;

    @JsonAlias("group_id")
    private int groupId;
}
