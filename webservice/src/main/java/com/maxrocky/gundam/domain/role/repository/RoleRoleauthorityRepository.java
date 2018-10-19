package com.maxrocky.gundam.domain.role.repository;

import com.maxrocky.gundam.domain.role.model.Userroleauthority;
import com.maxrocky.gundam.hibernate.BaseRepository;

/**
 * Created by lizhipeng on 2016/6/6.
 */
public interface RoleRoleauthorityRepository extends BaseRepository<Userroleauthority> {
    //修改用户的功能
    public boolean updateRoleSet(Userroleauthority userroleauthority);
    //通过用户ID查询用户的功能
    public Userroleauthority getRoleauthorityById(String userId);

    public void AddRoleForUser(Userroleauthority userroleauthority);
    public boolean addRoleForUser(Userroleauthority userroleauthority);

}
