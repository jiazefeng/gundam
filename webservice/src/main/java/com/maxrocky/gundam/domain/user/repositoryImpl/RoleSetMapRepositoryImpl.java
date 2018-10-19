package com.maxrocky.gundam.domain.user.repositoryImpl;

import com.maxrocky.gundam.domain.role.model.Rolesetmap;
import com.maxrocky.gundam.domain.user.repository.RoleSetMapRepository;
import com.maxrocky.gundam.hibernate.BaseRepositoryImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by lizhipeng on 2016/6/2.
 */
@Repository
public class RoleSetMapRepositoryImpl extends BaseRepositoryImpl<Rolesetmap> implements RoleSetMapRepository {
    @Override
    public boolean addRoleSetMap(Rolesetmap rolesetmap) {
        if (rolesetmap != null) {
            this.save(rolesetmap);
            return true;
        }
        return false;
    }
}
