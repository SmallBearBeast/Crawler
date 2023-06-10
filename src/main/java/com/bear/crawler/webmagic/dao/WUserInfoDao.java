package com.bear.crawler.webmagic.dao;

import com.bear.crawler.webmagic.mybatis.generator.mapper.WUserInfoPOMapper;
import com.bear.crawler.webmagic.mybatis.generator.po.WUserInfoPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WUserInfoPOExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Repository
public class WUserInfoDao {

    @Autowired
    private WUserInfoPOMapper wUserInfoPOMapper;

    public List<WUserInfoPO> selectAll() {
        try {
            WUserInfoPOExample example = new WUserInfoPOExample();
            return wUserInfoPOMapper.selectByExample(example);
        } catch (Exception e) {
            log.warn("Select all user infos failed, e = {}", e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<WUserInfoPO> selectByRecent() {
        try {
            WUserInfoPOExample example = new WUserInfoPOExample();
            Date date = new Date();
            Date dateBefore48Hour = new Date(date.getTime() - 48 * 60 * 60 * 1000);
            example.createCriteria().andCreateTimeGreaterThanOrEqualTo(dateBefore48Hour);
            return wUserInfoPOMapper.selectByExample(example);
        } catch (Exception e) {
            log.warn("Select recent user infos failed, e = {}", e.getMessage());
        }
        return new ArrayList<>();
    }

    public void insert(WUserInfoPO userInfoPO) {
        try {
            wUserInfoPOMapper.insert(userInfoPO);
        } catch (Exception e) {
            log.warn("Insert user info failed, e = {}", e.getMessage());
        }
    }

    public void updateByOpenId(WUserInfoPO userInfoPO) {
        try {
            WUserInfoPOExample example = new WUserInfoPOExample();
            example.createCriteria().andOpenidEqualTo(userInfoPO.getOpenid());
            wUserInfoPOMapper.updateByExample(userInfoPO, example);
        } catch (Exception e) {
            log.warn("Update user info by openId failed, e = {}", e.getMessage());
        }
    }

    public void deleteByOpenId(WUserInfoPO userInfoPO) {
        try {
            WUserInfoPOExample example = new WUserInfoPOExample();
            example.createCriteria().andOpenidEqualTo(userInfoPO.getOpenid());
            wUserInfoPOMapper.deleteByExample(example);
        } catch (Exception e) {
            log.warn("Delete user info by openId failed, e = {}", e.getMessage());
        }
    }
}
