package com.crm.common;

public enum ECode {
	/**
	 * 正常
	 */
	SUCCESS(1000, "请求成功"),

	/**
	 * 缺失参数
	 */
	ARG_ERROR(1001, "缺失参数"),

	/**
	 * 查询异常
	 */
	QUEERY_ERROR(1002, "查询异常"),

	/**
	 * 更新异常
	 */
	UPDATE_ERROR(1003, "更新异常"),

	/**
	 * 新增异常
	 */
	ADD_ERROR(1004, "新增异常"),

	/**
	 * 删除异常
	 */
	DELETE_ERROR(1005, "删除异常"),

	/**
	 * DAO异常
	 */
	DAO_ERROR(1009, "DAO异常"),

	/**
	 * 未授权
	 */
	UNAUTHORIZED(1006, "未授权"),

	/**
	 * 不支持的编码
	 */
	UNSUPPORTEDENCODING(1007, "不支持的编码"),

	/**
	 * 无效的URL
	 */
	MALFORMEDURL(1008, "无效的URL"),

	/** 创建构建失败 */
	CICD_JOB_CREATE_FAILED(1401, "创建构建失败"),

	/** 复制构建失败 */
	CICD_JOB_COPY_FAILED(1402, "创建构建失败"),

	/** 删除构建失败 */
	CICD_JOB_DELETE_FAILED(1403, "删除构建失败"),

	/** 更新构建失败 */
	CICD_JOB_UPDATE_FAILED(1404, "修改构建失败"),

	/** 查询构建详情失败 */
	CICD_JOB_QUERY_DETAILS_FAILED(1405, "查询构建详情失败"),

	/** 查询构建列表失败 */
	CICD_JOB_QUERY_LIST_FAILED(1406, "查询构建列表失败"),

	/** 启用构建失败 */
	CICD_JOB_ENABLE_FAILED(1407, "启用构建失败"),

	/** 禁用构建失败 */
	CICD_JOB_DISABLE_FAILED(1408, "禁用构建失败"),

	/** 查询是否可构建失败 */
	CICD_JOB_QUERY_BUILDABLE_FAILED(1409, "查询是否可构建失败"),

	/** 启动构建失败 */
	CICD_JOB_START_FAILED(1410, "启动构建失败"),

	/** 查询构建配置失败 */
	CICD_JOB_QUERY_CONFIG_FAILED(1411, "查询构建配置失败"),

	/** 创建构建任务失败 */
	CICD_BUILD_CREATE_FAILED(1412, "创建构建任务失败"),

	/** 删除构建任务失败 */
	CICD_BUILD_DELETE_FAILED(1413, "删除构建任务失败"),

	/** 查询构建任务详情失败 */
	CICD_BUILD_QUERY_DETAILS_FAILED(1414, "查询构建任务详情失败"),

	/** 查询构建任务列表失败 */
	CICD_BUILD_QUERY_LIST_FAILED(1415, "查询构建任务列表失败"),

	/** 查询html格式构建任务日志失败 */
	CICD_BUILD_QUERY_HTML_BUILD_LOG_FAILED(1416, "查询html格式构建任务日志失败"),

	/** 查询text格式构建任务日志失败 */
	CICD_BUILD_QUERY_TEXT_BUILD_LOG_FAILED(1417, "查询text格式构建任务日志失败"),

	/** 停止构建任务失败 */
	CICD_BUILD_STOP_FAILED(1418, "停止构建任务失败"),

	/** 处理构建任务完成通知失败 */
	CICD_BUILD_PROCESS_NOTIFICATION_FAILED(1419, "处理构建任务完成通知失败"),

	/** 查询构建项目失败 */
	CICD_JOB_QUERY_PROJECTS_FAILED(1420, "查询构建项目失败"),

	/** 查询代码仓库失败 */
	CICD_JOB_QUERY_PROJECT_CODES_FAILED(1421, "查询代码仓库失败"),

	/** 查询代码分支失败 */
	CICD_JOB_QUERY_CODE_BRANCHES_FAILED(1422, "查询代码分支失败"),

	/** 查询镜像仓库失败 */
	CICD_JOB_QUERY_PROJECT_IMAGES_FAILED(1423, "查询镜像仓库失败"),

	/** 下载构建包失败 */
	CICD_BUILD_DOWNLOAD_PACKAGES_FAILED(1424, "下载构建包失败"),
	
	/** 未知异常 */
	SYSTEM_UNKNOWN_EXCEPTION(1425, "系统出现未知异常，请与管理员联系"),
	
	/** 未查询到数据 */
	NO_DATA_RESULT(1426, "未查询到数据"),
	

	/** 删除职位失败 */
	DELETE_POSITION_FAILED(1427, "删除职位失败,该职位下还有用户"),
	
	/** 删除职位失败 */
	DELETE_DEPARTMENT_FAILED(1431, "删除部门失败,该部门下还有用户"),
	
	/** 添加职位失败 */
	ADD_POSITION_FAILED(1428, "添加职位失败,该职位名称已存在"),
	
	/** 修改职位失败 */
	SET_POSITION_FAILED(1429, "修改职位失败,该职位名称已存在"),

	NOT_LOGGED_IN(1430,"未登录");
	;

	/**
	 * 返回码
	 */
	private final int code;

	/**
	 * 提示信息
	 */
	private final String message;

	/**
	 * 构造函数
	 * 
	 * @param code
	 *            code
	 * @param message
	 *            message
	 */
	ECode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * @return 获取 code属性值
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return 获取 message属性值
	 */
	public String getMessage() {
		return message;
	}

}
