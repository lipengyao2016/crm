package com.crm.administration.model.login;

import com.crm.dto.BaseDto;
import com.crm.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 给APP端注册用户时做参数封装json
 * 
 * @author Administrator
 *
 */
@ApiModel
@SuppressWarnings("serial")
public class UserRegisterVo extends BaseDto {
	@ApiModelProperty(value = "登录账号", example = "boying", required = true)
	private String userLoginId="";
	@ApiModelProperty(value = "姓名", example = "炎睿", required = true)
	private String userName="";
	@ApiModelProperty(value = "用户编码", example = "432", required = true)
	private String userCode="";
	@ApiModelProperty(value = "注册密码", example = "bo_ying", required = true)
	private String currentPassword="";
	@ApiModelProperty(value = "性别", example = "M", required = true)
	private String gender="";
	@ApiModelProperty(value = "电话号码", example = "13565325896")
	private String phone="";
	@ApiModelProperty(value = "邮箱", example = "6538465@163.com")
	private String email="";
	@ApiModelProperty(value = "微信号", example = "ddr32")
	private String weixin="";
	@ApiModelProperty(value = "是否启用", example = "Y")
	private String status="";
	@ApiModelProperty(value = "图片路径", example = "")
	private String attachmentKey="";

	public String getUserLoginId() {
		return userLoginId;
	}

	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAttachmentKey() {
		return attachmentKey;
	}

	public void setAttachmentKey(String attachmentKey) {
		this.attachmentKey = attachmentKey;
	}
}
