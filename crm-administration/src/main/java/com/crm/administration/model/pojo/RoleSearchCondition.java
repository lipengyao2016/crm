package com.crm.administration.model.pojo;

import com.crm.common.SearchCondition;
import com.crm.common.SearchCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("角色检索模型")
public class RoleSearchCondition extends SearchCondition {

    @ApiModelProperty("角色名称")
    private String name;
    @ApiModelProperty("是否系统角色")
    private String isSystem;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(String isSystem) {
        this.isSystem = isSystem == null ? null : isSystem.trim();
    }

}
