package com.crm.vo;

import com.crm.entity.Permission;
import com.crm.entity.Permission;

import java.util.List;

public class PermissionVo extends Permission {
    private Boolean hasNext;

    private List<PermissionVo> childPermissionList;

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<PermissionVo> getChildPermissionList() {
        return childPermissionList;
    }

    public void setChildPermissionList(List<PermissionVo> childPermissionList) {
        this.childPermissionList = childPermissionList;
    }

}