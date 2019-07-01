package com.crm.assistant.service.impl;

import static com.crm.utils.StringUtil.isEmpty;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import com.crm.assistant.mapper.AttachmentMapper;
import com.crm.assistant.model.attachment.VersionInfo;
import com.crm.assistant.service.AttachmentService;
import com.crm.assistant.util.FileUtil;
import com.crm.common.MessageException;
import com.crm.dto.AttachmentDto;
import com.crm.vo.AttachmentVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AttachmentServiceImpl implements AttachmentService {
	@Autowired
    AttachmentMapper attachmentmapper;
	public static final Logger log = LoggerFactory.getLogger(AttachmentServiceImpl.class);
	@Autowired
    FileUtil fileUtil;

    @Value("${attachment.bucketName}")
    private String bucketName;
    @Value("${attachment.secretId}")
    private String accessKeyId_boyin;
    @Value("${attachment.secretKey}")
    private String secretAccessKey_boyin;
    @Value("${attachment.endpoint}")
    private String endpoint;

	@Override
	public Integer deleteByAttachmentKey(String attachmentKey) {
		return attachmentmapper.deleteByAttachmentKey(attachmentKey);
	}

	@Override
	public List<AttachmentDto> saveDataInfo(List<AttachmentDto> attachmentDtoList) throws Exception {
		if (attachmentDtoList == null || attachmentDtoList.size() == 0) {
			log.info("附件集合为空");
			throw new MessageException("附件集合为空");
		}
		// 清除原附件信息
		String attachmentKey = attachmentDtoList.get(0).getAttachmentKey();
		if (isEmpty(attachmentKey)) {
			attachmentKey = UUID.randomUUID().toString();
		} else {
			attachmentmapper.deleteByAttachmentKey(attachmentKey);
		}
		String pattern1 = "^http://.*";
		String pattern2 = "^http://" + bucketName + "." + endpoint + ".*";
		for (int i = 0; i < attachmentDtoList.size(); i++) {
			AttachmentDto attachmentDto = attachmentDtoList.get(i);

			// 正则校检文件路径
			String fileUrl = attachmentDto.getFileUrl();
			if (!Pattern.matches(pattern1, fileUrl)) {
				fileUrl = "http://" + fileUrl;
				attachmentDto.setFileUrl(fileUrl);
			}
			//			if (!Pattern.matches(pattern2, fileUrl)) {
			//				throw new MessageException("文件路径不正确");
			//			}
			attachmentDto.setSeqNo(i + 1);
			attachmentDto.setAttachmentKey(attachmentKey);
			// 将入参转换为持久化对象
			AttachmentVo attachmentVo = new AttachmentVo();
			BeanUtils.copyProperties(attachmentDto, attachmentVo);
			attachmentmapper.insert(attachmentVo);
		}
		return attachmentDtoList;
	}

	@Override
	public List<AttachmentVo> getDataList(String id, boolean isFromPC) throws Exception {
		List<AttachmentVo> attachmentVoList = attachmentmapper.selectByPrimaryId(id);
		// 为阿里云存储路径加上授权字符串
		/*for (int i = 0; i < attachmentVoList.size(); i++) {
			AttachmentVo attachmentVo = attachmentVoList.get(i);
			String generateAliUrl =
					fileUtil.generateAliUrl(attachmentVo.getFileUrl(),isFromPC);
			attachmentVo.setFileUrl(generateAliUrl);

			// 暂不加授权字符串
			//			if (attachmentVo.getFileUrl().indexOf("?") != -1) {
			//				attachmentVo.setFileUrl(attachmentVo.getFileUrl().substring(0, attachmentVo.getFileUrl().indexOf("?")));
			//			}
		}*/
		return attachmentVoList;
	}

	@Override
	public Map<String, List<AttachmentVo>> getDataLists(String[] attachmentKeyArray, boolean isFromPC)
			throws Exception {
		Map<String, List<AttachmentVo>> reuslt = new HashMap<String, List<AttachmentVo>>();
		// 将附件id作为key，附件组作为map返回
		for (int i = 0; i < attachmentKeyArray.length; i++) {
			String id = attachmentKeyArray[i];
			List<AttachmentVo> attachmentVoList = attachmentmapper.selectByPrimaryId(id);
			// 为阿里云存储路径加上授权字符串
			/*for (int ij = 0; ij < attachmentVoList.size(); ij++) {
				AttachmentVo attachmentVo = attachmentVoList.get(ij);
				String generateAliUrl =
						fileUtil.generateAliUrl(attachmentVo.getFileUrl(),isFromPC);
				attachmentVo.setFileUrl(generateAliUrl);

				// 暂不加授权字符串
				//				if (attachmentVo.getFileUrl().indexOf("?") != -1) {
				//					attachmentVo
				//							.setFileUrl(attachmentVo.getFileUrl().substring(0, attachmentVo.getFileUrl().indexOf("?")));
				//				}
			}*/
			reuslt.put(id, attachmentVoList);
		}
		return reuslt;
	}

	@Override
	public VersionInfo getVersion() {
		VersionInfo vo= attachmentmapper.getVerstion();
		return vo;
	}

	@Override
	public String doHandleFileUrlForAliyun(String content, boolean isFromPC) throws Exception {
		String prefix="src=\"";

		Map<String,String> replceMap=new HashMap<String,String>();
		int indexOf = prefix.length();
		int prefixLength=prefix.length();
		int odlIndexOf = content.indexOf(prefix);
		if(odlIndexOf!=-1) {
			int tailIndex =0;

			while(true) {
				indexOf = content.indexOf(prefix,tailIndex);
				if(indexOf==-1) {
					break;
				}
				indexOf+=prefixLength;
				tailIndex = content.indexOf("\"",indexOf);
				String fileUrl = content.substring(indexOf, tailIndex);
				//如果是阿里云的OSS附件
				if(fileUrl.indexOf(".oss")!=-1) {
					String generateAliUrl =
							fileUtil.generateAliUrl(fileUrl,isFromPC);
					replceMap.put(fileUrl, generateAliUrl);
				}
			}
			Iterator<String> iterator = replceMap.keySet().iterator();
			while(iterator.hasNext()) {
				String key = iterator.next();
				String temp = replceMap.get(key);
				content=content.replace(key, temp);
			}
		}else {
			log.error("当前富文本未找到需要处理的增加授权字符串的Aliyun附件,text:"+content);
		}
		return content;
	}

	@Override
	public String[] doHandleFileUrlForAliyunByPC(String[] content) throws Exception {
		for (int i = 0; i < content.length; i++) {
			String fileUrl = content[i];

			String generateAliUrl =fileUtil.generateAliUrl(fileUrl,true);
			content[i]=generateAliUrl;
		}
		return content;
	}

}
