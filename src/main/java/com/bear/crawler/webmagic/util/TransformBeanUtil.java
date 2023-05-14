package com.bear.crawler.webmagic.util;

import com.bear.crawler.webmagic.mybatis.generator.po.WechatArticleItemPO;
import com.bear.crawler.webmagic.pojo.dto.WechatArticleItemDto;

import java.util.Date;

public class TransformBeanUtil {
    public static WechatArticleItemPO dtoToPo(WechatArticleItemDto dto) {
        WechatArticleItemPO po = new WechatArticleItemPO();
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
}
