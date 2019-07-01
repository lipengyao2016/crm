package com.crm.administration.controller;

import com.crm.administration.model.pojo.PermissionSearchCondition;
import com.crm.administration.service.IPermissionService;
import com.crm.annotation.NoPermissionAuth;
import com.crm.annotation.OperationLog;
import com.crm.annotation.PermissionAuth;
import com.crm.dto.PermissionDto;
import com.crm.vo.PermissionVo;
import com.crm.vo.UserLoginInfoVo;
import com.crm.vo.UserVo;
import com.crm.common.*;
import io.swagger.annotations.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags={"权限管理"})
@RestController()
@RequestMapping("/permission")
@PermissionAuth
public class PermissionController {

    private Log log = LogFactory.getLog(PermissionController.class);

    @Autowired
    IPermissionService permissionService;

    @ApiOperation(value = "查询权限列表分页信息")
    @GetMapping("/listPermission")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", dataType = "int", value = "第几页", required = true, defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", value = "每页数据行数", required = true, defaultValue = "10"),
            @ApiImplicitParam(paramType = "query", name = "keyword", dataType = "String", value = "权限名称", defaultValue = "")})
    public RestfulResponse<NewPageInfo
                <PermissionVo>> list(HttpServletRequest request, HttpServletResponse response,
                                     @ApiParam(name = "搜索参数", required = true) PermissionSearchCondition cond) {
        NewPageInfo<PermissionVo> list = permissionService.list(cond);
        if(list==null || list.getList().size()==0){
            throw new MessageException(ECode.NO_DATA_RESULT);
        }
        RestfulResponse<NewPageInfo<PermissionVo>> restfulResponse = new RestfulResponse<NewPageInfo<PermissionVo>>(list);
        return restfulResponse;
    }

    @NoPermissionAuth
    @ApiOperation(value = "查询权限详情")
    @GetMapping("/get/{pid}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pid", dataType = "String", value = "权限编码", defaultValue = "")})
    public RestfulResponse<PermissionVo> get(@PathVariable("pid") Long pid) {
        RestfulResponse<PermissionVo> result = new RestfulResponse<PermissionVo>(new PermissionVo());
        PermissionVo roleVo = permissionService.get(pid);
        if(roleVo == null){
            result.setMessage(ECode.NO_DATA_RESULT.getMessage());
            result.setCode(ECode.NO_DATA_RESULT.getCode());
        }
        result.setData(roleVo);
        return result;
    }

    @ApiOperation(value = "删除权限")
    @DeleteMapping("/delete/{pid}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pid", dataType = "java.lang.String", value = "权限编码", defaultValue = "")})
    public RestfulResponse<Void> delete(@PathVariable("pid") Long pid) {
        permissionService.delete(pid);
        return new RestfulResponse<Void>();
    }

    @OperationLog
    @ApiOperation(value = "新增权限")
    @PostMapping("/add/addPermission")
    public RestfulResponse<Void> addUserInfo(HttpServletRequest request, HttpServletResponse response,
                                             @ApiParam(name = "权限信息", required = true) @RequestBody PermissionDto dto) throws Exception {

        RestfulResponse restful = new RestfulResponse<Void>();
        restful.setTableName("permission");
        restful.setOperateType(Response.INSERT);

        try {
            permissionService.create(dto);
            restful.setMajorKeyId(dto.getId().toString());
        } catch (MessageException e) {
            restful.setCode(ECode.ADD_ERROR.getCode());
            if (e.getCode() != null) {
                restful.setCode(e.getCode().getCode());
            }
            restful.setMessage("保存权限失败：" + e.getMessage());
            log.error("保存权限失败：" + e.getMessage(),e);
            return restful;
        }

        return restful;
    }

    @OperationLog
    @ApiOperation(value = "修改权限")
    @PostMapping("/update/updatePermission")
    public RestfulResponse<Void> setUserInfo(HttpServletRequest request, HttpServletResponse response,
                                             @ApiParam(name = "权限信息", required = true) @RequestBody PermissionDto dto) {
        RestfulResponse<Void> restful = new RestfulResponse<Void>();
        restful.setMajorKeyId(dto.getId().toString());
        restful.setOperateType(Response.UPDATE);
        restful.setTableName("permission");

        permissionService.update(dto);
        return restful;
    }

    @ApiOperation(value = "查询权限树")
    @GetMapping("/list/psermissionTree")
    public RestfulResponse<List<PermissionVo>> psermissionTree() {
        List<PermissionVo> list = permissionService.permissionTree();
        RestfulResponse<List<PermissionVo>> restfulResponse = new RestfulResponse<List<PermissionVo>>(list);
        return restfulResponse;
    }

    @ApiOperation(value = "查询当前用户拥有的权限")
    @GetMapping("/list/permissionByUser")
    @ApiImplicitParams({
    @ApiImplicitParam(paramType = "query", name = "functionType", dataType = "int", value = "功能类型(1为菜单，2为功能点)", required = true, defaultValue = "1")})
    public RestfulResponse<List<PermissionVo>> permissionByUser(HttpServletRequest request,@ApiParam(name = "搜索参数", required = true) PermissionSearchCondition cond) {
        UserVo loginVo = (UserVo) request.getAttribute("loginUserInfo");
        Long uid = loginVo.getId();
        List<PermissionVo> list = permissionService.permissionByUser(cond);
        RestfulResponse<List<PermissionVo>> restfulResponse = new RestfulResponse<List<PermissionVo>>(list);
        return restfulResponse;
    }

    @NoPermissionAuth
    @ApiOperation(value = "查询该用户在指定商户下是否有操作该菜单的权限", notes = "供微服务之间调用", hidden = true)
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 1000, message = "查询成功") })
    @GetMapping("/holdPermission/{requestMethod}")
    public RestfulResponse<Boolean> hasPermission(HttpServletRequest request, @PathVariable String requestMethod) {
        RestfulResponse<Boolean> restful = new RestfulResponse<Boolean>(false);
        try {
            UserLoginInfoVo loginVo = (UserLoginInfoVo) request.getAttribute("loginUserInfo");
            Long uid = loginVo.getId();
            Long agencyId = loginVo.getAgencyId();
            String permissionUrl = request.getHeader("requestURL");
            Boolean flag = permissionService.hasPermission(uid, null, permissionUrl, requestMethod, null);
            restful.setData(flag);
        } catch (Exception e) {
            log.error("查询权限出错{}"+ e.getMessage(),e);
            restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
            restful.setMessage("查询权限出错！");
            return restful;
        }
        return restful;
    }

    @NoPermissionAuth
    @ApiOperation(value = "查询权限菜单是否需要验证", notes = "供微服务之间调用", hidden = true)
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 1000, message = "查询成功") })
    @GetMapping("/needPermissionByUrl/{requestMethod}")
    public RestfulResponse<Boolean> needPermissionByUrl(HttpServletRequest request,
                                                        @PathVariable String requestMethod) {
        RestfulResponse<Boolean> restful = new RestfulResponse<Boolean>(false);
        try {
            System.out.println("请求路径：" + request.getRequestURI());
            String permissionUrl = request.getHeader("requestURL");
            Boolean flag = permissionService.needPermissionByUrl(permissionUrl, requestMethod, null);
            restful.setData(flag);
        } catch (Exception e) {
            log.error("查询权限出错{}"+e.getMessage(),e);
            restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
            restful.setMessage("查询权限出错！"+e.getMessage());
            return restful;
        }
        return restful;
    }

    @NoPermissionAuth
    @ApiOperation(value = "根据权限url和请求方式获取对应权限编码", notes = "此服务供后台微服务之间使用，如当查询业务数据时需从动态属性表获取对应信息在页面展示", hidden = true)
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 1000, message = "查询成功") })
    @GetMapping("/permissionsByUrl")
    public RestfulResponse<PermissionVo> findPermissionByUrl(
            @RequestParam(value = "permissionUrl", required = true) String permissionUrl,
            @RequestParam(value = "requestMethod", required = false) String requestMethod) {
        RestfulResponse<PermissionVo> restful = new RestfulResponse<PermissionVo>();
        try {
            PermissionVo result = permissionService.findPermissionByUrl(permissionUrl, requestMethod);
            if (result == null ) {
                restful.setCode(ECode.NO_DATA_RESULT.getCode());
                restful.setMessage("未匹配到权限信息！");
            } else {
                restful.setData(result);
            }
        } catch (Exception e) {
            log.error("匹配到权限出错{}"+ e.getMessage(),e);
            restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
            restful.setMessage("匹配到权限出错！");
            return restful;
        }
        return restful;
    }
}
