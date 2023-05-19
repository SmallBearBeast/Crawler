package com.bear.crawler.webmagic.dao;

import com.bear.crawler.webmagic.mybatis.generator.mapper.WPublicAccountPOMapper;
import com.bear.crawler.webmagic.mybatis.generator.po.WPublicAccountPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WPublicAccountPOExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class WPublicAccountDao {

    @Autowired
    private WPublicAccountPOMapper wPublicAccountPOMapper;

    public List<WPublicAccountPO> selectNeedFetch() {
        try {
            WPublicAccountPOExample example = new WPublicAccountPOExample();
            example.createCriteria().andNeedFetchEqualTo(true);
            return wPublicAccountPOMapper.selectByExample(example);
        } catch (Exception e) {
            log.warn("Select the need fetch public account list failed, e = {}", e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<WPublicAccountPO> selectAll() {
        try {
            WPublicAccountPOExample example = new WPublicAccountPOExample();
            return wPublicAccountPOMapper.selectByExample(example);
        } catch (Exception e) {
            log.warn("Select the all public account list failed, e = {}", e.getMessage());
        }
        return new ArrayList<>();
    }

    public void insert(WPublicAccountPO accountPO) {
        try {
            wPublicAccountPOMapper.insert(accountPO);
        } catch (Exception e) {
            log.warn("Insert public account failed, e = {}", e.getMessage());
        }
    }

    public void updateByFakeId(WPublicAccountPO accountPO) {
        try {
            WPublicAccountPOExample example = new WPublicAccountPOExample();
            example.createCriteria().andFakeIdEqualTo(accountPO.getFakeId());
            wPublicAccountPOMapper.updateByExampleSelective(accountPO, example);
        } catch (Exception e) {
            log.warn("Update public account by fakeId failed, e = {}", e.getMessage());
        }
    }
}
