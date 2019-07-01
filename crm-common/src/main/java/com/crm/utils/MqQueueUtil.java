package com.crm.utils;

import java.util.HashMap;
import java.util.Map;

public class MqQueueUtil {
	/**
	 * MQ队列
	 */
	public static final String AGC_NEW_ADDRESS = "AGC_NEW_ADDRESS"; // 新增地址MQ队列
	public static final String AGC_NEW_FACTORY = "AGC_NEW_FACTORY"; // 新增工厂MQ队列
	public static final String ADDRESS_ID = "ADDRESS_ID"; // 地址编码MQ队列
	public static final String AGC_NEW_HOUSE_TYPE = "AGC_NEW_HOUSE_TYPE"; // 新增房屋类型MQ队列
	public static final String AGC_HANDLE_INVENTORY = "AGC_HANDLE_INVENTORY"; // 增减产品库存MQ队列
	public static final String AGC_HANDLE_CUSTOMER_STATUS = "AGC_HANDLE_CUSTOMER_STATUS"; // 修改客户状态MQ队列
	public static final String AGC_SEND_UPLOAD = "AGC_SEND_UPLOAD"; // 发送上传MQ队列
	public static final String AGC_UPLOAD_CALLBACK = "AGC_UPLOAD_CALLBACK"; // 文件上传回调(返回附件key)
	public static final String FAC_NEW_AGENCY = "FAC_NEW_AGENCY"; // 同步新增经销商端经销商信息
	public static final String FAC_RELEVANCE_AGENCY_ID = "FAC_RELEVANCE_AGENCY_ID"; // 同步新增经销商端的经销商编码到工厂端
	public static final String HAS_ORDER_CUSTOMER = "HAS_ORDER_CUSTOMER"; // 包含有效订单客户编码队列
	public static final String AGC_USER_ADMIN = "AGC_USER_ADMIN"; // 创建经销商管理员队列
	public static final String AGC_USER_ADMIN_CODE = "AGC_USER_ADMIN_CODE"; // 经销商管理员创建回传队列
	public static final String FAC_NEW_LOGAUDIT = "FAC_NEW_LOGAUDIT"; // 同步经销商端日志审核信息
	public static final String FAC_NEW_STAGE = "FAC_NEW_STAGE"; // 同步经销商端阶段活动信息
	public static final String FAC_NEW_TEMPLATE = "FAC_NEW_TEMPLATE"; // 同步经销商端竞赛日志信息
	public static final String FAC_NEW_THEME = "FAC_NEW_THEME"; // 同步经销商端主题活动信息
	public static final String AGC_NEW_ACTIVITYLOG = "AGC_NEW_ACTIVITYLOG"; // 同步工厂端端写日志信息
	public static final String AGC_AGENCY_STATUS = "AGC_AGENCY_STATUS"; // 同步修改经销商状态消息
	public static final String AGC_SAVE_ATTACHMENT = "AGC_SAVE_ATTACHMENT"; // 经销商端队列中加入保存附件队列
	public static final String AGC_NEW_BRAND = "AGC_NEW_BRAND"; // 经销商端队列中加入保存品牌品类队列
	public static final String AGC_DEL_BRAND = "AGC_DEL_BRAND"; // 经销商端队列中加入删除品牌品类队列
	public static final String AGC_NEW_PROPERTYFORBIDDEN = "AGC_NEW_PROPERTYFORBIDDEN"; // 经销商端列队中写入动态字段列队
	public static final String FAC_AGENCY_STORE_QTY = "FAC_AGENCY_STORE_QTY";// 更新经销商门店数量队列
	public static final String AGC_SEND_PPT_UPLOAD = "AGC_SEND_PPT_UPLOAD";// 上传PPT附件
	public static final String AGC_COURSE_PPT_CALLBACK = "AGC_COURSE_PPT_CALLBACK";// 课程PPT文件上传回调(返回附件key)
	public static final String AGC_ASSISTANT_PPT_CALLBACK = "AGC_ASSISTANT_PPT_CALLBACK";// 助销工具PPT文件上传回调(返回附件key)
	public static final String AGC_AUDIO_COMPOSE = "AGC_AUDIO_COMPOSE";// 语音合成队列
	public static final String QUOTED_ORDERED = "QUOTED_ORDERED";// 设计案例订单生成销售单
	public static final String INDUSTRY_INFO = "INDUSTRY_INFO";// 行业修改
	public static final String BRAND_INFO = "BRAND_INFO";// 修改品牌信息
	public static final String FAC_UPDATE_STORE_INFO = "FAC_UPDATE_STORE_INFO";// 修改门店信息
	public static final String FAC_UPDATE_AGENCY_NAME = "FAC_UPDATE_AGENCY_NAME";// 修改经销商名称
	public static final String AGC_UPDATE_STORE_INFO = "AGC_UPDATE_STORE_INFO";// 修改门店信息
	public static final String AGC_UPDATE_AGENCY_NAME = "AGC_UPDATE_AGENCY_NAME";// 修改经销商名称
	public static final String AGC_NEW_HOUSES = "AGC_NEW_HOUSES";// 新增楼盘
	public static final String AGC_REMAIN_INFO = "AGC_REMAIN_INFO";// 添加待办
	public static final String CUSTOMER_TRACE_LOG = "CUSTOMER_TRACE_LOG";// 添加跟进记录
	public static final String CLEAR_CUSTOMER_CONTRACT_NUM = "CLEAR_CUSTOMER_CONTRACT_NUM";// 清空客户合同
	public static final String COMPETITIVE_PRODUCT_FEEDBACK = "COMPETITIVE_PRODUCT_FEEDBACK";// 竞品反馈
	public static final String AGC_ORDER_PRODUCT = "AGC_ORDER_PRODUCT";// 保存定制产品

