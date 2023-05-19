package com.bear.crawler.webmagic.provider;

import cn.hutool.core.thread.ThreadUtil;
import com.bear.crawler.webmagic.dao.WPublicAccountDao;
import com.bear.crawler.webmagic.mybatis.generator.po.WPublicAccountPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class WPublicAccountProvider implements InitializingBean {

    @Autowired
    private WPublicAccountDao wPublicAccountDao;

    // TODO: 5/18/23 更新列表，监听变化
    private final List<WPublicAccountPO> wPublicAccountPOS = new ArrayList<>();
    private final List<WPublicAccountPO> wNeedFetchPublicAccountPOS = new ArrayList<>();

    // TODO: 5/17/23 还没加载完成case处理 加锁wait直到完成
    @Override
    public void afterPropertiesSet() {
        ThreadUtil.execute(() -> {
            wNeedFetchPublicAccountPOS.addAll(wPublicAccountDao.selectNeedFetch());
            wPublicAccountPOS.addAll(wPublicAccountDao.selectAll());
        });
    }

    public @NonNull List<WPublicAccountPO> getAll() {
        return wPublicAccountPOS;
    }

    public @NonNull List<WPublicAccountPO> getNeedFetch() {
        return wNeedFetchPublicAccountPOS;
    }

    public WPublicAccountPO findByFakeId(String fakeId) {
        for (WPublicAccountPO publicAccountPO : wPublicAccountPOS) {
            if (fakeId.equals(publicAccountPO.getFakeId())) {
                return publicAccountPO;
            }
        }
        return null;
    }
}
