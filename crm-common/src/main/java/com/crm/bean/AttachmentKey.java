package com.crm.bean;


import com.crm.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel("附件信息")
public class AttachmentKey  extends BaseDto {
	@ApiModelProperty(value = "附件id",example="9f0d4e8a-4f98-49af-8952-95becbe17cc5")
	private String attachmentKey;
	@ApiModelProperty(value = "排序",example="1")
    private Integer seqNo;

	public String getAttachmentKey() {
		return attachmentKey;
	}

	public void setAttachmentKey(String attachmentKey) {
		this.attachmentKey = attachmentKey;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}
 
}