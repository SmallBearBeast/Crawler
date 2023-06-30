package com.bear.crawler.wechat.pojo.dto.resp;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SendMsgRespDto extends BaseRespDto {

    @JsonAlias("is_miss_reply")
    private int isMissReply;

    @JsonAlias("stage")
    private int stage;
}
