package com.crm.work.mapper;

import com.crm.work.model.PlanInfo;
import com.crm.work.model.PlanInfoCriteria;
import java.util.List;

import com.crm.work.model.pojo.PlanSearchCondition;
import com.crm.work.model.vo.PlanInfoVo;
import org.apache.ibatis.annotations.Param;

public interface PlanInfoMapper {
    int countByExample(PlanInfoCriteria example);

    int deleteByExample(PlanInfoCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(PlanInfo record);

    int insertSelective(PlanInfo record);

    List<PlanInfo> selectByExample(PlanInfoCriteria example);

    PlanInfoVo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PlanInfo record, @Param("example") PlanInfoCriteria example);

    int updateByExample(@Param("record") PlanInfo record, @Param("example") PlanInfoCriteria example);

    int updateByPrimaryKeySelective(PlanInfo record);

    int updateByPrimaryKey(PlanInfo record);

    List<PlanInfoVo> list(PlanSearchCondition cond);

    List<Long> getCanLookRids(Long uid);
}