package com.maxrocky.gundam.domain.user.repository;

import com.maxrocky.gundam.domain.role.model.Rolesetmap;
import com.maxrocky.gundam.hibernate.BaseRepository;

/**
 * Created by lizhipeng on 2016/6/2.
 */
public interface RoleSetMapRepository extends BaseRepository<Rolesetmap> {
    public boolean addRoleSetMap(Rolesetmap rolesetmap);
}
