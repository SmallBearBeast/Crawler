package com.bear.crawler.webmagic.provider;

import com.bear.crawler.webmagic.dao.WPublicAccountDao;
import com.bear.crawler.webmagic.mybatis.generator.po.WPublicAccountPO;
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
@CacheConfig(cacheNames = "WArticleCache")
public class WPublicAccountCache {

    @Autowired
    private WPublicAccountDao wPublicAccountDao;

    @Cacheable(key = "'allAccountsMap'")
    public Map<String, WPublicAccountPO> getAllAccountMap() {
        Map<String, WPublicAccountPO> map = new ConcurrentHashMap<>();
        List<WPublicAccountPO> accountPOS = wPublicAccountDao.selectAll();
        for (WPublicAccountPO accountPO : accountPOS) {
            map.put(accountPO.getFakeId(), accountPO);
        }
        return map;
    }

    @CachePut(key = "'allAccountsMap'")
    public Map<String, WPublicAccountPO> updateAllAccountMap(WPublicAccountPO accountPO, Map<String, WPublicAccountPO> map) {
        map.put(accountPO.getFakeId(), accountPO);
        return map;
    }

    @Cacheable(key = "'needFetchAccountMap'")
    public Map<String, WPublicAccountPO> getNeedFetchAccountMap() {
        Map<String, WPublicAccountPO> map = new ConcurrentHashMap<>();
        List<WPublicAccountPO> accountPOS = wPublicAccountDao.selectByNeedFetch();
        for (WPublicAccountPO accountPO : accountPOS) {
            map.put(accountPO.getFakeId(), accountPO);
        }
        return map;
    }

    @CachePut(key = "'needFetchAccountMap'")
    public Map<String, WPublicAccountPO> updateAccountMapByNeedFetch(WPublicAccountPO accountPO, Map<String, WPublicAccountPO> map) {
        map.put(accountPO.getFakeId(), accountPO);
        return map;
    }
}
