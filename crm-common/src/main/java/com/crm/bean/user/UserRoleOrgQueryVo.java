package com.crm.bean.user;

import org.apache.ibatis.annotations.Param;

public class UserRoleOrgQueryVo {

    public String getOwnerUUID() {
        return ownerUUID;
    }

    public void setOwnerUUID(String ownerUUID) {
        this.ownerUUID = ownerUUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String ownerUUID;
    private String name;
}
