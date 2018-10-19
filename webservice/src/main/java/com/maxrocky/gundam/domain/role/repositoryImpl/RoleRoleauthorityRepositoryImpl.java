package com.maxrocky.gundam.domain.role.repositoryImpl;

import com.maxrocky.gundam.domain.role.model.Userroleauthority;
import com.maxrocky.gundam.domain.role.repository.RoleRoleauthorityRepository;
import com.maxrocky.gundam.hibernate.BaseRepositoryImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhipeng on 2016/6/6.
 */
@Repository
public class RoleRoleauthorityRepositoryImpl extends BaseRepositoryImpl<Userroleauthority> implements RoleRoleauthorityRepository {
    @Override
    public boolean updateRoleSet(Userroleauthority userroleauthority) {
        if (userroleauthority != null) {
            this.update(userroleauthority);
            return true;
        }
        return false;
    }
    @Override
    public Userroleauthority getRoleauthorityById(String userID){
        List<Object> params = new ArrayList<Object>();
        String sql = "from Userroleauthority where userId = ?";
        params.add(userID);
        return (Userroleauthority)this.findObjectByQueryResult(sql,params);
    }
    @Override
    public void AddRoleForUser(Userroleauthority userroleauthority){
        this.save(userroleauthority);
    }

    @Override
    public boolean addRoleForUser(Userroleauthority userroleauthority) {
        if(userroleauthority != null){
            this.save(userroleauthority);
            return true;
        }
        return false;
    }

}
