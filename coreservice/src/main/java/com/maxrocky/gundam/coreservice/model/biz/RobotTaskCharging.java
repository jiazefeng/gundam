package com.maxrocky.gundam.coreservice.model.biz;

import com.maxrocky.gundam.commons.configs.Enums;
import com.maxrocky.gundam.commons.model.biz.Graph;
import com.maxrocky.gundam.commons.model.biz.GraphNode;
import com.maxrocky.gundam.commons.utility.GraphUtility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by yuer5 on 16/7/11.
 */
public class RobotTaskCharging extends RobotTask {

    private Date startTime;
    private Boolean isInCharging;

    public RobotTaskCharging(){};

    public RobotTaskCharging(int taskId, int taskInfoId, int taskItemId) {
        super(taskId, Enums.TaskItemType.Charging.ordinal(), taskInfoId, taskItemId, -2, -2);
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Boolean getIsInCharging() {
        return isInCharging;
    }

    public void setIsInCharging(Boolean isInCharging) {
        this.isInCharging = isInCharging;
    }

    @Override
    public Boolean IsFinishTask(RobetUpStreamInfo upStreamInfo, double[][] theta) {

        boolean upChargingStatus = upStreamInfo.getStatus().getIsCharging();
        if(upChargingStatus) {
            this.isInCharging = upChargingStatus;
            return false;
        }

        if(this.isInCharging != null && this.isInCharging && !upChargingStatus){
            return true;
        }
        return false;
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(this.startTime);
//        calendar.add(Calendar.MINUTE, this.stayMinutes);
//        if(calendar.getTime().before(new Date()) && upStreamInfo.getStatus().getIsCharging() == false) {
//
//        }

    }

    @Override
    public void SetPathAndFootprint(Graph map, double[][] theta, GraphNode startNode) {

        List<GraphNode> gpslist = new ArrayList<>();
        List<GraphNode> footprints = new ArrayList<>();

        GraphNode graphNode = new GraphNode(1,
                "fp-" + (this.getTaskId() - 1) + "-" + 1,
                (this.getTaskId() - 1) + "",
                startNode.getX(),
                startNode.getY());
        gpslist.add(graphNode);

        graphNode = GraphUtility.TransferNodeToGps(graphNode, theta);
        footprints.add(graphNode);

        this.setPathPoints(gpslist);
        this.setFootPrints(footprints);
    }

    @Override
    public void StartTask() {
        this.startTime = new Date();
    }
}
