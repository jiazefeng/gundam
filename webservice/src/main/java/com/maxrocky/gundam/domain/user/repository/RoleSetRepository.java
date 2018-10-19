package com.maxrocky.gundam.domain.user.repository;

import com.maxrocky.gundam.domain.role.model.Roleset;
import com.maxrocky.gundam.domain.role.model.Rolesetmap;
import com.maxrocky.gundam.hibernate.BaseRepository;

/**
 * Created by lizhipeng on 2016/6/2.
 */
public interface RoleSetRepository extends BaseRepository<Roleset> {
    //通过角色查询权限
    public Rolesetmap getRolesetIdByRoleId(String roleId);
    public boolean addRoleSet(Roleset roleset);
}
