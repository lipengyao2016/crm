package com.crm.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Restful 接口返回结果
 * 
 * @since 1.0
 * @param <T>
 *            数据类型
 */
@ApiModel
public class RestfulResponse<T> implements Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7115914438015603229L;

	/**
	 * 默认响应码
	 */
	public static final int DEFAULT_CODE = 1000;

	/**
	 * 响应码
	 */
	private int code = DEFAULT_CODE;

	/**
	 * 数据
	 */
	private T data;

	/**
	 * 提示信息
	 */
	private String message;

	/**
	 * 构造函数
	 */
	public RestfulResponse() {
		this(null, ECode.SUCCESS);
	}

	/**
	 * 构造函数
	 * 
	 * @param data
	 *            返回数据
	 */
	public RestfulResponse(T data) {
		this(data, null);
	}

	/**
	 * 构造函数
	 * 
	 * @param code
	 *            code
	 */
	public RestfulResponse(ECode code) {
		this(null, code);
	}
	
	/**
	 * 构造函数
	 * 
	 * @param code
	 *            code
	 */
	public RestfulResponse(int code,String message) {
		this.setCode(code);
		this.setMessage(message);
	}

	/**
	 * 构造函数
	 * 
	 * @param data
	 *            data
	 * @param code
	 *            code
	 */
	public RestfulResponse(T data, ECode code) {
		this.data = data;
		if (code != null) {
			this.code = code.getCode();
			this.message = code.getMessage();
		} else {
			this.code = ECode.SUCCESS.getCode();
			this.message = ECode.SUCCESS.getMessage();
		}
	}

	/**
	 * @param <T>
	 *            <T>
	 * @param code
	 *            code
	 * @return RestfulResponse<T>
	 */
	public static <T> RestfulResponse<T> error(ECode code) {
		RestfulResponse<T> resp = new RestfulResponse<>(code);
		return resp;
	}

	/**
	 * @param message
	 *            message
	 * @return RestfulResponse;
	 */
	public static RestfulResponse<Void> error(String message) {
		RestfulResponse<Void> response = new RestfulResponse<>();
		return response.message(message);
	}

	public static RestfulResponse<Void> error(ECode code, String message) {
		RestfulResponse<Void> resp = new RestfulResponse<>(code);
		resp.setMessage(message);
		return resp;
	}

	/**
	 * @param codex
	 *            code
	 * @return this
	 */
	public RestfulResponse<T> code(int codex) {
		this.code = codex;
		return this;
	}

	/**
	 * @param messagex
	 *            message
	 * @return this
	 */
	public RestfulResponse<T> message(String messagex) {
		this.message = messagex;
		return this;
	}

	/**
	 * @param datax
	 *            data
	 * @return this
	 */
	public RestfulResponse<T> data(T datax) {
		this.data = datax;
		return this;
	}

	/**
	 * @return 获取 code属性值
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code
	 *            设置 code 属性值为参数值 code
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return 获取 data属性值
	 */
	public T getData() {
		return data;
	}

	/**
	 * @param data
	 *            设置 data 属性值为参数值 data
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * @return 获取 message属性值
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            设置 message 属性值为参数值 message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	// 操作成功后返回的主键
	@ApiModelProperty(hidden = true)
	private String majorKeyId;
	// 操作类型SELECT，UPDATE，DELETE，INSERT
	@ApiModelProperty(hidden = true)
	private String operateType;
	// 操作的表名
	@ApiModelProperty(hidden = true)
	private String tableName;
	// 操作备注
	@ApiModelProperty(hidden = true)
	private String comments;
	// 总数(辅助字段)
	@ApiModelProperty(hidden = true)
	private Integer total = 0;

	public String getMajorKeyId() {
		return majorKeyId;
	}

	public void setMajorKeyId(String majorKeyId) {
		this.majorKeyId = majorKeyId;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
}
