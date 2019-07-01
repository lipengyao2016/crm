package com.crm.administration.service;

import com.crm.administration.model.UserRole;

import java.util.List;

public interface IUserRoleService{

    public List<UserRole> getUserRoles(Long userID);

    public int batchInsert(List<UserRole> list);

    public int batchDelete(List<Long> list);

}
