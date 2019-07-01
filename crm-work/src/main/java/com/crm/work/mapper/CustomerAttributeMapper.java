package com.crm.work.mapper;

import java.util.List;

import com.crm.work.model.CustomerAttribute;
import com.crm.work.model.CustomerAttributeCriteria;
import org.apache.ibatis.annotations.Param;

public interface CustomerAttributeMapper {
    int countByExample(CustomerAttributeCriteria example);

    int deleteByExample(CustomerAttributeCriteria example);

    int deleteByPrimaryKey(Long cid);

    int insert(CustomerAttribute record);

    int insertSelective(CustomerAttribute record);

    List<CustomerAttribute> selectByExample(CustomerAttributeCriteria example);

    CustomerAttribute selectByPrimaryKey(Long cid);

    int updateByExampleSelective(@Param("record") CustomerAttribute record, @Param("example") CustomerAttributeCriteria example);

    int updateByExample(@Param("record") CustomerAttribute record, @Param("example") CustomerAttributeCriteria example);

    int updateByPrimaryKeySelective(CustomerAttribute record);

    int updateByPrimaryKey(CustomerAttribute record);
}