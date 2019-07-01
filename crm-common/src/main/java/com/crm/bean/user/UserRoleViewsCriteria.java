package com.crm.bean.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserRoleViewsCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserRoleViewsCriteria() {
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

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andOwneruuidIsNull() {
            addCriterion("ownerUUID is null");
            return (Criteria) this;
        }

        public Criteria andOwneruuidIsNotNull() {
            addCriterion("ownerUUID is not null");
            return (Criteria) this;
        }

        public Criteria andOwneruuidEqualTo(String value) {
            addCriterion("ownerUUID =", value, "owneruuid");
            return (Criteria) this;
        }

        public Criteria andOwneruuidNotEqualTo(String value) {
            addCriterion("ownerUUID <>", value, "owneruuid");
            return (Criteria) this;
        }

        public Criteria andOwneruuidGreaterThan(String value) {
            addCriterion("ownerUUID >", value, "owneruuid");
            return (Criteria) this;
        }

        public Criteria andOwneruuidGreaterThanOrEqualTo(String value) {
            addCriterion("ownerUUID >=", value, "owneruuid");
            return (Criteria) this;
        }

        public Criteria andOwneruuidLessThan(String value) {
            addCriterion("ownerUUID <", value, "owneruuid");
            return (Criteria) this;
        }

        public Criteria andOwneruuidLessThanOrEqualTo(String value) {
            addCriterion("ownerUUID <=", value, "owneruuid");
            return (Criteria) this;
        }

        public Criteria andOwneruuidLike(String value) {
            addCriterion("ownerUUID like", value, "owneruuid");
            return (Criteria) this;
        }

        public Criteria andOwneruuidNotLike(String value) {
            addCriterion("ownerUUID not like", value, "owneruuid");
            return (Criteria) this;
        }

        public Criteria andOwneruuidIn(List<String> values) {
            addCriterion("ownerUUID in", values, "owneruuid");
            return (Criteria) this;
        }

        public Criteria andOwneruuidNotIn(List<String> values) {
            addCriterion("ownerUUID not in", values, "owneruuid");
            return (Criteria) this;
        }

        public Criteria andOwneruuidBetween(String value1, String value2) {
            addCriterion("ownerUUID between", value1, value2, "owneruuid");
            return (Criteria) this;
        }

        public Criteria andOwneruuidNotBetween(String value1, String value2) {
            addCriterion("ownerUUID not between", value1, value2, "owneruuid");
            return (Criteria) this;
        }

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andRoleuuidIsNull() {
            addCriterion("roleUUID is null");
            return (Criteria) this;
        }

        public Criteria andRoleuuidIsNotNull() {
            addCriterion("roleUUID is not null");
            return (Criteria) this;
        }

        public Criteria andRoleuuidEqualTo(String value) {
            addCriterion("roleUUID =", value, "roleuuid");
            return (Criteria) this;
        }

        public Criteria andRoleuuidNotEqualTo(String value) {
            addCriterion("roleUUID <>", value, "roleuuid");
            return (Criteria) this;
        }

        public Criteria andRoleuuidGreaterThan(String value) {
            addCriterion("roleUUID >", value, "roleuuid");
            return (Criteria) this;
        }

        public Criteria andRoleuuidGreaterThanOrEqualTo(String value) {
            addCriterion("roleUUID >=", value, "roleuuid");
            return (Criteria) this;
        }

        public Criteria andRoleuuidLessThan(String value) {
            addCriterion("roleUUID <", value, "roleuuid");
            return (Criteria) this;
        }

        public Criteria andRoleuuidLessThanOrEqualTo(String value) {
            addCriterion("roleUUID <=", value, "roleuuid");
            return (Criteria) this;
        }

        public Criteria andRoleuuidLike(String value) {
            addCriterion("roleUUID like", value, "roleuuid");
            return (Criteria) this;
        }

        public Criteria andRoleuuidNotLike(String value) {
            addCriterion("roleUUID not like", value, "roleuuid");
            return (Criteria) this;
        }

        public Criteria andRoleuuidIn(List<String> values) {
            addCriterion("roleUUID in", values, "roleuuid");
            return (Criteria) this;
        }

        public Criteria andRoleuuidNotIn(List<String> values) {
            addCriterion("roleUUID not in", values, "roleuuid");
            return (Criteria) this;
        }

        public Criteria andRoleuuidBetween(String value1, String value2) {
            addCriterion("roleUUID between", value1, value2, "roleuuid");
            return (Criteria) this;
        }

        public Criteria andRoleuuidNotBetween(String value1, String value2) {
            addCriterion("roleUUID not between", value1, value2, "roleuuid");
            return (Criteria) this;
        }

        public Criteria andUseruuidIsNull() {
            addCriterion("userUuid is null");
            return (Criteria) this;
        }

        public Criteria andUseruuidIsNotNull() {
            addCriterion("userUuid is not null");
            return (Criteria) this;
        }

        public Criteria andUseruuidEqualTo(String value) {
            addCriterion("userUuid =", value, "useruuid");
            return (Criteria) this;
        }

        public Criteria andUseruuidNotEqualTo(String value) {
            addCriterion("userUuid <>", value, "useruuid");
            return (Criteria) this;
        }

        public Criteria andUseruuidGreaterThan(String value) {
            addCriterion("userUuid >", value, "useruuid");
            return (Criteria) this;
        }

        public Criteria andUseruuidGreaterThanOrEqualTo(String value) {
            addCriterion("userUuid >=", value, "useruuid");
            return (Criteria) this;
        }

        public Criteria andUseruuidLessThan(String value) {
            addCriterion("userUuid <", value, "useruuid");
            return (Criteria) this;
        }

        public Criteria andUseruuidLessThanOrEqualTo(String value) {
            addCriterion("userUuid <=", value, "useruuid");
            return (Criteria) this;
        }

        public Criteria andUseruuidLike(String value) {
            addCriterion("userUuid like", value, "useruuid");
            return (Criteria) this;
        }

        public Criteria andUseruuidNotLike(String value) {
            addCriterion("userUuid not like", value, "useruuid");
            return (Criteria) this;
        }

        public Criteria andUseruuidIn(List<String> values) {
            addCriterion("userUuid in", values, "useruuid");
            return (Criteria) this;
        }

        public Criteria andUseruuidNotIn(List<String> values) {
            addCriterion("userUuid not in", values, "useruuid");
            return (Criteria) this;
        }

        public Criteria andUseruuidBetween(String value1, String value2) {
            addCriterion("userUuid between", value1, value2, "useruuid");
            return (Criteria) this;
        }

        public Criteria andUseruuidNotBetween(String value1, String value2) {
            addCriterion("userUuid not between", value1, value2, "useruuid");
            return (Criteria) this;
        }

        public Criteria andUsercreateatIsNull() {
            addCriterion("userCreateAt is null");
            return (Criteria) this;
        }

        public Criteria andUsercreateatIsNotNull() {
            addCriterion("userCreateAt is not null");
            return (Criteria) this;
        }

        public Criteria andUsercreateatEqualTo(Date value) {
            addCriterion("userCreateAt =", value, "usercreateat");
            return (Criteria) this;
        }

        public Criteria andUsercreateatNotEqualTo(Date value) {
            addCriterion("userCreateAt <>", value, "usercreateat");
            return (Criteria) this;
        }

        public Criteria andUsercreateatGreaterThan(Date value) {
            addCriterion("userCreateAt >", value, "usercreateat");
            return (Criteria) this;
        }

        public Criteria andUsercreateatGreaterThanOrEqualTo(Date value) {
            addCriterion("userCreateAt >=", value, "usercreateat");
            return (Criteria) this;
        }

        public Criteria andUsercreateatLessThan(Date value) {
            addCriterion("userCreateAt <", value, "usercreateat");
            return (Criteria) this;
        }

        public Criteria andUsercreateatLessThanOrEqualTo(Date value) {
            addCriterion("userCreateAt <=", value, "usercreateat");
            return (Criteria) this;
        }

        public Criteria andUsercreateatIn(List<Date> values) {
            addCriterion("userCreateAt in", values, "usercreateat");
            return (Criteria) this;
        }

        public Criteria andUsercreateatNotIn(List<Date> values) {
            addCriterion("userCreateAt not in", values, "usercreateat");
            return (Criteria) this;
        }

        public Criteria andUsercreateatBetween(Date value1, Date value2) {
            addCriterion("userCreateAt between", value1, value2, "usercreateat");
            return (Criteria) this;
        }

        public Criteria andUsercreateatNotBetween(Date value1, Date value2) {
            addCriterion("userCreateAt not between", value1, value2, "usercreateat");
            return (Criteria) this;
        }

        public Criteria andUsermodifiedatIsNull() {
            addCriterion("userModifiedAt is null");
            return (Criteria) this;
        }

        public Criteria andUsermodifiedatIsNotNull() {
            addCriterion("userModifiedAt is not null");
            return (Criteria) this;
        }

        public Criteria andUsermodifiedatEqualTo(Date value) {
            addCriterion("userModifiedAt =", value, "usermodifiedat");
            return (Criteria) this;
        }

        public Criteria andUsermodifiedatNotEqualTo(Date value) {
            addCriterion("userModifiedAt <>", value, "usermodifiedat");
            return (Criteria) this;
        }

        public Criteria andUsermodifiedatGreaterThan(Date value) {
            addCriterion("userModifiedAt >", value, "usermodifiedat");
            return (Criteria) this;
        }

        public Criteria andUsermodifiedatGreaterThanOrEqualTo(Date value) {
            addCriterion("userModifiedAt >=", value, "usermodifiedat");
            return (Criteria) this;
        }

        public Criteria andUsermodifiedatLessThan(Date value) {
            addCriterion("userModifiedAt <", value, "usermodifiedat");
            return (Criteria) this;
        }

        public Criteria andUsermodifiedatLessThanOrEqualTo(Date value) {
            addCriterion("userModifiedAt <=", value, "usermodifiedat");
            return (Criteria) this;
        }

        public Criteria andUsermodifiedatIn(List<Date> values) {
            addCriterion("userModifiedAt in", values, "usermodifiedat");
            return (Criteria) this;
        }

        public Criteria andUsermodifiedatNotIn(List<Date> values) {
            addCriterion("userModifiedAt not in", values, "usermodifiedat");
            return (Criteria) this;
        }

        public Criteria andUsermodifiedatBetween(Date value1, Date value2) {
            addCriterion("userModifiedAt between", value1, value2, "usermodifiedat");
            return (Criteria) this;
        }

        public Criteria andUsermodifiedatNotBetween(Date value1, Date value2) {
            addCriterion("userModifiedAt not between", value1, value2, "usermodifiedat");
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