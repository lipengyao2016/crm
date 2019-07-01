package com.crm.vo;

import java.util.ArrayList;
import java.util.List;

public class UserLoginInfoVo {
    private String userLoginId;
    private String ifSuperAdmin;
    private String ifAdmin;
    private Long agencyId;
    private Long id;
    private String name;
    private String phone;
    private String token;
    private String shaHexPwd;
    private String status;
    private String currentPassword;
    private String imageString;
    private List<RoleVo> roleList = new ArrayList<>();

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShaHexPwd() {
        return shaHexPwd;
    }

    public void setShaHexPwd(String shaHexPwd) {
        this.shaHexPwd = shaHexPwd;
    }

    public String getIfAdmin() {
        return ifAdmin;
    }

    public void setIfAdmin(String ifAdmin) {
        this.ifAdmin = ifAdmin;
    }

    public String getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }

    public String getIfSuperAdmin() {
        return ifSuperAdmin;
    }

    public void setIfSuperAdmin(String ifSuperAdmin) {
        this.ifSuperAdmin = ifSuperAdmin;
    }

    public Long getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Long agencyId) {
        this.agencyId = agencyId;
    }

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
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<RoleVo> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleVo> roleList) {
        this.roleList = roleList;
    }
}
