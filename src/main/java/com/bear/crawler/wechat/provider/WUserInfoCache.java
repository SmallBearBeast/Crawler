package com.bear.crawler.wechat.provider;

import com.bear.crawler.wechat.dao.WUserInfoDao;
import com.bear.crawler.wechat.mybatis.generator.po.WUserInfoPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@CacheConfig(cacheNames = "WUserInfoCache")
public class WUserInfoCache {

    @Autowired
    private WUserInfoDao wUserInfoDao;

    @Cacheable(key = "'allUserInfoMap'")
    public Map<String, WUserInfoPO> getAllUserInfoMap() {
        Map<String, WUserInfoPO> map = new ConcurrentHashMap<>();
        List<WUserInfoPO> userInfoPOS = wUserInfoDao.selectAll();
        for (WUserInfoPO userInfoPO : userInfoPOS) {
            map.put(userInfoPO.getOpenid(), userInfoPO);
        }
        return map;
    }

    @CachePut(key = "'allUserInfoMap'")
    public Map<String, WUserInfoPO> updateAllUserInfoMap(Map<String, WUserInfoPO> map, WUserInfoPO userInfoPO, boolean isRemove) {
        if (isRemove) {
            map.remove(userInfoPO.getOpenid());
        } else {
            map.put(userInfoPO.getOpenid(), userInfoPO);
        }
        return map;
    }

    @Cacheable(key = "'recentUserInfoMap'")
    public Map<String, WUserInfoPO> getResentUserInfoMap() {
        Map<String, WUserInfoPO> map = new ConcurrentHashMap<>();
        List<WUserInfoPO> userInfoPOS = wUserInfoDao.selectByRecent();
        for (WUserInfoPO userInfoPO : userInfoPOS) {
            map.put(userInfoPO.getOpenid(), userInfoPO);
        }
        return map;
    }

    @CachePut(key = "'recentUserInfoMap'")
    public Map<String, WUserInfoPO> updateRecentUserInfoMap(Map<String, WUserInfoPO> map, WUserInfoPO userInfoPO, boolean isRemove) {
        long createTime = userInfoPO.getCreateTime().getTime();
        long curTime = new Date().getTime();
        if (isRemove || curTime - createTime > 48 * 3600 * 1000L) {
            map.remove(userInfoPO.getOpenid());
        } else {
            map.put(userInfoPO.getOpenid(), userInfoPO);
        }
        return map;
    }

}
