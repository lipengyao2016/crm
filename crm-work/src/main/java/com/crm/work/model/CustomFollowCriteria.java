package com.crm.work.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomFollowCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CustomFollowCriteria() {
        oredCriteria = new ArrayList<Criteria>();
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
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
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

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCidIsNull() {
            addCriterion("cid is null");
            return (Criteria) this;
        }

        public Criteria andCidIsNotNull() {
            addCriterion("cid is not null");
            return (Criteria) this;
        }

        public Criteria andCidEqualTo(Long value) {
            addCriterion("cid =", value, "cid");
            return (Criteria) this;
        }

        public Criteria andCidNotEqualTo(Long value) {
            addCriterion("cid <>", value, "cid");
            return (Criteria) this;
        }

        public Criteria andCidGreaterThan(Long value) {
            addCriterion("cid >", value, "cid");
            return (Criteria) this;
        }

        public Criteria andCidGreaterThanOrEqualTo(Long value) {
            addCriterion("cid >=", value, "cid");
            return (Criteria) this;
        }

        public Criteria andCidLessThan(Long value) {
            addCriterion("cid <", value, "cid");
            return (Criteria) this;
        }

        public Criteria andCidLessThanOrEqualTo(Long value) {
            addCriterion("cid <=", value, "cid");
            return (Criteria) this;
        }

        public Criteria andCidIn(List<Long> values) {
            addCriterion("cid in", values, "cid");
            return (Criteria) this;
        }

        public Criteria andCidNotIn(List<Long> values) {
            addCriterion("cid not in", values, "cid");
            return (Criteria) this;
        }

        public Criteria andCidBetween(Long value1, Long value2) {
            addCriterion("cid between", value1, value2, "cid");
            return (Criteria) this;
        }

        public Criteria andCidNotBetween(Long value1, Long value2) {
            addCriterion("cid not between", value1, value2, "cid");
            return (Criteria) this;
        }

        public Criteria andCustomerStageIsNull() {
            addCriterion("customer_stage is null");
            return (Criteria) this;
        }

        public Criteria andCustomerStageIsNotNull() {
            addCriterion("customer_stage is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerStageEqualTo(Short value) {
            addCriterion("customer_stage =", value, "customerStage");
            return (Criteria) this;
        }

        public Criteria andCustomerStageNotEqualTo(Short value) {
            addCriterion("customer_stage <>", value, "customerStage");
            return (Criteria) this;
        }

        public Criteria andCustomerStageGreaterThan(Short value) {
            addCriterion("customer_stage >", value, "customerStage");
            return (Criteria) this;
        }

        public Criteria andCustomerStageGreaterThanOrEqualTo(Short value) {
            addCriterion("customer_stage >=", value, "customerStage");
            return (Criteria) this;
        }

        public Criteria andCustomerStageLessThan(Short value) {
            addCriterion("customer_stage <", value, "customerStage");
            return (Criteria) this;
        }

        public Criteria andCustomerStageLessThanOrEqualTo(Short value) {
            addCriterion("customer_stage <=", value, "customerStage");
            return (Criteria) this;
        }

        public Criteria andCustomerStageIn(List<Short> values) {
            addCriterion("customer_stage in", values, "customerStage");
            return (Criteria) this;
        }

        public Criteria andCustomerStageNotIn(List<Short> values) {
            addCriterion("customer_stage not in", values, "customerStage");
            return (Criteria) this;
        }

        public Criteria andCustomerStageBetween(Short value1, Short value2) {
            addCriterion("customer_stage between", value1, value2, "customerStage");
            return (Criteria) this;
        }

        public Criteria andCustomerStageNotBetween(Short value1, Short value2) {
            addCriterion("customer_stage not between", value1, value2, "customerStage");
            return (Criteria) this;
        }

        public Criteria andFollowUidIsNull() {
            addCriterion("follow_uid is null");
            return (Criteria) this;
        }

        public Criteria andFollowUidIsNotNull() {
            addCriterion("follow_uid is not null");
            return (Criteria) this;
        }

        public Criteria andFollowUidEqualTo(Long value) {
            addCriterion("follow_uid =", value, "followUid");
            return (Criteria) this;
        }

        public Criteria andFollowUidNotEqualTo(Long value) {
            addCriterion("follow_uid <>", value, "followUid");
            return (Criteria) this;
        }

        public Criteria andFollowUidGreaterThan(Long value) {
            addCriterion("follow_uid >", value, "followUid");
            return (Criteria) this;
        }

        public Criteria andFollowUidGreaterThanOrEqualTo(Long value) {
            addCriterion("follow_uid >=", value, "followUid");
            return (Criteria) this;
        }

        public Criteria andFollowUidLessThan(Long value) {
            addCriterion("follow_uid <", value, "followUid");
            return (Criteria) this;
        }

        public Criteria andFollowUidLessThanOrEqualTo(Long value) {
            addCriterion("follow_uid <=", value, "followUid");
            return (Criteria) this;
        }

        public Criteria andFollowUidIn(List<Long> values) {
            addCriterion("follow_uid in", values, "followUid");
            return (Criteria) this;
        }

        public Criteria andFollowUidNotIn(List<Long> values) {
            addCriterion("follow_uid not in", values, "followUid");
            return (Criteria) this;
        }

        public Criteria andFollowUidBetween(Long value1, Long value2) {
            addCriterion("follow_uid between", value1, value2, "followUid");
            return (Criteria) this;
        }

        public Criteria andFollowUidNotBetween(Long value1, Long value2) {
            addCriterion("follow_uid not between", value1, value2, "followUid");
            return (Criteria) this;
        }

        public Criteria andFollowDtIsNull() {
            addCriterion("follow_dt is null");
            return (Criteria) this;
        }

        public Criteria andFollowDtIsNotNull() {
            addCriterion("follow_dt is not null");
            return (Criteria) this;
        }

        public Criteria andFollowDtEqualTo(Date value) {
            addCriterion("follow_dt =", value, "followDt");
            return (Criteria) this;
        }

        public Criteria andFollowDtNotEqualTo(Date value) {
            addCriterion("follow_dt <>", value, "followDt");
            return (Criteria) this;
        }

        public Criteria andFollowDtGreaterThan(Date value) {
            addCriterion("follow_dt >", value, "followDt");
            return (Criteria) this;
        }

        public Criteria andFollowDtGreaterThanOrEqualTo(Date value) {
            addCriterion("follow_dt >=", value, "followDt");
            return (Criteria) this;
        }

        public Criteria andFollowDtLessThan(Date value) {
            addCriterion("follow_dt <", value, "followDt");
            return (Criteria) this;
        }

        public Criteria andFollowDtLessThanOrEqualTo(Date value) {
            addCriterion("follow_dt <=", value, "followDt");
            return (Criteria) this;
        }

        public Criteria andFollowDtIn(List<Date> values) {
            addCriterion("follow_dt in", values, "followDt");
            return (Criteria) this;
        }

        public Criteria andFollowDtNotIn(List<Date> values) {
            addCriterion("follow_dt not in", values, "followDt");
            return (Criteria) this;
        }

        public Criteria andFollowDtBetween(Date value1, Date value2) {
            addCriterion("follow_dt between", value1, value2, "followDt");
            return (Criteria) this;
        }

        public Criteria andFollowDtNotBetween(Date value1, Date value2) {
            addCriterion("follow_dt not between", value1, value2, "followDt");
            return (Criteria) this;
        }

        public Criteria andAttachementKeyIsNull() {
            addCriterion("attachement_key is null");
            return (Criteria) this;
        }

        public Criteria andAttachementKeyIsNotNull() {
            addCriterion("attachement_key is not null");
            return (Criteria) this;
        }

        public Criteria andAttachementKeyEqualTo(String value) {
            addCriterion("attachement_key =", value, "attachementKey");
            return (Criteria) this;
        }

        public Criteria andAttachementKeyNotEqualTo(String value) {
            addCriterion("attachement_key <>", value, "attachementKey");
            return (Criteria) this;
        }

        public Criteria andAttachementKeyGreaterThan(String value) {
            addCriterion("attachement_key >", value, "attachementKey");
            return (Criteria) this;
        }

        public Criteria andAttachementKeyGreaterThanOrEqualTo(String value) {
            addCriterion("attachement_key >=", value, "attachementKey");
            return (Criteria) this;
        }

        public Criteria andAttachementKeyLessThan(String value) {
            addCriterion("attachement_key <", value, "attachementKey");
            return (Criteria) this;
        }

        public Criteria andAttachementKeyLessThanOrEqualTo(String value) {
            addCriterion("attachement_key <=", value, "attachementKey");
            return (Criteria) this;
        }

        public Criteria andAttachementKeyLike(String value) {
            addCriterion("attachement_key like", value, "attachementKey");
            return (Criteria) this;
        }

        public Criteria andAttachementKeyNotLike(String value) {
            addCriterion("attachement_key not like", value, "attachementKey");
            return (Criteria) this;
        }

        public Criteria andAttachementKeyIn(List<String> values) {
            addCriterion("attachement_key in", values, "attachementKey");
            return (Criteria) this;
        }

        public Criteria andAttachementKeyNotIn(List<String> values) {
            addCriterion("attachement_key not in", values, "attachementKey");
            return (Criteria) this;
        }

        public Criteria andAttachementKeyBetween(String value1, String value2) {
            addCriterion("attachement_key between", value1, value2, "attachementKey");
            return (Criteria) this;
        }

        public Criteria andAttachementKeyNotBetween(String value1, String value2) {
            addCriterion("attachement_key not between", value1, value2, "attachementKey");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andIsFinishIsNull() {
            addCriterion("is_finish is null");
            return (Criteria) this;
        }

        public Criteria andIsFinishIsNotNull() {
            addCriterion("is_finish is not null");
            return (Criteria) this;
        }

        public Criteria andIsFinishEqualTo(String value) {
            addCriterion("is_finish =", value, "isFinish");
            return (Criteria) this;
        }

        public Criteria andIsFinishNotEqualTo(String value) {
            addCriterion("is_finish <>", value, "isFinish");
            return (Criteria) this;
        }

        public Criteria andIsFinishGreaterThan(String value) {
            addCriterion("is_finish >", value, "isFinish");
            return (Criteria) this;
        }

        public Criteria andIsFinishGreaterThanOrEqualTo(String value) {
            addCriterion("is_finish >=", value, "isFinish");
            return (Criteria) this;
        }

        public Criteria andIsFinishLessThan(String value) {
            addCriterion("is_finish <", value, "isFinish");
            return (Criteria) this;
        }

        public Criteria andIsFinishLessThanOrEqualTo(String value) {
            addCriterion("is_finish <=", value, "isFinish");
            return (Criteria) this;
        }

        public Criteria andIsFinishLike(String value) {
            addCriterion("is_finish like", value, "isFinish");
            return (Criteria) this;
        }

        public Criteria andIsFinishNotLike(String value) {
            addCriterion("is_finish not like", value, "isFinish");
            return (Criteria) this;
        }

        public Criteria andIsFinishIn(List<String> values) {
            addCriterion("is_finish in", values, "isFinish");
            return (Criteria) this;
        }

        public Criteria andIsFinishNotIn(List<String> values) {
            addCriterion("is_finish not in", values, "isFinish");
            return (Criteria) this;
        }

        public Criteria andIsFinishBetween(String value1, String value2) {
            addCriterion("is_finish between", value1, value2, "isFinish");
            return (Criteria) this;
        }

        public Criteria andIsFinishNotBetween(String value1, String value2) {
            addCriterion("is_finish not between", value1, value2, "isFinish");
            return (Criteria) this;
        }

        public Criteria andHelpUidIsNull() {
            addCriterion("help_uid is null");
            return (Criteria) this;
        }

        public Criteria andHelpUidIsNotNull() {
            addCriterion("help_uid is not null");
            return (Criteria) this;
        }

        public Criteria andHelpUidEqualTo(Long value) {
            addCriterion("help_uid =", value, "helpUid");
            return (Criteria) this;
        }

        public Criteria andHelpUidNotEqualTo(Long value) {
            addCriterion("help_uid <>", value, "helpUid");
            return (Criteria) this;
        }

        public Criteria andHelpUidGreaterThan(Long value) {
            addCriterion("help_uid >", value, "helpUid");
            return (Criteria) this;
        }

        public Criteria andHelpUidGreaterThanOrEqualTo(Long value) {
            addCriterion("help_uid >=", value, "helpUid");
            return (Criteria) this;
        }

        public Criteria andHelpUidLessThan(Long value) {
            addCriterion("help_uid <", value, "helpUid");
            return (Criteria) this;
        }

        public Criteria andHelpUidLessThanOrEqualTo(Long value) {
            addCriterion("help_uid <=", value, "helpUid");
            return (Criteria) this;
        }

        public Criteria andHelpUidIn(List<Long> values) {
            addCriterion("help_uid in", values, "helpUid");
            return (Criteria) this;
        }

        public Criteria andHelpUidNotIn(List<Long> values) {
            addCriterion("help_uid not in", values, "helpUid");
            return (Criteria) this;
        }

        public Criteria andHelpUidBetween(Long value1, Long value2) {
            addCriterion("help_uid between", value1, value2, "helpUid");
            return (Criteria) this;
        }

        public Criteria andHelpUidNotBetween(Long value1, Long value2) {
            addCriterion("help_uid not between", value1, value2, "helpUid");
            return (Criteria) this;
        }

        public Criteria andNextIdIsNull() {
            addCriterion("next_id is null");
            return (Criteria) this;
        }

        public Criteria andNextIdIsNotNull() {
            addCriterion("next_id is not null");
            return (Criteria) this;
        }

        public Criteria andNextIdEqualTo(Long value) {
            addCriterion("next_id =", value, "nextId");
            return (Criteria) this;
        }

        public Criteria andNextIdNotEqualTo(Long value) {
            addCriterion("next_id <>", value, "nextId");
            return (Criteria) this;
        }

        public Criteria andNextIdGreaterThan(Long value) {
            addCriterion("next_id >", value, "nextId");
            return (Criteria) this;
        }

        public Criteria andNextIdGreaterThanOrEqualTo(Long value) {
            addCriterion("next_id >=", value, "nextId");
            return (Criteria) this;
        }

        public Criteria andNextIdLessThan(Long value) {
            addCriterion("next_id <", value, "nextId");
            return (Criteria) this;
        }

        public Criteria andNextIdLessThanOrEqualTo(Long value) {
            addCriterion("next_id <=", value, "nextId");
            return (Criteria) this;
        }

        public Criteria andNextIdIn(List<Long> values) {
            addCriterion("next_id in", values, "nextId");
            return (Criteria) this;
        }

        public Criteria andNextIdNotIn(List<Long> values) {
            addCriterion("next_id not in", values, "nextId");
            return (Criteria) this;
        }

        public Criteria andNextIdBetween(Long value1, Long value2) {
            addCriterion("next_id between", value1, value2, "nextId");
            return (Criteria) this;
        }

        public Criteria andNextIdNotBetween(Long value1, Long value2) {
            addCriterion("next_id not between", value1, value2, "nextId");
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