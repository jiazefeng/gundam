package com.maxrocky.gundam.service.map.inf;

import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.commons.core.MapCorrectionPoints;
import com.maxrocky.gundam.commons.model.biz.Graph;
import com.maxrocky.gundam.domain.map.dto.GraphDto;
import com.maxrocky.gundam.domain.map.dto.MapDto;
import com.maxrocky.gundam.domain.map.model.BussinessMap;
import com.maxrocky.gundam.domain.user.model.UserInfo;

/**
 * Created by yuer5 on 16/5/19.
 */
public interface MapService {

    MapDto GetMap(String villageId);

    void SaveGraph(Graph graph,UserInfo userInfo) throws Exception;

    void UpdateGraph(Graph graph) throws Exception;

    Graph GetGraph(String villageId);

    GraphDto GetGraphDto(String villageId);

    double[][] GetTheta(String villageId);

    void MapCoordinate(MapCorrectionPoints mappings,UserInfo userInfo) throws Exception;

    /**
     * 上传地图
     *
     * @param mapDto
     * @return
     */
    public BussinessMap uploadMap(MapDto mapDto,UserInfo userInfo) throws Exception;

    /**
     * 删除地图
     *
     * @param mapId
     */
    public boolean deleteMapInfoById(int mapId);

    /**
     * 按小区ID 检索地图信息
     *
     * @param villageId
     * @return
     */
    public MapDto findMapByVillageId(String villageId);

    MapDto GetMapById(String villageId);
}
