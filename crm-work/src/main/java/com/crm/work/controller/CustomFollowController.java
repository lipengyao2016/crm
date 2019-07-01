package com.crm.work.controller;

import com.crm.annotation.NoPermissionAuth;
import com.crm.annotation.OperationLog;
import com.crm.annotation.PermissionAuth;
import com.crm.common.*;
import com.crm.work.model.dto.CustomFollowDto;
import com.crm.work.model.pojo.CustomFollowSearchCondition;
import com.crm.work.model.pojo.CustomSearchCondition;
import com.crm.work.model.vo.CustomFollowVo;
import com.crm.work.service.ICustomFollowService;
import com.crm.work.service.ICustomService;
import io.swagger.annotations.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags={"跟进管理/工作待办"})
@RestController()
@RequestMapping("/customFollow")
@PermissionAuth
public class CustomFollowController {

    private Log log = LogFactory.getLog(CustomFollowController.class);

    @Autowired
    ICustomFollowService customFollowService;

    @ApiOperation(value = "查询跟进列表分页信息")
    @GetMapping("/listFollow")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", dataType = "int", value = "第几页", required = true, defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", value = "每页数据行数", required = true, defaultValue = "10"),
            @ApiImplicitParam(paramType = "query", name = "customType", dataType = "int", value = "跟进分类", required = false, defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "customId", dataType = "int", value = "客户编码", required = false, defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "followStage", dataType = "int", value = "跟进状态", required = false, defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "followUserId", dataType = "Long", value = "跟进人", defaultValue = "")})
    public RestfulResponse<NewPageInfo
                <CustomFollowVo>> list(HttpServletRequest request, HttpServletResponse response,
                                     @ApiParam(name = "搜索参数", required = true) CustomFollowSearchCondition cond)throws Exception {
        NewPageInfo<CustomFollowVo> list = customFollowService.list(cond);
        RestfulResponse<NewPageInfo<CustomFollowVo>> restfulResponse = new RestfulResponse<NewPageInfo<CustomFollowVo>>(list);
        return restfulResponse;
    }

    @ApiOperation(value = "查询待办列表分页信息")
    @GetMapping("/listRemain")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", dataType = "int", value = "第几页", required = true, defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", value = "每页数据行数", required = true, defaultValue = "10"),
            @ApiImplicitParam(paramType = "query", name = "startTime", dataType = "String", value = "开始时间", required = true, defaultValue = "2019-6-19"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", value = "每页数据行数", required = false, defaultValue = "10"),
            @ApiImplicitParam(paramType = "query", name = "customType", dataType = "int", value = "跟进分类", required = false, defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "customId", dataType = "int", value = "客户编码", required = false, defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "followStage", dataType = "int", value = "跟进状态", required = false, defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "followUserId", dataType = "Long", value = "跟进人", defaultValue = "")})
    public RestfulResponse<NewPageInfo
            <CustomFollowVo>> listRemain(HttpServletRequest request, HttpServletResponse response,
                                 @ApiParam(name = "搜索参数", required = true) CustomFollowSearchCondition cond)throws Exception {
        NewPageInfo<CustomFollowVo> list = customFollowService.list(cond);
        if(list.getList()==null || list.getList().size()==0){
            throw new MessageException(ECode.NO_DATA_RESULT);
        }
        RestfulResponse<NewPageInfo<CustomFollowVo>> restfulResponse = new RestfulResponse<NewPageInfo<CustomFollowVo>>(list);
        return restfulResponse;
    }

    @NoPermissionAuth
    @ApiOperation(value = "查询跟进详情")
    @GetMapping("/get/{fid}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "fid", dataType = "String", value = "跟进编码", defaultValue = "")})
    public RestfulResponse<CustomFollowVo> get(@PathVariable("fid") Long fid)throws Exception {
        RestfulResponse<CustomFollowVo> result = new RestfulResponse<CustomFollowVo>(new CustomFollowVo());
        CustomFollowVo roleVo = customFollowService.get(fid);
        if(roleVo == null){
            result.setMessage(ECode.NO_DATA_RESULT.getMessage());
            result.setCode(ECode.NO_DATA_RESULT.getCode());
        }
        result.setData(roleVo);
        return result;
    }

    @ApiOperation(value = "删除跟进")
    @DeleteMapping("/delete/{fid}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "fid", dataType = "Long", value = "跟进编码", defaultValue = "")})
    public RestfulResponse<Void> delete(@PathVariable("fid") Long fid)throws Exception {
        customFollowService.delete(fid);
        return new RestfulResponse<Void>();
    }

    @OperationLog
    @ApiOperation(value = "新增跟进")
    @PostMapping("/add/addFollow")
    public RestfulResponse<Void> addFollow(HttpServletRequest request, HttpServletResponse response,
                                             @ApiParam(name = "跟进信息", required = true) @RequestBody CustomFollowDto dto) throws Exception {

        RestfulResponse restful = new RestfulResponse<Void>();
        restful.setTableName("custom_follow");
        restful.setOperateType(Response.INSERT);

        try {
            customFollowService.insert(dto);
            restful.setMajorKeyId(dto.getCid().toString());
        } catch (MessageException e) {
            restful.setCode(ECode.ADD_ERROR.getCode());
            if (e.getCode() != null) {
                restful.setCode(e.getCode().getCode());
            }
            restful.setMessage("保存跟进失败：" + e.getMessage());
            log.error("保存跟进失败：" + e.getMessage(),e);
            return restful;
        }

        return restful;
    }

    @OperationLog
    @ApiOperation(value = "修改跟进")
    @PostMapping("/update/updateFollow")
    public RestfulResponse<Void> updateFollow(HttpServletRequest request, HttpServletResponse response,
                                             @ApiParam(name = "跟进信息", required = true) @RequestBody CustomFollowDto dto)throws Exception {
        RestfulResponse<Void> restful = new RestfulResponse<Void>();
        restful.setMajorKeyId(dto.getId().toString());
        restful.setOperateType(Response.UPDATE);
        restful.setTableName("custom_follow");

        customFollowService.update(dto);
        return restful;
    }
}
