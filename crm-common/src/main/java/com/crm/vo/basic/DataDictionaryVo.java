package com.crm.vo.basic;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class DataDictionaryVo {

	@ApiModelProperty(value = "动态配置下拉菜单属性seqId", example = "1")
	private Integer dataDictionarySeqId = 0;

	@ApiModelProperty(value = "动态配置属性seqId", example = "1")
	private Integer propertyConfigSeqId = 0;

	@ApiModelProperty(value = "下拉菜单属性描述", example = "男")
	private String dataName = "";

	@ApiModelProperty(value = "下拉菜单属性值", example = "M")
	private String dataValue = "";

	@ApiModelProperty(value = "是否启用", example = "Y")
	private String status = "";

	@ApiModelProperty(value = "排序号", example = "1")
	private Integer sequenceNum = 0;

	@ApiModelProperty(value = "是否选中", example = "Y")
	private String isSelect = "N";	
	
	@ApiModelProperty(value = "经销商编码", example = "boyin")
	private Long agencyId;
	
	public Long getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}
	
	public String getIsSelect() {
		return isSelect;
	}

	public void setIsSelect(String isSelect) {
		this.isSelect = isSelect;
	}

	public Integer getDataDictionarySeqId() {
		return dataDictionarySeqId;
	}

	public void setDataDictionarySeqId(Integer dataDictionarySeqId) {
		this.dataDictionarySeqId = dataDictionarySeqId;
	}

	public Integer getPropertyConfigSeqId() {
		return propertyConfigSeqId;
	}

	public void setPropertyConfigSeqId(Integer propertyConfigSeqId) {
		this.propertyConfigSeqId = propertyConfigSeqId;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName == null ? null : dataName.trim();
	}

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue == null ? null : dataValue.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public Integer getSequenceNum() {
		return sequenceNum;
	}

	public void setSequenceNum(Integer sequenceNum) {
		this.sequenceNum = sequenceNum;
	}
}