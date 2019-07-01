package com.crm.administration.model.login;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class TokenLoginVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5809405346660609486L;
	@ApiModelProperty(value = "登录账号", required = true, example = "myUserLoginId")
	private String userLoginId;
	@ApiModelProperty(value = "登录密码", required = true, example = "123456")
	private String password;

	public String getUserLoginId() {
		return userLoginId;
	}

	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
