package com.maxrocky.gundam.domain.demo.repositoryImpl;

import com.maxrocky.gundam.domain.demo.model.Demo;
import com.maxrocky.gundam.domain.demo.repository.DemoRepository;
import com.maxrocky.gundam.hibernate.BaseRepositoryImpl;
import com.maxrocky.gundam.page.PageInfoTools;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom on 2016/5/9 15:56.
 * Describe:The demo repositoryImpl.
 */
@Repository
public class DemoRepositoryImpl extends BaseRepositoryImpl<Demo> implements DemoRepository {

    /**
     * Describe:Return the Demo according to name.
     * CreateBy:Tom
     * CreateOn:2016-05-27 03:08:53
     */
    @Override
    public Demo getByName(String name) {
        List<Criterion> sql = new ArrayList<Criterion>();
        sql.add(Restrictions.eq("demoName", name));
        return this.findUniqueResult(sql);
    }

    /**
     * Describe:Return the Demo List according to name.
     * CreateBy:Tom
     * CreateOn:2016-05-27 03:08:53
     */
    @Override
    public List<Demo> getList(String name) {
        List<Criterion> sql = new ArrayList<Criterion>();
        sql.add(Restrictions.eq("demoName", name));
        return this.findByCriteria(sql, Order.desc("createOn"));
    }

    /**
     * Describe:Return count.
     * CreateBy:Tom
     * CreateOn:2016-05-31 12:10:17
     */
    @Override
    public int getCount() {
        return this.findByCriteriaForPageCount();
    }

    /**
     * Describe:Return the Demo page List.
     * CreateBy:Tom
     * CreateOn:2016-05-27 03:08:53
     */
    @Override
    public List<Demo> getList(PageInfoTools pageInfoTools) {
        return this.findByCriteriaForPage(pageInfoTools);
    }

    /**
     * Describe:Return the Demo List by hql.
     * CreateBy:Tom
     * CreateOn:2016-06-01 16:53:54
     */
    @Override
    public List<Object> getListByHql() {
        List<Object> params = new ArrayList<Object>();
        String hql = "FROM Demo WHERE name = ?";
        params.add("tom");
        return this.findObjectByQueryList(hql, new PageInfoTools(), params);
    }
}
