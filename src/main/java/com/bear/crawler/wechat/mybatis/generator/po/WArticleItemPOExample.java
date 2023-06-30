package com.bear.crawler.wechat.mybatis.generator.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WArticleItemPOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public WArticleItemPOExample() {
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

        public Criteria andAidIsNull() {
            addCriterion("aid is null");
            return (Criteria) this;
        }

        public Criteria andAidIsNotNull() {
            addCriterion("aid is not null");
            return (Criteria) this;
        }

        public Criteria andAidEqualTo(String value) {
            addCriterion("aid =", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidNotEqualTo(String value) {
            addCriterion("aid <>", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidGreaterThan(String value) {
            addCriterion("aid >", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidGreaterThanOrEqualTo(String value) {
            addCriterion("aid >=", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidLessThan(String value) {
            addCriterion("aid <", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidLessThanOrEqualTo(String value) {
            addCriterion("aid <=", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidLike(String value) {
            addCriterion("aid like", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidNotLike(String value) {
            addCriterion("aid not like", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidIn(List<String> values) {
            addCriterion("aid in", values, "aid");
            return (Criteria) this;
        }

        public Criteria andAidNotIn(List<String> values) {
            addCriterion("aid not in", values, "aid");
            return (Criteria) this;
        }

        public Criteria andAidBetween(String value1, String value2) {
            addCriterion("aid between", value1, value2, "aid");
            return (Criteria) this;
        }

        public Criteria andAidNotBetween(String value1, String value2) {
            addCriterion("aid not between", value1, value2, "aid");
            return (Criteria) this;
        }

        public Criteria andAppmsgidIsNull() {
            addCriterion("appmsgid is null");
            return (Criteria) this;
        }

        public Criteria andAppmsgidIsNotNull() {
            addCriterion("appmsgid is not null");
            return (Criteria) this;
        }

        public Criteria andAppmsgidEqualTo(Long value) {
            addCriterion("appmsgid =", value, "appmsgid");
            return (Criteria) this;
        }

        public Criteria andAppmsgidNotEqualTo(Long value) {
            addCriterion("appmsgid <>", value, "appmsgid");
            return (Criteria) this;
        }

        public Criteria andAppmsgidGreaterThan(Long value) {
            addCriterion("appmsgid >", value, "appmsgid");
            return (Criteria) this;
        }

        public Criteria andAppmsgidGreaterThanOrEqualTo(Long value) {
            addCriterion("appmsgid >=", value, "appmsgid");
            return (Criteria) this;
        }

        public Criteria andAppmsgidLessThan(Long value) {
            addCriterion("appmsgid <", value, "appmsgid");
            return (Criteria) this;
        }

        public Criteria andAppmsgidLessThanOrEqualTo(Long value) {
            addCriterion("appmsgid <=", value, "appmsgid");
            return (Criteria) this;
        }

        public Criteria andAppmsgidIn(List<Long> values) {
            addCriterion("appmsgid in", values, "appmsgid");
            return (Criteria) this;
        }

        public Criteria andAppmsgidNotIn(List<Long> values) {
            addCriterion("appmsgid not in", values, "appmsgid");
            return (Criteria) this;
        }

        public Criteria andAppmsgidBetween(Long value1, Long value2) {
            addCriterion("appmsgid between", value1, value2, "appmsgid");
            return (Criteria) this;
        }

        public Criteria andAppmsgidNotBetween(Long value1, Long value2) {
            addCriterion("appmsgid not between", value1, value2, "appmsgid");
            return (Criteria) this;
        }

        public Criteria andCoverIsNull() {
            addCriterion("cover is null");
            return (Criteria) this;
        }

        public Criteria andCoverIsNotNull() {
            addCriterion("cover is not null");
            return (Criteria) this;
        }

        public Criteria andCoverEqualTo(String value) {
            addCriterion("cover =", value, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverNotEqualTo(String value) {
            addCriterion("cover <>", value, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverGreaterThan(String value) {
            addCriterion("cover >", value, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverGreaterThanOrEqualTo(String value) {
            addCriterion("cover >=", value, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverLessThan(String value) {
            addCriterion("cover <", value, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverLessThanOrEqualTo(String value) {
            addCriterion("cover <=", value, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverLike(String value) {
            addCriterion("cover like", value, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverNotLike(String value) {
            addCriterion("cover not like", value, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverIn(List<String> values) {
            addCriterion("cover in", values, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverNotIn(List<String> values) {
            addCriterion("cover not in", values, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverBetween(String value1, String value2) {
            addCriterion("cover between", value1, value2, "cover");
            return (Criteria) this;
        }

        public Criteria andCoverNotBetween(String value1, String value2) {
            addCriterion("cover not between", value1, value2, "cover");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andItemidxIsNull() {
            addCriterion("itemidx is null");
            return (Criteria) this;
        }

        public Criteria andItemidxIsNotNull() {
            addCriterion("itemidx is not null");
            return (Criteria) this;
        }

        public Criteria andItemidxEqualTo(Integer value) {
            addCriterion("itemidx =", value, "itemidx");
            return (Criteria) this;
        }

        public Criteria andItemidxNotEqualTo(Integer value) {
            addCriterion("itemidx <>", value, "itemidx");
            return (Criteria) this;
        }

        public Criteria andItemidxGreaterThan(Integer value) {
            addCriterion("itemidx >", value, "itemidx");
            return (Criteria) this;
        }

        public Criteria andItemidxGreaterThanOrEqualTo(Integer value) {
            addCriterion("itemidx >=", value, "itemidx");
            return (Criteria) this;
        }

        public Criteria andItemidxLessThan(Integer value) {
            addCriterion("itemidx <", value, "itemidx");
            return (Criteria) this;
        }

        public Criteria andItemidxLessThanOrEqualTo(Integer value) {
            addCriterion("itemidx <=", value, "itemidx");
            return (Criteria) this;
        }

        public Criteria andItemidxIn(List<Integer> values) {
            addCriterion("itemidx in", values, "itemidx");
            return (Criteria) this;
        }

        public Criteria andItemidxNotIn(List<Integer> values) {
            addCriterion("itemidx not in", values, "itemidx");
            return (Criteria) this;
        }

        public Criteria andItemidxBetween(Integer value1, Integer value2) {
            addCriterion("itemidx between", value1, value2, "itemidx");
            return (Criteria) this;
        }

        public Criteria andItemidxNotBetween(Integer value1, Integer value2) {
            addCriterion("itemidx not between", value1, value2, "itemidx");
            return (Criteria) this;
        }

        public Criteria andLinkIsNull() {
            addCriterion("link is null");
            return (Criteria) this;
        }

        public Criteria andLinkIsNotNull() {
            addCriterion("link is not null");
            return (Criteria) this;
        }

        public Criteria andLinkEqualTo(String value) {
            addCriterion("link =", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkNotEqualTo(String value) {
            addCriterion("link <>", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkGreaterThan(String value) {
            addCriterion("link >", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkGreaterThanOrEqualTo(String value) {
            addCriterion("link >=", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkLessThan(String value) {
            addCriterion("link <", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkLessThanOrEqualTo(String value) {
            addCriterion("link <=", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkLike(String value) {
            addCriterion("link like", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkNotLike(String value) {
            addCriterion("link not like", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkIn(List<String> values) {
            addCriterion("link in", values, "link");
            return (Criteria) this;
        }

        public Criteria andLinkNotIn(List<String> values) {
            addCriterion("link not in", values, "link");
            return (Criteria) this;
        }

        public Criteria andLinkBetween(String value1, String value2) {
            addCriterion("link between", value1, value2, "link");
            return (Criteria) this;
        }

        public Criteria andLinkNotBetween(String value1, String value2) {
            addCriterion("link not between", value1, value2, "link");
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

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountIdIsNull() {
            addCriterion("official_account_id is null");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountIdIsNotNull() {
            addCriterion("official_account_id is not null");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountIdEqualTo(Integer value) {
            addCriterion("official_account_id =", value, "officialAccountId");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountIdNotEqualTo(Integer value) {
            addCriterion("official_account_id <>", value, "officialAccountId");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountIdGreaterThan(Integer value) {
            addCriterion("official_account_id >", value, "officialAccountId");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("official_account_id >=", value, "officialAccountId");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountIdLessThan(Integer value) {
            addCriterion("official_account_id <", value, "officialAccountId");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountIdLessThanOrEqualTo(Integer value) {
            addCriterion("official_account_id <=", value, "officialAccountId");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountIdIn(List<Integer> values) {
            addCriterion("official_account_id in", values, "officialAccountId");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountIdNotIn(List<Integer> values) {
            addCriterion("official_account_id not in", values, "officialAccountId");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountIdBetween(Integer value1, Integer value2) {
            addCriterion("official_account_id between", value1, value2, "officialAccountId");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountIdNotBetween(Integer value1, Integer value2) {
            addCriterion("official_account_id not between", value1, value2, "officialAccountId");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountFakeIdIsNull() {
            addCriterion("official_account_fake_id is null");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountFakeIdIsNotNull() {
            addCriterion("official_account_fake_id is not null");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountFakeIdEqualTo(String value) {
            addCriterion("official_account_fake_id =", value, "officialAccountFakeId");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountFakeIdNotEqualTo(String value) {
            addCriterion("official_account_fake_id <>", value, "officialAccountFakeId");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountFakeIdGreaterThan(String value) {
            addCriterion("official_account_fake_id >", value, "officialAccountFakeId");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountFakeIdGreaterThanOrEqualTo(String value) {
            addCriterion("official_account_fake_id >=", value, "officialAccountFakeId");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountFakeIdLessThan(String value) {
            addCriterion("official_account_fake_id <", value, "officialAccountFakeId");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountFakeIdLessThanOrEqualTo(String value) {
            addCriterion("official_account_fake_id <=", value, "officialAccountFakeId");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountFakeIdLike(String value) {
            addCriterion("official_account_fake_id like", value, "officialAccountFakeId");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountFakeIdNotLike(String value) {
            addCriterion("official_account_fake_id not like", value, "officialAccountFakeId");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountFakeIdIn(List<String> values) {
            addCriterion("official_account_fake_id in", values, "officialAccountFakeId");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountFakeIdNotIn(List<String> values) {
            addCriterion("official_account_fake_id not in", values, "officialAccountFakeId");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountFakeIdBetween(String value1, String value2) {
            addCriterion("official_account_fake_id between", value1, value2, "officialAccountFakeId");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountFakeIdNotBetween(String value1, String value2) {
            addCriterion("official_account_fake_id not between", value1, value2, "officialAccountFakeId");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountTitleIsNull() {
            addCriterion("official_account_title is null");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountTitleIsNotNull() {
            addCriterion("official_account_title is not null");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountTitleEqualTo(String value) {
            addCriterion("official_account_title =", value, "officialAccountTitle");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountTitleNotEqualTo(String value) {
            addCriterion("official_account_title <>", value, "officialAccountTitle");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountTitleGreaterThan(String value) {
            addCriterion("official_account_title >", value, "officialAccountTitle");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountTitleGreaterThanOrEqualTo(String value) {
            addCriterion("official_account_title >=", value, "officialAccountTitle");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountTitleLessThan(String value) {
            addCriterion("official_account_title <", value, "officialAccountTitle");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountTitleLessThanOrEqualTo(String value) {
            addCriterion("official_account_title <=", value, "officialAccountTitle");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountTitleLike(String value) {
            addCriterion("official_account_title like", value, "officialAccountTitle");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountTitleNotLike(String value) {
            addCriterion("official_account_title not like", value, "officialAccountTitle");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountTitleIn(List<String> values) {
            addCriterion("official_account_title in", values, "officialAccountTitle");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountTitleNotIn(List<String> values) {
            addCriterion("official_account_title not in", values, "officialAccountTitle");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountTitleBetween(String value1, String value2) {
            addCriterion("official_account_title between", value1, value2, "officialAccountTitle");
            return (Criteria) this;
        }

        public Criteria andOfficialAccountTitleNotBetween(String value1, String value2) {
            addCriterion("official_account_title not between", value1, value2, "officialAccountTitle");
            return (Criteria) this;
        }

        public Criteria andHandleStateIsNull() {
            addCriterion("handle_state is null");
            return (Criteria) this;
        }

        public Criteria andHandleStateIsNotNull() {
            addCriterion("handle_state is not null");
            return (Criteria) this;
        }

        public Criteria andHandleStateEqualTo(Integer value) {
            addCriterion("handle_state =", value, "handleState");
            return (Criteria) this;
        }

        public Criteria andHandleStateNotEqualTo(Integer value) {
            addCriterion("handle_state <>", value, "handleState");
            return (Criteria) this;
        }

        public Criteria andHandleStateGreaterThan(Integer value) {
            addCriterion("handle_state >", value, "handleState");
            return (Criteria) this;
        }

        public Criteria andHandleStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("handle_state >=", value, "handleState");
            return (Criteria) this;
        }

        public Criteria andHandleStateLessThan(Integer value) {
            addCriterion("handle_state <", value, "handleState");
            return (Criteria) this;
        }

        public Criteria andHandleStateLessThanOrEqualTo(Integer value) {
            addCriterion("handle_state <=", value, "handleState");
            return (Criteria) this;
        }

        public Criteria andHandleStateIn(List<Integer> values) {
            addCriterion("handle_state in", values, "handleState");
            return (Criteria) this;
        }

        public Criteria andHandleStateNotIn(List<Integer> values) {
            addCriterion("handle_state not in", values, "handleState");
            return (Criteria) this;
        }

        public Criteria andHandleStateBetween(Integer value1, Integer value2) {
            addCriterion("handle_state between", value1, value2, "handleState");
            return (Criteria) this;
        }

        public Criteria andHandleStateNotBetween(Integer value1, Integer value2) {
            addCriterion("handle_state not between", value1, value2, "handleState");
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