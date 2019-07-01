package com.by.crm.zuul.filter;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.by.crm.zuul.config.JWTUtils;
import com.by.crm.zuul.core.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class CrmPreZuulFilter extends ZuulFilter {
	private static final Logger LOGGER = LoggerFactory.getLogger(CrmPreZuulFilter.class);
	@Autowired
	private RedisUtils redisUtils;

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		String responseBody = ctx.getResponseBody();
		if (responseBody == null || "".equals(responseBody)) {
			return true;
		}
		return false;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		HttpServletResponse response = ctx.getResponse();
		String sessionId = request.getSession().getId();

		// zuul默认屏蔽了cookie，这样浏览器端每次请求的sessionId都会不一致。故手动在请求头里添加cookie，并设置sessionId
		ctx.addZuulRequestHeader("Cookie", "SESSION=" + sessionId);
		ctx.addZuulRequestHeader("loginUserInfo", null);
		String host = request.getRemoteHost();
		String url = request.getRequestURI();
		CrmPreZuulFilter.LOGGER.info("请求的host:{}", host + ":" + url + ":" + sessionId);

		ctx.addZuulRequestHeader("requestURL", url);
		if (sessionId == null || "".equals(sessionId)) {
		sessionId = (String) request.getSession().getAttribute("sessionId");
	}

		String token = request.getHeader("Authorization");
		String userLoginId = null;
		String userLoginInfoVoJSON="";
		//解析JWT认证
		if(token != null && !"".equals(token)){
			userLoginInfoVoJSON = JWTUtils.verifyToken(token);
			userLoginId=JSONObject.parseObject(userLoginInfoVoJSON).get("userLoginId")+"";
		}


		String fromBy = RequestHelper.getFromBy(request);

		LOGGER.info("请求的终端类型：" + fromBy);
		for (String requestUri : INoLoginRequestUrl.URL_LIST) {
			if (url.contains(requestUri)) {
				return null;
			}
		}

		response.setContentType("application/json; charset=UTF-8");
		RestfulResponse restful = new RestfulResponse();

		if (url.contains("/null/")) {
			restful.setCode(1001);
			restful.setMessage("非法请求路径！");
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(200);
			ctx.setResponseBody(JSONObject.toJSONString(restful));
			return null;
		}

		// 考虑的用户可能不用PC端的浏览器
		if (sessionId == null || "".equals(sessionId)) {
			if (!"PC".equals(fromBy)) {
				LOGGER.info("token：" + token + ",userLoginId：" + userLoginId);
				if (StringUtils.isBlank(token) || StringUtils.isBlank(userLoginId)) {
					restful.setCode(1001);
					restful.setMessage("非法请求，token或用户登录账号为空！");
					ctx.setSendZuulResponse(false);
					ctx.setResponseStatusCode(200);
					ctx.setResponseBody(JSONObject.toJSONString(restful));
					return null;
				}
			}
		}

		// 获取登录用户信息
		if (StringUtils.isNotBlank(token)) {
			LOGGER.info("登录信息loginUserInfo" + userLoginInfoVoJSON);
			if (userLoginInfoVoJSON == null || "".equals(userLoginInfoVoJSON)) {
				restful.setCode(1430);
				restful.setMessage("账号:" + userLoginId + "已下线或在其他终端登录!");
				ctx.setSendZuulResponse(false);
				ctx.setResponseStatusCode(200);
				ctx.setResponseBody(JSONObject.toJSONString(restful));
				return null;
			}

			LOGGER.info("重新设置token超时时间");
			// 重新设置token超时时间
		} else if (StringUtils.isNotBlank(sessionId)) {
			userLoginInfoVoJSON = (String) request.getSession().getAttribute("loginUserInfo");
		} else {
			restful.setCode(1008);
			restful.setMessage("非法访问系统!");
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(200);
			ctx.setResponseBody(JSONObject.toJSONString(restful));
			return null;
		}

		try {
			if (userLoginInfoVoJSON != null && !"".equals(userLoginInfoVoJSON)) {
				ctx.addZuulRequestHeader("loginUserInfo", URLDecoder.decode(userLoginInfoVoJSON,"UTF-8"));
				// if
				// (DigestUtils.shaHex("123456").equals(JSONObject.parseObject(loginUserInfo).getString("shaHexPwd")))
				// {
				// String createdTime =
				// JSONObject.parseObject(loginUserInfo).getString("createdTime");
				// Long createdMillis = System.currentTimeMillis();
				// if (createdTime != null && !"".equals(createdTime)) {
				// createdMillis = (new SimpleDateFormat("yyyy-MM-dd
				// HH:mm:ss")).parse(createdTime).getTime();
				// }
				// Long currentMillis = System.currentTimeMillis();
				// if (currentMillis - createdMillis >= 10 * 24 * 60 * 60 * 1000) {
				// restful.setCode(1425);
				// restful.setMessage("密码已过期，请联系管理员重置！");
				// ctx.setSendZuulResponse(false);
				// ctx.setResponseStatusCode(200);
				// ctx.setResponseBody(JSONObject.toJSONString(restful));
				// return null;
				// }
				// }
			} else {
				//TODO
			}

		} catch (Exception e) {
			LOGGER.info("zuul设置登录用户信息出错{}", e.getMessage(),e);
			restful.setCode(1425);
			restful.setMessage("zuul设置登录用户信息出错"+ e.getMessage());
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(200);
			ctx.setResponseBody(JSONObject.toJSONString(restful));
			return null;
		}

		return null;
	}

	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return 2;
	}

}
