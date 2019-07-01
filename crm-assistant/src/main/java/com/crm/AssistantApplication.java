package com.crm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.crm.assistant.mapper")
@EnableEurekaClient
@EnableCircuitBreaker
@EnableFeignClients
public class AssistantApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		//设置启动类，用于独立Tomcat运行的入口
		return builder.sources(AssistantApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(AssistantApplication.class, args);
	}
}
 