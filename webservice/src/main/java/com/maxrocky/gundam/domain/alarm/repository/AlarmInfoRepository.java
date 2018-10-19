package com.maxrocky.gundam.domain.alarm.repository;


import com.maxrocky.gundam.domain.alarm.model.AlarmInfo;
import com.maxrocky.gundam.hibernate.BaseRepository;

import java.util.List;

/**
 * Created by jiazefeng on 2016/5/26.
 */
public interface AlarmInfoRepository extends BaseRepository<AlarmInfo> {
    /**
     * 检索全部报警信息
     *
     * @return
     */
    public List<AlarmInfo> getAlarmInfoList();

    /**
     * 报警信息总条数
     *
     * @return
     */
    public int getAlarmInfoCount();


    /**
     * 按条件检索全部报警信息
     *
     * @return
     */
    public List<AlarmInfo> getAlarmInfoList(AlarmInfo alarmInfo, int startRow);

    /**
     * 按条件检索的总条数
     *
     * @param alarmInfo
     * @return
     */
    public int getCount(AlarmInfo alarmInfo);

    /**
     * 导出Excel 数据
     *
     * @return
     */
    public List<AlarmInfo> getAlarmInfoList(AlarmInfo alarmInfo);

}
