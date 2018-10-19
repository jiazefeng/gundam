package com.maxrocky.gundam.domain.alarm.repositoryImpl;


import com.maxrocky.gundam.domain.alarm.model.AlarmInfo;
import com.maxrocky.gundam.domain.alarm.repository.AlarmInfoRepository;
import com.maxrocky.gundam.hibernate.BaseRepositoryImpl;
import com.maxrocky.gundam.page.PageInfoTools;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiazefeng on 2016/5/26.
 */
@Repository
public class AlarmInfoRepositoryImpl extends BaseRepositoryImpl<AlarmInfo> implements AlarmInfoRepository {

    //    @Autowired
//    private HibernateDaoImpl hibernateDao;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public List<AlarmInfo> getAlarmInfoList() {
        List<Object> params = new ArrayList<Object>();
        String hql = "from AlarmInfo where status = ?";
        params.add(1);
        hql += " ORDER BY alarmDate DESC";
        return this.findByQueryList(hql, new PageInfoTools(), params);
    }


    @Override
    public int getAlarmInfoCount() {
        List<Criterion> sql = new ArrayList<Criterion>();
        sql.add(Restrictions.eq("status", 1));
        return this.findByCriteriaForPageCount(sql);
    }

    @Override
    public List<AlarmInfo> getAlarmInfoList(AlarmInfo alarmInfo, int startRow) {

        List<Object> params = new ArrayList<Object>();
        String sql = "from AlarmInfo where 1 = 1 and status = ?";
        params.add(1);

        if (alarmInfo.getRobotId() != null && !alarmInfo.getRobotId().equals("")) {
            sql += " and robotId = '" + alarmInfo.getRobotId() + "'";
        }
        if (alarmInfo.getAlarmDate() != null && !alarmInfo.getAlarmDate().equals("")) {
            sql += " and alarmDate >= '" + formatter.format(alarmInfo.getAlarmDate()) + "'";
        }
        sql += " ORDER BY alarmDate DESC";
        List<AlarmInfo> alarmInfoList = this.findByQueryList(sql, new PageInfoTools(startRow, 10), params);

        return alarmInfoList;

    }

    @Override
    public int getCount(AlarmInfo alarmInfo) {
        List<Criterion> sql = new ArrayList<Criterion>();
        sql.add(Restrictions.eq("status", 1));

        if (alarmInfo.getRobotId() != null && !alarmInfo.getRobotId().equals("")) {
            sql.add(Restrictions.eq("robotId", alarmInfo.getRobotId()));
        }
        if (alarmInfo.getAlarmDate() != null && !alarmInfo.getAlarmDate().equals("")) {
            sql.add(Restrictions.ge("alarmDate", alarmInfo.getAlarmDate()));
        }
        return this.findByCriteriaForPageCount(sql);
    }

    @Override
    public List<AlarmInfo> getAlarmInfoList(AlarmInfo alarmInfo) {
        List<Object> params = new ArrayList<Object>();

        String sql = "from AlarmInfo where 1 = 1 and status = ?";
        if (alarmInfo.getRobotId() != null && !alarmInfo.getRobotId().equals("")) {
            sql += " and robotId = '" + alarmInfo.getRobotId() + "'";
        }
        if (alarmInfo.getAlarmDate() != null && !alarmInfo.getAlarmDate().equals("")) {
            sql += " and alarmDate >= '" + formatter.format(alarmInfo.getAlarmDate()) + "'";
        }
        params.add(1);
        return this.findByQueryList(sql, params);
    }

}
