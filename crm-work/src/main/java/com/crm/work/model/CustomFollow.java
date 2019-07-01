package com.crm.work.model;

import com.crm.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel("跟进状态原型")
public class CustomFollow extends BaseDto{
    private Long id;

    @ApiModelProperty("客户编码")
    private Long cid;

    @ApiModelProperty("客户当前状态")
    private Short customerStage;

    @ApiModelProperty("跟进人")
    private Long followUid;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="跟进时间",example = "2019-07-11 00:00:00")
    private Date followDt;

    @ApiModelProperty("附件编码")
    private String attachementKey;

    @ApiModelProperty("活动内容")
    private String remark;

    @ApiModelProperty(value = "阶段是否完成",example = "Y")
    private String isFinish;

    @ApiModelProperty("协助人")
    private Long helpUid;

    @ApiModelProperty("下一次计划信息编码")
    private Long nextId;

    @ApiModelProperty("计划内容")
    private String planDesc;

    public String getPlanDesc() {
        return planDesc;
    }

    public void setPlanDesc(String planDesc) {
        this.planDesc = planDesc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Short getCustomerStage() {
        return customerStage;
    }

    public void setCustomerStage(Short customerStage) {
        this.customerStage = customerStage;
    }

    public Long getFollowUid() {
        return followUid;
    }

    public void setFollowUid(Long followUid) {
        this.followUid = followUid;
    }

    public Date getFollowDt() {
        return followDt;
    }

    public void setFollowDt(Date followDt) {
        this.followDt = followDt;
    }

    public String getAttachementKey() {
        return attachementKey;
    }

    public void setAttachementKey(String attachementKey) {
        this.attachementKey = attachementKey == null ? null : attachementKey.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(String isFinish) {
        this.isFinish = isFinish == null ? null : isFinish.trim();
    }

    public Long getHelpUid() {
        return helpUid;
    }

    public void setHelpUid(Long helpUid) {
        this.helpUid = helpUid;
    }

    public Long getNextId() {
        return nextId;
    }

    public void setNextId(Long nextId) {
        this.nextId = nextId;
    }
}