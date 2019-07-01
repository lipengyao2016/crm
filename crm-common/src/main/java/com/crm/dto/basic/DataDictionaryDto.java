package com.crm.dto.basic;

import com.crm.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel("动态字段信息")
public class DataDictionaryDto extends BaseDto {

	@ApiModelProperty(value = "动态配置下拉菜单属性seqId", example = "0")
	private Integer dataDictionarySeqId;

	@ApiModelProperty(value = "动态配置属性seqId", example = "1", hidden = true)
	private Integer propertyConfigSeqId;

	@ApiModelProperty(value = "下拉菜单属性描述", example = "男", required = true)
	private String dataName;

	@ApiModelProperty(value = "下拉菜单属性值", example = "M", required = true)
	private String dataValue;

	@ApiModelProperty(value = "是否启用", example = "Y", hidden = true)
	private String status;

	@ApiModelProperty(value = "排序号", example = "1")
	private Integer sequenceNum;

	@ApiModelProperty(value = "经销商编码", example = "boyin")
	private Long agencyId;

	public Long getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
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