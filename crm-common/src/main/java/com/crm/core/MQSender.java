package com.crm.core;

import java.util.HashMap;
import java.util.Set;

import com.crm.common.EMQStatus;
import com.crm.dto.MessageDto;
import com.crm.utils.HttpClientUtil;
import com.crm.utils.MQMessageUtil;
import com.crm.utils.MqQueueUtil;
import com.crm.common.EMQStatus;
import com.crm.utils.HttpClientUtil;
import com.crm.utils.MQMessageUtil;
import com.crm.utils.MqQueueUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

@Component
public class MQSender {
	private static final Logger logger = LoggerFactory.getLogger(MQSender.class);

	@Value(value = "${spring.application.name}")
	private String applicationName;

	@Autowired
	private RabbitTemplate amqpTemplate;

	@Autowired
	private MQMessageUtil messageUtil;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public void sendMessage(MessageDto dto) {
		Message message = messageUtil.getMessage(dto.getMessageId());
		messageUtil.setSendStatus(dto.getMessageId(), EMQStatus.SENDED.name(), true);
		logger.info("发送消息：{}", message);
		amqpTemplate.convertAndSend(dto.getQueueName(), message, new CorrelationData(dto.getMessageId()));
	}

	/**
	 * 消息重发或删除
	 */
	@Scheduled(fixedRate = 1000 * 1)
	public void checkQueueMessage() {
		Set<String> keys = redisTemplate.keys("mq." + applicationName + ".*");
		if (keys != null && keys.size() > 0) {
			Long nowTime = System.currentTimeMillis();
			for (String id : keys) {
				int pushTimes = messageUtil.getPushTimes(id);
				if (pushTimes >= 5) {
					continue;
				}

				String sendStatus = messageUtil.getSendStatus(id);
				Message message = messageUtil.getMessage(id);
				MessageDto dto = JSONObject.parseObject(new String(message.getBody()), MessageDto.class);

				// 消息状态为待发送,校验生产者是否已成功处理相关业务数据
				if (EMQStatus.WAIT_SEND.name().equals(sendStatus)) {
					String result = "N";
					try {
						String url = "http://127.0.0.1:9090" + dto.getProducerUrl() + "/" + dto.getMessageId();
						result = HttpClientUtil.doGet(url, new HashMap<String,Object>(), false);
						logger.info("请求:" + url + "校验结果:" + result);
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("校验生产者队列{}业务错误：{}", dto.getQueueName(), e.getMessage());
						continue;
					}

					// 生产者未成功处理相关业务数据，将消息从redis删除
					if (!"Y".equals(result)) {
						Long addTime = messageUtil.getAddTime(dto.getMessageId());
						// 为避免消息刚保存还未发送(生产者还在提交事务)就被定时器抓取到，加此限制
						if ((nowTime - addTime) >= 5 * 1000) {
							redisTemplate.delete(id);

							// 消息未被消费,将全局序列号减1
							if (MqQueueUtil.queueMap.get(dto.getQueueName())) {
								messageUtil.decreaseGlobalSequence(dto);
							}
						}
					} else {
						// 生产者已成功处理业务，消息状态未改变，redis故障，发送消息之前修改发送状态失败，重新发送
						messageUtil.setErrorMsg(dto.getMessageId(), "消息状态未改变，redis故障，发送消息之前修改发送状态失败，重新发送");
						sendMessage(dto);
					}

					/**
					 * 消息状态停留在已发送时存在以下几种可能： 1、MQ故障，未发送到MQ 2、消息发送到exchange，ack时redis故障，修改发送状态失败
					 * 3、消息发送到exchange是，nack时redis故障，修改发送状态失败。 4、ack或nack超时。
					 * 5、消息发送到队列失败，redis故障，修改发送状态失败。
					 * 以上情况都不会触发MQ重新发送消息，但是有可能消息已经到达队列或正在排队等待进入队列。是否到达无法判断
					 */
				} else if (EMQStatus.SENDED.name().equals(sendStatus)) {
					int pullTimes = messageUtil.getPullTimes(id);
					String consumerStatus = messageUtil.getConsumerStatus(id);

					// 已被被监听，则不再发送，是否删除由消费端决定
					if (pullTimes > 0) {
						continue;
					}

					// 如果消费失败，则不再发送，是否删除由消费端决定
					if (EMQStatus.FAILURE.name().equals(consumerStatus)) {
						continue;
					}

					Long sendTime = messageUtil.getSendTime(dto.getMessageId());
					if ((nowTime - sendTime) >= 5 * 1000) {
						sendMessage(dto);
					}
				} else if (EMQStatus.CONFIRMED.name().equals(sendStatus)) {
					String consumerStatus = messageUtil.getConsumerStatus(id);
					if (EMQStatus.SUCCESS.name().equals(consumerStatus)) {
						messageUtil.deleteMessage(id);
					}
				}
			}
		}
	}
}
