package com.maxrocky.gundam.coreservice.model.biz;

import com.maxrocky.gundam.commons.model.biz.GraphNode;

import java.util.Date;

/**
 * Created by yuer5 on 16/6/12.
 */
public class RobetUpStreamInfo {

    private double pose;

    private UpStreamGps gps;
    private UpStreamStatus status;
    private UpStreamId id;
    private Date reportTime;

    public RobetUpStreamInfo() {
    }

    public double getPose() {
        return pose;
    }

    public void setPose(double pose) {
        this.pose = pose;
    }

    public UpStreamGps getGps() {
        return gps;
    }

    public void setGps(UpStreamGps gps) {
        this.gps = gps;
    }

    public UpStreamStatus getStatus() {
        return status;
    }

    public void setStatus(UpStreamStatus status) {
        this.status = status;
    }

    public UpStreamId getId() {
        return id;
    }

    public void setId(UpStreamId id) {
        this.id = id;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public GraphNode GetGpsNode(String NodeName){

        GraphNode node = new GraphNode(0, NodeName, NodeName,
                this.getGps().getX(),
                this.getGps().getY());
        return node;
    }

    public GraphNode GetMapAxisValue(double[][] theta, String NodeName){

        GraphNode node = new GraphNode(0, NodeName, NodeName,
                (this.getGps().getX()-theta[0][1])/theta[0][0],
                (this.getGps().getY()-theta[1][1])/theta[1][0]);
        return node;
    }

}
