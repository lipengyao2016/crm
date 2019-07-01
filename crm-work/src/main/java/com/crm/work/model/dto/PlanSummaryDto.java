package com.crm.work.model.dto;

import com.crm.work.model.PlanSummary;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

@ApiModel("计划总结编辑类")
public class PlanSummaryDto extends PlanSummary {

    @ApiModelProperty("汇报人编码")
    private List<Long> uidList;

    @ApiModelProperty("动态字段，k/v")
    private Map<Integer,String> propertyList;

    @ApiModelProperty("单人模式，跟进信息")
    private List<CustomFollowDto> customFollowDto;

    public List<CustomFollowDto> getCustomFollowDto() {
        return customFollowDto;
    }

    public void setCustomFollowDto(List<CustomFollowDto> customFollowDto) {
        this.customFollowDto = customFollowDto;
    }

    public List<Long> getUidList() {
        return uidList;
    }

    public void setUidList(List<Long> uidList) {
        this.uidList = uidList;
    }

    public Map<Integer, String> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(Map<Integer, String> propertyList) {
        this.propertyList = propertyList;
    }
}
