package com.crm.feign.assistant;

import java.util.List;
import java.util.Map;

import com.crm.bean.Attachment;
import com.crm.common.RestfulResponse;
import com.crm.vo.AttachmentVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@FeignClient(name = "crm-assistant", fallbackFactory = AssistantHystrixClientFallbackFactory.class)
public interface AssistantFeignClient {

	/**
	 * 根据附件key获取附件集合
	 * 
	 * @param attachmentKey
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listData",method = RequestMethod.GET)
	public RestfulResponse<List<AttachmentVo>> listData(@RequestParam("attachmentKey") String attachmentKey)
			throws Exception;

	/**
	 * 根据附件key获取附件集合
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listDatas",method = RequestMethod.GET)
	public RestfulResponse<Map<String, List<AttachmentVo>>> listDatas(
            @RequestParam("attachmentKeyArray") String[] attachmentKeyArray) throws Exception;

	/**
	 * 新增附件
	 * 
	 * @param attachmentDtolist
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveData",method = RequestMethod.POST)
	public RestfulResponse<String> saveData(
            @ApiParam(name = "信息字段", required = true) @RequestBody List<Attachment> attachmentDtolist) throws Exception;

	/**
	 * 根据附件key删除附件
	 * 
	 * @param attachmentKey
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteData",method = RequestMethod.DELETE)
	public RestfulResponse<Integer> deleteData(@RequestParam("attachmentKey") String attachmentKey) throws Exception;

	/**
	 * 保存附件集合
	 * 
	 * @return attachmentKey
	 * @throws Exception
	 */
	@RequestMapping(value = "/file/saveFileList",method = RequestMethod.POST)
	public RestfulResponse<String> saveFileList(@RequestBody List<String> fileUrlList);

	/**
	 * 通过feign实现文件上传
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/upload", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE }, consumes = MediaType.MULTIPART_FORM_DATA_VALUE,method = RequestMethod.POST)
	public String handleFileUpload(@RequestPart(value = "file") MultipartFile file);

	class MultipartSupportConfig {
		@Bean
		public Encoder feignFormEncoder() {
			return new SpringFormEncoder();
		}
	}
	
	/**
	 * 处理阿里云附件私有化授权字符串
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doHandleFileUrlForAliyun",method = RequestMethod.POST)
	public RestfulResponse<String> doHandleFileUrlForAliyun(@RequestParam("content") String content,
                                                            @RequestParam("isFromPC") boolean isFromPC);
}