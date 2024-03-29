package com.bear.crawler.wechat.provider;

import com.bear.crawler.wechat.mybatis.generator.po.WMsgItemPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class WMsgItemProvider {

    @Autowired
    private WMsgItemCache wMsgItemCache;

    public void updateCache(WMsgItemPO msgItemPO) {
        Map<String, WMsgItemPO> allMsgItemMap = wMsgItemCache.getAllMsgItemMap();
        Map<String, WMsgItemPO> recentMsgItemMap = getRecentMsgItemMap();
        Map<String, WMsgItemPO> canReplayMsgItemMap = wMsgItemCache.getCanReplayMsgItemMap();
        wMsgItemCache.updateAllMsgItemMap(allMsgItemMap, msgItemPO);
        wMsgItemCache.updateRecentMsgItemMap(recentMsgItemMap, msgItemPO);
        wMsgItemCache.updateCanReplayMsgItemMap(canReplayMsgItemMap, msgItemPO);
    }

    public List<WMsgItemPO> getRecentMsgItems() {
        return new ArrayList<>(getRecentMsgItemMap().values());
    }

    public List<WMsgItemPO> getCanReplayMsgItems() {
        return new ArrayList<>(wMsgItemCache.getCanReplayMsgItemMap().values());
    }

    public @Nullable WMsgItemPO findMsgItemByFakeId(String fakeId) {
        return wMsgItemCache.getAllMsgItemMap().get(fakeId);
    }

    public boolean isRecentMsg(String fakeId) {
        return getRecentMsgItemMap().containsKey(fakeId);
    }

    private Map<String, WMsgItemPO> getRecentMsgItemMap() {
        Map<String, WMsgItemPO> recentMsgItemMap = wMsgItemCache.getRecentMsgItemMap();
        for (Map.Entry<String, WMsgItemPO> entry : recentMsgItemMap.entrySet()) {
            WMsgItemPO msgItemPO = entry.getValue();
            wMsgItemCache.updateRecentMsgItemMap(recentMsgItemMap, msgItemPO);
        }
        return recentMsgItemMap;
    }
}
