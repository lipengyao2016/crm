package com.crm.administration.service.impl;


import com.crm.administration.mapper.UserMapper;
import com.crm.administration.mapper.UserRoleMapper;
import com.crm.administration.model.User;
import com.crm.administration.model.UserRole;
import com.crm.administration.model.dto.UserDto;
import com.crm.administration.model.pojo.UserSearchCondition;
import com.crm.administration.service.IRoleService;
import com.crm.administration.service.IUserRoleService;
import com.crm.administration.service.IUserService;
import com.crm.common.NewPageInfo;
import com.crm.utils.DBEntityUtils;
import com.crm.vo.RoleVo;
import com.crm.vo.UserVo;
import com.github.pagehelper.PageHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    private Log log = LogFactory.getLog(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DBEntityUtils dbEntityUtils;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private IRoleService roleService;

    @Transactional()
    @Override
    public User create(UserDto dto)throws Exception {
        User newUser = this.userDtoToUserEntiry(dto);
        userMapper.insertSelective(newUser);
        User user =userMapper.selectByPrimaryKey(dto.getId());
                //插入角色
        dto.setId(user.getId());
        this.batchInsertUserRole(dto);
        return user;
    }

    @Override
    public User update(UserDto user) {

        //读取原有角色编码
        List<UserRole> userRoles = userRoleService.getUserRoles(user.getId());
        List<Long> roleIdList=new ArrayList<Long>();
        for (UserRole userRole:userRoles) {
            roleIdList.add(userRole.getId());
        }

        //删除所有角色
        if(userRoles.size()>0){
            userRoleMapper.batchDelete(roleIdList);
        }
        userMapper.updateByPrimaryKeySelective(user);
        return userMapper.selectByPrimaryKey(user.getId());
    }



    @Override
    public int batchInsertCustom(List<UserDto> dtoList) {
        List<User> userList=new ArrayList<User>();
        for (UserDto user:
        dtoList) {
            //处理角色插入
            this.batchInsertUserRole(user);
            userList.add(user);
        }
        return this.userMapper.batchInsert(userList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public NewPageInfo<UserVo> list(UserSearchCondition ucond) {
        PageHelper.startPage(ucond.getPageNum(), ucond.getPageSize());
//        handleOrganization(ucond);
        List<UserVo> list = userMapper.list(ucond);

        list.forEach(u -> {
            //处理组织架构
//            setOrganization(u);

            //处理角色
            List<RoleVo> roles = roleService.getRolesByUserId(u.getId());
            String roleStr = roles.stream().map(r -> r.getName()).collect(Collectors.joining(","));
            u.setRoleNameStr(roleStr);
        });
        //附件处理
//        DaoUtil.doHandelAttachment(list, "imglist");
        return new NewPageInfo<UserVo>(list);
    }

    @Override
    public UserVo getUserById(Long id) {
        UserVo userVo=new UserVo();
        User user = userMapper.selectByPrimaryKey(id);

        //处理角色
        List<RoleVo> roles = roleService.getRolesByUserId(id);
        return userVo;
    }

    @Override
    public void delete(long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    private void batchInsertUserRole(UserDto dto){
        List userRoleList=new ArrayList<UserRole>();
        for(String roleUuid : dto.getRoleUuidList())
        {
            UserRole userRoleTemp=new UserRole();
            dbEntityUtils.preCreate(userRoleTemp);
            userRoleTemp.setUserId(dto.getId());
            userRoleList.add(userRoleTemp);
        }

        if(userRoleList.size() > 0)
        {
            this.userRoleMapper.batchInsert(userRoleList);
        }
    }
    private User userDtoToUserEntiry(UserDto dto) throws Exception {
        User newUser = new User();
//        String[] excludeAtts ={"ownerUUID","roleUUID","userRoleMemberShips"};
        BeanUtils.copyProperties(dto, newUser);
        dbEntityUtils.initialEntity(newUser);
        return newUser;
    }
}
