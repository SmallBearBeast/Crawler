package com.bear.crawler.webmagic.provider;

import cn.hutool.core.thread.ThreadUtil;
import com.bear.crawler.webmagic.dao.WPublicAccountDao;
import com.bear.crawler.webmagic.mybatis.generator.po.WPublicAccountPO;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WPublicAccountProvider implements InitializingBean {

    @Autowired
    private WPublicAccountDao wPublicAccountDao;

    private final List<WPublicAccountPO> wPublicAccountPOS = new ArrayList<>();

    @Override
    public void afterPropertiesSet() {
        ThreadUtil.execute(() -> {
            wPublicAccountPOS.addAll(wPublicAccountDao.selectAllPublicAccounts());
        });
    }

    public List<WPublicAccountPO> getWPublicAccountPOS() {
        return wPublicAccountPOS;
    }
}
