package com.bear.crawler.wechat.pojo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class WArticleItemDto {
    @JsonAlias("aid")
    private String aid;

    @JsonAlias("album_id")
    private String albumId;

    @JsonAlias("appmsg_album_infos")
    private List<AlbumInfoDto> albumInfoDtos;

    @JsonAlias("appmsgid")
    private long appmsgid;

    @JsonAlias("checking")
    private int checking;

    @JsonAlias("copyright_type")
    private int copyrightType;

    @JsonAlias("cover")
    private String cover;

    @JsonAlias("create_time")
    private long createTime;

    @JsonAlias("digest")
    private String digest;

    @JsonAlias("has_red_packet_cover")
    private int hasRedPacketCover;

    @JsonAlias("is_pay_subscribe")
    private int isPaySubscribe;

    @JsonAlias("item_show_type")
    private int itemShowType;

    @JsonAlias("itemidx")
    private int itemidx;

    @JsonAlias("link")
    private String link;

    @JsonAlias("media_duration")
    private String mediaDuration;

    @JsonAlias("mediaapi_publish_status")
    private int mediaapiPublishStatus;

    @JsonAlias("pay_album_info")
    private Object payAlbumInfo;

    @JsonAlias("tagid")
    private List<String> tagid;

    @JsonAlias("title")
    private String title;

    @JsonAlias("update_time")
    private long updateTime;
}
