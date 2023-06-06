package com.bear.crawler.webmagic.provider;

import com.bear.crawler.webmagic.mybatis.generator.po.WAccountPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class WAccountProvider {

    @Autowired
    private WAccountCache wAccountCache;

    public void updateCache(WAccountPO accountPO) {
        Map<String, WAccountPO> allAccountMap = wAccountCache.getAllAccountMap();
        wAccountCache.updateAllAccountMap(accountPO, allAccountMap);
    }

    public void updateNeedToFetchCache(WAccountPO accountPO) {
        Map<String, WAccountPO> needFetchAccountMap = wAccountCache.getNeedFetchAccountMap();
        wAccountCache.updateAccountMapByNeedFetch(accountPO, needFetchAccountMap);
    }

    public boolean isInAccountDB(WAccountPO accountPO) {
        return findByFakeId(accountPO.getFakeId()) != null;
    }

    public @Nullable WAccountPO findByFakeId(String fakeId) {
        Map<String, WAccountPO> allAccountMap = wAccountCache.getAllAccountMap();
        return allAccountMap.get(fakeId);
    }

    public @Nullable WAccountPO findByNickname(String nickname) {
        Map<String, WAccountPO> allAccountMap = wAccountCache.getAllAccountMap();
        for (WAccountPO accountPO : allAccountMap.values()) {
            if (nickname.equalsIgnoreCase(accountPO.getNickname())) {
                return accountPO;
            }
        }
        return null;
    }

    public @NonNull Map<String, WAccountPO> getAllAccountMap() {
        return wAccountCache.getAllAccountMap();
    }

    public @NonNull List<WAccountPO> getAllAccounts() {
        return new ArrayList<>(wAccountCache.getAllAccountMap().values());
    }

    public @NonNull Map<String, WAccountPO> getNeedFetchAccountMap() {
        return wAccountCache.getNeedFetchAccountMap();
    }

    public @NonNull List<WAccountPO> getNeedFetchAccounts() {
        return new ArrayList<>(wAccountCache.getNeedFetchAccountMap().values());
    }

    public @NonNull List<String> getNeedFetchAccountFakeIds() {
        return wAccountCache.getNeedFetchAccountMap().values().stream()
                .map(WAccountPO::getFakeId).collect(Collectors.toList());
    }
}
