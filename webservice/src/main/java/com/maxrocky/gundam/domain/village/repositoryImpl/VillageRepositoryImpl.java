package com.maxrocky.gundam.domain.village.repositoryImpl;


import com.maxrocky.gundam.domain.village.model.Village;
import com.maxrocky.gundam.domain.village.repository.VillageRepository;
import com.maxrocky.gundam.hibernate.BaseRepositoryImpl;
import com.maxrocky.gundam.page.PageInfoTools;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhipeng on 2016/5/24.
 */
@Repository
public class VillageRepositoryImpl extends BaseRepositoryImpl<Village> implements VillageRepository {

    @Override
    public List<Village> getVillage(String villageId){
        List<Object> params = new ArrayList<Object>();
        String sql = "from Village where state = ?";
        params.add(1);
        if(villageId!=null && !villageId.equals("")){
            sql+=" and villageId = ?";
            params.add(villageId);
        }
        sql +=" ORDER BY modifyDate DESC";
        List<Village> VillageList = this.findByQueryList(sql, new PageInfoTools(0,10), params);
//        Query query= session.createQuery(sql);
//        query.setParameter("state", 1);
//        List<Village> VillageList = query.list();
        return VillageList;
    }
    @Override
    public List<Village>  getVillageByItem(Village village,int startRow){
        List<Object> params = new ArrayList<Object>();
        String sql = "from Village where 1 = 1 and state = ?";
        params.add(1);
        if (!village.getVillageId().equals("")&&village.getVillageId()!=null){
            sql += " and villageId = '"+village.getVillageId()+"'";
        }
        if (village.getUserName()!=null&&!village.getUserName().equals("")){
            sql += " and userName = '"+village.getUserName()+"'";
        }
        sql +=" ORDER BY modifyDate DESC";
        List<Village> VillageList =  this.findByQueryList(sql, new PageInfoTools(startRow,10), params);
//        Session session = getSession();
//        Query query= session.createQuery(sql);
//        query.setParameter("state", 1);
//        List<Village> VillageList = query.list();
        return VillageList;
    }
    @Override
    public int getCount(String villageId){
        List<Criterion> sql = new ArrayList<Criterion>();
        sql.add(Restrictions.eq("state", 1));
        if(villageId!=null&&!villageId.equals("")) {
            sql.add(Restrictions.eq("villageId", villageId));
        }
        return this.findByCriteriaForPageCount(sql);
    }
    @Override
    public int getCount(Village village){
        String sql = "from Village where 1 = 1 and state = :state";
        if (!village.getVillageId().equals("")&&village.getVillageId()!=null){
            sql = sql + " and villageId = '"+village.getVillageId()+"'";
        }
        if (village.getUserName()!=null&&!village.getUserName().equals("")){
            sql= sql + " and userName = '"+village.getUserName()+ "'";
        }
        Session session = getSession();
        Query query= session.createQuery(sql);
        query.setParameter("state", 1);
        int count = query.list().size();
        return count;
    }
    @Override
    public boolean addVillage(Village village){
       this.save(village);
        return true;
    }
    @Override
    public boolean deleteVillage(String id){
        Village village = new Village();
        if(id!=null&&!id.equals("")){
            village= searchVillageById(id);
        }
        if(village!=null){
            village.setState(0);
            this.update(village);
            return true;
        }else{
            return false;
        }
    }
    @Override
    public Village searchVillageById(String id){
        List<Object> params = new ArrayList<Object>();
        String sql = "from Village where 1=1";
        if(id!=null&&!id.equals("")) {
            sql+=" and villageId = ?";
            params.add(id);
        }
        List<Village> list =  this.findByQueryList(sql,params);
        if(list.size()>0) {
            return list.get(0);
        }else{
            return null;
        }
    }
    @Override
    public boolean updateVillage(Village village){
        this.update(village);
        return true;
    }
    @Override
    public List<Village> getAllVillage(){
        List<Object> params = new ArrayList<Object>();
        String sql = "from Village where state = ?";
        params.add(1);
        List<Village> villages = this.findByQueryList(sql, params);
        return villages;
    }
}
