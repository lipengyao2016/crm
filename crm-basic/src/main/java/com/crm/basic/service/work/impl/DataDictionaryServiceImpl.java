package com.crm.basic.service.work.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.crm.basic.service.CacheDataService;
import com.crm.basic.service.work.DataDictionaryService;
import com.crm.common.ECode;
import com.crm.common.MessageException;
import com.crm.common.NewPageInfo;
import com.crm.common.SearchCondition;
import com.crm.dto.basic.CommonDictionaryDto;
import com.crm.utils.RandUtil;
import com.crm.vo.basic.DataDictionaryVo;
import com.crm.basic.mapper.work.DataDictionaryMapper;
import com.crm.basic.model.CommonDictionaryCondition;
import com.crm.basic.model.CommonDictionaryVo;
import com.crm.basic.model.DictionaryConfigDto;
import com.crm.common.ECode;
import com.crm.common.MessageException;
import com.crm.common.NewPageInfo;
import com.crm.common.SearchCondition;
import com.crm.dto.basic.CommonDictionaryDto;
import com.crm.utils.RandUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
@Service
public class DataDictionaryServiceImpl implements DataDictionaryService {

	@Autowired
	private DataDictionaryMapper dataDictionaryMapper;

	@Autowired
	private CacheDataService cacheService;

	public NewPageInfo<CommonDictionaryVo> findCommonDictionaryList(CommonDictionaryCondition cond) {
		List<CommonDictionaryVo> voList = PageHelper.startPage(cond.getPageNum(), cond.getPageSize());
		NewPageInfo<CommonDictionaryVo> pageInfo = new NewPageInfo<CommonDictionaryVo>(
				dataDictionaryMapper.findCommonDictionaryVoList(cond));

		if (voList != null && voList.size() > 0) {
			Map<String, Map<String, CommonDictionaryDto>> commonDictionaryMap = cacheService.findAllCommonDictionary();
			for (CommonDictionaryVo vo : voList) {
				List<CommonDictionaryDto> list = new ArrayList();
				Map<String, CommonDictionaryDto> map = commonDictionaryMap.get(vo.getDictionaryName());

				// 非超级管理员则只能查询自己特有的非公共数据字典值
				if (!"SUPER_ADMIN".equals(cond.getRoleId())) {
					if (map != null && map.size() > 0) {
						// List<AgencyDictionaryDto> selfList = cacheService
						// .findAgencyAllDictionaryList(cond.getAgencyId());
						for (CommonDictionaryDto dto : map.values()) {
							if ("Y".equals(dto.getIsCommon())) {
								list.add(dto);
							} else {
								if (cond.getAgencyId().equals(dto.getAgencyId())) {
									list.add(dto);
								}
							}
						}
						vo.setDictionaryList(list);
					}
				} else {
					if (map != null && map.size() > 0) {
						list.addAll(map.values());
						vo.setDictionaryList(list);
					}
				}
			}
		}
		return pageInfo;

	}

	@Override
	public Map<String, Map<String, CommonDictionaryDto>> findCommonDictionaryMap(String[] dictionaryNameList,
			String status, Long agencyId) {
		Map<String, Map<String, CommonDictionaryDto>> mapAll = cacheService.findAllCommonDictionary();
		if (mapAll == null || mapAll.size() == 0) {
			return new LinkedHashMap();
		}

		List<DictionaryConfigDto> configList = cacheService.findDictionaryConfigList();
		if (configList == null || configList.size() == 0) {
			return new LinkedHashMap();
		}

		Map<String, Map<String, CommonDictionaryDto>> result = new LinkedHashMap();
		for (String dictionaryName : dictionaryNameList) {
			String key = dictionaryName;
			Map<String, CommonDictionaryDto> map = mapAll.get(dictionaryName);
			if (map == null || map.size() == 0) {
				// 如果没匹配到则根据数据字典名称中文描述进行匹配
				for (DictionaryConfigDto dto : configList) {
					if (dictionaryName.equals(dto.getDictionaryDesc())) {
						map = mapAll.get(dto.getDictionaryName());
						key = dto.getDictionaryName();
						break;
					}
				}
			}

			if (map == null || map.size() == 0) {
				continue;
			}
			Map<String, CommonDictionaryDto> newMap = new LinkedHashMap();
			if (status != null && !"".equals(status)) {
				for (CommonDictionaryDto dto : map.values()) {
					if (dto.getStatus().equals(status)) {
						newMap.put(dto.getPropertyValue(), dto);
					}
				}
			} else {
				newMap = map;
			}

			// 经销商指定专用数据字典
			// List<AgencyDictionaryDto> list =
			// dataDictionaryMapper.findAgencyDictionaryList(agencyId, key);
			Map<String, CommonDictionaryDto> resultMap = new LinkedHashMap();
			// 针对经销商过滤
			if (newMap != null && newMap.size() > 0) {
				for (CommonDictionaryDto dto : newMap.values()) {
					// 先把共用的添加进去
					if ("Y".equals(dto.getIsCommon())) {
						resultMap.put(dto.getPropertyValue(), dto);
					} else {
						if (agencyId.equals(dto.getAgencyId())) {
							resultMap.put(dto.getPropertyValue(), dto);
						}
					}
				}
			}

			result.put(key, resultMap);
		}
		return result;
	}

