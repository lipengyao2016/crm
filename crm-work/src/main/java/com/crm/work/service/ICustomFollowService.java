package com.crm.work.service;

import com.crm.common.NewPageInfo;
import com.crm.work.model.dto.CustomFollowDto;
import com.crm.work.model.pojo.CustomFollowSearchCondition;
import com.crm.work.model.vo.CustomFollowVo;

public interface ICustomFollowService {
    public NewPageInfo<CustomFollowVo> list(CustomFollowSearchCondition cond);

    public CustomFollowVo get(Long id);

    public void insert(CustomFollowDto record) throws Exception;

    public void update(CustomFollowDto record);

    public void delete(Long id);
}
