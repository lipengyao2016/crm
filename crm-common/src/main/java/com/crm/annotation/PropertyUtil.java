package com.crm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PropertyUtil {
	/**
	 * 字段id，如createdBy，则会根据createdBy查询用户姓名信息
	 * 
	 * @return String
	 */
	String propertyKey() default "";

	/**
	 * 字段对应的id字段名(如用户姓名所对应的登录帐号的字段名createdBy)
	 * 
	 * @return String
	 */
	String propertyName() default "";

	/**
	 * 字段对应的父id，如数据字典下拉值对应的数据字典id
	 * 
	 * @return String
	 */
	String parentKey() default "";

	/**
	 * 是否在多条数据(列表)时获取附件信息
	 * 
	 * @return boolean
	 */
	boolean isRequestList() default true;
}
