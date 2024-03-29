package com.bear.crawler.wechat.pojo.dto.resp;

import com.bear.crawler.wechat.pojo.dto.WConversationDto;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class WConversationRespDto extends BaseRespDto {

    @JsonAlias("page_info")
    private WConversationDto conversationDto;
}
