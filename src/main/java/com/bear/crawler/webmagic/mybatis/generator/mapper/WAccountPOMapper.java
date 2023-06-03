package com.bear.crawler.webmagic.mybatis.generator.mapper;

import com.bear.crawler.webmagic.mybatis.generator.po.WAccountPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WAccountPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WAccountPOMapper {
    long countByExample(WAccountPOExample example);

    int deleteByExample(WAccountPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WAccountPO record);

    int insertSelective(WAccountPO record);

    List<WAccountPO> selectByExample(WAccountPOExample example);

    WAccountPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WAccountPO record, @Param("example") WAccountPOExample example);

    int updateByExample(@Param("record") WAccountPO record, @Param("example") WAccountPOExample example);

    int updateByPrimaryKeySelective(WAccountPO record);

    int updateByPrimaryKey(WAccountPO record);
}