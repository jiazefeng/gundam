package com.maxrocky.gundam.domain.strategy.dto;

import com.maxrocky.gundam.hibernate.BaseVO;
import com.maxrocky.gundam.page.PageInfoTools;

import java.util.Date;
import java.util.List;

/**
 * Created by JIAZEFENG on 2016/6/15.
 */
public class StrategyDTO extends PageInfoTools {
    private String strategyId;//方案ID
    private String strategyName;//方案名称
    private String operator;//操作人
    private Date createTime;//添加时间
    private Date updateTime;//修改时间
    private String taskName;//任务组名称
    private String ET;//预计时间
    private int overState;//任务结束后的状态
    private List taskId;//方案内容（任务组list）
    private String statues;//结束方案后的状态前台显示
    /*服务小区*/
    private String village;
    private String schemeId;//执行其他方案

    public String getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getET() {
        return ET;
    }

    public void setET(String ET) {
        this.ET = ET;
    }

    public int getOverState() {
        return overState;
    }

    public void setOverState(int overState) {
        this.overState = overState;
    }

    public List getTaskId() {
        return taskId;
    }

    public void setTaskId(List taskId) {
        this.taskId = taskId;
    }

    public String getStatues() {
        return statues;
    }

    public void setStatues(String statues) {
        this.statues = statues;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }
}
