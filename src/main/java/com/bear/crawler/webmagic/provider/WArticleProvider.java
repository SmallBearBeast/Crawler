package com.bear.crawler.webmagic.provider;

import cn.hutool.core.thread.ThreadUtil;
import com.bear.crawler.webmagic.dao.WArticleDao;
import com.bear.crawler.webmagic.mybatis.generator.mapper.WArticleItemPOMapper;
import com.bear.crawler.webmagic.mybatis.generator.po.WArticleItemPO;
import com.bear.crawler.webmagic.pojo.dto.WArticleItemDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class WArticleProvider implements InitializingBean {

    @Autowired
    private WArticleDao wArticleDao;

    private final ConcurrentHashMap<String, WArticleItemPO> articleItemPOMap = new ConcurrentHashMap<>();

    private WArticleItemPO latestArticleItemPO;

    // TODO: 5/17/23 还没加载完成case处理 加锁wait直到完成
    // TODO: 5/18/23 考虑用缓存cache实现
    @Override
    public void afterPropertiesSet() {
        ThreadUtil.execute(() -> {
            articleItemPOMap.clear();
            List<WArticleItemPO> articleItemPOS = wArticleDao.selectAll();
            for (WArticleItemPO articleItemPO : articleItemPOS) {
                articleItemPOMap.put(articleItemPO.getAid(), articleItemPO);
            }
        });
    }

    public boolean isInArticleDB(WArticleItemPO articleItemPO) {
        return articleItemPOMap.containsKey(articleItemPO.getAid());
    }

    public void put(WArticleItemPO articleItemPO) {
        articleItemPOMap.put(articleItemPO.getAid(), articleItemPO);
    }
}
