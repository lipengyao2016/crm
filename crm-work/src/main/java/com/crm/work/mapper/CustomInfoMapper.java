package com.crm.work.mapper;

import java.util.List;

import com.crm.work.model.CustomInfo;
import com.crm.work.model.CustomInfoCriteria;
import com.crm.work.model.pojo.CustomSearchCondition;
import com.crm.work.model.vo.CustomInfoVo;
import org.apache.ibatis.annotations.Param;

public interface CustomInfoMapper {
    int countByExample(CustomInfoCriteria example);

    int deleteByExample(CustomInfoCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(CustomInfo record);

    int insertSelective(CustomInfo record);

    List<CustomInfo> selectByExample(CustomInfoCriteria example);

    CustomInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CustomInfo record, @Param("example") CustomInfoCriteria example);

    int updateByExample(@Param("record") CustomInfo record, @Param("example") CustomInfoCriteria example);

    int updateByPrimaryKeySelective(CustomInfo record);

    int updateByPrimaryKey(CustomInfo record);

    List<CustomInfoVo> list(CustomSearchCondition cond);
}