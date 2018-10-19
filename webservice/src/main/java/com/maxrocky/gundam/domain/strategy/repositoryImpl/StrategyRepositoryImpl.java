package com.maxrocky.gundam.domain.strategy.repositoryImpl;

import com.maxrocky.gundam.domain.strategy.model.Strategy;
import com.maxrocky.gundam.domain.strategy.repository.StrategyRepository;
import com.maxrocky.gundam.domain.strategy.dto.StrategyDTO;
import com.maxrocky.gundam.domain.village.model.Village;
import com.maxrocky.gundam.hibernate.BaseRepositoryImpl;
import com.maxrocky.gundam.page.PageInfoTools;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JIAZEFENG on 16/6/15.
 */
@Repository
public class StrategyRepositoryImpl extends BaseRepositoryImpl<Strategy> implements StrategyRepository {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.openSession();
    }

    @Override
    public List<Strategy> getStrategyList() {
        String sql = "from Strategy where state=?";
        List<Object> params = new ArrayList<Object>();
        params.add(1);
        sql += " ORDER BY modifyOn DESC";
        return this.findByQueryList(sql, new PageInfoTools(), params);
    }

    @Override
    public List<Strategy> getStrategy() {
        String sql = "from Strategy where state=?";
        List<Object> params = new ArrayList<Object>();
        params.add(1);
        sql += " ORDER BY modifyOn DESC";
        return this.findByQueryList(sql, params);
    }

    @Override
    public int getStrategyTotal() {
        List<Criterion> sql = new ArrayList<Criterion>();
        return this.findByCriteriaForPageCount(sql);
    }

    @Override
    public List<Strategy> getStrategyList(Strategy strategy, String villageId, int startRow) {
        String sql = "from Strategy as s where state=?";
        List<Object> params = new ArrayList<Object>();
        params.add(1);
        if (strategy.getsId() != null && !strategy.getsId().equals("")) {
            sql += " AND s.sId = '" + strategy.getsId() + "'";
        }

        if (villageId != null && !villageId.equals("")) {
            sql += " AND s.villageId =?";
            params.add(villageId);
        }

        sql += " ORDER BY modifyOn DESC";
        List<Strategy> strategyList = findByQueryList(sql, new PageInfoTools(startRow, 10), params);
        return strategyList;
    }

    @Override
    public List<Strategy> getStrategyList(String id) {
        String sql = "from Strategy as s where state=?";

        if (id != null && !id.equals("")) {
            sql += " AND s.sId = '" + id + "'";
        }
        List<Object> params = new ArrayList<Object>();
        params.add(1);
        sql += " ORDER BY modifyOn DESC";
        List<Strategy> strategyList = findByQueryList(sql, params);
        return strategyList;
    }


    @Override
    public List<Strategy> getStrategyListByVillage(String id) {
        String sql = "from Strategy as s where s.state=?";
        List<Object> params = new ArrayList<Object>();
        params.add(1);
        if (id != null && !id.equals("")) {
            sql += " AND s.villageId=?";
            params.add(id);
        }
//        if (id != null && !id.equals("")) {
//            sql += " AND s.mapId IN (select v.mapId FROM Village as v where  v.villageId =?)";
//            params.add(id);
//        }
        sql += " ORDER BY modifyOn DESC";
        List<Strategy> strategyList = findByQueryList(sql, new PageInfoTools(), params);
        return strategyList;
    }

    @Override
    public int getStrategyTotal(Strategy strategy) {
        List<Criterion> sql = new ArrayList<Criterion>();
        sql.add(Restrictions.eq("state", 1));
        if (strategy.getsId() != null && !strategy.getsId().equals("")) {
            sql.add(Restrictions.eq("sId", strategy.getsId()));
        }
        if (strategy.getVillageId() != null && !strategy.getVillageId().equals("")) {
            sql.add(Restrictions.eq("villageId", strategy.getVillageId()));
        }
        return this.findByCriteriaForPageCount(sql);
    }

    @Override
    public int getStrategyTotalByVillageId(String villageId) {
        List<Criterion> sql = new ArrayList<Criterion>();
        sql.add(Restrictions.eq("state", 1));
        if (villageId != null && !villageId.equals("")) {
            sql.add(Restrictions.eq("villageId", villageId));
        }
        return this.findByCriteriaForPageCount(sql);
    }


    @Override
    public void Create(Strategy entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void Update(Strategy entity) {
        Session s = getCurrentSession();
        s.update(entity);
        s.flush();
    }

    @Override
    public void Delete(String key) {
        Session s = getCurrentSession();
        Strategy object = (Strategy) s.load(Strategy.class, key);
        s.delete(object);
        s.flush();
    }

    @Override
    public void BatchDelete(int mapId) {
        Session s = getCurrentSession();
        Transaction tx = s.beginTransaction();
        String hql = "delete " + Strategy.class.getName() + " where map_id = :id";
        s.createQuery(hql).setParameter("id", mapId).executeUpdate();
        tx.commit();
    }

    @Override
    public Strategy Retrive(int key) {
        return (Strategy) getCurrentSession().get(Strategy.class, key);
    }

    @Override
    public List<Strategy> List(int mapId) {
        return getCurrentSession().createQuery("from Strategy where map_id = :id").setParameter("id", mapId).list();
    }

    @Override
    public Strategy getStrategyById(String id) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from Strategy where sId = ?";
        params.add(id);
        return (Strategy) this.findObjectByQueryResult(sql, params);
    }

    @Override
    public Strategy getSchemeById(String id) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from Strategy where schemeId = ?";
        params.add(id);
        return (Strategy) this.findObjectByQueryResult(sql, params);
    }

    @Override
    public boolean deleteStrategy(String id) {
        Strategy strategy = new Strategy();
        if (id != null && !id.equals("")) {
            strategy = getStrategyById(id);
            if (strategy != null) {
                strategy.setState(0);
                this.update(strategy);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean addStrategy(Strategy strategy) {
        if (strategy != null) {
            this.save(strategy);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateStrategy(Strategy strategy) {
        if (strategy != null) {
            this.update(strategy);
            return true;
        }
        return false;
    }

    @Override
    public int getMapIdByVillageId(String villageId) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from Village where villageId = ?";
        params.add(villageId);
        Village village = (Village) this.findObjectByQueryResult(sql, params);
        return village.getMapId();
    }
}
