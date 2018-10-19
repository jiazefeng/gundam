package com.maxrocky.gundam.domain.strategy.model;

import com.maxrocky.gundam.hibernate.BaseVO;

import javax.persistence.*;

/**
 * 方案与任务组对应信息
 * Created by jiazefeng on 16/6/15.
 */
@Entity
@Table(name = "strategy_tasks")
public class StrategyTasks extends BaseVO{
    private String id;//对应ID
    private String strategyId;//方案ID
    private int taskId;//任务组ID
    private int orderly;//排序

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    @Column(name = "task_id", nullable = false, insertable = true, updatable = true, length = 6)
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Basic
    @Column(name = "orderly", nullable = false, insertable = true, updatable = true, length = 6)
    public int getOrderly() {
        return orderly;
    }

    public void setOrderly(int orderly) {
        this.orderly = orderly;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StrategyTasks)) return false;

        StrategyTasks that = (StrategyTasks) o;

        if (getTaskId() != that.getTaskId()) return false;
        if (getOrderly() != that.getOrderly()) return false;
        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        return !(getStrategyId() != null ? !getStrategyId().equals(that.getStrategyId()) : that.getStrategyId() != null);

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getStrategyId() != null ? getStrategyId().hashCode() : 0);
        result = 31 * result + getTaskId();
        result = 31 * result + getOrderly();
        return result;
    }
}
