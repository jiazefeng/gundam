package com.maxrocky.gundam.domain.robot.dto;

import com.maxrocky.gundam.commons.algorithm.GPoint;
import com.maxrocky.gundam.page.PageInfoTools;

import java.util.Date;
import java.util.List;

/**
 * Created by maxrocky on 2016/5/26.
 */
public class RobotInfoDTo extends PageInfoTools {
    /**
     * 机器人ID
     */
    private String robotId;
    /**
     * 机器人名称
     */
    private String robotName;
    /**
     * 机器人编号
     */
    private String robotNumber;
    /**
     * 机器人状态
     */
    private String status;
    /**
     * 机器人的电量
     */
    private String electricity;
    /**
     * 机器人类型
     */
    private String robotType;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 地点
     */
    private GPoint position;

    /**
     * 地图ID
     */
    private int mapId;
    /**
     * 小区ID
     */
    private String villageId;

    /**
     * 当前状态（策略名称）
     *
     * @return
     */
    private String strategyName;
    /**
     * 证书
     */
    private String certificate;
    /**
     * 功能
     */
    private String function;

    private List funList;
    //机器人是否繁忙(是否正在执行策略)
    private String isBusy;
    //正在执行的策略ID
    private String strategyId;
    //面向角度
    private double pose;
    //true手动，false自动
    private boolean is_remote;
    private GPoint remote_target_map_point;
    //是否清空任务
    private boolean clearTask;
    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public String getRobotName() {
        return robotName;
    }

    public void setRobotName(String robotName) {
        this.robotName = robotName;
    }

    public String getRobotNumber() {
        return robotNumber;
    }

    public void setRobotNumber(String robotNumber) {
        this.robotNumber = robotNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getElectricity() {
        return electricity;
    }

    public void setElectricity(String electricity) {
        this.electricity = electricity;
    }

    public String getRobotType() {
        return robotType;
    }

    public void setRobotType(String robotType) {
        this.robotType = robotType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public GPoint getPosition() {
        return position;
    }

    public void setPosition(GPoint position) {
        this.position = position;
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

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List getFunList() {
        return funList;
    }

    public void setFunList(List funList) {
        this.funList = funList;
    }

    public String getIsBusy() {
        return isBusy;
    }

    public void setIsBusy(String isBusy) {
        this.isBusy = isBusy;
    }

    public String getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }

    public double getPose() {
        return pose;
    }

    public void setPose(double pose) {
        this.pose = pose;
    }

    public boolean is_remote() {
        return is_remote;
    }

    public void setIs_remote(boolean is_remote) {
        this.is_remote = is_remote;
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
