package com.crm.work.model.dto;

import com.crm.work.model.PlanInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

@ApiModel("任务新增编辑类")
public class PlanInfoDto extends PlanInfo {

    @ApiModelProperty(value = "动态字段k,v")
    private Map<Integer,String> propertyIdList;

    @ApiModelProperty(value = "跟进阶段编码",hidden = true)
    private Long customFollowId;

    @ApiModelProperty("客户跟进信息")
    private CustomFollowDto customFollowDto;

    public CustomFollowDto getCustomFollowDto() {
        return customFollowDto;
    }

    public void setCustomFollowDto(CustomFollowDto customFollowDto) {
        this.customFollowDto = customFollowDto;
    }

    public Map<Integer, String> getPropertyIdList() {
        return propertyIdList;
    }

    public void setPropertyIdList(Map<Integer, String> propertyIdList) {
        this.propertyIdList = propertyIdList;
    }

    public Long getCustomFollowId() {
        return customFollowId;
    }

    public void setCustomFollowId(Long customFollowId) {
        this.customFollowId = customFollowId;
    }
}
