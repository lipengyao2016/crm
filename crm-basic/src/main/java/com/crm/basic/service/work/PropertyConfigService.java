package com.crm.basic.service.work;

import java.util.List;

import com.crm.common.NewPageInfo;
import com.crm.common.SearchCondition;
import com.crm.dto.basic.DataDictionaryDto;
import com.crm.dto.basic.PropertyConfigDto;
import com.crm.vo.basic.PropertyConfigVo;
import com.crm.common.NewPageInfo;
import com.crm.common.SearchCondition;
import com.crm.dto.basic.DataDictionaryDto;
import com.crm.dto.basic.PropertyConfigDto;
import com.crm.vo.basic.PropertyConfigVo;
import org.springframework.web.bind.annotation.PathVariable;

public interface PropertyConfigService {

	/**
	 * 分页查询
	 */
	public NewPageInfo<?> list(SearchCondition cond) throws Exception;

	/**
	 * 根据主键查询详情
	 */
	public Object get(Object key) throws Exception;

	/**
	 * 根据主键删除
	 */
	public void delete(Object key) throws Exception;

	/**
	 * 新增
	 */
	public void insert(Object dto) throws Exception;

	/**
	 * 修改
	 */
	public void update(Object dto) throws Exception;

	/**
	 * 查询动态配置
	 * 
	 * @param permissionId
	 * @param permissionType
	 * @param status
	 * @return
	 */
	public List<PropertyConfigVo> findPropertyConfigList(String permissionType, String permissionId, String ifForbidden, String status, Long agencyId);
	
	/**
	 * 查询固定配置
	 * 
	 * @param permissionType
	 * @param status
	 * @return
	 */
	public List<PropertyConfigVo> findSystemPropertyConfigList(String permissionId, String permissionType, String status);

	/**
	 * 新增动态配置数据
	 * 
	 * @param dto
	 * @return
	 * @throws Exception 
	 */
	public PropertyConfigDto insertPropertyConfig(PropertyConfigDto dto) throws Exception;

	/**
	 * 新增动态配置的下拉框数据
	 * 
	 * @param dto
	 * @return
	 */
	public DataDictionaryDto insertDataDictionary(DataDictionaryDto dto);

	/**
	 * 修改动态配置数据
	 * 
	 * @param dto
	 * @return
	 */
	public PropertyConfigDto updatePropertyConfig(PropertyConfigDto dto);

	/**
	 * 修改动态配置的下拉框数据
	 * 
	 * @param dto
	 * @return
	 */
	public DataDictionaryDto updateDataDictionary(DataDictionaryDto dto);

	/**
	 * 根据表名反查菜单id
	 *
	 * @param tableName
	 * @return
	 *//*
	public String findPermissionByTableName(String tableName);*/

	/**
	 * 根据字段配置表id组合输入类型查
	 * 
	 * @param propertyConfigSeqIdArray
	 * @param inputType
	 * @return
	 */
	public List<PropertyConfigVo> findPropertyConfigByIds(String propertyConfigSeqIdArray, String inputType,
			String arrtValue, Long agencyId);

	/**
	 * 保存自定义属性
	 * 
	 * @param dto
	 * @return
	 */
	public PropertyConfigDto savePropertyConfig(PropertyConfigDto dto) throws Exception;
	
//	/**
//	 * 保存自定义属性,MQ队列消息传递
//	 *
//	 * @param dto
//	 * @return
//	 */
//	public void savePropertyConfigByMQ(List<PropertyConfigDto> dto) throws Exception;

	/**
	 * 系统属性列表
	 * 
	 * @return
	 */
	public NewPageInfo<PropertyConfigVo> listSystemProperty(SearchCondition condition) throws Exception;

	/**
	 * 获取单个动态配置项
	 * 
	 * @param id
	 * @param agencyId
	 * @return
	 * @throws Exception
	 */
	public PropertyConfigVo getSystemProperty(String id, Long agencyId) throws Exception;

	/**
	 * 根据经销商保存固定字段下拉列表配置项
	 * 
	 * @param agencyId
	 * @return
	 */
	public int saveDataDictionary(Long agencyId, PropertyConfigVo propertyConfigVo) throws Exception;
	
	/**
	 * 
	 * 查询某个模块重复的自定义字段
	 * @param agencyId
	 * @param permissionType
	 * @param propertyNames
	 * @return
	 */
	public List<PropertyConfigDto> queryExsitByPermission(Long agencyId,String permissionType,
			String propertyNames)throws Exception;
}
