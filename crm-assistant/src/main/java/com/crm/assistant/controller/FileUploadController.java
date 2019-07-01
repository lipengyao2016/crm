package com.crm.assistant.controller;


import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.crm.assistant.service.AttachmentService;
import com.crm.assistant.util.FileUtil;
import com.crm.common.MessageException;
import com.crm.common.RestfulResponse;
import com.crm.dto.AttachmentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.Api;

@Api(tags = "文件上传")
@RestController
@RequestMapping("/file")
public class FileUploadController {
	
	@Autowired
    AttachmentService attachmentService;
	// 显示声明CommonsMultipartResolver为mutipartResolver  
    @Bean(name = "multipartResolver")  
    public MultipartResolver multipartResolver()  
    {  
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();  
         resolver.setDefaultEncoding("UTF-8");  
        // resolver.setResolveLazily(true);// resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常  
        // resolver.setMaxInMemorySize(40960);  
        resolver.setMaxUploadSize(2000 * 1024 * 1024);// 上传文件大小 5M 5*1024*1024  
        return resolver;  
    }  
	@Autowired
    FileUtil fileUtil;
	public static final Logger log = LoggerFactory.getLogger(FileUploadController.class);
	@PostMapping("/upload")
	public RestfulResponse<List<AttachmentDto>> upload(HttpServletRequest request){
		List<AttachmentDto> attachmentlist=new ArrayList<AttachmentDto>();
		long currentTime=System.currentTimeMillis();
		log.error(MessageFormat.format("upload start--currentTimeNow: {0}",currentTime));
		try {
			String path = request.getSession().getServletContext().getRealPath("upload");
			path=path.replaceAll("\\\\", "/")+"/";
			//兼容格式读取，富文本编辑器、普通控件上传
			List<MultipartFile> files = new ArrayList<MultipartFile>();
			Map<String, MultipartFile> fileMap = ((MultipartHttpServletRequest) request).getFileMap();
			for (String key : fileMap.keySet()) {
				files.add(fileMap.get(key));
			}
			long currentTimeMillis = System.currentTimeMillis();
			log.error(MessageFormat.format("load file--currentTimeNow: {0},Time of use：{1}",currentTimeMillis,currentTimeMillis-currentTime));
			for(MultipartFile file:files){
					String uploadfilename=file.getOriginalFilename();
					String suffix=uploadfilename.substring(uploadfilename.lastIndexOf('.')+1).toLowerCase();
					String fileName=new Date().getTime()+"."+suffix;
					File targetFile = new File(path, fileName);
					if (!targetFile.exists()) {
						targetFile.getParentFile().mkdirs();
						targetFile.createNewFile();
					}else{
						targetFile.delete();
						targetFile.getParentFile().mkdirs();
						targetFile.createNewFile();
					}
					//保存到服务器
					file.transferTo(targetFile);
					
					long currentTimeMillis2 = System.currentTimeMillis();
					log.error(MessageFormat.format("save file to Server--currentTimeNow: {0},Time of use：{1}",currentTimeMillis,currentTimeMillis2-currentTimeMillis));
					
					//保存到阿里云
					String url="";
					url=fileUtil.aliUpload(targetFile);
					
					long currentTimeMillis3 = System.currentTimeMillis();
					log.error(MessageFormat.format("save file to Aliyun--currentTimeNow: {0},Time of use：{1}",currentTimeMillis,currentTimeMillis3-currentTimeMillis2));
					
					//保存到阿里云
					AttachmentDto attachment=new AttachmentDto();
					attachment.setFileName(uploadfilename);
					attachment.setFileSize(FileUtil.formatFileLength(targetFile.length()));
					attachment.setFileUrl(url);
					//视频文件获取时长和略缩图
					if(suffix.equals("mp4")){
						long duration= FileUtil.getMediaDuration(targetFile);
						File thumb= FileUtil.getVideoThumb(targetFile);
						//略缩图同样上传到阿里云
						String thumburl=fileUtil.aliUpload(thumb);
						attachment.setDuration(FileUtil.formatVideoDuration(duration));
						attachment.setThumb(thumburl);
						FileUtil.deleteFile(thumb);

						long currentTimeMillis4 = System.currentTimeMillis();
						log.error(MessageFormat.format("get thumb file and save to Aliyun--currentTimeNow: {0},Time of use：{1}",currentTimeMillis,currentTimeMillis4-currentTimeMillis3));
						
					}
					attachmentlist.add(attachment);
					//删除临时文件
					targetFile.delete();
					

					long currentTimeMillis4 = System.currentTimeMillis();
					log.error(MessageFormat.format("file upload OK--currentTimeNow: {0}, the fileName:{1},total Time of use：{2}",currentTimeMillis,uploadfilename,currentTimeMillis4-currentTimeMillis3));
					
			}
			System.gc();
			//输出list
			log.error(JSONObject.toJSONString(attachmentlist));
		}
		catch (Exception e) {
			log.error("上传文件出错"+e,e);
			return new RestfulResponse<List<AttachmentDto>>(attachmentlist);
		} catch (Throwable e) {
			log.error("上传文件出错"+e,e);
			return new RestfulResponse<List<AttachmentDto>>(attachmentlist);
		}
		long currentTimeMillis5 = System.currentTimeMillis();
		log.error(MessageFormat.format("file upload OK--currentTimeNow: {0}, the fileName:{1},total Time of use：{2}",currentTimeMillis5,currentTimeMillis5-currentTime));
		
		return new RestfulResponse<List<AttachmentDto>>(attachmentlist);
	}
	
