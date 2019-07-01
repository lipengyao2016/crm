package com.crm.feign.assistant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crm.bean.Attachment;
import com.crm.common.ECode;
import com.crm.common.MessageException;
import com.crm.common.RestfulResponse;
import com.crm.vo.AttachmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssistantFeignCommon {
	@Autowired
	private AssistantFeignClient feignClient;

	/**
	 * 根据附件key获取附件集合
	 * 
	 * @param attachmentKey
	 * @return
	 * @throws Exception
	 */
	public List<? extends Attachment> listData(String attachmentKey) throws Exception {
		if (attachmentKey == null || "".equals(attachmentKey)) {
			return new ArrayList();
		}
		RestfulResponse<List<AttachmentVo>> attachmentResult = feignClient.listData(attachmentKey);
		if (attachmentResult.getCode() == ECode.NO_DATA_RESULT.getCode()) {
			throw new MessageException("未查询到附件信息");
		}
		if (attachmentResult.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(attachmentResult.getMessage());
		}

		return attachmentResult.getData();
	}

	/**
	 * 根据附件key获取附件集合
	 * 
	 * @param attachmentKeyList
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<? extends Attachment>> listDatas(List<String> attachmentKeyList) throws Exception {
		Map<String, List<? extends Attachment>> result = new HashMap();
		if (attachmentKeyList == null || attachmentKeyList.size() == 0) {
			return result;
		}

		String[] attachmentKeyArray = new String[attachmentKeyList.size()];
		RestfulResponse<Map<String, List<AttachmentVo>>> attachmentResult = feignClient
				.listDatas(attachmentKeyList.toArray(attachmentKeyArray));
		if (attachmentResult.getCode() == ECode.NO_DATA_RESULT.getCode()) {
			throw new MessageException("未查询到附件信息");
		}
		if (attachmentResult.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(attachmentResult.getMessage());
		}

		for (String attachmentKey : attachmentResult.getData().keySet()) {
			List<Attachment> list = new ArrayList();
			List<AttachmentVo> voList = attachmentResult.getData().get(attachmentKey);
			list.addAll(voList);

			result.put(attachmentKey, list);
		}
		return result;
	}

	/**
	 * 新增附件
	 * 
	 * @return
	 * @throws Exception
	 */
	public String saveData(List<? extends Attachment> attachmentlist) throws Exception {
		if (attachmentlist == null || attachmentlist.size() == 0) {
			return null;
		}
		RestfulResponse<String> attachmentResult = feignClient.saveData((List<Attachment>) attachmentlist);
		if (attachmentResult.getData() == null || attachmentResult.getData().equals("")) {
			throw new MessageException("服务未返回附件上传成功信息");
		}
		if (attachmentResult.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(attachmentResult.getMessage());
		}

		return attachmentResult.getData();
	}

	/**
	 * 根据附件key删除附件
	 * 
	 * @param attachmentKey
	 * @return
	 * @throws Exception
	 */
	public Integer deleteData(String attachmentKey) throws Exception {
		if (attachmentKey == null || "".equals(attachmentKey)) {
			return 0;
		}
		RestfulResponse<Integer> attachmentResult = feignClient.deleteData(attachmentKey);
		if (attachmentResult.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(attachmentResult.getMessage());
		}

		return attachmentResult.getData();
	}

	/**
	 * 保存附件集合
	 * 
	 * @return attachmentKey
	 * @throws Exception
	 */
	public String saveFileList(List<String> fileUrlList) {
		RestfulResponse<String> attachmentResult = feignClient.saveFileList(fileUrlList);
		if (attachmentResult.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(attachmentResult.getMessage());
		}

		return attachmentResult.getData();
	}
	
	public String doHandleFileUrlForAliyun(String content, boolean isFromPC) {
		RestfulResponse<String> attachmentResult = feignClient.doHandleFileUrlForAliyun(content,isFromPC);
		if (attachmentResult.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(attachmentResult.getMessage());
		}

		return attachmentResult.getData();
	}
}
