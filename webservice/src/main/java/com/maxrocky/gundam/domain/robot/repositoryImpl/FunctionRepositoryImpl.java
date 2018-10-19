package com.maxrocky.gundam.domain.robot.repositoryImpl;

import com.maxrocky.gundam.domain.robot.model.Function;
import com.maxrocky.gundam.domain.robot.model.RobotInfo;
import com.maxrocky.gundam.domain.robot.repository.FunctionRepository;
import com.maxrocky.gundam.domain.robot.repository.RobotRepository;
import com.maxrocky.gundam.hibernate.BaseRepositoryImpl;
import com.maxrocky.gundam.page.PageInfoTools;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiazefeng on 2016/7/11.
 */
@Repository
public class FunctionRepositoryImpl extends BaseRepositoryImpl<Function> implements FunctionRepository {
    @Override
    public List<Function> getFunctionList() {
        List<Object> params = new ArrayList<Object>();
        String hql = " from Function";
        return this.findByQueryList(hql, params);
    }

    @Override
    public List<Function> getFunctionList(String id) {
        List<Object> params = new ArrayList<Object>();
        String hql = " from Function where fId=?";
        params.add(id);
        return this.findByQueryList(hql, params);
    }

    @Override
    public Function getFunctionById(String id) {
        String sql = "from Function as f where f.fId = ?";
        List<Object> params = new ArrayList<Object>();
        params.add(id);
        return (Function) this.findObjectByQueryResult(sql, params);
    }
}
