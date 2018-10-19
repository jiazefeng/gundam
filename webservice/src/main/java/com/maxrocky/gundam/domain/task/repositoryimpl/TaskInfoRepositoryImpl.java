package com.maxrocky.gundam.domain.task.repositoryimpl;

import com.maxrocky.gundam.domain.task.model.TaskInfo;
import com.maxrocky.gundam.domain.task.repository.TaskInfoRepository;
import com.maxrocky.gundam.hibernate.BaseRepositoryImpl;
import com.maxrocky.gundam.page.PageInfoTools;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
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
public class TaskInfoRepositoryImpl extends BaseRepositoryImpl<TaskInfo> implements TaskInfoRepository {
    @Autowired
    @Qualifier("sessionFactory")
    SessionFactory sessionFactory;

    Session getCurrentSession() {
        return sessionFactory.openSession();
    }

    @Override
    public boolean Create(TaskInfo entity) {
        if (entity != null) {
            getCurrentSession().save(entity);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void Update(TaskInfo entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public boolean Delete(int key) {
        if (key > 0) {
            Session s = getCurrentSession();
            TaskInfo object = (TaskInfo) s.load(TaskInfo.class, key);
            s.delete(object);
            s.flush();
            return true;
        }
        return false;

    }

    @Override
    public boolean updateTaskInfo(TaskInfo taskInfo) {
        if (taskInfo != null) {
            this.update(taskInfo);
            return true;
        }
        return false;
    }

    @Override
    public void BatchDelete(int mapId) {
        Session s = getCurrentSession();
//        Transaction tx = s.beginTransaction();
        String hql = "delete " + TaskInfo.class.getName() + " where map_id = :id";
        s.createQuery(hql).setParameter("id", mapId).executeUpdate();
//        tx.commit();
    }

    @Override
    public TaskInfo Retrive(int key) {
        return (TaskInfo) getCurrentSession().get(TaskInfo.class, key);
    }

    @Override
    public List<TaskInfo> ListByMap(int id) {
        return getCurrentSession()
                .createQuery("from TaskInfo where id = :id").setParameter("id", id)
                .list();

    }

    @Override
    public List<TaskInfo> ListByVillageId(String villageId) {
        String sql = "from TaskInfo as ti where 1=1";
        List<Object> params = new ArrayList<Object>();
        if (villageId != null && !villageId.equals("")) {
            sql += " AND ti.villageId = ?";
            params.add(villageId);
        }
        sql += " ORDER BY modifyOn DESC";
        List<TaskInfo> taskInfoList = findByQueryList(sql, new PageInfoTools(), params);
        return taskInfoList;

//        if (villageId != null && !villageId.equals("")) {
//            return getCurrentSession()
//                    .createQuery("from TaskInfo where village_id = :id").setParameter("id", villageId)
//                    .list();
//        } else {
//            return getCurrentSession()
//                    .createQuery("from TaskInfo").list();
//        }
    }

    @Override
    public List<TaskInfo> ListByItem(String villageId, int startRow) {
        String sql = "from TaskInfo as ti where 1=1";
        List<Object> params = new ArrayList<Object>();
        if (villageId != null && !villageId.equals("")) {
            sql += " AND ti.villageId = ?";
            params.add(villageId);
        }
        sql += " ORDER BY modifyOn DESC";
        List<TaskInfo> taskInfoList = findByQueryList(sql, new PageInfoTools(startRow, 10), params);
        return taskInfoList;
    }

    @Override
    public int totalByVillageId(String villageId) {
        List<Criterion> sql = new ArrayList<Criterion>();

        if (villageId != null && !villageId.equals("")) {
            sql.add(Restrictions.eq("villageId", villageId));
        }
        return this.findByCriteriaForPageCount(sql);
    }

    @Override
    public List<TaskInfo> ListByIds(List<Integer> taskIds) {
        List<TaskInfo> ret = new ArrayList<>();
        if (taskIds != null && taskIds.size() > 0) {
            List<TaskInfo> all = getCurrentSession().createQuery("from TaskInfo").list();
            ret = all.stream().filter(f -> taskIds.contains(f.getId()))
                    .collect(Collectors.toList());
        }
        return ret;
    }


}
