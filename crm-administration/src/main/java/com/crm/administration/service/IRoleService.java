package com.crm.administration.service;

import com.crm.administration.model.dto.RoleDto;
import com.crm.administration.model.pojo.RoleSearchCondition;
import com.crm.common.NewPageInfo;
import com.crm.vo.RoleVo;

import java.util.List;

public interface IRoleService {
    public List<RoleVo> getRolesByUserId(Long uid);

    public void create(RoleDto dto) throws Exception;

    public void update(RoleDto dto);

    public NewPageInfo<RoleVo> list(RoleSearchCondition cond);

    public RoleVo get(Long rid);

    public void delete(Long rid);
}
