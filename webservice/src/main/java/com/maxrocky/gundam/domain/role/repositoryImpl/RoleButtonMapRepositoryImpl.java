package com.maxrocky.gundam.domain.role.repositoryImpl;

import com.maxrocky.gundam.domain.role.model.Rolebuttonmap;
import com.maxrocky.gundam.domain.role.repository.RoleButtonMapRepository;
import com.maxrocky.gundam.hibernate.BaseRepositoryImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhipeng on 2016/6/6.
 */
@Repository
public class RoleButtonMapRepositoryImpl extends BaseRepositoryImpl<Rolebuttonmap> implements RoleButtonMapRepository {
    @Override
    public boolean addRoleButtonMap(List<Rolebuttonmap> list){
        if(list.size()>0){
            for (Rolebuttonmap rolebuttonmap : list) {
                this.save(rolebuttonmap);
            }
            return true;
        }
        return false;
    }
    @Override
    public boolean deleteRoleButtonMap(String roleId){
        String sql = "delete Rolebuttonmap where roleId = ?";
        List<Object> params = new ArrayList<Object>();
        params.add(roleId);
        this.deleteListPhysical(sql,params);
        return true;
    }
    @Override
    public List<Rolebuttonmap> getButtonList(String roleId){
        String sql = "from Rolebuttonmap where 1 = 1 ";
        List<Object> params = new ArrayList<Object>();
        if(roleId!=null&&!roleId.equals("")) {
            sql +="and roleId = ?";
            params.add(roleId);
        }
        return this.findByQueryList(sql,params);
    }
}
