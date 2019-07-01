package com.crm.work.model;

import com.crm.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel("客户原型")
public class CustomInfo extends BaseDto{
    private Long id;

    @ApiModelProperty("客户名称")
    private String customerName;

    @ApiModelProperty(value = "性别",example = "M")
    private String gender;

    @ApiModelProperty("年龄")
    private Short age;

    @ApiModelProperty("客户来源编码")
    private Short source;

    @ApiModelProperty(value = "客户分类编码",example = "A")
    private String assortment;

    @ApiModelProperty(value = "客户状态",example = "Y")
    private String status;

    @ApiModelProperty("描述")
    private String remark;

    @ApiModelProperty("报备人")
    private Long reportedBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="报备时间",example = "2019-07-11 00:00:00")
    private Date reportDt;

    @ApiModelProperty("客户归属人")
    private Long owner;

    @ApiModelProperty("客户职位")
    private Short position;

    @ApiModelProperty("客户电话号码")
    private String phone;

    @ApiModelProperty("客户微信号码")
    private String weixin;

    @ApiModelProperty("客户成交可能性")
    private Short intention;

    private Integer pid;

    private Integer cid;

    private Integer aid;

    @ApiModelProperty("客户人员规模")
    private Short personnelScale;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public Short getAge() {
        return age;
    }

    public void setAge(Short age) {
        this.age = age;
    }

    public Short getSource() {
        return source;
    }

    public void setSource(Short source) {
        this.source = source;
    }

    public String getAssortment() {
        return assortment;
    }

    public void setAssortment(String assortment) {
        this.assortment = assortment == null ? null : assortment.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(Long reportedBy) {
        this.reportedBy = reportedBy;
    }

    public Date getReportDt() {
        return reportDt;
    }

    public void setReportDt(Date reportDt) {
        this.reportDt = reportDt;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public Short getPosition() {
        return position;
    }

    public void setPosition(Short position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin == null ? null : weixin.trim();
    }

    public Short getIntention() {
        return intention;
    }

    public void setIntention(Short intention) {
        this.intention = intention;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Short getPersonnelScale() {
        return personnelScale;
    }

    public void setPersonnelScale(Short personnelScale) {
        this.personnelScale = personnelScale;
    }
}