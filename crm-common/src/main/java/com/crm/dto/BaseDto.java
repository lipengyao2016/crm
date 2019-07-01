package com.crm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@SuppressWarnings("serial")
@ApiModel("基础Dto模型")
public class BaseDto implements IDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(hidden=true,value = "创建时间",example = "2019-6-12 10:04:25")
    private Date createdDt;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(hidden=true,value = "修改时间",example = "2019-6-12 10:04:25")
    private Date updatedDt;
    @ApiModelProperty(value = "创建人",example = "10329104781")
    private Long createdBy;
    @ApiModelProperty(value = "修改人",example = "10329104781")
    private Long updatedBy;
    @ApiModelProperty(value = "状态",example = "Y")
    private String status;
    @ApiModelProperty(value = "数据分区",example = "")
    private Long partitionNo;

    public Long getPartitionNo() {
        return partitionNo;
    }

    public void setPartitionNo(Long partitionNo) {
        this.partitionNo = partitionNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    public Date getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }
}
