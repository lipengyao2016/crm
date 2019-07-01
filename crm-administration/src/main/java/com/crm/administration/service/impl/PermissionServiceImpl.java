package com.crm.administration.service.impl;

import com.crm.administration.mapper.PermissionMapper;
import com.crm.administration.model.pojo.PermissionSearchCondition;
import com.crm.administration.service.IPermissionService;
import com.crm.common.NewPageInfo;
import com.crm.dto.PermissionDto;
import com.crm.utils.DBEntityUtils;
import com.crm.utils.StringUtil;
import com.crm.vo.PermissionVo;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    PermissionMapper permissionMapper;
    @Autowired
    DBEntityUtils dbEntityUtils;

    @Override
    @CacheEvict(value = "permission", allEntries = true)
    public void create(PermissionDto dto) throws Exception {
        dbEntityUtils.initialEntity(dto);
        if(dto.getParentId() == null || dto.getParentId().equals(0L)){
            dto.setParentId(null);
        }
        permissionMapper.insertSelective(dto);
    }

    @Override
    @CacheEvict(value = "permission", allEntries = true)
    public void update(PermissionDto dto) {
        permissionMapper.updateByPrimaryKeySelective(dto);
    }

    @Override
    public NewPageInfo<PermissionVo> list(PermissionSearchCondition cond) {
        PageHelper.startPage(cond.getPageNum(), cond.getPageSize());

        List<PermissionVo> list = permissionMapper.list(cond);

        return new NewPageInfo(list);
    }

    @Override
    public PermissionVo get(Long rid) {
        return permissionMapper.selectByPrimaryKey(rid);
    }

    @Override
    @CacheEvict(value = "permission", allEntries = true)
    public void delete(Long rid) {
        permissionMapper.deleteByPrimaryKey(rid);
    }

    @Override

    public List<PermissionVo> permissionTree() {
        List<PermissionVo> permissionVoList = this.AllPermissionCache();

        //取出父级、子集和父级编码进行递归
        List<PermissionVo> parentPermission = permissionVoList.stream().filter(p->p.getParentId()==null || p.getParentId()==0).collect(Collectors.toList());
        List<PermissionVo> childPermission = permissionVoList.stream().filter(p->p.getParentId()!=null && p.getParentId()!=0).collect(Collectors.toList());
        List<Long> childPermissionId=childPermission.stream().map(p->p.getParentId()).collect(Collectors.toList());
        this.doHandleTreeData(parentPermission,childPermission,childPermissionId);
        return parentPermission;
    }

    @Override
    @Cacheable(value = "permission", key = "'ALL_PERMISSION'")
    public List<PermissionVo> AllPermissionCache() {
        return permissionMapper.list(null);
    }

    @Override
    public List<PermissionVo> permissionByUser(PermissionSearchCondition cond) {
        return permissionMapper.permissionByUser(cond);
    }

    @Override
    public boolean needPermissionByUrl(String permissionUrl, String requestMethod, String pcMenuUrl) {
        List<PermissionVo> permissionVos = AllPermissionCache();
       return permissionVos.stream().filter(p->
                        StringUtil.isNotEmpty(permissionUrl)?permissionUrl.equals(p.getUrl()): 1==1 &&
                        StringUtil.isNotEmpty(requestMethod)?requestMethod.equals(p.getRequestMethod()): 1==1 &&
                        StringUtil.isNotEmpty(pcMenuUrl)?pcMenuUrl.equals(p.getPcMenuUrl()):1==1
        ).collect(Collectors.toList()).size()>0;
    }

    @Override
    public boolean hasPermission(Long uid, Long merchantId, String permissionUrl, String requestMethod, String pcMenuUrl) {
        return permissionMapper.hasPermission(uid,merchantId,permissionUrl,requestMethod,pcMenuUrl)>0;
    }

    @Override
    public PermissionVo findPermissionByUrl(String permissionUrl, String requestMethod) {
        List<PermissionVo> permissionVos = AllPermissionCache();
        PermissionVo result = null;
        List<PermissionVo> collect = permissionVos.stream().filter(p ->
                StringUtil.isNotEmpty(permissionUrl) ? permissionUrl.equals(p.getUrl()) : 1 == 1 &&
                        StringUtil.isNotEmpty(requestMethod) ? requestMethod.equals(p.getRequestMethod()) : 1 == 1
        ).collect(Collectors.toList());
        if(collect != null && collect.size()>0){
            result=collect.get(0);
        }
        return result;
    }

    /**
     * 处理权限菜单树形数据
     *
     * @param source
     * @return
     */
    private void doHandleTreeData(List<PermissionVo> source, List<PermissionVo> addData,
                                  List<Long> resultPermissionIds) {

        // 需要继续递归的权限组
        List<PermissionVo> childrenPermission = new ArrayList<PermissionVo>();

        // 取出当前级别权限ids
        List<Long> sourceIds = source.stream().map(p -> p.getId()).collect(Collectors.toList());
        for (int i = 0; i < sourceIds.size(); i++) {
            Long permissionId = sourceIds.get(i);

            // 将配置的下级加入下级属性中
            for (int j = 0; j < resultPermissionIds.size(); j++) {
                Long ParentIdpermission = resultPermissionIds.get(j);
                if (ParentIdpermission.equals(permissionId)) {
                    source.get(i).getChildPermissionList().add(addData.get(j));

                    // 如果该层级还有下级
                    if (addData.get(j).getHasNext()) {
                        childrenPermission.add(addData.get(j));
                    }
                }
            }

        }
        // 需要递归的下级权限数据
        if (childrenPermission.size() > 0) {
            doHandleTreeData(childrenPermission, addData, resultPermissionIds);
        }
    }
}
