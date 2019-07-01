//package com.crm.core;
//
//import java.lang.reflect.Field;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.stereotype.Component;
//
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//
//@Component
//@SuppressWarnings({ "rawtypes", "unchecked" })
//public class DtoClassCache {
//
//	/**
//	 * 缓存所有dto类名称及model描述
//	 *
//	 * @return
//	 * @throws ClassNotFoundException
//	 */
//	@Cacheable(value = "DtoClassModelMap", keyGenerator = "demoKeyGenerator")
//	public Map<String, String> getDtoClassModelMap() throws ClassNotFoundException {
//		Map<String, Class> classMap = ClassUtil.getAllDtoClass("com.by.crm.basic.model");
//		Map<String, String> result = new HashMap();
//		for (Class clz : classMap.values()) {
//			// 判断类上是否有次注解
//			boolean clzHasAnno = clz.isAnnotationPresent(ApiModel.class);
//			if (clzHasAnno) {
//				// 获取类上的注解
//				ApiModel annotation = (ApiModel) clz.getAnnotation(ApiModel.class);
//				result.put(clz.getName(), annotation.value());
//			}
//		}
//
//		return result;
//	}
//
//	/**
//	 * 缓存所有dto类名称及类型
//	 *
//	 * @return
//	 * @throws ClassNotFoundException
//	 */
//	@Cacheable(value = "DtoClassMap", keyGenerator = "demoKeyGenerator")
//	public Map<String, Class> getDtoClassMap() throws ClassNotFoundException {
//		return ClassUtil.getAllDtoClass("com.by.crm.basic.model");
//	}
//
//	/**
//	 * 缓存所有dto类字段名及model描述
//	 *
//	 * @return
//	 * @throws ClassNotFoundException
//	 */
//	@Cacheable(value = "DtoClassPropertyMap", keyGenerator = "demoKeyGenerator")
//	public Map<String, Map<String, String>> getDtoClassPropertyMap() throws ClassNotFoundException {
//		// 此处要用反射将字段中的注解解析出来
//		Map<String, Map<String, String>> propertyMap = new HashMap();
//		Map<String, Class> classMap = ClassUtil.getAllDtoClass("com.by.crm.basic.model");
//		for (Class clz : classMap.values()) {
//			// 解析字段上是否有注解
//			// ps：getDeclaredFields会返回类所有声明的字段，包括private、protected、public，但是不包括父类的
//			// getFields:则会返回包括父类的所有的public字段，和getMethods()一样
//			Field[] fields = clz.getDeclaredFields();
//			Map<String, String> map = new HashMap();
//			for (Field field : fields) {
//				boolean fieldHasAnno = field.isAnnotationPresent(ApiModelProperty.class);
//				if (fieldHasAnno) {
//					ApiModelProperty fieldAnno = field.getAnnotation(ApiModelProperty.class);
//					if (!fieldAnno.hidden()) {
//						// 输出注解属性
//						map.put(field.getName(), fieldAnno.value());
//					}
//				}
//			}
//
//			propertyMap.put(clz.getName(), map);
//		}
//		return propertyMap;
//	}
//}
