package com.crm.administration.model.login;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel("账号密码信息")
public class AccountVo implements Serializable {
	@ApiModelProperty("登录账号")
	private String userLoginId;
	@ApiModelProperty("新密码")
	private String newPwd;
	@ApiModelProperty("旧密码")
	private String oldPwd;
	@ApiModelProperty("手机验证码")
	private String verifyCode;

	public String getUserLoginId() {
		return userLoginId;
	}

	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

}
