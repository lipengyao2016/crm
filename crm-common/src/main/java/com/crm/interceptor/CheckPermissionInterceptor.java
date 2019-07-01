package com.crm.interceptor;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crm.annotation.NoPermissionAuth;
import com.crm.annotation.PermissionAuth;
import com.crm.common.ECode;
import com.crm.common.ELoginChannel;
import com.crm.common.RestfulResponse;
import com.crm.core.RequestHelper;
import com.crm.feign.administration.AdministrationFeignCommon;
import com.crm.utils.JWTUtils;
import com.crm.vo.PermissionVo;
import com.crm.vo.UserLoginInfoVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;

/**
 * 用户请求访问权限菜单验证拦截器
 *
 */
@Component
public class CheckPermissionInterceptor extends HandlerInterceptorAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(CheckPermissionInterceptor.class);

	private static final Map<String, String> operationMap = new HashMap<String, String>();
	static {
		operationMap.put("POST", "保存");
		operationMap.put("PUT", "修改");
		operationMap.put("DELETE", "删除");
		operationMap.put("GET", "查询");
	}
	@Autowired
	private AdministrationFeignCommon feignClient;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception {
		//测试中

		response.setContentType("application/json; charset=UTF-8");
		RestfulResponse<Void> restful = new RestfulResponse<Void>();

		final HandlerMethod handlerMethod = (HandlerMethod) handle;
		final Method method = handlerMethod.getMethod();
		final Class<?> clazz = method.getDeclaringClass();

//			request.setAttribute("loginUserInfo", null);
		String sessionId = request.getSession().getId();
		if (sessionId == null || "".equals(sessionId)) {
			sessionId = (String) request.getSession().getAttribute("sessionId");
		}

		String URLUserLoginInfo=request.getHeader("loginUserInfo") ;
		String userLoingJSON = URLDecoder.decode(URLUserLoginInfo,"UTF-8");
		UserLoginInfoVo loginVo = null;

		if(userLoingJSON != null && !"".equals(userLoingJSON)) {
			try {
				loginVo=JSONObject.parseObject(userLoingJSON,UserLoginInfoVo.class);
				request.setAttribute("loginUserInfo",loginVo);
				LOGGER.info("登录用户信息：" + loginVo);
			} catch (Exception e1) {
				restful.setCode(ECode.NOT_LOGGED_IN.getCode());
				restful.setMessage("登录用户信息转换出错！");
				response.getWriter().print(JSONObject.toJSONString(restful));
				response.getWriter().close();
				return false;
			}
		}
/*
		String currentRequestUrl = request.getRequestURL().toString();
		System.out.println("当前请求url:" + currentRequestUrl);

		if (!handle.getClass().isAssignableFrom(HandlerMethod.class)) {
			return true;
		}

		// 针对添加了@NoPermissionAuth注解的请求不做拦截处理(如不需要权限验证的请求或微服务之间调用的请求)
		if (clazz.isAnnotationPresent(NoPermissionAuth.class) || method.isAnnotationPresent(NoPermissionAuth.class)) {
			return true;
		}

		// 针对添加了@Permission注解的请求做拦截处理
		if (clazz.isAnnotationPresent(PermissionAuth.class) || method.isAnnotationPresent(PermissionAuth.class)) {
			String fromBy = RequestHelper.getFromBy(request);
			if (!ELoginChannel.PC.name().equals(fromBy)) {
				LOGGER.info("终端类型：" + fromBy + "请求的token值为：" + token);
				if (!ifUserLogin(loginVo)) {
					restful.setCode(ECode.NOT_LOGGED_IN.getCode());
					restful.setMessage("因长时间未操作，本app和服务器的连接已断开，请重新登陆！");
					response.getWriter().print(JSONObject.toJSONString(restful));
					response.getWriter().close();
					return false;
				}

			} else if (StringUtils.isNotBlank(sessionId)) {
				// 登录用户修改自身密码后，session被清空，设置key为pass的值用以区分是失效还是修改密码
				String pass = (String) request.getSession().getAttribute("pass");
				if ("success".equals(pass)) {
					restful.setCode(ECode.NOT_LOGGED_IN.getCode());
					restful.setMessage("密码修改成功，请重新登录！");
					response.getWriter().print(JSONObject.toJSONString(restful));
					response.getWriter().close();
					return false;
				}
				if (!ifUserLogin(loginVo)) {
					restful.setCode(ECode.NOT_LOGGED_IN.getCode());
					restful.setMessage("因长时间未操作，本网页和服务器的连接已断开，请重新登陆！");
					response.getWriter().print(JSONObject.toJSONString(restful));
					response.getWriter().close();
					return false;
				}
			} else {
				restful.setCode(ECode.MALFORMEDURL.getCode());
				restful.setMessage("非法访问系统！");
				response.getWriter().print(JSONObject.toJSONString(restful));
				response.getWriter().close();
				return false;
			}

			// 权限验证
			try {
				// 如果是超级管理员，则不做权限验证
				String ifSuperAdmin = loginVo.getIfSuperAdmin();
				if ("Y".equals(ifSuperAdmin)) {
					return true;
				}

				// 外部请求的url
				String requestUrl = request.getHeader("requestURL");
				String requestMethod = request.getMethod();
				boolean needPermission = feignClient.needPermissionByUrl(requestMethod);

				// 需要权限认证
				if (needPermission) {
					PermissionVo permissionRestful = feignClient.findPermissionByUrl(requestUrl,
							requestMethod);

					// 外部系统调用直接返回
//					String ifAgencyUser = loginVo.getIfAgencyUser();
//					if (!"Y".equals(ifAgencyUser)) {
//						restful.setCode(ECode.UNAUTHORIZED.getCode());
//						restful.setMessage("你没有" + operationMap.get(requestMethod)
//								+ list.iterator().next().getPermissionName() + "的权限！");
//						response.getWriter().print(JSONObject.toJSONString(restful));
//						response.getWriter().close();
//						return false;
//					}


					if (requestUrl.contains("/operationLogDatas")) {
						String majorKeyId = request.getParameter("majorKeyId");
						if (majorKeyId != null && !"".equals(majorKeyId)) {
							return true;
						}
					}

					// 验证用户是否有权限
					boolean hasPermission = feignClient.hasPermission(requestMethod);
					if (hasPermission) {
						return true;
					}

					if (permissionRestful != null) {
						restful.setCode(ECode.UNAUTHORIZED.getCode());
						restful.setMessage("你没有" + operationMap.get(requestMethod)
								+ permissionRestful.getName() + "的权限！");
						response.getWriter().print(JSONObject.toJSONString(restful));
						response.getWriter().close();
						return false;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.info("系统异常{}", e.getMessage());
				restful.setCode(ECode.SYSTEM_UNKNOWN_EXCEPTION.getCode());
				restful.setMessage(ECode.SYSTEM_UNKNOWN_EXCEPTION.getMessage());
				response.getWriter().print(JSONObject.toJSONString(restful));
				response.getWriter().close();
				return false;
			}
		}*/
		return true;
	}

	private boolean ifUserLogin(UserLoginInfoVo loginVo) {
		if (loginVo == null) {
			return false;
		}

		if (loginVo.getUserLoginId() == null || "".equals(loginVo.getUserLoginId())) {
			return false;
		}

		return true;
	}
}
