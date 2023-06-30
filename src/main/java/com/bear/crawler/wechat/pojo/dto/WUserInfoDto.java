package com.bear.crawler.wechat.pojo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class WUserInfoDto {

    @JsonAlias("user_openid")
    private String userOpenid;

    @JsonAlias("user_name")
    private String userName;

    @JsonAlias("user_remark")
    private String userRemark;

    @JsonAlias("user_group_id")
    private List<Integer> userGroupIds;

    @JsonAlias("user_create_time")
    private long userCreateTime;

    @JsonAlias("user_head_img")
    private String userHeadImg;
}
