package com.maxrocky.gundam.domain.map.model;

import com.maxrocky.gundam.hibernate.BaseVO;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yuer5 on 16/5/13.
 */
@Entity
@Table(name = "bussiness_map")
public class BussinessMap extends BaseVO{

    //其他想到的：地图update的时候需要重新生成任务轨迹

    private int id;
    private String villageId;               //小区ID
    private String mapTitle;
    private String graphJson;               //图Json
    private int isMappingCoordinate;        //是否校正坐标信息 0-未校正； 1-已校正
    private Date mappingCoordinateTime;     //Graph生成时间
    private String mappingCoordinateJson;   //坐标校正json
    private String theta;

    private Date createTime;
    private String createUser;
    private Date updateTime;
    private String updateUser;
    private int isDelete;

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
    @Column(name = "village_id", nullable = false, insertable = true, updatable = true, length = 32)
    public String getVillageId() {
        return villageId;
    }

    public void setVillageId(String villageId) {
        this.villageId = villageId;
    }

    public String getMapTitle() {
        return mapTitle;
    }

    public void setMapTitle(String mapTitle) {
        this.mapTitle = mapTitle;
    }

    @Column(name = "graph_json", nullable = true, insertable = true, updatable = true, columnDefinition = "TEXT")
    public String getGraphJson() {
        return graphJson;
    }

    public void setGraphJson(String graphJson) {
        this.graphJson = graphJson;
    }

    public int getIsMappingCoordinate() {
        return isMappingCoordinate;
    }

    public void setIsMappingCoordinate(int isMappingCoordinate) {
        this.isMappingCoordinate = isMappingCoordinate;
    }

    @Column(name = "mapping_coordinate_json", nullable = true, insertable = true, updatable = true, columnDefinition = "TEXT")
    public String getMappingCoordinateJson() {
        return mappingCoordinateJson;
    }

    public void setMappingCoordinateJson(String mappingCoordinateJson) {
        this.mappingCoordinateJson = mappingCoordinateJson;
    }


    public Date getMappingCoordinateTime() {
        return mappingCoordinateTime;
    }

    public void setMappingCoordinateTime(Date mappingCoordinateTime) {
        this.mappingCoordinateTime = mappingCoordinateTime;
    }

    public String getTheta() {
        return theta;
    }

    public void setTheta(String theta) {
        this.theta = theta;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Basic
    @Column(name = "is_delete", nullable = false, insertable = true, updatable = true)
    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }
}
