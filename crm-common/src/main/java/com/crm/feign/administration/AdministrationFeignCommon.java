package com.crm.feign.administration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crm.common.ECode;
import com.crm.common.MessageException;
import com.crm.common.RestfulResponse;
import com.crm.vo.PermissionVo;
import com.crm.common.ECode;
import com.crm.common.MessageException;
import com.crm.common.RestfulResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdministrationFeignCommon {

	@Autowired
	private AdministrationFeignClient feignClient;

    /**
     * 查询该用户是否有操作该菜单的权限
     *
     * @param requestMethod
     * @return
     */
    public Boolean hasPermission(String requestMethod) {
		RestfulResponse<Boolean> permissionResult = feignClient.hasPermission(requestMethod);
		if (permissionResult.getCode() == ECode.NO_DATA_RESULT.getCode()) {
			return false;
		}
		if (permissionResult.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(permissionResult.getMessage());
		}

		return permissionResult.getData();
	}
	/**
	 * 查询权限是否需要权限认证
	 *
	 * @param requestMethod
	 * @return
	 */
	public Boolean needPermissionByUrl(String requestMethod) {
		RestfulResponse<Boolean> permissionResult = feignClient.needPermissionByUrl(requestMethod);
		if (permissionResult.getCode() == ECode.NO_DATA_RESULT.getCode()) {
			return false;
		}
		if (permissionResult.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(permissionResult.getMessage());
		}

		return permissionResult.getData();
	}

	/**
	 * 根据权限url和请求方式获取对应权限编码
	 *
	 * @param permissionUrl
	 * @param requestMethod
	 * @return
	 */
	public PermissionVo findPermissionByUrl(String permissionUrl, String requestMethod) {
		RestfulResponse<PermissionVo> permissionResult = feignClient
				.findPermissionByUrl(permissionUrl, requestMethod);
		if (permissionResult.getCode() == ECode.NO_DATA_RESULT.getCode()) {
			return null;
		}
		if (permissionResult.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(permissionResult.getMessage());
		}

		return permissionResult.getData();
	}
}
