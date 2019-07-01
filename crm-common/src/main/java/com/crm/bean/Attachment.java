package com.crm.bean;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class Attachment extends AttachmentKey{
	@ApiModelProperty(value = "文件名",example="TB2ANS8atifF1Jjy0FdXXX3dpXa_!!272849560.jpg")
	private String fileName;
	@ApiModelProperty(value = "文件路径",example="http://boyintest.oss-cn-shenzhen.aliyuncs.com/admin/1516267398742.jpg")
    private String fileUrl;
	@ApiModelProperty(value = "文件大小",example="11.5KB")
    private String fileSize;
	@ApiModelProperty(value = "视频时长",example="")
    private String duration;
	@ApiModelProperty(value = "视频缩略图",example="")
    private String thumb;
	@ApiModelProperty(value = "创建时间",example="")
    private Date createDt;
	@ApiModelProperty(value = "标题",example="测试图片")
    private String fileTitle;
	@ApiModelProperty(value = "描述",example="描述内容")
    private String remark;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public String getFileTitle() {
		return fileTitle;
	}

	public void setFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

  
}