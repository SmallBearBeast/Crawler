package com.bear.crawler.wechat.pojo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class AlbumInfoDto {

    @JsonAlias("album_id")
    private long albumId;

    @JsonAlias("appmsg_album_infos")
    private List<AlbumInfoDto> albumInfos;

    @JsonAlias("id")
    private String id;

    @JsonAlias("tagSource")
    private int tagSource;

    @JsonAlias("title")
    private String title;
}
