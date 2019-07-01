package com.crm.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel("权限信息")
public class PermissionDto extends BaseDto {
	@ApiModelProperty(value = "权限编码")
	private Integer id = 0;
	@ApiModelProperty(value = "父权限编码")
	private Integer parentId = 0;
	@ApiModelProperty(value = "权限名称")
	private String permissionName = "";
	@ApiModelProperty(value = "权限Url")
	private String permissionUrl = "";
	@ApiModelProperty(value = "权限图片地址")
	private String permissionImg = "";
	@ApiModelProperty(value = "序列号")
	private Integer seqNo = 0;
	// private String parentidPath;
	@ApiModelProperty(value = "功能类型(1为菜单，2为功能点)")
	private Integer functionType;
	@ApiModelProperty(value = "是否启用")
	private String status;
	@ApiModelProperty(value = "可否动态配置页面属性")
	private String ifDynamicConfig;
	@ApiModelProperty(value = "可动态配置时，动态字段值保存的表名")
	private String tableName;
	@ApiModelProperty(value = "请求方式")
	private String requestMethod;
	@ApiModelProperty(value = "是否需要权限验证")
	private String ifNeedAuth;
	@ApiModelProperty(value = "是否共有")
	private String isCommon;
	@ApiModelProperty(value = "PC端页面URL")
	private String pcMenuUrl;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName == null ? null : permissionName.trim();
	}

	public String getPermissionUrl() {
		return permissionUrl;
	}

	public void setPermissionUrl(String permissionUrl) {
		this.permissionUrl = permissionUrl == null ? null : permissionUrl.trim();
	}

	public String getPermissionImg() {
		return permissionImg;
	}

	public void setPermissionImg(String permissionImg) {
		this.permissionImg = permissionImg == null ? null : permissionImg.trim();
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public Integer getFunctionType() {
		return functionType;
	}

	public void setFunctionType(Integer functionType) {
		this.functionType = functionType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public String getIfDynamicConfig() {
		return ifDynamicConfig;
	}

	public void setIfDynamicConfig(String ifDynamicConfig) {
		this.ifDynamicConfig = ifDynamicConfig == null ? null : ifDynamicConfig.trim();
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

	public String getIsCommon() {
		return isCommon;
	}

	public void setIsCommon(String isCommon) {
		this.isCommon = isCommon;
	}

	public String getPcMenuUrl() {
		return pcMenuUrl;
	}

	public void setPcMenuUrl(String pcMenuUrl) {
		this.pcMenuUrl = pcMenuUrl;
	}
}