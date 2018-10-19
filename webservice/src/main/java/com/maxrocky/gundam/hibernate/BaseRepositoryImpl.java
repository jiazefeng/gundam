package com.maxrocky.gundam.hibernate;

import com.maxrocky.gundam.page.PageInfoTools;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tom on 2016/5/9 15:00.
 * Describe:
 */
public class BaseRepositoryImpl<PO extends BaseVO> implements BaseRepository<PO> {

    private Class<PO> persistentClass;

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    protected Session createSession() {
        return sessionFactory.openSession();
    }
    public Class<PO> getPersistentClass() {
        if (this.persistentClass == null) {
            this.persistentClass = (Class<PO>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return persistentClass;
    }


    @Override
    public void flush() {
        getSession().flush();
    }

    @Override
    public void clear() {
        getSession().clear();
    }

    @Override
    public PO get(String id) {
        return (PO) getSession().get(getPersistentClass(), id);
    }

    @Override
    public List<PO> getAll() {
        return this.findByCriteria();
    }

    @Override
    public void save(PO entity) {
        getSession().save(entity);
    }

    @Override
    public void update(PO entity) {
        getSession().update(entity);
    }

    @Override
    public void saveOrUpdate(PO entity) {
        getSession().saveOrUpdate(entity);
    }

    @Override
    public void deletePhysical(PO obj) {
        getSession().delete(obj);
    }

    protected int deleteListPhysical(String hql,List<Object> params) {
        if (StringUtils.isEmpty(hql)) {
            throw new IllegalStateException("queryString is null");
        }
        Query query = getSession().createQuery(hql);

        if (!ObjectUtils.isEmpty(params)) {
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i, params.get(i));
            }
        }

        return query.executeUpdate();
    }
    protected List<PO> findByCriteria(Criterion... criterion) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        for (Criterion c : criterion) {
            if (c != null) {
                criteria.add(c);
            }
        }
        return criteria.list();
    }

    protected List<PO> findByCriteria(List<Criterion> criterion, Order... order) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        for (Criterion c : criterion) {
            if (c != null)
                criteria.add(c);
        }
        for (Order o : order) {
            if (o != null)
                criteria.addOrder(o);
        }
        return criteria.list();
    }

    protected PO findUniqueResult(Criterion... criterion) {
        return findUniqueResult(Arrays.asList(criterion));
    }

    protected PO findUniqueResult(List<Criterion> criterion, Order... order) {
        Criteria criteria = createSession().createCriteria(getPersistentClass());
        for (Criterion c : criterion) {
            if (c != null)
                criteria.add(c);
        }
        for (Order o : order) {
            if (o != null)
                criteria.addOrder(o);
        }
        return (PO) criteria.uniqueResult();
    }

    protected int findByCriteriaForPageCount(Criterion... criterion) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        for (Criterion c : criterion) {
            if (c != null)
                criteria.add(c);
        }
        criteria.setProjection(Projections.rowCount());
        return Integer.parseInt(criteria.uniqueResult().toString());
    }

    protected int findByCriteriaForPageCount(List<Criterion> criterion) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        for (Criterion c : criterion) {
            if (c != null)
                criteria.add(c);
        }
        criteria.setProjection(Projections.rowCount());
        return Integer.parseInt(criteria.uniqueResult().toString());
    }

    protected int findByCriteriaForPageCount(String hql,List<Object> params) {
        if (StringUtils.isEmpty(hql)) {
            throw new IllegalStateException("queryString is null");
        }
        Query query = getSession().createQuery(hql);

        if (!ObjectUtils.isEmpty(params)) {
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i, params.get(i));
            }
        }

        return Integer.parseInt(query.uniqueResult().toString());
    }

    protected List<PO> findByCriteriaForPage(PageInfoTools pageInfoTools) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.setFirstResult(pageInfoTools.getCount());
        if (pageInfoTools.getSize() >= 0) {
            criteria.setMaxResults(pageInfoTools.getSize());
        }
        return criteria.list();
    }

    protected List<PO> findByCriteriaForPage(PageInfoTools pageInfoTools, List<Criterion> criterion) {
        return findByCriteriaForPage(pageInfoTools, criterion, Order.desc("id"));
    }

    protected List<PO> findByCriteriaForPage(PageInfoTools pageInfoTools, List<Criterion> criterion, Order... order) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        for (Criterion c : criterion) {
            if (c != null)
                criteria.add(c);
        }
        for (Order o : order) {
            if (o != null)
                criteria.addOrder(o);
        }
        criteria.setFirstResult(pageInfoTools.getCount());
        if (pageInfoTools.getSize() >= 0) {
            criteria.setMaxResults(pageInfoTools.getSize());
        }
        return criteria.list();
    }

    protected PO findByQueryResult(String hql, List<Object> params){
        return (PO)this.findObjectByQueryResult(hql, params);
    }

    protected Object findObjectByQueryResult(String hql, List<Object> params){
        if (StringUtils.isEmpty(hql)) {
            throw new IllegalStateException("queryString is null");
        }
        Query query = getSession().createQuery(hql);
        if (!ObjectUtils.isEmpty(params)) {
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i, params.get(i));
            }
        }
        return query.uniqueResult();
    }

    protected List<PO> findByQueryList(String hql, PageInfoTools pageInfoTools, List<Object> params){
        if (StringUtils.isEmpty(hql)) {
            throw new IllegalStateException("queryString is null");
        }
        Query query = getSession().createQuery(hql);
        if (!ObjectUtils.isEmpty(params)) {
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i, params.get(i));
            }
        }
        query.setFirstResult(pageInfoTools.getCount());
        query.setMaxResults(pageInfoTools.getSize());
        return (List<PO>)query.list();
    }
    protected List<PO> findByQueryList(String hql,List<Object> params){
        if (StringUtils.isEmpty(hql)) {
            throw new IllegalStateException("queryString is null");
        }
        Query query = getSession().createQuery(hql);
        if (!ObjectUtils.isEmpty(params)) {
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i, params.get(i));
            }
        }
        return (List<PO>)query.list();
    }

    protected List<Object> findObjectByQueryList(String hql, PageInfoTools pageInfoTools, Object... params){
        if (StringUtils.isEmpty(hql)) {
            throw new IllegalStateException("queryString is null");
        }
        Query query = getSession().createQuery(hql);
        if (!ObjectUtils.isEmpty(params)) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i, params[i]);
            }
        }
        query.setFirstResult(pageInfoTools.getCount());
        query.setMaxResults(pageInfoTools.getSize());
        return (List<Object>)query.list();
    }

}
