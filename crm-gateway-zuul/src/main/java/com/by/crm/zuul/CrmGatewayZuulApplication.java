package com.by.crm.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import com.by.crm.zuul.filter.CrmErrorZuulFilter;
import com.by.crm.zuul.filter.CrmPreZuulFilter;
import com.by.crm.zuul.filter.MemoryLimitZuulFilter;
import com.by.crm.zuul.filter.RateLimitZuulFilter;

@EnableZuulProxy
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800)
@SpringCloudApplication
public class CrmGatewayZuulApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CrmGatewayZuulApplication.class);
	}

	@Bean
	public RateLimitZuulFilter rateLimitZuulFilter() {
		return new RateLimitZuulFilter();
	}

	@Bean
	public MemoryLimitZuulFilter memoryZuulFilter() {
		return new MemoryLimitZuulFilter();
	}

	@Bean
	public CrmPreZuulFilter preZuulFilter() {
		return new CrmPreZuulFilter();
	}

	@Bean
	public CrmErrorZuulFilter errorZuulFilter() {
		return new CrmErrorZuulFilter();
	}

	public static void main(String[] args) {
		SpringApplication.run(CrmGatewayZuulApplication.class, args);
	}
}
