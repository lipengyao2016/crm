package com.crm.vo.basic;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel
public class PropertyConfigVo {

	@ApiModelProperty(value = "字段id")
	private Integer propertyConfigSeqId=0;

	@ApiModelProperty(value = "动态配置类型")
	private String permissionType="";
	
	@ApiModelProperty(value = "动态配置关联id")
	private String permissionId="";

	@ApiModelProperty(value = "字段名")
	private String propertyName="";

	@ApiModelProperty(value = "字段描述")
	private String propertyDesc="";

	@ApiModelProperty(value = "字段值类型")
	private String propertyType="";

	@ApiModelProperty(value = "字段单位")
	private String propertyUnit="";
	
	@ApiModelProperty(value = "排序")
	private Integer sequenceNum=0;
	
	@ApiModelProperty(value = "提示")
	private String pointOut="";
	
	@ApiModelProperty(value = "最小长度")
	private Integer textMinLength=0;

	@ApiModelProperty(value = "最大长度")
	private Integer textMaxLength=0;

	@ApiModelProperty(value = "显示行数")
	private Integer textRow=0;

	@ApiModelProperty(value = "列数")
	private Integer inputCol=0;

	@ApiModelProperty(value = "是否不可配置")
	private String ifForbidden="";

	@ApiModelProperty(value = "是否必须")
	private String isNecessary="";

	@ApiModelProperty(value = "是否启用")
	private String status="";

	@ApiModelProperty(value = "字段值")
	private String propertyValue="";
	
	@ApiModelProperty(value = "下拉框选项")
	private List<DataDictionaryVo> dataDictionaryList=new ArrayList<>();
	@ApiModelProperty(value = "已选中的值组")
	private List<String> dataDictionaryList2=new ArrayList<>();
	
	@ApiModelProperty(value = "字段值类型中文名称")
	private String propertyTypeName="";
	
	@ApiModelProperty(value = "是否必填中文名称")
	private String isNecessaryName="";
	
	@ApiModelProperty(value = "动态字段汇总字符串")
	private String propertyStr;
	
	@ApiModelProperty(value = "分区编码")
	private String partitionNo;
	
	public String getPartitionNo() {
		return partitionNo;
	}

	public void setPartitionNo(String partitionNo) {
		this.partitionNo = partitionNo;
	}

	public String getPropertyTypeName() {
		return propertyTypeName;
	}

	public void setPropertyTypeName(String propertyTypeName) {
		this.propertyTypeName = propertyTypeName;
	}

	public String getIsNecessaryName() {
		return isNecessaryName;
	}

	public void setIsNecessaryName(String isNecessaryName) {
		this.isNecessaryName = isNecessaryName;
	}

	public String getPropertyStr() {
		return propertyStr;
	}

	public void setPropertyStr(String propertyStr) {
		this.propertyStr = propertyStr;
	}

	public List<String> getDataDictionaryList2() {
		return dataDictionaryList2;
	}

	public void setDataDictionaryList2(List<String> dataDictionaryList2) {
		this.dataDictionaryList2 = dataDictionaryList2;
	}

	public List<DataDictionaryVo> getDataDictionaryList() {
		return dataDictionaryList;
	}

	public void setDataDictionaryList(List<DataDictionaryVo> dataDictionaryList) {
		this.dataDictionaryList = dataDictionaryList;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

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

}