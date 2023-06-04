package com.bear.crawler.webmagic.pojo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SendMsgRespDto extends BaseRespDto {

    @JsonAlias("is_miss_reply")
    private int isMissReply;

    @JsonAlias("stage")
    private int stage;
}
