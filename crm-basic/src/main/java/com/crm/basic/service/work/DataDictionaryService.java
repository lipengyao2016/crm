package com.crm.basic.service.work;

import java.util.List;
import java.util.Map;

import com.crm.basic.model.CommonDictionaryCondition;
import com.crm.basic.model.CommonDictionaryVo;
import com.crm.basic.model.DictionaryConfigDto;
import com.crm.common.NewPageInfo;
import com.crm.common.SearchCondition;
import com.crm.dto.basic.CommonDictionaryDto;
import com.crm.common.NewPageInfo;
import com.crm.common.SearchCondition;
import com.crm.dto.basic.CommonDictionaryDto;

public interface DataDictionaryService {

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
	 * 查询数据字典列表(从数据库中查询)
	 * 
	 * @param cond
	 * @return
	 */
	public NewPageInfo<CommonDictionaryVo> findCommonDictionaryList(CommonDictionaryCondition cond);

	/**
	 * 查询数据字典列表(从缓存中查询)
	 * 
	 * @param cond
	 * @return
	 */
	// public NewPageInfo<CommonDictionaryVo>
	// findCommonDictionaryListByCache(CommonDictionaryCondition cond);

	/**
	 * 根据数据字典名列表查询对应的数据字典
	 * 
	 * @return
	 */
	public Map<String, Map<String, CommonDictionaryDto>> findCommonDictionaryMap(String[] dictionaryNameList,
                                                                                 String status, Long agencyId);

	/**
	 * 根据数据字典名列表查询对应的数据字典
	 * 
	 * @param dictionaryNameList
	 * @param status
	 * @return
	 */
	public Map<String, List<CommonDictionaryDto>> findCommonDictionaryList(String[] dictionaryNameList, String status,
			Long agencyId);

	/**
	 * 新增数据字典信息
	 * 
	 * @param dictionary
	 */
	public void insertCommonDictionary(CommonDictionaryDto dictionary);

	/**
	 * 修改数据字典信息
	 * 
	 * @param dictionary
	 */
	public void updateCommonDictionary(CommonDictionaryDto dictionary);

	/**
	 * 编辑数据字典及下拉框，维护非共用数据字典和经销商/厂家之间的关系
	 * 
	 * @param dto
	 */
	public boolean editDictionaryConfig(DictionaryConfigDto dto, String ifSuperAdmin, Long agencyId);

	/**
	 * 查询数据字典详细
	 * 
	 * @param dictionaryName
	 * @return
	 */
	DictionaryConfigDto findDictionaryConfigDetail(String dictionaryName, Long agencyId, String ifSuperAdmin);

}
