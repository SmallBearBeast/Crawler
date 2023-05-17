package com.bear.crawler.webmagic.mybatis.generator.mapper;

import com.bear.crawler.webmagic.mybatis.generator.po.WPublicAccountPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WPublicAccountPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WPublicAccountPOMapper {
    long countByExample(WPublicAccountPOExample example);

    int deleteByExample(WPublicAccountPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WPublicAccountPO record);

    int insertSelective(WPublicAccountPO record);

    List<WPublicAccountPO> selectByExample(WPublicAccountPOExample example);

    WPublicAccountPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WPublicAccountPO record, @Param("example") WPublicAccountPOExample example);

    int updateByExample(@Param("record") WPublicAccountPO record, @Param("example") WPublicAccountPOExample example);

    int updateByPrimaryKeySelective(WPublicAccountPO record);

    int updateByPrimaryKey(WPublicAccountPO record);
}