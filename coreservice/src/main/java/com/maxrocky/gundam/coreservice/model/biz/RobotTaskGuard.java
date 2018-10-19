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
 * Created by yuer5 on 16/7/10.
 */
public class RobotTaskGuard extends RobotTask {

    private Date startTime;
    private int stayMinutes;

    public RobotTaskGuard(){};

    public RobotTaskGuard(int taskId, int taskInfoId, int taskItemId, double mapPointToX, double mapPointToY, int stayMinutes) {
        super(taskId, Enums.TaskItemType.Gurad.ordinal(), taskInfoId, taskItemId, mapPointToX, mapPointToY);
        this.stayMinutes = stayMinutes;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getStayMinutes() {
        return stayMinutes;
    }

    public void setStayMinutes(int stayMinutes) {
        this.stayMinutes = stayMinutes;
    }

    @Override
    public Boolean IsFinishTask(RobetUpStreamInfo upStreamInfo, double[][] theta) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.startTime);
        calendar.add(Calendar.MINUTE, this.stayMinutes);
        if(calendar.getTime().before(new Date())) {
            return true;
        }
        return false;
    }

    @Override
    public void SetPathAndFootprint(Graph map, double[][] theta, GraphNode startNode) {

        List<GraphNode> gpslist = new ArrayList<>();
        List<GraphNode> footprints = new ArrayList<>();

        GraphNode graphNode = new GraphNode(1,
                "fp-" + (this.getTaskId() - 1) + "-" + 1,
                (this.getTaskId() - 1) + "",
                this.getMapPointToX(),
                this.getMapPointToY());
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
