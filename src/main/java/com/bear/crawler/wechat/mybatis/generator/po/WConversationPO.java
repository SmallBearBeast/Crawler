package com.bear.crawler.wechat.mybatis.generator.po;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class WConversationPO implements Serializable {
    private Integer id;

    private Integer replayId;

    @ApiModelProperty(value = "是否可以回复: 0不可以回复，1可以回复")
    private Integer canReply;

    private String nickname;

    private Integer isBlacked;

    private Integer isMissReply;

    private String msgitems;

    private Integer stage;

    private Integer unreadCnt;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReplayId() {
        return replayId;
    }

    public void setReplayId(Integer replayId) {
        this.replayId = replayId;
    }

    public Integer getCanReply() {
        return canReply;
    }

    public void setCanReply(Integer canReply) {
        this.canReply = canReply;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getIsBlacked() {
        return isBlacked;
    }

    public void setIsBlacked(Integer isBlacked) {
        this.isBlacked = isBlacked;
    }

    public Integer getIsMissReply() {
        return isMissReply;
    }

    public void setIsMissReply(Integer isMissReply) {
        this.isMissReply = isMissReply;
    }

    public String getMsgitems() {
        return msgitems;
    }

    public void setMsgitems(String msgitems) {
        this.msgitems = msgitems;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public Integer getUnreadCnt() {
        return unreadCnt;
    }

    public void setUnreadCnt(Integer unreadCnt) {
        this.unreadCnt = unreadCnt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", replayId=").append(replayId);
        sb.append(", canReply=").append(canReply);
        sb.append(", nickname=").append(nickname);
        sb.append(", isBlacked=").append(isBlacked);
        sb.append(", isMissReply=").append(isMissReply);
        sb.append(", msgitems=").append(msgitems);
        sb.append(", stage=").append(stage);
        sb.append(", unreadCnt=").append(unreadCnt);
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
        WConversationPO other = (WConversationPO) that;
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