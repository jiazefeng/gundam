package com.maxrocky.gundam.commons.core;

import java.util.List;

/**
 * Created by yuer5 on 16/6/7.
 */
public class MapCorrectionPoints {

    private int mapId;
    private String villageId;
    private List<PointPair> points;

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

    public List<PointPair> getPoints() {
        return points;
    }

    public void setPoints(List<PointPair> points) {
        this.points = points;
    }

}
