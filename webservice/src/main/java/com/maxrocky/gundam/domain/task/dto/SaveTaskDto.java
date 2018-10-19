package com.maxrocky.gundam.domain.task.dto;

import com.maxrocky.gundam.commons.model.biz.GraphEdge;
import com.maxrocky.gundam.commons.model.biz.GraphNode;
import com.maxrocky.gundam.domain.task.model.TaskItem;

import java.util.List;

/**
 * Created by lizhipeng on 2016/7/15.
 */
public class SaveTaskDto {
    private int id;
    private int mapId;
    private String villageId;
    private String name;
    private List<TaskItem> taskitems;
    private List<GraphEdge> edges;
    private List<GraphNode> nodes;
    private List<Integer> taskItemIds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public String getVillageId() {
        return villageId;
    }

    public void setVillageId(String villageId) {
        this.villageId = villageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TaskItem> getTaskitems() {
        return taskitems;
    }

    public void setTaskitems(List<TaskItem> taskitems) {
        this.taskitems = taskitems;
    }

    public List<GraphEdge> getEdges() {
        return edges;
    }

    public void setEdges(List<GraphEdge> edges) {
        this.edges = edges;
    }

    public List<Integer> getTaskItemIds() {
        return taskItemIds;
    }

    public void setTaskItemIds(List<Integer> taskItemIds) {
        this.taskItemIds = taskItemIds;
    }

    public List<GraphNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<GraphNode> nodes) {
        this.nodes = nodes;
    }
}
