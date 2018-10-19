package com.maxrocky.gundam.coreservice.model.biz;

import javax.swing.text.StyledEditorKit;

/**
 * Created by yuer5 on 16/6/28.
 */
public class DownStreamCommand {

    private DownStreamMetadata direction;
    private DownStreamMetadata speed;
    private Boolean charging;

    public DownStreamMetadata getDirection() {
        return direction;
    }

    public void setDirection(DownStreamMetadata direction) {
        this.direction = direction;
    }

    public DownStreamMetadata getSpeed() {
        return speed;
    }

    public void setSpeed(DownStreamMetadata speed) {
        this.speed = speed;
    }

    public Boolean getCharging() {
        return charging;
    }

    public void setCharging(Boolean charging) {
        this.charging = charging;
    }
}
