package com.crm.work.mapper;

import com.crm.common.NewPageInfo;
import com.crm.work.model.PlanCustomFollow;
import com.crm.work.model.PlanSummary;
import com.crm.work.model.PlanSummaryCriteria;
import java.util.List;

import com.crm.work.model.dto.PlanSummaryAttributeDto;
import com.crm.work.model.pojo.PlanSummarySearchCondition;
import com.crm.work.model.vo.PlanSummaryVo;
import org.apache.ibatis.annotations.Param;

public interface PlanSummaryMapper {
    int countByExample(PlanSummaryCriteria example);

    int deleteByExample(PlanSummaryCriteria example);

    int insert(PlanSummary record);

    int insertSelective(PlanSummary record);

    List<PlanSummary> selectByExample(PlanSummaryCriteria example);

    int updateByExampleSelective(@Param("record") PlanSummary record, @Param("example") PlanSummaryCriteria example);

    int updateByExample(@Param("record") PlanSummary record, @Param("example") PlanSummaryCriteria example);

    public List<PlanSummaryVo> list(PlanSummarySearchCondition cond);

    public PlanSummaryVo get(Long id);

    int insertSummnaryAttribute(PlanSummaryAttributeDto record);

    int updateByPrimaryKey(PlanSummary record);

    int delete(Long id);

    int deleteBySummnaryAttribute(Long id);

    List<Long> canLookSummaryByUser(Long uid);
}