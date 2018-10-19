package com.maxrocky.gundam.service.map.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.common.result.SuccessApiResult;
import com.maxrocky.gundam.commons.algorithm.CoordinateConvert;
import com.maxrocky.gundam.commons.algorithm.GPoint;
import com.maxrocky.gundam.commons.core.MapCorrectionPoints;
import com.maxrocky.gundam.commons.model.biz.Graph;
import com.maxrocky.gundam.commons.utility.ArrayStringConverter;
import com.maxrocky.gundam.commons.utility.GraphUtility;
import com.maxrocky.gundam.domain.map.dto.GraphDto;
import com.maxrocky.gundam.domain.map.dto.MapDto;
import com.maxrocky.gundam.domain.map.model.BussinessMap;
import com.maxrocky.gundam.domain.map.model.MapInfo;
import com.maxrocky.gundam.domain.map.repository.BussinessMapRepository;
import com.maxrocky.gundam.domain.map.repository.MapInfoRepository;
import com.maxrocky.gundam.domain.robot.model.RobotStrategy;
import com.maxrocky.gundam.domain.robot.repository.RobotStratrgyRepository;
import com.maxrocky.gundam.domain.strategy.model.Strategy;
import com.maxrocky.gundam.domain.strategy.model.StrategyTasks;
import com.maxrocky.gundam.domain.strategy.repository.StrategyRepository;
import com.maxrocky.gundam.domain.strategy.repository.StrategyTasksRepository;
import com.maxrocky.gundam.domain.task.model.TaskInfo;
import com.maxrocky.gundam.domain.task.model.TaskItem;
import com.maxrocky.gundam.domain.task.repository.TaskInfoRepository;
import com.maxrocky.gundam.domain.task.repository.TaskItemRepository;
import com.maxrocky.gundam.domain.user.model.UserInfo;
import com.maxrocky.gundam.domain.village.model.Village;
import com.maxrocky.gundam.domain.village.repository.VillageRepository;
import com.maxrocky.gundam.service.map.inf.MapService;
import com.maxrocky.gundam.service.strategy.impl.StrategyServiceImpl;
import com.maxrocky.gundam.service.strategy.inf.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.ujmp.core.Matrix;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by yuer5 on 16/5/19.
 */
@Service
public class MapServiceImpl implements MapService {
    @Autowired
    private BussinessMapRepository mapRepository;
    @Autowired
    private VillageRepository villageRepository;
    @Autowired
    private StrategyRepository strategyRepository;
    @Autowired
    private TaskInfoRepository taskInfoRepository;
    @Autowired
    private StrategyTasksRepository strategyTasksRepository;
    @Autowired
    private TaskItemRepository taskItemRepository;
    @Autowired
    private RobotStratrgyRepository robotStratrgyRepository;
    @Autowired
    private MapInfoRepository mapInfoRepository;

    @Override
    public MapDto GetMap(String villageId) {
        //BussinessMap bussinessMap = mapRepository.GetByVillageId(villageId);
        BussinessMap bussinessMap = null;
        List<BussinessMap> bussinessMapList = mapRepository.getBussinessMapByVillageId(villageId);
        if (bussinessMapList != null && bussinessMapList.size() > 0) {
            bussinessMap = bussinessMapList.get(0);
        }
        if (bussinessMap == null) {
            return null;
        }
        MapDto mapDto = new MapDto();
        mapDto.setMapId(bussinessMap.getId());
        mapDto.setMapSrc(bussinessMap.getGraphJson());
        return mapDto;
    }

    @Override
    public void SaveGraph(Graph graph, UserInfo userInfo) throws Exception {

        //BussinessMap bussinessMap = mapRepository.GetByVillageId(graph.getVillageId());
//        if (bussinessMap == null) {
//            BussinessMap bussinessMap1 = new BussinessMap();
//            bussinessMap1.setVillageId(graph.getVillageId());
//            bussinessMap1.setCreateTime(new Date());
//            bussinessMap1.setCreateUser(userInfo.getuName());
//            bussinessMap1.setIsDelete(0);
//            bussinessMap1.s
//        }
        List<BussinessMap> bussinessMapList = mapRepository.getBussinessMapByVillageId(graph.getVillageId());
        BussinessMap bussinessMap = bussinessMapList.get(0);
        //序列化保存图片里的图信息为Json
        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(graph);
        //如果图有修改,删除坐标校正信息，删除校正系数theta，删除GPS图
        bussinessMap.setGraphJson(json);
        bussinessMap.setUpdateTime(new Date());
        bussinessMap.setUpdateUser(userInfo.getuName());

        mapRepository.Update(bussinessMap);
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

        //如果图有修改,删除坐标校正信息，删除校正系数theta，删除GPS图
        if (bussinessMap.getGraphJson() != json || bussinessMap.getId() == 0) {
//                bussinessMap.setIsMappingCoordinate(0);
//                bussinessMap.setMappingCoordinateJson("");
//                bussinessMap.setTheta("");
//                bussinessMap.setGraphGpsJson("");
            bussinessMap.setGraphJson(json);
        }
        bussinessMap.setUpdateTime(new Date());
        bussinessMap.setUpdateUser(0 + "");

        mapRepository.Update(bussinessMap);
    }

