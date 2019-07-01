package com.crm.work.service;

import com.crm.common.NewPageInfo;
import com.crm.work.model.dto.PlanInfoDto;
import com.crm.work.model.dto.ReportInfoDto;
import com.crm.work.model.pojo.PlanSearchCondition;
import com.crm.work.model.pojo.ReportSearchCondition;
import com.crm.work.model.vo.PlanInfoVo;
import com.crm.work.model.vo.ReportInfoVo;

public interface IPlanInfoService {
    public NewPageInfo<PlanInfoVo> list(PlanSearchCondition cond)throws Exception;

    public PlanInfoVo get(Long id)throws Exception;

    public void insert(PlanInfoDto record) throws Exception;

    public void update(PlanInfoDto record)throws Exception;

    public void delete(Long id)throws Exception;
}
