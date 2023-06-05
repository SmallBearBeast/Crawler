package com.bear.crawler.webmagic.provider;

import com.bear.crawler.webmagic.dao.WArticleDao;
import com.bear.crawler.webmagic.mybatis.generator.po.WArticleItemPO;
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
public class WArticleCache {

    private static final int ARTICLE_INIT_SIZE = 200;

    @Autowired
    private WArticleDao wArticleDao;

    @Cacheable(key = "'lastLatestTime_'+#fakeId")
    public long getLastLatestTime(String fakeId) {
        WArticleItemPO articleItemPO = wArticleDao.selectLatest(fakeId);
        if (articleItemPO != null) {
            return articleItemPO.getUpdateTime().getTime() / 1000;
        }
        return 0L;
    }

    @CachePut(key = "'lastLatestTime_'+#fakeId")
    public long setLastLatestTime(String fakeId, long time) {
        return time;
    }

    @Cacheable(key = "'articleMap_'+#fakeId")
    public Map<String, WArticleItemPO> getArticleMap(String fakeId) {
        Map<String, WArticleItemPO> map = new ConcurrentHashMap<>();
        List<WArticleItemPO> articleItemPOS = wArticleDao.selectByFakeId(fakeId, ARTICLE_INIT_SIZE);
        for (WArticleItemPO articleItemPO : articleItemPOS) {
            map.put(articleItemPO.getAid(), articleItemPO);
        }
        return map;
    }

    @CachePut(key = "'articleMap_'+#articleItemPO.getOfficialAccountFakeId()")
    public Map<String, WArticleItemPO> updateArticleMap(WArticleItemPO articleItemPO, Map<String, WArticleItemPO> map) {
        map.put(articleItemPO.getAid(), articleItemPO);
        return map;
    }

    @Cacheable(key = "'curDateArticleMap_'+#fakeId")
    public Map<String, WArticleItemPO> getCurDateArticleMap(String fakeId) {
        Map<String, WArticleItemPO> map = new ConcurrentHashMap<>();
        List<WArticleItemPO> articleItemPOS = wArticleDao.selectByToday(fakeId);
        for (WArticleItemPO articleItemPO : articleItemPOS) {
            map.put(articleItemPO.getAid(), articleItemPO);
        }
        return map;
    }

    @CachePut(key = "'curDateArticleMap_'+#articleItemPO.getOfficialAccountFakeId()")
    public Map<String, WArticleItemPO> updateCurDateArticles(WArticleItemPO articleItemPO, Map<String, WArticleItemPO> map) {
        map.put(articleItemPO.getAid(), articleItemPO);
        return map;
    }
}
