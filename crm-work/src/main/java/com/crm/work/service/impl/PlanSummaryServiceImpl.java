package com.crm.work.service.impl;

import com.crm.common.NewPageInfo;
import com.crm.dto.basic.CommonDictionaryDto;
import com.crm.feign.basic.BasicFeignCommon;
import com.crm.utils.DBEntityUtils;
import com.crm.utils.DateTimeUtil;
import com.crm.work.mapper.CustomFollowMapper;
import com.crm.work.mapper.PlanCustomFollowMapper;
import com.crm.work.mapper.PlanInfoMapper;
import com.crm.work.mapper.PlanSummaryMapper;
import com.crm.work.model.PlanCustomFollow;
import com.crm.work.model.dto.CustomFollowDto;
import com.crm.work.model.dto.PlanSummaryAttributeDto;
import com.crm.work.model.dto.PlanSummaryDto;
import com.crm.work.model.pojo.CustomFollowSearchCondition;
import com.crm.work.model.pojo.PlanSummarySearchCondition;
import com.crm.work.model.vo.CustomFollowVo;
import com.crm.work.model.vo.CustomInfoVo;
import com.crm.work.model.vo.PlanInfoVo;
import com.crm.work.model.vo.PlanSummaryVo;
import com.crm.work.service.ICustomFollowService;
import com.crm.work.service.IPlanSummaryService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PlanSummaryServiceImpl implements IPlanSummaryService {
    @Autowired
    BasicFeignCommon basicFeignCommon;
    @Autowired
    DBEntityUtils dbEntityUtils;
    @Autowired
    private PlanSummaryMapper planSummaryMapper;
    @Autowired
    private PlanCustomFollowMapper planCustomFollowMapper;
    @Autowired
    private CustomFollowMapper customFollowMapper;
    @Autowired
    private PlanInfoMapper planInfoMapper;
    @Override
    public NewPageInfo<PlanSummaryVo> list(PlanSummarySearchCondition cond) throws Exception {
        //查询可以查看的计划总结
        List<Long> longs = planSummaryMapper.canLookSummaryByUser(cond.getCreatedBy());
        cond.setRids(longs);

        PageHelper.startPage(cond.getPageNum(), cond.getPageSize());
        List<PlanSummaryVo> list = planSummaryMapper.list(cond);
        return new NewPageInfo<PlanSummaryVo>(list);
    }

    @Override
    public PlanSummaryVo get(Long id) throws Exception {
        return planSummaryMapper.get(id);
    }

    @Override
    public void insert(PlanSummaryDto record) throws Exception {
        dbEntityUtils.initialEntity(record);
        this.doHandleUpdateRecord(record);
        planSummaryMapper.insert(record);
    }

    @Override
    public void update(PlanSummaryDto record) throws Exception {
        dbEntityUtils.preUpdate(record);
        this.doHandleUpdateRecord(record);
        planSummaryMapper.updateByPrimaryKey(record);
    }

    @Override
    public void delete(Long id) throws Exception {
        planSummaryMapper.delete(id);
        planSummaryMapper.deleteBySummnaryAttribute(id);
        planCustomFollowMapper.deleteByPlanSummaryId(id);
    }

    private void doHandleUpdateRecord(PlanSummaryDto record){
        Long psid=record.getId();
        Long pid = record.getPid();
        PlanInfoVo planInfoVo = planInfoMapper.selectByPrimaryKey(pid);
        if(planInfoVo.getPattern()==1){
            //单人指派模式
            List<CustomFollowDto> customFollowDtoList = record.getCustomFollowDto();
            for (CustomFollowDto customFollowDto:customFollowDtoList) {
                PlanCustomFollow planCustomFollow = new PlanCustomFollow();
                planCustomFollow.setFollowId(customFollowDto.getId());
                planCustomFollow.setCustomStage(customFollowDto.getCustomerStage());
                planCustomFollow.setNotFinishedCause(customFollowDto.getNotFinishedCause());
                planCustomFollowMapper.insert(planCustomFollow);
            }
            if(record.getType()==2){
                //处理跟进数据融入总结中
                this.doHandleSummaryDesc(record,planInfoVo.getCustomId());
            }
        }else{
            //多人指标
            Map<Integer, String> propertyList = record.getPropertyList();
            propertyList.forEach((k,v)->{
                PlanSummaryAttributeDto attributeDto=new PlanSummaryAttributeDto();
                attributeDto.setPpid(k);
                attributeDto.setPsid(psid);
                attributeDto.setValue(v);
                planSummaryMapper.insertSummnaryAttribute(attributeDto);
            });
            if(record.getType()==2){
                this.doHandleSummaryDesc(record,planInfoVo.getCustomId());
            }
        }
    }
    /**
     * 处理跟进数据融入总结中
     * @param record
     * @param customId
     */
    private void doHandleSummaryDesc(PlanSummaryDto record,Long customId){
        String[] basicArray=new String[]{"customerStage"};
        Map<String, Map<String, CommonDictionaryDto>> dictionaryMap = basicFeignCommon.getDictionaryMap(basicArray, null);
        Map<String, CommonDictionaryDto> customerStage = dictionaryMap.get("customerStage");
        //拉取当日成交记录入总结中
        CustomFollowSearchCondition cond =new CustomFollowSearchCondition();
        cond.setCustomId(customId);
        cond.setStartTime(DateTimeUtil.getStringDateShort());
        List<CustomFollowVo> customFollowVolist = customFollowMapper.list(cond);
        StringBuilder desc=new StringBuilder("-----------------客户跟进情况总结------------------\n" +
                " 客户名称\t\t  跟进时间\t\t\t  跟进状态\t\t  跟进结果\t\t");
        for (CustomFollowVo customFollowVo: customFollowVolist) {
            CustomInfoVo customInfoVo = customFollowVo.getCustomInfoVo();
            desc.append(customInfoVo.getCustomerName()).append("\t\t").append(customFollowVo.getFollowDt()).append("\t\t\t")
                    .append(customerStage.get(customFollowVo.getCustomerStage()).getPropertyValue()).append(customFollowVo.getRemark()).append("\n");
        }
        desc.append(record.getRemark());
        record.setRemark(desc.toString());
    }
}
