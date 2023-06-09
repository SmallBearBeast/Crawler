package com.bear.crawler.webmagic.pojo.dto;

import cn.hutool.core.collection.CollectionUtil;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class WConversationDto {

    public static final int CAN_REPLAY = 1;
    public static final int NO_REPLAY = 0;

    @JsonAlias("biz_last_replay_id")
    private int bizLastReplayId;

    // 是否可以回复: 0不可以回复，1可以回复
    @JsonAlias("can_replay")
    private int canReplay;

    @JsonAlias("fans_nick_name")
    private String fansNickName;

    @JsonAlias("is_blacked")
    private int isBlacked;

    @JsonAlias("is_miss_reply")
    private int isMissReply;

    @JsonAlias("msg_items")
    private Map<String, List<MsgItemDto>> msgItemMap;

    @JsonAlias("stage")
    private int stage;

    @JsonAlias("unread_cnt")
    private int unreadCnt;

    public List<MsgItemDto> getMsgItemDtos() {
        if (CollectionUtil.isEmpty(msgItemMap)) {
            return new ArrayList<>();
        }
        List<MsgItemDto> msgItemDtos = msgItemMap.get("msg_item");
        return msgItemDtos == null ? new ArrayList<>() : msgItemDtos;
    }
}
