package com.crm.assistant.controller;

import static com.crm.utils.StringUtil.isEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.crm.annotation.NoPermissionAuth;
import com.crm.assistant.service.AttachmentService;
import com.crm.common.ECode;
import com.crm.common.ELoginChannel;
import com.crm.common.MessageException;
import com.crm.common.RestfulResponse;
import com.crm.core.RequestHelper;
import com.crm.dto.AttachmentDto;
import com.crm.utils.StringUtil;
import com.crm.vo.AttachmentVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(tags = "附件管理")
@RequestMapping("/attachment")
public class AttachmentController {

	@Autowired
    AttachmentService attachmentService;
	public static final Logger log = LoggerFactory.getLogger(AttachmentController.class);

	/**
	 * 根据附件key集合获取附件集合
	 * 
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "列表查询附件信息")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "attachmentKeyArray", dataType = "String", required = true, value = "附件key集合", defaultValue = "507889087647-5f23-2970-8199-18ea030b") })
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 1000, message = "查询成功") })
	@GetMapping("/listDatas")
	public RestfulResponse<Map<String, List<AttachmentVo>>> listDatas(HttpServletRequest request,
																	  @RequestParam("attachmentKeyArray") String[] attachmentKeyArray) throws Exception {
		RestfulResponse<Map<String, List<AttachmentVo>>> result = new RestfulResponse<Map<String, List<AttachmentVo>>>();
		Map<String, List<AttachmentVo>> dataInfo = new HashMap<String, List<AttachmentVo>>();
		try {
			boolean isFromPC = ELoginChannel.PC.name().equals(RequestHelper.getFromBy(request)) ? true : false;
			dataInfo = attachmentService.getDataLists(attachmentKeyArray, isFromPC);
			result.setData(dataInfo);
		} catch (Exception e) {
			log.error("查询附件信息出错{}", e.getMessage());
			result.setCode(ECode.ADD_ERROR.getCode());
			result.setMessage("查询附件信息失败！");
		}
		if (dataInfo == null || dataInfo.size() == 0) {
			result.setCode(ECode.NO_DATA_RESULT.getCode());
			result.setMessage("未查询到附件信息！");
		}
		return result;
	}

	/**
	 * 根据附件key获取附件集合
	 * 
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "查询附件信息")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "attachmentKey", dataType = "String", required = true, value = "附件编码", defaultValue = "1") })
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 1000, message = "查询成功") })
	@GetMapping("/listData")
	public RestfulResponse<List<AttachmentVo>> listData(HttpServletRequest request,
			@RequestParam("attachmentKey") String attachmentKey) throws Exception {
		RestfulResponse<List<AttachmentVo>> result = new RestfulResponse<List<AttachmentVo>>();
		List<AttachmentVo> dataInfo = new ArrayList<AttachmentVo>();
		try {
			boolean isFromPC = ELoginChannel.PC.name().equals(RequestHelper.getFromBy(request)) ? true : false;
			dataInfo = attachmentService.getDataList(attachmentKey, isFromPC);
			result.setData(dataInfo);
		} catch (Exception e) {
			log.error("查询附件信息出错{}", e.getMessage());
			result.setCode(ECode.ADD_ERROR.getCode());
			result.setMessage("查询附件信息失败！");
		}
		if (dataInfo == null || dataInfo.size() == 0) {
			result.setCode(ECode.NO_DATA_RESULT.getCode());
			result.setMessage("未查询到附件信息！");
		}
		return result;
	}

	/**
	 * 修改保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "新增附件")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 1000, message = "新增成功") })
	@PostMapping("/saveData")
	public RestfulResponse<String> saveData(
			@ApiParam(name = "信息字段", required = true) @RequestBody List<AttachmentDto> attachmentDtolist)
			throws Exception {
		RestfulResponse<String> result = new RestfulResponse<String>();
		List<AttachmentDto> dataInfo = new ArrayList<AttachmentDto>();
		try {
			dataInfo = attachmentService.saveDataInfo(attachmentDtolist);
			String attachmentKey = dataInfo.get(0).getAttachmentKey();
			result.setData(attachmentKey);
		} catch (Exception e) {
			log.error("新增附件出错{}", e.getMessage());
			result.setCode(ECode.ADD_ERROR.getCode());
			result.setMessage("新增附件失败！");
		}
		if (dataInfo == null || dataInfo.size() == 0) {
			result.setCode(ECode.ADD_ERROR.getCode());
			result.setMessage("新增失败！");
		}
		return result;
	}

	/**
	 * 根据附件编码删除对象
	 * 
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "删除附件")
	@ApiImplicitParam(paramType = "query", name = "attachmentKey", dataType = "String", value = "家装公司编码", required = true, defaultValue = "")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 1000, message = "删除成功") })
	@DeleteMapping("/deleteData")
	public RestfulResponse<Integer> deleteData(@RequestParam("attachmentKey") String attachmentKey)
			throws Exception {
		RestfulResponse<Integer> result = new RestfulResponse<Integer>();
		if (isEmpty(attachmentKey)) {
			result.setCode(ECode.ADD_ERROR.getCode());
			result.setMessage("删除失败：编码不能为空！");
		}
		try {
			Integer updateStatusCount = attachmentService.deleteByAttachmentKey(attachmentKey);
			result.setData(updateStatusCount);
		} catch (Exception e) {
			log.error("删除附件出错{}", e.getMessage());
			result.setCode(ECode.ADD_ERROR.getCode());
			result.setMessage("删除失败！");
		}
		return result;
	}
	
	/**
	 * 处理阿里云附件私有化授权字符串
	 * 
	 * @return
	 * @throws Exception
	 */
	@NoPermissionAuth
	@ApiOperation(value = "处理阿里云附件私有化授权字符串")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 1000, message = "删除成功") })
	@PostMapping("/doHandleFileUrlForAliyun")
	public RestfulResponse<String> doHandleFileUrlForAliyun(@RequestParam("content")String content,
			@RequestParam("isFromPC")boolean isFromPC)
			throws Exception {
		RestfulResponse<String> result = new RestfulResponse<String>();
		try {
			String data = attachmentService.doHandleFileUrlForAliyun(content,isFromPC);
			if(StringUtil.isEmpty(data)) {
				log.info("处理授权字符串失败:"+"返回数据为空");
			}
			result.setData(data);
		} catch (MessageException e) {
			log.error("处理授权字符串失败:"+e.getMessage(),e);
			result.setMessage("处理授权字符串失败" + e.getMessage());
		} catch (Exception e) {
			log.error("处理授权字符串失败:"+e.getMessage(),e);
		}
		return result;
	}
	
	/**
	 * 处理阿里云附件私有化授权字符串
	 * 
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "处理阿里云附件私有化授权字符串")
	@ApiImplicitParam(paramType = "query", name = "attachmentKey", dataType = "String", value = "家装公司编码", required = true, defaultValue = "")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
		@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 1000, message = "删除成功") })
	@PostMapping("/doHandleFileUrlForAliyunByPC")
	public RestfulResponse<String[]> doHandleFileUrlForAliyunByPC(@RequestParam("content")String[] content)
					throws Exception {
		RestfulResponse<String[]> result = new RestfulResponse<String[]>();
		try {
			String[] data = attachmentService.doHandleFileUrlForAliyunByPC(content);
			if(data.length==0) {
				log.info("处理授权字符串失败:"+"返回数据为空");
			}
			result.setData(data);
		} catch (MessageException e) {
			log.error("处理授权字符串失败:"+e.getMessage(),e);
		} catch (Exception e) {
			log.error("处理授权字符串失败:"+e.getMessage(),e);
		}
		return result;
	}
}
