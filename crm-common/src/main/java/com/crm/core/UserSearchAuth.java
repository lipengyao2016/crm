//package com.crm.core;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import com.alibaba.fastjson.JSONObject;
//import com.by.crm.common.DataConvertUtil;
//import com.by.crm.dto.UserAgencyRoleDto;
//import com.by.crm.feign.administration.AdministrationFeignClient;
//import com.by.crm.feign.administration.AdministrationFeignCommon;
//import com.by.crm.feign.basic.BasicFeignClient;
//import com.by.crm.vo.PermissionSensitiveVo;
//import com.by.crm.vo.StatisticsCondition;
//import com.by.crm.vo.UserAuthSensitiveVo;
//import com.by.crm.vo.UserLoginInfoVo;
//
//@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
//@Component
//public class UserSearchAuth {
//	@Autowired
//	private AdministrationFeignCommon administrationFeign;
//	@Autowired
//	private BasicFeignClient basicFeign;
//
//	/**
//	 * 获取用户能查看的其他用户数据对应的用户编码串
//	 *
//	 * @param agencyId
//	 * @param createdBy
//	 * @return
//	 */
//	public String getSearchUserIds(String agencyId, String createdBy) {
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
//				.getRequest();
//		String loginUserInfo = null;
//		String userLoginId = null;
//		String loginAgencyId = agencyId;
//
//		UserLoginInfoVo loginVo = (UserLoginInfoVo) request.getAttribute("loginUserInfo");
//		if (loginVo != null) {
//			userLoginId = loginVo.getUserLoginId();
//		} else {
//			throw new MessageException("用户未登录或已下线!",ECode.NOT_LOGGED_IN);
//		}
//
//		// 作为查询条件的用户范围
//		String userIds = "";
//		// 查询条件里的用户信息
//		List<String> condUserIdList = new ArrayList();
//		if (createdBy != null && !"".equals(createdBy)) {
//			condUserIdList = findUserIdsByCond(createdBy);
//			if (condUserIdList == null || condUserIdList.size() == 0) {
//				return "''";
//			}
//		} else {
//			condUserIdList.add(userLoginId);
//		}
//
//		if (condUserIdList != null && condUserIdList.size() > 0) {
//			for (String userId : condUserIdList) {
//				userIds += ",'" + userId + "'";
//			}
//
//			userIds = userIds.substring(1);
//		}
//
//		if (loginVo != null) {
//			// 如果是超级管理员，则不做权限验证
//			String ifSuperAdmin = loginVo.getIfSuperAdmin();
//			if ("Y".equals(ifSuperAdmin)) {
//				return userIds;
//			}
//		}
//
//		if (agencyId == null || "".equals(agencyId)) {
//			agencyId = loginAgencyId;
//		}
//
//		/**
//		 * 不管任何角色了,全部通过维护上下级来管理用户查看数据范围
//		 */
//		// RestfulResponse<List<UserAgencyRoleDto>> roleRestful =
//		// administrationFeign.findUserAgencyRoleList(userLoginId);
//		// if (roleRestful.getCode() == ECode.NO_DATA_RESULT.getCode()) {
//		// throw new MessageException("用户：" + userName + "未维护任何经销商角色！");
//		// }
//		//
//		// if (roleRestful.getCode() != ECode.SUCCESS.getCode()) {
//		// // 调用微服务出错了，抛出异常信息
//		// throw new MessageException(roleRestful.getMessage());
//		// }
//		//
//		// List<String> roleIdList = new ArrayList<>();
//		// 获取用户在归属经销商下的角色信息
//		// List<UserAgencyRoleDto> roleList = roleRestful.getData();
//		// for (UserAgencyRoleDto dto : roleList) {
//		// if (dto.getAgencyId().equals(agencyId)) {
//		// roleIdList.add(dto.getRoleId());
//		// }
//		// }
//
//		// if (roleList.size() == 0) {
//		// // throw new MessageException("用户：" + userName + "在经销商" + agencyName +
//		// // "下未维护任何角色！");
//		// return "'nothing'";
//		// }
//
//		// RestfulResponse<String> result = null;
//		// 经销商管理员，可查询此经销商下所有用户数据
//		// if (roleIdList.contains("AGENCY_ADMIN")) {
//		// result = administrationFeign.findAgencyUserIds(agencyId);
//		// if (result.getCode() == ECode.NO_DATA_RESULT.getCode()) {
//		// throw new MessageException("经销商：" + agencyName + "下未绑定任何用户！");
//		// }
//		// if (result.getCode() != ECode.SUCCESS.getCode()) {
//		// throw new MessageException("查询经销商用户出错：" + result.getMessage());
//		// }
//		// }
//
//		// 店长，可查询此门店下所有用户数据
//		// if (roleIdList.contains("SHOP_ADMIN")) {
//		// result = administrationFeign.findStoreUserIds(agencyId);
//		// if (result.getCode() == ECode.NO_DATA_RESULT.getCode()) {
//		// throw new MessageException("门店下未绑定任何用户！");
//		// }
//		// if (result.getCode() != ECode.SUCCESS.getCode()) {
//		// throw new MessageException("查询门店用户出错：" + result.getMessage());
//		// }
//		// }
//
//		// if (result != null) {
//		// String ids = "";
//		// String agencyUserIds = result.getData();
//		// if (createdBy != null && !"".equals(createdBy)) {
//		// String[] condUserIdArr = userIds.split(",");
//		// String[] agencyUserIdArr = agencyUserIds.split(",");
//		// // 取交集
//		// for (String userId : condUserIdArr) {
//		// for (String id : agencyUserIdArr) {
//		// if (userId.equals(id)) {
//		// ids += "," + userId;
//		// }
//		// }
//		// }
//		// ids = ids.replaceFirst(",", "");
//		// } else {
//		// ids = agencyUserIds;
//		// }
//		//
//		// if ("".equals(ids)) {
//		// return "''";
//		// }
//		// return ids;
//		// }
//
//		// 普通用户只能查询自己的数据及被授权查看其他人的数据
//		List<String> userIdList = new ArrayList<>();
//		userIdList.add(userLoginId);
//
//		// 查询被授权了哪些用户
//		List<PermissionSensitiveVo> voList = administrationFeign.findPermissionAuthFromUserList();
//		if (voList == null || voList.size() == 0) {
//			// 没有可查看的下级，则只能查看自己
//			if (condUserIdList != null && condUserIdList.contains(userLoginId)) {
//				userIds = "'" + userLoginId + "'";
//				return userIds;
//			} else {
//				return "''";
//			}
//		}
//
//		Map<String, List<String>> sensitiveMap = new HashMap<>();
//		for (PermissionSensitiveVo vo : voList) {
//			userIdList.add(vo.getUserId());
//
//			// 将敏感字段信息放入request请求,当查询结果返回时,将对应的用户的查询结果设置敏感词字段值不显示
//			if (!sensitiveMap.containsKey(vo.getUserId())) {
//				List<String> sensitiveProperty = new ArrayList<>();
//				sensitiveProperty.add(vo.getPropertyName());
//				sensitiveMap.put(vo.getUserId(), sensitiveProperty);
//			} else {
//				List<String> sensitiveProperty = sensitiveMap.get(vo.getUserId());
//				sensitiveProperty.add(vo.getPropertyName());
//			}
//
//			request.setAttribute("sensitiveMap", sensitiveMap);
//		}
//
//		String ids = "";
//		if (createdBy != null && !"".equals(createdBy)) {
//			// 取交集
//			for (String userId : condUserIdList) {
//				for (String id : userIdList) {
//					if (userId.equals(id)) {
//						ids += ",'" + userId + "'";
//					}
//				}
//			}
//		} else {
//			for (String id : userIdList) {
//				ids += ",'" + id + "'";
//			}
//		}
//		ids = ids.replaceFirst(",", "");
//
//		if ("".equals(ids)) {
//			return "''";
//		}
//		return ids;
//	}
//
//	/**
//	 * 过滤掉返回结果的敏感词信息
//	 *
//	 * @param list
//	 * @param userId
//	 *            传入的用户账号字段名(默认为createdBy)
//	 * @param userName
//	 *            返回的用户姓名字段名(默认为createdUserName)
//	 * @return
//	 */
//	public List getSensitiveFiltrateResult(List list, String userId, String userName) {
//		if (userId == null || "".equals(userId)) {
//			userId = "createdBy";
//		}
//		if (userName == null || "".equals(userName)) {
//			userName = "createdUserName";
//		}
//		if (list != null && list.size() > 0) {
//			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
//					.getRequest();
//
//			Map<String, List<String>> sensitiveMap = (Map<String, List<String>>) request.getAttribute("sensitiveMap");
//			List<String> userIdList = new ArrayList();
//			for (int i = 0; i < list.size(); i++) {
//				Object obj = list.get(i);
//				JSONObject json = setJson(obj, sensitiveMap, userId);
//				obj = JSONObject.toJavaObject(json, Object.class);
//				list.set(i, obj);
//
//				JSONObject objJson = JSONObject.parseObject(JSONObject.toJSONString(obj));
//				if (objJson.containsKey(userId)) {
//					userIdList.add(objJson.getString(userId));
//				}
//			}
//
//			if (userIdList.size() > 0) {
//				Map<String, String> userMap = administrationFeign.findUserNames(userIdList);
//				for (int i = 0; i < list.size(); i++) {
//					Object obj = list.get(i);
//					JSONObject objJson = JSONObject.parseObject(JSONObject.toJSONString(obj));
//					objJson.put(userName, userMap.get(objJson.get(userId)));
//					list.set(i, JSONObject.toJavaObject(objJson, Object.class));
//				}
//			}
//		}
//
//		return list;
//	}
//
//	/**
//	 * 处理单个返回结果的敏感词
//	 *
//	 * @param obj
//	 * @param userId
//	 * @param userName
//	 * @return
//	 */
//	public Object getSensitiveFiltrateResult(Object obj, String userId, String userName) {
//		if (userId == null || "".equals(userId)) {
//			userId = "createdBy";
//		}
//		if (userName == null || "".equals(userName)) {
//			userName = "createdUserName";
//		}
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
//				.getRequest();
//
//		Map<String, List<String>> sensitiveMap = (Map<String, List<String>>) request.getAttribute("sensitiveMap");
//		JSONObject json = setJson(obj, sensitiveMap, userId);
//		obj = JSONObject.toJavaObject(json, Object.class);
//
//		JSONObject objJson = JSONObject.parseObject(JSONObject.toJSONString(obj));
//		if (objJson.containsKey(userId)) {
//			List<String> userIdList = new ArrayList();
//			userIdList.add(objJson.getString(userId));
//			Map<String, String> userMap = administrationFeign.findUserNames(userIdList);
//
//			objJson = JSONObject.parseObject(JSONObject.toJSONString(obj));
//			objJson.put(userName, userMap.get(objJson.get(userId)));
//			obj = JSONObject.toJavaObject(objJson, Object.class);
//		}
//
//		return obj;
//
//	}
//
//	/**
//	 * 封装数据统计查询条件
//	 *
//	 * @return
//	 */
//	public StatisticsCondition buildStatisticsCondition() {
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
//				.getRequest();
//
//		UserLoginInfoVo loginVo = (UserLoginInfoVo) request.getAttribute("loginUserInfo");
//		StatisticsCondition cond = new StatisticsCondition();
//		cond.setUserLoginId(loginVo.getUserLoginId());
//
//		String userIds = getSearchUserIds(cond.getAgencyId(), cond.getUserLoginId());
//		cond.setUserIds(userIds);
//
//		/**
//		 * 不管任何角色了,全部通过维护上下级来管理用户查看数据范围
//		 */
//		// if (cond.getAgencyId() == null || "".equals(cond.getAgencyId())) {
//		// cond.setAgencyId(loginVo.getAgencyId());
//		// }
//		//
//		// boolean isAdmin = false; // 是否经销商管理员或超级管理员
//		// boolean isShopkeeper = false; // 是否门店管理员
//		//
//		// // 首先查询用户角色
//		// RestfulResponse<List<UserAgencyRoleDto>> roleRestful = administrationFeign
//		// .findUserAgencyRoleList(cond.getUserLoginId());
//		// if (roleRestful.getCode() == ECode.NO_DATA_RESULT.getCode()) {
//		// throw new MessageException("用户：" + loginVo.getUserName() +
//		// "未维护任何经销商角色！");
//		// }
//		//
//		// if (roleRestful.getCode() != ECode.SUCCESS.getCode()) {
//		// // 调用微服务出错了，抛出异常信息
//		// throw new MessageException(roleRestful.getMessage());
//		// }
//		// List<UserAgencyRoleDto> roleList = roleRestful.getData();
//		// for (UserAgencyRoleDto dto : roleList) {
//		// // 获取指定经销商下该用户拥有的角色
//		// if (dto.getAgencyId().equals(cond.getAgencyId())) {
//		// if ("SUPER_ADMIN".equals(dto.getRoleId()) ||
//		// "AGENCY_ADMIN".equals(dto.getRoleId())) {
//		// isAdmin = true;
//		//
//		// // 查询经销商下所有门店
//		// RestfulResponse<List<String>> storeRestful = administrationFeign
//		// .findAgencyStoreIds(cond.getAgencyId());
//		// if (roleRestful.getCode() == ECode.NO_DATA_RESULT.getCode()) {
//		// throw new MessageException("经销商：" + loginUserInfo.getString("agencyName") +
//		// "没有任何门店！");
//		// }
//		//
//		// if (storeRestful.getCode() != ECode.SUCCESS.getCode()) {
//		// // 调用微服务出错了，抛出异常信息
//		// throw new MessageException(storeRestful.getMessage());
//		// }
//		// List<String> storIdList = storeRestful.getData();
//		// String storeIds = "";
//		// for (String id : storIdList) {
//		// storeIds += ",'" + id + "'";
//		// }
//		// storeIds = storeIds.replaceFirst(",", "");
//		// cond.setAgencyId(storeIds);
//		// cond.setStoreId("");// 管理员默认不查询单个门店，如需查询单个门店则在具体统计的业务代码里重新设置
//		// break;
//		// }
//		// if ("SHOP_ADMIN".equals(dto.getRoleId())) {
//		// isShopkeeper = true;
//		// cond.setStoreId(loginVo.getStoreId());
//		// break;
//		// }
//		// }
//		// }
//		//
//		// if (!isAdmin) {
//		// cond.setAgencyId("");
//		// }
//		// if (!isAdmin && !isShopkeeper) {
//		// // 非管理员和门店管理员则需具体控制权限
//		// String userIds = getSearchUserIds(cond.getAgencyId(), cond.getUserLoginId());
//		// cond.setUserIds(userIds);
//		// }
//
//		return cond;
//	}
//
//	public JSONObject setJson(Object obj, Map<String, List<String>> sensitiveMap, String userId) {
//		if (userId == null || "".equals(userId)) {
//			userId = "createdBy";
//		}
//		JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(obj));
//		if (!json.containsKey(userId)) {
//			return json;
//		}
//
//		if (sensitiveMap == null || sensitiveMap.size() == 0) {
//			return json;
//		}
//		for (String id : sensitiveMap.keySet()) {
//			if (id.equals(json.getString(userId))) {
//				Set<String> propertySet = json.keySet();
//				List<String> propertyList = sensitiveMap.get(id);
//				for (String property : propertySet) {
//					if (propertyList.contains(property)) {
//						json.put(property, "***");
//					}
//				}
//				break;
//			}
//		}
//
//		return json;
//	}
//
//	private List<String> findUserIdsByCond(String createdBy) {
//		List<String> condList = new ArrayList<>();
//		Map<String, String> userMap = administrationFeign.findUserNameByKeyword(createdBy);
//		if (userMap != null && userMap.size() > 0) {
//			condList.addAll(userMap.keySet());
//		}
//		return condList;
//	}
//}
