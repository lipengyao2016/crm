package com.crm.basic.mapper.work;

import java.util.List;
import java.util.Map;

import com.crm.basic.mapper.BasicMapper;
import com.crm.dto.basic.DataDictionaryDto;
import com.crm.dto.basic.PropertyConfigDto;
import com.crm.vo.basic.DataDictionaryVo;
import com.crm.vo.basic.PropertyConfigVo;
import com.crm.dto.basic.DataDictionaryDto;
import com.crm.dto.basic.PropertyConfigDto;
import com.crm.vo.basic.PropertyConfigVo;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

public interface PropertyConfigMapper<T> extends BasicMapper<T> {

	@SelectProvider(type = PropertyConfigProvider.class, method = "findPropertyConfigList")
	public List<PropertyConfigVo> findPropertyConfigList(@Param("partitionNo") Long partitionNo);

	@SelectProvider(type = PropertyConfigProvider.class, method = "findDataDictionaryList")
	public List<DataDictionaryVo> findDataDictionaryList(@Param("partitionNo") Long partitionNo);

	@InsertProvider(type = PropertyConfigProvider.class, method = "insertPropertyConfig")
	@Options(useGeneratedKeys = true, keyProperty = "propertyConfigSeqId", keyColumn = "property_config_seq_id")
	public Integer insertPropertyConfig(PropertyConfigDto dto);

	@InsertProvider(type = PropertyConfigProvider.class, method = "insertDataDictionary")
													 
	@Options(useGeneratedKeys = true, keyProperty = "dataDictionarySeqId", keyColumn = "data_dictionary_seq_id")
	public Integer insertDataDictionaryDto(DataDictionaryDto dto);

	@UpdateProvider(type = PropertyConfigProvider.class, method = "updatePropertyConfig")
	public void updatePropertyConfig(PropertyConfigDto dto);

	@UpdateProvider(type = PropertyConfigProvider.class, method = "updateDataDictionary")
	public void updateDataDictionary(DataDictionaryDto dto);

	/**
	 * 根据字段配置id组、字段输入类型、查出字段配置数组
	 * 
	 * @param propertyConfigSeqIdArray
	 * @return
	 */
	public List<PropertyConfigVo> findPropertyConfigByIds(
			@Param("propertyConfigSeqIdArray") String propertyConfigSeqIdArray,
			@Param("propertyType") String propertyType, @Param("attrValue") String attrValue,
			@Param("agencyId") Long agencyId, @Param("partitionNo") Long partitionNo);

	/**
	 * 查询模板使用的固定字段列表
	 */
	public List queryWorkLogTemplatePropertyConfigList1(@Param("workLogTemplateId") Integer workLogTemplateId,
			@Param("partitionNo") Long partitionNo);

	/**
	 * 查询模板使用的自定义字段列表
	 */
	public List queryWorkLogTemplatePropertyConfigList2(@Param("workLogTemplateId") Integer workLogTemplateId,
			@Param("partitionNo") Long partitionNo);

	/**
	 * 查询所有日志固定字段
	 */
	public List queryWorkLogTemplatePropertyConfigList3(@Param("partitionNo") Long partitionNo);

	/**
	 * 删除日志模板的自定义字段
	 */
	public void deleteByWorkLogTemplateId(@Param("permissionId") Integer permissionId,
			@Param("partitionNo") Long partitionNo);
	
	/**
	 * 根据分类获取其动态字段和所有基础数据
	 * 
	 * @param productClassifyId
	 * @return
	 */
	public List<PropertyConfigVo> findPropertyConfigByClassIfy(@Param("productClassifyId") String productClassifyId,
			@Param("isShow") String isShow, @Param("ifForbidden") String ifForbidden,
			@Param("agencyId") Long agencyId, @Param("partitionNo") Long partitionNo);

	/**
	 * 根据动态配置属性id删除下拉列表数据
	 * 
	 * @param propertyConfigSeqId
	 * @return
	 */
	public int deleteDataDictionaryByPropertyId(@Param("propertyConfigSeqId") Integer propertyConfigSeqId,
			@Param("partitionNo") Long partitionNo);

	/**
	 * 判断字段名称是否重复
	 * 
	 * @param propertyName
	 * @return
	 */
	public Map propertyNameExists(@Param("propertyName") String propertyName, @Param("partitionNo") Long partitionNo);

	/**
	 * 根据分类获取其动态字段和所有基础数据
	 * 
	 * @return
	 */
	public List<PropertyConfigVo> findPropertyConfigByPermissionIdArray(@Param("permissionType") String permissionType,
			@Param("permissionIdArray") String permissionIdArray, @Param("ifForbidden") String ifForbidden,
			@Param("agencyId") Long agencyId, @Param("partitionNo") Long partitionNo);

	/**
	 * 系统属性列表
	 * 
	 * @return
	 */
	public List<PropertyConfigVo> listSystemProperty(Map<String, Object> param);

	/**
	 * 系统默认字段列表数量
	 * 
	 * @return
	 */
	public int listSystemPropertyCount(@Param("propertyType") String propertyType,
			@Param("partitionNo") Long partitionNo);

	/**
	 * 获取单个固定配置项
	 */
	public PropertyConfigVo getSystemProperty(@Param("id") String id, @Param("agencyId") Long agencyId);

	/**
	 * 根据经销商id和属性id清除下拉列表中的数据
	 * 
	 * @param agencyId
	 * @param propertySeqId
	 * @return
	 */
	public int deleteDataDictionaryByagencyId(@Param("agencyId") Long agencyId,
			@Param("properydSeqId") Integer propertySeqId);
	
	/**
	 * 校检自定义字段组的英文名称是否重复
	 * @return
	 */
	public String queryPropertyNamesIsExists(@Param("propertyNames")String propertyNames);
	
	/**
	 * 删除某个模板的自定义字段
	 */
	public void deleteByPermission(@Param("permissionType") String permissionType,@Param("permissionId") String permissionId,@Param("partitionNo") Long partitionNo);
	
	/**
	 * 查询某个模块重复的自定义字段
	 */
	public List<PropertyConfigVo> queryExsitByPermission(@Param("permissionType") String permissionType,@Param("partitionNo") Long partitionNo,@Param("propertyNames") String propertyNames);
	
	@SelectProvider(type = PropertyConfigProvider.class, method = "findMaxPropertyName")
	public String findMaxPropertyName(@Param("propertyName") String propertyName);
	
	/**
	 * 查询系统初始化配置固定字段及其下拉数据
	 * @return
	 */
	public List<PropertyConfigVo> findSystemPropertyConfigList();
}