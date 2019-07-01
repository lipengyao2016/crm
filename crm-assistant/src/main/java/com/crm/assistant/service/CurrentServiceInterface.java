package com.crm.assistant.service;

import com.crm.common.NewPageInfo;

public interface CurrentServiceInterface<T> {
	/**
	 * 查询列表数据
	 * @return 返回集合列表 ：xxxMap：[{xx:xx}，{xx:xx}]
	 * @throws Exception 向上抛异常
	 */
	public NewPageInfo<T> listDataInfo() throws Exception;
	/**
	 * 获取数据方法
	 * @return T
	 * @throws Exception 向上抛异常
	 */
	public T getDataInfo(String id) throws Exception;
	/**
	 * 根据主键删除附件
	 * @id 主键
	 * @return
	 */
	public Integer deleteByAttachmentKey(String attachmentKey);
}
