package com.crm.administration.model.login;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class SessionLoginVo extends TokenLoginVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5809405346660609486L;
	@ApiModelProperty(value = "验证码")
	private String verifyCode;

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

}
