package com.bear.crawler.webmagic.provider;

import com.bear.crawler.webmagic.mybatis.generator.po.WUserInfoPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class WUserInfoProvider {

    @Autowired
    private WUserInfoCache wUserInfoCache;

    public void updateCache(WUserInfoPO userInfoPO) {

    }

    public boolean isInDB(WUserInfoPO userInfoPO) {
        return findByOpenId(userInfoPO.getOpenid()) != null;
    }

    public @Nullable WUserInfoPO findByOpenId(String openId) {
        Map<String, WUserInfoPO> allUserInfoMap = wUserInfoCache.getAllUserInfoMap();
        return allUserInfoMap.get(openId);
    }
}
