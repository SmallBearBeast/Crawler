package com.bear.crawler.wechat.mybatis.generator.po;

import java.util.ArrayList;
import java.util.List;

public class WConversationPOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public WConversationPOExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
        offset = null;
        limit = null;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return offset;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andReplayIdIsNull() {
            addCriterion("replay_id is null");
            return (Criteria) this;
        }

        public Criteria andReplayIdIsNotNull() {
            addCriterion("replay_id is not null");
            return (Criteria) this;
        }

        public Criteria andReplayIdEqualTo(Integer value) {
            addCriterion("replay_id =", value, "replayId");
            return (Criteria) this;
        }

        public Criteria andReplayIdNotEqualTo(Integer value) {
            addCriterion("replay_id <>", value, "replayId");
            return (Criteria) this;
        }

        public Criteria andReplayIdGreaterThan(Integer value) {
            addCriterion("replay_id >", value, "replayId");
            return (Criteria) this;
        }

        public Criteria andReplayIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("replay_id >=", value, "replayId");
            return (Criteria) this;
        }

        public Criteria andReplayIdLessThan(Integer value) {
            addCriterion("replay_id <", value, "replayId");
            return (Criteria) this;
        }

        public Criteria andReplayIdLessThanOrEqualTo(Integer value) {
            addCriterion("replay_id <=", value, "replayId");
            return (Criteria) this;
        }

        public Criteria andReplayIdIn(List<Integer> values) {
            addCriterion("replay_id in", values, "replayId");
            return (Criteria) this;
        }

        public Criteria andReplayIdNotIn(List<Integer> values) {
            addCriterion("replay_id not in", values, "replayId");
            return (Criteria) this;
        }

        public Criteria andReplayIdBetween(Integer value1, Integer value2) {
            addCriterion("replay_id between", value1, value2, "replayId");
            return (Criteria) this;
        }

        public Criteria andReplayIdNotBetween(Integer value1, Integer value2) {
            addCriterion("replay_id not between", value1, value2, "replayId");
            return (Criteria) this;
        }

        public Criteria andCanReplyIsNull() {
            addCriterion("can_reply is null");
            return (Criteria) this;
        }

        public Criteria andCanReplyIsNotNull() {
            addCriterion("can_reply is not null");
            return (Criteria) this;
        }

        public Criteria andCanReplyEqualTo(Integer value) {
            addCriterion("can_reply =", value, "canReply");
            return (Criteria) this;
        }

        public Criteria andCanReplyNotEqualTo(Integer value) {
            addCriterion("can_reply <>", value, "canReply");
            return (Criteria) this;
        }

        public Criteria andCanReplyGreaterThan(Integer value) {
            addCriterion("can_reply >", value, "canReply");
            return (Criteria) this;
        }

        public Criteria andCanReplyGreaterThanOrEqualTo(Integer value) {
            addCriterion("can_reply >=", value, "canReply");
            return (Criteria) this;
        }

        public Criteria andCanReplyLessThan(Integer value) {
            addCriterion("can_reply <", value, "canReply");
            return (Criteria) this;
        }

        public Criteria andCanReplyLessThanOrEqualTo(Integer value) {
            addCriterion("can_reply <=", value, "canReply");
            return (Criteria) this;
        }

        public Criteria andCanReplyIn(List<Integer> values) {
            addCriterion("can_reply in", values, "canReply");
            return (Criteria) this;
        }

        public Criteria andCanReplyNotIn(List<Integer> values) {
            addCriterion("can_reply not in", values, "canReply");
            return (Criteria) this;
        }

        public Criteria andCanReplyBetween(Integer value1, Integer value2) {
            addCriterion("can_reply between", value1, value2, "canReply");
            return (Criteria) this;
        }

        public Criteria andCanReplyNotBetween(Integer value1, Integer value2) {
            addCriterion("can_reply not between", value1, value2, "canReply");
            return (Criteria) this;
        }

        public Criteria andNicknameIsNull() {
            addCriterion("nickname is null");
            return (Criteria) this;
        }

        public Criteria andNicknameIsNotNull() {
            addCriterion("nickname is not null");
            return (Criteria) this;
        }

        public Criteria andNicknameEqualTo(String value) {
            addCriterion("nickname =", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotEqualTo(String value) {
            addCriterion("nickname <>", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameGreaterThan(String value) {
            addCriterion("nickname >", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameGreaterThanOrEqualTo(String value) {
            addCriterion("nickname >=", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLessThan(String value) {
            addCriterion("nickname <", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLessThanOrEqualTo(String value) {
            addCriterion("nickname <=", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLike(String value) {
            addCriterion("nickname like", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotLike(String value) {
            addCriterion("nickname not like", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameIn(List<String> values) {
            addCriterion("nickname in", values, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotIn(List<String> values) {
            addCriterion("nickname not in", values, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameBetween(String value1, String value2) {
            addCriterion("nickname between", value1, value2, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotBetween(String value1, String value2) {
            addCriterion("nickname not between", value1, value2, "nickname");
            return (Criteria) this;
        }

        public Criteria andIsBlackedIsNull() {
            addCriterion("is_blacked is null");
            return (Criteria) this;
        }

        public Criteria andIsBlackedIsNotNull() {
            addCriterion("is_blacked is not null");
            return (Criteria) this;
        }

        public Criteria andIsBlackedEqualTo(Integer value) {
            addCriterion("is_blacked =", value, "isBlacked");
            return (Criteria) this;
        }

        public Criteria andIsBlackedNotEqualTo(Integer value) {
            addCriterion("is_blacked <>", value, "isBlacked");
            return (Criteria) this;
        }

        public Criteria andIsBlackedGreaterThan(Integer value) {
            addCriterion("is_blacked >", value, "isBlacked");
            return (Criteria) this;
        }

        public Criteria andIsBlackedGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_blacked >=", value, "isBlacked");
            return (Criteria) this;
        }

        public Criteria andIsBlackedLessThan(Integer value) {
            addCriterion("is_blacked <", value, "isBlacked");
            return (Criteria) this;
        }

        public Criteria andIsBlackedLessThanOrEqualTo(Integer value) {
            addCriterion("is_blacked <=", value, "isBlacked");
            return (Criteria) this;
        }

        public Criteria andIsBlackedIn(List<Integer> values) {
            addCriterion("is_blacked in", values, "isBlacked");
            return (Criteria) this;
        }

        public Criteria andIsBlackedNotIn(List<Integer> values) {
            addCriterion("is_blacked not in", values, "isBlacked");
            return (Criteria) this;
        }

        public Criteria andIsBlackedBetween(Integer value1, Integer value2) {
            addCriterion("is_blacked between", value1, value2, "isBlacked");
            return (Criteria) this;
        }

        public Criteria andIsBlackedNotBetween(Integer value1, Integer value2) {
            addCriterion("is_blacked not between", value1, value2, "isBlacked");
            return (Criteria) this;
        }

        public Criteria andIsMissReplyIsNull() {
            addCriterion("is_miss_reply is null");
            return (Criteria) this;
        }

        public Criteria andIsMissReplyIsNotNull() {
            addCriterion("is_miss_reply is not null");
            return (Criteria) this;
        }

        public Criteria andIsMissReplyEqualTo(Integer value) {
            addCriterion("is_miss_reply =", value, "isMissReply");
            return (Criteria) this;
        }

        public Criteria andIsMissReplyNotEqualTo(Integer value) {
            addCriterion("is_miss_reply <>", value, "isMissReply");
            return (Criteria) this;
        }

        public Criteria andIsMissReplyGreaterThan(Integer value) {
            addCriterion("is_miss_reply >", value, "isMissReply");
            return (Criteria) this;
        }

        public Criteria andIsMissReplyGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_miss_reply >=", value, "isMissReply");
            return (Criteria) this;
        }

        public Criteria andIsMissReplyLessThan(Integer value) {
            addCriterion("is_miss_reply <", value, "isMissReply");
            return (Criteria) this;
        }

        public Criteria andIsMissReplyLessThanOrEqualTo(Integer value) {
            addCriterion("is_miss_reply <=", value, "isMissReply");
            return (Criteria) this;
        }

        public Criteria andIsMissReplyIn(List<Integer> values) {
            addCriterion("is_miss_reply in", values, "isMissReply");
            return (Criteria) this;
        }

        public Criteria andIsMissReplyNotIn(List<Integer> values) {
            addCriterion("is_miss_reply not in", values, "isMissReply");
            return (Criteria) this;
        }

        public Criteria andIsMissReplyBetween(Integer value1, Integer value2) {
            addCriterion("is_miss_reply between", value1, value2, "isMissReply");
            return (Criteria) this;
        }

        public Criteria andIsMissReplyNotBetween(Integer value1, Integer value2) {
            addCriterion("is_miss_reply not between", value1, value2, "isMissReply");
            return (Criteria) this;
        }

        public Criteria andMsgitemsIsNull() {
            addCriterion("msgItems is null");
            return (Criteria) this;
        }

        public Criteria andMsgitemsIsNotNull() {
            addCriterion("msgItems is not null");
            return (Criteria) this;
        }

        public Criteria andMsgitemsEqualTo(String value) {
            addCriterion("msgItems =", value, "msgitems");
            return (Criteria) this;
        }

        public Criteria andMsgitemsNotEqualTo(String value) {
            addCriterion("msgItems <>", value, "msgitems");
            return (Criteria) this;
        }

        public Criteria andMsgitemsGreaterThan(String value) {
            addCriterion("msgItems >", value, "msgitems");
            return (Criteria) this;
        }

        public Criteria andMsgitemsGreaterThanOrEqualTo(String value) {
            addCriterion("msgItems >=", value, "msgitems");
            return (Criteria) this;
        }

        public Criteria andMsgitemsLessThan(String value) {
            addCriterion("msgItems <", value, "msgitems");
            return (Criteria) this;
        }

        public Criteria andMsgitemsLessThanOrEqualTo(String value) {
            addCriterion("msgItems <=", value, "msgitems");
            return (Criteria) this;
        }

        public Criteria andMsgitemsLike(String value) {
            addCriterion("msgItems like", value, "msgitems");
            return (Criteria) this;
        }

        public Criteria andMsgitemsNotLike(String value) {
            addCriterion("msgItems not like", value, "msgitems");
            return (Criteria) this;
        }

        public Criteria andMsgitemsIn(List<String> values) {
            addCriterion("msgItems in", values, "msgitems");
            return (Criteria) this;
        }

        public Criteria andMsgitemsNotIn(List<String> values) {
            addCriterion("msgItems not in", values, "msgitems");
            return (Criteria) this;
        }

        public Criteria andMsgitemsBetween(String value1, String value2) {
            addCriterion("msgItems between", value1, value2, "msgitems");
            return (Criteria) this;
        }

        public Criteria andMsgitemsNotBetween(String value1, String value2) {
            addCriterion("msgItems not between", value1, value2, "msgitems");
            return (Criteria) this;
        }

        public Criteria andStageIsNull() {
            addCriterion("stage is null");
            return (Criteria) this;
        }

        public Criteria andStageIsNotNull() {
            addCriterion("stage is not null");
            return (Criteria) this;
        }

        public Criteria andStageEqualTo(Integer value) {
            addCriterion("stage =", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotEqualTo(Integer value) {
            addCriterion("stage <>", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageGreaterThan(Integer value) {
            addCriterion("stage >", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageGreaterThanOrEqualTo(Integer value) {
            addCriterion("stage >=", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageLessThan(Integer value) {
            addCriterion("stage <", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageLessThanOrEqualTo(Integer value) {
            addCriterion("stage <=", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageIn(List<Integer> values) {
            addCriterion("stage in", values, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotIn(List<Integer> values) {
            addCriterion("stage not in", values, "stage");
            return (Criteria) this;
        }

        public Criteria andStageBetween(Integer value1, Integer value2) {
            addCriterion("stage between", value1, value2, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotBetween(Integer value1, Integer value2) {
            addCriterion("stage not between", value1, value2, "stage");
            return (Criteria) this;
        }

        public Criteria andUnreadCntIsNull() {
            addCriterion("unread_cnt is null");
            return (Criteria) this;
        }

        public Criteria andUnreadCntIsNotNull() {
            addCriterion("unread_cnt is not null");
            return (Criteria) this;
        }

        public Criteria andUnreadCntEqualTo(Integer value) {
            addCriterion("unread_cnt =", value, "unreadCnt");
            return (Criteria) this;
        }

        public Criteria andUnreadCntNotEqualTo(Integer value) {
            addCriterion("unread_cnt <>", value, "unreadCnt");
            return (Criteria) this;
        }

        public Criteria andUnreadCntGreaterThan(Integer value) {
            addCriterion("unread_cnt >", value, "unreadCnt");
            return (Criteria) this;
        }

        public Criteria andUnreadCntGreaterThanOrEqualTo(Integer value) {
            addCriterion("unread_cnt >=", value, "unreadCnt");
            return (Criteria) this;
        }

        public Criteria andUnreadCntLessThan(Integer value) {
            addCriterion("unread_cnt <", value, "unreadCnt");
            return (Criteria) this;
        }

        public Criteria andUnreadCntLessThanOrEqualTo(Integer value) {
            addCriterion("unread_cnt <=", value, "unreadCnt");
            return (Criteria) this;
        }

        public Criteria andUnreadCntIn(List<Integer> values) {
            addCriterion("unread_cnt in", values, "unreadCnt");
            return (Criteria) this;
        }

        public Criteria andUnreadCntNotIn(List<Integer> values) {
            addCriterion("unread_cnt not in", values, "unreadCnt");
            return (Criteria) this;
        }

        public Criteria andUnreadCntBetween(Integer value1, Integer value2) {
            addCriterion("unread_cnt between", value1, value2, "unreadCnt");
            return (Criteria) this;
        }

        public Criteria andUnreadCntNotBetween(Integer value1, Integer value2) {
            addCriterion("unread_cnt not between", value1, value2, "unreadCnt");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}