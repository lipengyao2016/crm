package com.crm.assistant.mapper;

import com.crm.assistant.model.attachment.VersionInfo;
import com.crm.bean.AttachmentKey;
import com.crm.vo.AttachmentVo;

import java.util.List;

public interface AttachmentMapper extends BasicMapper<AttachmentVo>{
    int deleteByPrimaryKey(AttachmentKey key);

    int insert(AttachmentVo record);

    int insertSelective(AttachmentVo record);

    AttachmentVo selectByPrimaryKey(AttachmentKey key);

    int updateByPrimaryKeySelective(AttachmentVo record);

    int updateByPrimaryKeyWithBLOBs(AttachmentVo record);

    int updateByPrimaryKey(AttachmentVo record);
    
    List<AttachmentVo> selectByPrimaryId(String id);

    int deleteByAttachmentKey(String key);
    
    VersionInfo getVerstion();
}