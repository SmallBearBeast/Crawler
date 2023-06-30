package com.bear.crawler.wechat.pojo.dto.resp;

import cn.hutool.core.collection.CollectionUtil;
import com.bear.crawler.wechat.pojo.dto.WConversationDto;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class ConversationsRespDto extends BaseRespDto {

    @JsonAlias("continue_flag")
    private int continueFlag;

    @JsonAlias("filter_ivr_msg_setting")
    private int filterIvrMsgSetting;

    @JsonAlias("filter_spam_msg_setting")
    private int filterSpamMsgSetting;

    @JsonAlias("frommsgid")
    private int frommsgid;

    @JsonAlias("getunreadmsg")
    private int getunreadmsg;

    @JsonAlias("item")
    private List<Map<String, WConversationDto>> item;

    @JsonAlias("last_fans_msg_id")
    private int lastFansMsgId;

    @JsonAlias("latest_msg_id_64bit")
    private String latestMsgId64bit;

    @JsonAlias("new_fans_msg_num")
    private int newFansMsgNum;

    @JsonAlias("total_msg_cnt")
    private int totalMsgCnt;

    public List<WConversationDto> getConversationDtos() {
        if (CollectionUtil.isEmpty(item)) {
            return new ArrayList<>();
        }
        List<WConversationDto> msgDtos = new ArrayList<>();
        for (Map<String, WConversationDto> map : item) {
            if (map.get("msg") != null) {
                msgDtos.add(map.get("msg"));
            }
        }
        return msgDtos;
    }
}
