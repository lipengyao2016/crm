package com.crm.feign.basic;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.crm.common.RestfulResponse;
import com.crm.dto.basic.CommonDictionaryDto;
import com.crm.dto.basic.PropertyConfigDto;
import com.crm.dto.basic.PropertyConfigNotForbiddenDto;
import com.crm.vo.basic.PropertyConfigVo;
import com.crm.dto.basic.CommonDictionaryDto;
import com.crm.dto.basic.PropertyConfigDto;
import com.crm.dto.basic.PropertyConfigNotForbiddenDto;
import com.crm.vo.basic.PropertyConfigVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiParam;

@FeignClient(name = "crm-basic", fallbackFactory = BasicHystrixClientFallbackFactory.class)
public interface BasicFeignClient {
	/**
	 * 查询多个数据字典
	 * 
	 * @param dictionaryNameArr
	 * @param status
	 * @param agencyId
	 * @return
	 */
	@RequestMapping(value="basicDatasForPc/{dictionaryNameArr}/{status}/{agencyId}",method = RequestMethod.GET)
	public RestfulResponse<Map<String, Map<String, CommonDictionaryDto>>> findBasicDataForPc(
            @PathVariable("dictionaryNameArr") String[] dictionaryNameArr, @PathVariable("status") String status,
            @PathVariable("agencyId")  Long agencyId);

	/**
	 * 查询动态配置属性信息
	 * 
	 * @param permissionId
	 * @param permissionType
	 * @param status
	 * @return
	 */
	@RequestMapping(value="propertyConfigs/{permissionId}/{permissionType}/{status}",method = RequestMethod.GET)
	public RestfulResponse<List<PropertyConfigDto>> findPropertyConfigList(
            @PathVariable("permissionId") String permissionId, @PathVariable("permissionType") String permissionType,
            @PathVariable("status") String status);

	/**
	 * 查询动态字段列表
	 */
	@RequestMapping(value="queryPropertyConfigFeign",method = RequestMethod.GET)
	public RestfulResponse<List<PropertyConfigVo>> queryPropertyConfigFeign(@RequestParam("agencyId")  Long agencyId,
																			@RequestParam("permissionType") String permissionType, @RequestParam("permissionId") String permissionId,
																			@RequestParam("ifForbidden") String ifForbidden);

	/**
	 * 删除某个模块的自定义字段
	 */
	@RequestMapping(value="deleteByPermission",method = RequestMethod.GET)
	public RestfulResponse<Void> deleteByPermission(@RequestParam("agencyId")  Long agencyId,
                                                    @RequestParam("permissionType") String permissionType, @RequestParam("permissionId") String permissionId);

	/**
	 * 查询某个模块重复的自定义字段
	 */
	@RequestMapping(value="queryExsitByPermission",method = RequestMethod.GET)
	public RestfulResponse<List<PropertyConfigDto>> queryExsitByPermission(@RequestParam("agencyId")  Long agencyId,
                                                                           @RequestParam("permissionType") String permissionType, @RequestParam("propertyNames") String propertyNames);

	/**
	 * 新增动态字段
	 */
	@RequestMapping(value="createPropertyConfig",method = RequestMethod.POST)
	public RestfulResponse<PropertyConfigDto> createPropertyConfig(@RequestBody PropertyConfigDto dto);

	/**
	 * 修改动态字段
	 */
	@RequestMapping(value="createPropertyConfigByNotForbidden",method = RequestMethod.POST)
	public RestfulResponse<PropertyConfigNotForbiddenDto> savePropertyConfigByNotForbidden(
            @RequestBody PropertyConfigNotForbiddenDto dto);


	/**
	 * 查询固定配置信息
	 * 
	 * @return
	 */
	@RequestMapping(value="systemPpropertyConfigs/{permissionType}/{status}",method = RequestMethod.GET)
	public RestfulResponse<List<PropertyConfigVo>> findSystemPropertyConfigList(
            @PathVariable("permissionType") String permissionType, @PathVariable("status") String status);

	/**
	 * 查询固定配置信息(工厂端同步至经销商系统的数据作为固定字段与同步数据主键绑定)
	 * 
	 * @return
	 */
	@RequestMapping(value="systemPpropertyById/{permissionId}/{permissionType}/{status}",method = RequestMethod.GET)
	public RestfulResponse<List<PropertyConfigVo>> systemPpropertyById(
            @PathVariable("permissionId") String permissionId, @PathVariable("permissionType") String permissionType, @PathVariable("status") String status);


}