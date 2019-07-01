package com.by.crm.zuul.filter;

import java.util.Collection;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.SystemPublicMetrics;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.alibaba.fastjson.JSONObject;
import com.by.crm.zuul.core.RestfulResponse;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 内存限流
 * 
 * @author Administrator
 *
 */
public class MemoryLimitZuulFilter extends ZuulFilter {
	private static final Logger LOGGER = LoggerFactory.getLogger(MemoryLimitZuulFilter.class);

	@Autowired
	private SystemPublicMetrics systemPublicMetrics;

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		String responseBody = ctx.getResponseBody();
		if (responseBody != null && !"".equals(responseBody)) {
			return false;
		}

		Collection<Metric<?>> metrics = systemPublicMetrics.metrics();
		Optional<Metric<?>> freeMemoryMetric = metrics.stream().filter(t -> "mem.free".equals(t.getName())).findFirst();
		// 如果不存在这个指标，稳妥起见，返回true，开启限流
		if (!freeMemoryMetric.isPresent()) {
			return true;
		}

		long freeMemory = freeMemoryMetric.get().getValue().longValue();
		LOGGER.info("当前JVM可用内存:" + freeMemory);
		// 如果JVM可用内存小于10M，开启流控
		return freeMemory < 10000L;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletResponse response = ctx.getResponse();
		response.setContentType("application/json; charset=UTF-8");
		RestfulResponse restful = new RestfulResponse();
		restful.setCode(1001);
		restful.setMessage("服务器内存空间不足，请稍后再试！");
		ctx.setSendZuulResponse(false);
		ctx.setResponseStatusCode(200);
		ctx.setResponseBody(JSONObject.toJSONString(restful));
		return null;
	}

	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return 1;
	}
}