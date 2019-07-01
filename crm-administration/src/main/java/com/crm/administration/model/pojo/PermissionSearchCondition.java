package com.crm.administration.model.pojo;

import com.crm.common.SearchCondition;
import com.crm.common.SearchCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("权限检索模型")
public class PermissionSearchCondition extends SearchCondition {

    @ApiModelProperty(value = "功能类型(1为菜单，2为功能点)",example = "1")
    private Integer functionType;

    @ApiModelProperty(value = "父编码",example = "")
    public Long parentId;

    @ApiModelProperty(value = "权限名称模糊查询",example = "")
    public String name;

    @ApiModelProperty(value = "请求方式(GET/POST)",example = "GET")
    private String requestMethod;

    @ApiModelProperty(value = "是否需要权限验证",example = "Y")
    private String ifNeedAuth;

    public Integer getFunctionType() {
        return functionType;
    }

    public void setFunctionType(Integer functionType) {
        this.functionType = functionType;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getIfNeedAuth() {
        return ifNeedAuth;
    }

    public void setIfNeedAuth(String ifNeedAuth) {
        this.ifNeedAuth = ifNeedAuth;
    }
}
