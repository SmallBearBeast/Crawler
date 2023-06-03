package com.bear.crawler.webmagic.dao;

import com.bear.crawler.webmagic.mybatis.generator.mapper.WAccountPOMapper;
import com.bear.crawler.webmagic.mybatis.generator.po.WAccountPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WAccountPOExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class WAccountDao {

    @Autowired
    private WAccountPOMapper wAccountPOMapper;

    public List<WAccountPO> selectByNeedFetch() {
        try {
            WAccountPOExample example = new WAccountPOExample();
            example.createCriteria().andNeedFetchEqualTo(true);
            return wAccountPOMapper.selectByExample(example);
        } catch (Exception e) {
            log.warn("Select the need fetch public account list failed, e = {}", e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<WAccountPO> selectAll() {
        try {
            WAccountPOExample example = new WAccountPOExample();
            return wAccountPOMapper.selectByExample(example);
        } catch (Exception e) {
            log.warn("Select the all public account list failed, e = {}", e.getMessage());
        }
        return new ArrayList<>();
    }

    public void insert(WAccountPO accountPO) {
        try {
            wAccountPOMapper.insert(accountPO);
        } catch (Exception e) {
            log.warn("Insert public account failed, e = {}", e.getMessage());
        }
    }

    public void updateByFakeId(WAccountPO accountPO) {
        try {
            WAccountPOExample example = new WAccountPOExample();
            example.createCriteria().andFakeIdEqualTo(accountPO.getFakeId());
            wAccountPOMapper.updateByExampleSelective(accountPO, example);
        } catch (Exception e) {
            log.warn("Update public account by fakeId failed, e = {}", e.getMessage());
        }
    }
}
