package com.crm.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import com.crm.dto.basic.CommonDictionaryDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import static com.crm.utils.StringUtil.isEmpty;
import static com.crm.utils.StringUtil.isNotEmpty;

@Component
public class DaoUtil {
	public static final Logger log = LoggerFactory.getLogger(DaoUtil.class);
//	@Autowired
//	private AssistantFeignClient assistantfeignClient;
//	@Autowired
//	private BasicFeignClient basicFeignClient;
//	@Autowired
//	private AdministrationFeignClient administrationFeignClient;
//
//	private static DaoUtil daoutil;
//
//	// 工具类中静态方法使用注入Bean的方法。只会在服务器启用时注入一次(隐患为服务挂掉重新连接会不会存在不刷新的问题？)
//	@PostConstruct
//	private void init() {
//		daoutil = this;
//		daoutil.administrationFeignClient = this.administrationFeignClient;
//		daoutil.assistantfeignClient = this.assistantfeignClient;
//		daoutil.basicFeignClient = this.basicFeignClient;
//	}
//
//	/**
//	 * 将多个对象中的字典key查出value并使用：字段+Name的方式加入对象中
//	 *
//	 * @param source
//	 *            集合数据对象
//	 * @param beProcessed
//	 *            要查询的字典字段
//	 *            查出后的字典
//	 * @throws Exception
//	 */
//	public static <T> void doHandelListDicData(List<T> source, List<String> beProcessed, Class<T> type, String agencyId)
//			throws Exception {
//		if (source == null || source.size() == 0) {
//			return;
//		}
//		RestfulResponse<Map<String, Map<String, CommonDictionaryDto>>> findBasicDataList = daoutil.basicFeignClient
//				.findBasicDataForPc(beProcessed.toArray(new String[beProcessed.size()]), "Y", agencyId);
//		Map<String, Map<String, CommonDictionaryDto>> target = null;
//		// 如果调用失败也补入中文提示“暂无数据”
//		boolean isException = false;
//		if (findBasicDataList.getCode() != ECode.SUCCESS.getCode()) {
//			isException = true;
//			log.info("异常信息：调用数据字典失败值'" + JSONObject.toJSONString(beProcessed) + "',ErrorMessage:"
//					+ findBasicDataList.getMessage());
//		} else {
//			target = findBasicDataList.getData();
//		}
//		List<Field> fieldList = new ArrayList<>();
//		Class<? extends Object> tempClass = source.get(0).getClass();
//		while (tempClass != null) {// 当父类为null的时候说明到达了最上层的父类(Object类).
//			fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
//			tempClass = tempClass.getSuperclass(); // 得到父类,然后赋给自己
//		}
//
//		List<Map<String, Object>> listDataMap = new ArrayList<Map<String, Object>>();
//		for (int i = 0; i < source.size(); i++) {
//			HashMap<String, Object> hashMap = new HashMap<String, Object>();
//			hashMap.put("obj", source.get(i));
//			listDataMap.add(hashMap);
//		}
//
//		// Map<String,String> datamap=new HashMap<String,String>();
//		// 根据字典id加入字典中文名称 ，例如：propertytype/propertytypeName
//		for (String colName : beProcessed) {
//			for (Field f : fieldList) {
//				f.setAccessible(true);
//				// 对应基础数据列名，取值
//				if (f.getName().equals(colName)) {
//					for (int i = 0; i < source.size(); i++) {
//
//						T t = source.get(i);
//						String key = f.get(t) + "";
//						String value = "";
//						if (isEmpty(key) || isException) {
//							value = "暂无数据";
//						} else {
//							// 取出字典实体
//							CommonDictionaryDto commonDictionaryDto = target.get(colName).get(key);
//							if (commonDictionaryDto == null) {
//								value = "暂无数据";
//							} else {
//								value = commonDictionaryDto.getPropertyText();
//							}
//						}
//						Map<String, Object> map = listDataMap.get(i);
//						// 加入对象中
//						map.put(colName + "Name", value);
//					}
//				}
//			}
//		}
//		for (Field f : fieldList) {
//			// 对应基础数据列名，取值
//			for (int i = 0; i < listDataMap.size(); i++) {
//				Map<String, Object> map = listDataMap.get(i);
//				Set<String> keySet = map.keySet();
//				Object object = map.get("obj");
//				for (String colName : keySet) {
//					if (f.getName().equals(colName)) {
//						setFieldValue(f, object, map.get(colName));
//					}
//				}
//			}
//		}
//	}
//
//	/**
//	 * 将多个对象中的字典key查出value并使用：字段+Name的方式加入对象中
//	 *
//	 * @param source
//	 *            单个数据对象
//	 * @param beProcessed
//	 *            要查询的字典字段
//	 * @throws Exception
//	 */
//	public static <T> void doHandelListDicData(T t, List<String> beProcessed, Class<T> type, String agencyId)
//			throws Exception {
//		RestfulResponse<Map<String, Map<String, CommonDictionaryDto>>> findBasicDataList = daoutil.basicFeignClient
//				.findBasicDataForPc(beProcessed.toArray(new String[beProcessed.size()]), "Y", agencyId);
//		Map<String, Map<String, CommonDictionaryDto>> target = null;
//		// 如果调用失败也补入中文提示“暂无数据”
//		boolean isException = false;
//		if (findBasicDataList.getCode() != ECode.SUCCESS.getCode()) {
//			isException = true;
//			log.info("异常信息：调用数据字典失败值'" + JSONObject.toJSONString(beProcessed) + "',ErrorMessage:"
//					+ findBasicDataList.getMessage());
//		} else {
//			target = findBasicDataList.getData();
//		}
//		List<Field> fieldList = new ArrayList<>();
//		Class<? extends Object> tempClass = t.getClass();
//		while (tempClass != null) {// 当父类为null的时候说明到达了最上层的父类(Object类).
//			fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
//			tempClass = tempClass.getSuperclass(); // 得到父类,然后赋给自己
//		}
//		Map<String, String> datamap = new HashMap<String, String>();
//		// 根据字典id加入字典中文名称 ，例如：propertytype/propertytypeName
//		for (String colName : beProcessed) {
//			for (Field f : fieldList) {
//				f.setAccessible(true);
//				// 对应基础数据列名，取值
//				if (f.getName().equals(colName)) {
//					String key = f.get(t) + "";
//					String value = "";
//					if (isEmpty(key) || isException) {
//						value = "暂无数据";
//					} else {
//						// 取出字典实体
//						CommonDictionaryDto commonDictionaryDto = target.get(colName).get(key);
//						if (commonDictionaryDto == null) {
//							value = "暂无数据";
//						} else {
//							value = commonDictionaryDto.getPropertyText();
//						}
//					}
//					// 加入对象中
//					datamap.put(colName + "Name", value);
//					// setFieldValue(f,t,value);
//				}
//			}
//		}
//		Set<String> keySet = datamap.keySet();
//		for (String colName : keySet) {
//			for (Field f : fieldList) {
//				// 对应基础数据列名，取值
//				if (f.getName().equals(colName)) {
//					setFieldValue(f, t, datamap.get(colName));
//				}
//			}
//		}
//	}
//
//
//	/**
//	 * 根据附件key获取附件集合
//	 *
//	 * @param attachmentKey
//	 * @return
//	 * @throws Exception
//	 */
//	public static List<AttachmentVo> listAttachment(String attachmentKey) throws Exception {
//		List<AttachmentVo> result = new ArrayList<AttachmentVo>();
//		if (StringUtil.isEmpty(attachmentKey)) {
//			return result;
//		}
//		RestfulResponse<List<AttachmentVo>> listData = daoutil.assistantfeignClient.listData(attachmentKey);
//		if (listData.getCode() != ECode.SUCCESS.getCode()) {
//			log.error(listData.getMessage());
//		} else {
//			result = listData.getData();
//		}
//		return result;
//	}
//
//	/**
//	 * 处理列表对象中包含的附件对象信息
//	 *
//	 * @param source
//	 * @throws Exception
//	 */
//	public static <T> void doHandelAttachment(List<T> source, String listName) throws Exception {
//		if (source == null || source.size() == 0) {
//			return;
//		}
//		List<Field> fieldList = new ArrayList<>();
//		Class<? extends Object> tempClass = source.get(0).getClass();
//
//		// 当父类为null的时候说明到达了最上层的父类(Object类).
//		// 排除BaseDto所有实体继承类，有相同的attachmentKey
//		while (tempClass != null && tempClass != BaseDto.class) {
//			fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
//			tempClass = tempClass.getSuperclass(); // 得到父类,然后赋给自己
//		}
//		// 记录附件id和附件对象数组两个属性对象
//		Field attachmentKeyField = null;
//		Field attachmentVoField = null;
//		// 附件id数组
//		String[] addressArray = new String[source.size()];
//		boolean isEnter = false;
//		for (int i = 0; i < fieldList.size(); i++) {
//			Field field = fieldList.get(i);
//			field.setAccessible(true);
//			// 记录附件key
//			if (field.getName().equals("attachmentKey")) {
//				attachmentKeyField = field;
//				isEnter = true;
//				for (int j = 0; j < source.size(); j++) {
//					// 记录附件key加入数组中
//					if (isNotEmpty(field.get(source.get(j)))) {
//						addressArray[j] = (String) field.get(source.get(j));
//					}
//				}
//			}
//			// 记录附件数组属性
//			if (field.getName().equals(listName)) {
//				attachmentVoField = field;
//			}
//			// 跳出条件
//			if (attachmentVoField != null && isEnter) {
//				break;
//			}
//		}
//		// 调用微服务获取附件组数据
//		RestfulResponse<Map<String, List<AttachmentVo>>> attachmentsByArray = daoutil.assistantfeignClient
//				.listDatas(addressArray);
//		Map<String, List<AttachmentVo>> attachmentList = null;
//		if (attachmentsByArray.getCode() != ECode.SUCCESS.getCode()) {
//			log.info("异常信息：调用附件信息失败值'" + JSONObject.toJSONString(addressArray) + "',ErrorMessage:"
//					+ attachmentsByArray.getMessage());
//		} else {
//			attachmentList = attachmentsByArray.getData();
//		}
//		for (int i = 0; i < source.size(); i++) {
//			// 将地址对象循环放入列表对象中
//			T t = source.get(i);
//			// 根据每个对象id取出对应的地址对象
//			String attachmentKey = attachmentKeyField.get(t) + "";
//			List<AttachmentVo> attachmentVo = new ArrayList<AttachmentVo>();
//			if (attachmentList != null && attachmentList.get(attachmentKey) != null) {
//				attachmentVo = attachmentList.get(attachmentKey);
//			}
//			// 写入地址对象
//			if (attachmentVoField != null) {
//				attachmentVoField.set(t, attachmentVo);
//			}
//		}
//	}
//
//	/**
//	 * 处理对象中包含的附件对象信息
//	 *
//	 * @param source
//	 * @throws Exception
//	 */
//	public static <T> void doHandelAttachment(T source, String listName) throws Exception {
//		if (source == null) {
//			return;
//		}
//		List<Field> fieldList = new ArrayList<>();
//		Class<? extends Object> tempClass = source.getClass();
//		while (tempClass != null) {// 当父类为null的时候说明到达了最上层的父类(Object类).
//			fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
//			tempClass = tempClass.getSuperclass(); // 得到父类,然后赋给自己
//		}
//		// 记录附件id和附件对象数组两个属性对象
//		Field attachmentVoField = null;
//		// 附件id数组
//		String attachmentKey = "";
//		boolean isEnter = false;
//		for (int i = 0; i < fieldList.size(); i++) {
//			Field field = fieldList.get(i);
//			field.setAccessible(true);
//			// 记录附件key
//			if (field.getName().equals("attachmentKey")) {
//				// 记录附件key加入数组中
//				if (isNotEmpty(field.get(source))) {
//					attachmentKey = (String) field.get(source);
//				}
//			}
//			// 记录附件数组属性
//			if (field.getName().equals(listName)) {
//				attachmentVoField = field;
//			}
//			// 跳出条件
//			if (attachmentVoField != null && isEnter) {
//				break;
//			}
//		}
//		// 调用微服务获取附件组数据
//		RestfulResponse<List<AttachmentVo>> attachmentsByArray = daoutil.assistantfeignClient.listData(attachmentKey);
//		List<AttachmentVo> attachmentList = null;
//		if (attachmentsByArray.getCode() != ECode.SUCCESS.getCode()) {
//			log.info("异常信息：调用附件信息失败值'" + JSONObject.toJSONString(attachmentKey) + "',ErrorMessage:"
//					+ attachmentsByArray.getMessage());
//		} else {
//			attachmentList = attachmentsByArray.getData();
//		}
//		List<AttachmentVo> attachmentVo = new ArrayList<AttachmentVo>();
//		if (attachmentList != null && attachmentList.size() > 0) {
//			attachmentVo = attachmentList;
//		}
//		// 写入附件对象
//		if (attachmentVoField != null) {
//			attachmentVoField.set(source, attachmentVo);
//		}
//	}
//
//
//
//	/**
//	 * 处理对象中包含的附件对象信息
//	 *
//	 * @param source
//	 * @throws Exception
//	 */
//	@SuppressWarnings("unchecked")
//	public static <T> void saveAttachment(T source, String attachmentVoName) throws Exception {
//		if (source == null) {
//			return;
//		}
//		String attachmentKey = "";
//		List<Attachment> attachmentVo = null;
//		Field attachemntKeyField = null;
//		Field attachmentVoField = null;
//		List<Field> fieldList = new ArrayList<>();
//		Class<? extends Object> tempClass = source.getClass();
//		while (tempClass != null) {// 当父类为null的时候说明到达了最上层的父类(Object类).
//			fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
//			tempClass = tempClass.getSuperclass(); // 得到父类,然后赋给自己
//		}
//		for (int i = 0; i < fieldList.size(); i++) {
//			Field field = fieldList.get(i);
//			field.setAccessible(true);
//			// 记录附件key
//			if (field.getName().equals("attachmentKey")) {
//				// 记录附件key
//				attachemntKeyField = field;
//				attachmentKey = (String) field.get(source);
//			}
//			// 记录附件数组属性
//			if (field.getName().equals(attachmentVoName)) {
//				attachmentVoField = field;
//				attachmentVo = (List<Attachment>) field.get(source);
//			}
//			// 跳出条件
//			if (attachmentVoField != null && attachemntKeyField != null) {
//				break;
//			}
//		}
//		// 调用微服务获取附件组数据
//		if (attachmentVo == null || attachmentVo.size() == 0) {
//			return;
//		} else {
//
//			// 如果原存在key
//			String temAttachmentKey = attachmentKey;
//			if (isNotEmpty(temAttachmentKey)) {
//				attachmentVo.forEach(p -> {
//					p.setAttachmentKey(temAttachmentKey);
//				});
//			}
//
//			RestfulResponse<String> saveData = daoutil.assistantfeignClient.saveData(attachmentVo);
//			if (saveData.getCode() != ECode.SUCCESS.getCode()) {
//				log.info("调用失败值：'" + JSONObject.toJSONString(attachmentVo) + ";ErrorMessage:" + saveData.getMessage());
//				throw new MessageException(saveData.getMessage());
//			} else {
//				String attachmentKey2 = saveData.getData();
//				attachemntKeyField.set(source, attachmentKey2);
//			}
//		}
//	}

}