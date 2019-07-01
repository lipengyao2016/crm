package com.crm.feign.assistant;

import java.util.List;
import java.util.Map;

import com.crm.bean.Attachment;
import com.crm.common.ECode;
import com.crm.common.RestfulResponse;
import com.crm.vo.AttachmentVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import feign.hystrix.FallbackFactory;

@Component
public class AssistantHystrixClientFallbackFactory implements FallbackFactory<AssistantFeignClient> {
	private static final Logger log = LoggerFactory.getLogger(AssistantHystrixClientFallbackFactory.class);

	@Override
	public AssistantFeignClient create(Throwable cause) {
		return new AssistantFeignClientWithFallBackFactory() {

			// -----------------------------------附件接口---------------------------------------------
			@Override
			public RestfulResponse<List<AttachmentVo>> listData(String attachmentKey) throws Exception {
				RestfulResponse<List<AttachmentVo>> response = RestfulResponse
						.error(ECode.CICD_JOB_QUERY_CODE_BRANCHES_FAILED);
				response.setMessage("查询附件列表失败！");
				return response;
			}

			@Override
			public RestfulResponse<String> saveData(List<Attachment> attachmentlist) throws Exception {
				RestfulResponse<String> response = RestfulResponse.error(ECode.CICD_JOB_QUERY_CODE_BRANCHES_FAILED);
				response.setMessage("保存附件失败！");
				return response;
			}

			@Override
			public RestfulResponse<Map<String, List<AttachmentVo>>> listDatas(String[] attachmentKeyArray)
					throws Exception {
				RestfulResponse<Map<String, List<AttachmentVo>>> response = RestfulResponse
						.error(ECode.CICD_JOB_QUERY_CODE_BRANCHES_FAILED);
				response.setMessage("查询附件列表集合失败！");
				return response;
			}

			@Override
			public RestfulResponse<Integer> deleteData(String attachmentKey) throws Exception {
				RestfulResponse<Integer> response = RestfulResponse.error(ECode.CICD_JOB_QUERY_CODE_BRANCHES_FAILED);
				response.setMessage("删除附件失败！");
				return response;
			}

			@Override
			public RestfulResponse<String> saveFileList(List<String> fileUrlList) {
				RestfulResponse<String> response = RestfulResponse.error(ECode.CICD_JOB_QUERY_CODE_BRANCHES_FAILED);
				response.setMessage("保存附件失败！");
				return response;
			}

			@Override
			public String handleFileUpload(MultipartFile file) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public RestfulResponse<String> doHandleFileUrlForAliyun(String content, boolean isFromPC) {
				RestfulResponse<String> response = RestfulResponse.error(ECode.CICD_JOB_QUERY_CODE_BRANCHES_FAILED);
				response.setMessage("远程调用出错处理阿里云附件私有化授权字符串失败");
				return response;
			}
		};
	}
}