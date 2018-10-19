package com.maxrocky.gundam.domain.task.model;

import javax.persistence.*;

/**
 * Created by yuer5 on 16/6/9.
 */
@Entity
@Table(name = "task_item")
public class TaskItem {

    private int id;
    private int mapId;
    private String villageId;
    private int taskId; //
    private int taskType;       //0-Guard站岗， 1-Move巡逻, 3-Charge充电，4-GoStation回服务站

    private double mapPointFromX;
    private double mapPointFromY;
    private double mapPointToX;
    private double mapPointToY;
    private String mapPathWay;
    private double gpsPointFromLat;     //.lon表示经度，.lat表示纬度 - y
    private double gpsPointFromLon;     //.lon表示经度，.lat表示纬度 - x
    private double gpsPointToLat;     //.lon表示经度，.lat表示纬度 - y
    private double gpsPointToLon;     //.lon表示经度，.lat表示纬度 - x
    private String gpsPathWay;
    private int stayMinutes;
    private int orderly;

    private String footprintsJson;  //GPS坐标链表,格点化路径，json存储

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "map_id", nullable = false, insertable = true, updatable = true)
    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    @Column(name = "village_id", nullable = false, insertable = true, updatable = true)
    public String getVillageId() {
        return villageId;
    }

    public void setVillageId(String villageId) {
        this.villageId = villageId;
    }

    @Basic
    @Column(name = "task_id", nullable = false, insertable = true, updatable = true)
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Basic
    @Column(name = "task_type", nullable = false, insertable = true, updatable = true)
    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    @Basic
    @Column(name = "mapPointFromX", nullable = true, insertable = true, updatable = true)
    public double getMapPointFromX() {
        return mapPointFromX;
    }

    public void setMapPointFromX(double mapPointFromX) {
        this.mapPointFromX = mapPointFromX;
    }

    @Basic
    @Column(name = "mapPointFromY", nullable = true, insertable = true, updatable = true)
    public double getMapPointFromY() {
        return mapPointFromY;
    }

    public void setMapPointFromY(double mapPointFromY) {
        this.mapPointFromY = mapPointFromY;
    }

    @Basic
    @Column(name = "mapPointToX", nullable = true, insertable = true, updatable = true)
    public double getMapPointToX() {
        return mapPointToX;
    }

    public void setMapPointToX(double mapPointToX) {
        this.mapPointToX = mapPointToX;
    }

    @Basic
    @Column(name = "mapPointToY", nullable = true, insertable = true, updatable = true)
    public double getMapPointToY() {
        return mapPointToY;
    }

    public void setMapPointToY(double mapPointToY) {
        this.mapPointToY = mapPointToY;
    }

    @Column(name = "map_passway_json", nullable = true, insertable = true, updatable = true, columnDefinition = "TEXT")
    public String getMapPathWay() {
        return mapPathWay;
    }

    public void setMapPathWay(String mapPathWay) {
        this.mapPathWay = mapPathWay;
    }

    @Basic
    @Column(name = "gpsPointFromLat", nullable = true, insertable = true, updatable = true)
    public double getGpsPointFromLat() {
        return gpsPointFromLat;
    }

    public void setGpsPointFromLat(double gpsPointFromLat) {
        this.gpsPointFromLat = gpsPointFromLat;
    }

    @Basic
    @Column(name = "gpsPointFromLon", nullable = true, insertable = true, updatable = true)
    public double getGpsPointFromLon() {
        return gpsPointFromLon;
    }

    public void setGpsPointFromLon(double gpsPointFromLon) {
        this.gpsPointFromLon = gpsPointFromLon;
    }

    @Basic
    @Column(name = "gpsPointToLat", nullable = true, insertable = true, updatable = true)
    public double getGpsPointToLat() {
        return gpsPointToLat;
    }

    public void setGpsPointToLat(double gpsPointToLat) {
        this.gpsPointToLat = gpsPointToLat;
    }

    @Basic
    @Column(name = "gpsPointToLon", nullable = true, insertable = true, updatable = true)
    public double getGpsPointToLon() {
        return gpsPointToLon;
    }

    public void setGpsPointToLon(double gpsPointToLon) {
        this.gpsPointToLon = gpsPointToLon;
    }

    @Column(name = "gps_passway_json", nullable = true, insertable = true, updatable = true, columnDefinition = "TEXT")
    public String getGpsPathWay() {
        return gpsPathWay;
    }

    public void setGpsPathWay(String gpsPathWay) {
        this.gpsPathWay = gpsPathWay;
    }

    @Basic
    @Column(name = "stayMinutes", nullable = true, insertable = true, updatable = true)
    public int getStayMinutes() {
        return stayMinutes;
    }

    public void setStayMinutes(int stayMinutes) {
        this.stayMinutes = stayMinutes;
    }

    public int getOrderly() {
        return orderly;
    }

    public void setOrderly(int orderly) {
        this.orderly = orderly;
    }

    @Column(name = "footprints_json", nullable = true, insertable = true, updatable = true, columnDefinition = "TEXT")
    public String getFootprintsJson() {
        return footprintsJson;
    }

    public void setFootprintsJson(String footprintsJson) {
        this.footprintsJson = footprintsJson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskItem taskItem = (TaskItem) o;

        if (id != taskItem.id) return false;
        if (mapId != taskItem.mapId) return false;
        if (taskId != taskItem.taskId) return false;
        if (taskType != taskItem.taskType) return false;
        if (Double.compare(taskItem.mapPointFromX, mapPointFromX) != 0) return false;
        if (Double.compare(taskItem.mapPointFromY, mapPointFromY) != 0) return false;
        if (Double.compare(taskItem.mapPointToX, mapPointToX) != 0) return false;
        if (Double.compare(taskItem.mapPointToY, mapPointToY) != 0) return false;
        if (stayMinutes != taskItem.stayMinutes) return false;
        if (orderly != taskItem.orderly) return false;
        return !(villageId != null ? !villageId.equals(taskItem.villageId) : taskItem.villageId != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + mapId;
        result = 31 * result + (villageId != null ? villageId.hashCode() : 0);
        result = 31 * result + taskId;
        result = 31 * result + taskType;
        temp = Double.doubleToLongBits(mapPointFromX);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(mapPointFromY);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(mapPointToX);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(mapPointToY);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + stayMinutes;
        result = 31 * result + orderly;
        return result;
    }
}
