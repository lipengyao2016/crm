package com.crm.administration.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crm.administration.model.dto.UserDto;
import com.crm.administration.model.pojo.UserSearchCondition;
import com.crm.administration.service.IUserService;
import com.crm.common.NewPageInfo;
import com.crm.common.RestfulResponse;
import com.crm.vo.UserVo;
import com.crm.annotation.NoPermissionAuth;
import com.crm.annotation.OperationLog;
import com.crm.common.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController()
@Api(tags={"用户管理"},value = "Test")
@RequestMapping("/user")
public class UserController {

    private Log log = LogFactory.getLog(UserController.class);

    @Autowired
    IUserService userService;

    @ApiOperation(value = "查询用户列表分页信息")
    @GetMapping("/listUser")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", dataType = "int", value = "第几页", required = true, defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", value = "每页数据行数", required = true, defaultValue = "10"),
            @ApiImplicitParam(paramType = "query", name = "status", dataType = "String", value = "是否在岗", defaultValue = "Y"),
            @ApiImplicitParam(paramType = "query", name = "positionSeqId", dataType = "int", value = "岗位id", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "organizationId", dataType = "String", value = "组织结构编码", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "organizationLevel", dataType = "String", value = "组织结构类型", defaultValue = ""),
            @ApiImplicitParam(paramType = "query", name = "keyword", dataType = "String", value = "姓名或登录名", defaultValue = "")})
    public RestfulResponse<NewPageInfo<UserVo>> list(HttpServletRequest request, HttpServletResponse response,
                                                     @ApiParam(name = "搜索参数", required = true) UserSearchCondition cond) {
        NewPageInfo<UserVo> list = userService.list(cond);
//        list.getList().forEach(e -> {
//            if (e.getImglist() != null && e.getImglist().size() > 0) {
//                e.setPicUrl(e.getImglist().get(0).getFileUrl());
//            }
//        });
        RestfulResponse<NewPageInfo<UserVo>> restfulResponse = new RestfulResponse<NewPageInfo<UserVo>>(list);
        return restfulResponse;
    }

    @NoPermissionAuth
    @ApiOperation(value = "查询用户详情")
    @GetMapping("/get/{userLoginId}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "userLoginId", dataType = "Long", value = "用户编码", defaultValue = "")})
    public RestfulResponse<UserVo> get(@PathVariable("userLoginId") Long uid) {
        RestfulResponse<UserVo> result = new RestfulResponse<UserVo>(new UserVo());
        UserVo user = userService.getUserById(uid);
        if(user == null){
            result.setMessage(ECode.NO_DATA_RESULT.getMessage());
            result.setCode(ECode.NO_DATA_RESULT.getCode());
        }
        result.setData(user);
        return result;
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("/delete/{userLoginId}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "userLoginId", dataType = "Long", value = "用户编码", defaultValue = "")})
    public RestfulResponse<Void> delete(@PathVariable("userLoginId") Long uid) {
        userService.delete(uid);
        return new RestfulResponse<Void>();
    }

    @OperationLog
    @ApiOperation(value = "新增用户")
    @PostMapping("/add/addUserInfo")
    public RestfulResponse<Void> addUserInfo(HttpServletRequest request, HttpServletResponse response,
                                             @ApiParam(name = "用户相关信息", required = true) @RequestBody UserDto userDto) {
        // System.out.println("--------------------------------------------------------------------"+userInfoDto.getUserName());
        // if (StringUtil.isEmpty(userInfoDto.getUserCode())) {

        RestfulResponse restful = new RestfulResponse<Void>();
        restful.setTableName("user");
        restful.setOperateType(Response.INSERT);

        //检查登录名是否重复
        UserSearchCondition cond = new UserSearchCondition();
        cond.setUserLoginId(userDto.getUserLoginId());
        cond.setKeyword(userDto.getPhone());
        List<UserVo> list = userService.list(cond).getList();
        if (list.size() > 0) {
            UserVo userVo = list.get(0);
            if (userVo.getPhone() != null && userVo.getPhone().equals(userDto.getPhone())) {
                RestfulResponse restfulResponse = new RestfulResponse();
                restfulResponse.setCode(ECode.ADD_ERROR.getCode());
                restfulResponse.setMessage("电话：" + userDto.getPhone() + "已被注册！");
                return restfulResponse;
            }
            if (userVo.getUserLoginId() != null && userVo.getUserLoginId().equals(userDto.getUserLoginId())) {
                RestfulResponse restfulResponse = new RestfulResponse();
                restfulResponse.setCode(ECode.ADD_ERROR.getCode());
                restfulResponse.setMessage("重复的登录名");
                return restfulResponse;
            }
        }

        try {
            userDto.setCurrentPassword(DigestUtils.sha256Hex(userDto.getCurrentPassword()));
            userService.create(userDto);
            restful.setMajorKeyId(userDto.getId().toString());
        } catch (MessageException e) {
            restful.setCode(ECode.ADD_ERROR.getCode());
            if (e.getCode() != null) {
                restful.setCode(e.getCode().getCode());
            }
            restful.setMessage("保存用户失败：" + e.getMessage());
            return restful;
        }catch (Exception e) {
            log.error("保存用户失败:",e);
            restful.setCode(ECode.ADD_ERROR.getCode());
            restful.setMessage("保存用户失败,未知错误");
            return restful;
        }

        // } else {
        // userInfoDto.setUpdatedBy(StringUtil.getUserLoginId(request));
        // userService.update(userInfoDto);
        // }

        return restful;
    }

    @OperationLog
    @ApiOperation(value = "修改用户")
    @PostMapping("/update/userInfo")
    public RestfulResponse<Void> setUserInfo(HttpServletRequest request, HttpServletResponse response,
                                             @ApiParam(name = "用户信息字段", required = true) @RequestBody UserDto userDto) {
        RestfulResponse<Void> restful = new RestfulResponse<Void>();
        restful.setMajorKeyId(userDto.getUserLoginId());
        restful.setOperateType(Response.UPDATE);
        restful.setTableName("user_info");

        if(userDto.getId() == null || userDto.getId()==0L){
            restful.setCode(ECode.UPDATE_ERROR.getCode());
            restful.setMessage("更新失败，为查询到用户信息");
        }
        //检查登录名是否重复
        UserSearchCondition cond = new UserSearchCondition();
        cond.setUserLoginId(userDto.getUserLoginId());
        cond.setKeyword(userDto.getPhone());
        List<UserVo> list = userService.list(cond).getList();
        if (list.size() > 0) {
            UserVo userVo = list.get(0);
            if (userVo.getPhone() != null && userVo.getPhone().equals(userDto.getPhone()) && userVo.getId()==userDto.getId()) {
                RestfulResponse restfulResponse = new RestfulResponse();
                restfulResponse.setCode(ECode.UPDATE_ERROR.getCode());
                restfulResponse.setMessage("电话：" + userDto.getPhone() + "已被注册！");
                return restfulResponse;
            }
        }
        userDto.setCurrentPassword(DigestUtils.sha256Hex(userDto.getCurrentPassword()));
        userService.update(userDto);
        return restful;
    }
}
