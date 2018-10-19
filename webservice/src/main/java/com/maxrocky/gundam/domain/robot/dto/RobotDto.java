package com.maxrocky.gundam.domain.robot.dto;

import com.maxrocky.gundam.commons.algorithm.GPoint;
import com.maxrocky.gundam.commons.model.biz.GraphNode;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lizhipeng on 2016/7/15.
 */
public class RobotDto {
    private String robotid;
    private String deviceid;
    private String power;
    private GraphNode gps_position;
    private GPoint map_position;
    private boolean is_remote;
    private String work_mode;
    private String strategy;
    private String alarm;
    private String direction;
    private String latest_report;
    private String currTaskItemType;
    private String online;
    private Date strategyStartTime;
    private int taskItemid;
    private double pose;
    private GPoint release_target_map_point;
    private GPoint remote_target_map_point;
    //是否清空任务
    private boolean clearTask;

    public String getRobotid() {
        return robotid;
    }

    public void setRobotid(String robotid) {
        this.robotid = robotid;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public GraphNode getGps_position() {
        return gps_position;
    }

    public void setGps_position(GraphNode gps_position) {
        this.gps_position = gps_position;
    }

    public GPoint getMap_position() {
        return map_position;
    }

    public void setMap_position(GPoint map_position) {
        this.map_position = map_position;
    }

    public boolean is_remote() {
        return is_remote;
    }

    public void setIs_remote(boolean is_remote) {
        this.is_remote = is_remote;
    }

    public String getWork_mode() {
        return work_mode;
    }

    public void setWork_mode(String work_mode) {
        this.work_mode = work_mode;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getLatest_report() {
        return latest_report;
    }

    public void setLatest_report(String latest_report) {
        this.latest_report = latest_report;
    }

    public String getCurrTaskItemType() {
        return currTaskItemType;
    }

    public void setCurrTaskItemType(String currTaskItemType) {
        this.currTaskItemType = currTaskItemType;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public Date getStrategyStartTime() {
        return strategyStartTime;
    }

    public void setStrategyStartTime(Date strategyStartTime) {
        this.strategyStartTime = strategyStartTime;
    }

    public int getTaskItemid() {
        return taskItemid;
    }

    public void setTaskItemid(int taskItemid) {
        this.taskItemid = taskItemid;
    }

    public double getPose() {
        return pose;
    }

    public void setPose(double pose) {
        this.pose = pose;
    }

    public GPoint getRelease_target_map_point() {
        return release_target_map_point;
    }

    public void setRelease_target_map_point(GPoint release_target_map_point) {
        this.release_target_map_point = release_target_map_point;
    }

    public GPoint getRemote_target_map_point() {
        return remote_target_map_point;
    }

    public void setRemote_target_map_point(GPoint remote_target_map_point) {
        this.remote_target_map_point = remote_target_map_point;
    }

    public boolean isClearTask() {
        return clearTask;
    }

    public void setClearTask(boolean clearTask) {
        this.clearTask = clearTask;
    }
}
