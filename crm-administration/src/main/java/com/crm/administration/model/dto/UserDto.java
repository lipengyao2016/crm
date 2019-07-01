package com.crm.administration.model.dto;

import com.crm.administration.model.User;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class UserDto extends User {
    @ApiModelProperty(value = "角色编码List",example = "")
    private List<String> roleUuidList;

    public List<String> getRoleUuidList() {
        return roleUuidList;
    }

    public void setRoleUuidList(List<String> roleUuidList) {
        this.roleUuidList = roleUuidList;
    }
}
