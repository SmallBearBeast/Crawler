package com.bear.crawler.webmagic.util;

import com.bear.crawler.webmagic.mybatis.generator.po.WechatArticleItemPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WechatOfficialAccountPO;
import com.bear.crawler.webmagic.pojo.dto.WechatArticleItemDto;
import com.bear.crawler.webmagic.pojo.dto.WechatOfficialAccountDto;

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

    public static WechatOfficialAccountPO dtoToPo(WechatOfficialAccountDto dto) {
        WechatOfficialAccountPO po = new WechatOfficialAccountPO();
        po.setAlias(dto.getAlias());
        po.setFakeId(dto.getFakeId());
        po.setHeadImg(dto.getHeadImg());
        po.setNickname(dto.getNickname());
        po.setSignature(dto.getSignature());
        return po;
    }
}
