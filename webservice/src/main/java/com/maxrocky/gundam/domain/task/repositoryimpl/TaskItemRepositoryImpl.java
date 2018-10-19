package com.maxrocky.gundam.domain.task.repositoryimpl;


import com.maxrocky.gundam.domain.task.model.TaskItem;
import com.maxrocky.gundam.domain.task.repository.TaskItemRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yuer5 on 16/6/9.
 */
@Repository
public class TaskItemRepositoryImpl implements TaskItemRepository {

    @Autowired
    @Qualifier("sessionFactory")
    SessionFactory sessionFactory;

    Session getCurrentSession() {
        return sessionFactory.openSession();
    }

    @Override
    public boolean Create(TaskItem entity) {
        if (entity != null) {
            getCurrentSession().save(entity);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void Update(TaskItem entity) {
        Session s = getCurrentSession();
        s.update(entity);
        s.flush();
    }

    @Override
    public void Delete(int key) {
        Session s = getCurrentSession();
        TaskItem object = (TaskItem) s.load(TaskItem.class, key);
        s.delete(object);
        s.flush();
    }
    @Override
    public void DeleteByTaskId(int taskId){
        Session s = getCurrentSession();
        String hql = "delete TaskItem where taskId = :taskId";
        s.createQuery(hql).setParameter("taskId", taskId).executeUpdate();
    }
    @Override
    public void BatchDeleteByMap(int mapId) {
        Session s = getCurrentSession();
//        Transaction tx = s.beginTransaction();
        String hql = "delete " + TaskItem.class.getName() + " where map_id = :id";
        s.createQuery(hql).setParameter("id", mapId).executeUpdate();
//        tx.commit();
    }

    @Override
    public boolean BatchDeleteByTask(int taskId) {
        if (taskId > 0) {
            Session s = getCurrentSession();
//        Transaction tx = s.beginTransaction();
            String hql = "delete " + TaskItem.class.getName() + " where task_id = :id";
            s.createQuery(hql).setParameter("id", taskId).executeUpdate();
//        tx.commit();
            return true;
        }
        return false;
    }

    @Override
    public TaskItem Retrive(int key) {
        return (TaskItem) getCurrentSession().get(TaskItem.class, key);
    }

    @Override
    public List<TaskItem> List(int taskId) {
        List<TaskItem> ret = sessionFactory.getCurrentSession()
                .createQuery("from TaskItem where task_id =:id").setParameter("id", taskId)
                .list();
        return ret;
    }
}
