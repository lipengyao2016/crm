package com.crm.vo;

import com.crm.dto.BaseDto;
import com.crm.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class UserVo extends BaseDto {
    @ApiModelProperty(hidden=true,example = "")
    private Long id;

    @ApiModelProperty(value="登录账户",example = "zhoujs")
    private String userLoginId;

    @ApiModelProperty(value="职位",example = "")
    private Long positionSeqId;

    @ApiModelProperty(value="名称",example = "周靖松")
    private String name;

    @ApiModelProperty(value="密码",example = "123456")
    private String currentPassword;

    @ApiModelProperty(value="性别",example = "M")
    private String gender;

    @ApiModelProperty(value="电话",example = "15323494048")
    private String phone;

    @ApiModelProperty(value="邮箱",example = "15323494048@qq.com")
    private String email;

    @ApiModelProperty(value="微信",example = "15323494048")
    private String weixin;

    @ApiModelProperty(value="附件",example = "")
    private String attachmentKey;

    @ApiModelProperty(value="部门编码",example = "1")
    private String departmentId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="入职时间",example = "2019-07-11 00:00:00")
    private Date entryDate;

    @ApiModelProperty(hidden=true,example = "")
    private Date lastLoginTime;
    @ApiModelProperty(hidden=true,example = "")
    private String lastLoginIp;
    @ApiModelProperty(hidden=true,example = "")
    private String lastLoginChannel;
    @ApiModelProperty(value="角色集合",example = "")
    private List<RoleVo> roleVoList;
    @ApiModelProperty(value="角色名称集",example = "")
    private String roleNameStr;

    public String getRoleNameStr() {
        return roleNameStr;
    }

    public void setRoleNameStr(String roleNameStr) {
        this.roleNameStr = roleNameStr;
    }

    public List<RoleVo> getRoleVoList() {
        return roleVoList;
    }

    public void setRoleVoList(List<RoleVo> roleVoList) {
        this.roleVoList = roleVoList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId == null ? null : userLoginId.trim();
    }

    public Long getPositionSeqId() {
        return positionSeqId;
    }

    public void setPositionSeqId(Long positionSeqId) {
        this.positionSeqId = positionSeqId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword == null ? null : currentPassword.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin == null ? null : weixin.trim();
    }

    public String getAttachmentKey() {
        return attachmentKey;
    }

    public void setAttachmentKey(String attachmentKey) {
        this.attachmentKey = attachmentKey == null ? null : attachmentKey.trim();
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    public String getLastLoginChannel() {
        return lastLoginChannel;
    }

    public void setLastLoginChannel(String lastLoginChannel) {
        this.lastLoginChannel = lastLoginChannel == null ? null : lastLoginChannel.trim();
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }
}
