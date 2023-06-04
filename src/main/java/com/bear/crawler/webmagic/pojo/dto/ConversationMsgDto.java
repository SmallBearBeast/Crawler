package com.bear.crawler.webmagic.pojo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class ConversationMsgDto {
    @JsonAlias("biz_last_replay_id")
    private int bizLastReplayId;

    @JsonAlias("can_replay")
    private int canReplay;

    @JsonAlias("fans_nick_name")
    private String fansNickName;

    @JsonAlias("is_blacked")
    private int isBlacked;

    @JsonAlias("is_miss_reply")
    private int isMissReply;

    @JsonAlias("msg_items")
    private String msgItems;

    @JsonAlias("stage")
    private int stage;

    @JsonAlias("unread_cnt")
    private int unreadCnt;
}
