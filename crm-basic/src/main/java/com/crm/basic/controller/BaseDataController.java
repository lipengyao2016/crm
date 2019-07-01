package com.crm.basic.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.crm.annotation.OperationLog;
import com.crm.annotation.PermissionAuth;
import com.crm.basic.service.CacheDataService;
import com.crm.basic.service.work.DataDictionaryService;
import com.crm.dto.basic.CommonDictionaryDto;
import com.crm.utils.StringUtil;
import com.crm.vo.UserLoginInfoVo;
import com.crm.annotation.OperationLog;
import com.crm.annotation.PermissionAuth;
import com.crm.basic.model.CommonDictionaryCondition;
import com.crm.basic.model.CommonDictionaryVo;
import com.crm.basic.model.DictionaryConfigDto;
import com.crm.common.*;
import com.crm.dto.basic.CommonDictionaryDto;
import com.crm.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "数据字典")
@RestController
public class BaseDataController {
	private static final Logger logger = LoggerFactory.getLogger(BaseDataController.class);
//	@Autowired
//	private AdministrationFeignCommon administrationFeign;

	@Autowired
	private DataDictionaryService dataDictonaryService;

	@Autowired
	private CacheDataService cacheService;

	@PermissionAuth
	@ApiOperation(value = "分页查询数据字典列表信息")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 1000, message = "查询成功") })
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "pageNum", dataType = "int", value = "第几页", required = true, defaultValue = "1"),
			@ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", value = "每页数据行数", required = true, defaultValue = "10"),
			@ApiImplicitParam(paramType = "query", name = "dictionaryName", dataType = "String", value = "数据字典名", defaultValue = "area"),
			@ApiImplicitParam(paramType = "query", name = "dictionaryDesc", dataType = "String", value = "数据字典名中文描述", defaultValue = "面积"),
			@ApiImplicitParam(paramType = "query", name = "propertyValue", dataType = "String", value = "数据字典属性值", defaultValue = "1"),
			@ApiImplicitParam(paramType = "query", name = "propertyText", dataType = "String", value = "数据字典属性描述", defaultValue = "60以下"),
			@ApiImplicitParam(paramType = "query", name = "isCommon", dataType = "String", value = "是否共用", defaultValue = "Y"),
			@ApiImplicitParam(paramType = "query", name = "available", dataType = "String", value = "数据字典名是否启用(整个数据字典)", defaultValue = "Y"),
			@ApiImplicitParam(paramType = "query", name = "agencyId", dataType = "String", value = "经销商编码", defaultValue = ""),
			@ApiImplicitParam(paramType = "query", name = "status", dataType = "String", value = "数据字典选项是否启用", defaultValue = "Y") })
	@GetMapping("dataDictionarys")
	public RestfulResponse<NewPageInfo<CommonDictionaryVo>> findCommonDictionaryVoList(HttpServletRequest request,
																					   @ApiParam(name = "分页查询条件") CommonDictionaryCondition cond) {
		NewPageInfo<CommonDictionaryVo> voList = new NewPageInfo<CommonDictionaryVo>(new ArrayList<>());
		RestfulResponse<NewPageInfo<CommonDictionaryVo>> restful = new RestfulResponse<NewPageInfo<CommonDictionaryVo>>(
				voList);

		UserLoginInfoVo loginVo = (UserLoginInfoVo) request.getAttribute("loginUserInfo");
		;
		Long agencyId = cond.getAgencyId();
		if (agencyId == null || "".equals(agencyId)) {
			agencyId = loginVo.getAgencyId();
		}

		// 查询用户角色
		/*List<UserAgencyRoleDto> roleList = administrationFeign.findUserAgencyRoleList(loginVo.getUserLoginId());
		if (roleList == null || roleList.size() == 0) {
			restful.setCode(ECode.UNAUTHORIZED.getCode());
			restful.setMessage("用户未维护任何角色信息");
			return restful;
		}
		for (UserAgencyRoleDto role : roleList) {
			if (role.getRoleId().equals("SUPER_ADMIN")) {
				cond.setRoleId("SUPER_ADMIN");
				break;
			} else if (role.getRoleId().equals("AGENCY_ADMIN") && role.getAgencyId().equals(agencyId)) {
				cond.setRoleId("AGENCY_ADMIN");
				break;
			} else if (role.getAgencyId().equals(agencyId)) {
				cond.setRoleId(role.getRoleId());
			}
		}*/

		if (!"SUPER_ADMIN".equals(cond.getRoleId())) {
			cond.setAgencyId(agencyId);
		}

		try {
			NewPageInfo<CommonDictionaryVo> result = dataDictonaryService.findCommonDictionaryList(cond);
			restful.setData(result);
			if (result == null || result.getSize() == 0) {
				restful.setCode(ECode.NO_DATA_RESULT.getCode());
				restful.setMessage("未查询到数据字典信息！");
			}

		} catch (MessageException e) {
			logger.error("查询数据字典失败{}", e.getMessage());
			restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
			if (e.getCode() != null) {
				restful.setCode(e.getCode().getCode());
			}
			restful.setMessage("查询数据字典失败" + e.getMessage());
			return restful;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询数据字典失败{}", e.getMessage());
			restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
			restful.setMessage("查询数据字典失败！");
			return restful;
		}
		return restful;
	}

	@ApiOperation(value = "查询数据字典信息", notes = "支持同时查询多个数据字典")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", name = "dictionaryName", dataType = "String", value = "数据字典名称", required = true, defaultValue = "GENDER"),
			@ApiImplicitParam(paramType = "path", name = "agencyId", dataType = "String", value = "经销商编码(为空时查询登录用户所在的供应商)", required = false, defaultValue = ""),
			@ApiImplicitParam(paramType = "path", name = "status", dataType = "String", value = "是否启用", required = false, defaultValue = "Y") })
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"),
			@ApiResponse(code = 1000, message = "查询成功(第一层key为数据字典名称)") })
	@GetMapping("basicDatas/{dictionaryName}/{status}/{agencyId}")
	public RestfulResponse<List<CommonDictionaryDto>> findBasicData(HttpServletRequest request,
																	@PathVariable String dictionaryName, @PathVariable String status, @PathVariable Long agencyId) {
		RestfulResponse<List<CommonDictionaryDto>> restful = new RestfulResponse<List<CommonDictionaryDto>>(
				new ArrayList<>());

		if (dictionaryName == null || "".equals(dictionaryName)) {
			restful.setCode(ECode.ARG_ERROR.getCode());
			restful.setMessage("数据字典属性名为空！");
			return restful;
		}

		try {
			String[] dictionaryNameArr = new String[1];
			dictionaryNameArr[0] = dictionaryName;
			if (agencyId == null || "".equals(agencyId)) {
				UserLoginInfoVo loginVo = (UserLoginInfoVo) request.getAttribute("loginUserInfo");
				agencyId = loginVo.getAgencyId();
			}
			Map<String, List<CommonDictionaryDto>> result = dataDictonaryService
					.findCommonDictionaryList(dictionaryNameArr, status, agencyId);
			if (result == null || result.size() == 0) {
				restful.setCode(ECode.NO_DATA_RESULT.getCode());
				restful.setMessage("未查询到数据字典信息！");
			} else {
				for (List<CommonDictionaryDto> list : result.values()) {
					restful.setData(list); // 只有一个值
				}
			}
		} catch (MessageException e) {
			logger.error("查询数据字典失败{}", e.getMessage());
			restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
			if (e.getCode() != null) {
				restful.setCode(e.getCode().getCode());
			}
			restful.setMessage("查询数据字典失败" + e.getMessage());
			return restful;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询数据字典失败{}", e.getMessage());
			restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
			restful.setMessage("查询数据字典失败！");
			return restful;
		}
		return restful;
	}

	@ApiOperation(value = "查询数据字典信息", notes = "支持同时查询多个数据字典")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", name = "dictionaryNameArr", dataType = "String", value = "数据字典名称", required = true, defaultValue = "ROLE_TYPE,GENDER"),
			@ApiImplicitParam(paramType = "path", name = "agencyId", dataType = "Long", value = "经销商编码(为空时查询登录用户所在的供应商)", required = true, defaultValue = "11038547698"),
			@ApiImplicitParam(paramType = "path", name = "status", dataType = "String", value = "是否启用", required = true, defaultValue = "Y") })
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"),
			@ApiResponse(code = 1000, message = "查询成功(第一层key为数据字典名称)") })
	@GetMapping("basicDatasForPc/{dictionaryNameArr}/{status}/{agencyId}")
	public RestfulResponse<Map<String, Map<String, CommonDictionaryDto>>> findBasicDataForPc(HttpServletRequest request,
			@PathVariable String[] dictionaryNameArr, @PathVariable String status, @PathVariable Long agencyId) {

		RestfulResponse<Map<String, Map<String, CommonDictionaryDto>>> restful = new RestfulResponse<Map<String, Map<String, CommonDictionaryDto>>>(
				new HashMap<>());
		if (dictionaryNameArr == null || dictionaryNameArr.length == 0) {
			restful.setCode(ECode.ARG_ERROR.getCode());
			restful.setMessage("数据字典属性名为空！");
			return restful;
		}

		try {
			status = status.replace("{status}", "");
			if (agencyId == null || "".equals(agencyId)) {
				agencyId= StringUtil.getAgencyId(request);
			}
			Map<String, Map<String, CommonDictionaryDto>> mapAll = dataDictonaryService
					.findCommonDictionaryMap(dictionaryNameArr, status, agencyId);
			if (mapAll == null || mapAll.size() == 0) {
				restful.setCode(ECode.NO_DATA_RESULT.getCode());
				restful.setMessage("未查询到数据字典信息！");
			} else {
				restful.setData(mapAll);
			}
		} catch (MessageException e) {
			logger.error("查询数据字典失败{}", e.getMessage());
			restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
			if (e.getCode() != null) {
				restful.setCode(e.getCode().getCode());
			}
			restful.setMessage("查询数据字典失败" + e.getMessage());
			return restful;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询数据字典失败{}", e.getMessage());
			restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
			restful.setMessage("查询数据字典失败！");
			return restful;
		}
		return restful;
	}

	@ApiOperation(value = "查询数据字典详细信息", notes = "")
	@ApiImplicitParam(paramType = "path", name = "dictionaryName", dataType = "String", value = "数据字典名称", required = true, defaultValue = "")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"),
			@ApiResponse(code = 1000, message = "查询成功(第一层key为数据字典名称)") })
	@GetMapping("dictionaryConfigs/{dictionaryName}")
	public RestfulResponse<DictionaryConfigDto> findDictionaryConfigDetail(HttpServletRequest request,
																		   @PathVariable String dictionaryName) {
		RestfulResponse<DictionaryConfigDto> restful = new RestfulResponse<DictionaryConfigDto>(
				new DictionaryConfigDto());
		if (dictionaryName == null || "".equals(dictionaryName)) {
			restful.setCode(ECode.ARG_ERROR.getCode());
			restful.setMessage("数据字典属性名为空！");
			return restful;
		}

		try {
			UserLoginInfoVo loginVo = (UserLoginInfoVo) request.getAttribute("loginUserInfo");
			DictionaryConfigDto dto = dataDictonaryService.findDictionaryConfigDetail(dictionaryName,
					loginVo.getAgencyId(), loginVo.getIfSuperAdmin());
			if (dto == null) {
				restful.setCode(ECode.NO_DATA_RESULT.getCode());
				restful.setMessage("未查询到数据字典信息！");
			} else {
				restful.setData(dto);
			}
		} catch (MessageException e) {
			logger.error("查询数据字典失败{}", e.getMessage());
			restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
			if (e.getCode() != null) {
				restful.setCode(e.getCode().getCode());
			}
			restful.setMessage("查询数据字典失败" + e.getMessage());
			return restful;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询数据字典失败{}", e.getMessage());
			restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
			restful.setMessage("查询数据字典失败!");
			return restful;
		}
		return restful;
	}

	@ApiOperation(value = "查询数据字典属性描述")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", name = "dictionaryName", dataType = "String", value = "数据字典名称", required = true, defaultValue = "GENDER"),
			@ApiImplicitParam(paramType = "path", name = "propertyValue", dataType = "String", value = "属性值", required = true, defaultValue = "W") })
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 1000, message = "查询成功") })
	@GetMapping("basicDataText/{dictionaryName}/{propertyValue}")
	public RestfulResponse<String> findBasicDataText(@PathVariable String dictionaryName,
			@PathVariable String propertyValue) {

		RestfulResponse<String> restful = new RestfulResponse<String>("");
		if (dictionaryName == null || "".equals(dictionaryName)) {
			restful.setCode(ECode.ARG_ERROR.getCode());
			restful.setMessage("数据字典名为空！");
			return restful;
		}

		if (propertyValue == null || "".equals(propertyValue)) {
			restful.setCode(ECode.ARG_ERROR.getCode());
			restful.setMessage("数据字典属性名为空！");
			return restful;
		}

		try {
			Map<String, Map<String, CommonDictionaryDto>> mapAll = cacheService.findAllCommonDictionary();
			if (mapAll == null || mapAll.size() == 0) {
				restful.setCode(ECode.NO_DATA_RESULT.getCode());
				restful.setMessage("未查询到数据字典信息！");
				return restful;
			} else {
				Map<String, CommonDictionaryDto> map = mapAll.get(dictionaryName);
				if (map != null && map.containsKey(propertyValue)) {
					restful.setData(map.get(propertyValue).getPropertyText());
				}
			}

			if (restful.getData() == null || "".equals(restful.getData())) {
				restful.setCode(ECode.NO_DATA_RESULT.getCode());
				restful.setMessage("未查询到数据字典信息！");
				return restful;
			}

		} catch (MessageException e) {
			logger.error("查询数据字典失败{}", e.getMessage());
			restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
			if (e.getCode() != null) {
				restful.setCode(e.getCode().getCode());
			}
			restful.setMessage("查询数据字典失败" + e.getMessage());
			return restful;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询数据字典失败{}", e.getMessage());
			restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
			restful.setMessage("查询数据字典失败！");
			return restful;
		}
		return restful;
	}

	@PermissionAuth
	@OperationLog
	@ApiOperation(value = "新增/修改数据字典信息", notes = "系统管理员维护数据字典，包括数据字典名、数据字典对应下拉数据以及如果是非共用字典，字典与经销商/厂家关系")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 1000, message = "新增数据字典成功") })
	@PostMapping("dictionaryConfigs")
	public RestfulResponse<DictionaryConfigDto> createDictionaryConfig(HttpServletRequest request,
			@ApiParam(name = "数据字典下拉框信息", required = true) @RequestBody DictionaryConfigDto dto) {
		RestfulResponse<DictionaryConfigDto> restful = new RestfulResponse<DictionaryConfigDto>(dto);
		restful.setTableName("dictionary_config");
		restful.setOperateType(Response.INSERT);
		if (dto.getDictionaryName() == null || "".equals(dto.getDictionaryName())) {
			restful.setCode(ECode.ARG_ERROR.getCode());
			restful.setMessage("数据字典名为空！");
			return restful;
		}

		UserLoginInfoVo loginVo = (UserLoginInfoVo) request.getAttribute("loginUserInfo");
		try {
			boolean isNew = dataDictonaryService.editDictionaryConfig(dto, loginVo.getIfSuperAdmin(),
					loginVo.getAgencyId());
			restful.setMajorKeyId(dto.getDictionaryName());
			if (!isNew) {
				restful.setOperateType(Response.UPDATE);
			}
		} catch (MessageException e) {
			logger.error("新增/修改数据字典失败{}", e.getMessage());
			restful.setCode(ECode.ADD_ERROR.getCode());
			if (e.getCode() != null) {
				restful.setCode(e.getCode().getCode());
			}
			restful.setMessage("新增/修改数据字典失败" + e.getMessage());
			return restful;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("新增/修改数据字典失败{}", e.getMessage());
			restful.setCode(ECode.ADD_ERROR.getCode());
			restful.setMessage("新增/修改数据字典失败！");
			return restful;
		}

		// 新增成功后清理下缓存
		cacheService.cacheEvictDictionaryConfig();
		cacheService.cacheEvictCommonDictionary();
		// cacheService.cacheEvictAgencyAllDictionary(loginVo.getAgencyId());
		return restful;
	}

	@PermissionAuth
	@OperationLog
	@ApiOperation(value = "新增数据字典下拉框信息", notes = "单独在某个数据字典下新增下拉框属性")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 1000, message = "新增数据字典成功") })
	@PostMapping("basicDatas")
	public RestfulResponse<CommonDictionaryDto> createBasicData(
			@ApiParam(name = "数据字典下拉框信息", required = true) @RequestBody CommonDictionaryDto commonDictionary) {
		RestfulResponse<CommonDictionaryDto> restful = new RestfulResponse<CommonDictionaryDto>(commonDictionary);
		restful.setTableName("COMMON_DICTIONARY");
		restful.setOperateType(Response.INSERT);
		if (commonDictionary.getDictionaryName() == null || "".equals(commonDictionary.getDictionaryName())) {
			restful.setCode(ECode.ARG_ERROR.getCode());
			restful.setMessage("数据字典名为空！");
			return restful;
		}
		if (commonDictionary.getPropertyValue() == null || "".equals(commonDictionary.getPropertyValue())) {
			restful.setCode(ECode.ARG_ERROR.getCode());
			restful.setMessage("属性值为空！");
			return restful;
		}
		if (commonDictionary.getPropertyText() == null || "".equals(commonDictionary.getPropertyText())) {
			restful.setCode(ECode.ARG_ERROR.getCode());
			restful.setMessage("属性内容为空！");
			return restful;
		}

		try {
			dataDictonaryService.insertCommonDictionary(commonDictionary);
			restful.setMajorKeyId(commonDictionary.getDictionaryName() + "," + commonDictionary.getPropertyValue());
		} catch (MessageException e) {
			logger.error("新增数据字典下拉框失败{}", e.getMessage());
			restful.setCode(ECode.ADD_ERROR.getCode());
			if (e.getCode() != null) {
				restful.setCode(e.getCode().getCode());
			}
			restful.setMessage("新增数据字典下拉框失败" + e.getMessage());
			return restful;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("新增数据字典下拉框失败{}", e.getMessage());
			restful.setCode(ECode.ADD_ERROR.getCode());
			restful.setMessage("新增数据字典下拉框失败！");
			return restful;
		}

		// 新增成功后清理下缓存
		cacheService.cacheEvictCommonDictionary();
		return restful;
	}

	@PermissionAuth
	@OperationLog
	@ApiOperation(value = "修改数据字典下拉框信息", notes = "单独在某个数据字典下修改下拉框属性")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 1000, message = "修改数据字典成功") })
	@PutMapping("basicDatas")
	public RestfulResponse<CommonDictionaryDto> updateBasicData(
			@ApiParam(name = "数据字典下拉框信息", required = true) @RequestBody CommonDictionaryDto commonDictionary) {
		RestfulResponse<CommonDictionaryDto> restful = new RestfulResponse<CommonDictionaryDto>(commonDictionary);
		restful.setTableName("COMMON_DICTIONARY");
		restful.setOperateType(Response.UPDATE);
		restful.setMajorKeyId(commonDictionary.getDictionaryName() + ":" + commonDictionary.getPropertyValue());
		if (commonDictionary.getDictionaryName() == null || "".equals(commonDictionary.getDictionaryName())) {
			restful.setCode(ECode.ARG_ERROR.getCode());
			restful.setMessage("数据字典名为空！");
			return restful;
		}
		if (commonDictionary.getPropertyValue() == null || "".equals(commonDictionary.getPropertyValue())) {
			restful.setCode(ECode.ARG_ERROR.getCode());
			restful.setMessage("属性值为空！");
			return restful;
		}
		if (commonDictionary.getPropertyText() == null || "".equals(commonDictionary.getPropertyText())) {
			restful.setCode(ECode.ARG_ERROR.getCode());
			restful.setMessage("属性内容为空！");
			return restful;
		}

		try {
			dataDictonaryService.updateCommonDictionary(commonDictionary);
		} catch (MessageException e) {
			logger.error("修改数据字典下拉框失败{}", e.getMessage());
			restful.setCode(ECode.UPDATE_ERROR.getCode());
			if (e.getCode() != null) {
				restful.setCode(e.getCode().getCode());
			}
			restful.setMessage("修改数据字典下拉框失败" + e.getMessage());
			return restful;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改数据字典下拉框失败{}", e.getMessage());
			restful.setCode(ECode.UPDATE_ERROR.getCode());
			restful.setMessage("修改数据字典下拉框失败，请检测该数据字典是否已存在！");
			return restful;
		}

		// 修改成功后清理下缓存
		cacheService.cacheEvictCommonDictionary();
		return restful;
	}

	// @ApiOperation(value = "查询数据字典信息(工厂端查询所有经销商数据的情况)", notes = "支持同时查询多个数据字典")
	// @ApiImplicitParams({
	// @ApiImplicitParam(paramType = "path", name = "dictionaryNameArr", dataType =
	// "String", value = "数据字典名称", required = true, defaultValue =
	// "ROLE_TYPE,GENDER"),
	// @ApiImplicitParam(paramType = "path", name = "status", dataType = "String",
	// value = "是否启用", required = true, defaultValue = "Y") })
	// @ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
	// @ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"),
	// @ApiResponse(code = 1000, message = "查询成功(第一层key为数据字典名称)") })
	// @GetMapping("basicDatasForPc/{dictionaryNameArr}/{status}")
	// public RestfulResponse<Map<String, Map<String, CommonDictionaryDto>>>
	// findBasicDataBySystem(HttpServletRequest request,
	// @PathVariable String[] dictionaryNameArr, @PathVariable String status) {
	//
	// RestfulResponse<Map<String, Map<String, CommonDictionaryDto>>> restful = new
	// RestfulResponse<Map<String, Map<String, CommonDictionaryDto>>>(
	// new HashMap<>());
	// if (dictionaryNameArr == null || dictionaryNameArr.length == 0) {
	// restful.setCode(ECode.ARG_ERROR.getCode());
	// restful.setMessage("数据字典属性名为空！");
	// return restful;
	// }
	//
	// try {
	// status = status.replace("{status}", "");
	// agencyId = agencyId.replace("{agencyId}", "");
	// if (agencyId == null || "".equals(agencyId)) {
	// agencyId = JSONObject.parseObject((String)
	// request.getAttribute("loginUserInfo")).getString("agencyId");
	// }
	// Map<String, Map<String, CommonDictionaryDto>> mapAll = dataDictonaryService
	// .findCommonDictionaryMap(dictionaryNameArr, status, agencyId);
	// if (mapAll == null || mapAll.size() == 0) {
	// restful.setCode(ECode.NO_DATA_RESULT.getCode());
	// restful.setMessage("未查询到数据字典信息！");
	// } else {
	// restful.setData(mapAll);
	// }
	// } catch (MessageException e) {
	// logger.error("查询数据字典失败{}", e.getMessage());
	// restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
	// if (e.getCode() != null) {
	// restful.setCode(e.getCode().getCode());
	// }
	// restful.setMessage("查询数据字典失败{}", e.getMessage());
	// return restful;
	// } catch (Exception e) {
	// e.printStackTrace();
	// logger.error("查询数据字典失败{}", e.getMessage());
	// restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
	// restful.setMessage("查询数据字典失败！");
	// return restful;
	// }
	// return restful;
	// }

	@ApiOperation(value = "清除所有缓存数据", notes = "")
	@PutMapping("basicDatas/cleanAllCache")
	public RestfulResponse<String> cleanAllCache() {
		RestfulResponse<String> restful = new RestfulResponse<String>("");
		cacheService.cacheEvictAllPropertyConfig();
		cacheService.cacheEvictCommonDictionary();;
		cacheService.cacheEvictAllDataDisctionary();
		cacheService.cacheEvictDictionaryConfig();
		cacheService.cacheEvictFixedPropertyConfig();
		return restful;
	}
}
