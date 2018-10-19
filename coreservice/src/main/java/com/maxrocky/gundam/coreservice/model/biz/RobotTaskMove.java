package com.maxrocky.gundam.coreservice.model.biz;

import com.maxrocky.gundam.commons.configs.Enums;
import com.maxrocky.gundam.commons.model.biz.Graph;
import com.maxrocky.gundam.commons.model.biz.GraphNode;
import com.maxrocky.gundam.commons.utility.GraphUtility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yuer5 on 16/7/10.
 */
public class RobotTaskMove extends RobotTask{

    private double mapPointFromX;
    private double mapPointFromY;
    private int stepPoint;
//    private int printSize = 50000;
    private int printSize = 2;

    public RobotTaskMove(){};

    public RobotTaskMove(int taskId, int taskInfoId, int taskItemId, double mapPointFromX, double mapPointFromY, double mapPointToX, double mapPointToY) {
        super(taskId, Enums.TaskItemType.Move.ordinal(), taskInfoId, taskItemId, mapPointToX, mapPointToY);
        this.mapPointFromX = mapPointFromX;
        this.mapPointFromY = mapPointFromY;
    }

    public RobotTaskMove(int taskId, GraphNode fromNode, GraphNode toNode){
        super(taskId, Enums.TaskItemType.Move.ordinal(), -1, -1, toNode.getX(), toNode.getY());
        this.mapPointFromX = fromNode != null ? fromNode.getX() : -1;
        this.mapPointFromY = fromNode != null ? fromNode.getY() : -1;
    }

    public double getMapPointFromX() {
        return mapPointFromX;
    }

    public void setMapPointFromX(double mapPointFromX) {
        this.mapPointFromX = mapPointFromX;
    }

    public double getMapPointFromY() {
        return mapPointFromY;
    }

    public void setMapPointFromY(double mapPointFromY) {
        this.mapPointFromY = mapPointFromY;
    }

    public int getStepPoint() {
        return stepPoint;
    }

    public void setStepPoint(int stepPoint) {
        this.stepPoint = stepPoint;
    }

    /**
     * 把任务项转化为一系列的GPS坐标点
     * @param map
     * @return
     */
    public void SetPathAndFootprint(Graph map, double[][] theta, GraphNode startNode){

        if(startNode == null)
            startNode = new GraphNode(0,"0","0",this.getMapPointFromX(), this.getMapPointFromY());

        List<GraphNode> gpslist = new ArrayList<>();
        List<GraphNode> footprints = new ArrayList<>();

        if (this.getTaskType() == Enums.TaskItemType.Move.ordinal()) {
            GraphNode start;
            if(startNode == null || this.mapPointFromX == -1 || this.mapPointFromX == -1)
                start = new GraphNode(0, "taskItem" + this.getTaskId() + "Start", "taskItem" + this.getTaskId() + "Start",
                        this.mapPointFromX, this.mapPointFromY);
            else
                start = new GraphNode(0, "taskItem" + this.getTaskId() + "Start", "taskItem" + this.getTaskId() + "Start",
                        startNode.getX(), startNode.getY());

            GraphNode end = new GraphNode(0, "taskItem" + this.getTaskId() + "End", "taskItem" + this.getTaskId() + "End",
                    this.getMapPointToX(), this.getMapPointToY());

            gpslist = GraphUtility.GetGPSPath(map, theta, start, end);
            footprints = GraphUtility.GetGpsNodeFootprint(gpslist, this.printSize);
        }

        for (int i = 0; i < gpslist.size(); i++) {
            gpslist.get(i).setName("path-" + (this.getTaskId() - 1) + "-" + (i + 1));
            gpslist.get(i).setLabel(String.valueOf(this.getTaskId()-1));
        }
        for (int i = 0; i < footprints.size(); i++) {
            footprints.get(i).setName("fp-" + (this.getTaskId() - 1) + "-" + (i+1));
            footprints.get(i).setLabel(String.valueOf(this.getTaskId() - 1));
        }
        this.setFootPrints(footprints);
        this.setPathPoints(gpslist);
    }

    @Override
    public Boolean IsFinishTask(RobetUpStreamInfo upStreamInfo, double[][] theta) {

        //机器人当前GPS位置
        GraphNode nodegpsPlace = new GraphNode(0, "robot", "robot", upStreamInfo.getGps().getX(), upStreamInfo.getGps().getY());
        //当前任务终点GPS位置
        GraphNode currtaskEndPointGpsPlace = GraphUtility.TransferNodeToGps(
                new GraphNode(0, "end", "end",
                        this.getMapPointToX(),
                        this.getMapPointToY()),
                theta);
        //机器人与当前任务终点距离<2m被认为当然任务结束，任务指针＋1
        if (nodegpsPlace.GpsDistanceOnWGS84(currtaskEndPointGpsPlace) < 1){
            return true;
        }
        return false;
    }

    @Override
    public void StartTask() {

    }
}
