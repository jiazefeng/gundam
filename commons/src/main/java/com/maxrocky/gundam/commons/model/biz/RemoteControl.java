package com.maxrocky.gundam.commons.model.biz;

import com.maxrocky.gundam.commons.algorithm.GPoint;

/**
 * Created by yuer5 on 16/7/7.
 */
public class RemoteControl {
    private String deviceId;
    private String robotId;
    private int control_type;
    private GPoint map_position;
    private GPoint direction;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public int getControl_type() {
        return control_type;
    }

    public void setControl_type(int control_type) {
        this.control_type = control_type;
    }

    public GPoint getMap_position() {
        return map_position;
    }

    public void setMap_position(GPoint map_position) {
        this.map_position = map_position;
    }

    public GPoint getDirection() {
        return direction;
    }

    public void setDirection(GPoint direction) {
        this.direction = direction;
    }
}
