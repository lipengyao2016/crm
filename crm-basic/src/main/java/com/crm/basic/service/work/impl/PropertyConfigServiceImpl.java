package com.crm.basic.service.work.impl;

import static com.crm.utils.StringUtil.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.crm.basic.mapper.work.DataDictionaryMapper;
import com.crm.basic.mapper.work.PropertyConfigMapper;
import com.crm.basic.service.CacheDataService;
import com.crm.basic.service.work.PropertyConfigService;
import com.crm.dto.basic.CommonDictionaryDto;
import com.crm.dto.basic.DataDictionaryDto;
import com.crm.dto.basic.PropertyConfigDto;
import com.crm.utils.RandUtil;
import com.crm.utils.StringUtil;
import com.crm.vo.basic.DataDictionaryVo;
import com.crm.vo.basic.PropertyConfigVo;
import com.crm.common.MessageException;
import com.crm.common.NewPageInfo;
import com.crm.common.SearchCondition;
import com.crm.dto.basic.CommonDictionaryDto;
import com.crm.dto.basic.DataDictionaryDto;
import com.crm.dto.basic.PropertyConfigDto;
import com.crm.feign.administration.AdministrationFeignClient;
import com.crm.utils.RandUtil;
import com.crm.utils.StringUtil;
import com.crm.vo.basic.PropertyConfigVo;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.crm.feign.administration.AdministrationFeignClient;
import com.github.pagehelper.PageHelper;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Service
public class PropertyConfigServiceImpl implements PropertyConfigService {

	@Autowired
	private CacheDataService cacheDataService;

	@Autowired
	private PropertyConfigMapper propertyConfigMapper;

	@Autowired
	private DataDictionaryMapper dataDictionaryMapper;

	public static void main(String[] args) {
//		List<PropertyConfigVo> list = new ArrayList<PropertyConfigVo>();
//		PropertyConfigVo propertyConfigVo = new PropertyConfigVo();
//		propertyConfigVo.setPropertyName("test");
//		list.add(propertyConfigVo);
//		Stream<PropertyConfigVo> stream = list.stream();
//		System.out.println("stream:" + JSONObject.toJSONString(stream.collect(Collectors.toList())));
	}

	public List<PropertyConfigVo> findPropertyConfigList(String permissionType, String permissionId, String ifForbidden,
														 String status, Long agencyId) {
		List<PropertyConfigVo> list = cacheDataService.findPropertyConfigList(agencyId);
		Stream<PropertyConfigVo> stream = list.stream();
		if (StringUtil.isNotEmpty(permissionType)) {
			stream = stream.filter(e -> permissionType.equals(e.getPermissionType()));
		}
		if (StringUtil.isNotEmpty(permissionId)) {
			stream = stream.filter(e -> permissionId.equals(e.getPermissionId()));
		}
		if (StringUtil.isNotEmpty(ifForbidden)) {
			stream = stream.filter(e -> ifForbidden.equals(e.getIfForbidden()));
		}
		if (StringUtil.isNotEmpty(status)) {
			stream = stream.filter(e -> status.equals(e.getStatus()));
		}
		list = stream.collect(Collectors.toList());
		List<DataDictionaryVo> ddList = cacheDataService.findDataDictionaryList(agencyId);
		for (PropertyConfigVo vo : list) {
			for (DataDictionaryVo dd : ddList) {
				if (vo.getPropertyConfigSeqId().equals(dd.getPropertyConfigSeqId())) {
					if (StringUtil.isNotEmpty(status) && dd.getStatus().equals(status)) {
						vo.getDataDictionaryList().add(dd);
					} else {
						vo.getDataDictionaryList().add(dd);
					}
				}
			}
		}
		return list;
	}

