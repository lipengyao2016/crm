package com.crm.config;

import com.crm.utils.MqQueueUtil;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQQueueConfig {

	// @ConditionalOnMissingBean
	// @Bean
	// public Exchange deadLetterExchange() {
	// return ExchangeBuilder.directExchange("DL_EXCHANGE").durable(true).build();
	// }

	// @ConditionalOnMissingBean
	// @Bean
	// public Queue deadLetterQueue() {
	// Map<String, Object> args = new HashMap();
	// args.put("x-dead-letter-exchange", "DL_EXCHANGE");
	// args.put("x-dead-letter-routing-key", "DL_ROUTING");
	// return QueueBuilder.durable("DL_QUEUE").withArguments(args).build();
	// }
	//
	// @ConditionalOnMissingBean
	// @Bean
	// public Binding deadLetterBinding() {
	// return new Binding("DL_QUEUE", Binding.DestinationType.QUEUE, "DL_EXCHANGE",
	// "DL_KEY", null);
	//
	// }
	//
	// @ConditionalOnMissingBean
	// @Bean
	// public Binding customerBinding() {
	// return new Binding("QUEUE_CUSTOMER", Binding.DestinationType.QUEUE,
	// "DL_EXCHANGE", "DL_ROUTING", null);
	// }
	//
	// @ConditionalOnMissingBean
	// @Bean
	// public Binding producerBinding() {
	// return new Binding("QUEUE_PRODUCER", Binding.DestinationType.QUEUE,
	// "DL_EXCHANGE", "DL_ROUTING", null);
	// }

	/**
	 * 发送修改客户状态队列
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue agcHandleCustomerStatus() {return new Queue(MqQueueUtil.AGC_HANDLE_CUSTOMER_STATUS);
	}

	/**
	 * 发送有订单客户编码队列
	 *
	 *
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue hasOrderCustomer() {
		return new Queue(MqQueueUtil.HAS_ORDER_CUSTOMER);
	}

	/**
	 * 新增地址MQ队列
	 *
	 * @return Queue Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue agcNewAddress() {
		return new Queue(MqQueueUtil.AGC_NEW_ADDRESS);
	}

	/**
	 * 新增工厂MQ队列
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue agcNewFactory() {
		return new Queue(MqQueueUtil.AGC_NEW_FACTORY);
	}

	/**
	 * 地址编码MQ队列
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue addressId() {
		return new Queue(MqQueueUtil.ADDRESS_ID);
	}

	/**
	 * 新增房屋类型MQ队列
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue agcNewHouseType() {
		return new Queue(MqQueueUtil.AGC_NEW_HOUSE_TYPE);
	}

	/**
	 * 增减产品库存MQ队列
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue agcHandleInventory() {
		return new Queue(MqQueueUtil.AGC_HANDLE_INVENTORY);
	}

	/**
	 * 发送上传MQ队列
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue agcSendUpload() {
		return new Queue(MqQueueUtil.AGC_SEND_UPLOAD);
	}

	/**
	 * 文件上传回调(返回附件key)
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue agcUploadCallback() {
		return new Queue(MqQueueUtil.AGC_UPLOAD_CALLBACK);
	}

	/**
	 * 同步新增经销商端经销商信息
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue facNewAgency() {
		return new Queue(MqQueueUtil.FAC_NEW_AGENCY);
	}

	/**
	 * 同步新增经销商端的经销商编码到工厂端
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue facRelevanceAgencyId() {
		return new Queue(MqQueueUtil.FAC_RELEVANCE_AGENCY_ID);
	}

	/**
	 * 创建经销商管理员队列
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue agcUserAdmin() {
		return new Queue(MqQueueUtil.AGC_USER_ADMIN);
	}

	/**
	 * 经销商管理员创建回传队列
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue agcUserAdminCode() {
		return new Queue(MqQueueUtil.AGC_USER_ADMIN_CODE);
	}

	/**
	 * 同步经销商端日志审核信息
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue facNewLogaudit() {
		return new Queue(MqQueueUtil.FAC_NEW_LOGAUDIT);
	}

	/**
	 * 同步经销商端阶段活动信息
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue facNewStage() {
		return new Queue(MqQueueUtil.FAC_NEW_STAGE);
	}

	/**
	 * 同步经销商端竞赛日志信息
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue facNewTemplate() {
		return new Queue(MqQueueUtil.FAC_NEW_TEMPLATE);
	}

	/**
	 * 同步经销商端主题活动信息
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue facNewTheme() {
		return new Queue(MqQueueUtil.FAC_NEW_THEME);
	}

	/**
	 * 同步工厂端端写日志信息
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue agcNewActivitylog() {
		return new Queue(MqQueueUtil.AGC_NEW_ACTIVITYLOG);
	}

	/**
	 * 同步修改经销商状态消息
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue agcAgencyStatus() {
		return new Queue(MqQueueUtil.AGC_AGENCY_STATUS);
	}

	/**
	 * 经销商端队列中加入保存附件队列
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue agcSaveAttachment() {
		return new Queue(MqQueueUtil.AGC_SAVE_ATTACHMENT);
	}

	/**
	 * 经销商端队列中加入保存品牌品类队列
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue agcNewBrand() {
		return new Queue(MqQueueUtil.AGC_NEW_BRAND);
	}

	/**
	 * 经销商端队列中加入删除品牌品类队列
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue agcDelBrand() {
		return new Queue(MqQueueUtil.AGC_DEL_BRAND);
	}

	/**
	 * 经销商端列队中写入动态字段列队
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue agcNewPropertyforbidden() {
		return new Queue(MqQueueUtil.AGC_NEW_PROPERTYFORBIDDEN);
	}

	/**
	 * 更新经销商门店数量队列
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue facAgencyStoreQty() {
		return new Queue(MqQueueUtil.FAC_AGENCY_STORE_QTY);
	}

	/**
	 * 上传PPT附件
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue agcSendPptUpload() {
		return new Queue(MqQueueUtil.AGC_SEND_PPT_UPLOAD);
	}

	/**
	 * 课程PPT文件上传回调(返回附件key)
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue agcCoursePptCallback() {
		return new Queue(MqQueueUtil.AGC_COURSE_PPT_CALLBACK);
	}

	/**
	 * 助销工具PPT文件上传回调(返回附件key)
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue agcAssistantPptCallback() {
		return new Queue(MqQueueUtil.AGC_ASSISTANT_PPT_CALLBACK);
	}

	/**
	 * 语音合成队列
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue agcAudioCompose() {
		return new Queue(MqQueueUtil.AGC_AUDIO_COMPOSE);
	}

	/**
	 * 设计案例订单生成销售单
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue quotedOrdered() {
		return new Queue(MqQueueUtil.QUOTED_ORDERED);
	}

	/**
	 * 行业修改
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue industryInfo() {
		return new Queue(MqQueueUtil.INDUSTRY_INFO);
	}

	/**
	 * 修改品牌信息
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue brandInfo() {
		return new Queue(MqQueueUtil.BRAND_INFO);
	}

	/**
	 * 修改门店信息
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue facUpdateStoreInfo() {
		return new Queue(MqQueueUtil.FAC_UPDATE_STORE_INFO);
	}

	/**
	 * 修改经销商名称
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue facUpdateAgencyName() {
		return new Queue(MqQueueUtil.FAC_UPDATE_AGENCY_NAME);
	}

	/**
	 * 修改门店信息
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue agcUpdateStoreInfo() {
		return new Queue(MqQueueUtil.AGC_UPDATE_STORE_INFO);
	}

	/**
	 * 修改经销商名称
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue agcUpdateAgencyName() {
		return new Queue(MqQueueUtil.AGC_UPDATE_AGENCY_NAME);
	}

	/**
	 * 新增楼盘
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue agcNewHouses() {
		return new Queue(MqQueueUtil.AGC_NEW_HOUSES);
	}

	/**
	 * 添加待办
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue agcRemainInfo() {
		return new Queue(MqQueueUtil.AGC_REMAIN_INFO);
	}

	/**
	 * 添加跟进记录
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue customerTraceLog() {
		return new Queue(MqQueueUtil.CUSTOMER_TRACE_LOG);
	}

	/**
	 * 清空客户合同
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue clearCustomerContractNum() {
		return new Queue(MqQueueUtil.CLEAR_CUSTOMER_CONTRACT_NUM);
	}

	/**
	 * 竞品反馈
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue competitiveProductFeedback() {
		return new Queue(MqQueueUtil.COMPETITIVE_PRODUCT_FEEDBACK);
	}

	/**
	 * 保存定制产品
	 *
	 * @return Queue
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue agcOrderProduct() {
		return new Queue(MqQueueUtil.AGC_ORDER_PRODUCT);
	}
}