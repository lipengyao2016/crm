package com.crm.work.controller;

import com.crm.annotation.NoPermissionAuth;
import com.crm.annotation.OperationLog;
import com.crm.annotation.PermissionAuth;
import com.crm.common.*;
import com.crm.utils.StringUtil;
import com.crm.work.model.dto.PlanInfoDto;
import com.crm.work.model.pojo.PlanSearchCondition;
import com.crm.work.model.vo.PlanInfoVo;
import com.crm.work.service.IPlanInfoService;
import io.swagger.annotations.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags={"任务管理"})
@RestController()
@RequestMapping("/plan")
@PermissionAuth
public class PlanController {

    private Log log = LogFactory.getLog(PlanController.class);

    @Autowired
    IPlanInfoService planInfoService;

    @ApiOperation(value = "查询任务列表分页信息")
    @GetMapping("/listPlan")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", dataType = "int", value = "第几页", required = true, defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", value = "每页数据行数", required = true, defaultValue = "10")})
    public RestfulResponse<NewPageInfo
                <PlanInfoVo>> list(HttpServletRequest request, HttpServletResponse response,
                                 @ApiParam(name = "搜索参数", required = true) PlanSearchCondition cond)throws Exception {
        RestfulResponse<NewPageInfo<PlanInfoVo>> restfulResponse = new RestfulResponse<NewPageInfo<PlanInfoVo>>(new NewPageInfo<>());
        Long id = StringUtil.getUserId(request);
        NewPageInfo<PlanInfoVo> list = planInfoService.list(cond);

        if(list==null || list.getList().size()==0){
            throw new MessageException(ECode.NO_DATA_RESULT);
        }
        restfulResponse.setData(list);
        return restfulResponse;
    }

    @NoPermissionAuth
    @ApiOperation(value = "查询任务详情")
    @GetMapping("/get/{pid}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pid", dataType = "String", value = "任务编码", defaultValue = "")})
    public RestfulResponse<PlanInfoVo> get(@PathVariable("pid") Long pid)throws Exception {
        RestfulResponse<PlanInfoVo> result = new RestfulResponse<PlanInfoVo>(new PlanInfoVo());
        PlanInfoVo roleVo = planInfoService.get(pid);
        result.setData(roleVo);
        return result;
    }

    @ApiOperation(value = "删除任务")
    @DeleteMapping("/delete/{pid}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pid", dataType = "java.lang.String", value = "任务编码", defaultValue = "")})
    public RestfulResponse<Void> delete(@PathVariable("pid") Long pid)throws Exception {
        planInfoService.delete(pid);
        return new RestfulResponse<Void>();
    }

    @OperationLog
    @ApiOperation(value = "新增任务")
    @PostMapping("/add/addPlan")
    public RestfulResponse<Void> addUserInfo(HttpServletRequest request, HttpServletResponse response,
                                             @ApiParam(name = "任务信息", required = true) @RequestBody PlanInfoDto dto) throws Exception {
        Long agencyId = StringUtil.getAgencyId(request);
        dto.setCreatedBy(agencyId);
        RestfulResponse restful = new RestfulResponse<Void>();
        restful.setTableName("plan_info");
        restful.setOperateType(Response.INSERT);

        try {
            planInfoService.insert(dto);
            restful.setMajorKeyId(dto.getId().toString());
        } catch (MessageException e) {
            restful.setCode(ECode.ADD_ERROR.getCode());
            if (e.getCode() != null) {
                restful.setCode(e.getCode().getCode());
            }
            restful.setMessage("保存任务失败：" + e.getMessage());
            log.error("保存任务失败：" + e.getMessage(),e);
            return restful;
        }

        return restful;
    }

    @OperationLog
    @ApiOperation(value = "修改任务")
    @PostMapping("/update/updatePlan")
    public RestfulResponse<Void> setUserInfo(HttpServletRequest request, HttpServletResponse response,
                                             @ApiParam(name = "任务信息", required = true) @RequestBody PlanInfoDto dto)throws Exception {
        RestfulResponse<Void> restful = new RestfulResponse<Void>();
        restful.setMajorKeyId(dto.getId().toString());
        restful.setOperateType(Response.UPDATE);
        restful.setTableName("plan_info");

        planInfoService.update(dto);
        return restful;
    }

}
