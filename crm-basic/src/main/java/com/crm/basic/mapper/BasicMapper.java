package com.crm.basic.mapper;

import java.util.List;
import java.util.Map;

import com.crm.common.SearchCondition;
import org.apache.ibatis.annotations.Param;

/**
 * 
* @ClassName: BasicMapper
* @pack:com.by.crm.sysconfig.mapper.currencymapper
* @Description: 通用Mapper集成
* @author:zhoujingsong
 * @param <T>
* @date 2017年9月8日 下午12:11:18
*
*/
public interface BasicMapper<T>{
	/**
	 * 非空插入
	 * @param dto
	 * @return 返回操作数
	 */
	public void insertselective(T dto);
	/**
	 * 根据实体属性进行查找
	 * @param param 条件对象Map<String,Object>
	 * @return 返回List<Map<String,Object>>>集合对象
	 */
	public List<T> selectByCondition(Map<String,Object> param);
	/**
	 * 根据条件更新
	 * @param param 更新对象，更新的属性值为传入的条件对象，传入前判断主键不可为空
	 * @return
	 */
	public int updateByConditionselective(Map<String,Object> param);
	/**
	 * 禁用对象
	 * @id 主键
	 * @return
	 */
	public int updateStatusById(@Param("id")String id);
	
	
	
	
	@SuppressWarnings("rawtypes")
	public List list(SearchCondition cond);
	
	public Object selectByPrimaryKey(Object key);
	
	public void deleteByPrimaryKey(Object key);
	
	public void insert(Object dto);
	
	public void insertSelective(T dto);
	
	public void updateByPrimaryKeySelective(Object dto);
	
	public void updateByPrimaryKey(Object dto);
	
	
}
