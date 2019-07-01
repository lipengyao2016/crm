package com.crm.feign.administration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crm.common.ECode;
import com.crm.common.RestfulResponse;
import com.crm.vo.PermissionVo;
import com.crm.common.ECode;
import com.crm.common.RestfulResponse;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class AdministrationHystrixClientFallbackFactory implements FallbackFactory<AdministrationFeignClient> {
	private static final Logger log = LoggerFactory.getLogger(AdministrationHystrixClientFallbackFactory.class);

	@Override
	public AdministrationFeignClient create(Throwable cause) {
		return new AdministrationFeignClientWithFallBackFactory() {
			@Override
			public RestfulResponse<Boolean> needPermissionByUrl(String requestMethod) {
				log.error("查询权限出错！",cause);
				RestfulResponse<Boolean> restful = new RestfulResponse<Boolean>(false);
				restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
				restful.setMessage("查询权限出错！");
				return restful;
			}

			@Override
			public RestfulResponse<Boolean> hasPermission(String requestMethod) {
				log.error("查询权限出错！",cause);
				RestfulResponse<Boolean> restful = new RestfulResponse<Boolean>(false);
				restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
				restful.setMessage("查询权限出错！");
				return restful;
			}

			@Override
			public RestfulResponse<PermissionVo> findPermissionByUrl(String permissionUrl, String requestMethod) {
				log.error("查询权限出错！",cause);
				RestfulResponse<PermissionVo> restful = new RestfulResponse<PermissionVo>();
				restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
				restful.setMessage("查询权限出错！");
				return restful;
			}
		};
	}
}