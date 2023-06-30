package com.bear.crawler.wechat.mybatis.generator.mapper;

import com.bear.crawler.wechat.mybatis.generator.po.WConversationPO;
import com.bear.crawler.wechat.mybatis.generator.po.WConversationPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WConversationPOMapper {
    long countByExample(WConversationPOExample example);

    int deleteByExample(WConversationPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WConversationPO record);

    int insertSelective(WConversationPO record);

    List<WConversationPO> selectByExample(WConversationPOExample example);

    WConversationPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WConversationPO record, @Param("example") WConversationPOExample example);

    int updateByExample(@Param("record") WConversationPO record, @Param("example") WConversationPOExample example);

    int updateByPrimaryKeySelective(WConversationPO record);

    int updateByPrimaryKey(WConversationPO record);
}