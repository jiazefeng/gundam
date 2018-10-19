package com.maxrocky.gundam.domain.role.repositoryImpl;
;
import com.maxrocky.gundam.domain.role.model.Viewmodel;
import com.maxrocky.gundam.domain.role.repository.ViewModelRepository;
import com.maxrocky.gundam.hibernate.BaseRepositoryImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhipeng on 2016/5/31.
 */
@Repository
public class ViewModelRepositoryImpl extends BaseRepositoryImpl<Viewmodel> implements ViewModelRepository {
    public List<Viewmodel> getViewListByUserId(String userid){
        if(userid==null || "".equals(userid.trim())){
            return null;
        }
        List<Object> params = new ArrayList<Object>();
        String hql= "FROM Viewmodel WHERE menulevel= ? and  menuId IN  (" +
                "SELECT DISTINCT e.menuId FROM  Roleset a,Userroleauthority b,Rolesetmap c,Role d ,Rolebuttonmap e" +
                " WHERE a.setId = b.roleId AND a.setId = c.rolesetid AND c.roleid = d.roleId  AND d.roleId=e.roleId" +
                " AND b.userId = ? and d.state='1') order by menuorder";
        params.add("1");
        params.add(userid);
        return this.findByQueryList(hql, params);
    }

    @Override
    public List<Viewmodel> getViewLisOthertByUserId(String userid,String menuId) {
        if(userid==null || "".equals(userid.trim())){
            return null;
        }
        List<Object> params = new ArrayList<Object>();
        String hql= "FROM Viewmodel WHERE menulevel = ? and parantmenuid = ? and  menuId IN  (" +
                "SELECT DISTINCT e.menuId FROM  Roleset a,Userroleauthority b,Rolesetmap c,Role d ,Rolebuttonmap e" +
                " WHERE a.setId = b.roleId AND a.setId = c.rolesetid AND c.roleid = d.roleId  AND d.roleId=e.roleId" +
                " AND b.userId = ? and a.setState='01')  order by menuId,menuorder";
        params.add("2");
        params.add(menuId);
        params.add(userid);
        List<Viewmodel> viewmodels = this.findByQueryList(hql, params);
        if(viewmodels==null || viewmodels.size()<=0){
            return null;
        }
        return viewmodels;
    }
    @Override
    public List<Viewmodel> getViewLisThreeByUserId(String userid,String menuId) {
        if(userid==null || "".equals(userid.trim())){
            return null;
        }
        List<Object> params = new ArrayList<Object>();
        String hql= "FROM Viewmodel WHERE menulevel = ? and owner = ? and  menuId IN  (" +
                "SELECT DISTINCT e.menuId FROM  Roleset a,Userroleauthority b,Rolesetmap c,Role d ,Rolebuttonmap e" +
                " WHERE a.setId = b.roleId AND a.setId = c.rolesetid AND c.roleid = d.roleId  AND d.roleId=e.roleId" +
                " AND b.userId = ? and a.setState='01')  order by menuId,menuorder";
        params.add("3");
        params.add(menuId);
        params.add(userid);
        List<Viewmodel> viewmodels = this.findByQueryList(hql, params);
        if(viewmodels==null || viewmodels.size()<=0){
            return null;
        }
        return viewmodels;
    }
    @Override
    public List<Viewmodel> getViewModel(){
        List<Object> params = new ArrayList<Object>();
        String sql = "from Viewmodel where menulevel = ? ";
        params.add("1");
       return this.findByQueryList(sql, params);
    }
    @Override
    public List<Viewmodel> getViewLisOthertBymenuId(String menuId){
        List<Object> params = new ArrayList<Object>();
        String sql = "from Viewmodel where menulevel <> 1 and parantmenuid = ?";
        params.add(menuId);
        return this.findByQueryList(sql, params);
    }

}
