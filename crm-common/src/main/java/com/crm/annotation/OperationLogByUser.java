package com.crm.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface OperationLogByUser {
	String mrjokRequestUrl();
	
	int tableNameKey();
	
	boolean isRestFulRequest() default true;
	
	String requestParam() default "";
}