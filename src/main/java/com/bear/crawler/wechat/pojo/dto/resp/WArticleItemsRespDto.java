package com.bear.crawler.wechat.pojo.dto.resp;

import com.bear.crawler.wechat.pojo.dto.WArticleItemDto;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class WArticleItemsRespDto extends BaseRespDto {
    @JsonAlias("app_msg_cnt")
    private int appMsgCnt;

    @JsonAlias("app_msg_list")
    private List<WArticleItemDto> articleItemDtos;
}
