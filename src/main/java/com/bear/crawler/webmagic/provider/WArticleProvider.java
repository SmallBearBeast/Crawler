package com.bear.crawler.webmagic.provider;

import cn.hutool.core.date.DateUtil;
import com.bear.crawler.webmagic.mybatis.generator.po.WArticleItemPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void updateCache(WArticleItemPO articleItemPO) {
        String fakeId = articleItemPO.getOfficialAccountFakeId();
        Map<String, WArticleItemPO> articleMap = wArticleCache.getArticleMap(fakeId);
        wArticleCache.updateArticleMap(articleItemPO, articleMap);
        Map<String, WArticleItemPO> curDateArticleMap = wArticleCache.getCurDateArticleMap(fakeId);
        // 只有当天的Article才更新缓存。
        if (DateUtil.isSameDay(articleItemPO.getUpdateTime(), new Date())) {
            wArticleCache.updateCurDateArticles(articleItemPO, curDateArticleMap);
        }
    }

    public boolean isInDB(WArticleItemPO articleItemPO) {
        String fakeId = articleItemPO.getOfficialAccountFakeId();
        Map<String, WArticleItemPO> map = wArticleCache.getArticleMap(fakeId);
        return map.containsKey(articleItemPO.getAid());
    }

    public long getLastLatestTime(String fakeId) {
        return wArticleCache.getLastLatestTime(fakeId);
    }

    public void setLastLatestTime(String fakeId, long time) {
        wArticleCache.setLastLatestTime(fakeId, time);
    }

    public List<WArticleItemPO> getCurDateArticles(String fakeId) {
        return new ArrayList<>(wArticleCache.getCurDateArticleMap(fakeId).values());
    }

    public List<WArticleItemPO> getArticles(String fakeId) {
        return new ArrayList<>(wArticleCache.getArticleMap(fakeId).values());
    }
}
