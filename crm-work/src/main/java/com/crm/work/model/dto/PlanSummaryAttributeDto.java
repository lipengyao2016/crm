package com.crm.work.model.dto;

import com.crm.work.model.PlanSummary;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

public class PlanSummaryAttributeDto {
    private Integer ppid;
    private Long psid;
    private String value;

    public Integer getPpid() {
        return ppid;
    }

    public void setPpid(Integer ppid) {
        this.ppid = ppid;
    }

    public Long getPsid() {
        return psid;
    }

    public void setPsid(Long psid) {
        this.psid = psid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
