package com.bear.crawler.wechat.mybatis.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.plugins.EqualsHashCodePlugin;

import java.util.List;

public class MyEqualsHashCodePlugin extends EqualsHashCodePlugin {
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return modelPrimaryKeyClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> columns = introspectedTable.getPrimaryKeyColumns();
        if(columns == null || columns.size() == 0){
            columns = introspectedTable.getAllColumns();
        }

        generateEquals(topLevelClass, columns, introspectedTable);
        generateHashCode(topLevelClass, columns, introspectedTable);

        return true;
    }

    @Override
    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return modelPrimaryKeyClassGenerated(topLevelClass, introspectedTable);
    }
}
