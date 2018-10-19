package com.maxrocky.gundam.commons.core;

import com.maxrocky.gundam.commons.algorithm.GPoint;


/**
 * Created by yuer5 on 16/6/7.
 */
public class PointPair{
    private GPoint mapPosition;
    private GPoint gpsPosition;

    public GPoint getMapPosition() {
        return mapPosition;
    }

    public void setMapPosition(GPoint mapPosition) {
        this.mapPosition = mapPosition;
    }

    public GPoint getGpsPosition() {
        return gpsPosition;
    }

    public void setGpsPosition(GPoint gpsPosition) {
        this.gpsPosition = gpsPosition;
    }

    public PointPair(){}

    public PointPair(GPoint mapPosition, GPoint gpsPosition){
        this.mapPosition = mapPosition;
        this.gpsPosition = gpsPosition;
    }
}
