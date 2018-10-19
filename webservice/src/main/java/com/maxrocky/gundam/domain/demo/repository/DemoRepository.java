package com.maxrocky.gundam.domain.demo.repository;

import com.maxrocky.gundam.domain.demo.model.Demo;
import com.maxrocky.gundam.hibernate.BaseRepository;
import com.maxrocky.gundam.page.PageInfoTools;

import java.util.List;

/**
 * Created by Tom on 2016/5/6 13:59.
 * Describe:The demo repository.
 */
public interface DemoRepository extends BaseRepository<Demo> {


    /**
     * Describe:Return the Demo according to name.
     * CreateBy:Tom
     * CreateOn:2016-05-27 03:08:53
     */
    Demo getByName(String name);

    /**
     * Describe:Return the Demo List according to name.
     * CreateBy:Tom
     * CreateOn:2016-05-27 03:08:53
     */
    List<Demo> getList(String name);

    /**
     * Describe:Return count.
     * CreateBy:Tom
     * CreateOn:2016-05-31 12:10:17
     */
    int getCount();

    /**
     * Describe:Return the Demo page List.
     * CreateBy:Tom
     * CreateOn:2016-05-27 03:08:53
     */
    List<Demo> getList(PageInfoTools pageInfoTools);

    /**
     * Describe:Return the Demo List by hql.
     * CreateBy:Tom
     * CreateOn:2016-06-01 16:53:54
     */
    List<Object> getListByHql();
}
