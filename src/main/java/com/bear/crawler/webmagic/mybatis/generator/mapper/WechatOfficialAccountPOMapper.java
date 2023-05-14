package com.bear.crawler.webmagic.mybatis.generator.mapper;

import com.bear.crawler.webmagic.mybatis.generator.po.WechatOfficialAccountPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WechatOfficialAccountPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WechatOfficialAccountPOMapper {
    long countByExample(WechatOfficialAccountPOExample example);

    int deleteByExample(WechatOfficialAccountPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WechatOfficialAccountPO record);

    int insertSelective(WechatOfficialAccountPO record);

    List<WechatOfficialAccountPO> selectByExample(WechatOfficialAccountPOExample example);

    WechatOfficialAccountPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WechatOfficialAccountPO record, @Param("example") WechatOfficialAccountPOExample example);

    int updateByExample(@Param("record") WechatOfficialAccountPO record, @Param("example") WechatOfficialAccountPOExample example);

    int updateByPrimaryKeySelective(WechatOfficialAccountPO record);

    int updateByPrimaryKey(WechatOfficialAccountPO record);
}