	@Override
	public Map<String, List<CommonDictionaryDto>> findCommonDictionaryList(String[] dictionaryNameList, String status,
			 Long agencyId) {
		Map<String, Map<String, CommonDictionaryDto>> map = this.findCommonDictionaryMap(dictionaryNameList, status,
				agencyId);
		Map<String, List<CommonDictionaryDto>> result = new LinkedHashMap();
		if (map == null || map.size() == 0) {
			return new LinkedHashMap();
		}
		for (String dictionaryName : map.keySet()) {
			List<CommonDictionaryDto> list = new ArrayList();
			list.addAll(map.get(dictionaryName).values());
			result.put(dictionaryName, list);
		}
		return result;
	}

	@Override
	public DictionaryConfigDto findDictionaryConfigDetail(String dictionaryName, Long agencyId, String ifSuperAdmin) {
		DictionaryConfigDto dto = null;
		Map<String, Map<String, CommonDictionaryDto>> mapAll = cacheService.findAllCommonDictionary();
		List<DictionaryConfigDto> configList = cacheService.findDictionaryConfigList();
		if (configList != null && configList.size() > 0) {
			for (DictionaryConfigDto dc : configList) {
				if (dc.getDictionaryName().equals(dictionaryName)) {
					dto = dc;
					break;
				}
			}

			if (dto == null) {
				throw new MessageException("未查询到该数据字典名称信息！", ECode.CICD_BUILD_QUERY_DETAILS_FAILED);
			}
			if (mapAll != null && mapAll.size() > 0) {
				Map<String, CommonDictionaryDto> cdMap = mapAll.get(dictionaryName);
				if (cdMap == null || cdMap.size() == 0) {
					return dto;
				}

				if ("Y".equals(ifSuperAdmin)) {
					dto.getDictionaryDtoList().addAll(cdMap.values());
				} else {
					for (String key : cdMap.keySet()) {
						CommonDictionaryDto cd = cdMap.get(key);
						// 非共用的数据字典项查询起对应的供应商
						if ("Y".equals(cd.getIsCommon())) {
							dto.getDictionaryDtoList().add(cd);
						} else {
							if (agencyId.equals(cd.getAgencyId())) {
								dto.getDictionaryDtoList().add(cd);
							}
						}
					}
				}
			}
		}

		return dto;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class })
	@Override
	public void insertCommonDictionary(CommonDictionaryDto dictionary) {
		Map<String, Map<String, CommonDictionaryDto>> mapAll = cacheService.findAllCommonDictionary();
		if (mapAll != null && mapAll.size() > 0) {
			Map<String, CommonDictionaryDto> map = mapAll.get(dictionary.getDictionaryName());
			if (map != null && map.size() > 0) {
				if (map.containsKey(dictionary.getPropertyValue())) {
					throw new MessageException("该数据字典已存在！", ECode.CICD_BUILD_QUERY_DETAILS_FAILED);
				}
			}
		}
		dataDictionaryMapper.insertCommonDictionary(dictionary);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class })
	@Override
	public void updateCommonDictionary(CommonDictionaryDto dictionary) {
		dataDictionaryMapper.updateCommonDictionary(dictionary);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class })
	@Override
	public boolean editDictionaryConfig(DictionaryConfigDto dto, String ifSuperAdmin, Long agencyId) {
		// 先查询数据字典名是否已存在
		int count = 0;
		boolean isNew = true;
		List<DictionaryConfigDto> dcList = cacheService.findDictionaryConfigList();
		if (dcList != null && dcList.size() > 0) {
			for (DictionaryConfigDto dc : dcList) {
				if (dc.getDictionaryName().equals(dto.getDictionaryName())) {
					count = 1;
					break;
				}
			}
		}

		if (count == 0) {
			// 没有则先新增字典名称信息
			dataDictionaryMapper.insertDictionaryConfig(dto);
		} else {
			// 存在则修改字典名称信息(注意数据字典名称是不能修改的)
			dataDictionaryMapper.updateDictionaryConfig(dto);
			isNew = false;
		}

		// 超管添加的都为共用字典,非超管添加的即为自己所在经销商的字典
		if (dto.getDictionaryDtoList() != null && dto.getDictionaryDtoList().size() > 0) {
			int i = 1;
			DictionaryConfigDto dictionaryDetail = this.findDictionaryConfigDetail(dto.getDictionaryName(), agencyId,
					ifSuperAdmin);
			for (CommonDictionaryDto cd : dto.getDictionaryDtoList()) {
				cd.setSequenceNum(i++);
				cd.setDictionaryName(dto.getDictionaryName());
				if (cd.getPropertyValue() == null || "".equals(cd.getPropertyValue())) {
					String newPropertyValue = RandUtil.buildIdByHanyu(cd.getPropertyText(), "P");

					// 已存在同样的下拉，则往后面补位
					String maxPropertyValue = dataDictionaryMapper.findMaxPropertyValue(dto.getDictionaryName(),
							newPropertyValue);
					newPropertyValue = RandUtil.appendSeqNum(maxPropertyValue, newPropertyValue, 2);

					cd.setPropertyValue(newPropertyValue);

					cd.setIsCommon("Y");
					if (!"Y".equals(ifSuperAdmin)) {
						cd.setIsCommon("N");
						cd.setAgencyId(agencyId);
					}

					dataDictionaryMapper.insertCommonDictionary(cd);
				} else {
					List<CommonDictionaryDto> oldListdictionary = dictionaryDetail.getDictionaryDtoList();

					// 非超管不能修改共用数据字典下拉信息
					if (!"Y".equals(ifSuperAdmin)) {
						for (CommonDictionaryDto old : oldListdictionary) {
							if (old.getPropertyValue().equals(cd.getPropertyValue())) {
								if (!"Y".equals(old.getIsCommon()) && agencyId.equals(old.getAgencyId())) {
									cd.setIsCommon("N");
									cd.setAgencyId(agencyId);

									dataDictionaryMapper.updateCommonDictionary(cd);
								}
							}
						}
					} else {
						for (CommonDictionaryDto old : oldListdictionary) {
							// 超管只修改共用数据字典下拉信息
							if ("Y".equals(old.getIsCommon())) {
								cd.setAgencyId(null);
								dataDictionaryMapper.updateCommonDictionary(cd);
							}
						}
					}
				}
			}
		}

		return isNew;
	}

	@Override
	public NewPageInfo<?> list(SearchCondition cond) throws Exception {
		PageHelper.startPage(cond.getPageNum(), cond.getPageSize());
		List list = dataDictionaryMapper.list(cond);
		return new NewPageInfo(list);
	}

	@Override
	public Object get(Object key) throws Exception {
		// TODO Auto-generated method stub
		DataDictionaryVo vo = (DataDictionaryVo) dataDictionaryMapper.selectByPrimaryKey(key);
		return vo;
	}

	@Override
	public void delete(Object key) throws Exception {
		// TODO Auto-generated method stub
		dataDictionaryMapper.deleteByPrimaryKey(key);
	}

	@Override
	public void insert(Object dto) throws Exception {
		// TODO Auto-generated method stub
		dataDictionaryMapper.insertSelective(dto);
	}

	@Override
	public void update(Object dto) throws Exception {
		// TODO Auto-generated method stub
		dataDictionaryMapper.updateByPrimaryKeySelective(dto);
	}
}
