package com.maxrocky.gundam.domain.robot.dto;

import java.sql.Time;
import java.util.Date;

/**
 * Created by jiazefeng on 2016/07/28.
 */
public class RobotStrategyDto {
    private String strategyId;
    private String strategyName;
    private Time sTime;
    private String repeatState;

    public String getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public Time getsTime() {
        return sTime;
    }

    public void setsTime(Time sTime) {
        this.sTime = sTime;
    }

    public String getRepeatState() {
        return repeatState;
    }

    public void setRepeatState(String repeatState) {
        this.repeatState = repeatState;
    }
}
