package com.bear.crawler.gov;

public class GovConstant {

    public static final String SMALL_TYPE = "smallType";

    public static final String PAGE_NUM = "pageNum";

    public static final String PAGE_SIZE = "pageSize";

    // 获取个人办事所有的主题分类
    public static final String ALL_CATEGORY_URL = "https://zwfw.fujian.gov.cn:732/cms-business/apasService/categorizationMatters?type=1&bigtype=1&areacode=350626";

    // 获取个人办事某个主题下面的所有办事项目
    public static final String ITEM_BY_CATEGORY_URL = "https://zwfw.fujian.gov.cn:732/cms-business/apasService/getThemeApasServiceByParams?userType=1&areaCode=350626&bigType=01&smallType=085&pageNum=1&pageSize=10&isOnline=0&serviceName=";

    // 获取个人办事所有的部门分类
    public static final String ALL_DEPARTMENT_URL = "https://zwfw.fujian.gov.cn:732/cms-business/apasService/categorizationMattersByDept?type=1&deptUnid=19665A73924115B2B5CA771D50186D8E";

    // 某个办事项目内容详情页
    public static final String ITEM_DETAIL_URL = "https://zwfw.fujian.gov.cn/person-todo/todo-fingerpost?unid=8B1DE78317F17F37B9B4313791336161&infoType=1";
}
