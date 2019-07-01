package com.crm.administration.model.login;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class UserLoginLog implements Serializable {
	private String userLoginId;
	private Date lastLoginTime;
	private String lastLoginIP;
	private String lastLoginChannel;
	private String lastLoginImsiNum;

	public String getUserLoginId() {
		return userLoginId;
	}

	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIP() {
		return lastLoginIP;
	}

	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}

	public String getLastLoginChannel() {
		return lastLoginChannel;
	}

	public void setLastLoginChannel(String lastLoginChannel) {
		this.lastLoginChannel = lastLoginChannel;
	}

	public String getLastLoginImsiNum() {
		return lastLoginImsiNum;
	}

	public void setLastLoginImsiNum(String lastLoginImsiNum) {
		this.lastLoginImsiNum = lastLoginImsiNum;
	}

}
