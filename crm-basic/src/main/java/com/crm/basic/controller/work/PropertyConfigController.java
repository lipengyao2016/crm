package com.crm.basic.controller.work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.crm.basic.mapper.work.PropertyConfigMapper;
import com.crm.annotation.NoPermissionAuth;
import com.crm.annotation.PermissionAuth;
import com.crm.basic.service.CacheDataService;
import com.crm.basic.service.work.PropertyConfigService;
import com.crm.dto.basic.DataDictionaryDto;
import com.crm.dto.basic.PropertyConfigDto;
import com.crm.dto.basic.PropertyConfigNotForbiddenDto;
import com.crm.utils.StringUtil;
import com.crm.vo.UserLoginInfoVo;
import com.crm.vo.basic.PropertyConfigVo;
import com.crm.annotation.NoPermissionAuth;
import com.crm.annotation.PermissionAuth;
import com.crm.common.*;
import com.crm.dto.basic.DataDictionaryDto;
import com.crm.dto.basic.PropertyConfigDto;
import com.crm.dto.basic.PropertyConfigNotForbiddenDto;
import com.crm.utils.StringUtil;
import com.crm.vo.basic.PropertyConfigVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@PermissionAuth
@Api(tags = "动态属性配置")
@RestController
public class PropertyConfigController {
	private static final Logger logger = LoggerFactory.getLogger(PropertyConfigController.class);

	@Autowired
	private PropertyConfigService configService;

	@Autowired
	private CacheDataService cacheDataService;

	@Autowired
	PropertyConfigMapper<PropertyConfigDto> propertyConfigMapper;

