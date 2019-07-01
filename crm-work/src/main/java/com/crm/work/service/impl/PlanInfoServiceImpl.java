package com.crm.work.service.impl;

import com.crm.common.MessageException;
import com.crm.common.NewPageInfo;
import com.crm.feign.basic.BasicFeignCommon;
import com.crm.utils.DBEntityUtils;
import com.crm.vo.basic.PropertyConfigVo;
import com.crm.work.mapper.PlanCustomFollowMapper;
import com.crm.work.mapper.PlanInfoMapper;
import com.crm.work.mapper.PlanPropertyMapper;
import com.crm.work.model.PlanCustomFollow;
import com.crm.work.model.PlanProperty;
import com.crm.work.model.dto.CustomFollowDto;
import com.crm.work.model.dto.PlanInfoDto;
import com.crm.work.model.pojo.PlanSearchCondition;
import com.crm.work.model.vo.PlanInfoVo;
import com.crm.work.model.vo.PlanInfoVo;
import com.crm.work.service.ICustomFollowService;
import com.crm.work.service.IPlanInfoService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class PlanInfoServiceImpl implements IPlanInfoService {

    @Autowired
    PlanInfoMapper planMapper;
    @Autowired
    ICustomFollowService followService;
    @Autowired
    private PlanPropertyMapper planPropertyMapper;
    @Autowired
    private DBEntityUtils dbEntityUtils;
    @Autowired
    private BasicFeignCommon basicFeignCommon;
    @Autowired
    private PlanCustomFollowMapper planCustomFollowMapper;
    @Override
    public NewPageInfo<PlanInfoVo> list(PlanSearchCondition cond) throws Exception {
        PageHelper.startPage(cond.getPageNum(), cond.getPageSize());
        List<PlanInfoVo> list = planMapper.list(cond);
        return new NewPageInfo<PlanInfoVo>(list);
}



    @Override
    public PlanInfoVo get(Long id) throws Exception {
        PlanInfoVo reportInfo = planMapper.selectByPrimaryKey(id);
        return reportInfo;
    }

    @Override
    public void insert(PlanInfoDto record) throws Exception {
        dbEntityUtils.initialEntity(record);
        planMapper.insertSelective(record);
        Long pid = record.getId();
        Map<Integer,String> propertyIdList = record.getPropertyIdList();

        //多人指标
        if(record.getPattern()==2 && propertyIdList != null && propertyIdList.size()>0){
            //拉取动态字段
            List<PropertyConfigVo> plan = basicFeignCommon.queryPropertyConfigFeign(record.getCreatedBy(), "planInfo", null, null);
            Map<Integer, String> collect = plan.stream().collect(Collectors.toMap(PropertyConfigVo::getPropertyConfigSeqId, p -> p.getPropertyDesc()));
            //写入
            propertyIdList.forEach((k,v)->{
                PlanProperty temp=new PlanProperty();
                try {
                    dbEntityUtils.initialEntity(temp);
                } catch (Exception e) {
                    throw new MessageException("初始化动态字段配置任务写入失败，请联系管理员！");
                }
                temp.setPid(pid);
                temp.setPropertySeqId(k);
                temp.setPropertyName(collect.get(k));
                temp.setTarget(v);
                planPropertyMapper.insert(temp);
            });

        }else if(record.getPattern()==1){
            //单人指派
            CustomFollowDto customFollowDto = record.getCustomFollowDto();
            followService.insert(customFollowDto);

            PlanCustomFollow planCustomFollow = new PlanCustomFollow();
            dbEntityUtils.initialEntity(planCustomFollow);
            planCustomFollow.setPid(pid);
            planCustomFollow.setFollowId(customFollowDto.getId());
            planCustomFollow.setCustomStage(customFollowDto.getCustomerStage());
            planCustomFollowMapper.insert(planCustomFollow);
        }else{
            throw new MessageException("请检查模式配置，多人指标需存在动态字段");
        }

    }

    @Override
    public void update(PlanInfoDto record) throws Exception {
        dbEntityUtils.preUpdate(record);
        planMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void delete(Long id) throws Exception {
        planMapper.deleteByPrimaryKey(id);
    }
}
