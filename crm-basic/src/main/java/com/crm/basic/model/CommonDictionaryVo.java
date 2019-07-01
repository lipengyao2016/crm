package com.crm.basic.model;

import java.util.ArrayList;
import java.util.List;

import com.crm.dto.basic.CommonDictionaryDto;
import com.crm.dto.basic.CommonDictionaryDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class CommonDictionaryVo {
	@ApiModelProperty(value = "数据字典名称", example = "COOPERATE_TYPE")
	private String dictionaryName = "";
	@ApiModelProperty(value = "数据字典名称中文描述", example = "合作类型")
	private String dictionaryDesc = "";
	@ApiModelProperty(value = "整个数据字典是否启用", example = "Y")
	private String available = "";
	@ApiModelProperty(value = "数据字典下拉列表")
	private List<CommonDictionaryDto> dictionaryList = new ArrayList<>();

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

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public List<CommonDictionaryDto> getDictionaryList() {
		return dictionaryList;
	}

	public void setDictionaryList(List<CommonDictionaryDto> dictionaryList) {
		this.dictionaryList = dictionaryList;
	}
}
