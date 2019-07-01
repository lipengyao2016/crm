package com.crm.work.model.vo;

import com.crm.work.model.CustomInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("客户展示模型")
public class CustomInfoVo extends CustomInfo {

    @ApiModelProperty("客户当前跟进状态")
    private int customerStage;

    @ApiModelProperty("客户状态描述")
    private String customerStageName;

    @ApiModelProperty("客户分类描述")
    private String assortmentName;

    @ApiModelProperty("客户来源描述")
    private String sourceName;

}
