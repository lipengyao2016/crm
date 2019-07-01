package com.crm.administration.service.impl;

import com.crm.administration.mapper.RoleMapper;
import com.crm.administration.model.dto.RoleDto;
import com.crm.administration.model.pojo.RoleSearchCondition;
import com.crm.administration.service.IRoleService;
import com.crm.common.NewPageInfo;
import com.crm.utils.DBEntityUtils;
import com.crm.vo.RoleVo;
import com.github.pagehelper.PageHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {
    private Log log = LogFactory.getLog(RoleServiceImpl.class);

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private DBEntityUtils dbEntityUtils;

    @Override
    public List<RoleVo> getRolesByUserId(Long uid) {
        return roleMapper.getRolesByUserId(uid);
    }

    @Override
    public void create(RoleDto dto) throws Exception {
        dbEntityUtils.initialEntity(dto);
        roleMapper.insertSelective(dto);
    }

    @Override
    public void update(RoleDto dto) {
        roleMapper.updateByPrimaryKeySelective(dto);
    }

    @Override
    public NewPageInfo<RoleVo> list(RoleSearchCondition cond) {

        PageHelper.startPage(cond.getPageNum(), cond.getPageSize());
        List<RoleVo> list = roleMapper.list(cond);

        return new NewPageInfo(list);
    }

    @Override
    public RoleVo get(Long rid) {
        return roleMapper.selectByPrimaryKey(rid);
    }

    @Override
    public void delete(Long rid) {
        roleMapper.deleteByPrimaryKey(rid);
    }
}
