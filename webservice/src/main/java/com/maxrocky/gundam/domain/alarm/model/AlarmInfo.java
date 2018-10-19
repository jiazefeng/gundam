package com.maxrocky.gundam.domain.alarm.model;

import com.maxrocky.gundam.hibernate.BaseVO;

import javax.persistence.*;
import java.util.Date;

/**
 * 报警信息
 * Created by jiazefeng on 2016/5/26.
 */
@Entity
@Table(name = "alarm_info")
public class AlarmInfo extends BaseVO {

    private String alarmId;//报警id
    private Date alarmDate;//报警时间
    private String alarmPosition;//报警位置
    private String robotId;//机器人ID
    private String event;//事件
    private int status;//状态
    private int level;//级别

    @Id
    @Column(name = "alarm_id", nullable = false, insertable = true, updatable = true, length = 30)
    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    @Basic
    @Column(name = "alarm_date", nullable = true, insertable = true, updatable = true)
    public Date getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(Date alarmDate) {
        this.alarmDate = alarmDate;
    }

    @Basic
    @Column(name = "alarm_position", nullable = true, insertable = true, updatable = true, length = 30)
    public String getAlarmPosition() {
        return alarmPosition;
    }

    public void setAlarmPosition(String alarmPosition) {
        this.alarmPosition = alarmPosition;
    }

    @Basic
    @Column(name = "alarm_robot_id", nullable = false, insertable = true, updatable = true, length = 30)
    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    @Basic
    @Column(name = "alarm_event", nullable = true, insertable = true, updatable = true, length = 50)
    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Basic
    @Column(name = "alarm_status", nullable = true, insertable = true, updatable = true, length = 6)
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name = "alarm_level", nullable = true, insertable = true, updatable = true, length = 6)
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlarmInfo)) return false;

        AlarmInfo alarmInfo = (AlarmInfo) o;

        if (getStatus() != alarmInfo.getStatus()) return false;
        if (getLevel() != alarmInfo.getLevel()) return false;
        if (getAlarmId() != null ? !getAlarmId().equals(alarmInfo.getAlarmId()) : alarmInfo.getAlarmId() != null)
            return false;
        if (getAlarmDate() != null ? !getAlarmDate().equals(alarmInfo.getAlarmDate()) : alarmInfo.getAlarmDate() != null)
            return false;
        if (getAlarmPosition() != null ? !getAlarmPosition().equals(alarmInfo.getAlarmPosition()) : alarmInfo.getAlarmPosition() != null)
            return false;
        if (getRobotId() != null ? !getRobotId().equals(alarmInfo.getRobotId()) : alarmInfo.getRobotId() != null)
            return false;
        return !(getEvent() != null ? !getEvent().equals(alarmInfo.getEvent()) : alarmInfo.getEvent() != null);

    }

    @Override
    public int hashCode() {
        int result = getAlarmId() != null ? getAlarmId().hashCode() : 0;
        result = 31 * result + (getAlarmDate() != null ? getAlarmDate().hashCode() : 0);
        result = 31 * result + (getAlarmPosition() != null ? getAlarmPosition().hashCode() : 0);
        result = 31 * result + (getRobotId() != null ? getRobotId().hashCode() : 0);
        result = 31 * result + (getEvent() != null ? getEvent().hashCode() : 0);
        result = 31 * result + getStatus();
        result = 31 * result + getLevel();
        return result;
    }
}
