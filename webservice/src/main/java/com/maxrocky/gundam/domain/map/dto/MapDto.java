package com.maxrocky.gundam.domain.map.dto;

/**
 * Created by jiazefeng on 2016/7/1.
 */
public class MapDto {
    private int mapId;
    private String mapSrc;
    private String villageName;
    private String villageId;

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public String getMapSrc() {
        return mapSrc;
    }

    public void setMapSrc(String mapSrc) {
        this.mapSrc = mapSrc;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getVillageId() {
        return villageId;
    }

    public void setVillageId(String villageId) {
        this.villageId = villageId;
    }
}
