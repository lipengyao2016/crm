package com.crm.work.controller;

import com.crm.annotation.NoPermissionAuth;
import com.crm.annotation.OperationLog;
import com.crm.annotation.PermissionAuth;
import com.crm.common.*;
import com.crm.work.model.dto.CustomInfoDto;
import com.crm.work.model.pojo.CustomSearchCondition;
import com.crm.work.model.vo.CustomInfoVo;
import com.crm.work.service.ICustomService;
import io.swagger.annotations.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Api(tags={"客户管理"})
@RestController()
@RequestMapping("/custom")
@PermissionAuth
public class CustomController {

    private Log log = LogFactory.getLog(CustomController.class);

    @Autowired
    ICustomService customService;

    @ApiOperation(value = "查询客户列表分页信息")
    @GetMapping("/listCustom")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", dataType = "int", value = "第几页", required = true, defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", value = "每页数据行数", required = true, defaultValue = "10"),
            @ApiImplicitParam(paramType = "query", name = "customType", dataType = "int", value = "客户分类", required = false, defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "keyword", dataType = "String", value = "名称、微信、电话", defaultValue = "")})
    public RestfulResponse<NewPageInfo
                <CustomInfoVo>> list(HttpServletRequest request, HttpServletResponse response,
                                 @ApiParam(name = "搜索参数", required = true) CustomSearchCondition cond)throws Exception {
        RestfulResponse<NewPageInfo<CustomInfoVo>> restfulResponse = new RestfulResponse<NewPageInfo<CustomInfoVo>>(new NewPageInfo<>());
        NewPageInfo<CustomInfoVo> list = customService.list(cond);

        if(list==null || list.getList().size()==0){
            throw new MessageException(ECode.NO_DATA_RESULT);
        }
        restfulResponse.setData(list);
        return restfulResponse;
    }

    @NoPermissionAuth
    @ApiOperation(value = "查询客户详情")
    @GetMapping("/get/{cid}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "cid", dataType = "String", value = "客户编码", defaultValue = "")})
    public RestfulResponse<CustomInfoVo> get(@PathVariable("cid") Long cid)throws Exception {
        RestfulResponse<CustomInfoVo> result = new RestfulResponse<CustomInfoVo>(new CustomInfoVo());
        CustomInfoVo roleVo = customService.get(cid);
        result.setData(roleVo);
        return result;
    }

    @ApiOperation(value = "删除客户")
    @DeleteMapping("/delete/{cid}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "cid", dataType = "java.lang.String", value = "客户编码", defaultValue = "")})
    public RestfulResponse<Void> delete(@PathVariable("cid") Long cid)throws Exception {
        customService.delete(cid);
        return new RestfulResponse<Void>();
    }

    @OperationLog
    @ApiOperation(value = "新增客户")
    @PostMapping("/add/addCustom")
    public RestfulResponse<Void> addUserInfo(HttpServletRequest request, HttpServletResponse response,
                                             @ApiParam(name = "客户信息", required = true) @RequestBody CustomInfoDto dto) throws Exception {

        RestfulResponse restful = new RestfulResponse<Void>();
        restful.setTableName("permission");
        restful.setOperateType(Response.INSERT);

        try {
            customService.insert(dto);
            restful.setMajorKeyId(dto.getCid().toString());
        } catch (MessageException e) {
            restful.setCode(ECode.ADD_ERROR.getCode());
            if (e.getCode() != null) {
                restful.setCode(e.getCode().getCode());
            }
            restful.setMessage("保存客户失败：" + e.getMessage());
            log.error("保存客户失败：" + e.getMessage(),e);
            return restful;
        }

        return restful;
    }

    @OperationLog
    @ApiOperation(value = "修改客户")
    @PostMapping("/update/updateCustom")
    public RestfulResponse<Void> setUserInfo(HttpServletRequest request, HttpServletResponse response,
                                             @ApiParam(name = "客户信息", required = true) @RequestBody CustomInfoDto dto)throws Exception {
        RestfulResponse<Void> restful = new RestfulResponse<Void>();
        restful.setMajorKeyId(dto.getId().toString());
        restful.setOperateType(Response.UPDATE);
        restful.setTableName("permission");

        customService.update(dto);
        return restful;
    }

}
