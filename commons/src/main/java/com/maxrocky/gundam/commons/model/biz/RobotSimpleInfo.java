package com.maxrocky.gundam.commons.model.biz;

import com.maxrocky.gundam.commons.algorithm.GPoint;

import java.util.Date;

/**
 * Created by yuer5 on 16/7/1.
 */
public class RobotSimpleInfo {

    private String robotid;
    private String deviceid;
    private int power;
    private GPoint gps_position;
    private GPoint map_position;
    private boolean is_remote;
    private int work_mode;
    private String strategy;
    private int taskId;
    private int taskItemid;
    private int[] alarm;
    private int direction;
    private boolean isOnline;
    private Date latest_report;
    private int currTaskItemType;
    private double pose;
    private Date strategyStartTime;
    private GPoint release_target_map_point;
    private GPoint remote_target_map_point;

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

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public GPoint getGps_position() {
        return gps_position;
    }

    public void setGps_position(GPoint gps_position) {
        this.gps_position = gps_position;
    }

    public GPoint getMap_position() {
        return map_position;
    }

    public void setMap_position(GPoint map_position) {
        this.map_position = map_position;
    }

    public boolean getIs_remote() {
        return is_remote;
    }

    public void setIs_remote(boolean is_remote) {
        this.is_remote = is_remote;
    }

    public int getWork_mode() {
        return work_mode;
    }

    public void setWork_mode(int work_mode) {
        this.work_mode = work_mode;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskItemid() {
        return taskItemid;
    }

    public void setTaskItemid(int taskItemid) {
        this.taskItemid = taskItemid;
    }

    public int[] getAlarm() {
        return alarm;
    }

    public void setAlarm(int[] alarm) {
        this.alarm = alarm;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public Date getLatest_report() {
        return latest_report;
    }

    public void setLatest_report(Date latest_report) {
        this.latest_report = latest_report;
    }

    public int getCurrTaskItemType() {
        return currTaskItemType;
    }

    public void setCurrTaskItemType(int currTaskItemType) {
        this.currTaskItemType = currTaskItemType;
    }

    public double getPose() {
        return pose;
    }

    public void setPose(double pose) {
        this.pose = pose;
    }

    public Date getStrategyStartTime() {
        return strategyStartTime;
    }

    public void setStrategyStartTime(Date strategyStartTime) {
        this.strategyStartTime = strategyStartTime;
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
}
