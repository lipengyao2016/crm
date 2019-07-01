package com.crm.basic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api(tags = "用户查看的操作日志")
@RequestMapping("/operationLogByUser")
@RestController()
public class OperationLogByUserController {
	/*private static final Logger logger = LoggerFactory.getLogger(OperationLogByUserController.class);

	@Autowired
	private OperationLogByUserService operationLogService;

	@PermissionAuth
	@ApiOperation(value = "查询操作日志信息")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 1000, message = "查询成功") })
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "pageNum", dataType = "int", value = "第几页", required = true, defaultValue = "1"),
			@ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", value = "每页数据行数", required = true, defaultValue = "10"),
			@ApiImplicitParam(paramType = "query", name = "startTime", dataType = "String", value = "起始时间", defaultValue = "2017-09-20 08:39:20"),
			@ApiImplicitParam(paramType = "query", name = "endTime", dataType = "String", value = "结束时间", defaultValue = "2017-09-20 18:39:22"),
			@ApiImplicitParam(paramType = "query", name = "createdBy", dataType = "String", value = "用户登录账号或姓名", defaultValue = "boying"),
			@ApiImplicitParam(paramType = "query", name = "operationType", dataType = "String", value = "操作类型", defaultValue = "SELECT"),
			@ApiImplicitParam(paramType = "query", name = "tableName", dataType = "String", value = "表名称", defaultValue = "user_info"),
			@ApiImplicitParam(paramType = "query", name = "permissionUrl", dataType = "String", value = "请求的URL", defaultValue = "/houseTypes"),
			@ApiImplicitParam(paramType = "query", name = "requestMethod", dataType = "String", value = "请求方式", defaultValue = "POST"),
			@ApiImplicitParam(paramType = "query", name = "operateStatus", dataType = "String", value = "操作状态", defaultValue = "SUCCESS"),
			@ApiImplicitParam(paramType = "query", name = "fromBy", dataType = "String", value = "操作终端类型", defaultValue = "Android"),
			@ApiImplicitParam(paramType = "query", name = "majorKeyId", dataType = "String", value = "操作的对应数据行主键", defaultValue = "1"),
			@ApiImplicitParam(paramType = "query", name = "tableDesc", dataType = "String", value = "操作业务", defaultValue = "楼盘信息") })
	@GetMapping("operationLogDatas")
	public RestfulResponse<NewPageInfo<OperationLogVo>> findOperationLogData(
			@ApiParam(name = "分页查询条件") OperationSearchCondition cond) {

		if (cond == null) {
			cond = new OperationSearchCondition();
		}

		NewPageInfo<OperationLogVo> voList = new NewPageInfo<OperationLogVo>(new ArrayList<>());
		RestfulResponse<NewPageInfo<OperationLogVo>> restful = new RestfulResponse<NewPageInfo<OperationLogVo>>(voList);
		try {
			NewPageInfo<OperationLogVo> result = operationLogService.findOperationLogList(cond);
			restful.setData(result);
			if (result == null || result.getSize() == 0) {
				restful.setCode(ECode.NO_DATA_RESULT.getCode());
				restful.setMessage("未查询到操作日志信息！");
			}

		} catch (MessageException e) {
			logger.error("查询到操作日志失败{}", e.getMessage());
			restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
			if (e.getCode() != null) {
				restful.setCode(e.getCode().getCode());
			}
			restful.setMessage("查询到操作日志失败" + e.getMessage());
			return restful;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询到操作日志失败{}", e.getMessage());
			restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
			restful.setMessage("查询到操作日志失败！");
			return restful;
		}
		return restful;
	}

	@ApiOperation(value = "保存操作日志信息", notes = "成功后返回日志seqId")
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 1000, message = "新增操作日志成功"),
			@ApiResponse(code = 1004, message = "新增失败！") })
	@PostMapping("createOperationLog")
	public RestfulResponse<OperationLogDto> createOperationLog(
			@ApiParam(name = "操作日志信息", required = true) @RequestBody OperationLogDto log) {
		RestfulResponse<OperationLogDto> restful = new RestfulResponse<OperationLogDto>(log);
		if (log.getPermissionUrl() == null || "".equals(log.getPermissionUrl())) {
			restful.setCode(ECode.ARG_ERROR.getCode());
			restful.setMessage("操作的模块id不能为空！");
			return restful;
		}
		if (log.getOperatedBy() == null || "".equals(log.getOperatedBy())) {
			restful.setCode(ECode.ARG_ERROR.getCode());
			restful.setMessage("操作用户编码不能为空！");
			return restful;
		}

		Long id = 0l;
		try {
			String content = log.getContent().replaceAll("~~", "/");
			log.setContent(content);
			id = operationLogService.insertOperationLog(log);
			log.setLogSeqId(id);
		} catch (MessageException e) {
			logger.error("保存操作日志失败:" + e.getMessage());
			restful.setCode(ECode.ADD_ERROR.getCode());
			if (e.getCode() != null) {
				restful.setCode(e.getCode().getCode());
			}
			restful.setMessage("保存操作日志失败:" + e.getMessage());
			return restful;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存操作日志失败:" + e.getMessage());
			restful.setCode(ECode.ADD_ERROR.getCode());
			restful.setMessage("保存操作日志失败！");
			return restful;
		}

		return restful;
	}
*/
 
}
