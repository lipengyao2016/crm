package com.crm.assistant.service;

import java.util.List;
import java.util.Map;

import com.crm.assistant.model.attachment.VersionInfo;
import com.crm.dto.AttachmentDto;
import com.crm.vo.AttachmentVo;

public interface AttachmentService{
	/**
	 * 实体保存方法
	 * @return
	 * @throws Exception
	 */
	public List<AttachmentDto> saveDataInfo(List<AttachmentDto> attachmentDto) throws Exception;
	/**
	 * 根据附件编码获取附件集合
	 * @return
	 * @throws Exception
	 */
	public List<AttachmentVo> getDataList(String attachmentKey, boolean isFromPC) throws Exception;
	/**
	 * 根据附件编码集合获取附件集合
	 * @return
	 * @throws Exception
	 */
	public Map<String,List<AttachmentVo>> getDataLists(String[] attachmentKeyArray,boolean isFromPC) throws Exception;
	
	/**
	 * 获取版本号
	 * @return
	 */
	public VersionInfo getVersion();
	
	/**
	 * 处理阿里云附件私有化授权字符串
	 * @param content
	 * @param isFromPC
	 * @return
	 * @throws Exception 
	 */
	public String doHandleFileUrlForAliyun(String content,boolean isFromPC) throws Exception;
	
	/**
	 * 处理阿里云附件私有化授权字符串
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	public String[] doHandleFileUrlForAliyunByPC(String[] content) throws Exception;

	public Integer deleteByAttachmentKey(String attachmentKey);
}
