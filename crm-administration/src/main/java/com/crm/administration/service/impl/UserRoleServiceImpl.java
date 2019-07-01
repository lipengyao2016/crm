package com.crm.administration.service.impl;

import com.crm.administration.mapper.UserRoleMapper;
import com.crm.administration.model.UserRole;
import com.crm.administration.service.IUserRoleService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements IUserRoleService {
    private Log log = LogFactory.getLog(UserRoleServiceImpl.class);

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public int batchInsert(List<UserRole> list) {
       return this.userRoleMapper.batchInsert(list);
    }

    @Override
    public int batchDelete(List<Long> list) {
        return this.userRoleMapper.batchDelete(list);
    }


    @Override
    public List<UserRole> getUserRoles(Long userID) {
        UserRole UserRole=new UserRole();
        UserRole.setUserId(userID);
        List<UserRole> userRoles = userRoleMapper.selectByUserId(userID);
        return userRoles;
    }
}
