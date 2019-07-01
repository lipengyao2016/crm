package com.crm.work.mapper;

import com.crm.common.NewPageInfo;
import com.crm.work.model.ReportInfo;
import com.crm.work.model.ReportInfoCriteria;
import java.util.List;

import com.crm.work.model.pojo.CustomSearchCondition;
import com.crm.work.model.pojo.ReportSearchCondition;
import com.crm.work.model.vo.ReportInfoVo;
import org.apache.ibatis.annotations.Param;

public interface ReportInfoMapper {
    int countByExample(ReportInfoCriteria example);

    int deleteByExample(ReportInfoCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(ReportInfo record);

    int insertSelective(ReportInfo record);

    List<ReportInfo> selectByExample(ReportInfoCriteria example);

    ReportInfoVo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ReportInfo record, @Param("example") ReportInfoCriteria example);

    int updateByExample(@Param("record") ReportInfo record, @Param("example") ReportInfoCriteria example);

    int updateByPrimaryKeySelective(ReportInfo record);

    int updateByPrimaryKey(ReportInfo record);

    List<ReportInfoVo> list(ReportSearchCondition cond);

    List<Long> getCanLookRids(Long uid);
}