package com.crm.basic.model;

import com.crm.common.SearchCondition;

@SuppressWarnings("serial")
public class CommonDictionaryCondition extends SearchCondition {
	private String dictionaryName;
	private String dictionaryDesc;
	private String propertyValue;
	private String propertyText;
	private String available;
	private String status;
	private String isCommon;
	private Long agencyId;
	private String roleId;

	public String getDictionaryName() {
		return dictionaryName;
	}

	public void setDictionaryName(String dictionaryName) {
		this.dictionaryName = dictionaryName;
	}

	public String getDictionaryDesc() {
		return dictionaryDesc;
	}

	public void setDictionaryDesc(String dictionaryDesc) {
		this.dictionaryDesc = dictionaryDesc;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public String getPropertyText() {
		return propertyText;
	}

	public void setPropertyText(String propertyText) {
		this.propertyText = propertyText;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsCommon() {
		return isCommon;
	}

	public void setIsCommon(String isCommon) {
		this.isCommon = isCommon;
	}

	public Long getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
