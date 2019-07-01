package com.crm.administration.mapper;

import com.crm.administration.model.PermissionCriteria;
import com.crm.administration.model.pojo.PermissionSearchCondition;
import com.crm.dto.PermissionDto;
import com.crm.entity.Permission;
import com.crm.vo.PermissionVo;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface PermissionMapper {
    int countByExample(PermissionCriteria example);

    int deleteByExample(PermissionCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(PermissionDto record);

    int insertSelective(PermissionDto record);

    List<PermissionVo> selectByExample(PermissionCriteria example);

    PermissionVo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Permission record, @Param("example") PermissionCriteria example);

    int updateByExample(@Param("record") Permission record, @Param("example") PermissionCriteria example);

    int updateByPrimaryKeySelective(PermissionDto record);

    int updateByPrimaryKey(PermissionDto record);

    List<PermissionVo> list(PermissionSearchCondition cond);

    List<PermissionVo> permissionByUser(PermissionSearchCondition cond);

    int hasPermission(Long uid, Long merchantId, String permissionUrl, String requestMethod,
                                 String pcMenuUrl);

}