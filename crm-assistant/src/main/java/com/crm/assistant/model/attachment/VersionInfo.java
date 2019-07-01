package com.crm.assistant.model.attachment;

public class VersionInfo {
	private Integer id;
	private String version;
	private String downUrl;
	private String desc;
	private String isHintUpdate;
	private String isSompulsoryRenewal;
	
	public String getIsHintUpdate() {
		return isHintUpdate;
	}
	public void setIsHintUpdate(String isHintUpdate) {
		this.isHintUpdate = isHintUpdate;
	}
	public String getIsSompulsoryRenewal() {
		return isSompulsoryRenewal;
	}
	public void setIsSompulsoryRenewal(String isSompulsoryRenewal) {
		this.isSompulsoryRenewal = isSompulsoryRenewal;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	 
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDownUrl() {
		return downUrl;
	}
	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}

