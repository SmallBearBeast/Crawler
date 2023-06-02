package com.bear.crawler.webmagic.provider;

import com.bear.crawler.webmagic.mybatis.generator.po.WPublicAccountPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class WPublicAccountProvider {

    @Autowired
    private WPublicAccountCache wPublicAccountCache;

    public void updateCache(WPublicAccountPO accountPO) {
        Map<String, WPublicAccountPO> allAccountMap = wPublicAccountCache.getAllAccountMap();
        wPublicAccountCache.updateAllAccountMap(accountPO, allAccountMap);
    }

    public void updateNeedToFetchCache(WPublicAccountPO accountPO) {
        Map<String, WPublicAccountPO> needFetchAccountMap = wPublicAccountCache.getNeedFetchAccountMap();
        wPublicAccountCache.updateAccountMapByNeedFetch(accountPO, needFetchAccountMap);
    }

    public boolean isInAccountDB(WPublicAccountPO accountPO) {
        Map<String, WPublicAccountPO> map = wPublicAccountCache.getAllAccountMap();
        return map.containsKey(accountPO.getFakeId());
    }

    public @Nullable WPublicAccountPO findByFakeId(String fakeId) {
        Map<String, WPublicAccountPO> allAccountMap = wPublicAccountCache.getAllAccountMap();
        return allAccountMap.get(fakeId);
    }

    public @Nullable WPublicAccountPO findByNickname(String nickname) {
        Map<String, WPublicAccountPO> allAccountMap = wPublicAccountCache.getAllAccountMap();
        for (WPublicAccountPO accountPO : allAccountMap.values()) {
            if (nickname.equals(accountPO.getNickname())) {
                return accountPO;
            }
        }
        return null;
    }

    public @NonNull Map<String, WPublicAccountPO> getAllAccountMap() {
        return wPublicAccountCache.getAllAccountMap();
    }

    public @NonNull List<WPublicAccountPO> getAllAccounts() {
        return new ArrayList<>(wPublicAccountCache.getAllAccountMap().values());
    }

    public @NonNull Map<String, WPublicAccountPO> getNeedFetchAccountMap() {
        return wPublicAccountCache.getNeedFetchAccountMap();
    }

    public @NonNull List<WPublicAccountPO> getNeedFetchAccounts() {
        return new ArrayList<>(wPublicAccountCache.getNeedFetchAccountMap().values());
    }
}
