package com.maxrocky.gundam.domain.robot.model;

import com.maxrocky.gundam.hibernate.BaseVO;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * Created by lizhipeng on 2016/6/23.
 */
@Entity
@Table(name = "robot_strategy")
public class RobotStrategy extends BaseVO {
    //ID
    private String id;
    /**
     * 机器人ID
     */
    private String robotId;
    /**
     * 策略ID
     */
    private String strategyId;
    /**
     * 添加时间
     */
    private Date createDate;

    private Time startTime;
    private int status;//执行方式：0：立即执行；1：定时执行
    private int order;//排序
    private int executionState;//机器人执行任务方案状态：1：执行完成；0：未执行  2：正在执行
    private int repeatStatus;//执行策略状态：1：重复；0：永不

    private List robotList;
    private List strategyList;
    private Date completeTime;//策略完成时间

    @Id
    @Column(name = "robot_strategy_id", nullable = false, insertable = true, updatable = true, length = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "robot_id", nullable = false, insertable = true, updatable = true, length = 32)
    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    @Basic
    @Column(name = "strategy_id", nullable = false, insertable = true, updatable = true, length = 32)
    public String getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }

    @Basic
    @Column(name = "create_date", nullable = true, insertable = true, updatable = true)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "start_time", nullable = true, insertable = true, updatable = true)
    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }


    @Basic
    @Column(name = "rs_order", nullable = true, insertable = true, updatable = true)
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Basic
    @Column(name = "status", nullable = true, insertable = true, updatable = true)
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name = "execution_state", nullable = true, insertable = true, updatable = true)
    public int getExecutionState() {
        return executionState;
    }

    public void setExecutionState(int executionState) {
        this.executionState = executionState;
    }

    @Basic
    @Column(name = "repeat_state", nullable = true, insertable = true, updatable = true)
    public int getRepeatStatus() {
        return repeatStatus;
    }

    public void setRepeatStatus(int repeatStatus) {
        this.repeatStatus = repeatStatus;
    }

    @Transient
    public List getRobotList() {
        return robotList;
    }

    public void setRobotList(List robotList) {
        this.robotList = robotList;
    }

    @Transient
    public List getStrategyList() {
        return strategyList;
    }

    public void setStrategyList(List strategyList) {
        this.strategyList = strategyList;
    }
    @Basic
    @Column(name = "complete_time", nullable = true, insertable = true, updatable = true)
    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }
}
