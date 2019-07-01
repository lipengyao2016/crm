package com.crm.work.service;

import com.crm.common.NewPageInfo;
import com.crm.work.model.dto.CustomInfoDto;
import com.crm.work.model.pojo.CustomSearchCondition;
import com.crm.work.model.vo.CustomInfoVo;

public interface ICustomService {
    public NewPageInfo<CustomInfoVo> list(CustomSearchCondition cond)throws Exception;

    public CustomInfoVo get(Long id)throws Exception;

    public void insert(CustomInfoDto record) throws Exception;

    public void update(CustomInfoDto record)throws Exception;

    public void delete(Long id)throws Exception;

}
