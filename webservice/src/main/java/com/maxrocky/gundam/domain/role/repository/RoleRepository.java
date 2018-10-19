package com.maxrocky.gundam.domain.role.repository;

import com.maxrocky.gundam.domain.role.model.Role;
import com.maxrocky.gundam.hibernate.BaseRepository;

import java.util.List;

/**
 * Created by lizhipeng on 2016/6/2.
 */
public interface RoleRepository extends BaseRepository<Role> {
    //获取所有的角色
    public List<Role> getRoleList();
    //通过角色Id查询角色
    public Role getRoleById(String roleId);
    //获取角色(分页展示)
    public List<Role> getRole();
    //获取角色总数量
    public int getRoleCount();
    //添加角色
    public boolean addRole(Role role);
    //修改角色
    public boolean updateRole(Role role);
    //分页查询角色
    public List<Role> searchRole(int startRow);
    //根据角色级别查询角色
    public List<Role> inquireRole(int degree);
    public Role searRoleInfo(String userId);
}
