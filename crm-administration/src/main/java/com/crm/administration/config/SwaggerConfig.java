package com.crm.administration.config;

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
//@Profile("pro")
public class SwaggerConfig {
	Predicate<RequestHandler> predicate = new Predicate<RequestHandler>() {
		@Override
		public boolean apply(RequestHandler input) {
			Class<?> declaringClass = input.declaringClass();
			if (declaringClass == BasicErrorController.class)// 排除
				return false;
            return declaringClass.isAnnotationPresent(RestController.class);
        }
	};

	@Bean
	public Docket basicApi() {
		return new Docket(DocumentationType.SWAGGER_2).genericModelSubstitutes(DeferredResult.class)
				.useDefaultResponseMessages(false).forCodeGeneration(true).pathMapping("/").select().apis(predicate)
				.paths(or(regex("/.*"))).build().apiInfo(apiInfo("系统后台管理服务API")).securitySchemes(Lists.newArrayList(apiKey()));
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
//	@Bean
//	public Docket loginApi() {
//		return new Docket(DocumentationType.SWAGGER_2).groupName("login").genericModelSubstitutes(DeferredResult.class)
//				.useDefaultResponseMessages(false).forCodeGeneration(true).pathMapping("/").select().apis(predicate)
//				.paths(or(regex("/login/.*"))).build().apiInfo(apiInfo("登录管理服务API"));
//	}
//
//	@Bean
//	public Docket mainApi() {
//		return new Docket(DocumentationType.SWAGGER_2).groupName("main").genericModelSubstitutes(DeferredResult.class)
//				.useDefaultResponseMessages(false).forCodeGeneration(true).pathMapping("/").select().apis(predicate)
//				.paths(or(regex("/main/.*"))).build().apiInfo(apiInfo("首页数据加载服务API"));
//	}
//
//	@Bean
//	public Docket userApi() {
//		return new Docket(DocumentationType.SWAGGER_2).groupName("sysconfig/user").genericModelSubstitutes(DeferredResult.class)
//				.useDefaultResponseMessages(false).forCodeGeneration(true).pathMapping("/").select().apis(predicate)
//				.paths(or(regex("/sysconfig/user/.*"))).build().apiInfo(apiInfo("用户管理服务API"));
//	}
//
//	@Bean
//	public Docket positionApi() {
//		return new Docket(DocumentationType.SWAGGER_2).groupName("sysconfig/position").genericModelSubstitutes(DeferredResult.class)
//				.useDefaultResponseMessages(false).forCodeGeneration(true).pathMapping("/").select().apis(predicate)
//				.paths(or(regex("/sysconfig/position/.*"))).build().apiInfo(apiInfo("职位管理服务API"));
//	}
//
//	@Bean
//	public Docket permissionApi() {
//		return new Docket(DocumentationType.SWAGGER_2).groupName("sysconfig/permission").genericModelSubstitutes(DeferredResult.class)
//				.useDefaultResponseMessages(false).forCodeGeneration(true).pathMapping("/").select().apis(predicate)
//				.paths(or(regex("/sysconfig/permission/.*"))).build().apiInfo(apiInfo("权限管理服务API"));
//	}
}
