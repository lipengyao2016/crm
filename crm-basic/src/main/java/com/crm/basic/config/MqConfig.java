package com.crm.basic.config;

import com.crm.utils.MqQueueUtil;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {
	
	/**
	 * 新增/修改动态字段MQ列队
	 * 
	 * @return
	 */
	@ConditionalOnMissingBean
	@Bean
	public Queue saveActivityLogQueue() {
		return new Queue(MqQueueUtil.AGC_NEW_PROPERTYFORBIDDEN);
	}
}