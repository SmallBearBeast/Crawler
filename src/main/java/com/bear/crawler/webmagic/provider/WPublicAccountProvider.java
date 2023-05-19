package com.bear.crawler.webmagic.provider;

import com.bear.crawler.webmagic.mybatis.generator.po.WPublicAccountPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class WPublicAccountProvider {

    @Autowired
    private WPublicAccountCache wPublicAccountCache;

    public void updateCache(WPublicAccountPO accountPO) {
        Map<String, WPublicAccountPO> allAccountMap = wPublicAccountCache.getAllAccountMap();
        allAccountMap.put(accountPO.getFakeId(), accountPO);
    }

    public boolean isInAccountDB(WPublicAccountPO accountPO) {
        Map<String, WPublicAccountPO> map = wPublicAccountCache.getAllAccountMap();
        return map.containsKey(accountPO.getFakeId());
    }

    public @Nullable WPublicAccountPO findByFakeId(String fakeId) {
        Map<String, WPublicAccountPO> allAccountMap = wPublicAccountCache.getAllAccountMap();
        return allAccountMap.get(fakeId);
    }

    public @NonNull Map<String, WPublicAccountPO> getAllAccountMap() {
        return wPublicAccountCache.getAllAccountMap();
    }

    public @NonNull Map<String, WPublicAccountPO> getNeedFetchAccountMap() {
        return wPublicAccountCache.getNeedFetchAccountMap();
    }
}
