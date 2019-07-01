package com.crm.feign.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crm.common.ECode;
import com.crm.common.MessageException;
import com.crm.common.RestfulResponse;
import com.crm.dto.basic.CommonDictionaryDto;
import com.crm.dto.basic.PropertyConfigDto;
import com.crm.vo.basic.PropertyConfigVo;
import com.crm.dto.basic.CommonDictionaryDto;
import com.crm.dto.basic.PropertyConfigDto;
import com.crm.vo.basic.PropertyConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BasicFeignCommon {
	@Autowired
	private BasicFeignClient feignClient;

	/**
	 * 查询多个数据字典
	 * 
	 * @param dictionaryNameArr
	 * @param agencyId
	 * @return
	 */
	public Map<String, Map<String, CommonDictionaryDto>> getDictionaryMap(String[] dictionaryNameArr, Long agencyId) {
		if (dictionaryNameArr == null || dictionaryNameArr.length == 0) {
			return new HashMap();
		}
		RestfulResponse<Map<String, Map<String, CommonDictionaryDto>>> restful = feignClient
				.findBasicDataForPc(dictionaryNameArr, "Y", agencyId);
		if (restful.getCode() == ECode.NO_DATA_RESULT.getCode()) {
			throw new MessageException("未查询到数据字典信息");
		}
		if (restful.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(restful.getMessage());
		}

		return restful.getData();
	}



	/**
	 * 查询动态配置属性信息
	 * 
	 * @param permissionId
	 * @param permissionType
	 * @param status
	 * @return
	 */
	public List<PropertyConfigDto> findPropertyConfigList(String permissionId, String permissionType, String status) {
		RestfulResponse<List<PropertyConfigDto>> restful = feignClient.findPropertyConfigList(permissionId,
				permissionType, status);
		if (restful.getCode() == ECode.NO_DATA_RESULT.getCode()) {
			return new ArrayList();
		}
		if (restful.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(restful.getMessage());
		}

		return restful.getData();
	}

	/**
	 * 查询动态字段列表
	 */
	public List<PropertyConfigVo> queryPropertyConfigFeign(Long agencyId, String permissionType, String permissionId,
                                                           String ifForbidden) {
		RestfulResponse<List<PropertyConfigVo>> restful = feignClient.queryPropertyConfigFeign(agencyId, permissionType,
				permissionId, ifForbidden);
		if (restful.getCode() == ECode.NO_DATA_RESULT.getCode()) {
			return new ArrayList();
		}
		if (restful.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(restful.getMessage());
		}

		return restful.getData();
	}

	/**
	 * 删除某个模块的自定义字段
	 */
	public void deleteByPermission( Long agencyId, String permissionType, String permissionId) {
		RestfulResponse<Void> restful = feignClient.deleteByPermission(agencyId, permissionType, permissionId);
		if (restful.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(restful.getMessage());
		}
	}

	/**
	 * 查询某个模块重复的自定义字段
	 */
	public List<PropertyConfigDto> queryExsitByPermission( Long agencyId, String permissionType,
			String propertyNames) {
		RestfulResponse<List<PropertyConfigDto>> restful = feignClient.queryExsitByPermission(agencyId, permissionType,
				propertyNames);
		if (restful.getCode() == ECode.NO_DATA_RESULT.getCode()) {
			return null;
		}
		if (restful.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(restful.getMessage());
		}

		return restful.getData();
	}

	/**
	 * 新增动态字段
	 */
	public PropertyConfigDto createPropertyConfig(PropertyConfigDto dto) {
		RestfulResponse<PropertyConfigDto> restful = feignClient.createPropertyConfig(dto);
		if (restful.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(restful.getMessage());
		}

		return restful.getData();
	}


	/**
	 * 查询固定配置属性信息
	 * 
	 * @param permissionType
	 * @param status
	 * @return
	 */
	public List<PropertyConfigVo> findSystemPropertyConfigList(String permissionType, String status) {
		RestfulResponse<List<PropertyConfigVo>> restful = feignClient.findSystemPropertyConfigList(permissionType,
				status);
		if (restful.getCode() == ECode.NO_DATA_RESULT.getCode()) {
			return new ArrayList();
		}
		if (restful.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(restful.getMessage());
		}

		return restful.getData();
	}

	/**
	 * 查询固定配置信息(工厂端同步至经销商系统的数据作为固定字段与同步数据主键绑定)
	 * 
	 * @param permissionType
	 * @param status
	 * @return
	 */
	public List<PropertyConfigVo> systemPpropertyById(String permissionId,String permissionType, String status) {
		RestfulResponse<List<PropertyConfigVo>> restful = feignClient.systemPpropertyById(permissionId,permissionType,
				status);
		if (restful.getCode() == ECode.NO_DATA_RESULT.getCode()) {
			return new ArrayList();
		}
		if (restful.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(restful.getMessage());
		}

		return restful.getData();
	}
}
