package com.crm.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.crm.common.EMQStatus;
import com.crm.common.MessageException;
import com.crm.dto.MessageDto;
import com.crm.common.EMQStatus;
import com.crm.common.MessageException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class MQMessageUtil {
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public Message buildMessage(MessageDto dto) {
		String messageId = dto.getMessageId();
		messageId = "mq." + MqQueueUtil.applicationMap.get(dto.getQueueName()) + "." + dto.getQueueName() + "."
				+ messageId;

		Message message;
		if (!redisTemplate.hasKey(messageId)) {
			dto.setMessageId(messageId);
			message = putMessage(dto);
		} else {
			message = getMessage(messageId);
		}
		return message;
	}

	public Message setMessageDto(MessageDto dto) {
		String messageId = dto.getMessageId();
		Message message;
		if (redisTemplate.hasKey(messageId)) {
			message = putMessage(dto);
		} else {
			throw new MessageException("未获取到消息");
		}
		return message;
	}

	public void addPullTimes(String id) {
		if (redisTemplate.hasKey(id)) {
			redisTemplate.opsForHash().increment(id, "pullTimes", 1);
		}
	}

	public void addDeliveredTimes(String id) {
		if (redisTemplate.hasKey(id)) {
			redisTemplate.opsForHash().increment(id, "deliveredTimes", 1);
		}
	}

	public void setSendStatus(String id, String status, boolean ifPush) {
		if (redisTemplate.hasKey(id)) {
			if (ifPush) {
				redisTemplate.opsForHash().increment(id, "pushTimes", 1);
			}
			Map<String, String> messageMap = new HashMap<String, String>();
			messageMap.put("sendTime", System.currentTimeMillis() + "");
			messageMap.put("sendStatus", status);

			redisTemplate.opsForHash().putAll(id, messageMap);
		}
	}

	public void setConsumerStatus(String id, String status) {
		if (redisTemplate.hasKey(id)) {
			redisTemplate.opsForHash().put(id, "consumerStatus", status);
		}
	}

	public void setErrorMsg(String id, String msg) {
		if (redisTemplate.hasKey(id)) {
			redisTemplate.opsForHash().put(id, "errorMsg", msg);
		}
	}

	public Message getMessage(String id) {
		String str = DataConvertUtil.getString(redisTemplate.opsForHash().get(id, "message"));
		return JSONObject.parseObject(str, Message.class);
	}

	public String getSendStatus(String id) {
		return DataConvertUtil.getString(redisTemplate.opsForHash().get(id, "sendStatus"));
	}

	public String getConsumerStatus(String id) {
		return DataConvertUtil.getString(redisTemplate.opsForHash().get(id, "consumerStatus"));
	}

	public Long getSendTime(String id) {
		return DataConvertUtil.getLong(redisTemplate.opsForHash().get(id, "sendTime"));
	}
	
	public Long getAddTime(String id) {
		return DataConvertUtil.getLong(redisTemplate.opsForHash().get(id, "addTime"));
	}

	public Integer getPushTimes(String id) {
		return DataConvertUtil.getInt(redisTemplate.opsForHash().get(id, "pushTimes"));
	}

	public Integer getPullTimes(String id) {
		return DataConvertUtil.getInt(redisTemplate.opsForHash().get(id, "pullTimes"));
	}

	public Integer getDeliveredTimes(String id) {
		return DataConvertUtil.getInt(redisTemplate.opsForHash().get(id, "deliveredTimes"));
	}

	public String getErrorMsg(String id) {
        return  DataConvertUtil.getString(redisTemplate.opsForHash().get(id, "errorMsg"));
	}

	public Long getMsgSequence(String id) {
        return DataConvertUtil.getLong(redisTemplate.opsForHash().get(id, "sequence"));
	}

	public String getMessageContent(Message message) {
		MessageDto dto = JSONObject.parseObject(new String(message.getBody()), MessageDto.class);
		return dto.getContent();
	}

	public MessageDto getMessageDto(String id) {
		Message message = getMessage(id);
		return JSONObject.parseObject(new String(message.getBody()), MessageDto.class);
	}

	public void deleteMessage(String id) {
		redisTemplate.watch(id);
		redisTemplate.setEnableTransactionSupport(true);
		String sendStatus = this.getSendStatus(id);
		redisTemplate.multi();
		List<Object> rs ;

		if (EMQStatus.CONFIRMED.name().equals(sendStatus)) {
			redisTemplate.delete(id);
		}
		rs = redisTemplate.exec();
		if (rs == null || rs.size() == 0) {
			deleteMessage(id);
		}
	}

	public synchronized Long getGlobalConsume(MessageDto dto) {
		return DataConvertUtil.getLong(redisTemplate.opsForHash().get("seq." + dto.getQueueName(), "consumed"));
	}

	public synchronized void setGlobalConsume(MessageDto dto, Long sequence) {
		redisTemplate.opsForHash().put("seq." + dto.getQueueName(), "consumed", sequence + "");
	}

	public void decreaseGlobalSequence(MessageDto dto) {
		String key = "seq." + dto.getQueueName();
		redisTemplate.opsForHash().increment(key, "sequence", -1);
	}

	public Message putMessage(MessageDto dto) {
		Message message = MessageBuilder.withBody(JSONObject.toJSONString(dto).getBytes())
				.setContentType(MessageProperties.CONTENT_TYPE_JSON).setCorrelationIdString(dto.getMessageId())
				.setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();

		Map<String, String> messageMap = new HashMap<String, String>();
		messageMap.put("message", JSONObject.toJSONString(message));
		messageMap.put("pushTimes", "0");
		messageMap.put("pullTimes", "0");
		messageMap.put("deliveredTimes", "0");

		// 非幂等性队列需按顺序消费
		if (MqQueueUtil.queueMap.get(dto.getQueueName())) {
			if (!redisTemplate.opsForHash().hasKey(dto.getMessageId(), "sequence")) {
				// long globalSequence = DataConvertUtil
				// .getLong(redisTemplate.opsForHash().get("seq." + dto.getQueueName(),
				// "sequence"));
				// long globalConsume = getGlobalConsume(dto);
				// if (globalSequence > 1 && globalSequence - globalConsume > 1) {
				// throw new MessageException("当前业务还有未成功处理的数据,请稍后再试,或联系系统管理员！");
				// }
				messageMap.put("sequence", incrementGlobalSequence(dto) + "");
			}
		}
		messageMap.put("sendStatus", EMQStatus.WAIT_SEND.name());
		messageMap.put("addTime", System.currentTimeMillis() + "");
		redisTemplate.opsForHash().putAll(dto.getMessageId(), messageMap);

		return message;
	}

	/**
	 * 获取全局增量值，应对非幂等性(同时多条消息需按顺序消费)的消息
	 * 
	 * @param dto
	 */
	private synchronized Long incrementGlobalSequence(MessageDto dto) {
		String key = "seq." + dto.getQueueName();
		if (redisTemplate.hasKey(key)) {
			return redisTemplate.opsForHash().increment(key, "sequence", 1);
		} else {
			Map<String, String> seqMap = new HashMap<String, String>();
			seqMap.put("sequence", "1");
			seqMap.put("consumed", "0");
			redisTemplate.opsForHash().putAll(key, seqMap);
			return 1L;
		}
	}
}
