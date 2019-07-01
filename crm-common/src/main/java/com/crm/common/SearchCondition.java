package com.crm.common;

import com.crm.dto.IDto;
import com.crm.utils.DateTimeUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 查询条件父类
 * 
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
@ApiModel("列表查询基类")
public class SearchCondition implements IDto {
	private Integer pageNum = 1; // 查询结果分页参数：第几页
	private Integer pageSize = 10; // 查询结果分页参数：每页数据条数
	private String userIds; // 根据登录用户权限过滤出来的能查看的所有其他用户(包括自己)登录账号数据(以及和查询条件里的用户信息所取得的并集)，格式为'user1','user2'
	private Long createdBy; // 查询条件里的用户登录账号或姓名
	private String userLoginId; // 登录用户账号
	@ApiModelProperty(value = "开始时间",example = "2019-01-10 09:42:02")
	private String startTime;
	@ApiModelProperty(value = "结束时间",example = "2019-12-10 09:42:02")
	private String endTime;
	private Map<String, EOrderRule> orderFeildMap; // 查询排序字段名及排序规则
	private String status; // 根据是否从PC端登录由拦截器赋值Y/N/null可直接用于sql中,PC端登录值为空，移动端则为Y
	private Long agencyId;

	public SearchCondition() {
	}

	public SearchCondition(Integer pageNum, Integer pageSize) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}

	/**
	 * 子类可以实现该方法，用来根据条件获取查询SQL的OrderBy部分内容
	 * 
	 * @return
	 */
	public String getSqlOrderByCond() {
		StringBuilder orderBy = new StringBuilder();
		String replaceFirst="";
		if (this.getOrderFeildMap() != null && this.getOrderFeildMap().size() > 0) {
			orderBy.append(" order by ");
			for (String key : this.getOrderFeildMap().keySet()) {
				orderBy.append( "," + key + " " + this.getOrderFeildMap().get(key).name());
			}
			replaceFirst = orderBy.toString().replaceFirst(",", "");

		}
		return replaceFirst;
	}

	public Long getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPageNum() {
		if (pageNum == null || pageNum.equals(0)) {
			return 1;
		}
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		if (pageSize == null || pageSize.equals(0)) {
			return 1000;
		}
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Map<String, EOrderRule> getOrderFeildMap() {
		return orderFeildMap;
	}

	public void setOrderFeildMap(Map<String, EOrderRule> orderFeildMap) {
		this.orderFeildMap = orderFeildMap;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public String getUserLoginId() {
		return userLoginId;
	}

	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

	public String getStartTime() {
		if (startTime != null && !"".equals(startTime)) {
			Date startDate = DateTimeUtil.strToDate(startTime, "yyyy-MM-dd");
			startTime = DateTimeUtil.dateToStr(startDate);
			startTime += " 00:00:00";
		}
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		if (endTime != null && !"".equals(endTime)) {
			Date endDate = DateTimeUtil.strToDate(endTime, "yyyy-MM-dd");
			endTime = DateTimeUtil.dateToStr(endDate);
			endTime += " 23:59:59";
		}
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
