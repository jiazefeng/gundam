package com.maxrocky.gundam.service.role.inf;


import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.domain.role.dto.RoleDTO;
import com.maxrocky.gundam.page.PageInfoTools;

/**
 * Created by lizhipeng on 2016/6/6.
 */
public interface RoleService {
    public ApiResult getRoleList();
    public ApiResult initAddRole();
    public ApiResult addRole(String villageId, RoleDTO roleDTO, String userInfo);
    public ApiResult searchRole(PageInfoTools PageInfoTools);
    //通过Id获取角色
    public ApiResult getRoleById(String id);
    //修改角色
    public ApiResult updateRole(RoleDTO roleDTO);
    //初始化授权界面
    public ApiResult initAccredit(String id);
}
