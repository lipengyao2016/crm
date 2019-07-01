package com.crm.administration.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "verify.pic")
public class VerifyPicProperty {
	private Integer height;
	private Integer width;
	private Integer letterQty;

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getLetterQty() {
		return letterQty;
	}

	public void setLetterQty(Integer letterQty) {
		this.letterQty = letterQty;
	}

}
