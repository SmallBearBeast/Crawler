package com.bear.crawler.webmagic.util;

import com.bear.crawler.webmagic.mybatis.generator.po.WArticleItemPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WAccountPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WMsgItemPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WUserInfoPO;
import com.bear.crawler.webmagic.pojo.dto.MsgItemDto;
import com.bear.crawler.webmagic.pojo.dto.WUserInfoDto;
import com.bear.crawler.webmagic.pojo.dto.WArticleItemDto;
import com.bear.crawler.webmagic.pojo.dto.WAccountDto;

import java.util.Date;

public class BeanConverterUtil {
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

    public static WAccountPO dtoToPo(WAccountDto dto) {
        WAccountPO po = new WAccountPO();
        po.setAlias(dto.getAlias());
        po.setFakeId(dto.getFakeId());
        po.setHeadImg(dto.getHeadImg());
        po.setNickname(dto.getNickname());
        po.setSignature(dto.getSignature());
        return po;
    }

    public static WUserInfoPO dtoToPo(WUserInfoDto dto) {
        WUserInfoPO po = new WUserInfoPO();
        po.setOpenid(dto.getUserOpenid());
        po.setName(dto.getUserName());
        po.setRemark(dto.getUserRemark());
        po.setGroupId(dto.getUserGroupIds().toString());
        po.setCreateTime(new Date(dto.getUserCreateTime() * 1000L));
        po.setHeadImg(dto.getUserHeadImg());
        return po;
    }

    public static WMsgItemPO dtoToPo(MsgItemDto dto) {
        WMsgItemPO po = new WMsgItemPO();
        po.setMsgId(dto.getMsgId());
        po.setType(dto.getType());
        po.setFakeId(dto.getFakeId());
        po.setNickname(dto.getNickName());
        po.setDateTime(new Date(dto.getDateTime() * 1000L));
        po.setContent(dto.getContent());
        po.setSource(dto.getSource());
        po.setMsgStatus(dto.getMsgStatus());
        po.setTitle(dto.getTitle() == null ? "" : dto.getTitle());
        po.setMsgDesc(dto.getMsgDesc() == null ? "" : dto.getMsgDesc());
        po.setContentUrl(dto.getContentUrl() == null ? "" : dto.getContentUrl());
        po.setShowType(dto.getShowType());
        po.setFileId(dto.getFileId());
        po.setAppSubType(dto.getAppSubType());
        po.setHasReply(dto.getHasReply());
        po.setRefuseReason(dto.getRefuseReason());
        po.setToUin(dto.getToUin());
        po.setCopyrightStatus(dto.getCopyrightStatus());
        po.setCopyrightType(dto.getCopyrightType());
        po.setIsVipMsg(dto.getIsVipMsg());
        po.setIsOftenRead(dto.getIsOftenRead());
        po.setRewardMoneyCount(dto.getRewardMoneyCount());
        po.setCommentCount(dto.getCommentCount());
        po.setPaysubscribeWecoinAmount(dto.getPaysubscribeWecoinAmount());
        return po;
    }
}
