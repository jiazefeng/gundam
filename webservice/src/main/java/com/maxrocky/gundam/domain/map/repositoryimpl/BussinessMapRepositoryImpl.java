package com.maxrocky.gundam.domain.map.repositoryimpl;

import com.maxrocky.gundam.domain.map.model.BussinessMap;
import com.maxrocky.gundam.domain.map.repository.BussinessMapRepository;
import com.maxrocky.gundam.domain.strategy.model.Strategy;
import com.maxrocky.gundam.hibernate.BaseRepositoryImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuer5 on 16/5/18.
 */
@Repository
public class BussinessMapRepositoryImpl extends BaseRepositoryImpl<BussinessMap> implements BussinessMapRepository {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

//    @Autowired
//    private MapperFacade mapper = null;

    private Session getCurrentSession() {
//        return sessionFactory.openSession();
        return sessionFactory.getCurrentSession();
    }

    @Override
    public boolean Add(BussinessMap object) {
        if(object != null){
            Session s = getCurrentSession();
            s.save(object);
            s.flush();
            return  true;
        }
        return false;
    }

    @Override
    public void Update(BussinessMap object) {
//        mapper.map(object, BussinessMap.class);
        Session s = getCurrentSession();
        s.update(object);
        s.flush();
    }

    @Override
    public void Del(int id) {
        BussinessMap objToDel = Get(id);
        if (objToDel != null) {
            objToDel.setIsDelete(1);
            getCurrentSession().update(objToDel);
        }
    }

    @Override
    public BussinessMap Get(int id) {
        return (BussinessMap) getCurrentSession().get(BussinessMap.class, id);
    }

    @Override
    public BussinessMap GetByVillageId(String villageId) {

//        return (BussinessMap)getCurrentSession()
//                .createQuery("from bussiness_map where map_bid=:id and is_delete=0").setParameter("id", bid)
//                .uniqueResult();

        return (BussinessMap) getCurrentSession()
                .createQuery("from BussinessMap where village_id=:id and is_delete=0").setParameter("id", villageId)
                .uniqueResult();
    }

    @Override
    public List<BussinessMap> getBussinessMapByVillageId(String villageId) {
        String sql=" from BussinessMap where is_delete=0";
        List<Object> params = new ArrayList<Object>();
        if (villageId != null && !villageId.equals("")) {
            sql += " AND villageId=?";
            params.add(villageId);
        }
        sql+=" ORDER BY createTime DESC";
        List<BussinessMap> bussinessMapList = findByQueryList(sql,params);
        return bussinessMapList;
    }

}
