package com.bear.crawler.webmagic.provider;

import cn.hutool.core.thread.ThreadUtil;
import com.bear.crawler.webmagic.dao.WPublicAccountDao;
import com.bear.crawler.webmagic.mybatis.generator.po.WPublicAccountPO;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WPublicAccountProvider implements InitializingBean {

    @Autowired
    private WPublicAccountDao wPublicAccountDao;

    private final List<WPublicAccountPO> wPublicAccountPOS = new ArrayList<>();
    private final List<WPublicAccountPO> wNeedFetchPublicAccountPOS = new ArrayList<>();

    // TODO: 5/17/23 还没加载完成case处理
    @Override
    public void afterPropertiesSet() {
        ThreadUtil.execute(() -> {
            wNeedFetchPublicAccountPOS.addAll(wPublicAccountDao.selectNeedFetchPublicAccounts());
            wPublicAccountPOS.addAll(wPublicAccountDao.selectAllPublicAccounts());
        });
    }

    public @NonNull List<WPublicAccountPO> getWPublicAccountPOS() {
        return wPublicAccountPOS;
    }

    public @NonNull List<WPublicAccountPO> getWNeedFetchPublicAccountPOS() {
        return wNeedFetchPublicAccountPOS;
    }
}
