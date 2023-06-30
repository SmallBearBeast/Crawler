package com.bear.crawler.wechat.pojo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class MsgItemDto {

    @JsonAlias("id")
    private int msgId; // 500000200

    @JsonAlias("type")
    private int type; // 10002

    @JsonAlias("fakeid")
    private String fakeId = ""; // "3880971249"

    @JsonAlias("nick_name")
    private String nickName = ""; // "SmallBearBeast"

    @JsonAlias("date_time")
    private long dateTime; // 1685895744

    @JsonAlias("content")
    private String content = "";

    @JsonAlias("source")
    private String source = ""; // "biz"

    @JsonAlias("msg_status")
    private int msgStatus; // 0

    @JsonAlias("title")
    private String title = "";

    @JsonAlias("desc")
    private String msgDesc = "";

    @JsonAlias("content_url")
    private String contentUrl = "";

    @JsonAlias("show_type")
    private int showType;  // 1

    @JsonAlias("file_id")
    private int fileId; // 0

    @JsonAlias("app_sub_type")
    private int appSubType; // 3

    @JsonAlias("has_reply")
    private int hasReply; // 0

    @JsonAlias("refuse_reason")
    private String refuseReason = "";

    @JsonAlias("multi_item")
    private List<Object> multiItem;

    @JsonAlias("to_uin")
    private String toUin = ""; // "o8etS59OrP8pUUdjCB6Dy8sKomII"

    @JsonAlias("copyright_status")
    private int copyrightStatus; // 100

    @JsonAlias("copyright_type")
    private int copyrightType; // 0

    @JsonAlias("wx_headimg_url")
    private String wxHeadimgUrl = "";

    @JsonAlias("func_flag")
    private int funcFlag; // 16777216

    @JsonAlias("is_vip_msg")
    private int isVipMsg; // 0

    @JsonAlias("small_headimg_url")
    private String smallHeadImgUrl = ""; // "/64"

    @JsonAlias("id_64bit")
    private String id64bit = ""; // "500000200"

    @JsonAlias("is_often_read")
    private int isOftenRead; // 0

    @JsonAlias("biz_nickname")
    private String bizNickname = ""; // "SmallBearBeast"

    @JsonAlias("biz_headimgurl")
    private String bizHeadImgUrl = ""; // "http://wx.qlogo.cn/mmhead/Q3auHgzwzM7miaO8liaoic1F8r1oiayzqqiclIR2m3brjUjjXeIu5rxKiaOQ/0"

    @JsonAlias("reward_money_count")
    private int rewardMoneyCount;

    @JsonAlias("comment_count")
    private int commentCount;

    @JsonAlias("paysubscribe_wecoin_amount")
    private int paysubscribeWecoinAmount;
}
