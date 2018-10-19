package com.maxrocky.gundam.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxrocky.gundam.commons.utility.ArrayStringConverter;
import com.maxrocky.gundam.commons.algorithm.CoordinateConvert;
import com.maxrocky.gundam.commons.algorithm.GPoint;
import com.maxrocky.gundam.commons.core.MapCorrectionPoints;
import com.maxrocky.gundam.commons.model.biz.Graph;
import com.maxrocky.gundam.commons.utility.GraphUtility;
import com.maxrocky.gundam.service.MapService;
import com.maxrocky.gundam.domain.map.model.BussinessMap;
import com.maxrocky.gundam.domain.map.repository.BussinessMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.ujmp.core.Matrix;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by yuer5 on 16/5/19.
 */
@Service
public class MapServiceImpl implements MapService {

    @Autowired
    private BussinessMapRepository mapRepository;


    @Override
    /**
     * 从DB获取theta
     */
    public double[][] GetTheta(String villageId) {

        BussinessMap map = mapRepository.GetByVillageId(villageId);
        String thetaStr = map.getTheta();
        if(thetaStr ==null || thetaStr.isEmpty())
            return null;
        else
            return ArrayStringConverter.convertToArray(thetaStr, 2, 2);
    }

    @Override
    public BussinessMap GetMap(String villageId) {

        return mapRepository.GetByVillageId(villageId);
    }

    @Override
    public void SaveGraph(Graph graph) throws Exception {

        BussinessMap bussinessMap = mapRepository.GetByVillageId(graph.getVillageId());
        if (bussinessMap != null) {
            throw new Exception("图已存在！");
        }

        //序列化保存图片里的图信息为Json
        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(graph);
        //如果图有修改,删除坐标校正信息，删除校正系数theta，删除GPS图
        bussinessMap = new BussinessMap();
        bussinessMap.setVillageId(graph.getVillageId());
        bussinessMap.setIsMappingCoordinate(0);
        bussinessMap.setMappingCoordinateJson("");
        bussinessMap.setTheta("");
        bussinessMap.setGraphJson(json);
        bussinessMap.setUpdateTime(new Date());

        mapRepository.Add(bussinessMap);
    }

    @Override
    public void UpdateGraph(Graph graph) throws Exception {

        BussinessMap bussinessMap = mapRepository.GetByVillageId(graph.getVillageId());
        if (bussinessMap == null) {
            throw new Exception("图未找到！");
        }

        //序列化保存图片里的图信息为Json
        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(graph);

        if (bussinessMap.getGraphJson() != json || bussinessMap.getId() == 0) {
            bussinessMap.setGraphJson(json);
        }
        bussinessMap.setUpdateTime(new Date());

        mapRepository.Update(bussinessMap);
    }

    @Override
    public Graph GetGraph(String villageId) {
        BussinessMap map = mapRepository.GetByVillageId(villageId);
        String mapStr = map.getGraphJson();
        ObjectMapper mapper = new ObjectMapper();
        Graph graph = null;
        try {
            graph = mapper.readValue(mapStr, Graph.class);
            graph.setMapId(map.getId());
            graph.setVillageId(map.getVillageId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }

    @Override
    public void MapCoordinate(MapCorrectionPoints mappings) throws Exception {

        double[][] factors = getFactors(mappings);

        Graph map = GetGraph(mappings.getVillageId());

        Graph gpsGraph = new Graph();
        gpsGraph.setMapId(map.getMapId());
        gpsGraph.setVillageId(map.getVillageId());
        gpsGraph.setNodes(new ArrayList<>());
        gpsGraph.setEdges(new ArrayList<>());
        gpsGraph.setLabeledNodes(new ArrayList<>());

        GraphUtility.fillGpsMapInfo(map, factors, gpsGraph);

        BussinessMap businessMap = mapRepository.Get(mappings.getMapId());
        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(gpsGraph);
        businessMap.setGraphJson(json);
        businessMap.setIsMappingCoordinate(1);
        businessMap.setUpdateTime(new Date());

        businessMap.setTheta(ArrayStringConverter.convertToString(factors, 2, 2));

        mapRepository.Update(businessMap);
    }

    /**
     * 生成坐标转换theta
     * @param mappings
     * @return
     */
    private double[][] getFactors(MapCorrectionPoints mappings){

        CoordinateConvert convert = new CoordinateConvert();
        List<GPoint> fromPoints = mappings.getPoints().stream().map(a ->a.getMapPosition()).collect(Collectors.toList());
        List<GPoint> toPoints = mappings.getPoints().stream().map(a ->a.getGpsPosition()).collect(Collectors.toList());

        double[][] factors = convert.GetFactorsByLinearRegression(fromPoints, toPoints);
        return factors;
    }

}
