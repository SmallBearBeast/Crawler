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

    public List<WPublicAccountPO> selectNeedFetchPublicAccounts() {
        try {
            WPublicAccountPOExample example = new WPublicAccountPOExample();
            example.createCriteria().andNeedFetchEqualTo(true);
            return wPublicAccountPOMapper.selectByExample(example);
        } catch (Exception e) {
            log.warn("Select the need fetch public account list failed, e = {}", e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<WPublicAccountPO> selectAllPublicAccounts() {
        try {
            WPublicAccountPOExample example = new WPublicAccountPOExample();
            return wPublicAccountPOMapper.selectByExample(example);
        } catch (Exception e) {
            log.warn("Select the all public account list failed, e = {}", e.getMessage());
        }
        return new ArrayList<>();
    }
}
