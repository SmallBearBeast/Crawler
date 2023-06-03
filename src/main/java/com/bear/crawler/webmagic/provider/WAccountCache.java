package com.bear.crawler.webmagic.provider;

import com.bear.crawler.webmagic.dao.WAccountDao;
import com.bear.crawler.webmagic.mybatis.generator.po.WAccountPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@CacheConfig(cacheNames = "WAccountCache")
public class WAccountCache {

    @Autowired
    private WAccountDao wAccountDao;

    @Cacheable(key = "'allAccountsMap'")
    public Map<String, WAccountPO> getAllAccountMap() {
        Map<String, WAccountPO> map = new ConcurrentHashMap<>();
        List<WAccountPO> accountPOS = wAccountDao.selectAll();
        for (WAccountPO accountPO : accountPOS) {
            map.put(accountPO.getFakeId(), accountPO);
        }
        return map;
    }

    @CachePut(key = "'allAccountsMap'")
    public Map<String, WAccountPO> updateAllAccountMap(WAccountPO accountPO, Map<String, WAccountPO> map) {
        map.put(accountPO.getFakeId(), accountPO);
        return map;
    }

    @Cacheable(key = "'needFetchAccountMap'")
    public Map<String, WAccountPO> getNeedFetchAccountMap() {
        Map<String, WAccountPO> map = new ConcurrentHashMap<>();
        List<WAccountPO> accountPOS = wAccountDao.selectByNeedFetch();
        for (WAccountPO accountPO : accountPOS) {
            map.put(accountPO.getFakeId(), accountPO);
        }
        return map;
    }

    @CachePut(key = "'needFetchAccountMap'")
    public Map<String, WAccountPO> updateAccountMapByNeedFetch(WAccountPO accountPO, Map<String, WAccountPO> map) {
        if (accountPO.getNeedFetch()) {
            map.put(accountPO.getFakeId(), accountPO);
        } else {
            map.remove(accountPO.getFakeId());
        }
        return map;
    }
}
