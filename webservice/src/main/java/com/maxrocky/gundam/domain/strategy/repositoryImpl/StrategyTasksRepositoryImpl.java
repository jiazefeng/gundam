package com.maxrocky.gundam.domain.strategy.repositoryImpl;

import com.maxrocky.gundam.domain.strategy.model.StrategyTasks;
import com.maxrocky.gundam.domain.strategy.repository.StrategyTasksRepository;
import com.maxrocky.gundam.domain.task.model.TaskInfo;
import com.maxrocky.gundam.hibernate.BaseRepositoryImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yuer5 on 16/6/9.
 */
@Repository
public class StrategyTasksRepositoryImpl extends BaseRepositoryImpl<StrategyTasks> implements StrategyTasksRepository {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.openSession();
    }

    @Override
    public TaskInfo getTaskInfoById(int id) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from TaskInfo where id = ?";
        params.add(id);
        return (TaskInfo) this.findObjectByQueryResult(sql, params);
    }

    @Override
    public List<TaskInfo> getTaskInfoByVillageId(String villageId) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from TaskInfo as ti where 1=1";
        if (villageId != null && !villageId.equals("")) {
//            sql += " and ti.villageId in (select v.mapId FROM Village as v where v.villageId = ?)";
            sql += " and ti.villageId=?";
            params.add(villageId);
        }

        List tasksList = this.findByQueryList(sql, params);
        return tasksList;
    }

    @Override
    public List<TaskInfo> getTaskInfoList(String strategyId) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from TaskInfo as t where  t.id in (select st.taskId from StrategyTasks st where st.strategyId = ? ORDER BY orderly ASC)";
        params.add(strategyId);
        List tasksList = this.findByQueryList(sql, params);
        return tasksList;
    }

    @Override
    public List<StrategyTasks> getStrategyTasks(String strategyId) {
        List<Object> params = new ArrayList<Object>();
        String sql = "from StrategyTasks st where st.strategyId = ?";
        params.add(strategyId);
        sql += " ORDER BY orderly ASC";
        List<StrategyTasks> list = this.findByQueryList(sql, params);
        return list;
    }

    @Override
    public boolean addStrategyTask(List<StrategyTasks> strategyTaskses) {
        if (strategyTaskses.size() > 0) {
            for (StrategyTasks strategyTasks : strategyTaskses) {
                this.save(strategyTasks);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean updateStrategyTask(List<StrategyTasks> strategyTaskses) {
        if (strategyTaskses.size() > 0) {
            for (StrategyTasks strategyTasks : strategyTaskses) {
                this.update(strategyTasks);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteStrategyTask(List<StrategyTasks> strategyTaskses) {
        if (strategyTaskses.size() > 0) {
            for (StrategyTasks strategyTasks : strategyTaskses) {
                this.deletePhysical(strategyTasks);
            }
            return true;
        }
        return false;
    }

    @Override
    public void Create(StrategyTasks entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void Update(StrategyTasks entity) {
        Session s = getCurrentSession();
        s.update(entity);
        s.flush();
    }

    @Override
    public void Delete(int key) {
        Session s = getCurrentSession();
        StrategyTasks object = (StrategyTasks) s.load(StrategyTasks.class, key);
        s.delete(object);
        s.flush();
    }

    @Override
    public void BatchDelete(String strategyId) {
        Session s = getCurrentSession();
//        Transaction tx = s.beginTransaction();
        String hql = "delete " + StrategyTasks.class.getName() + " where strategy_id = :id";
        s.createQuery(hql).setParameter("id", strategyId).executeUpdate();
//        tx.commit();
    }

    @Override
    public void BatchDeleteByMap(int mapId) {
        Session s = getCurrentSession();
//        Transaction tx = s.beginTransaction();
        String hql = "delete " + StrategyTasks.class.getName() + " where map_id = :id";
        s.createQuery(hql).setParameter("id", mapId).executeUpdate();
//        tx.commit();
    }

    @Override
    public StrategyTasks Retrive(int key) {
        return (StrategyTasks) getCurrentSession().get(StrategyTasks.class, key);
    }

    @Override
    public List<StrategyTasks> List(int strategyId) {
        return getCurrentSession()
                .createQuery("from StrategyTasks where strategy_id = :id").setParameter("id", strategyId)
                .list();
    }

    @Override
    public boolean IsRemainTask(int taskId) {
        int count = getCurrentSession()
                .createQuery("from StrategyTasks where task_id = :id").setParameter("id", taskId)
                .list().size();
        return count > 0;
    }

    @Override
    public boolean IsRemainTaskIds(List<Integer> taskIds) {

        List<StrategyTasks> ret = new ArrayList<>();
        if (taskIds != null && taskIds.size() > 0) {
            List<StrategyTasks> all = getCurrentSession().createQuery("from StrategyTasks").list();
            ret = all.stream().filter(f -> taskIds.contains(f.getTaskId()))
                    .collect(Collectors.toList());
        }
        return ret.size() > 0;
    }

}
