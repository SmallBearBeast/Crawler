package com.bear.crawler.webmagic.util;

import com.bear.crawler.webmagic.mybatis.generator.po.WArticleItemPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WPublicAccountPO;
import com.bear.crawler.webmagic.pojo.dto.WArticleItemDto;
import com.bear.crawler.webmagic.pojo.dto.WPublicAccountDto;

import java.util.Date;

public class TransformBeanUtil {
    public static WArticleItemPO dtoToPo(WArticleItemDto dto) {
        WArticleItemPO po = new WArticleItemPO();
        po.setAid(dto.getAid());
        po.setAppmsgid(dto.getAppmsgid());
        po.setCover(dto.getCover());
        po.setCreateTime(new Date(dto.getCreateTime() * 1000L));
        po.setItemidx(dto.getItemidx());
        po.setLink(dto.getLink());
        po.setTitle(dto.getTitle());
        po.setUpdateTime(new Date(dto.getUpdateTime() * 1000L));
        return po;
    }

    public static WPublicAccountPO dtoToPo(WPublicAccountDto dto) {
        WPublicAccountPO po = new WPublicAccountPO();
        po.setAlias(dto.getAlias());
        po.setFakeId(dto.getFakeId());
        po.setHeadImg(dto.getHeadImg());
        po.setNickname(dto.getNickname());
        po.setSignature(dto.getSignature());
        return po;
    }
}
