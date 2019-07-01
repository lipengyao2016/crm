package com.crm.work.mapper;

import java.util.List;

import com.crm.work.model.CustomFollow;
import com.crm.work.model.CustomFollowCriteria;
import com.crm.work.model.pojo.CustomFollowSearchCondition;
import com.crm.work.model.vo.CustomFollowVo;
import org.apache.ibatis.annotations.Param;

public interface CustomFollowMapper {
    int countByExample(CustomFollowCriteria example);

    int deleteByExample(CustomFollowCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(CustomFollow record);

    int insertSelective(CustomFollow record);

    List<CustomFollow> selectByExample(CustomFollowCriteria example);

    CustomFollowVo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CustomFollow record, @Param("example") CustomFollowCriteria example);

    int updateByExample(@Param("record") CustomFollow record, @Param("example") CustomFollowCriteria example);

    int updateByPrimaryKeySelective(CustomFollow record);

    int updateByPrimaryKey(CustomFollow record);

    List<CustomFollowVo> list(CustomFollowSearchCondition cond);


}