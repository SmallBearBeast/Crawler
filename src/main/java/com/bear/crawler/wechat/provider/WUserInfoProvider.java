package com.bear.crawler.wechat.provider;

import com.bear.crawler.wechat.mybatis.generator.po.WUserInfoPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class WUserInfoProvider {

    @Autowired
    private WUserInfoCache wUserInfoCache;

    public void updateCache(WUserInfoPO userInfoPO, Boolean isRemove) {
        Map<String, WUserInfoPO> allUserInfoMap = wUserInfoCache.getAllUserInfoMap();
        Map<String, WUserInfoPO> resentUserInfoMap = getResentUserInfoMap();
        wUserInfoCache.updateAllUserInfoMap(allUserInfoMap, userInfoPO, isRemove);
        wUserInfoCache.updateRecentUserInfoMap(resentUserInfoMap, userInfoPO, isRemove);
    }

    public boolean isInDB(WUserInfoPO userInfoPO) {
        return findByOpenId(userInfoPO.getOpenid()) != null;
    }

    public @Nullable WUserInfoPO findByOpenId(String openId) {
        Map<String, WUserInfoPO> allUserInfoMap = wUserInfoCache.getAllUserInfoMap();
        return allUserInfoMap.get(openId);
    }

    public List<WUserInfoPO> getRecentUserInfos() {
        return new ArrayList<>(getResentUserInfoMap().values());
    }

    public List<WUserInfoPO> getAllUserInfos() {
        return new ArrayList<>(wUserInfoCache.getAllUserInfoMap().values());
    }

    public List<WUserInfoPO> getUnRecentUserInfos() {
        List<WUserInfoPO> unRecentUserInfoPOS = new ArrayList<>();
        Set<String> recentOpenIdSet = getResentUserInfoMap().keySet();
        Collection<WUserInfoPO> allUserInfoPOCollection = wUserInfoCache.getAllUserInfoMap().values();
        for (WUserInfoPO infoPO : allUserInfoPOCollection) {
            if (!recentOpenIdSet.contains(infoPO.getOpenid())) {
                unRecentUserInfoPOS.add(infoPO);
            }
        }
        return unRecentUserInfoPOS;
    }

    private Map<String, WUserInfoPO> getResentUserInfoMap() {
        Map<String, WUserInfoPO> resentUserInfoMap = wUserInfoCache.getResentUserInfoMap();
        for (Map.Entry<String, WUserInfoPO> entry : resentUserInfoMap.entrySet()) {
            WUserInfoPO userInfoPO = entry.getValue();
            wUserInfoCache.updateRecentUserInfoMap(resentUserInfoMap, userInfoPO, false);
        }
        return resentUserInfoMap;
    }
}
