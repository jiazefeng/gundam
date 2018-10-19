package com.maxrocky.gundam.domain.alarm.model;

import javax.persistence.*;

/**
 * 报警信息与机器人关系
 * Created by jiazefeng on 2016/5/26.
 */
@Entity
@Table(name = "alarm_robot")
public class AlarmRobot {
    /**
     * 报警信息与机器人关系ID
     */
    private String alarmRobotId;
    /**
     * 报警信息ID
     */
    private String alarmId;
    /**
     * 机器人ID
     */
    private String robotId;

    @Id
    @Column(name = "alarm_robot_id", nullable = false, insertable = true, updatable = true, length = 32)
    public String getAlarmRobotId() {
        return alarmRobotId;
    }

    public void setAlarmRobotId(String alarmRobotId) {
        this.alarmRobotId = alarmRobotId;
    }

    @Basic
    @Column(name = "robot_id", nullable = true, insertable = true, updatable = true, length = 50)
    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    @Basic
    @Column(name = "alarm_id", nullable = true, insertable = true, updatable = true, length = 50)
    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlarmRobot)) return false;

        AlarmRobot that = (AlarmRobot) o;

        if (getAlarmRobotId() != null ? !getAlarmRobotId().equals(that.getAlarmRobotId()) : that.getAlarmRobotId() != null)
            return false;
        if (getAlarmId() != null ? !getAlarmId().equals(that.getAlarmId()) : that.getAlarmId() != null) return false;
        return !(getRobotId() != null ? !getRobotId().equals(that.getRobotId()) : that.getRobotId() != null);

    }

    @Override
    public int hashCode() {
        int result = getAlarmRobotId() != null ? getAlarmRobotId().hashCode() : 0;
        result = 31 * result + (getAlarmId() != null ? getAlarmId().hashCode() : 0);
        result = 31 * result + (getRobotId() != null ? getRobotId().hashCode() : 0);
        return result;
    }
}
