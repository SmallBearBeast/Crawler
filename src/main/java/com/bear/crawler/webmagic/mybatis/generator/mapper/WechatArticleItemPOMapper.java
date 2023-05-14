package com.bear.crawler.webmagic.mybatis.generator.mapper;

import com.bear.crawler.webmagic.mybatis.generator.po.WechatArticleItemPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WechatArticleItemPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WechatArticleItemPOMapper {
    long countByExample(WechatArticleItemPOExample example);

    int deleteByExample(WechatArticleItemPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WechatArticleItemPO record);

    int insertSelective(WechatArticleItemPO record);

    List<WechatArticleItemPO> selectByExample(WechatArticleItemPOExample example);

    WechatArticleItemPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WechatArticleItemPO record, @Param("example") WechatArticleItemPOExample example);

    int updateByExample(@Param("record") WechatArticleItemPO record, @Param("example") WechatArticleItemPOExample example);

    int updateByPrimaryKeySelective(WechatArticleItemPO record);

    int updateByPrimaryKey(WechatArticleItemPO record);
}