	public static final Map<String, Boolean> queueMap = new HashMap<String, Boolean>();
	static {
		queueMap.put("AGC_NEW_ADDRESS", false);
		queueMap.put("AGC_NEW_FACTORY", false);
		queueMap.put("ADDRESS_ID", false);
		queueMap.put("AGC_NEW_HOUSE_TYPE", false);
		queueMap.put("AGC_HANDLE_INVENTORY", false);
		queueMap.put("AGC_HANDLE_CUSTOMER_STATUS", false);
		queueMap.put("AGC_SEND_UPLOAD", false);
		queueMap.put("AGC_UPLOAD_CALLBACK", false);
		queueMap.put("FAC_NEW_AGENCY", false);
		queueMap.put("FAC_RELEVANCE_AGENCY_ID", false);
		queueMap.put("HAS_ORDER_CUSTOMER", false);
		queueMap.put("AGC_USER_ADMIN", false);
		queueMap.put("AGC_USER_ADMIN_CODE", false);
		queueMap.put("FAC_NEW_LOGAUDIT", false);
		queueMap.put("FAC_NEW_STAGE", false);
		queueMap.put("FAC_NEW_TEMPLATE", false);
		queueMap.put("FAC_NEW_THEME", false);
		queueMap.put("AGC_NEW_ACTIVITYLOG", false);
		queueMap.put("AGC_AGENCY_STATUS", false);
		queueMap.put("AGC_SAVE_ATTACHMENT", false);
		queueMap.put("AGC_NEW_BRAND", false);
		queueMap.put("AGC_DEL_BRAND", false);
		queueMap.put("AGC_NEW_PROPERTYFORBIDDEN", false);
		queueMap.put("FAC_AGENCY_STORE_QTY", false);
		queueMap.put("AGC_SEND_PPT_UPLOAD", false);
		queueMap.put("AGC_COURSE_PPT_CALLBACK", false);
		queueMap.put("AGC_ASSISTANT_PPT_CALLBACK", false);
		queueMap.put("AGC_AUDIO_COMPOSE", false);
		queueMap.put("QUOTED_ORDERED", false);
		queueMap.put("INDUSTRY_INFO", false);
		queueMap.put("BRAND_INFO", false);
		queueMap.put("FAC_UPDATE_STORE_INFO", false);
		queueMap.put("FAC_UPDATE_AGENCY_NAME", false);
		queueMap.put("AGC_UPDATE_STORE_INFO", false);
		queueMap.put("AGC_UPDATE_AGENCY_NAME", false);
		queueMap.put("AGC_NEW_HOUSES", false);
		queueMap.put("AGC_REMAIN_INFO", false);
		queueMap.put("CUSTOMER_TRACE_LOG", true);
		queueMap.put("CLEAR_CUSTOMER_CONTRACT_NUM", false);
		queueMap.put("COMPETITIVE_PRODUCT_FEEDBACK", false);
		queueMap.put("AGC_ORDER_PRODUCT", false);
	}

