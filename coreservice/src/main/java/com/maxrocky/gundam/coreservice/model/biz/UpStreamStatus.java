package com.maxrocky.gundam.coreservice.model.biz;

/**
 * Created by yuer5 on 16/6/26.
 */
public class UpStreamStatus {
    private int power;
    private int alert;
    private Boolean isCharging;

    public UpStreamStatus() {
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getAlert() {
        return alert;
    }

    public void setAlert(int alert) {
        this.alert = alert;
    }

    public Boolean getIsCharging() {
        return isCharging;
    }

    public void setIsCharging(Boolean isCharging) {
        this.isCharging = isCharging;
    }
}
