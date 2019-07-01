package com.crm.administration.service;

import com.crm.administration.model.pojo.PermissionSearchCondition;
import com.crm.common.NewPageInfo;
import com.crm.dto.PermissionDto;
import com.crm.vo.PermissionVo;

import java.util.List;

public interface IPermissionService {

    public void create(PermissionDto dto) throws Exception;

    public void update(PermissionDto dto);

    public NewPageInfo<PermissionVo> list(PermissionSearchCondition cond);

    public PermissionVo get(Long rid);

    public void delete(Long rid);

    public List<PermissionVo> permissionTree();

    public List<PermissionVo> AllPermissionCache();

    public List<PermissionVo> permissionByUser(PermissionSearchCondition cond);

    public boolean needPermissionByUrl(String permissionUrl,String requestMethod,String pcMenuUrl);

    /**
     * 查询用户在指定商户下是否有权限操作
     *
     * @param permissionUrl
     * @return
     */
    public boolean hasPermission(Long uid, Long merchantId, String permissionUrl, String requestMethod,
                                 String pcMenuUrl);
    public PermissionVo findPermissionByUrl(String permissionUrl, String requestMethod);
}
