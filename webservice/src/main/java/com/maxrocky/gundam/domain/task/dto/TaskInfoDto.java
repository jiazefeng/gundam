package com.maxrocky.gundam.domain.task.dto;

import com.maxrocky.gundam.commons.model.biz.GraphEdge;
import com.maxrocky.gundam.commons.model.biz.GraphNode;
import com.maxrocky.gundam.page.PageInfoTools;

import java.util.Calendar;
import java.util.List;

/**
 * Created by maxrocky on 2016/06/28.
 */
public class TaskInfoDto extends PageInfoTools {
    /**
     * 任务组ID
     */
    private int taskId;
    /**
     * 小区ID
     */
    private String villageId;
    /**
     * 任务组名称
     */
    private String name;
    /**
     * 创建时间
     */
    private Calendar createOn;
    /**
     * 修改时间
     */
    private Calendar modifyOn;
    /**
     * 修改人
     */
    private String modifyBy;

    /**
     * 任务项
     */
    private List taskitems;
    /**
     * 任务项名称（内容）
     */
    private String content;
    /**
     * 路径图
     */
    private String way;
    /**
     * 预计时间
     */
    private String estimatedTime;
    /**任务节点*/
    private List<GraphNode> nodes;
    /**任务道路*/
    private List<GraphEdge> edges;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
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

    public Calendar getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Calendar createOn) {
        this.createOn = createOn;
    }

    public Calendar getModifyOn() {
        return modifyOn;
    }

    public void setModifyOn(Calendar modifyOn) {
        this.modifyOn = modifyOn;
    }

    public List getTaskitems() {
        return taskitems;
    }

    public void setTaskitems(List taskitems) {
        this.taskitems = taskitems;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    public List<GraphNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<GraphNode> nodes) {
        this.nodes = nodes;
    }

    public List<GraphEdge> getEdges() {
        return edges;
    }

    public void setEdges(List<GraphEdge> edges) {
        this.edges = edges;
    }
}
