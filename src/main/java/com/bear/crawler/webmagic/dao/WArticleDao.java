package com.bear.crawler.webmagic.dao;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.bear.crawler.webmagic.AppConstant;
import com.bear.crawler.webmagic.mybatis.custom.WArticleItemPOCustomMapper;
import com.bear.crawler.webmagic.mybatis.generator.mapper.WArticleItemPOMapper;
import com.bear.crawler.webmagic.mybatis.generator.po.WArticleItemPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WArticleItemPOExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public List<WArticleItemPO> selectByFakeId(String fakeId, int limit) {
        try {
            WArticleItemPOExample example = new WArticleItemPOExample();
            example.createCriteria().andOfficialAccountFakeIdEqualTo(fakeId);
            if (limit > 0) {
                example.setLimit(limit);
            }
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

    public @Nullable WArticleItemPO selectLatest(String fakeId) {
        try {
            List<WArticleItemPO> articleItemPOS = wArticleItemPOCustomMapper.selectLatest(fakeId);
            return CollectionUtil.getFirst(articleItemPOS);
        } catch (Exception e) {
            log.warn("Select the lastest article by fakeId failed, fakeId = {}, e = {}", fakeId, e.getMessage());
        }
        return null;
    }

    public List<WArticleItemPO> selectByToday(String fakeId) {
        try {
            WArticleItemPOExample example = new WArticleItemPOExample();
            Date date = new Date();
            Date startDate = DateUtil.beginOfDay(date);
            Date endDate = DateUtil.endOfDay(date);
            example.createCriteria().andUpdateTimeBetween(startDate, endDate).andOfficialAccountFakeIdEqualTo(fakeId);
            return wArticleItemPOMapper.selectByExample(example);
        } catch (Exception e) {
            log.warn("Select the today article list by fakeId failed, fakeId = {}, e = {}", fakeId, e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<WArticleItemPO> selectMeByToday() {
        return selectByToday(AppConstant.MY_FAKE_ID);
    }

    public WArticleItemPO selectByMeTitle(String title) {
        try {
            WArticleItemPOExample example = new WArticleItemPOExample();
            example.createCriteria().andOfficialAccountFakeIdEqualTo(AppConstant.MY_FAKE_ID).andTitleLike("%" + title + "%");
            List<WArticleItemPO> articleItemPOS = wArticleItemPOMapper.selectByExample(example);
            return CollectionUtil.getFirst(articleItemPOS);
        } catch (Exception e) {
            log.warn("Select the my article by title failed, title = {}, e = {}", title, e.getMessage());
        }
        return null;
    }

    public List<WArticleItemPO> selectBefore7Week() {
        try {
            Date dateBefore7Week = new Date(new Date().getTime() - 7 * 24 * 3600 * 1000);
            Date formatDateBefore7Week = DateUtil.beginOfDay(dateBefore7Week);
            WArticleItemPOExample example = new WArticleItemPOExample();
            example.createCriteria().andUpdateTimeLessThan(formatDateBefore7Week);
            return wArticleItemPOMapper.selectByExample(example);
        } catch (Exception e) {
            log.warn("selectBefore7Week: select the articles before 7 weeks failed, e = {}", e.getMessage());
        }
        return new ArrayList<>();
    }

    public void deleteBefore7Week() {
        try {
            Date dateBefore7Week = new Date(new Date().getTime() - 7 * 24 * 3600 * 1000);
            Date formatDateBefore7Week = DateUtil.beginOfDay(dateBefore7Week);
            WArticleItemPOExample example = new WArticleItemPOExample();
            example.createCriteria().andUpdateTimeLessThan(formatDateBefore7Week);
            wArticleItemPOMapper.deleteByExample(example);
        } catch (Exception e) {
            log.warn("deleteBefore7Week: delete the articles before 7 weeks failed, e = {}", e.getMessage());
        }
    }
}
