package com.crm.administration.controller;

import com.crm.administration.model.dto.RoleDto;
import com.crm.administration.model.pojo.RoleSearchCondition;
import com.crm.administration.service.IRoleService;
import com.crm.annotation.NoPermissionAuth;
import com.crm.annotation.OperationLog;
import com.crm.annotation.PermissionAuth;
import com.crm.vo.RoleVo;
import com.crm.common.*;
import io.swagger.annotations.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController()
@Api(tags={"角色管理"},value = "Test")
@RequestMapping("role")
@PermissionAuth
public class RoleController {

    private Log log = LogFactory.getLog(RoleController.class);

    @Autowired
    IRoleService roleService;

    @ApiOperation(value = "查询角色列表分页信息")
    @GetMapping("/listRole")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", dataType = "int", value = "第几页", required = true, defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", value = "每页数据行数", required = true, defaultValue = "10"),
            @ApiImplicitParam(paramType = "query", name = "keyword", dataType = "String", value = "角色名称", defaultValue = "")})
    public RestfulResponse<NewPageInfo
                <RoleVo>> list(HttpServletRequest request, HttpServletResponse response,
                           @ApiParam(name = "搜索参数", required = true) RoleSearchCondition cond) {
        NewPageInfo<RoleVo> list = roleService.list(cond);
        RestfulResponse<NewPageInfo<RoleVo>> restfulResponse = new RestfulResponse<NewPageInfo<RoleVo>>(list);
        return restfulResponse;
    }

    @NoPermissionAuth
    @ApiOperation(value = "查询角色详情")
    @GetMapping("/get/{rid}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "rid", dataType = "java.lang.Long", value = "角色编码", defaultValue = "")})
    public RestfulResponse<RoleVo> get(@PathVariable("rid") Long rid) {
        RestfulResponse<RoleVo> result = new RestfulResponse<RoleVo>(new RoleVo());
        RoleVo roleVo = roleService.get(rid);
        if(roleVo == null){
            result.setMessage(ECode.NO_DATA_RESULT.getMessage());
            result.setCode(ECode.NO_DATA_RESULT.getCode());
        }
        result.setData(roleVo);
        return result;
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/delete/{rid}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "rid", dataType = "java.lang.Long", value = "角色编码", defaultValue = "")})
    public RestfulResponse<Void> delete(@PathVariable("rid") Long rid) {
        roleService.delete(rid);
        return new RestfulResponse<Void>();
    }

    @OperationLog
    @ApiOperation(value = "新增角色")
    @PostMapping("/add/addRole")
    public RestfulResponse<Void> addUserInfo(HttpServletRequest request, HttpServletResponse response,
                                             @ApiParam(name = "角色信息", required = true) @RequestBody RoleDto dto) throws Exception {

        RestfulResponse<Void> restful = new RestfulResponse<Void>();
        restful.setTableName("role");
        restful.setOperateType(Response.INSERT);

        try {
            roleService.create(dto);
            restful.setMajorKeyId(dto.getId().toString());
        } catch (MessageException e) {
            restful.setCode(ECode.ADD_ERROR.getCode());
            if (e.getCode() != null) {
                restful.setCode(e.getCode().getCode());
            }
            restful.setMessage("保存角色失败：" + e.getMessage());
            log.error("保存角色失败：" + e.getMessage(),e);
            return restful;
        }

        return restful;
    }

    @OperationLog
    @ApiOperation(value = "修改角色")
    @PostMapping("/update/updateRole")
    public RestfulResponse<Void> setUserInfo(HttpServletRequest request, HttpServletResponse response,
                                             @ApiParam(name = "角色信息", required = true) @RequestBody RoleDto dto) {
        RestfulResponse<Void> restful = new RestfulResponse<Void>();
        restful.setMajorKeyId(dto.getId().toString());
        restful.setOperateType(Response.UPDATE);
        restful.setTableName("role");

        roleService.update(dto);
        return restful;
    }
}
