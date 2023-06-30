package com.bear.crawler.wechat.mybatis.custom;

import com.bear.crawler.wechat.mybatis.generator.po.WArticleItemPO;

import java.util.Date;
import java.util.List;

public interface WArticleItemPOCustomMapper {
    List<WArticleItemPO> selectLatest(String fakeId);

    Date selectLatest_1(String fakeId);
}