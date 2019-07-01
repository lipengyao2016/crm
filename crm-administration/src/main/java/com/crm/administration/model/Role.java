package com.crm.administration.model;

import com.crm.dto.BaseDto;
import com.crm.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("角色模型")
public class Role extends BaseDto {
    @ApiModelProperty("角色编码")
    private Long id;
    @ApiModelProperty("角色名称")
    private String name;
    @ApiModelProperty(value = "是否系统角色",example = "N")
    private String isSystem;
    @ApiModelProperty("角色描述")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}