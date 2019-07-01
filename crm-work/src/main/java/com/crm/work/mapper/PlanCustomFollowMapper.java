package com.crm.work.mapper;

import com.crm.work.model.PlanCustomFollow;
import com.crm.work.model.PlanCustomFollowCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlanCustomFollowMapper {
    int countByExample(PlanCustomFollowCriteria example);

    int deleteByExample(PlanCustomFollowCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(PlanCustomFollow record);

    int insertSelective(PlanCustomFollow record);

    List<PlanCustomFollow> selectByExample(PlanCustomFollowCriteria example);

    PlanCustomFollow selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PlanCustomFollow record, @Param("example") PlanCustomFollowCriteria example);

    int updateByExample(@Param("record") PlanCustomFollow record, @Param("example") PlanCustomFollowCriteria example);

    int updateByPrimaryKeySelective(PlanCustomFollow record);

    int updateByPrimaryKey(PlanCustomFollow record);

    int deleteByPlanSummaryId(Long id);
}