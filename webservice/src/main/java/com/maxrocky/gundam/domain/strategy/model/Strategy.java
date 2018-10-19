package com.maxrocky.gundam.domain.strategy.model;

import com.maxrocky.gundam.domain.task.model.TaskInfo;
import com.maxrocky.gundam.hibernate.BaseVO;

import javax.persistence.*;
import java.util.List;

/**
 * 方案信息
 * Created by jiazefeng on 16/6/15.
 */
@Entity
@Table(name = "strategy")
public class Strategy extends BaseVO {
    private String sId;//方案Id
    private String sName;//方案名称
    private int overStatus;//方案结束后的状态
    private int state;//方案状态：0：离线；1：在线
    private String ET;//预计时间
    private int mapId;//地图ID
    private String villageId;//小区id
    private String schemeId;//执行其他方案

    private List<TaskInfo> tasks;//任务组集合
    private List<Integer> taskIds;//任务组id
//    private Map<Integer, TaskItem> taskitems;

    @Id
    @Column(name = "strategy_id", nullable = false, insertable = true, updatable = true, length = 32)
    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    @Basic
    @Column(name = "strategy_name", nullable = false, insertable = true, updatable = true, length = 64)
    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    @Basic
    @Column(name = "over_status", nullable = false, insertable = true, updatable = true, length = 32)
    public int getOverStatus() {
        return overStatus;
    }

    public void setOverStatus(int overStatus) {
        this.overStatus = overStatus;
    }

    @Basic
    @Column(name = "state", nullable = false, insertable = true, updatable = true, length = 6)
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Basic
    @Column(name = "village_id", nullable = true, insertable = true, updatable = true, length = 32)
    public String getVillageId() {
        return villageId;
    }

    public void setVillageId(String villageId) {
        this.villageId = villageId;
    }

    @Basic
    @Column(name = "map_id", nullable = true, insertable = true, updatable = true, length = 32)
    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    @Basic
    @Column(name = "ET", nullable = true, insertable = true, updatable = true)
    public String getET() {
        return ET;
    }

    public void setET(String ET) {
        this.ET = ET;
    }
    @Basic
    @Column(name = "scheme_Id", nullable = true, insertable = true, updatable = true,length = 32)
    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    @Transient
    public List<TaskInfo> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskInfo> tasks) {
        this.tasks = tasks;
    }

    @Transient
    public List<Integer> getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(List<Integer> taskIds) {
        this.taskIds = taskIds;
    }

}