	public List<PropertyConfigVo> findSystemPropertyConfigList(String permissionId,String permissionType, String status) {
		List<PropertyConfigVo> list = cacheDataService.findSystemPropertyConfigList();
		Stream<PropertyConfigVo> stream = list.stream();
		if (StringUtil.isNotEmpty(permissionType)) {
			stream = stream.filter(e -> permissionType.equals(e.getPermissionType()));
		}
		if (StringUtil.isNotEmpty(permissionId)) {
			stream = stream.filter(e -> permissionId.equals(e.getPermissionId()) || "0".equals(e.getPermissionId()));
		}
		if (StringUtil.isNotEmpty(status)) {
			stream = stream.filter(e -> status.equals(e.getStatus()));
		}
		list = stream.collect(Collectors.toList());
		return list;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class })
	@Override
	public PropertyConfigDto insertPropertyConfig(PropertyConfigDto dto) throws Exception {
		String propertyName = dto.getPropertyName();
		String propertyDesc = dto.getPropertyDesc();

		// 判断属性字段名称是否存在(字段名)
		propertyName = RandUtil.buildIdByHanyu(propertyDesc, dto.getPermissionType().toUpperCase());
		propertyName = RandUtil.appendSeqNum("", propertyName, 2);
		int count = ((Long) propertyConfigMapper.propertyNameExists(propertyName,null).get("count")).intValue();
		if (count > 0) {
			String maxCompanyId = propertyConfigMapper.findMaxPropertyName(propertyName);
			propertyName = RandUtil.appendSeqNum(maxCompanyId, propertyName, 2);
		}
		dto.setPropertyName(propertyName);

		Integer id = propertyConfigMapper.insertPropertyConfig(dto);
		if (dto.getDataDictionaryList() != null && dto.getDataDictionaryList().size() > 0) {
			for (DataDictionaryDto dd : dto.getDataDictionaryList()) {
				dd.setPropertyConfigSeqId(dto.getPropertyConfigSeqId());
				propertyConfigMapper.insertDataDictionaryDto(dd);
			}
		}
		dto.setPropertyConfigSeqId(id);
		return dto;
	}

	@SuppressWarnings("unused")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class })
	@Override
	public PropertyConfigDto savePropertyConfig(PropertyConfigDto dto) throws Exception {
		dto.setStatus("Y");
		Integer propertyConfigSeqId = dto.getPropertyConfigSeqId();
		String propertyName = dto.getPropertyName();
		String propertyDesc = dto.getPropertyDesc();

		// 新增修改自定义字段
		if (propertyConfigSeqId == null || propertyConfigSeqId.equals(0)) {

			if (StringUtil.isEmpty(propertyName)) {

				// 判断属性字段名称是否存在(字段名)
				propertyName = RandUtil.buildIdByHanyu(propertyDesc, dto.getPermissionType().toUpperCase());
				int count = ((Long) propertyConfigMapper.propertyNameExists(propertyName, null).get("count")).intValue();
				if (count > 0) {
					String maxCompanyId = propertyConfigMapper.findMaxPropertyName(propertyName);
					propertyName = RandUtil.appendSeqNum(maxCompanyId, propertyName, 2);
				}
				dto.setPropertyName(propertyName);
			}

			propertyConfigMapper.insertPropertyConfig(dto);
			propertyConfigSeqId = dto.getPropertyConfigSeqId();
		} else {
			// 修改前删除该自定义字段的下拉列表
			int deleteDataDictionaryByPropertyId = propertyConfigMapper
					.deleteDataDictionaryByPropertyId(propertyConfigSeqId, null);

			propertyConfigMapper.updatePropertyConfig(dto);
		}

		// 遍历写入
		List<DataDictionaryDto> dataDictionaryList = dto.getDataDictionaryList();
		if (dataDictionaryList != null && dataDictionaryList.size() > 0) {
			for (int i = 0; i < dataDictionaryList.size(); i++) {
				DataDictionaryDto dd = dataDictionaryList.get(i);
				dd.setPropertyConfigSeqId(propertyConfigSeqId);
				dd.setStatus("Y");
				dd.setSequenceNum(i);
				propertyConfigMapper.insertDataDictionaryDto(dd);
			}
		}
		return dto;
	}

	/*@SuppressWarnings("unused")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class })
	@Override
	public void savePropertyConfigByMQ(List<PropertyConfigDto> dtoList) throws Exception {
		// 新增修改自定义字段,通过字段名称是否存在于数据库判断新增还是修改
		List<PropertyConfigVo> findSystemPropertyConfigList = this.findSystemPropertyConfigList(dtoList.get(0).getPermissionId(), PropertyType.ACTIVITYLOG.getValue(), null);
		List<String> propertyNameList = findSystemPropertyConfigList.stream().map(p -> p.getPropertyName())
				.collect(Collectors.toList());
		for (int ki = 0; ki < dtoList.size(); ki++) {
			PropertyConfigDto dto = dtoList.get(ki);
			// 使用经销商端的主键生成策略
			dto.setPropertyConfigSeqId(null);

			Integer propertyConfigSeqId = dto.getPropertyConfigSeqId();
			String propertyName = dto.getPropertyName();
			String propertyDesc = dto.getPropertyDesc();

			if (!propertyNameList.contains(dto.getPropertyName())) {
				propertyConfigMapper.insertPropertyConfig(dto);
				propertyConfigSeqId = dto.getPropertyConfigSeqId();
			} else {
				// update
				// 修改前删除该自定义字段的下拉列表
				int deleteDataDictionaryByPropertyId = propertyConfigMapper
						.deleteDataDictionaryByPropertyId(propertyConfigSeqId, null);
				PropertyConfigVo orElse = findSystemPropertyConfigList.stream()
						.filter(p -> p.getPropertyName().equals(propertyName)).findFirst().map(p -> p).orElse(null);
				dto.setPropertyConfigSeqId(orElse.getPropertyConfigSeqId());
				propertyConfigMapper.updatePropertyConfig(dto);
			}
			// 包含选择型数据对象

			// 先根据自定义字段删除其下拉数据
			propertyConfigMapper.deleteDataDictionaryByPropertyId(propertyConfigSeqId,null);

			// 遍历写入
			List<DataDictionaryDto> dataDictionaryList = dto.getDataDictionaryList();
			if (dataDictionaryList != null && dataDictionaryList.size() > 0) {
				for (int i = 0; i < dataDictionaryList.size(); i++) {
					DataDictionaryDto dd = dataDictionaryList.get(i);
					dd.setPropertyConfigSeqId(propertyConfigSeqId);
					dd.setStatus("Y");
					dd.setSequenceNum(i);
					propertyConfigMapper.insertDataDictionaryDto(dd);
				}
			}
		}
		cacheDataService.cacheEvictFixedPropertyConfig();
	}*/

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class })
	@Override
	public DataDictionaryDto insertDataDictionary(DataDictionaryDto dto) {
		propertyConfigMapper.insertDataDictionaryDto(dto);
		return dto;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class })
	@Override
	public PropertyConfigDto updatePropertyConfig(PropertyConfigDto dto) {
		propertyConfigMapper.updatePropertyConfig(dto);
		return dto;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class })
	@Override
	public DataDictionaryDto updateDataDictionary(DataDictionaryDto dto) {
		propertyConfigMapper.updateDataDictionary(dto);
		return dto;
	}

