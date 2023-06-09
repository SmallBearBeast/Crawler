package com.bear.crawler.webmagic.mybatis.generator.po;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class WMsgItemPO implements Serializable {
    private Integer id;

    private Integer msgId;

    private Integer type;

    private String fakeId;

    private String nickname;

    private Date dateTime;

    private String content;

    private String source;

    private Integer msgStatus;

    private String title;

    private String msgDesc;

    private String contentUrl;

    private Integer showType;

    private Integer fileId;

    private Integer appSubType;

    private Integer hasReply;

    private String refuseReason;

    private String toUin;

    private Integer copyrightStatus;

    private Integer copyrightType;

    private Integer isVipMsg;

    private Integer isOftenRead;

    private Integer rewardMoneyCount;

    private Integer commentCount;

    private Integer paysubscribeWecoinAmount;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(Integer msgStatus) {
        this.msgStatus = msgStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsgDesc() {
        return msgDesc;
    }

    public void setMsgDesc(String msgDesc) {
        this.msgDesc = msgDesc;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public Integer getShowType() {
        return showType;
    }

    public void setShowType(Integer showType) {
        this.showType = showType;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getAppSubType() {
        return appSubType;
    }

    public void setAppSubType(Integer appSubType) {
        this.appSubType = appSubType;
    }

    public Integer getHasReply() {
        return hasReply;
    }

    public void setHasReply(Integer hasReply) {
        this.hasReply = hasReply;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public String getToUin() {
        return toUin;
    }

    public void setToUin(String toUin) {
        this.toUin = toUin;
    }

    public Integer getCopyrightStatus() {
        return copyrightStatus;
    }

    public void setCopyrightStatus(Integer copyrightStatus) {
        this.copyrightStatus = copyrightStatus;
    }

    public Integer getCopyrightType() {
        return copyrightType;
    }

    public void setCopyrightType(Integer copyrightType) {
        this.copyrightType = copyrightType;
    }

    public Integer getIsVipMsg() {
        return isVipMsg;
    }

    public void setIsVipMsg(Integer isVipMsg) {
        this.isVipMsg = isVipMsg;
    }

    public Integer getIsOftenRead() {
        return isOftenRead;
    }

    public void setIsOftenRead(Integer isOftenRead) {
        this.isOftenRead = isOftenRead;
    }

    public Integer getRewardMoneyCount() {
        return rewardMoneyCount;
    }

    public void setRewardMoneyCount(Integer rewardMoneyCount) {
        this.rewardMoneyCount = rewardMoneyCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getPaysubscribeWecoinAmount() {
        return paysubscribeWecoinAmount;
    }

    public void setPaysubscribeWecoinAmount(Integer paysubscribeWecoinAmount) {
        this.paysubscribeWecoinAmount = paysubscribeWecoinAmount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", msgId=").append(msgId);
        sb.append(", type=").append(type);
        sb.append(", fakeId=").append(fakeId);
        sb.append(", nickname=").append(nickname);
        sb.append(", dateTime=").append(dateTime);
        sb.append(", content=").append(content);
        sb.append(", source=").append(source);
        sb.append(", msgStatus=").append(msgStatus);
        sb.append(", title=").append(title);
        sb.append(", msgDesc=").append(msgDesc);
        sb.append(", contentUrl=").append(contentUrl);
        sb.append(", showType=").append(showType);
        sb.append(", fileId=").append(fileId);
        sb.append(", appSubType=").append(appSubType);
        sb.append(", hasReply=").append(hasReply);
        sb.append(", refuseReason=").append(refuseReason);
        sb.append(", toUin=").append(toUin);
        sb.append(", copyrightStatus=").append(copyrightStatus);
        sb.append(", copyrightType=").append(copyrightType);
        sb.append(", isVipMsg=").append(isVipMsg);
        sb.append(", isOftenRead=").append(isOftenRead);
        sb.append(", rewardMoneyCount=").append(rewardMoneyCount);
        sb.append(", commentCount=").append(commentCount);
        sb.append(", paysubscribeWecoinAmount=").append(paysubscribeWecoinAmount);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        WMsgItemPO other = (WMsgItemPO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }
}