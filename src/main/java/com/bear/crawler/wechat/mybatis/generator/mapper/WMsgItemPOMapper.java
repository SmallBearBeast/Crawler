package com.bear.crawler.wechat.mybatis.generator.mapper;

import com.bear.crawler.wechat.mybatis.generator.po.WMsgItemPO;
import com.bear.crawler.wechat.mybatis.generator.po.WMsgItemPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WMsgItemPOMapper {
    long countByExample(WMsgItemPOExample example);

    int deleteByExample(WMsgItemPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WMsgItemPO record);

    int insertSelective(WMsgItemPO record);

    List<WMsgItemPO> selectByExample(WMsgItemPOExample example);

    WMsgItemPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WMsgItemPO record, @Param("example") WMsgItemPOExample example);

    int updateByExample(@Param("record") WMsgItemPO record, @Param("example") WMsgItemPOExample example);

    int updateByPrimaryKeySelective(WMsgItemPO record);

    int updateByPrimaryKey(WMsgItemPO record);
}