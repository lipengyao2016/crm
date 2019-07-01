package com.crm.assistant.controller;

import javax.servlet.http.HttpServletRequest;

import com.crm.assistant.model.attachment.VersionInfo;
import com.crm.assistant.service.AttachmentService;
import com.crm.assistant.util.FileUtil;
import com.crm.common.RestfulResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
@Api(tags = "文件下载(安卓)")
@RestController
@RequestMapping("/app")
public class FileDownLoadController {
	private final static String downLoadUrl="http://yanruicrm.oss-cn-qingdao.aliyuncs.com/app/crm2.0.apk";
	@Autowired
    AttachmentService attachmentService;
    
	@Autowired
    FileUtil fileUtil;
	public static final Logger log = LoggerFactory.getLogger(FileUploadController.class);
	@GetMapping("/version")
	public RestfulResponse<VersionInfo> upload(HttpServletRequest request){
		VersionInfo vo = new VersionInfo();
		RestfulResponse<VersionInfo> result=new RestfulResponse<VersionInfo>(vo);
		try {
			vo = attachmentService.getVersion();
			String downUrl=downLoadUrl.replace(".apk","_"+vo.getVersion()+".apk");
			vo.setDownUrl(downUrl);
			result.setData(vo);
		}catch(Exception e){
			log.error("下载安装包错误："+e);
			e.printStackTrace();
		}
		return result;
	}
}
