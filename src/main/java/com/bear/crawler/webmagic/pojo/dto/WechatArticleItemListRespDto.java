package com.bear.crawler.webmagic.pojo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class WechatArticleItemListRespDto {
    @JsonAlias("app_msg_cnt")
    private int appMsgCnt;

    @JsonAlias("app_msg_list")
    private List<WechatArticleItemDto> appMsgList;
}