	@ApiOperation(value = "查询动态配置属性信息", notes = "PC端根据指定权限菜单查询，返回菜单下所有动态配置项，包括下拉框所有值(如果有)")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", name = "permissionId", dataType = "String", value = "权限菜单编码", required = true, defaultValue = "1"),
			@ApiImplicitParam(paramType = "path", name = "status", dataType = "String", value = "是否启用", required = false, defaultValue = "Y") })
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 1000, message = "查询成功") })
	@GetMapping("propertyConfigs/{permissionId}/{status}")
	public RestfulResponse<List<PropertyConfigVo>> findPropertyConfigList(HttpServletRequest request,
																		  @PathVariable String permissionId, @PathVariable String status) {
		RestfulResponse<List<PropertyConfigVo>> restful = new RestfulResponse<>(new ArrayList<>());
		if (permissionId == null || "".equals(permissionId)) {
			restful.setCode(ECode.ARG_ERROR.getCode());
			restful.setMessage("权限菜单编码为空！");
			return restful;
		}

		try {
			UserLoginInfoVo loginVo = (UserLoginInfoVo) request.getAttribute("loginUserInfo");
			status = status.replace("{status}", "");
			List<PropertyConfigVo> list = configService.findPropertyConfigList("", permissionId, "", status,
					loginVo.getAgencyId());
			if (list == null || list.size() == 0) {
				restful.setCode(ECode.NO_DATA_RESULT.getCode());
				restful.setMessage("未查询到动态配置属性信息！");
			} else {
				restful.setData(list);
			}
		} catch (MessageException e) {
			logger.error("查询动态配置属性失败{}", e.getMessage());
			restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
			if (e.getCode() != null) {
				restful.setCode(e.getCode().getCode());
			}
			restful.setMessage(e.getMessage());
			return restful;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询动态配置属性失败{}", e.getMessage());
			restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
			restful.setMessage("查询动态配置属性失败！");
			return restful;
		}
		return restful;
	}

	@ApiOperation(value = "查询动态配置属性信息", notes = "根据指定权限编码(或其他动态配置关联id)及动态配置类型查询，返回所有动态配置项，包括下拉框所有值(如果有)")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", name = "permissionId", dataType = "String", value = "权限编码(或其他动态配置关联id)", required = true, defaultValue = "1"),
			@ApiImplicitParam(paramType = "path", name = "permissionType", dataType = "String", value = "动态配置类型", required = true, defaultValue = ""),
			@ApiImplicitParam(paramType = "path", name = "status", dataType = "String", value = "是否启用", required = false, defaultValue = "Y") })
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 1000, message = "查询成功") })
	@GetMapping("propertyConfigs/{permissionId}/{permissionType}/{status}")
	public RestfulResponse<List<PropertyConfigVo>> findPropertyConfigList(HttpServletRequest request,
			@PathVariable String permissionId, @PathVariable String permissionType, @PathVariable String status) {
		RestfulResponse<List<PropertyConfigVo>> restful = new RestfulResponse<>(new ArrayList<>());
		if (permissionId == null || "".equals(permissionId)) {
			restful.setCode(ECode.ARG_ERROR.getCode());
			restful.setMessage("权限编码为空！");
			return restful;
		}

		try {
			status = status.replace("status", "").replace("{}", "");
			permissionType = permissionType.replace("permissionType", "").replace("{}", "");
			List<PropertyConfigVo> list = configService.findPropertyConfigList(permissionType, permissionId, "", status,
					StringUtil.getAgencyId(request));
			if (list == null || list.size() == 0) {
				restful.setCode(ECode.NO_DATA_RESULT.getCode());
				restful.setMessage("未查询到动态配置属性信息！");
			} else {
				restful.setData(list);
			}
		} catch (MessageException e) {
			logger.error("查询动态配置属性失败{}", e.getMessage());
			restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
			if (e.getCode() != null) {
				restful.setCode(e.getCode().getCode());
			}
			restful.setMessage(e.getMessage());
			return restful;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询动态配置属性失败{}", e.getMessage());
			restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
			restful.setMessage("查询动态配置属性失败！");
			return restful;
		}
		return restful;
	}

	@NoPermissionAuth
	@ApiOperation(value = "查询固定配置属性信息", notes = "根据指定权限编码(或其他动态配置关联id)及动态配置类型查询，返回所有动态配置项，包括下拉框所有值(如果有)")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", name = "permissionType", dataType = "String", value = "动态配置类型", required = true, defaultValue = ""),
			@ApiImplicitParam(paramType = "path", name = "status", dataType = "String", value = "是否启用", required = false, defaultValue = "Y") })
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 1000, message = "查询成功") })
	@GetMapping("systemPpropertyConfigs/{permissionType}/{status}")
	public RestfulResponse<List<PropertyConfigVo>> findSystemPropertyConfigList(HttpServletRequest request,
			@PathVariable("permissionType") String permissionType, @PathVariable("status") String status) {
		RestfulResponse<List<PropertyConfigVo>> restful = new RestfulResponse<>(new ArrayList<>());

		try {
			status = status.replace("status", "").replace("{}", "");
			permissionType = permissionType.replace("permissionType", "").replace("{}", "");
			List<PropertyConfigVo> list = configService.findSystemPropertyConfigList(null, permissionType, status);
			if (list == null || list.size() == 0) {
				restful.setCode(ECode.NO_DATA_RESULT.getCode());
				restful.setMessage("未查询到固定配置属性信息！");
			} else {
				restful.setData(list);
			}
		} catch (MessageException e) {
			logger.error("查询固定配置属性失败{}", e.getMessage());
			restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
			if (e.getCode() != null) {
				restful.setCode(e.getCode().getCode());
			}
			restful.setMessage(e.getMessage());
			return restful;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询固定配置属性失败{}", e.getMessage());
			restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
			restful.setMessage("查询固定配置属性失败！");
			return restful;
		}
		return restful;
	}

	@ApiOperation(value = "查询动态字段列表")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "agencyId", dataType = "String", value = "经销商名称", required = true, defaultValue = "boyin_test"),
			@ApiImplicitParam(paramType = "query", name = "permissionType", dataType = "String", value = "模块名称", required = true, defaultValue = "activitylog"),
			@ApiImplicitParam(paramType = "query", name = "permissionId", dataType = "String", value = "关联id", required = false, defaultValue = ""),
			@ApiImplicitParam(paramType = "query", name = "ifForbidden", dataType = "String", value = "是否固定字段(Y是/N否)", required = false, defaultValue = ""), })
	@GetMapping("queryPropertyConfigFeign")
	public RestfulResponse<List<PropertyConfigVo>> queryPropertyConfigFeign(Long agencyId, String permissionType,
			String permissionId, String ifForbidden) {
		RestfulResponse<List<PropertyConfigVo>> restful = new RestfulResponse<>();
		try {
			List<PropertyConfigVo> list = configService.findPropertyConfigList(permissionType, permissionId,
					ifForbidden, "Y", agencyId);
			if (list == null || list.size() == 0) {
				restful.setCode(ECode.NO_DATA_RESULT.getCode());
				restful.setMessage("未查询到动态配置属性信息！");
			} else {
				restful.setData(list);
			}
		} catch (MessageException e) {
			logger.error("查询动态配置属性失败{}", e.getMessage());
			restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
			if (e.getCode() != null) {
				restful.setCode(e.getCode().getCode());
			}
			restful.setMessage("查询动态配置属性失败" + e.getMessage());
			return restful;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询动态配置属性失败{}", e.getMessage());
			restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
			restful.setMessage("查询动态配置属性失败！");
			return restful;
		}
		return restful;
	}

	@ApiOperation(value = "查询固定字段列表")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "agencyId", dataType = "Long", value = "经销商名称", required = true, defaultValue = "boyin_test"),
			@ApiImplicitParam(paramType = "query", name = "permissionType", dataType = "String", value = "模块名称", required = true, defaultValue = "activitylog"),
			@ApiImplicitParam(paramType = "query", name = "permissionId", dataType = "String", value = "关联id", required = false, defaultValue = ""),
			@ApiImplicitParam(paramType = "query", name = "ifForbidden", dataType = "String", value = "是否固定字段(Y是/N否)", required = false, defaultValue = ""), })
	@GetMapping("querySystemProperty")
	public RestfulResponse<List<PropertyConfigVo>> querySystemProperty(Long agencyId, String permissionType,
			String permissionId, String ifForbidden) {
		RestfulResponse<List<PropertyConfigVo>> restful = new RestfulResponse<>();
		try {
			// 固定字段查询
			List<PropertyConfigVo> list = propertyConfigMapper.findPropertyConfigByPermissionIdArray(permissionType,
					"0", ifForbidden, agencyId, null);
			if (list == null || list.size() == 0) {
				restful.setCode(ECode.NO_DATA_RESULT.getCode());
				restful.setMessage("未查询到动态配置属性信息！");
			} else {
				restful.setData(list);
			}
		} catch (MessageException e) {
			logger.error("查询动态配置属性失败{}", e.getMessage());
			restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
			if (e.getCode() != null) {
				restful.setCode(e.getCode().getCode());
			}
			restful.setMessage("查询动态配置属性失败" + e.getMessage());
			return restful;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询动态配置属性失败{}", e.getMessage());
			restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
			restful.setMessage("查询动态配置属性失败！");
			return restful;
		}
		return restful;
	}

	@NoPermissionAuth
	@ApiOperation(value = "查询动态配置属性信息", notes = "微服务根据数据库表名查询，返回该表下所有动态配置项，包括下拉框所有值(如果有)")
	@ApiImplicitParam(paramType = "path", name = "tableName", dataType = "String", value = "数据库表名", required = true, defaultValue = "designer")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 1000, message = "查询成功") })
	@GetMapping("propertyConfigMap/{tableName}")
	public RestfulResponse<Map<Integer, PropertyConfigVo>> findPropertyConfigMap(HttpServletRequest request,
			@PathVariable String tableName) {
		RestfulResponse<Map<Integer, PropertyConfigVo>> restful = new RestfulResponse<>(new HashMap<>());
		if (tableName == null || "".equals(tableName)) {
			restful.setCode(ECode.ARG_ERROR.getCode());
			restful.setMessage("数据库表名为空！");
			return restful;
		}

		RestfulResponse<List<PropertyConfigVo>> list = this.findPropertyConfigList(request, null,tableName, "");
		if (list.getData() != null && list.getData().size() > 0) {
			Map<Integer, PropertyConfigVo> map = list.getData().stream()
					.collect(Collectors.toMap(PropertyConfigVo::getPropertyConfigSeqId, (p) -> p));
			restful.setData(map);
		}
		return restful;
	}

	@ApiOperation(value = "保存自定义属性")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 1000, message = "新增动态属性成功") })
	@PostMapping("createPropertyConfigByNotForbidden")
	public RestfulResponse<PropertyConfigNotForbiddenDto> savePropertyConfigByNotForbidden(HttpServletRequest request,
																						   @ApiParam(name = "动态属性信息", required = true) @RequestBody PropertyConfigNotForbiddenDto dto) {
		PropertyConfigDto pcb = new PropertyConfigDto();
		BeanUtils.copyProperties(dto, pcb);
		RestfulResponse<PropertyConfigNotForbiddenDto> restful = new RestfulResponse<PropertyConfigNotForbiddenDto>(
				dto);
		Long agencyId = StringUtil.getAgencyId(request);
		// String ifSuperAdmin = loginUserInfo.getString("ifSuperAdmin");
		// pcb.setIfForbidden("N");
		// if ("Y".equals(ifSuperAdmin)) {
		// pcb.setIfForbidden("Y");
		// }
		if (dto.getPropertyDesc() == null || "".equals(dto.getPropertyDesc())) {
			restful.setCode(ECode.ARG_ERROR.getCode());
			restful.setMessage("属性中文描述为空！");
			return restful;
		}

		// 添加自定义属性时该属性可以被修改
		dto.setIsNecessary("N");
		try {
			PropertyConfigDto newDto = configService.savePropertyConfig(pcb);
			BeanUtils.copyProperties(newDto, dto);
			restful.setData(dto);
		} catch (MessageException e) {
			restful.setCode(ECode.QUEERY_ERROR.getCode());
			if (e.getCode() != null) {
				restful.setCode(e.getCode().getCode());
			}
			e.printStackTrace();
			logger.error("保存自定义属性失败{}", e.getMessage());
			restful.setMessage("保存自定义属性失败"+ e.getMessage());
			return restful;
		} catch (Exception e) {
			logger.error("保存自定义属性失败{}", e.getMessage());
			restful.setCode(ECode.ADD_ERROR.getCode());
			restful.setMessage("保存自定义属性失败!");
			e.printStackTrace();
			return restful;
		}

		// 新增成功后清理下缓存
		cacheDataService.cacheEvictDataDisctionary(agencyId);
		cacheDataService.cacheEvictPropertyConfig(agencyId);
		return restful;
	}

	@ApiOperation(value = "新增动态属性")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 1000, message = "新增动态属性成功") })
	@PostMapping("createPropertyConfig")
	public RestfulResponse<PropertyConfigDto> createPropertyConfig(HttpServletRequest request,
			@ApiParam(name = "动态属性信息", required = true) @RequestBody PropertyConfigDto dto) {
		RestfulResponse<PropertyConfigDto> restful = new RestfulResponse<PropertyConfigDto>(dto);
		Long agencyId = StringUtil.getAgencyId(request);
		try {
			dto.setIfForbidden("N");
			dto.setIsNecessary("Y");
			dto.setStatus("Y");
			PropertyConfigDto newDto = configService.insertPropertyConfig(dto);
			restful.setData(newDto);
		} catch (MessageException e) {
			logger.error("新增动态属性失败{}", e.getMessage());
			restful.setCode(ECode.ADD_ERROR.getCode());
			if (e.getCode() != null) {
				restful.setCode(e.getCode().getCode());
			}
			restful.setMessage("新增动态属性失败"+ e.getMessage());
			return restful;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("新增动态属性失败{}", e.getMessage());
			restful.setCode(ECode.ADD_ERROR.getCode());
			restful.setMessage("新增动态属性失败！");
			return restful;
		}

		// 新增成功后清理下缓存
		cacheDataService.cacheEvictDataDisctionary(agencyId);
		cacheDataService.cacheEvictPropertyConfig(agencyId);
		return restful;
	}

	@ApiOperation(value = "新增动态属性下拉菜单")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"),
			@ApiResponse(code = 1000, message = "新增动态属性下拉菜单成功") })
	@PostMapping("dataDictionarys")
	public RestfulResponse<DataDictionaryDto> createDataDictionary(HttpServletRequest request,
																   @ApiParam(name = "动态属性下拉菜信息", required = true) @RequestBody DataDictionaryDto dto) {
		RestfulResponse<DataDictionaryDto> restful = new RestfulResponse<DataDictionaryDto>(dto);
		Long agencyId = StringUtil.getAgencyId(request);
		try {
			DataDictionaryDto newDto = configService.insertDataDictionary(dto);
			restful.setData(newDto);
		} catch (MessageException e) {
			logger.error("新增动态属性下拉菜单失败{}", e.getMessage());
			restful.setCode(ECode.ADD_ERROR.getCode());
			if (e.getCode() != null) {
				restful.setCode(e.getCode().getCode());
			}
			restful.setMessage("新增动态属性下拉菜单失败"+ e.getMessage());
			return restful;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("新增动态属性下拉菜单失败{}", e.getMessage());
			restful.setCode(ECode.ADD_ERROR.getCode());
			restful.setMessage("新增动态属性下拉菜单失败！");
			return restful;
		}

		// 新增成功后清理下缓存
		cacheDataService.cacheEvictDataDisctionary(agencyId);
		return restful;
	}

	@ApiOperation(value = "修改动态属性")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 1000, message = "修改动态属性成功") })
	@PutMapping("updatePropertyConfig")
	public RestfulResponse<PropertyConfigDto> updatePropertyConfig(HttpServletRequest request,
			@ApiParam(name = "动态属性信息", required = true) @RequestBody PropertyConfigDto dto) {
		RestfulResponse<PropertyConfigDto> restful = new RestfulResponse<PropertyConfigDto>(dto);
		Long agencyId = StringUtil.getAgencyId(request);
		try {
			configService.updatePropertyConfig(dto);
		} catch (MessageException e) {
			logger.error("修改动态属性失败{}", e.getMessage());
			restful.setCode(ECode.UPDATE_ERROR.getCode());
			if (e.getCode() != null) {
				restful.setCode(e.getCode().getCode());
			}
			restful.setMessage("修改动态属性失败"+ e.getMessage());
			return restful;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改动态属性失败{}", e.getMessage());
			restful.setCode(ECode.UPDATE_ERROR.getCode());
			restful.setMessage("修改动态属性失败！");
			return restful;
		}

		// 新增成功后清理下缓存
		cacheDataService.cacheEvictDataDisctionary(agencyId);
		cacheDataService.cacheEvictPropertyConfig(agencyId);
		return restful;
	}

	@ApiOperation(value = "修改动态属性下拉菜单")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"),
			@ApiResponse(code = 1000, message = "修改动态属性下拉菜单成功") })
	@PutMapping("dataDictionarys")
	public RestfulResponse<DataDictionaryDto> updateDataDictionary(HttpServletRequest request,
			@ApiParam(name = "动态属性下拉菜单信息", required = true) @RequestBody DataDictionaryDto dto) {
		RestfulResponse<DataDictionaryDto> restful = new RestfulResponse<DataDictionaryDto>(dto);
		Long agencyId = StringUtil.getAgencyId(request);
		try {
			configService.updateDataDictionary(dto);
		} catch (MessageException e) {
			logger.error("修改动态属性下拉菜单失败{}", e.getMessage());
			restful.setCode(ECode.UPDATE_ERROR.getCode());
			if (e.getCode() != null) {
				restful.setCode(e.getCode().getCode());
			}
			restful.setMessage("修改动态属性下拉菜单失败"+ e.getMessage());
			return restful;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改动态属性下拉菜单失败{}", e.getMessage());
			restful.setCode(ECode.UPDATE_ERROR.getCode());
			restful.setMessage("修改动态属性下拉菜单失败！");
			return restful;
		}

		// 新增成功后清理下缓存
		cacheDataService.cacheEvictDataDisctionary(agencyId);
		return restful;
	}

	/**
	 * 系统默认属性列表分页数据
	 * 
	 * @param
	 * @return
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "查询系统默认属性列表")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 1000, message = "查询成功") })
	@GetMapping("/listSystemProperty")
	public RestfulResponse<NewPageInfo<PropertyConfigVo>> listSystemProperty(SearchCondition condition,
																			 HttpServletRequest request) {
		RestfulResponse<NewPageInfo<PropertyConfigVo>> result = new RestfulResponse<NewPageInfo<PropertyConfigVo>>(
				new NewPageInfo(new ArrayList()));
		try {
			UserLoginInfoVo loginVo = (UserLoginInfoVo) request.getAttribute("loginUserInfo");
			// 读取当前登录人所在经销商
			if (!"Y".equals(loginVo.getIfSuperAdmin())) {
				Long userAgencyId = StringUtil.getAgencyId(request);
				condition.setAgencyId(userAgencyId);
			}
			NewPageInfo<PropertyConfigVo> dataInfo = configService.listSystemProperty(condition);
			result.setData(dataInfo);
			if (dataInfo.getList() == null || dataInfo.getList().size() == 0) {
				result.setCode(ECode.NO_DATA_RESULT.getCode());
				result.setMessage(ECode.NO_DATA_RESULT.getMessage());
			}

		} catch (MessageException e) {
			result.setCode(ECode.QUEERY_ERROR.getCode());
			if (e.getCode() != null) {
				result.setCode(e.getCode().getCode());
			}
			result.setMessage(e.getMessage());
			logger.error("{}",e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setCode(ECode.QUEERY_ERROR.getCode());
			result.setMessage(ECode.QUEERY_ERROR.getMessage());
		}
		return result;
	}

	/**
	 * 获取单个固定配置项
	 * 
	 * @param id
	 * @return
	 * @throws Exception-
	 */
	@ApiOperation(value = "获取单个固定配置项")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", name = "id", dataType = "String", required = true, value = "产品编码", defaultValue = "1") })
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 1000, message = "查询成功") })
	@GetMapping("/getSystemProperty/{id}")
	public RestfulResponse<PropertyConfigVo> getSystemProperty(HttpServletRequest request,
			@PathVariable(value = "id") String id) {
		RestfulResponse<PropertyConfigVo> result = new RestfulResponse<PropertyConfigVo>(new PropertyConfigVo());
		if (StringUtil.isEmpty(id)) {
			result.setCode(ECode.QUEERY_ERROR.getCode());
			result.setMessage("主键不能为空");
		}
		try {
			// 读取当前登录人所在经销商
			Long userAgencyId = StringUtil.getAgencyId(request);
			PropertyConfigVo dataInfo = configService.getSystemProperty(id, userAgencyId);
			result.setData(dataInfo);
			if (dataInfo == null) {
				result.setCode(ECode.NO_DATA_RESULT.getCode());
				result.setMessage(ECode.NO_DATA_RESULT.getMessage());
			}
		} catch (MessageException e) {
			result.setCode(ECode.QUEERY_ERROR.getCode());
			if (e.getCode() != null) {
				result.setCode(e.getCode().getCode());
			}
			result.setMessage(e.getMessage());
			logger.error("{}",e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setCode(ECode.QUEERY_ERROR.getCode());
			result.setMessage(ECode.QUEERY_ERROR.getMessage());
		}
		return result;
	}

	/**
	 * 修改保存系统固定字段根据经销商配置
	 * 
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "修改保存系统固定字段根据经销商配置ForPC")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 1000, message = "新增成功") })
	@PostMapping("/saveDataDictionaryForPC")
	public RestfulResponse<Integer> saveDataDictionary(
			@ApiParam(name = "信息字段", required = true) @RequestBody PropertyConfigVo propertyConfigVo,
			HttpServletRequest request) {
		// 读取当前登录人所在经销商
		Long userAgencyId = StringUtil.getAgencyId(request);
		RestfulResponse<Integer> result = new RestfulResponse<Integer>(0);
		try {
			Integer dataInfo = configService.saveDataDictionary(userAgencyId, propertyConfigVo);
			result.setData(dataInfo);
		} catch (MessageException e) {
			result.setCode(ECode.UPDATE_ERROR.getCode());
			if (e.getCode() != null) {
				result.setCode(e.getCode().getCode());
			}
			result.setMessage(e.getMessage());
			logger.error("{}",e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setCode(ECode.UPDATE_ERROR.getCode());
			result.setMessage(ECode.UPDATE_ERROR.getMessage());
		}
		return result;
	}

	@ApiOperation(value = "删除某个模块的自定义字段")
	@GetMapping("deleteByPermission")
	public RestfulResponse<Void> deleteByPermission(Long agencyId, String permissionType, String permissionId) {
		propertyConfigMapper.deleteByPermission(permissionType, permissionId, agencyId);
		cacheDataService.cacheEvictDataDisctionary(agencyId);
		cacheDataService.cacheEvictPropertyConfig(agencyId);
		return new RestfulResponse<>();
	}

	@ApiOperation(value = "查询某个模块重复的自定义字段")
	@GetMapping("queryExsitByPermission")
	public RestfulResponse<List<PropertyConfigDto>> queryExsitByPermission(
			@RequestParam(value = "agencyId") Long agencyId,
			@RequestParam(value = "permissionType") String permissionType,
			@RequestParam(value = "propertyNames") String propertyNames) {
		RestfulResponse<List<PropertyConfigDto>> result = new RestfulResponse<List<PropertyConfigDto>>(
				new ArrayList<PropertyConfigDto>());
		try {
			List<PropertyConfigDto> queryExsitByPermission = configService.queryExsitByPermission(agencyId,
					permissionType, propertyNames);
			result.setData(queryExsitByPermission);
		} catch (MessageException e) {
			result.setCode(ECode.QUEERY_ERROR.getCode());
			if (e.getCode() != null) {
				result.setCode(e.getCode().getCode());
			}
			result.setMessage(e.getMessage());
			logger.error("{}",e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			result.setCode(ECode.QUEERY_ERROR.getCode());
			result.setMessage(ECode.QUEERY_ERROR.getMessage());
		}
		return result;
	}

	@NoPermissionAuth
	@ApiOperation(value = "查询固定配置属性信息", notes = "根据指定权限编码(或其他动态配置关联id)及动态配置类型查询，返回所有动态配置项，包括下拉框所有值(如果有)")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", name = "permissionId", dataType = "String", value = "动态配置类型", required = true, defaultValue = ""),
			@ApiImplicitParam(paramType = "path", name = "permissionType", dataType = "String", value = "动态配置类型", required = true, defaultValue = ""),
			@ApiImplicitParam(paramType = "path", name = "status", dataType = "String", value = "是否启用", required = false, defaultValue = "Y") })
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 1000, message = "查询成功") })
	@GetMapping("systemPpropertyById/{permissionId}/{permissionType}/{status}")
	public RestfulResponse<List<PropertyConfigVo>> findSystemPropertyListById(HttpServletRequest request,
			@PathVariable("permissionId") String permissionId, @PathVariable("permissionType") String permissionType,
			@PathVariable("status") String status) {
		RestfulResponse<List<PropertyConfigVo>> restful = new RestfulResponse<>(new ArrayList<>());

		try {
			status = status.replace("status", "").replace("{}", "");
			permissionType = permissionType.replace("permissionType", "").replace("{}", "");
			permissionId = permissionId.replace("permissionId", "").replace("{}", "");
			List<PropertyConfigVo> list = configService.findSystemPropertyConfigList(permissionId, permissionType,
					status);
			if (list == null || list.size() == 0) {
				restful.setCode(ECode.NO_DATA_RESULT.getCode());
				restful.setMessage("未查询到固定配置属性信息！");
			} else {
				restful.setData(list);
			}
		} catch (MessageException e) {
			logger.info("查询固定配置属性失败：" + e.getMessage());
			restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
			if (e.getCode() != null) {
				restful.setCode(e.getCode().getCode());
			}
			restful.setMessage(e.getMessage());
			return restful;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询固定配置属性失败：" + e.getMessage());
			restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
			restful.setMessage("查询固定配置属性失败！");
			return restful;
		}
		return restful;
	}
	
	@ApiOperation(value = "清除系统固定字段配置数据", notes = "用于数据错乱时手动处理")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
		@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 1000, message = "查询成功") })
	@GetMapping("clearSystemPpropertyByType")
	public RestfulResponse<List<PropertyConfigVo>> clearSystemPpropertyByType(HttpServletRequest request) {
		RestfulResponse<List<PropertyConfigVo>> restful = new RestfulResponse<>(new ArrayList<>());
		
		try {
			cacheDataService.cacheEvictAllPropertyConfig();
			cacheDataService.cacheEvictAllDataDisctionary();
		} catch (MessageException e) {
			logger.info("清除系统固定字段配置失败：" + e.getMessage());
			restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
			if (e.getCode() != null) {
				restful.setCode(e.getCode().getCode());
			}
			restful.setMessage(e.getMessage());
			return restful;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("清除系统固定字段配置失败：" + e.getMessage());
			restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
			restful.setMessage("清除系统固定字段配置！");
			return restful;
		}
		return restful;
	}

}
