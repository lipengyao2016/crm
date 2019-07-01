package com.crm.work.controller;

import com.crm.annotation.NoPermissionAuth;
import com.crm.annotation.OperationLog;
import com.crm.annotation.PermissionAuth;
import com.crm.common.*;
import com.crm.utils.StringUtil;
import com.crm.work.model.dto.PlanSummaryDto;
import com.crm.work.model.pojo.PlanSearchCondition;
import com.crm.work.model.pojo.PlanSummarySearchCondition;
import com.crm.work.model.vo.PlanSummaryVo;
import com.crm.work.service.IPlanInfoService;
import com.crm.work.service.IPlanSummaryService;
import io.swagger.annotations.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags={"计划总结管理"})
@RestController()
@RequestMapping("/planSummary")
@PermissionAuth
public class PlanSummaryController {

    private Log log = LogFactory.getLog(PlanSummaryController.class);

    @Autowired
    IPlanSummaryService planSummaryService;

    @ApiOperation(value = "查询计划总结列表分页信息")
    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", dataType = "int", value = "第几页", required = true, defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", value = "每页数据行数", required = true, defaultValue = "10")})
    public RestfulResponse<NewPageInfo
                <PlanSummaryVo>> list(HttpServletRequest request, HttpServletResponse response,
                                 @ApiParam(name = "搜索参数", required = true) PlanSummarySearchCondition cond)throws Exception {
        RestfulResponse<NewPageInfo<PlanSummaryVo>> restfulResponse = new RestfulResponse<NewPageInfo<PlanSummaryVo>>(new NewPageInfo<>());
        Long id = StringUtil.getUserId(request);
        cond.setCreatedBy(id);
        NewPageInfo<PlanSummaryVo> list = planSummaryService.list(cond);

        if(list==null || list.getList().size()==0){
            throw new MessageException(ECode.NO_DATA_RESULT);
        }
        restfulResponse.setData(list);
        return restfulResponse;
    }

    @NoPermissionAuth
    @ApiOperation(value = "查询计划总结详情")
    @GetMapping("/get/{psid}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "psid", dataType = "String", value = "计划总结编码", defaultValue = "")})
    public RestfulResponse<PlanSummaryVo> get(@PathVariable("psid") Long psid)throws Exception {
        RestfulResponse<PlanSummaryVo> result = new RestfulResponse<PlanSummaryVo>(new PlanSummaryVo());
        PlanSummaryVo roleVo = planSummaryService.get(psid);
        result.setData(roleVo);
        return result;
    }

    @ApiOperation(value = "删除计划总结")
    @DeleteMapping("/delete/{psid}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "psid", dataType = "java.lang.String", value = "计划总结编码", defaultValue = "")})
    public RestfulResponse<Void> delete(@PathVariable("psid") Long psid)throws Exception {
        planSummaryService.delete(psid);
        return new RestfulResponse<Void>();
    }

    @OperationLog
    @ApiOperation(value = "新增计划总结")
    @PostMapping("/add")
    public RestfulResponse<Void> addUserInfo(HttpServletRequest request, HttpServletResponse response,
                                             @ApiParam(name = "计划总结信息", required = true) @RequestBody PlanSummaryDto dto) throws Exception {

        RestfulResponse restful = new RestfulResponse<Void>();
        restful.setTableName("permission");
        restful.setOperateType(Response.INSERT);

        try {
            planSummaryService.insert(dto);
            restful.setMajorKeyId(dto.getId().toString());
        } catch (MessageException e) {
            restful.setCode(ECode.ADD_ERROR.getCode());
            if (e.getCode() != null) {
                restful.setCode(e.getCode().getCode());
            }
            restful.setMessage("保存计划总结失败：" + e.getMessage());
            log.error("保存计划总结失败：" + e.getMessage(),e);
            return restful;
        }

        return restful;
    }

    @OperationLog
    @ApiOperation(value = "修改计划总结")
    @PostMapping("/update")
    public RestfulResponse<Void> setUserInfo(HttpServletRequest request, HttpServletResponse response,
                                             @ApiParam(name = "计划总结信息", required = true) @RequestBody PlanSummaryDto dto)throws Exception {
        RestfulResponse<Void> restful = new RestfulResponse<Void>();
        restful.setMajorKeyId(dto.getId().toString());
        restful.setOperateType(Response.UPDATE);
        restful.setTableName("permission");

        planSummaryService.update(dto);
        return restful;
    }

}
