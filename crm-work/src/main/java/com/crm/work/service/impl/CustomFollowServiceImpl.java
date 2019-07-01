package com.crm.work.service.impl;

import com.crm.common.ECode;
import com.crm.common.MessageException;
import com.crm.common.NewPageInfo;
import com.crm.utils.DBEntityUtils;
import com.crm.work.mapper.CustomFollowMapper;
import com.crm.work.model.CustomFollow;
import com.crm.work.model.dto.CustomFollowDto;
import com.crm.work.model.pojo.CustomFollowSearchCondition;
import com.crm.work.model.vo.CustomFollowVo;
import com.crm.work.service.ICustomFollowService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomFollowServiceImpl implements ICustomFollowService {

    @Autowired
    private DBEntityUtils dbEntityUtils;
    @Autowired
    private CustomFollowMapper customFollowMapper;
    @Override
    public NewPageInfo<CustomFollowVo> list(CustomFollowSearchCondition cond) {
        PageHelper.startPage(cond.getPageNum(), cond.getPageSize());
        List<CustomFollowVo> list = customFollowMapper.list(cond);
        return new NewPageInfo<CustomFollowVo>(list);
    }

    @Override
    public CustomFollowVo get(Long id) {
        CustomFollowSearchCondition cond=new CustomFollowSearchCondition();
        List<CustomFollowVo> list = customFollowMapper.list(cond);
        if(list==null || list.size()==0){
            throw  new MessageException(ECode.NO_DATA_RESULT);
        }
        return list.get(0);
    }

    @Override
    public void insert(CustomFollowDto record) throws Exception {
        CustomFollow newDto = new CustomFollow();
        BeanUtils.copyProperties(record,newDto);
        dbEntityUtils.initialEntity(newDto);
        customFollowMapper.insertSelective(newDto);
    }

    @Override
    public void update(CustomFollowDto record) {
        CustomFollow newDto = new CustomFollow();
        BeanUtils.copyProperties(record,newDto);
        dbEntityUtils.preUpdate(newDto);
        customFollowMapper.updateByPrimaryKeySelective(newDto);
    }

    @Override
    public void delete(Long id) {
        customFollowMapper.deleteByPrimaryKey(id);
    }
}
