package com.maxrocky.gundam.domain.alarm.dto;

import java.util.Date;

/**
 * Created by jiazefeng on 2016/5/26.
 */
public class ExportExcelDto {
    private Date alarmDate;//报警时间
    private String alarmPosition;//报警位置
    private String robotName;//机器人名称
    private String event;//事件
    private String status;//状态
    private String level;//级别

    public ExportExcelDto() {
    }

    public ExportExcelDto(Date alarmDate, String alarmPosition, String robotName, String event, String status, String level) {
        this.alarmDate = alarmDate;
        this.alarmPosition = alarmPosition;
        this.robotName = robotName;
        this.event = event;
        this.status = status;
        this.level = level;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
