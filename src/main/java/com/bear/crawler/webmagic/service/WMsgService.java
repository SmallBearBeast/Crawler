package com.bear.crawler.webmagic.service;

import cn.hutool.core.map.MapUtil;
import com.bear.crawler.webmagic.AppConstant;
import com.bear.crawler.webmagic.basic.http.OkHttp;
import com.bear.crawler.webmagic.dao.WMsgItemDao;
import com.bear.crawler.webmagic.mybatis.generator.po.WMsgItemPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WUserInfoPO;
import com.bear.crawler.webmagic.pojo.WechatProperties;
import com.bear.crawler.webmagic.pojo.dto.MsgItemDto;
import com.bear.crawler.webmagic.pojo.dto.WConversationDto;
import com.bear.crawler.webmagic.pojo.dto.resp.SendMsgRespDto;
import com.bear.crawler.webmagic.pojo.dto.resp.WConversationRespDto;
import com.bear.crawler.webmagic.provider.WMsgItemProvider;
import com.bear.crawler.webmagic.provider.WUserInfoProvider;
import com.bear.crawler.webmagic.util.BeanConverterUtil;
import com.bear.crawler.webmagic.util.OtherUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class WMsgService {

    @Autowired
    private WechatProperties wechatProperties;

    @Autowired
    private WMsgItemProvider wMsgItemProvider;

    @Autowired
    private WUserInfoProvider wUserInfoProvider;

    @Autowired
    private WMsgItemDao wMsgItemDao;

    @Autowired
    private OkHttp okHttp;

    public void syncRecentMsgs() {
        List<WUserInfoPO> userInfoPOS = wUserInfoProvider.getAllUserInfos();
        log.debug("syncRecentMsgs: unRecentUserInfos.size = {}", userInfoPOS.size());
        for (WUserInfoPO userInfoPO : userInfoPOS) {
            String fakeId = userInfoPO.getOpenid();
            if (!wMsgItemProvider.isRecentMsg(fakeId)) {
                WMsgItemPO msgItemPO = wMsgItemProvider.findMsgItemByFakeId(fakeId);
                String lastMsgId = String.valueOf(msgItemPO == null ? "" : msgItemPO.getMsgId());
                String url = "https://mp.weixin.qq.com/cgi-bin/singlesendpage?action=sync&tofakeid=" + fakeId + "&lastmsgfromfakeid=&lastmsgid=" + lastMsgId + "&createtime&token={{token}}&lang=zh_CN&f=json&ajax=1";
                WConversationRespDto respDto = okHttp.get(url, null, null, WConversationRespDto.class);
                if (OtherUtil.checkCommonRespDto(respDto, "WechatService.syncRecentMsgs()")) {
                    WConversationDto conversationDto = respDto.getConversationDto();
                    List<MsgItemDto> msgItemDtos = conversationDto.getMsgItemDtos();
                    findLatestMsgAndSaveToDB(msgItemDtos, fakeId, conversationDto.getCanReplay() == WConversationDto.CAN_REPLAY);
                }
            }
        }
    }

    private void findLatestMsgAndSaveToDB(List<MsgItemDto> msgItemDtos, String fakeId, boolean canReplay) {
        long latestTime = 0L;
        MsgItemDto latestMsgItemDto = null;
        for (MsgItemDto msgItemDto : msgItemDtos) {
            if (fakeId.equals(msgItemDto.getFakeId()) && latestTime < msgItemDto.getDateTime()) {
                latestTime = msgItemDto.getDateTime();
                latestMsgItemDto = msgItemDto;
            }
        }
        if (latestMsgItemDto == null) {
            // 没有找到msg用默认占位msg代替。
            latestMsgItemDto = new MsgItemDto();
            latestMsgItemDto.setFakeId(fakeId);
            latestMsgItemDto.setNickName("Empty NickName");
        }
        log.debug("findLatestMsgAndSaveToDB: title = {}", latestMsgItemDto.getTitle());
        WMsgItemPO msgItemPO = BeanConverterUtil.dtoToPo(latestMsgItemDto);
        WMsgItemPO saveMsgItemPO = wMsgItemProvider.findMsgItemByFakeId(msgItemPO.getFakeId());
        msgItemPO.setCanReplay(canReplay);
        if (saveMsgItemPO != null) {
            msgItemPO.setId(saveMsgItemPO.getId());
            wMsgItemDao.updateByFakeId(msgItemPO);
        } else {
            wMsgItemDao.insert(msgItemPO);
        }
        wMsgItemProvider.updateCache(msgItemPO);
    }

    // TODO: 6/15/23 支持发送其他消息
    public void sendMsgToMe(String aid) {
        log.debug("sendMsgToMe: aid = {}", aid);
        WMsgItemPO msgItemPO = wMsgItemProvider.findMsgItemByFakeId(AppConstant.MSG_FAKE_ID);
        if (msgItemPO != null) {
            sendMsgToRecentUserInternal(aid, msgItemPO.getFakeId(), msgItemPO.getNickname());
        } else {
            log.debug("sendMsgToMe: my msgItemPO is null");
        }
    }

    public void sendMsgToRecentUser(String aid) {
        log.debug("sendMsgToRecentUser: aid = {}", aid);
        List<WMsgItemPO> recentMsgItems = wMsgItemProvider.getRecentMsgItems();
        List<WMsgItemPO> canReplayMsgItems = wMsgItemProvider.getCanReplayMsgItems();
        Map<String, String> recentOpenIdMap = new HashMap<>();
        for (WMsgItemPO msgItem : recentMsgItems) {
            if (wUserInfoProvider.findByOpenId(msgItem.getFakeId()) != null) {
                recentOpenIdMap.put(msgItem.getFakeId(), msgItem.getNickname());
            }
        }
        for (WMsgItemPO msgItem : canReplayMsgItems) {
            if (wUserInfoProvider.findByOpenId(msgItem.getFakeId()) != null) {
                recentOpenIdMap.put(msgItem.getFakeId(), msgItem.getNickname());
            }
        }
        for (Map.Entry<String, String> entry : recentOpenIdMap.entrySet()) {
            String openId = entry.getKey();
            String name = entry.getValue();
            sendMsgToRecentUserInternal(aid, openId, name);
        }
    }

    private void sendMsgToRecentUserInternal(String aid, String openId, String name) {
        String url = "https://mp.weixin.qq.com/cgi-bin/singlesend?t=ajax-response&f=json";
        String referer = "https://mp.weixin.qq.com/cgi-bin/message?t=message/list&count=20&day=7&token=" + wechatProperties.getToken() + "&lang=zh_CN";
        Map<String, String> headerMap = MapUtil.of(AppConstant.REFERER, referer);
        Map<String, String> paramMap = MapUtil.builder("tofakeid", openId)
                .put("quickreplyid", "")
                .put("imgcode", "")
                .put("type", "10")
                .put("appmsgid", aid)
                .put("token", wechatProperties.getToken())
                .put("lang", "zh_CN")
                .put("f", "json")
                .put("ajax", "1")
                .build();
        SendMsgRespDto respDto = okHttp.post(url, paramMap, headerMap, SendMsgRespDto.class);
        if (OtherUtil.checkCommonRespDto(respDto, "WechatService.sendMsgToRecentUserInternal()")) {
            log.info("sendMsgToRecentUserInternal: send message to {} success", name);
        } else {
            log.info("sendMsgToRecentUserInternal: send message to {} fail", name);
            WMsgItemPO msgItemPO = wMsgItemProvider.findMsgItemByFakeId(openId);
            if (msgItemPO != null) {
                msgItemPO.setCanReplay(false);
                wMsgItemProvider.updateCache(msgItemPO);
            }
        }
    }
}
