package com.crm.core;

import java.io.IOException;

import com.crm.common.EMQStatus;
import com.crm.dto.MessageDto;
import com.crm.utils.MQMessageUtil;
import com.crm.utils.MqQueueUtil;
import com.crm.common.EMQStatus;
import com.crm.utils.MQMessageUtil;
import com.crm.utils.MqQueueUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;

@Component
public class MQReceiver {
	private static final Logger log = LoggerFactory.getLogger(MQReceiver.class);

	@Autowired
	private MQMessageUtil messageUtil;

	public void receiverMessage(Channel channel, Message message, MQConsumerService service) throws IOException {
		// 监听消息中不包含CorrelationIdString的值
		MessageDto dto = JSONObject.parseObject(new String(message.getBody()), MessageDto.class);

		// 设置消息已发送到队列
		messageUtil.addPullTimes(dto.getMessageId());
		try {
			// 消息幂等性控制，判断消费顺序
			if (MqQueueUtil.queueMap.get(dto.getQueueName())) {
				Long sequence = messageUtil.getMsgSequence(dto.getMessageId());
				Long consume = messageUtil.getGlobalConsume(dto);
				long sub = sequence - consume;
				log.info("差:" + sub + "," + (sub == 1));
				if (sub == 1) {
					handleMessage(channel, dto, message, service);
					messageUtil.setGlobalConsume(dto, sequence);
				} else {
					try {
						Thread.sleep(5000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					// 重新发送
					messageUtil.setSendStatus(dto.getMessageId(), EMQStatus.WAIT_SEND.name(), true);
					messageUtil.setErrorMsg(dto.getMessageId(), "当前消息之前还有未成功消费的消息！");
					channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
				}
			} else {
				handleMessage(channel, dto, message, service);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("消费消息{}失败:{}", dto.getMessageId(), e.getMessage());

			redeliverMessage(channel, message, e.getMessage());
		}
	}

	public void redeliverMessage(Channel channel, Message message, String errorMsg) throws IOException {
		boolean redelivered = true;
		try {
			MessageDto dto = JSONObject.parseObject(new String(message.getBody()), MessageDto.class);
			messageUtil.addDeliveredTimes(dto.getMessageId());
			int count = messageUtil.getDeliveredTimes(dto.getMessageId());
			if (count >= 6) {
				// 三次重新加入队列消费后仍失败，待人工干预
				redelivered = false;
				messageUtil.setErrorMsg(dto.getMessageId(), "消费消息失败：" + errorMsg);
			}

			if (redelivered) {
				messageUtil.setConsumerStatus(dto.getMessageId(), EMQStatus.FAILURE.name());
			}

			// 每间隔5秒再重新加入队列
			Thread.sleep(5000);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, redelivered);
		// channel.basicReject(message.getMessageProperties().getDeliveryTag(),
		// !message.getMessageProperties().getRedelivered());
	}

	private void handleMessage(Channel channel, MessageDto dto, Message message, MQConsumerService service)
			throws IOException {
		// 检验消息是否已被成功消费
		boolean flag = service.checkMessage(dto);

		// 消费者未成功处理相关业务数据，继续消费
		if (!flag) {
			// 处理消费者业务逻辑
			service.handleMessage(dto);
		}

		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

		// 消费成功
		messageUtil.setConsumerStatus(dto.getMessageId(), EMQStatus.SUCCESS.name());
	}
}
