package com.crm.common;

@SuppressWarnings("serial")
public class MessageException extends RuntimeException {
	private String message;
	private ECode code;

	public MessageException() {
	}

	public MessageException(String message) {
		this.message = message;
	}

	public MessageException(ECode code) {
		this.code = code;
		this.message=code.getMessage();
	}

	public MessageException(String message, ECode code) {
		this.code = code;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ECode getCode() {
		return code;
	}

	public void setCode(ECode code) {
		this.code = code;
	}
}
