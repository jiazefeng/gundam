package com.maxrocky.gundam.domain.role.repository;

import com.maxrocky.gundam.domain.role.model.Rolebuttonmap;
import com.maxrocky.gundam.hibernate.BaseRepository;

import java.util.List;

/**
 * Created by lizhipeng on 2016/6/6.
 */
public interface RoleButtonMapRepository extends BaseRepository<Rolebuttonmap> {
    public boolean addRoleButtonMap(List<Rolebuttonmap> list);
    //删除角色功能
    public boolean deleteRoleButtonMap(String roleId);
    //通过角色ID查询button
    public List<Rolebuttonmap> getButtonList(String roleId);
}
