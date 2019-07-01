package com.by.crm.zuul.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.alibaba.fastjson.JSONObject;
import com.by.crm.zuul.core.RestfulResponse;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class CrmErrorZuulFilter extends ZuulFilter {
	private static final Logger LOGGER = LoggerFactory.getLogger(CrmErrorZuulFilter.class);

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();

		ctx.getResponse().setContentType("application/json; charset=UTF-8");
		RestfulResponse restful = new RestfulResponse();

		LOGGER.info("zuul出现异常:" + ctx.getResponseBody());

		restful.setCode(1001);
		restful.setMessage("请求异常：" + ctx.getResponseBody());
		ctx.setResponseBody(JSONObject.toJSONString(restful));
		ctx.setSendZuulResponse(false);
		ctx.setResponseStatusCode(200);
		return null;
	}

	@Override
	public String filterType() {
		return FilterConstants.ERROR_TYPE;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

}
