package com.maxrocky.gundam.domain.user.repositoryImpl;

import com.maxrocky.gundam.domain.role.model.Roleset;
import com.maxrocky.gundam.domain.role.model.Rolesetmap;
import com.maxrocky.gundam.domain.user.repository.RoleSetRepository;
import com.maxrocky.gundam.hibernate.BaseRepositoryImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhipeng on 2016/6/2.
 */
@Repository
public class RoleSetRepositoryImpl extends BaseRepositoryImpl<Roleset> implements RoleSetRepository {
    @Override
    public Rolesetmap getRolesetIdByRoleId(String roleId){
        List<Object> params = new ArrayList<Object>();
        String sql = "from Rolesetmap where roleid = ?";
        params.add(roleId);
        return (Rolesetmap)this.findObjectByQueryResult(sql,params);
    }
    @Override
    public boolean addRoleSet(Roleset roleset){
        if(roleset!=null){
            this.save(roleset);
            return true;
        }
        return false;
    }
}
