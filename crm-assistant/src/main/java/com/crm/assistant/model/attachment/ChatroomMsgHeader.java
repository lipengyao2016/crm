package com.crm.assistant.model.attachment;

import java.io.Serializable;

public class ChatroomMsgHeader implements Serializable {

	private static final long serialVersionUID = 3399609498793833305L;

	private String msgid;
	private String from;
	private ChatroomMsgBody body;
	private Long sendtime;
	private int type;

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public ChatroomMsgBody getBody() {
		return body;
	}

	public void setBody(ChatroomMsgBody body) {
		this.body = body;
	}

	public Long getSendtime() {
		return sendtime;
	}

	public void setSendtime(Long sendtime) {
		this.sendtime = sendtime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
