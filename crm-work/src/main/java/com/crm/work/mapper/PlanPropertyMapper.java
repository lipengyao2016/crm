package com.crm.work.mapper;

import com.crm.work.model.PlanProperty;
import com.crm.work.model.PlanPropertyCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlanPropertyMapper {
    int countByExample(PlanPropertyCriteria example);

    int deleteByExample(PlanPropertyCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(PlanProperty record);

    int insertSelective(PlanProperty record);

    List<PlanProperty> selectByExample(PlanPropertyCriteria example);

    PlanProperty selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PlanProperty record, @Param("example") PlanPropertyCriteria example);

    int updateByExample(@Param("record") PlanProperty record, @Param("example") PlanPropertyCriteria example);

    int updateByPrimaryKeySelective(PlanProperty record);

    int updateByPrimaryKey(PlanProperty record);
}