    @Override
    public Graph GetGraph(String villageId) {
        //BussinessMap map = mapRepository.GetByVillageId(villageId);
        List<BussinessMap> bussinessMapList = mapRepository.getBussinessMapByVillageId(villageId);
        BussinessMap map = bussinessMapList.get(0);
        Graph graph = null;
        if (map != null) {
            String mapStr = map.getGraphJson();
            ObjectMapper mapper = new ObjectMapper();
            try {
                graph = mapper.readValue(mapStr, Graph.class);
                graph.setMapId(map.getId());
                graph.setVillageId(map.getVillageId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return graph;
    }

    @Override
    public GraphDto GetGraphDto(String villageId) {
//        BussinessMap map = mapRepository.GetByVillageId(villageId);
//        MapInfo mapInfo = mapInfoRepository.findMapByVillageId(villageId);
        List<BussinessMap> bussinessMapList = mapRepository.getBussinessMapByVillageId(villageId);
        BussinessMap map = new BussinessMap();
        if (bussinessMapList != null && bussinessMapList.size() > 0) {
            map = bussinessMapList.get(0);
        }
        List<MapInfo> mapInfoList = mapInfoRepository.getMapInfoByVillageId(villageId);
        MapInfo mapInfo = new MapInfo();
        if (mapInfoList != null && mapInfoList.size() > 0) {
            mapInfo = mapInfoList.get(0);
        }
        GraphDto graphDto = null;
        if (map != null) {
            String mapStr = map.getGraphJson();
            ObjectMapper mapper = new ObjectMapper();
            try {
                graphDto = mapper.readValue(mapStr, GraphDto.class);
                graphDto.setMapId(map.getId());
                graphDto.setVillageId(map.getVillageId());
                graphDto.setImgUrl(mapInfo.getMapSrc());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return graphDto;
    }

    @Override
    /**
     * 从DB获取theta
     */
    public double[][] GetTheta(String villageId) {

        BussinessMap map = mapRepository.GetByVillageId(villageId);
        String thetaStr = map.getTheta();
        if (thetaStr == null || thetaStr.isEmpty())
            return null;
        else
            return ArrayStringConverter.convertToArray(thetaStr, 2, 2);
    }

    @Override
    public void MapCoordinate(MapCorrectionPoints mappings, UserInfo userInfo) throws Exception {

        double[][] factors = getFactors(mappings);

        Graph map = GetGraph(mappings.getVillageId());
        mappings.setMapId(map.getMapId());
        Graph gpsGraph = new Graph();
        gpsGraph.setMapId(map.getMapId());
        gpsGraph.setVillageId(map.getVillageId());
        gpsGraph.setNodes(new ArrayList<>());
        gpsGraph.setEdges(new ArrayList<>());
        gpsGraph.setLabeledNodes(new ArrayList<>());

        GraphUtility.fillGpsMapInfo(map, factors, gpsGraph);

        //BussinessMap businessMap = mapRepository.Get(mappings.getMapId());
        List<BussinessMap> bussinessMapList = mapRepository.getBussinessMapByVillageId(mappings.getVillageId());
        BussinessMap businessMap = bussinessMapList.get(0);
        ObjectMapper mapper = new ObjectMapper();

//        String json = mapper.writeValueAsString(gpsGraph);
//        businessMap.setGraphJson(json);
        businessMap.setIsMappingCoordinate(1);
        businessMap.setUpdateTime(new Date());
        businessMap.setUpdateUser(0 + "");
        businessMap.setTheta(ArrayStringConverter.convertToString(factors, 2, 2));

        mapRepository.Update(businessMap);
    }

    @Override
    public BussinessMap uploadMap(MapDto mapDto, UserInfo userInfo) throws Exception {
        MapInfo mapInfo = new MapInfo();
        if (mapDto != null) {
            mapInfo.setVillageId(mapDto.getVillageId());
            mapInfo.setMapSrc(mapDto.getMapSrc());
            mapInfo.setStatus(0);
            mapInfo.setCreateBy(userInfo.getuName());
            mapInfo.setModifyBy(userInfo.getuName());
            mapInfo.setCreateOn(Calendar.getInstance());
            mapInfo.setModifyOn(Calendar.getInstance());
            if (mapInfoRepository.uploadMap(mapInfo)) {
                BussinessMap bussinessMap = new BussinessMap();

                bussinessMap.setVillageId(mapDto.getVillageId());
                bussinessMap.setIsMappingCoordinate(0);
                bussinessMap.setMappingCoordinateJson("");
                bussinessMap.setTheta("");
                bussinessMap.setGraphJson("");
                Graph graph = new Graph();
                graph.setNodes(new ArrayList<>());
                graph.setEdges(new ArrayList<>());
                graph.setLabeledNodes(new ArrayList<>());
                graph.setBeacons(new ArrayList<>());
                graph.setCharges(new ArrayList<>());
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(graph);
                bussinessMap.setGraphJson(json);
                bussinessMap.setCreateTime(new Date());
                bussinessMap.setUpdateTime(new Date());
                bussinessMap.setCreateUser(userInfo.getuName());
                bussinessMap.setUpdateUser(userInfo.getuName());

                if (mapRepository.Add(bussinessMap)) {
                    return bussinessMap;
                }
            }
        }

        return null;
    }

    @Override
    public boolean deleteMapInfoById(int mapId) {
        if (mapId > 0) {
            MapInfo mapInfo = mapInfoRepository.findMapByMapId(mapId);
//            List<MapInfo> mapInfo = mapInfoRepository.getMapInfoByMapId(mapId);
            if (mapInfo != null) {
                mapInfo.setStatus(1);
            }
            if (mapInfoRepository.deleteMap(mapInfo)) {
//                BussinessMap bussinessMap = mapRepository.GetByVillageId(mapInfo.getVillageId());
                List<BussinessMap> bussinessMapList = mapRepository.getBussinessMapByVillageId(mapInfo.getVillageId());
                BussinessMap bussinessMap = bussinessMapList.get(0);
                if (bussinessMap != null) {
                    bussinessMap.setIsDelete(1);
                    mapRepository.Update(bussinessMap);
                    List<Strategy> strategies = strategyRepository.getStrategyListByVillage(mapInfo.getVillageId());
                    for (Strategy strategy : strategies) {
                        if (strategyRepository.deleteStrategy(strategy.getsId())) {
                            List<StrategyTasks> strategyTasksList = strategyTasksRepository.getStrategyTasks(strategy.getsId());
                            if (strategyTasksList != null && strategyTasksList.size() > 0) {
                                strategyTasksRepository.deleteStrategyTask(strategyTasksList);
                            }
                        }
                        List<RobotStrategy> list = robotStratrgyRepository.getByStrategy(strategy.getsId());
                        for (RobotStrategy robotStrategy : list) {
                            robotStratrgyRepository.deleteRobotStrategy(robotStrategy);
                        }
                    }
                    List<TaskInfo> list = strategyTasksRepository.getTaskInfoByVillageId(mapInfo.getVillageId());
                    for (TaskInfo taskInfo : list) {
                        taskItemRepository.DeleteByTaskId(taskInfo.getId());
                        taskInfoRepository.Delete(taskInfo.getId());
                    }

                }
                return true;
            }
        }
        return false;
    }

    @Override
    public MapDto GetMapById(String villageId) {
//        MapInfo mapInfo = mapInfoRepository.getMapById(villageId);
        List<MapInfo> mapInfoList = mapInfoRepository.getMapInfoByVillageId(villageId);
        MapInfo mapInfo = new MapInfo();
        if (mapInfoList != null && mapInfoList.size() > 0) {
            mapInfo = mapInfoList.get(0);
        }
        MapDto mapDto = new MapDto();
        mapDto.setMapId(mapInfo.getMapId());
        mapDto.setMapSrc(mapInfo.getMapSrc());
        return mapDto;
    }

    @Override
    public MapDto findMapByVillageId(String villageId) {
        MapDto mapDto = new MapDto();
        if (villageId == null || villageId.equals("")) {
            return mapDto;
        } else {
            //            MapInfo mapInfo = mapInfoRepository.findMapByVillageId(villageId);
            List<MapInfo> mapInfoList = mapInfoRepository.getMapInfoByVillageId(villageId);
            if (mapInfoList != null && mapInfoList.size() > 0) {
                MapInfo mapInfo = mapInfoList.get(0);
                if (mapInfo != null) {
                    mapDto.setMapId(mapInfo.getMapId());
                    mapDto.setMapSrc(mapInfo.getMapSrc());

                }
            }

            Village village = villageRepository.get(villageId);
            if (village != null) {
                mapDto.setVillageId(village.getVillageId());
                mapDto.setVillageName(village.getVillageName());
            }
            return mapDto;
        }
    }

    /**
     * 生成坐标转换theta
     *
     * @param mappings
     * @return
     */
    private double[][] getFactors(MapCorrectionPoints mappings) {

        CoordinateConvert convert = new CoordinateConvert();
        List<GPoint> fromPoints = mappings.getPoints().stream().map(a -> a.getMapPosition()).collect(Collectors.toList());
        List<GPoint> toPoints = mappings.getPoints().stream().map(a -> a.getGpsPosition()).collect(Collectors.toList());
//        Matrix theta = convert.GetFactorsByGradintDescent(fromPoints, toPoints);

        double[][] factors = convert.GetFactorsByLinearRegression(fromPoints, toPoints);
        return factors;

//        double[][] factors = new double[2][2];
//        factors[0][0] = theta.getAsDouble(0, 0);
//        factors[0][1] = theta.getAsDouble(2, 0);
//        factors[1][0] = theta.getAsDouble(1, 0);
//        factors[1][1] = theta.getAsDouble(3, 0);
//        return factors;
    }

}
