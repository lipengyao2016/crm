package com.crm.core;

import com.crm.dto.MessageDto;

public interface MQConsumerService {
	/**
	 * 消费者处理MQ消息业务逻辑
	 * 
	 * @param message
	 */
	void handleMessage(MessageDto message);

	/**
	 * 检验消息是否已被成功消费
	 * 
	 * @param message
	 * @return
	 */
	boolean checkMessage(MessageDto message);
}
