package com.maxrocky.gundam.domain.alarm.dto;

import com.maxrocky.gundam.page.PageInfoTools;

import java.util.Date;

/**
 * Created by jiazefeng on 2016/5/26.
 */
public class AlarmInfoDto extends PageInfoTools {
    private String alarmId;//报警id
    private Date alarmDate;//报警时间
    private String alarmPosition;//报警位置
    private String robotId;//机器人ID
    private String robotName;//机器人名称
    private String event;//事件
    private int status;//状态
    private int level;//级别

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public Date getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(Date alarmDate) {
        this.alarmDate = alarmDate;
    }

    public String getAlarmPosition() {
        return alarmPosition;
    }

    public void setAlarmPosition(String alarmPosition) {
        this.alarmPosition = alarmPosition;
    }

    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public String getRobotName() {
        return robotName;
    }

    public void setRobotName(String robotName) {
        this.robotName = robotName;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
