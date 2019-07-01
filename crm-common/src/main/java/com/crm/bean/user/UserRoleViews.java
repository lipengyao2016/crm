package com.crm.bean.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class UserRoleViews {
    private String name;

    private String owneruuid;

    private String mobile;

    private String roleuuid;

    private String useruuid;

    //出参时间格式化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    //入参时，请求报文只需要传入yyyymmddhhmmss字符串进来，则自动转换为Date类型数据
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date usercreateat;

    //出参时间格式化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    //入参时，请求报文只需要传入yyyymmddhhmmss字符串进来，则自动转换为Date类型数据
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date usermodifiedat;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getOwneruuid() {
        return owneruuid;
    }

    public void setOwneruuid(String owneruuid) {
        this.owneruuid = owneruuid == null ? null : owneruuid.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getRoleuuid() {
        return roleuuid;
    }

    public void setRoleuuid(String roleuuid) {
        this.roleuuid = roleuuid == null ? null : roleuuid.trim();
    }

    public String getUseruuid() {
        return useruuid;
    }

    public void setUseruuid(String useruuid) {
        this.useruuid = useruuid == null ? null : useruuid.trim();
    }

    public Date getUsercreateat() {
        return usercreateat;
    }

    public void setUsercreateat(Date usercreateat) {
        this.usercreateat = usercreateat;
    }

    public Date getUsermodifiedat() {
        return usermodifiedat;
    }

    public void setUsermodifiedat(Date usermodifiedat) {
        this.usermodifiedat = usermodifiedat;
    }
}