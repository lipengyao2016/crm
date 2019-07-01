package com.crm.common;

public enum EMQStatus {
	/**
	 * 待发送
	 */
	WAIT_SEND,

	/**
	 * 已发送
	 */
	SENDED,

	/**
	 * 消息发送到达exchange
	 */
	CONFIRMED,

	/**
	 * 消息发送失败
	 */
	NOT_CONFIRMED,

	/**
	 * 消费成功
	 */
	SUCCESS,

	/**
	 * 消费失败
	 */
	FAILURE
}
