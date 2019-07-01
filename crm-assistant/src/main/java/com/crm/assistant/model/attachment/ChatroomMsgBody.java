package com.crm.assistant.model.attachment;

import java.io.Serializable;

public class ChatroomMsgBody implements Serializable {

	private static final long serialVersionUID = 2980191034670286165L;

	private String msg;
	private Long dur;
	private String md5;
	private String url;
	private String ext;
	private Integer size;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getDur() {
		return dur;
	}

	public void setDur(Long dur) {
		this.dur = dur;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
}
