package com.crm.feign.basic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crm.common.ECode;
import com.crm.common.RestfulResponse;
import com.crm.dto.basic.CommonDictionaryDto;
import com.crm.dto.basic.PropertyConfigDto;
import com.crm.dto.basic.PropertyConfigNotForbiddenDto;
import com.crm.vo.basic.PropertyConfigVo;
import com.crm.dto.basic.CommonDictionaryDto;
import com.crm.dto.basic.PropertyConfigDto;
import com.crm.dto.basic.PropertyConfigNotForbiddenDto;
import com.crm.vo.basic.PropertyConfigVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class BasicHystrixClientFallbackFactory implements FallbackFactory<BasicFeignClient> {
	private static final Logger log = LoggerFactory.getLogger(BasicHystrixClientFallbackFactory.class);

	@Override
	public BasicFeignClient create(Throwable cause) {
		return new BasicFeignClientWithFallBackFactory() {
			@Override
			public RestfulResponse<Map<String, Map<String, CommonDictionaryDto>>> findBasicDataForPc(
					String[] dictionaryNameArr, String status,  Long agencyId) {
				RestfulResponse<Map<String, Map<String, CommonDictionaryDto>>> restful = new RestfulResponse<Map<String, Map<String, CommonDictionaryDto>>>(
						new HashMap());
				restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
				restful.setMessage("查询基础数据字典出错！" + cause.getMessage());
				cause.printStackTrace();
				return restful;
			}


			@Override
			public RestfulResponse<List<PropertyConfigDto>> findPropertyConfigList(String permissionId,
                                                                                   String permissionType, String status) {
				RestfulResponse<List<PropertyConfigDto>> response = RestfulResponse
						.error(ECode.CICD_JOB_QUERY_CODE_BRANCHES_FAILED);
				response.setMessage("查询动态配置属性信息出错！");
				return response;
			}

			@Override
			public RestfulResponse<List<PropertyConfigVo>> queryPropertyConfigFeign(Long agencyId,
                                                                                    String permissionType, String permissionId, String ifForbidden) {
				RestfulResponse<List<PropertyConfigVo>> response = RestfulResponse
						.error(ECode.CICD_JOB_QUERY_CODE_BRANCHES_FAILED);
				response.setMessage("查询动态字段出错！");
				return response;
			}

			@Override
			public RestfulResponse<Void> deleteByPermission( Long agencyId, String permissionType,
					String permissionId) {
				RestfulResponse<Void> response = RestfulResponse.error(ECode.CICD_JOB_QUERY_CODE_BRANCHES_FAILED);
				response.setMessage("删除动态字段出错！");
				return response;
			}

			@Override
			public RestfulResponse<List<PropertyConfigDto>> queryExsitByPermission( Long agencyId,
					String permissionType, String propertyNames) {
				RestfulResponse<List<PropertyConfigDto>> response = RestfulResponse
						.error(ECode.CICD_JOB_QUERY_CODE_BRANCHES_FAILED);
				response.setMessage("查询重复的动态字段出错！");
				return response;
			}

			@Override
			public RestfulResponse<PropertyConfigDto> createPropertyConfig(PropertyConfigDto dto) {
				RestfulResponse<PropertyConfigDto> response = RestfulResponse
						.error(ECode.CICD_JOB_QUERY_CODE_BRANCHES_FAILED);
				response.setMessage("新增动态字段出错！");
				return response;
			}

			@Override
			public RestfulResponse<PropertyConfigNotForbiddenDto> savePropertyConfigByNotForbidden(
					PropertyConfigNotForbiddenDto dto) {
				RestfulResponse<PropertyConfigNotForbiddenDto> response = RestfulResponse
						.error(ECode.CICD_JOB_QUERY_CODE_BRANCHES_FAILED);
				response.setMessage("修改动态字段出错！");
				return response;
			}

			@Override
			public RestfulResponse<List<PropertyConfigVo>> findSystemPropertyConfigList(String permissionType,
					String status) {
				RestfulResponse<List<PropertyConfigVo>> response = RestfulResponse
						.error(ECode.CICD_JOB_QUERY_CODE_BRANCHES_FAILED);
				response.setMessage("查询固定字段出错！");
				return response;
			}
			 

			@Override
			public RestfulResponse<List<PropertyConfigVo>> systemPpropertyById(String permissionId,
					String permissionType, String status) {
				RestfulResponse<List<PropertyConfigVo>> response = RestfulResponse
						.error(ECode.CICD_JOB_QUERY_CODE_BRANCHES_FAILED);
				response.setMessage("查询固定字段出错(查询工厂同步的字段出错)！");
				return response;
			}
		};
	}
}