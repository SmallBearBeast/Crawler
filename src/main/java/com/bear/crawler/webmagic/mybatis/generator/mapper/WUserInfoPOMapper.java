package com.bear.crawler.webmagic.mybatis.generator.mapper;

import com.bear.crawler.webmagic.mybatis.generator.po.WUserInfoPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WUserInfoPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WUserInfoPOMapper {
    long countByExample(WUserInfoPOExample example);

    int deleteByExample(WUserInfoPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WUserInfoPO record);

    int insertSelective(WUserInfoPO record);

    List<WUserInfoPO> selectByExample(WUserInfoPOExample example);

    WUserInfoPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WUserInfoPO record, @Param("example") WUserInfoPOExample example);

    int updateByExample(@Param("record") WUserInfoPO record, @Param("example") WUserInfoPOExample example);

    int updateByPrimaryKeySelective(WUserInfoPO record);

    int updateByPrimaryKey(WUserInfoPO record);
}