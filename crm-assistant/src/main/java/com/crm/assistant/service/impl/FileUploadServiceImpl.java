package com.crm.assistant.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.crm.assistant.util.FileUtil;
import com.crm.common.ECode;
import com.crm.common.MessageException;
import com.crm.dto.AttachmentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.crm.assistant.service.AttachmentService;
import com.crm.assistant.service.FileUploadService;

@Service
public class FileUploadServiceImpl implements FileUploadService {
	@Autowired
    FileUtil fileUtil;
	@Autowired
	AttachmentService attachmentService;

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(FileUploadServiceImpl.class);

	@Override
	public String saveFileList(List<String> fileUrlList) {

		List<AttachmentDto> attachmentlist = new ArrayList<AttachmentDto>();
		String attachmentKey = "";
		try {
			for (String fileUrl : fileUrlList) {
				File targetFile = new File(fileUrl);
				if (!targetFile.exists()) {
					log.debug("上传附件失败,url:[" + fileUrl + "]文件不存在");
					continue;
				}
				// 由于是直接接收的文件绝对路径生成的文件，所以可以避免MultipartFile文件被占用的情况
				/*
				 * if (!targetFile.exists()) { targetFile.getParentFile().mkdirs();
				 * targetFile.createNewFile(); }else{ targetFile.delete();
				 * targetFile.getParentFile().mkdirs(); targetFile.createNewFile(); }
				 */
				// 保存到阿里云
				String url = "";
				url = fileUtil.aliUpload(targetFile);
				String uploadfilename = targetFile.getName();
				String suffix = uploadfilename.substring(uploadfilename.lastIndexOf('.') + 1).toLowerCase();
				AttachmentDto attachment = new AttachmentDto();
				attachment.setFileName(uploadfilename);
				attachment.setFileSize(FileUtil.formatFileLength(targetFile.length()));
				attachment.setFileUrl(url);
				// 视频文件获取时长和略缩图
				if (suffix.equals("mp4")) {
					long duration = FileUtil.getMediaDuration(targetFile);
					File thumb = FileUtil.getVideoThumb(targetFile);
					// 略缩图同样上传到阿里云
					String thumburl = fileUtil.aliUpload(thumb);
					attachment.setDuration(FileUtil.formatVideoDuration(duration));
					attachment.setThumb(thumburl);
					FileUtil.deleteFile(thumb);
				}
				attachmentlist.add(attachment);
				// 删除临时文件
				System.gc();
				targetFile.delete();
			}
			List<AttachmentDto> saveDataInfo = attachmentService.saveDataInfo(attachmentlist);
			attachmentKey = saveDataInfo.get(0).getAttachmentKey();
			log.info(JSONObject.toJSONString(attachmentlist));

			return attachmentKey;
		} catch (MessageException e) {
			log.error("上传文件出错" + e);
			throw new MessageException("上传文件出错" + e.getMessage(), ECode.CICD_BUILD_DOWNLOAD_PACKAGES_FAILED);
		} catch (Throwable e) {
			log.error("上传文件出错" + e);
			e.printStackTrace();
			throw new MessageException("上传文件出错" + e.getMessage(), ECode.CICD_BUILD_DOWNLOAD_PACKAGES_FAILED);
		}
	}
}
