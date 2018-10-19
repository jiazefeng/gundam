package com.maxrocky.gundam.controller;

import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.common.result.ErrorApiResult;
import com.maxrocky.gundam.common.result.SuccessApiResult;
import com.maxrocky.gundam.common.util.HttpClient;
import com.maxrocky.gundam.commons.algorithm.GraphManager;
import com.maxrocky.gundam.commons.core.MapCorrectionPoints;
import com.maxrocky.gundam.commons.model.biz.Graph;
import com.maxrocky.gundam.commons.model.biz.GraphEdge;
import com.maxrocky.gundam.commons.model.biz.GraphNode;
import com.maxrocky.gundam.commons.utility.GraphUtility;
import com.maxrocky.gundam.domain.map.dto.GraphDto;
import com.maxrocky.gundam.domain.map.dto.MapDto;
import com.maxrocky.gundam.domain.map.model.BussinessMap;
import com.maxrocky.gundam.domain.robot.dto.RobotDto;
import com.maxrocky.gundam.domain.robot.dto.RobotInfoDTo;
import com.maxrocky.gundam.domain.robot.model.RobotInfo;
import com.maxrocky.gundam.domain.user.model.UserInfo;
import com.maxrocky.gundam.domain.village.model.Village;
import com.maxrocky.gundam.service.map.inf.MapService;
import com.maxrocky.gundam.service.robot.inf.RobotService;
import com.maxrocky.gundam.service.user.inf.UserService;
import com.maxrocky.gundam.service.village.inf.VillageService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuer5 on 16/5/19.
 */
@RestController
@RequestMapping(value = "/map")
public class MapController {

    @Autowired
    private MapService mapService;
    @Autowired
    private UserService userService;
    @Autowired
    private RobotService robotService;
    @Autowired
    private VillageService villageService;

