package com.crm.work.model.pojo;

import com.crm.common.SearchCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户检索模型")
public class UserSearchCondition extends SearchCondition {

    private String userLoginId;

    private Integer positionSeqId;

    private String gender;

    private String departmentId;

    private String keyword;

    @ApiModelProperty(hidden=true)
    private String currentPasswrod;

    public String getCurrentPasswrod() {
        return currentPasswrod;
    }

    public void setCurrentPasswrod(String currentPasswrod) {
        this.currentPasswrod = currentPasswrod;
    }

    @Override
    public String getUserLoginId() {
        return userLoginId;
    }

    @Override
    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }

    public Integer getPositionSeqId() {
        return positionSeqId;
    }

    public void setPositionSeqId(Integer positionSeqId) {
        this.positionSeqId = positionSeqId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
