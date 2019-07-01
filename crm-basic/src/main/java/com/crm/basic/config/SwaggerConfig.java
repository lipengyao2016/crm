package com.crm.basic.config;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import com.google.common.collect.Lists;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.google.common.base.Predicate;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SuppressWarnings({"deprecation","unchecked"})
@Configuration
@EnableSwagger2
//@Profile("dev")
public class SwaggerConfig {
	Predicate<RequestHandler> predicate = new Predicate<RequestHandler>() {
		@Override
		public boolean apply(RequestHandler input) {
			Class<?> declaringClass = input.declaringClass();
			if (declaringClass == BasicErrorController.class)// 排除
				return false;
			if (declaringClass.isAnnotationPresent(RestController.class)) // 被注解的类
				return true;
			return false;
		}
	};

	@Bean
	public Docket basicApi() {
		return new Docket(DocumentationType.SWAGGER_2).genericModelSubstitutes(DeferredResult.class)
				.useDefaultResponseMessages(false).forCodeGeneration(true).pathMapping("/").select().apis(predicate)
				.paths(or(regex("/.*"))).build().apiInfo(apiInfo("基础数据管理服务API")).securitySchemes(Lists.newArrayList(apiKey()));
	}

	private ApiInfo apiInfo(String title) {
		return new ApiInfoBuilder().title(title).termsOfServiceUrl("/").contact(new Contact("约范科技", "", ""))
				.version("1.0").build();
	}
	/**
	 * swagger-ui开启JWT访问支持
	 */
	private ApiKey apiKey() {
		return new ApiKey("apikey", "Authorization", "header");
	}
}