    @RequestMapping(value = "/getcorrectWay", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getcorrectWay(@CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) {
        ModelMap result = new ModelMap();
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        List<RobotInfoDTo> robotInfoDTos = new ArrayList<RobotInfoDTo>();
        List<RobotInfoDTo> robotInfoDToList = robotService.getRobot(villageId);
        MapDto mapDto = mapService.GetMapById(villageId);
        for (RobotInfoDTo robotInfoDTo : robotInfoDToList) {
            JSONObject jsonObject = HttpClient.httpGet(HttpClient.url + "adminapi/robot/" + robotInfoDTo.getRobotId());
            Object object = jsonObject.get("data");
            JSONObject jsonObject1 = JSONObject.fromObject(object);
            RobotDto robotDto = (RobotDto) JSONObject.toBean(jsonObject1, RobotDto.class);
            if(robotDto.getOnline().equals("true")){

                robotInfoDTos.add(robotInfoDTo);
            }
        }
        result.addAttribute("robotInfoDToList", robotInfoDTos);
        result.addAttribute("bussinessMap", mapDto);
        List list = new ArrayList<>();
        result.addAttribute("charges", list);
        return new SuccessApiResult(result);
    }

    @RequestMapping(value = "/getWay", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap GetGraph(@CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) {
        GraphDto graphDto = mapService.GetGraphDto(villageId);
        MapDto mapDto = mapService.GetMapById(villageId);
        Village village = villageService.searchVillageById(villageId);
        graphDto.setImgUrl(mapDto.getMapSrc());
        graphDto.setVillageName(village.getVillageName());

        double[][] theta = mapService.GetTheta(villageId);
        if(theta==null){
            return new SuccessApiResult(false);
        }
        return new SuccessApiResult(graphDto);
    }

    @RequestMapping(value = "/SaveGraph", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ModelMap SaveGraph(@RequestBody Graph graph, @CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        try {
            graph.setVillageId(villageId);
            mapService.SaveGraph(graph, userInfo);
            JSONObject jsonObject = HttpClient.httpGet(HttpClient.url + "adminapi/map/" + villageId + "/refresh");
            return new SuccessApiResult("添加成功");
        } catch (Exception e) {
            return new ErrorApiResult("-1", e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    public ModelMap UpdateGraph(@RequestBody Graph graph) {
        try {
            mapService.UpdateGraph(graph);
            return new SuccessApiResult();
        } catch (Exception e) {
            return new ErrorApiResult("-1", e.getMessage(), e);
        }

    }

    @RequestMapping(value = "/coordinate", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    public ModelMap MapCorrection(@CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId, @RequestBody MapCorrectionPoints mappings) {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        mappings.setVillageId(villageId);
        try {
            mapService.MapCoordinate(mappings, userInfo);
            JSONObject jsonObject = HttpClient.httpGet(HttpClient.url + "adminapi/map/" + villageId + "/refresh");
            return new SuccessApiResult("校正成功");
        } catch (Exception e) {
            return new ErrorApiResult("-1", e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/findpath", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ModelMap GetGraphPath(@RequestBody List<GraphNode> pointPair, @CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        try {
            if (pointPair.size() != 2)
                throw new Exception("请检查起点和终点坐标");

            Graph graph = GraphUtility.regularisationMapInfo(mapService.GetGraph(villageId));
            if (graph.getEdges().size() <= 0) {
                return new SuccessApiResult(20001);
            }
            GraphManager graphManager = new GraphManager();
            GraphNode start = pointPair.get(0);
            GraphNode end = pointPair.get(1);

            List<GraphNode> list = graphManager.DoFindPathByExternalPoint(graph, start, end);
            List<GraphNode> list1 = new ArrayList<>();
            for (GraphNode graphNode : list) {
                list1.add(graphNode);
            }
            return new SuccessApiResult(list);
        } catch (Exception e) {
            return new ErrorApiResult("-1", e.getMessage(), e);
        }
    }

    /**
     * 上传地图
     *
     * @param mapDto
     * @param tokenId
     * @return
     */
    @RequestMapping(value = "/uploadMap", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public ApiResult addUser(@RequestBody MapDto mapDto, @CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) throws Exception {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        ModelMap result = new ModelMap();
        mapDto.setVillageId(villageId);
        BussinessMap bussinessMap = mapService.uploadMap(mapDto, userInfo);
        if(bussinessMap!=null){
             result.addAttribute("success", "上传成功");
             MapDto mapDto1 = mapService.findMapByVillageId(mapDto.getVillageId());
             result.addAttribute("mapDto",mapDto1);
            JSONObject jsonObject = HttpClient.httpGet(HttpClient.url + "adminapi/map/" + villageId + "/refresh");
        }else {
            result.addAttribute("error", "上传失败");
        }
        return  new SuccessApiResult(result);
    }

    /**
     * 删除地图
     *
     * @param mapId
     * @return
     */
    @RequestMapping(value = "/{mapId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ModelMap DeleteBussinessMapById(@PathVariable int mapId, @CookieValue(value = "villageId", required = false) String villageId) {
        ModelMap modelMap = new ModelMap();
        if(mapService.deleteMapInfoById(mapId)){
            modelMap.addAttribute("success", "删除成功");
            MapDto mapDto1 = mapService.findMapByVillageId(villageId);
            modelMap.addAttribute("mapDto",mapDto1);
        }else {
            modelMap.addAttribute("error", "删除失败");
        }
        return new SuccessApiResult(modelMap);
    }

    /**
     * 初始化地图管理
     *
     * @param tokenId
     * @param villageId
     * @return
     */
    @RequestMapping(value = "/findBussinessMapByVillageId", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult findBussinessMapByVillageId(@CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        MapDto mapDto = mapService.findMapByVillageId(villageId);
        return new SuccessApiResult(mapDto);
    }
    /**
     * 初始化地图校正页面 (判断是否已经校正地图)
     *
     * */
    @RequestMapping(value = "/initCoordinate", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult initCoordinate(@CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        double[][] theta = mapService.GetTheta(villageId);
        if(theta==null){
            return new SuccessApiResult(false);
        }
        return new SuccessApiResult(true);
    }


}
