package com.crm.work.model.vo;

import com.crm.work.model.PlanInfo;
import com.crm.work.model.PlanSummary;

import java.util.List;

public class PlanSummaryVo extends PlanSummary {
    private List<Long> uidList;

    private PlanInfoVo planInfoVo;

    public PlanInfoVo getPlanInfoVo() {
        return planInfoVo;
    }

    public void setPlanInfoVo(PlanInfoVo planInfoVo) {
        this.planInfoVo = planInfoVo;
    }

    public List<Long> getUidList() {
        return uidList;
    }

    public void setUidList(List<Long> uidList) {
        this.uidList = uidList;
    }
}
