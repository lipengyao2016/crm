package com.crm.work.service.impl;

import com.crm.common.ECode;
import com.crm.common.MessageException;
import com.crm.common.NewPageInfo;
import com.crm.utils.DBEntityUtils;
import com.crm.work.mapper.CustomInfoMapper;
import com.crm.work.model.CustomInfo;
import com.crm.work.model.dto.CustomInfoDto;
import com.crm.work.model.pojo.CustomSearchCondition;
import com.crm.work.model.vo.CustomInfoVo;
import com.crm.work.service.ICustomService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomServiceImpl implements ICustomService {
    @Autowired
    private DBEntityUtils dbEntityUtils;
    @Autowired
    private CustomInfoMapper customInfoMapper;
    @Override
    public NewPageInfo<CustomInfoVo> list(CustomSearchCondition cond)throws Exception {
        PageHelper.startPage(cond.getPageNum(), cond.getPageSize());
        List<CustomInfoVo> list = customInfoMapper.list(cond);
        return new NewPageInfo<CustomInfoVo>(list);
    }

    @Override
    public CustomInfoVo get(Long id)throws Exception {
        CustomInfoVo result=new CustomInfoVo();
        CustomInfo customInfo = customInfoMapper.selectByPrimaryKey(id);
        if(customInfo == null){
           throw new MessageException(ECode.NO_DATA_RESULT);
        }
        BeanUtils.copyProperties(customInfo,result);

        //拉去历史跟进记录 --由于是列表，单独调用接口处理

        return result;
    }

    @Override
    public void insert(CustomInfoDto record) throws Exception {
        dbEntityUtils.initialEntity(record);
        CustomInfo newDto=new CustomInfo();
        BeanUtils.copyProperties(record,newDto);
        customInfoMapper.insertSelective(record);
    }

    @Override
    public void update(CustomInfoDto record)throws Exception {
        CustomInfo newDto=new CustomInfo();
        BeanUtils.copyProperties(record,newDto);
        customInfoMapper.updateByPrimaryKeySelective(newDto);
    }

    @Override
    public void delete(Long id)throws Exception {
        customInfoMapper.deleteByPrimaryKey(id);
    }
}
