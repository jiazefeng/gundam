package com.maxrocky.gundam.domain.robot.model;

import com.maxrocky.gundam.hibernate.BaseVO;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jiazefeng on 2016/5/26.
 */
@Entity
@Table(name = "robot")
public class RobotInfo extends BaseVO {
    /**
     * 机器人ID
     */
    private String robotId;
    /**
     * 机器人名称
     */
    private String robotName;
    /**
     * 机器人型号
     */
    private String robotNumber;
    /**
     * 机器人状态
     */
    private int status;
    /**
     * 机器人的电量
     */
    private String electricity;
    /**
     * 机器人类型
     */
    private String robotType;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 地点
     */
    private String position;
    /**
     * 证书
     */
    private String certificate;

    /**
     * 小区Id
     */
    private String villageId;

    @Id
    @Column(name = "robot_id", nullable = false, insertable = true, updatable = true, length = 32)
    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    @Basic
    @Column(name = "robot_name", nullable = true, insertable = true, updatable = true, length = 50)
    public String getRobotName() {
        return robotName;
    }

    public void setRobotName(String robotName) {
        this.robotName = robotName;
    }

    @Basic
    @Column(name = "robot_number", nullable = true, insertable = true, updatable = true, length = 50)
    public String getRobotNumber() {
        return robotNumber;
    }

    public void setRobotNumber(String robotNumber) {
        this.robotNumber = robotNumber;
    }

    @Basic
    @Column(name = "robot_status", nullable = true, insertable = true, updatable = true, length = 16)
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name = "robot_electricity", nullable = true, insertable = true, updatable = true, length = 50)
    public String getElectricity() {
        return electricity;
    }

    public void setElectricity(String electricity) {
        this.electricity = electricity;
    }

    @Basic
    @Column(name = "robot_type", nullable = true, insertable = true, updatable = true, length = 50)
    public String getRobotType() {
        return robotType;
    }

    public void setRobotType(String robotType) {
        this.robotType = robotType;
    }

    @Basic
    @Column(name = "robot_startTime", nullable = true, insertable = true, updatable = true)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "robot_position", nullable = true, insertable = true, updatable = true, length = 50)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Basic
    @Column(name = "robot_certificate", nullable = true, insertable = true, updatable = true)
    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    @Basic
    @Column(name = "village_id", nullable = true, insertable = true, updatable = true, length = 32)
    public String getVillageId() {
        return villageId;
    }

    public void setVillageId(String villageId) {
        this.villageId = villageId;
    }
}
