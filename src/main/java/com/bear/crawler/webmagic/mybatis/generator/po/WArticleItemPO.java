package com.bear.crawler.webmagic.mybatis.generator.po;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class WArticleItemPO implements Serializable {
    @ApiModelProperty(value = "自定义文章id，唯一标识，主键")
    private Integer id;

    @ApiModelProperty(value = "微信文章id(id = appmsgid_itemidx)")
    private String aid;

    @ApiModelProperty(value = "微信文章系列id")
    private Long appmsgid;

    @ApiModelProperty(value = "微信文章封面图片url")
    private String cover;

    @ApiModelProperty(value = "微信文章创建日期")
    private Date createTime;

    @ApiModelProperty(value = "微信文章系列的位置")
    private Integer itemidx;

    @ApiModelProperty(value = "微信文章内容详情url")
    private String link;

    @ApiModelProperty(value = "微信文章标题")
    private String title;

    @ApiModelProperty(value = "微信文章发布日期")
    private Date updateTime;

    @ApiModelProperty(value = "微信公众号外键id")
    private Integer officialAccountId;

    @ApiModelProperty(value = "微信公众号原始id")
    private String officialAccountFakeId;

    @ApiModelProperty(value = "微信公众号名称")
    private String officialAccountTitle;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public Long getAppmsgid() {
        return appmsgid;
    }

    public void setAppmsgid(Long appmsgid) {
        this.appmsgid = appmsgid;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getItemidx() {
        return itemidx;
    }

    public void setItemidx(Integer itemidx) {
        this.itemidx = itemidx;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getOfficialAccountId() {
        return officialAccountId;
    }

    public void setOfficialAccountId(Integer officialAccountId) {
        this.officialAccountId = officialAccountId;
    }

    public String getOfficialAccountFakeId() {
        return officialAccountFakeId;
    }

    public void setOfficialAccountFakeId(String officialAccountFakeId) {
        this.officialAccountFakeId = officialAccountFakeId;
    }

    public String getOfficialAccountTitle() {
        return officialAccountTitle;
    }

    public void setOfficialAccountTitle(String officialAccountTitle) {
        this.officialAccountTitle = officialAccountTitle;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", aid=").append(aid);
        sb.append(", appmsgid=").append(appmsgid);
        sb.append(", cover=").append(cover);
        sb.append(", createTime=").append(createTime);
        sb.append(", itemidx=").append(itemidx);
        sb.append(", link=").append(link);
        sb.append(", title=").append(title);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", officialAccountId=").append(officialAccountId);
        sb.append(", officialAccountFakeId=").append(officialAccountFakeId);
        sb.append(", officialAccountTitle=").append(officialAccountTitle);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}