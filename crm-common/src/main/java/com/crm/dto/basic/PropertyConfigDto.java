package com.crm.dto.basic;

import java.util.ArrayList;
import java.util.List;

import com.crm.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel
public class PropertyConfigDto extends BaseDto {

	@ApiModelProperty(value = "动态配置属性seqId", example = "0")
	private Integer propertyConfigSeqId;

	@ApiModelProperty(value = "动态配置类型")
	private String permissionType = "";

	@ApiModelProperty(value = "权限Id或其他动态配置关联id", example = "1")
	private String permissionId;

	@ApiModelProperty(value = "属性名称(在数据库中保存的字段值)", example = "DESINER_HOBBY", required = true)
	private String propertyName;

	@ApiModelProperty(value = "属性中文描述", example = "设计师爱好", required = true)
	private String propertyDesc;

	@ApiModelProperty(value = "属性类型", example = "int")
	private String propertyType;

	@ApiModelProperty(value = "单位", example = "个")
	private String propertyUnit = "";

	@ApiModelProperty(value = "排序号", example = "1")
	private Integer sequenceNum;

	@ApiModelProperty(value = "提示语", example = "请输入...")
	private String pointOut;

	@ApiModelProperty(value = "文本最小长度(如果控件类型为text时)", example = "20")
	private Integer textMinLength;

	@ApiModelProperty(value = "文本最小长度(如果控件类型为text时)", example = "40")
	private Integer textMaxLength;

	@ApiModelProperty(value = "文本所占行数(如果控件类型为文本域textarea时)", example = "1")
	private Integer textRow;

	@ApiModelProperty(value = "控件所占列数(即控件在前端页面占去多少列，根据具体前端设计来设置，PC端目前都是占去整个一行)", example = "1")
	private Integer inputCol;

	@ApiModelProperty(value = "可否被禁用，禁用后及控件只显示，不可操作", example = "Y", hidden = true)
	private String ifForbidden;

	@ApiModelProperty(value = "是否必须", example = "Y", hidden = true)
	private String isNecessary;

	@ApiModelProperty(value = "是否启用，这里非启用状态则不会在页面显示", example = "Y", hidden = true)
	private String status;

	@ApiModelProperty(value = "是否多选")
	private String isMultiply;

	@ApiModelProperty(value = "下拉菜单值")
	private List<DataDictionaryDto> dataDictionaryList = new ArrayList<>();

	public Integer getPropertyConfigSeqId() {
		return propertyConfigSeqId;
	}

	public void setPropertyConfigSeqId(Integer propertyConfigSeqId) {
		this.propertyConfigSeqId = propertyConfigSeqId;
	}

	public String getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
	}

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyDesc() {
		return propertyDesc;
	}

	public void setPropertyDesc(String propertyDesc) {
		this.propertyDesc = propertyDesc;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getPropertyUnit() {
		return propertyUnit;
	}

	public void setPropertyUnit(String propertyUnit) {
		this.propertyUnit = propertyUnit;
	}

	public Integer getSequenceNum() {
		return sequenceNum;
	}

	public void setSequenceNum(Integer sequenceNum) {
		this.sequenceNum = sequenceNum;
	}

	public String getPointOut() {
		return pointOut;
	}

	public void setPointOut(String pointOut) {
		this.pointOut = pointOut;
	}

	public Integer getTextMinLength() {
		return textMinLength;
	}

	public void setTextMinLength(Integer textMinLength) {
		this.textMinLength = textMinLength;
	}

	public Integer getTextMaxLength() {
		return textMaxLength;
	}

	public void setTextMaxLength(Integer textMaxLength) {
		this.textMaxLength = textMaxLength;
	}

	public Integer getTextRow() {
		return textRow;
	}

	public void setTextRow(Integer textRow) {
		this.textRow = textRow;
	}

	public Integer getInputCol() {
		return inputCol;
	}

	public void setInputCol(Integer inputCol) {
		this.inputCol = inputCol;
	}

	public String getIfForbidden() {
		return ifForbidden;
	}

	public void setIfForbidden(String ifForbidden) {
		this.ifForbidden = ifForbidden;
	}

	public String getIsNecessary() {
		return isNecessary;
	}

	public void setIsNecessary(String isNecessary) {
		this.isNecessary = isNecessary;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<DataDictionaryDto> getDataDictionaryList() {
		return dataDictionaryList;
	}

	public void setDataDictionaryList(List<DataDictionaryDto> dataDictionaryList) {
		this.dataDictionaryList = dataDictionaryList;
	}

	public String getIsMultiply() {
		return isMultiply;
	}

	public void setIsMultiply(String isMultiply) {
		this.isMultiply = isMultiply;
	}

}