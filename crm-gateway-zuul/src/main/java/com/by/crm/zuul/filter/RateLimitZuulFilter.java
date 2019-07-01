package com.by.crm.zuul.filter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.alibaba.fastjson.JSONObject;
import com.by.crm.zuul.core.RedisUtils;
import com.by.crm.zuul.core.RestfulResponse;
import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 请求次数限流
 * 
 * @author Administrator
 *
 */
public class RateLimitZuulFilter extends ZuulFilter {
	// 每秒限流1000个访问量
	private final RateLimiter rateLimiter = RateLimiter.create(1000.0);

	@SuppressWarnings("unused")
	@Autowired
	private RedisUtils redisUtils;

	@Override
	public boolean shouldFilter() {
		// 限流开关
		boolean flag = redisUtils.getRequestTimes();
		if (flag) {
			return true;
		}
		
		RequestContext ctx = RequestContext.getCurrentContext();
		String responseBody = ctx.getResponseBody();
		if (responseBody == null || "".equals(responseBody)) {
			return true;
		}

		return false;
	}

	@Override
	public Object run() {
		// zuul为单节点时使用
		if (!rateLimiter.tryAcquire()) {
			RequestContext ctx = RequestContext.getCurrentContext();
			HttpServletResponse response = ctx.getResponse();
			response.setContentType("application/json; charset=UTF-8");
			RestfulResponse restful = new RestfulResponse();
			restful.setCode(1001);
			restful.setMessage("系统繁忙，请稍后再试！");
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(200);
			ctx.setResponseBody(JSONObject.toJSONString(restful));
		}

		// 有多个zuul实例时使用(可以针对某个IP或者用户进行限流)
		// boolean flag = redisUtils.getRequestTimes();
		// if(!flag) {
		// RequestContext ctx = RequestContext.getCurrentContext();
		// HttpServletResponse response = ctx.getResponse();
		// response.setContentType("application/json; charset=UTF-8");
		// RestfulResponse restful = new RestfulResponse();
		// restful.setCode(1001);
		// restful.setMessage("系统繁忙，请稍后再试！");
		// ctx.setSendZuulResponse(false);
		// ctx.setResponseStatusCode(200);
		// ctx.setResponseBody(JSONObject.toJSONString(restful));
		// }

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