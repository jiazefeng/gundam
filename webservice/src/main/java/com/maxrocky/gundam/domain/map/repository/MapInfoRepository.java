package com.maxrocky.gundam.domain.map.repository;

import com.maxrocky.gundam.domain.map.model.BussinessMap;
import com.maxrocky.gundam.domain.map.model.MapInfo;

import java.util.List;

/**
 * Created by jiazefeng on 2016/07/02.
 */
public interface MapInfoRepository {
    /**
     * 上传地图
     *
     * @param mapInfo
     * @return
     */
    public boolean uploadMap(MapInfo mapInfo);

    /**
     * 按小区id 检索地图信息
     *
     * @param villageId
     * @return
     */
    public MapInfo findMapByVillageId(String villageId);
    /**
     * 按小区id 检索地图信息
     *
     * @param villageId
     * @return
     */
    public List<MapInfo> getMapInfoByVillageId(String villageId);
    /**
     * 按地图id 检索地图信息
     *
     * @param mapId
     * @return
     */
    public List<MapInfo> getMapInfoByMapId(int mapId);
    /**
     * 按mapId 检索地图信息
     *
     * @param mapId
     * @return
     */
    public MapInfo findMapByMapId(int mapId);

    /**
     * 删除地图
     *
     * @param mapInfo
     * @return
     */
    public boolean deleteMap(MapInfo mapInfo);

    //根据小区Id查询地图
    public MapInfo getMapById(String id);
}
