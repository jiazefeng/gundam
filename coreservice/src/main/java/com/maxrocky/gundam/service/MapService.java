package com.maxrocky.gundam.service;

import com.maxrocky.gundam.commons.core.MapCorrectionPoints;
import com.maxrocky.gundam.commons.model.biz.Graph;
import com.maxrocky.gundam.domain.map.model.BussinessMap;

/**
 * Created by yuer5 on 16/5/19.
 */
public interface MapService {

    BussinessMap GetMap(String villageId);

    void SaveGraph(Graph graph) throws Exception;

    void UpdateGraph(Graph graph) throws Exception;

    Graph GetGraph(String villageId);

    double[][] GetTheta(String villageId);

    void MapCoordinate(MapCorrectionPoints mappings) throws Exception;

}
