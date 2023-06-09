package com.bear.crawler.webmagic.mybatis.generator.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WMsgItemPOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public WMsgItemPOExample() {
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

        public Criteria andMsgIdIsNull() {
            addCriterion("msg_id is null");
            return (Criteria) this;
        }

        public Criteria andMsgIdIsNotNull() {
            addCriterion("msg_id is not null");
            return (Criteria) this;
        }

        public Criteria andMsgIdEqualTo(Integer value) {
            addCriterion("msg_id =", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdNotEqualTo(Integer value) {
            addCriterion("msg_id <>", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdGreaterThan(Integer value) {
            addCriterion("msg_id >", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("msg_id >=", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdLessThan(Integer value) {
            addCriterion("msg_id <", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdLessThanOrEqualTo(Integer value) {
            addCriterion("msg_id <=", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdIn(List<Integer> values) {
            addCriterion("msg_id in", values, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdNotIn(List<Integer> values) {
            addCriterion("msg_id not in", values, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdBetween(Integer value1, Integer value2) {
            addCriterion("msg_id between", value1, value2, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdNotBetween(Integer value1, Integer value2) {
            addCriterion("msg_id not between", value1, value2, "msgId");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andFakeIdIsNull() {
            addCriterion("fake_id is null");
            return (Criteria) this;
        }

        public Criteria andFakeIdIsNotNull() {
            addCriterion("fake_id is not null");
            return (Criteria) this;
        }

        public Criteria andFakeIdEqualTo(String value) {
            addCriterion("fake_id =", value, "fakeId");
            return (Criteria) this;
        }

        public Criteria andFakeIdNotEqualTo(String value) {
            addCriterion("fake_id <>", value, "fakeId");
            return (Criteria) this;
        }

        public Criteria andFakeIdGreaterThan(String value) {
            addCriterion("fake_id >", value, "fakeId");
            return (Criteria) this;
        }

        public Criteria andFakeIdGreaterThanOrEqualTo(String value) {
            addCriterion("fake_id >=", value, "fakeId");
            return (Criteria) this;
        }

        public Criteria andFakeIdLessThan(String value) {
            addCriterion("fake_id <", value, "fakeId");
            return (Criteria) this;
        }

        public Criteria andFakeIdLessThanOrEqualTo(String value) {
            addCriterion("fake_id <=", value, "fakeId");
            return (Criteria) this;
        }

        public Criteria andFakeIdLike(String value) {
            addCriterion("fake_id like", value, "fakeId");
            return (Criteria) this;
        }

        public Criteria andFakeIdNotLike(String value) {
            addCriterion("fake_id not like", value, "fakeId");
            return (Criteria) this;
        }

        public Criteria andFakeIdIn(List<String> values) {
            addCriterion("fake_id in", values, "fakeId");
            return (Criteria) this;
        }

        public Criteria andFakeIdNotIn(List<String> values) {
            addCriterion("fake_id not in", values, "fakeId");
            return (Criteria) this;
        }

        public Criteria andFakeIdBetween(String value1, String value2) {
            addCriterion("fake_id between", value1, value2, "fakeId");
            return (Criteria) this;
        }

        public Criteria andFakeIdNotBetween(String value1, String value2) {
            addCriterion("fake_id not between", value1, value2, "fakeId");
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

        public Criteria andDateTimeIsNull() {
            addCriterion("date_time is null");
            return (Criteria) this;
        }

        public Criteria andDateTimeIsNotNull() {
            addCriterion("date_time is not null");
            return (Criteria) this;
        }

        public Criteria andDateTimeEqualTo(Date value) {
            addCriterion("date_time =", value, "dateTime");
            return (Criteria) this;
        }

        public Criteria andDateTimeNotEqualTo(Date value) {
            addCriterion("date_time <>", value, "dateTime");
            return (Criteria) this;
        }

        public Criteria andDateTimeGreaterThan(Date value) {
            addCriterion("date_time >", value, "dateTime");
            return (Criteria) this;
        }

        public Criteria andDateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("date_time >=", value, "dateTime");
            return (Criteria) this;
        }

        public Criteria andDateTimeLessThan(Date value) {
            addCriterion("date_time <", value, "dateTime");
            return (Criteria) this;
        }

        public Criteria andDateTimeLessThanOrEqualTo(Date value) {
            addCriterion("date_time <=", value, "dateTime");
            return (Criteria) this;
        }

        public Criteria andDateTimeIn(List<Date> values) {
            addCriterion("date_time in", values, "dateTime");
            return (Criteria) this;
        }

        public Criteria andDateTimeNotIn(List<Date> values) {
            addCriterion("date_time not in", values, "dateTime");
            return (Criteria) this;
        }

        public Criteria andDateTimeBetween(Date value1, Date value2) {
            addCriterion("date_time between", value1, value2, "dateTime");
            return (Criteria) this;
        }

        public Criteria andDateTimeNotBetween(Date value1, Date value2) {
            addCriterion("date_time not between", value1, value2, "dateTime");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andSourceIsNull() {
            addCriterion("source is null");
            return (Criteria) this;
        }

        public Criteria andSourceIsNotNull() {
            addCriterion("source is not null");
            return (Criteria) this;
        }

        public Criteria andSourceEqualTo(String value) {
            addCriterion("source =", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotEqualTo(String value) {
            addCriterion("source <>", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThan(String value) {
            addCriterion("source >", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThanOrEqualTo(String value) {
            addCriterion("source >=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThan(String value) {
            addCriterion("source <", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThanOrEqualTo(String value) {
            addCriterion("source <=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLike(String value) {
            addCriterion("source like", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotLike(String value) {
            addCriterion("source not like", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceIn(List<String> values) {
            addCriterion("source in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotIn(List<String> values) {
            addCriterion("source not in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceBetween(String value1, String value2) {
            addCriterion("source between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotBetween(String value1, String value2) {
            addCriterion("source not between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andMsgStatusIsNull() {
            addCriterion("msg_status is null");
            return (Criteria) this;
        }

        public Criteria andMsgStatusIsNotNull() {
            addCriterion("msg_status is not null");
            return (Criteria) this;
        }

        public Criteria andMsgStatusEqualTo(Integer value) {
            addCriterion("msg_status =", value, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusNotEqualTo(Integer value) {
            addCriterion("msg_status <>", value, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusGreaterThan(Integer value) {
            addCriterion("msg_status >", value, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("msg_status >=", value, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusLessThan(Integer value) {
            addCriterion("msg_status <", value, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusLessThanOrEqualTo(Integer value) {
            addCriterion("msg_status <=", value, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusIn(List<Integer> values) {
            addCriterion("msg_status in", values, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusNotIn(List<Integer> values) {
            addCriterion("msg_status not in", values, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusBetween(Integer value1, Integer value2) {
            addCriterion("msg_status between", value1, value2, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andMsgStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("msg_status not between", value1, value2, "msgStatus");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andMsgDescIsNull() {
            addCriterion("msg_desc is null");
            return (Criteria) this;
        }

        public Criteria andMsgDescIsNotNull() {
            addCriterion("msg_desc is not null");
            return (Criteria) this;
        }

        public Criteria andMsgDescEqualTo(String value) {
            addCriterion("msg_desc =", value, "msgDesc");
            return (Criteria) this;
        }

        public Criteria andMsgDescNotEqualTo(String value) {
            addCriterion("msg_desc <>", value, "msgDesc");
            return (Criteria) this;
        }

        public Criteria andMsgDescGreaterThan(String value) {
            addCriterion("msg_desc >", value, "msgDesc");
            return (Criteria) this;
        }

        public Criteria andMsgDescGreaterThanOrEqualTo(String value) {
            addCriterion("msg_desc >=", value, "msgDesc");
            return (Criteria) this;
        }

        public Criteria andMsgDescLessThan(String value) {
            addCriterion("msg_desc <", value, "msgDesc");
            return (Criteria) this;
        }

        public Criteria andMsgDescLessThanOrEqualTo(String value) {
            addCriterion("msg_desc <=", value, "msgDesc");
            return (Criteria) this;
        }

        public Criteria andMsgDescLike(String value) {
            addCriterion("msg_desc like", value, "msgDesc");
            return (Criteria) this;
        }

        public Criteria andMsgDescNotLike(String value) {
            addCriterion("msg_desc not like", value, "msgDesc");
            return (Criteria) this;
        }

        public Criteria andMsgDescIn(List<String> values) {
            addCriterion("msg_desc in", values, "msgDesc");
            return (Criteria) this;
        }

        public Criteria andMsgDescNotIn(List<String> values) {
            addCriterion("msg_desc not in", values, "msgDesc");
            return (Criteria) this;
        }

        public Criteria andMsgDescBetween(String value1, String value2) {
            addCriterion("msg_desc between", value1, value2, "msgDesc");
            return (Criteria) this;
        }

        public Criteria andMsgDescNotBetween(String value1, String value2) {
            addCriterion("msg_desc not between", value1, value2, "msgDesc");
            return (Criteria) this;
        }

        public Criteria andContentUrlIsNull() {
            addCriterion("content_url is null");
            return (Criteria) this;
        }

        public Criteria andContentUrlIsNotNull() {
            addCriterion("content_url is not null");
            return (Criteria) this;
        }

        public Criteria andContentUrlEqualTo(String value) {
            addCriterion("content_url =", value, "contentUrl");
            return (Criteria) this;
        }

        public Criteria andContentUrlNotEqualTo(String value) {
            addCriterion("content_url <>", value, "contentUrl");
            return (Criteria) this;
        }

        public Criteria andContentUrlGreaterThan(String value) {
            addCriterion("content_url >", value, "contentUrl");
            return (Criteria) this;
        }

        public Criteria andContentUrlGreaterThanOrEqualTo(String value) {
            addCriterion("content_url >=", value, "contentUrl");
            return (Criteria) this;
        }

        public Criteria andContentUrlLessThan(String value) {
            addCriterion("content_url <", value, "contentUrl");
            return (Criteria) this;
        }

        public Criteria andContentUrlLessThanOrEqualTo(String value) {
            addCriterion("content_url <=", value, "contentUrl");
            return (Criteria) this;
        }

        public Criteria andContentUrlLike(String value) {
            addCriterion("content_url like", value, "contentUrl");
            return (Criteria) this;
        }

        public Criteria andContentUrlNotLike(String value) {
            addCriterion("content_url not like", value, "contentUrl");
            return (Criteria) this;
        }

        public Criteria andContentUrlIn(List<String> values) {
            addCriterion("content_url in", values, "contentUrl");
            return (Criteria) this;
        }

        public Criteria andContentUrlNotIn(List<String> values) {
            addCriterion("content_url not in", values, "contentUrl");
            return (Criteria) this;
        }

        public Criteria andContentUrlBetween(String value1, String value2) {
            addCriterion("content_url between", value1, value2, "contentUrl");
            return (Criteria) this;
        }

        public Criteria andContentUrlNotBetween(String value1, String value2) {
            addCriterion("content_url not between", value1, value2, "contentUrl");
            return (Criteria) this;
        }

        public Criteria andShowTypeIsNull() {
            addCriterion("show_type is null");
            return (Criteria) this;
        }

        public Criteria andShowTypeIsNotNull() {
            addCriterion("show_type is not null");
            return (Criteria) this;
        }

        public Criteria andShowTypeEqualTo(Integer value) {
            addCriterion("show_type =", value, "showType");
            return (Criteria) this;
        }

        public Criteria andShowTypeNotEqualTo(Integer value) {
            addCriterion("show_type <>", value, "showType");
            return (Criteria) this;
        }

        public Criteria andShowTypeGreaterThan(Integer value) {
            addCriterion("show_type >", value, "showType");
            return (Criteria) this;
        }

        public Criteria andShowTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("show_type >=", value, "showType");
            return (Criteria) this;
        }

        public Criteria andShowTypeLessThan(Integer value) {
            addCriterion("show_type <", value, "showType");
            return (Criteria) this;
        }

        public Criteria andShowTypeLessThanOrEqualTo(Integer value) {
            addCriterion("show_type <=", value, "showType");
            return (Criteria) this;
        }

        public Criteria andShowTypeIn(List<Integer> values) {
            addCriterion("show_type in", values, "showType");
            return (Criteria) this;
        }

        public Criteria andShowTypeNotIn(List<Integer> values) {
            addCriterion("show_type not in", values, "showType");
            return (Criteria) this;
        }

        public Criteria andShowTypeBetween(Integer value1, Integer value2) {
            addCriterion("show_type between", value1, value2, "showType");
            return (Criteria) this;
        }

        public Criteria andShowTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("show_type not between", value1, value2, "showType");
            return (Criteria) this;
        }

        public Criteria andFileIdIsNull() {
            addCriterion("file_id is null");
            return (Criteria) this;
        }

        public Criteria andFileIdIsNotNull() {
            addCriterion("file_id is not null");
            return (Criteria) this;
        }

        public Criteria andFileIdEqualTo(Integer value) {
            addCriterion("file_id =", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotEqualTo(Integer value) {
            addCriterion("file_id <>", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdGreaterThan(Integer value) {
            addCriterion("file_id >", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("file_id >=", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLessThan(Integer value) {
            addCriterion("file_id <", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLessThanOrEqualTo(Integer value) {
            addCriterion("file_id <=", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdIn(List<Integer> values) {
            addCriterion("file_id in", values, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotIn(List<Integer> values) {
            addCriterion("file_id not in", values, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdBetween(Integer value1, Integer value2) {
            addCriterion("file_id between", value1, value2, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotBetween(Integer value1, Integer value2) {
            addCriterion("file_id not between", value1, value2, "fileId");
            return (Criteria) this;
        }

        public Criteria andAppSubTypeIsNull() {
            addCriterion("app_sub_type is null");
            return (Criteria) this;
        }

        public Criteria andAppSubTypeIsNotNull() {
            addCriterion("app_sub_type is not null");
            return (Criteria) this;
        }

        public Criteria andAppSubTypeEqualTo(Integer value) {
            addCriterion("app_sub_type =", value, "appSubType");
            return (Criteria) this;
        }

        public Criteria andAppSubTypeNotEqualTo(Integer value) {
            addCriterion("app_sub_type <>", value, "appSubType");
            return (Criteria) this;
        }

        public Criteria andAppSubTypeGreaterThan(Integer value) {
            addCriterion("app_sub_type >", value, "appSubType");
            return (Criteria) this;
        }

        public Criteria andAppSubTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("app_sub_type >=", value, "appSubType");
            return (Criteria) this;
        }

        public Criteria andAppSubTypeLessThan(Integer value) {
            addCriterion("app_sub_type <", value, "appSubType");
            return (Criteria) this;
        }

        public Criteria andAppSubTypeLessThanOrEqualTo(Integer value) {
            addCriterion("app_sub_type <=", value, "appSubType");
            return (Criteria) this;
        }

        public Criteria andAppSubTypeIn(List<Integer> values) {
            addCriterion("app_sub_type in", values, "appSubType");
            return (Criteria) this;
        }

        public Criteria andAppSubTypeNotIn(List<Integer> values) {
            addCriterion("app_sub_type not in", values, "appSubType");
            return (Criteria) this;
        }

        public Criteria andAppSubTypeBetween(Integer value1, Integer value2) {
            addCriterion("app_sub_type between", value1, value2, "appSubType");
            return (Criteria) this;
        }

        public Criteria andAppSubTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("app_sub_type not between", value1, value2, "appSubType");
            return (Criteria) this;
        }

        public Criteria andHasReplyIsNull() {
            addCriterion("has_reply is null");
            return (Criteria) this;
        }

        public Criteria andHasReplyIsNotNull() {
            addCriterion("has_reply is not null");
            return (Criteria) this;
        }

        public Criteria andHasReplyEqualTo(Integer value) {
            addCriterion("has_reply =", value, "hasReply");
            return (Criteria) this;
        }

        public Criteria andHasReplyNotEqualTo(Integer value) {
            addCriterion("has_reply <>", value, "hasReply");
            return (Criteria) this;
        }

        public Criteria andHasReplyGreaterThan(Integer value) {
            addCriterion("has_reply >", value, "hasReply");
            return (Criteria) this;
        }

        public Criteria andHasReplyGreaterThanOrEqualTo(Integer value) {
            addCriterion("has_reply >=", value, "hasReply");
            return (Criteria) this;
        }

        public Criteria andHasReplyLessThan(Integer value) {
            addCriterion("has_reply <", value, "hasReply");
            return (Criteria) this;
        }

        public Criteria andHasReplyLessThanOrEqualTo(Integer value) {
            addCriterion("has_reply <=", value, "hasReply");
            return (Criteria) this;
        }

        public Criteria andHasReplyIn(List<Integer> values) {
            addCriterion("has_reply in", values, "hasReply");
            return (Criteria) this;
        }

        public Criteria andHasReplyNotIn(List<Integer> values) {
            addCriterion("has_reply not in", values, "hasReply");
            return (Criteria) this;
        }

        public Criteria andHasReplyBetween(Integer value1, Integer value2) {
            addCriterion("has_reply between", value1, value2, "hasReply");
            return (Criteria) this;
        }

        public Criteria andHasReplyNotBetween(Integer value1, Integer value2) {
            addCriterion("has_reply not between", value1, value2, "hasReply");
            return (Criteria) this;
        }

        public Criteria andRefuseReasonIsNull() {
            addCriterion("refuse_reason is null");
            return (Criteria) this;
        }

        public Criteria andRefuseReasonIsNotNull() {
            addCriterion("refuse_reason is not null");
            return (Criteria) this;
        }

        public Criteria andRefuseReasonEqualTo(String value) {
            addCriterion("refuse_reason =", value, "refuseReason");
            return (Criteria) this;
        }

        public Criteria andRefuseReasonNotEqualTo(String value) {
            addCriterion("refuse_reason <>", value, "refuseReason");
            return (Criteria) this;
        }

        public Criteria andRefuseReasonGreaterThan(String value) {
            addCriterion("refuse_reason >", value, "refuseReason");
            return (Criteria) this;
        }

        public Criteria andRefuseReasonGreaterThanOrEqualTo(String value) {
            addCriterion("refuse_reason >=", value, "refuseReason");
            return (Criteria) this;
        }

        public Criteria andRefuseReasonLessThan(String value) {
            addCriterion("refuse_reason <", value, "refuseReason");
            return (Criteria) this;
        }

        public Criteria andRefuseReasonLessThanOrEqualTo(String value) {
            addCriterion("refuse_reason <=", value, "refuseReason");
            return (Criteria) this;
        }

        public Criteria andRefuseReasonLike(String value) {
            addCriterion("refuse_reason like", value, "refuseReason");
            return (Criteria) this;
        }

        public Criteria andRefuseReasonNotLike(String value) {
            addCriterion("refuse_reason not like", value, "refuseReason");
            return (Criteria) this;
        }

        public Criteria andRefuseReasonIn(List<String> values) {
            addCriterion("refuse_reason in", values, "refuseReason");
            return (Criteria) this;
        }

        public Criteria andRefuseReasonNotIn(List<String> values) {
            addCriterion("refuse_reason not in", values, "refuseReason");
            return (Criteria) this;
        }

        public Criteria andRefuseReasonBetween(String value1, String value2) {
            addCriterion("refuse_reason between", value1, value2, "refuseReason");
            return (Criteria) this;
        }

        public Criteria andRefuseReasonNotBetween(String value1, String value2) {
            addCriterion("refuse_reason not between", value1, value2, "refuseReason");
            return (Criteria) this;
        }

        public Criteria andToUinIsNull() {
            addCriterion("to_uin is null");
            return (Criteria) this;
        }

        public Criteria andToUinIsNotNull() {
            addCriterion("to_uin is not null");
            return (Criteria) this;
        }

        public Criteria andToUinEqualTo(String value) {
            addCriterion("to_uin =", value, "toUin");
            return (Criteria) this;
        }

        public Criteria andToUinNotEqualTo(String value) {
            addCriterion("to_uin <>", value, "toUin");
            return (Criteria) this;
        }

        public Criteria andToUinGreaterThan(String value) {
            addCriterion("to_uin >", value, "toUin");
            return (Criteria) this;
        }

        public Criteria andToUinGreaterThanOrEqualTo(String value) {
            addCriterion("to_uin >=", value, "toUin");
            return (Criteria) this;
        }

        public Criteria andToUinLessThan(String value) {
            addCriterion("to_uin <", value, "toUin");
            return (Criteria) this;
        }

        public Criteria andToUinLessThanOrEqualTo(String value) {
            addCriterion("to_uin <=", value, "toUin");
            return (Criteria) this;
        }

        public Criteria andToUinLike(String value) {
            addCriterion("to_uin like", value, "toUin");
            return (Criteria) this;
        }

        public Criteria andToUinNotLike(String value) {
            addCriterion("to_uin not like", value, "toUin");
            return (Criteria) this;
        }

        public Criteria andToUinIn(List<String> values) {
            addCriterion("to_uin in", values, "toUin");
            return (Criteria) this;
        }

        public Criteria andToUinNotIn(List<String> values) {
            addCriterion("to_uin not in", values, "toUin");
            return (Criteria) this;
        }

        public Criteria andToUinBetween(String value1, String value2) {
            addCriterion("to_uin between", value1, value2, "toUin");
            return (Criteria) this;
        }

        public Criteria andToUinNotBetween(String value1, String value2) {
            addCriterion("to_uin not between", value1, value2, "toUin");
            return (Criteria) this;
        }

        public Criteria andCopyrightStatusIsNull() {
            addCriterion("copyright_status is null");
            return (Criteria) this;
        }

        public Criteria andCopyrightStatusIsNotNull() {
            addCriterion("copyright_status is not null");
            return (Criteria) this;
        }

        public Criteria andCopyrightStatusEqualTo(Integer value) {
            addCriterion("copyright_status =", value, "copyrightStatus");
            return (Criteria) this;
        }

        public Criteria andCopyrightStatusNotEqualTo(Integer value) {
            addCriterion("copyright_status <>", value, "copyrightStatus");
            return (Criteria) this;
        }

        public Criteria andCopyrightStatusGreaterThan(Integer value) {
            addCriterion("copyright_status >", value, "copyrightStatus");
            return (Criteria) this;
        }

        public Criteria andCopyrightStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("copyright_status >=", value, "copyrightStatus");
            return (Criteria) this;
        }

        public Criteria andCopyrightStatusLessThan(Integer value) {
            addCriterion("copyright_status <", value, "copyrightStatus");
            return (Criteria) this;
        }

        public Criteria andCopyrightStatusLessThanOrEqualTo(Integer value) {
            addCriterion("copyright_status <=", value, "copyrightStatus");
            return (Criteria) this;
        }

        public Criteria andCopyrightStatusIn(List<Integer> values) {
            addCriterion("copyright_status in", values, "copyrightStatus");
            return (Criteria) this;
        }

        public Criteria andCopyrightStatusNotIn(List<Integer> values) {
            addCriterion("copyright_status not in", values, "copyrightStatus");
            return (Criteria) this;
        }

        public Criteria andCopyrightStatusBetween(Integer value1, Integer value2) {
            addCriterion("copyright_status between", value1, value2, "copyrightStatus");
            return (Criteria) this;
        }

        public Criteria andCopyrightStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("copyright_status not between", value1, value2, "copyrightStatus");
            return (Criteria) this;
        }

        public Criteria andCopyrightTypeIsNull() {
            addCriterion("copyright_type is null");
            return (Criteria) this;
        }

        public Criteria andCopyrightTypeIsNotNull() {
            addCriterion("copyright_type is not null");
            return (Criteria) this;
        }

        public Criteria andCopyrightTypeEqualTo(Integer value) {
            addCriterion("copyright_type =", value, "copyrightType");
            return (Criteria) this;
        }

        public Criteria andCopyrightTypeNotEqualTo(Integer value) {
            addCriterion("copyright_type <>", value, "copyrightType");
            return (Criteria) this;
        }

        public Criteria andCopyrightTypeGreaterThan(Integer value) {
            addCriterion("copyright_type >", value, "copyrightType");
            return (Criteria) this;
        }

        public Criteria andCopyrightTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("copyright_type >=", value, "copyrightType");
            return (Criteria) this;
        }

        public Criteria andCopyrightTypeLessThan(Integer value) {
            addCriterion("copyright_type <", value, "copyrightType");
            return (Criteria) this;
        }

        public Criteria andCopyrightTypeLessThanOrEqualTo(Integer value) {
            addCriterion("copyright_type <=", value, "copyrightType");
            return (Criteria) this;
        }

        public Criteria andCopyrightTypeIn(List<Integer> values) {
            addCriterion("copyright_type in", values, "copyrightType");
            return (Criteria) this;
        }

        public Criteria andCopyrightTypeNotIn(List<Integer> values) {
            addCriterion("copyright_type not in", values, "copyrightType");
            return (Criteria) this;
        }

        public Criteria andCopyrightTypeBetween(Integer value1, Integer value2) {
            addCriterion("copyright_type between", value1, value2, "copyrightType");
            return (Criteria) this;
        }

        public Criteria andCopyrightTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("copyright_type not between", value1, value2, "copyrightType");
            return (Criteria) this;
        }

        public Criteria andIsVipMsgIsNull() {
            addCriterion("is_vip_msg is null");
            return (Criteria) this;
        }

        public Criteria andIsVipMsgIsNotNull() {
            addCriterion("is_vip_msg is not null");
            return (Criteria) this;
        }

        public Criteria andIsVipMsgEqualTo(Integer value) {
            addCriterion("is_vip_msg =", value, "isVipMsg");
            return (Criteria) this;
        }

        public Criteria andIsVipMsgNotEqualTo(Integer value) {
            addCriterion("is_vip_msg <>", value, "isVipMsg");
            return (Criteria) this;
        }

        public Criteria andIsVipMsgGreaterThan(Integer value) {
            addCriterion("is_vip_msg >", value, "isVipMsg");
            return (Criteria) this;
        }

        public Criteria andIsVipMsgGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_vip_msg >=", value, "isVipMsg");
            return (Criteria) this;
        }

        public Criteria andIsVipMsgLessThan(Integer value) {
            addCriterion("is_vip_msg <", value, "isVipMsg");
            return (Criteria) this;
        }

        public Criteria andIsVipMsgLessThanOrEqualTo(Integer value) {
            addCriterion("is_vip_msg <=", value, "isVipMsg");
            return (Criteria) this;
        }

        public Criteria andIsVipMsgIn(List<Integer> values) {
            addCriterion("is_vip_msg in", values, "isVipMsg");
            return (Criteria) this;
        }

        public Criteria andIsVipMsgNotIn(List<Integer> values) {
            addCriterion("is_vip_msg not in", values, "isVipMsg");
            return (Criteria) this;
        }

        public Criteria andIsVipMsgBetween(Integer value1, Integer value2) {
            addCriterion("is_vip_msg between", value1, value2, "isVipMsg");
            return (Criteria) this;
        }

        public Criteria andIsVipMsgNotBetween(Integer value1, Integer value2) {
            addCriterion("is_vip_msg not between", value1, value2, "isVipMsg");
            return (Criteria) this;
        }

        public Criteria andIsOftenReadIsNull() {
            addCriterion("is_often_read is null");
            return (Criteria) this;
        }

        public Criteria andIsOftenReadIsNotNull() {
            addCriterion("is_often_read is not null");
            return (Criteria) this;
        }

        public Criteria andIsOftenReadEqualTo(Integer value) {
            addCriterion("is_often_read =", value, "isOftenRead");
            return (Criteria) this;
        }

        public Criteria andIsOftenReadNotEqualTo(Integer value) {
            addCriterion("is_often_read <>", value, "isOftenRead");
            return (Criteria) this;
        }

        public Criteria andIsOftenReadGreaterThan(Integer value) {
            addCriterion("is_often_read >", value, "isOftenRead");
            return (Criteria) this;
        }

        public Criteria andIsOftenReadGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_often_read >=", value, "isOftenRead");
            return (Criteria) this;
        }

        public Criteria andIsOftenReadLessThan(Integer value) {
            addCriterion("is_often_read <", value, "isOftenRead");
            return (Criteria) this;
        }

        public Criteria andIsOftenReadLessThanOrEqualTo(Integer value) {
            addCriterion("is_often_read <=", value, "isOftenRead");
            return (Criteria) this;
        }

        public Criteria andIsOftenReadIn(List<Integer> values) {
            addCriterion("is_often_read in", values, "isOftenRead");
            return (Criteria) this;
        }

        public Criteria andIsOftenReadNotIn(List<Integer> values) {
            addCriterion("is_often_read not in", values, "isOftenRead");
            return (Criteria) this;
        }

        public Criteria andIsOftenReadBetween(Integer value1, Integer value2) {
            addCriterion("is_often_read between", value1, value2, "isOftenRead");
            return (Criteria) this;
        }

        public Criteria andIsOftenReadNotBetween(Integer value1, Integer value2) {
            addCriterion("is_often_read not between", value1, value2, "isOftenRead");
            return (Criteria) this;
        }

        public Criteria andRewardMoneyCountIsNull() {
            addCriterion("reward_money_count is null");
            return (Criteria) this;
        }

        public Criteria andRewardMoneyCountIsNotNull() {
            addCriterion("reward_money_count is not null");
            return (Criteria) this;
        }

        public Criteria andRewardMoneyCountEqualTo(Integer value) {
            addCriterion("reward_money_count =", value, "rewardMoneyCount");
            return (Criteria) this;
        }

        public Criteria andRewardMoneyCountNotEqualTo(Integer value) {
            addCriterion("reward_money_count <>", value, "rewardMoneyCount");
            return (Criteria) this;
        }

        public Criteria andRewardMoneyCountGreaterThan(Integer value) {
            addCriterion("reward_money_count >", value, "rewardMoneyCount");
            return (Criteria) this;
        }

        public Criteria andRewardMoneyCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("reward_money_count >=", value, "rewardMoneyCount");
            return (Criteria) this;
        }

        public Criteria andRewardMoneyCountLessThan(Integer value) {
            addCriterion("reward_money_count <", value, "rewardMoneyCount");
            return (Criteria) this;
        }

        public Criteria andRewardMoneyCountLessThanOrEqualTo(Integer value) {
            addCriterion("reward_money_count <=", value, "rewardMoneyCount");
            return (Criteria) this;
        }

        public Criteria andRewardMoneyCountIn(List<Integer> values) {
            addCriterion("reward_money_count in", values, "rewardMoneyCount");
            return (Criteria) this;
        }

        public Criteria andRewardMoneyCountNotIn(List<Integer> values) {
            addCriterion("reward_money_count not in", values, "rewardMoneyCount");
            return (Criteria) this;
        }

        public Criteria andRewardMoneyCountBetween(Integer value1, Integer value2) {
            addCriterion("reward_money_count between", value1, value2, "rewardMoneyCount");
            return (Criteria) this;
        }

        public Criteria andRewardMoneyCountNotBetween(Integer value1, Integer value2) {
            addCriterion("reward_money_count not between", value1, value2, "rewardMoneyCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountIsNull() {
            addCriterion("comment_count is null");
            return (Criteria) this;
        }

        public Criteria andCommentCountIsNotNull() {
            addCriterion("comment_count is not null");
            return (Criteria) this;
        }

        public Criteria andCommentCountEqualTo(Integer value) {
            addCriterion("comment_count =", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountNotEqualTo(Integer value) {
            addCriterion("comment_count <>", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountGreaterThan(Integer value) {
            addCriterion("comment_count >", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("comment_count >=", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountLessThan(Integer value) {
            addCriterion("comment_count <", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountLessThanOrEqualTo(Integer value) {
            addCriterion("comment_count <=", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountIn(List<Integer> values) {
            addCriterion("comment_count in", values, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountNotIn(List<Integer> values) {
            addCriterion("comment_count not in", values, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountBetween(Integer value1, Integer value2) {
            addCriterion("comment_count between", value1, value2, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountNotBetween(Integer value1, Integer value2) {
            addCriterion("comment_count not between", value1, value2, "commentCount");
            return (Criteria) this;
        }

        public Criteria andPaysubscribeWecoinAmountIsNull() {
            addCriterion("paysubscribe_wecoin_amount is null");
            return (Criteria) this;
        }

        public Criteria andPaysubscribeWecoinAmountIsNotNull() {
            addCriterion("paysubscribe_wecoin_amount is not null");
            return (Criteria) this;
        }

        public Criteria andPaysubscribeWecoinAmountEqualTo(Integer value) {
            addCriterion("paysubscribe_wecoin_amount =", value, "paysubscribeWecoinAmount");
            return (Criteria) this;
        }

        public Criteria andPaysubscribeWecoinAmountNotEqualTo(Integer value) {
            addCriterion("paysubscribe_wecoin_amount <>", value, "paysubscribeWecoinAmount");
            return (Criteria) this;
        }

        public Criteria andPaysubscribeWecoinAmountGreaterThan(Integer value) {
            addCriterion("paysubscribe_wecoin_amount >", value, "paysubscribeWecoinAmount");
            return (Criteria) this;
        }

        public Criteria andPaysubscribeWecoinAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("paysubscribe_wecoin_amount >=", value, "paysubscribeWecoinAmount");
            return (Criteria) this;
        }

        public Criteria andPaysubscribeWecoinAmountLessThan(Integer value) {
            addCriterion("paysubscribe_wecoin_amount <", value, "paysubscribeWecoinAmount");
            return (Criteria) this;
        }

        public Criteria andPaysubscribeWecoinAmountLessThanOrEqualTo(Integer value) {
            addCriterion("paysubscribe_wecoin_amount <=", value, "paysubscribeWecoinAmount");
            return (Criteria) this;
        }

        public Criteria andPaysubscribeWecoinAmountIn(List<Integer> values) {
            addCriterion("paysubscribe_wecoin_amount in", values, "paysubscribeWecoinAmount");
            return (Criteria) this;
        }

        public Criteria andPaysubscribeWecoinAmountNotIn(List<Integer> values) {
            addCriterion("paysubscribe_wecoin_amount not in", values, "paysubscribeWecoinAmount");
            return (Criteria) this;
        }

        public Criteria andPaysubscribeWecoinAmountBetween(Integer value1, Integer value2) {
            addCriterion("paysubscribe_wecoin_amount between", value1, value2, "paysubscribeWecoinAmount");
            return (Criteria) this;
        }

        public Criteria andPaysubscribeWecoinAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("paysubscribe_wecoin_amount not between", value1, value2, "paysubscribeWecoinAmount");
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