//	public String findPermissionByTableName(String tableName) {
//		RestfulResponse<Map<Integer, String>> restful = feignClient.findPermissionByTableName(tableName);
//		if (restful.getCode() == ECode.NO_DATA_RESULT.getCode()) {
//			return null;
//		}
//		if (restful.getCode() != ECode.SUCCESS.getCode()) {
//			// 调用微服务出错了，抛出异常信息
//			throw new MessageException(restful.getMessage());
//		}
//		return restful.getData().keySet().iterator().next() + "";
//	}

	@Override
	public NewPageInfo<?> list(SearchCondition cond) throws Exception {
		PageHelper.startPage(cond.getPageNum(), cond.getPageSize());
		List list = propertyConfigMapper.list(cond);
		return new NewPageInfo(list);
	}

	@Override
	public Object get(Object key) throws Exception {
		// TODO Auto-generated method stub
		return propertyConfigMapper.selectByPrimaryKey(key);
	}

	@Override
	public void delete(Object key) throws Exception {
		// TODO Auto-generated method stub
		propertyConfigMapper.deleteByPrimaryKey(key);
	}

	@Override
	public void insert(Object dto) throws Exception {
		// TODO Auto-generated method stub
		propertyConfigMapper.insertSelective(dto);
	}

	@Override
	public void update(Object dto) throws Exception {
		// TODO Auto-generated method stub
		propertyConfigMapper.updateByPrimaryKeySelective(dto);
	}

	@Override
	public List<PropertyConfigVo> findPropertyConfigByIds(String propertyConfigSeqIdArray, String inputType,
			String arrtValue, Long agencyId) {
		List<PropertyConfigVo> findPropertyConfigByIds = propertyConfigMapper
				.findPropertyConfigByIds(propertyConfigSeqIdArray, inputType, arrtValue, agencyId, null);
		return findPropertyConfigByIds;
	}

	@SuppressWarnings("serial")
	@Override
	public NewPageInfo<PropertyConfigVo> listSystemProperty(SearchCondition condition) throws Exception {
		// 处理分页参数
		int pageNum = StringUtil.isNotEmpty(condition.getPageNum()) && Integer.valueOf(condition.getPageNum() + "") != 0
				? Integer.valueOf(condition.getPageNum() + "")
				: 1;
		int pageSize = StringUtil.isNotEmpty(condition.getPageSize()) && condition.getPageSize() != 0
				? condition.getPageSize()
				: 10;
		String propertyType = "'singleselect','multiplyselect'";
		int size = propertyConfigMapper.listSystemPropertyCount(propertyType, null);

		condition.setPageNum((pageNum - 1) * pageSize);
		condition.setPageSize(pageSize);
		List<PropertyConfigVo> listSystemProperty = propertyConfigMapper
				.listSystemProperty(new HashMap<String, Object>() {
					{
						put("agencyId", condition.getAgencyId());
						put("pageStart", (pageNum - 1) * pageSize);
						put("pageEnd", pageSize);
						put("propertyType", propertyType);
					}
				});

		// 处理字典数据
		List<CommonDictionaryDto> ddList = dataDictionaryMapper.findCommonDictionary("propertyType",
				condition.getAgencyId());
		Map<String, CommonDictionaryDto> ddMap = new HashMap();
		if (ddList != null && ddList.size() > 0) {
			ddMap = ddList.stream().collect(Collectors.toMap(CommonDictionaryDto::getPropertyValue, (p) -> p));

		}

		// 将下拉框的值拼接为字符串
		for (int i = 0; i < listSystemProperty.size(); i++) {
			PropertyConfigVo propertyConfigVo = listSystemProperty.get(i);
			List<DataDictionaryVo> dataDictionaryList = propertyConfigVo.getDataDictionaryList();
			String propertyStr = dataDictionaryList.stream().map(p -> p.getDataName())
					.collect(Collectors.joining(";"));
			
			propertyConfigVo.setPropertyTypeName(ddMap.get(propertyConfigVo.getPropertyType()).getPropertyText());
			
			//如果不是选项性字段则展示字段输入类型
			propertyConfigVo.setPropertyStr(StringUtil.isEmpty(propertyStr)?propertyConfigVo.getPropertyTypeName():propertyStr);
		
		}

		NewPageInfo<PropertyConfigVo> pageInfo = new NewPageInfo<PropertyConfigVo>(listSystemProperty);

		// 处理一对多对象映射带来分页数据错乱
		pageInfo.setPageNum(pageNum);
		pageInfo.setPageSize(pageSize);
		pageInfo.setHasNextPage(size - (pageSize * pageNum) > 0);
		pageInfo.setTotal((long) size);
		return pageInfo;
	}

	@Override
	public PropertyConfigVo getSystemProperty(String id, Long agencyId) throws Exception {
		String status="Y";
//		return propertyConfigMapper.getSystemProperty(id, agencyId);
		List<PropertyConfigVo> list = cacheDataService.findPropertyConfigList(agencyId);
		Stream<PropertyConfigVo> stream = list.stream();
		if (StringUtil.isNotEmpty(id)) {
			stream = stream.filter(e -> id.equals(e.getPropertyConfigSeqId().toString()));
		}
		 
		list = stream.collect(Collectors.toList());
		List<DataDictionaryVo> ddList = cacheDataService.findDataDictionaryList(agencyId);
		for (PropertyConfigVo vo : list) {
			for (DataDictionaryVo dd : ddList) {
				if (vo.getPropertyConfigSeqId().equals(dd.getPropertyConfigSeqId())) {
					if (StringUtil.isNotEmpty(status) && dd.getStatus().equals(status)) {
						vo.getDataDictionaryList().add(dd);
					} else {
						vo.getDataDictionaryList().add(dd);
					}
				}
			}
		}
		return list.size()>0?list.get(0):null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int saveDataDictionary(Long agencyId, PropertyConfigVo propertyConfigVo) throws Exception {

		// 根据经销商清除原先的固定字段下拉列表
		Integer propertySeqId = propertyConfigVo.getPropertyConfigSeqId();

		// 查询原固定字段id,将原不可删除的固定字段取出
		PropertyConfigVo systemProperty = propertyConfigMapper.getSystemProperty(propertySeqId.toString(), agencyId);
		List<DataDictionaryVo> dataDictionaryList = systemProperty.getDataDictionaryList();
		List<Integer> collect = dataDictionaryList.stream().filter(p -> p.getIsSelect().equals("Y"))
				.map(p -> p.getDataDictionarySeqId()).collect(Collectors.toList());

		Integer result = 0;
		result += propertyConfigMapper.deleteDataDictionaryByagencyId(agencyId, propertySeqId);

		List<DataDictionaryVo> propertyVo = propertyConfigVo.getDataDictionaryList();
		int propertyVosize = propertyVo.size();
		// 插入新的下拉列表数据，带上经销商编码
		for (int i = 0; i < propertyVo.size(); i++) {
			DataDictionaryVo dataDictionaryVo = propertyVo.get(i);

			// 属性转移至Dto对象
			DataDictionaryDto dataDictionaryDto = new DataDictionaryDto();
			BeanUtils.copyProperties(dataDictionaryDto, dataDictionaryVo);

			// 如果是新增情况，key值取最大属性下拉数量+1
			if (StringUtil.isEmpty(dataDictionaryDto.getDataValue())) {
				propertyVosize++;
				dataDictionaryDto.setDataValue(propertyVosize + "");
			}

			// 处理将经销商编码为空的作为该经销特有的数据
			if (StringUtil.isEmpty(dataDictionaryDto.getAgencyId())) {
				dataDictionaryDto.setDataDictionarySeqId(null);
				dataDictionaryDto.setAgencyId(agencyId);
			}

			// 前端按照顺序传给后端
			dataDictionaryDto.setSequenceNum(i + 1);
			dataDictionaryDto.setPropertyConfigSeqId(propertySeqId);
			dataDictionaryDto.setStatus("Y");
			dataDictionaryMapper.insertSelective(dataDictionaryDto);

			// 将没有删除的已绑定关系的数据从判断组中清除
			if (collect.contains(dataDictionaryVo.getDataDictionarySeqId())) {
				collect.remove(dataDictionaryVo.getDataDictionarySeqId());
			}
		}

		// 判断是否存在删除掉了已存在绑定产品的下拉数据
		if (collect.size() > 0) {
			// 取出无法删除的字段名称
			String collect2 = dataDictionaryList.stream().filter(p -> collect.contains(p.getDataDictionarySeqId()))
					.map(p -> p.getDataName()).collect(Collectors.joining(","));

			throw new MessageException("删除的选项：'" + collect2 + "'已经关联产品,无法删除");
		}
		result += propertyVo.size();
		return result;
	}

	@Override
	public List<PropertyConfigDto> queryExsitByPermission(Long agencyId, String permissionType, String propertyNames)
			throws Exception {
		List<PropertyConfigDto> data = new ArrayList<PropertyConfigDto>();
		List<PropertyConfigVo> queryExsitByPermission = propertyConfigMapper.queryExsitByPermission(permissionType,
				agencyId, propertyNames);
		queryExsitByPermission.forEach(p -> {
			PropertyConfigDto propertyConfigDto = new PropertyConfigDto();
			try {
				BeanUtils.copyProperties(p, propertyConfigDto);
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new MessageException("PropertyConfigVo convert PropertyConfigDto Exception!");
			}
			data.add(propertyConfigDto);
		});
//		cacheDataService.cacheEvictDataDisctionary();
//		cacheDataService.cacheEvictPropertyConfig();
		return data;
	}
}
