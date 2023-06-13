package com.bear.crawler.webmagic.provider;

import cn.hutool.core.date.DateUtil;
import com.bear.crawler.webmagic.dao.WArticleDao;
import com.bear.crawler.webmagic.mybatis.generator.po.WArticleItemPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class WArticleProvider {

    @Autowired
    private WArticleCache wArticleCache;

    @Autowired
    private WArticleDao wArticleDao;

    @Autowired
    private WAccountProvider wAccountProvider;

    public void updateCache(WArticleItemPO articleItemPO, Boolean isRemove) {
        String fakeId = articleItemPO.getOfficialAccountFakeId();
        Map<String, WArticleItemPO> articleMap = wArticleCache.getArticleMap(fakeId);
        wArticleCache.updateArticleMap(articleItemPO, articleMap, isRemove);
        Map<String, WArticleItemPO> curDateArticleMap = wArticleCache.getTodayArticleMap(fakeId);
        // 只有当天的Article才更新缓存。
        if (DateUtil.isSameDay(articleItemPO.getUpdateTime(), new Date())) {
            wArticleCache.updateTodayArticleMap(articleItemPO, curDateArticleMap, isRemove);
        }
    }

    public boolean isInDB(WArticleItemPO articleItemPO) {
        String fakeId = articleItemPO.getOfficialAccountFakeId();
        Map<String, WArticleItemPO> map = wArticleCache.getArticleMap(fakeId);
        return map.containsKey(articleItemPO.getAid());
    }

    public @Nullable WArticleItemPO findByAid(String aid) {
        List<String> fakeIds = wAccountProvider.getNeedFetchAccountFakeIds();
        for (String fakeId : fakeIds) {
            Map<String, WArticleItemPO> articleItemPOMap = wArticleCache.getArticleMap(fakeId);
            if (articleItemPOMap.containsKey(aid)) {
                return articleItemPOMap.get(aid);
            }
        }
        WArticleItemPO articleItemPO = wArticleDao.selectByAid(aid);
        if (articleItemPO != null) {
            updateCache(articleItemPO, false);
        }
        return articleItemPO;
    }

    public long getLastLatestTime(String fakeId) {
        return wArticleCache.getLastLatestTime(fakeId);
    }

    public void setLastLatestTime(String fakeId, long time) {
        wArticleCache.setLastLatestTime(fakeId, time);
    }

    public List<WArticleItemPO> getTodayArticles(String fakeId) {
        return new ArrayList<>(wArticleCache.getTodayArticleMap(fakeId).values());
    }

    public List<WArticleItemPO> getArticles(String fakeId) {
        return new ArrayList<>(wArticleCache.getArticleMap(fakeId).values());
    }
}
