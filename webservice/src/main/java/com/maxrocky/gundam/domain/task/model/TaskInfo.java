package com.maxrocky.gundam.domain.task.model;

//import org.codehaus.jackson.annotate.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maxrocky.gundam.hibernate.BaseVO;

import javax.persistence.*;
import java.util.List;

/**
 * Created by yuer5 on 16/5/13.
 */
@Entity
@Table(name = "task_info")
public class TaskInfo extends BaseVO {

    private int id;
    private int mapId;
    private String villageId;
    private String name;
    private List<TaskItem> taskitems;
    private String graphJson;               //ÈÎÎñ×éÍ¼Json
    @JsonIgnore
    private List<Integer> taskItemIds;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "map_id", nullable = false, insertable = true, updatable = true)
    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    @Basic
    @Column(name = "village_id", nullable = false, insertable = true, updatable = true)
    public String getVillageId() {
        return villageId;
    }

    public void setVillageId(String villageId) {
        this.villageId = villageId;
    }

    @Basic
    @Column(name = "name", nullable = false, insertable = true, updatable = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Transient
    public List<TaskItem> getTaskitems() {
        return taskitems;
    }

    public void setTaskitems(List<TaskItem> taskitems) {
        this.taskitems = taskitems;
    }

    @Transient
    public List<Integer> getTaskItemIds() {
        return taskItemIds;
    }

    public void setTaskItemIds(List<Integer> taskItemIds) {
        this.taskItemIds = taskItemIds;
    }
    @Column(name = "graph_json", nullable = true, insertable = true, updatable = true, columnDefinition = "TEXT")
    public String getGraphJson() {
        return graphJson;
    }

    public void setGraphJson(String graphJson) {
        this.graphJson = graphJson;
    }
}


