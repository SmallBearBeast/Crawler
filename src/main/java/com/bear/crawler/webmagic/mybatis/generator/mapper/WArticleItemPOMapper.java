package com.bear.crawler.webmagic.mybatis.generator.mapper;

import com.bear.crawler.webmagic.mybatis.generator.po.WArticleItemPO;
import com.bear.crawler.webmagic.mybatis.generator.po.WArticleItemPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WArticleItemPOMapper {
    long countByExample(WArticleItemPOExample example);

    int deleteByExample(WArticleItemPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WArticleItemPO record);

    int insertSelective(WArticleItemPO record);

    List<WArticleItemPO> selectByExample(WArticleItemPOExample example);

    WArticleItemPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WArticleItemPO record, @Param("example") WArticleItemPOExample example);

    int updateByExample(@Param("record") WArticleItemPO record, @Param("example") WArticleItemPOExample example);

    int updateByPrimaryKeySelective(WArticleItemPO record);

    int updateByPrimaryKey(WArticleItemPO record);
}