package com.crm.administration.mapper;

import com.crm.administration.model.RoleCriteria;
import com.crm.administration.model.pojo.RoleSearchCondition;
import com.crm.bean.Role;
import java.util.List;

import com.crm.vo.RoleVo;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper {
    int countByExample(RoleCriteria example);

    int deleteByExample(RoleCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleCriteria example);

    RoleVo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleCriteria example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleCriteria example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    public List<RoleVo> getRolesByUserId(Long uid);

    public List<RoleVo> list(RoleSearchCondition cond);
}