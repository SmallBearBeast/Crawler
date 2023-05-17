package com.bear.crawler.webmagic.mybatis.generator.po;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class WPublicAccountPO implements Serializable {
    @ApiModelProperty(value = "自定义公众号id，唯一标识，主键")
    private Integer id;

    @ApiModelProperty(value = "公众号别名")
    private String alias;

    @ApiModelProperty(value = "可能是公众号原始id")
    private String fakeId;

    @ApiModelProperty(value = "公众号昵称")
    private String nickname;

    @ApiModelProperty(value = "公众号头像url")
    private String headImg;

    @ApiModelProperty(value = "公众号介绍说明")
    private String signature;

    @ApiModelProperty(value = "是否需要抓取改公众号")
    private Boolean needFetch;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getFakeId() {
        return fakeId;
    }

    public void setFakeId(String fakeId) {
        this.fakeId = fakeId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Boolean getNeedFetch() {
        return needFetch;
    }

    public void setNeedFetch(Boolean needFetch) {
        this.needFetch = needFetch;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", alias=").append(alias);
        sb.append(", fakeId=").append(fakeId);
        sb.append(", nickname=").append(nickname);
        sb.append(", headImg=").append(headImg);
        sb.append(", signature=").append(signature);
        sb.append(", needFetch=").append(needFetch);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}