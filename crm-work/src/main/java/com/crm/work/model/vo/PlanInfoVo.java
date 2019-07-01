package com.crm.work.model.vo;

import com.crm.work.model.PlanInfo;

import java.util.List;

public class PlanInfoVo extends PlanInfo {
    private List<Long> uidList;

    public List<Long> getUidList() {
        return uidList;
    }

    public void setUidList(List<Long> uidList) {
        this.uidList = uidList;
    }
}
