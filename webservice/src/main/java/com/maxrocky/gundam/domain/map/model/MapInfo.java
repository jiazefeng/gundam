package com.maxrocky.gundam.domain.map.model;

import com.maxrocky.gundam.hibernate.BaseVO;

import javax.persistence.*;

/**
 * Created by jiazefeng on 2016/07/02.
 */
@Entity
@Table(name = "map_info")
public class MapInfo extends BaseVO{
    /**
     * 地图ID
     */
    private int mapId;
    /**
     * 地图路径
     */
    private String mapSrc;
    /**
     * 小区ID
     */
    private String villageId;
    /**
     * 状态（0：未被删除，1：已被删除）
     */
    private int status;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "map_id", nullable = false, insertable = true, updatable = true)
    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }
    @Basic
    @Column(name = "map_src", nullable = false, insertable = true, updatable = true, length = 200)
    public String getMapSrc() {
        return mapSrc;
    }

    public void setMapSrc(String mapSrc) {
        this.mapSrc = mapSrc;
    }
    @Basic
    @Column(name = "village_id", nullable = false, insertable = true, updatable = true, length = 32)
    public String getVillageId() {
        return villageId;
    }

    public void setVillageId(String villageId) {
        this.villageId = villageId;
    }
    @Basic
    @Column(name = "status", nullable = false, insertable = true, updatable = true, length = 32)
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
