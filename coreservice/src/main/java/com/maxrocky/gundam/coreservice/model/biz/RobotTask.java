package com.maxrocky.gundam.coreservice.model.biz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maxrocky.gundam.commons.algorithm.GraphManager;
import com.maxrocky.gundam.commons.configs.Enums;
import com.maxrocky.gundam.commons.model.biz.Graph;
import com.maxrocky.gundam.commons.model.biz.GraphNode;
import com.maxrocky.gundam.commons.utility.GraphUtility;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yuer5 on 16/7/1.
 */
public abstract class RobotTask {
    private int taskId;
    private int taskType;       //见Enums.TaskItemType

    private int taskInfoId;
    private int taskItemId;
    private double mapPointToX;
    private double mapPointToY;

    @JsonIgnore
    private List<GraphNode> footPrints;

    private List<GraphNode> pathPoints;

    public RobotTask() {
    }

    public RobotTask(int taskId, int taskType, int taskInfoId, int taskItemId, double mapPointToX, double mapPointToY) {
        this.taskId = taskId;
        this.taskType = taskType;
        this.taskInfoId = taskInfoId;
        this.taskItemId = taskItemId;
        this.mapPointToX = mapPointToX;
        this.mapPointToY = mapPointToY;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskItemId() {
        return taskItemId;
    }

    public void setTaskItemId(int taskItemId) {
        this.taskItemId = taskItemId;
    }

    public int getTaskInfoId() {
        return taskInfoId;
    }

    public void setTaskInfoId(int taskInfoId) {
        this.taskInfoId = taskInfoId;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public double getMapPointToX() {
        return mapPointToX;
    }

    public void setMapPointToX(double mapPointToX) {
        this.mapPointToX = mapPointToX;
    }

    public double getMapPointToY() {
        return mapPointToY;
    }

    public void setMapPointToY(double mapPointToY) {
        this.mapPointToY = mapPointToY;
    }

    public List<GraphNode> getFootPrints() {
        return footPrints;
    }

    public void setFootPrints(List<GraphNode> footPrints) {
        this.footPrints = footPrints;
    }

    public List<GraphNode> getPathPoints() {
        return pathPoints;
    }

    public void setPathPoints(List<GraphNode> pathPoints) {
        this.pathPoints = pathPoints;
    }



    public abstract void SetPathAndFootprint(Graph map, double[][] theta, GraphNode startNode);

    public abstract Boolean IsFinishTask(RobetUpStreamInfo upStreamInfo, double[][] theta);

    public abstract void StartTask();
}
