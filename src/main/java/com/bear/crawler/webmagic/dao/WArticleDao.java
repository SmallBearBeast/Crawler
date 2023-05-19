package com.bear.crawler.webmagic.dao;

import cn.hutool.core.collection.CollectionUtil;
import com.bear.crawler.webmagic.mybatis.custom.WArticleItemPOCustomMapper;
import com.bear.crawler.webmagic.mybatis.generator.mapper.WArticleItemPOMapper;
import com.bear.crawler.webmagic.mybatis.generator.po.WArticleItemPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WArticleItemPOExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

// TODO: 5/18/23 数组如何缓存
@Slf4j
@Repository
public class WArticleDao {

    @Autowired
    private WArticleItemPOMapper wArticleItemPOMapper;

    @Autowired
    private WArticleItemPOCustomMapper wArticleItemPOCustomMapper;

    public List<WArticleItemPO> selectAll() {
        try {
            WArticleItemPOExample example = new WArticleItemPOExample();
            return wArticleItemPOMapper.selectByExample(example);
        } catch (Exception e) {
            log.warn("Select the all article list failed, e = {}", e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<WArticleItemPO> selectByFakeId(String fakeId) {
        try {
            WArticleItemPOExample example = new WArticleItemPOExample();
            example.createCriteria().andOfficialAccountFakeIdEqualTo(fakeId);
            return wArticleItemPOMapper.selectByExample(example);
        } catch (Exception e) {
            log.warn("Select the article list by fakeId failed, fakeId = {}, e = {}", fakeId, e.getMessage());
        }
        return new ArrayList<>();
    }

    public void insert(WArticleItemPO articleItemPO) {
        try {
            wArticleItemPOMapper.insert(articleItemPO);
        } catch (Exception e) {
            log.warn("Insert the article failed, title = {}, e = {}", articleItemPO.getTitle(), e.getMessage());
        }
    }

    public void updateByAid(WArticleItemPO articleItemPO) {
        try {
            WArticleItemPOExample example = new WArticleItemPOExample();
            example.createCriteria().andAidEqualTo(articleItemPO.getAid());
            wArticleItemPOMapper.updateByExampleSelective(articleItemPO, example);
        } catch (Exception e) {
            log.warn("Update the article by aid failed, title = {}, e = {}", articleItemPO.getTitle(), e.getMessage());
        }
    }

    public void delete() {
        WArticleItemPOExample example = new WArticleItemPOExample();
        wArticleItemPOMapper.deleteByExample(example);
    }

    public WArticleItemPO selectByLatestDate(String fakeId) {
        List<WArticleItemPO> articleItemPOS = wArticleItemPOCustomMapper.selectLatest(fakeId);
        return CollectionUtil.getFirst(articleItemPOS);
    }
}
