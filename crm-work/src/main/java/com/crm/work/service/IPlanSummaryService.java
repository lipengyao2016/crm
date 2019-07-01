package com.crm.work.service;

import com.crm.common.NewPageInfo;
import com.crm.work.model.dto.PlanInfoDto;
import com.crm.work.model.dto.PlanSummaryDto;
import com.crm.work.model.pojo.PlanSearchCondition;
import com.crm.work.model.pojo.PlanSummarySearchCondition;
import com.crm.work.model.vo.PlanSummaryVo;

public interface IPlanSummaryService {
    public NewPageInfo<PlanSummaryVo> list(PlanSummarySearchCondition cond)throws Exception;

    public PlanSummaryVo get(Long id)throws Exception;

    public void insert(PlanSummaryDto record) throws Exception;

    public void update(PlanSummaryDto record)throws Exception;

    public void delete(Long id)throws Exception;


}
