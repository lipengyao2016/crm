package com.crm.dto.basic;

import java.util.HashMap;
import java.util.Map;

import com.crm.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "数据字典")
public class CommonDictionaryDto extends BaseDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "数据字典名称", example = "COOPERATE_TYPE")
	private String dictionaryName = "";
	@ApiModelProperty(value = "属性值", required = true, example = "SHOPPING_GUIDE")
	private String propertyValue = "";
	@ApiModelProperty(value = "属性内容", required = true, example = "导购")
	private String propertyText = "";
	@ApiModelProperty(value = "是否启用", example = "Y")
	private String status = "Y";
	@ApiModelProperty(value = "排序号", required = true, example = "1")
	private Integer sequenceNum = 1;
	@ApiModelProperty(value = "是否共用", example = "Y")
	private String isCommon = "";
	@ApiModelProperty(value = "所属经销商", hidden = true)
	private Long agencyId = null;
	// @ApiModelProperty(value = "经销商数据字典信息")
	// private Map<String,String> agencyMap = new HashMap<>();

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getSequenceNum() {
		return sequenceNum;
	}

	public void setSequenceNum(Integer sequenceNum) {
		this.sequenceNum = sequenceNum;
	}

	public String getDictionaryName() {
		return dictionaryName;
	}

	public void setDictionaryName(String dictionaryName) {
		this.dictionaryName = dictionaryName;
	}

	public String getIsCommon() {
		return isCommon;
	}

	public void setIsCommon(String isCommon) {
		this.isCommon = isCommon;
	}

	// public Map<String, String> getAgencyMap() {
	// return agencyMap;
	// }
	//
	// public void setAgencyMap(Map<String, String> agencyMap) {
	// this.agencyMap = agencyMap;
	// }

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

}
