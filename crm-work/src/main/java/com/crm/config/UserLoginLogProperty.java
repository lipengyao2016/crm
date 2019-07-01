package com.crm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@Component
@ConfigurationProperties(prefix = "aliyun")
public class UserLoginLogProperty {
	private String bucketName;
	private String accessKeyId;
	private String secretAccessKey;
	private String endpoint;
	@ApiModelProperty(value = "系统用户网易云信通前缀")
	private String wyPrefix;
	@ApiModelProperty(value = "微信用户网易云信通前缀")
	private String wxPrefix;

	public UserLoginLogProperty() {
	}
	
	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getSecretAccessKey() {
		return secretAccessKey;
	}

	public void setSecretAccessKey(String secretAccessKey) {
		this.secretAccessKey = secretAccessKey;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getWyPrefix() {
		return wyPrefix;
	}

	public void setWyPrefix(String wyPrefix) {
		this.wyPrefix = wyPrefix;
	}

	public String getWxPrefix() {
		return wxPrefix;
	}

	public void setWxPrefix(String wxPrefix) {
		this.wxPrefix = wxPrefix;
	}

}
