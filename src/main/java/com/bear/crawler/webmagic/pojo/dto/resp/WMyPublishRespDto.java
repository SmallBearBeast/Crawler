package com.bear.crawler.webmagic.pojo.dto.resp;

import cn.hutool.core.collection.CollectionUtil;
import com.bear.crawler.webmagic.pojo.dto.WArticleItemDto;
import com.bear.crawler.webmagic.pojo.dto.WPublishInfoDto;
import com.bear.crawler.webmagic.pojo.dto.WPublishItemDto;
import com.bear.crawler.webmagic.pojo.dto.WPublishPageDto;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class WMyPublishRespDto extends BaseRespDto {

    @JsonAlias("publish_page")
    private WPublishPageDto publishPage;

    public @NonNull List<WArticleItemDto> getMyArticleItemDtos() {
        List<WArticleItemDto> myArticleItemDtos = new ArrayList<>();
        List<WPublishItemDto> publishItemDtos = publishPage == null ? null : publishPage.getPublishList();
        if (!CollectionUtil.isEmpty(publishItemDtos)) {
            for (WPublishItemDto publishItemDto : publishItemDtos) {
                WPublishInfoDto publishInfoDto = publishItemDto.getPublishInfo();
                List<WArticleItemDto> articleItemDtos = publishInfoDto == null ? null : publishInfoDto.getAppmsgex();
                if (!CollectionUtil.isEmpty(articleItemDtos)) {
                    myArticleItemDtos.addAll(articleItemDtos);
                }
            }
        }
        return myArticleItemDtos;
    }
}
