package com.bear.crawler.wechat.provider;

import com.bear.crawler.wechat.dao.WMsgItemDao;
import com.bear.crawler.wechat.mybatis.generator.po.WMsgItemPO;
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
@CacheConfig(cacheNames = "WMsgItemCache")
public class WMsgItemCache {

    @Autowired
    private WMsgItemDao wMsgItemDao;

    @Cacheable(key = "'allMsgItemMap'")
    public Map<String, WMsgItemPO> getAllMsgItemMap() {
        Map<String, WMsgItemPO> map = new ConcurrentHashMap<>();
        List<WMsgItemPO> msgItemPOS = wMsgItemDao.selectAll();
        for (WMsgItemPO msgItemPO : msgItemPOS) {
            map.put(msgItemPO.getFakeId(), msgItemPO);
        }
        return map;
    }

    @CachePut(key = "'allMsgItemMap'")
    public Map<String, WMsgItemPO> updateAllMsgItemMap(Map<String, WMsgItemPO> map, WMsgItemPO msgItemPO) {
        map.put(msgItemPO.getFakeId(), msgItemPO);
        return map;
    }

    @Cacheable("'recentMsgItemMap'")
    public Map<String, WMsgItemPO> getRecentMsgItemMap() {
        Map<String, WMsgItemPO> map = new ConcurrentHashMap<>();
        List<WMsgItemPO> msgItemPOS = wMsgItemDao.selectByRecent();
        for (WMsgItemPO msgItemPO : msgItemPOS) {
            map.put(msgItemPO.getFakeId(), msgItemPO);
        }
        return map;
    }

    @CachePut("'recentMsgItemMap'")
    public Map<String, WMsgItemPO> updateRecentMsgItemMap(Map<String, WMsgItemPO> map, WMsgItemPO msgItemPO) {
        long createTime = msgItemPO.getDateTime().getTime();
        long curTime = new Date().getTime();
        if (curTime - createTime > 48 * 3600 * 1000L) {
            map.remove(msgItemPO.getFakeId());
        } else {
            map.put(msgItemPO.getFakeId(), msgItemPO);
        }
        return map;
    }

    @Cacheable("'canReplayMsgItemMap'")
    public Map<String, WMsgItemPO> getCanReplayMsgItemMap() {
        Map<String, WMsgItemPO> map = new ConcurrentHashMap<>();
        List<WMsgItemPO> msgItemPOS = wMsgItemDao.selectByCanReplay();
        for (WMsgItemPO msgItemPO : msgItemPOS) {
            map.put(msgItemPO.getFakeId(), msgItemPO);
        }
        return map;
    }

    @CachePut("'canReplayMsgItemMap'")
    public Map<String, WMsgItemPO> updateCanReplayMsgItemMap(Map<String, WMsgItemPO> map, WMsgItemPO msgItemPO) {
        if (msgItemPO.getCanReplay()) {
            map.put(msgItemPO.getFakeId(), msgItemPO);
        } else {
            map.remove(msgItemPO.getFakeId());
        }
        return map;
    }
}
