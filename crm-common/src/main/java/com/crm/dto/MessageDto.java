package com.crm.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("MQ消息类")
public class MessageDto implements Serializable {
	@ApiModelProperty(value = "业务数据主键id", required = true)
	private String messageId;
	@ApiModelProperty(value = "MQ队列名", required = true)
	private String queueName;
	@ApiModelProperty(value = "MQ消息內容", required = true)
	private String content;
	@ApiModelProperty(value = "MQ消息生产者业务校验Url", required = true)
	private String producerUrl;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getProducerUrl() {
		return producerUrl;
	}

	public void setProducerUrl(String producerUrl) {
		this.producerUrl = producerUrl;
	}

}
