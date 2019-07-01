package com.crm.basic.model;

import java.util.ArrayList;
import java.util.List;

import com.crm.dto.BaseDto;
import com.crm.dto.basic.CommonDictionaryDto;
import com.crm.dto.basic.CommonDictionaryDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel(value="数据字典名")
public class DictionaryConfigDto extends BaseDto {
	@ApiModelProperty(value = "数据字典名称", example = "COOPERATE_TYPE")
	private String dictionaryName = "";
	@ApiModelProperty(value = "数据字典名称中文描述", example = "合作类型")
	private String dictionaryDesc = "";
	@ApiModelProperty(value = "是否启用", example = "Y")
	private String status = "";
	@ApiModelProperty(value = "数据字典下拉列表")
	private List<CommonDictionaryDto> dictionaryDtoList = new ArrayList<>();

	public String getDictionaryName() {
		return dictionaryName;
	}

	public void setDictionaryName(String dictionaryName) {
		this.dictionaryName = dictionaryName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<CommonDictionaryDto> getDictionaryDtoList() {
		return dictionaryDtoList;
	}

	public void setDictionaryDtoList(List<CommonDictionaryDto> dictionaryDtoList) {
		this.dictionaryDtoList = dictionaryDtoList;
	}

	public String getDictionaryDesc() {
		return dictionaryDesc;
	}

	public void setDictionaryDesc(String dictionaryDesc) {
		this.dictionaryDesc = dictionaryDesc;
	}

}
