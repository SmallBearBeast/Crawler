package com.bear.crawler.webmagic.dao;

import cn.hutool.core.collection.CollectionUtil;
import com.bear.crawler.webmagic.mybatis.generator.mapper.WMsgItemPOMapper;
import com.bear.crawler.webmagic.mybatis.generator.po.WMsgItemPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WMsgItemPOExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Repository
public class WMsgItemDao {

    @Autowired
    private WMsgItemPOMapper wMsgItemPOMapper;

    public @Nullable WMsgItemPO selectByFakeId(String fakeId) {
        try {
            WMsgItemPOExample example = new WMsgItemPOExample();
            example.setLimit(1);
            example.setOrderByClause("order by dateTime desc");
            List<WMsgItemPO> msgItemPOS = wMsgItemPOMapper.selectByExample(example);
            return CollectionUtil.getFirst(msgItemPOS);
        } catch (Exception e) {
            log.warn("Select the msg item by fakeId failed, e = {}", e.getMessage());
        }
        return null;
    }

    public List<WMsgItemPO> selectAll() {
        try {
            WMsgItemPOExample example = new WMsgItemPOExample();
            return wMsgItemPOMapper.selectByExample(example);
        } catch (Exception e) {
            log.warn("Select all msg items failed, e = {}", e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<WMsgItemPO> selectByRecent() {
        try {
            WMsgItemPOExample example = new WMsgItemPOExample();
            Date date = new Date();
            Date dateBefore48Hour = new Date(date.getTime() - 48 * 60 * 60 * 1000);
            example.createCriteria().andDateTimeGreaterThanOrEqualTo(dateBefore48Hour);
            return wMsgItemPOMapper.selectByExample(example);
        } catch (Exception e) {
            log.warn("Select recent msg items failed, e = {}", e.getMessage());
        }
        return new ArrayList<>();
    }

    public void insert(WMsgItemPO msgItemPO) {
        try {
            wMsgItemPOMapper.insert(msgItemPO);
        } catch (Exception e) {
            log.warn("Insert msg item failed, e = {}", e.getMessage());
        }
    }

    public void updateByFakeId(WMsgItemPO msgItemPO) {
        try {
            WMsgItemPOExample example = new WMsgItemPOExample();
            example.createCriteria().andFakeIdEqualTo(msgItemPO.getFakeId());
            wMsgItemPOMapper.updateByExample(msgItemPO, example);
        } catch (Exception e) {
            log.warn("Insert msg item failed, e = {}", e.getMessage());
        }
    }
}
