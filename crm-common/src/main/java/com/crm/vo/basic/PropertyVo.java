package com.crm.vo.basic;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class PropertyVo implements Serializable {
	@ApiModelProperty(value = "字段名")
	private String propertyName;
	@ApiModelProperty(value = "字段描述")
	private String propertyDesc = "";
	@ApiModelProperty(value = "字段值")
	private String propertyValue = "";

	public String getPropertyDesc() {
		return propertyDesc;
	}

	public void setPropertyDesc(String propertyDesc) {
		this.propertyDesc = propertyDesc;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
}
