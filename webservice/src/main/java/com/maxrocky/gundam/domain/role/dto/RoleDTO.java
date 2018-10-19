package com.maxrocky.gundam.domain.role.dto;

import java.util.List;

/**
 * Created by lizhipeng on 2016/6/6.
 */
public class RoleDTO {
    private String roleId;
    private String roleName;
    private int state;
    private List viewModelList;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List getViewModelList() {
        return viewModelList;
    }

    public void setViewModelList(List viewModelList) {
        this.viewModelList = viewModelList;
    }
}