	@PostMapping("/saveFileList")
	public RestfulResponse<String> saveFileList(@RequestBody List<String> fileUrlList){
		List<AttachmentDto> attachmentlist=new ArrayList<AttachmentDto>();
		String attachmentKey ="";
		try {
			for(String fileUrl:fileUrlList){
				
				File targetFile=new File(fileUrl);
				if (!targetFile.exists()) {
					log.debug("上传附件失败,url:["+fileUrl+"]文件不存在");
					continue;
				}
				//由于是直接接收的文件绝对路径生成的文件，所以可以避免MultipartFile文件被占用的情况
				/*if (!targetFile.exists()) {
						targetFile.getParentFile().mkdirs();
						targetFile.createNewFile();
					}else{
						targetFile.delete();
						targetFile.getParentFile().mkdirs();
						targetFile.createNewFile();
					}*/
					//保存到阿里云
					String url="";
					url=fileUtil.aliUpload(targetFile);
					String uploadfilename = targetFile.getName();
					String suffix=uploadfilename.substring(uploadfilename.lastIndexOf('.')+1).toLowerCase();
					AttachmentDto attachment=new AttachmentDto();
					attachment.setFileName(uploadfilename);
					attachment.setFileSize(FileUtil.formatFileLength(targetFile.length()));
					attachment.setFileUrl(url);
					//视频文件获取时长和略缩图
					if(suffix.equals("mp4")){
						long duration= FileUtil.getMediaDuration(targetFile);
						File thumb= FileUtil.getVideoThumb(targetFile);
						//略缩图同样上传到阿里云
						String thumburl=fileUtil.aliUpload(thumb);
						attachment.setDuration(FileUtil.formatVideoDuration(duration));
						attachment.setThumb(thumburl);
						FileUtil.deleteFile(thumb);
					}
					attachmentlist.add(attachment);
					//删除临时文件
					System.gc();
					targetFile.delete();
			}
			List<AttachmentDto> saveDataInfo = attachmentService.saveDataInfo(attachmentlist);
			attachmentKey = saveDataInfo.get(0).getAttachmentKey();
			//输出list
			log.error(JSONObject.toJSONString(attachmentlist));
		}catch (MessageException e) {
			log.error("上传文件出错"+e,e);
			return new RestfulResponse<String>(e.getMessage());
		}
		catch (Throwable e) {
			log.error("上传文件出错"+e,e);
			return new RestfulResponse<String>(e.getMessage());
		}
		return new RestfulResponse<String>(attachmentKey);
	}
	/*public static void main(String[] args) {
		try {
			String fileUrl="C:/Users/Administrator/Desktop/test2.txt";
			fileUrl=fileUrl.replace("/", "\\\\");
			System.err.println(fileUrl);
			File targetFile=new File(fileUrl);
			System.out.println(formatFileLength(targetFile.length()));
			if (!targetFile.exists()) {
				targetFile.getParentFile().mkdirs();
				targetFile.createNewFile();
			}else{
				targetFile.delete();
				targetFile.getParentFile().mkdirs();
				targetFile.createNewFile();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}*/
	 
}
