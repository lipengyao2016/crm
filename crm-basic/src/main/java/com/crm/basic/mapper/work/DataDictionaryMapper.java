package com.crm.basic.mapper.work;

import java.util.List;

import com.crm.basic.mapper.BasicMapper;
import com.crm.basic.model.CommonDictionaryCondition;
import com.crm.basic.model.CommonDictionaryVo;
import com.crm.basic.model.DictionaryConfigDto;
import com.crm.dto.basic.CommonDictionaryDto;
import com.crm.dto.basic.CommonDictionaryDto;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

@SuppressWarnings("rawtypes")
public interface DataDictionaryMapper<T> extends BasicMapper<T> {

	@SelectProvider(type = DataDictionaryProvider.class, method = "findCommonDictionaryVoList")
	public List<CommonDictionaryVo> findCommonDictionaryVoList(CommonDictionaryCondition cond);

	@SelectProvider(type = DataDictionaryProvider.class, method = "findCommonDictionaryList")
	public List<CommonDictionaryDto> findCommonDictionaryList(@Param("dictionaryNames") String dictionaryNames,
															  @Param("agencyId") String agencyId);
	
	@SelectProvider(type = DataDictionaryProvider.class, method = "findCommonDictionary")
	public List<CommonDictionaryDto> findCommonDictionary(@Param("dictionaryName") String dictionaryName,
			@Param("agencyId") Long agencyId);

	@SelectProvider(type = DataDictionaryProvider.class, method = "findAllCommonDictionary")
	public List<CommonDictionaryDto> findAllCommonDictionary();

	@InsertProvider(type = DataDictionaryProvider.class, method = "insertCommonDictionary")
	public void insertCommonDictionary(CommonDictionaryDto dictionary);

	@UpdateProvider(type = DataDictionaryProvider.class, method = "updateCommonDictionary")
	public void updateCommonDictionary(CommonDictionaryDto dictionary);

	@DeleteProvider(type = DataDictionaryProvider.class, method = "deleteCommonDictionary")
	public void deleteCommonDictionary(@Param("dictionaryName") String dictionaryName);

	@InsertProvider(type = DataDictionaryProvider.class, method = "insertDictionaryConfig")
	public void insertDictionaryConfig(DictionaryConfigDto dto);

	@SelectProvider(type = DataDictionaryProvider.class, method = "findDictionaryConfigList")
	public List<DictionaryConfigDto> findDictionaryConfigList();

	@UpdateProvider(type = DataDictionaryProvider.class, method = "updateDictionaryConfig")
	public void updateDictionaryConfig(DictionaryConfigDto dto);

//	@InsertProvider(type = DataDictionaryProvider.class, method = "insertAgencyDictionary")
//	public void insertAgencyDictionary(AgencyDictionaryDto dto);
//
//	@DeleteProvider(type = DataDictionaryProvider.class, method = "deleteAgencyDictionary")
//	public void deleteAgencyDictionary(AgencyDictionaryDto dto);
//
//	@SelectProvider(type = DataDictionaryProvider.class, method = "findAgencyAllDictionaryList")
//	public List<AgencyDictionaryDto> findAgencyAllDictionaryList(@Param("agencyId") String agencyId);
//
//	@SelectProvider(type = DataDictionaryProvider.class, method = "findAgencyDictionaryList")
//	public List<AgencyDictionaryDto> findAgencyDictionaryList(@Param("agencyId") String agencyId,
//			@Param("dictionaryName") String dictionaryName);
//
//	@SelectProvider(type = DataDictionaryProvider.class, method = "findAgencyIdList")
//	public List<String> findAgencyIdList(@Param("dictionaryName") String dictionaryName,
//			@Param("propertyValue") String propertyValue);

	@SelectProvider(type = DataDictionaryProvider.class, method = "findMaxPropertyValue")
	public String findMaxPropertyValue(@Param("dictionaryName") String dictionaryName,
			@Param("propertyValue") String propertyValue);
	
	/**
	 * 查询动态字段的下拉框
	 */
	public List querySelectList(@Param("propertyConfigSeqId") Integer propertyConfigSeqId,
			@Param("partitionNo") String partitionNo);

	/**
	 * 删除动态字段的下拉框
	 */
	public void deleteBypropertyConfigSeqId(Integer propertyConfigSeqId);
}