	public static final Map<String, String> applicationMap = new HashMap<String, String>();
	static {
		applicationMap.put("AGC_NEW_ADDRESS", "crm-address");
		applicationMap.put("AGC_NEW_FACTORY", "sysconfig");
		applicationMap.put("ADDRESS_ID", "sysconfig");
		applicationMap.put("AGC_NEW_HOUSE_TYPE", "crm-basic");
		applicationMap.put("AGC_HANDLE_INVENTORY", "crm-basic");
		applicationMap.put("AGC_HANDLE_CUSTOMER_STATUS", "crm-customer");
		applicationMap.put("AGC_SEND_UPLOAD", "crm-assistant");
		applicationMap.put("AGC_UPLOAD_CALLBACK", "crm-college");
		applicationMap.put("FAC_NEW_AGENCY", "sysconfig");
		applicationMap.put("FAC_RELEVANCE_AGENCY_ID", "");
		applicationMap.put("HAS_ORDER_CUSTOMER", "crm-customer");
		applicationMap.put("AGC_USER_ADMIN", "");
		applicationMap.put("AGC_USER_ADMIN_CODE", "");
		applicationMap.put("FAC_NEW_LOGAUDIT", "crm-baiqiang");
		applicationMap.put("FAC_NEW_STAGE", "crm-baiqiang");
		applicationMap.put("FAC_NEW_TEMPLATE", "crm-baiqiang");
		applicationMap.put("FAC_NEW_THEME", "crm-baiqiang");
		applicationMap.put("AGC_NEW_ACTIVITYLOG", "");
		applicationMap.put("AGC_AGENCY_STATUS", "sysconfig");
		applicationMap.put("AGC_SAVE_ATTACHMENT", "crm-assistant");
		applicationMap.put("AGC_NEW_BRAND", "crm-basic");
		applicationMap.put("AGC_DEL_BRAND", "crm-basic");
		applicationMap.put("AGC_NEW_PROPERTYFORBIDDEN", "crm-basic");
		applicationMap.put("FAC_AGENCY_STORE_QTY", "");
		applicationMap.put("AGC_SEND_PPT_UPLOAD", "crm-assistant");
		applicationMap.put("AGC_COURSE_PPT_CALLBACK", "crm-college");
		applicationMap.put("AGC_ASSISTANT_PPT_CALLBACK", "crm-assistant");
		applicationMap.put("AGC_AUDIO_COMPOSE", "crm-assistant");
		applicationMap.put("QUOTED_ORDERED", "crm-design");
		applicationMap.put("INDUSTRY_INFO", "sysconfig");
		applicationMap.put("BRAND_INFO", "sysconfig");
		applicationMap.put("FAC_UPDATE_STORE_INFO", "");
		applicationMap.put("FAC_UPDATE_AGENCY_NAME", "");
		applicationMap.put("AGC_UPDATE_STORE_INFO", "crm-baiqiang");
		applicationMap.put("AGC_UPDATE_AGENCY_NAME", "crm-baiqiang");
		applicationMap.put("AGC_NEW_HOUSES", "channel");
		applicationMap.put("AGC_REMAIN_INFO", "crm-assistant");
		applicationMap.put("CUSTOMER_TRACE_LOG", "crm-customer");
		applicationMap.put("CLEAR_CUSTOMER_CONTRACT_NUM", "crm-customer");
		applicationMap.put("COMPETITIVE_PRODUCT_FEEDBACK", "");
		applicationMap.put("AGC_ORDER_PRODUCT", "crm-basic");
	}
}
