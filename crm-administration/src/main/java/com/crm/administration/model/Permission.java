package com.crm.administration.model;

import com.crm.dto.BaseDto;
import com.crm.dto.BaseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("权限模型")
public class Permission extends BaseDto {

    private Long id;

    @ApiModelProperty(value="父级编码",example = "")
    private Long parentId;

    @ApiModelProperty(value="权限名称",example = "测试权限")
    private String name;

    @ApiModelProperty(value="功能类型(1为菜单，2为功能点)",example = "1")
    private Short functionType;

    @ApiModelProperty(value="权限路径",example = "http://localhost:9020/vm-administration/user.html")
    private String url;

    @ApiModelProperty(value="权限路径",example = "http://localhost:9020/vm-administration/resources/test.jpg")
    private String img;

    @ApiModelProperty(value="编码",example = "1")
    private Integer seqNo;

    @ApiModelProperty(hidden=true,example = "")
    private String ifDynamicConfig;
    @ApiModelProperty(hidden=true,example = "")
    private String tableName;

    @ApiModelProperty(value="请求方式",example = "GET")
    private String requestMethod;

    @ApiModelProperty(value="是否需要权限认证",example = "Y")
    private String ifNeedAuth;

    @ApiModelProperty(value="PC端菜单地址",example = "/vm-administration/user/list")
    private String pcMenuUrl;

    @ApiModelProperty(value="是否公用的权限",example = "Y")
    private String isCommon;

    @ApiModelProperty(value="是否分配给非系统角色",example = "Y")
    private String isAuth;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        this.name = name == null ? null : name.trim();
    }

    public Short getFunctionType() {
        return functionType;
    }

    public void setFunctionType(Short functionType) {
        this.functionType = functionType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public Integer getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    public String getIfDynamicConfig() {
        return ifDynamicConfig;
    }

    public void setIfDynamicConfig(String ifDynamicConfig) {
        this.ifDynamicConfig = ifDynamicConfig == null ? null : ifDynamicConfig.trim();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod == null ? null : requestMethod.trim();
    }

    public String getIfNeedAuth() {
        return ifNeedAuth;
    }

    public void setIfNeedAuth(String ifNeedAuth) {
        this.ifNeedAuth = ifNeedAuth == null ? null : ifNeedAuth.trim();
    }

    public String getPcMenuUrl() {
        return pcMenuUrl;
    }

    public void setPcMenuUrl(String pcMenuUrl) {
        this.pcMenuUrl = pcMenuUrl == null ? null : pcMenuUrl.trim();
    }

    public String getIsCommon() {
        return isCommon;
    }

    public void setIsCommon(String isCommon) {
        this.isCommon = isCommon == null ? null : isCommon.trim();
    }

    public String getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(String isAuth) {
        this.isAuth = isAuth == null ? null : isAuth.trim();
    }

}