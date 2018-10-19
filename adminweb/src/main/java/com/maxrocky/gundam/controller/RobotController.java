package com.maxrocky.gundam.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.common.result.SuccessApiResult;
import com.maxrocky.gundam.common.util.DateUtils;
import com.maxrocky.gundam.common.util.HttpClient;
import com.maxrocky.gundam.commons.model.biz.RemoteControl;
import com.maxrocky.gundam.commons.model.biz.RobotSimpleInfo;
import com.maxrocky.gundam.domain.map.dto.GraphDto;
import com.maxrocky.gundam.domain.map.dto.MapDto;
import com.maxrocky.gundam.domain.robot.dto.GPSNode;
import com.maxrocky.gundam.domain.robot.dto.RobotDto;
import com.maxrocky.gundam.domain.robot.dto.RobotInfoDTo;
import com.maxrocky.gundam.domain.robot.model.RobotInfo;
import com.maxrocky.gundam.domain.robot.model.RobotStrategy;
import com.maxrocky.gundam.domain.strategy.model.Strategy;
import com.maxrocky.gundam.domain.task.model.TaskItem;
import com.maxrocky.gundam.domain.user.model.UserInfo;
import com.maxrocky.gundam.domain.village.model.Village;
import com.maxrocky.gundam.service.map.inf.MapService;
import com.maxrocky.gundam.service.robot.inf.RobotService;
import com.maxrocky.gundam.service.robotAllot.inf.RobotAllotService;
import com.maxrocky.gundam.service.strategy.inf.StrategyService;
import com.maxrocky.gundam.service.user.inf.UserService;
import com.maxrocky.gundam.service.village.inf.VillageService;
import com.maxrocky.gundam.service.task.inf.TaskService;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 机器人
 * Created by jiazefeng on 23/6/15.
 */
@RestController
@RequestMapping(value = "/robot")
public class RobotController {
    @Autowired
    private RobotService robotService;
    @Autowired
    private UserService userService;
    @Autowired
    private MapService mapService;
    @Autowired
    private VillageService villageService;
    @Autowired
    private StrategyService strategyService;
    @Autowired
    private TaskService taskService;
    /**
     * 添加机器人
     *
     * @param robotInfoDTo
     * @Author JZF
     */
    @RequestMapping(value = "/addRobot", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public ApiResult addRobot(@RequestBody RobotInfoDTo robotInfoDTo, @CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        ModelMap result = new ModelMap();
        robotInfoDTo.setVillageId(villageId);
        RobotInfo robotInfo = robotService.addRobot(robotInfoDTo);
        if(robotInfo!=null){
            result.addAttribute("success", "添加成功");
            HttpClient.httpGet(HttpClient.url + "adminapi/robot/" + villageId + "/refresh");
            int total = robotService.getRobotTotalByVillageId(villageId);
            List<RobotInfoDTo> robotInfos = robotService.getRobotListByVillageId(villageId);
            for(RobotInfoDTo robotInfoDTo1 : robotInfos){
                JSONObject jsonObject =  HttpClient.httpGet(HttpClient.url +"adminapi/robot/" + robotInfoDTo1.getRobotId());
                Object object = jsonObject.get("data");
                JSONObject jsonObject1 = JSONObject.fromObject(object);
                RobotDto  robotDto = (RobotDto) JSONObject.toBean(jsonObject1,RobotDto.class);
                Strategy strategy =  strategyService.GetStrategy(robotDto.getStrategy());
                //-1 离线   0  闲置    1  繁忙
                if(robotDto.getOnline().equals("true")) {
                    if (strategy != null) {
                        robotInfoDTo1.setStrategyName(strategy.getsName());
                        robotInfoDTo1.setIsBusy("1");
                    } else {
                        robotInfoDTo1.setStrategyName("闲置");
                        robotInfoDTo1.setIsBusy("0");
                    }
                }else{
                    robotInfoDTo1.setStrategyName("离线");
                    robotInfoDTo1.setIsBusy("-1");
                }
                robotInfoDTo1.setElectricity(robotDto.getPower()+"%");
            }
            result.addAttribute("count",total);
            result.addAttribute(robotInfos);
        } else {
            result.addAttribute("error", "添加失败");
        }
        return new SuccessApiResult(result);
    }

    /**
     * 初始化机器人列表信息
     *
     * @param tokenId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/robotList", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult getRobotList(@CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) throws Exception {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        ModelMap result = new ModelMap();
        int total = robotService.getRobotTotalByVillageId(villageId);
        List<RobotInfoDTo> robotInfos = robotService.getRobotListByVillageId(villageId);
        for (RobotInfoDTo robotInfoDTo : robotInfos) {
            JSONObject jsonObject = HttpClient.httpGet(HttpClient.url + "adminapi/robot/" + robotInfoDTo.getRobotId());
            Object object = jsonObject.get("data");
            JSONObject jsonObject1 = JSONObject.fromObject(object);
            RobotDto robotDto = (RobotDto) JSONObject.toBean(jsonObject1, RobotDto.class);
            Strategy strategy = strategyService.GetStrategy(robotDto.getStrategy());
            if (robotDto.getOnline().equals("true")) {
                robotInfoDTo.setElectricity(robotDto.getPower() + "%");
                if (robotDto.is_remote()&&!robotDto.getWork_mode().equals("3")) {
                    robotInfoDTo.setStrategyName("临时任务");
                    robotInfoDTo.setIsBusy("1");
                } else {
                    if (strategy != null) {
                        robotInfoDTo.setStrategyName(strategy.getsName());
                        robotInfoDTo.setIsBusy("1");
                    } else {
                        robotInfoDTo.setStrategyName("闲置");
                        robotInfoDTo.setIsBusy("0");
                    }
                }
            } else {
                robotInfoDTo.setStrategyName("离线");
                robotInfoDTo.setIsBusy("-1");
            }

        }
        result.addAttribute("count", total);
        result.addAttribute(robotInfos);
        return new SuccessApiResult(result);
    }

    /**
     * 按条件检索 机器人列表信息
     *
     * @param tokenId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/robotListByItem", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public ApiResult getRobotListByItem(@RequestBody RobotInfoDTo robotInfoDTo1, @CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) throws Exception {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        ModelMap result = new ModelMap();
        robotInfoDTo1.setVillageId(villageId);
        int total = robotService.getRobotTotalByVillageId(robotInfoDTo1.getVillageId());
        List<RobotInfoDTo> robotInfos = robotService.getRobotListByItem(robotInfoDTo1);
        for (RobotInfoDTo robotInfoDTo : robotInfos) {
            JSONObject jsonObject = HttpClient.httpGet(HttpClient.url + "adminapi/robot/" + robotInfoDTo.getRobotId());
            Object object = jsonObject.get("data");
            JSONObject jsonObject1 = JSONObject.fromObject(object);
            RobotDto robotDto = (RobotDto) JSONObject.toBean(jsonObject1, RobotDto.class);
            Strategy strategy = strategyService.GetStrategy(robotDto.getStrategy());
            //-1 离线   0  闲置    1  繁忙
            if (robotDto.getOnline().equals("true")) {
                robotInfoDTo.setElectricity(robotDto.getPower() + "%");
                if (robotDto.is_remote()&&!robotDto.getWork_mode().equals("3")) {
                    robotInfoDTo.setStrategyName("临时任务");
                    robotInfoDTo.setIsBusy("1");
                } else {
                    if (strategy != null) {
                        robotInfoDTo.setStrategyName(strategy.getsName());
                        robotInfoDTo.setIsBusy("1");
                        robotInfoDTo.setStrategyId(robotDto.getStrategy());
                    } else {
                        robotInfoDTo.setStrategyName("闲置");
                        robotInfoDTo.setIsBusy("0");
                    }
                }
            } else {
                robotInfoDTo.setStrategyName("离线");
                robotInfoDTo.setIsBusy("-1");
            }

        }
        result.addAttribute("count", total);
        result.addAttribute(robotInfos);
        result.addAttribute("page", robotInfoDTo1.getIndex());
        return new SuccessApiResult(result);
    }

    /**
     * 通过ID查询机器人信息
     *
     * @Author jzf
     */
    @RequestMapping(value = "/checkRobotById/{id}", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult searchStrategyById(@PathVariable("id") String id, @CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) throws Exception {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        return robotService.getRobotById(id);
    }

    /**
     * 新增或编辑前准备数据
     *
     * @Author jzf
     */
    @RequestMapping(value = "/readyDataById/{id}", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult readyDataById(@PathVariable("id") String id, @CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) throws Exception {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        return robotService.getReadyDataById(id);
    }

    /**
     * 修改机器人信息
     *
     * @param robotInfoDTo
     * @param tokenId
     * @return
     */
    @RequestMapping(value = "/updateRobot", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public ApiResult updateRobot(@RequestBody RobotInfoDTo robotInfoDTo, @CookieValue(value = "vestaToken", required = false) String tokenId) {
        UserInfo userInfos = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfos == null) {
            return new SuccessApiResult("当前未登陆");
        }
        ModelMap result = new ModelMap();
        RobotInfo robotInfo = robotService.updateRobot(robotInfoDTo);
        if(robotInfo!=null){
            result.addAttribute("success", "修改成功");
            List<RobotInfoDTo> robotInfos = robotService.getRobotListByVillageId(robotInfo.getVillageId());
            HttpClient.httpGet(HttpClient.url + "adminapi/robot/" + robotInfo.getVillageId() + "/refresh");
            for(RobotInfoDTo robotInfoDTo1 : robotInfos){
                JSONObject jsonObject =  HttpClient.httpGet(HttpClient.url +"adminapi/robot/" + robotInfoDTo.getRobotId());
                Object object = jsonObject.get("data");
                JSONObject jsonObject1 = JSONObject.fromObject(object);
                RobotDto  robotDto = (RobotDto) JSONObject.toBean(jsonObject1,RobotDto.class);
                Strategy strategy =  strategyService.GetStrategy(robotDto.getStrategy());
                //-1 离线   0  闲置    1  繁忙
                if(robotDto.getOnline().equals("true")) {
                    if (strategy != null) {
                        robotInfoDTo1.setStrategyName(strategy.getsName());
                        robotInfoDTo1.setIsBusy("1");
                    } else {
                        robotInfoDTo1.setStrategyName("闲置");
                        robotInfoDTo1.setIsBusy("0");
                    }
                }else{
                    robotInfoDTo1.setStrategyName("离线");
                    robotInfoDTo1.setIsBusy("-1");
                }
                robotInfoDTo1.setElectricity(robotDto.getPower()+"%");
            }
            result.addAttribute(robotInfos);
        }else {
            result.addAttribute("error","修改失败");
        }
        return new SuccessApiResult(result);
    }

    /**
     * 删除机器人信息
     *
     * @Author jzf
     */
    @RequestMapping(value = "/deleteRobot/{id}", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult deleteStrategy(@PathVariable("id") String id, @CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        ModelMap result = new ModelMap();
        if(robotService.deleteRobot(id)){
            result.addAttribute("success", "删除成功");
            JSONObject jsonObject = HttpClient.httpGet(HttpClient.url + "adminapi/robot/" + villageId + "/refresh");
        }else {
            result.addAttribute("error", "删除失败");
        }
        return new SuccessApiResult(result);
    }
    /**
     * 获取机器人状态
     * @Author lzp
     * */
        @RequestMapping(value = "/getRobotStatus/{id}", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult getRobotStatus(@PathVariable("id") String id, @CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
            MapDto mapDto = mapService.GetMapById(villageId);
            if(mapDto.getMapSrc()==null){
                //20002 没有地图的错误code
                return new SuccessApiResult(20002);
            }
            ModelMap result = new ModelMap();
            if(id.equals("-1")){
                List<RobotInfoDTo> robotInfoDToList = robotService.getRobot(villageId);
                List<RobotInfoDTo> robotInfoDTos = new ArrayList<RobotInfoDTo>();
                for (RobotInfoDTo robotInfoDTo : robotInfoDToList) {
                    JSONObject jsonObject = HttpClient.httpGet(HttpClient.url + "adminapi/robot/" + robotInfoDTo.getRobotId());
                    Object object = jsonObject.get("data");
                    JSONObject jsonObject1 = JSONObject.fromObject(object);
                    RobotDto robotDto = (RobotDto) JSONObject.toBean(jsonObject1, RobotDto.class);
                    if(robotDto.getOnline().equals("true")){
                        robotInfoDTos.add(robotInfoDTo);
                    }
                }
                if(robotInfoDTos.size()>0) {
                    id = robotInfoDTos.get(0).getRobotId();
                }
                result.addAttribute(robotInfoDTos);
            }
            if(!id.equals("-1")) {
                JSONObject jsonObject = HttpClient.httpGet(HttpClient.url + "adminapi/robot/" + id);
                Object object = jsonObject.get("data");
                JSONObject jsonObject1 = JSONObject.fromObject(object);
                RobotDto robotDto = (RobotDto) JSONObject.toBean(jsonObject1, RobotDto.class);
                RobotInfo robotInfo = robotService.getRobotByRobotId(robotDto.getRobotid());
                RobotInfoDTo robotInfoDTo = new RobotInfoDTo();
                TaskItem taskItem = new TaskItem();
                robotInfoDTo.setRobotName(robotInfo.getRobotName());
                robotInfoDTo.setRobotId(robotInfo.getRobotId());
                robotInfoDTo.setIs_remote(robotDto.is_remote());
                if(robotDto.getRemote_target_map_point()!=null)
                robotInfoDTo.setRemote_target_map_point(robotDto.getRemote_target_map_point());
                String startTime = DateUtils.format(robotDto.getStrategyStartTime());
                robotInfoDTo.setStartTime(startTime);
                Strategy strategy = strategyService.GetStrategy(robotDto.getStrategy());
                robotInfoDTo.setPosition(robotDto.getMap_position());
                if (robotDto.getOnline().equals("true")) {
                    robotInfoDTo.setElectricity(robotDto.getPower() + "%");
                    robotInfoDTo.setPose(robotDto.getPose());
                    if (robotDto.is_remote()&&!robotDto.getWork_mode().equals("3")) {
                        robotInfoDTo.setStrategyName("临时任务");
                        if(robotDto.getRelease_target_map_point()==null&&robotDto.getStrategy()==null){
                            robotInfoDTo.setClearTask(true);
                        }
                    }else {
                         if(robotDto.getRelease_target_map_point()==null&&robotDto.getStrategy()==null){
                            robotInfoDTo.setClearTask(true);
                        }
                        if (strategy != null) {
                            robotInfoDTo.setStrategyName(strategy.getsName());
                        } else {
                            robotInfoDTo.setStrategyName("闲置");
                        }
                        if (robotDto.getTaskItemid() == -1) {
                            robotInfoDTo.setStatus("充电中");
                        } else {
                            taskItem = taskService.GetTaskItem(robotDto.getTaskItemid());
                            if (taskItem != null) {
                                if (taskItem.getTaskType() == 1) {
                                    robotInfoDTo.setStatus("巡逻中");
                                } else if (taskItem.getTaskType() == 0) {
                                    robotInfoDTo.setStatus("站岗中");
                                }
                            } else {
                                robotInfoDTo.setStatus("闲置");
                            }
                        }

                    }
                } else {
                    robotInfoDTo.setStrategyName("离线");
                }
                if(taskItem!=null) {
                    result.addAttribute(taskItem);
                }
                result.addAttribute(robotInfoDTo);
            }
       GraphDto graphDto = mapService.GetGraphDto(villageId);
       graphDto.setImgUrl(mapDto.getMapSrc());
       result.addAttribute(graphDto);
       return new SuccessApiResult(result);
    }
    /**
     * 获取机器人动态
     * @Author lzp
     * */
    @RequestMapping(value = "/getRobotStatusInit", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult getRobotStatusInit(@CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        MapDto mapDto = mapService.GetMapById(villageId);
        if (mapDto.getMapSrc() == null) {
            return new SuccessApiResult("error");
        }
        ModelMap result = new ModelMap();
        JSONObject jsonObject = HttpClient.httpGet(HttpClient.url + "adminapi/robotall/map/" + villageId);
        Object object = jsonObject.get("data");
        JSONArray jsonArray = JSONArray.fromObject(object);
        List<RobotDto> robotDtos = (List<RobotDto>) JSONArray.toCollection(jsonArray, RobotDto.class);
        List<RobotInfoDTo> robotInfos = new ArrayList<RobotInfoDTo>();
        for (RobotDto robotDto : robotDtos) {
            RobotInfoDTo robotInfoDTo = new RobotInfoDTo();
            TaskItem taskItem = new TaskItem();
            RobotInfo robotInfo = robotService.getRobotByRobotId(robotDto.getRobotid());
//            robotInfoDTo.setElectricity(robotInfo.getElectricity());
            robotInfoDTo.setRobotName(robotInfo.getRobotName());
            robotInfoDTo.setRobotId(robotInfo.getRobotId());
            String startTime = DateUtils.format(robotDto.getStrategyStartTime());
            robotInfoDTo.setStartTime(startTime);
            Strategy strategy = strategyService.GetStrategy(robotDto.getStrategy());
            robotInfoDTo.setPosition(robotDto.getMap_position());
            if (robotDto.getOnline().equals("true")) {
                robotInfoDTo.setElectricity(robotDto.getPower() + "%");
                robotInfoDTo.setPose(robotDto.getPose());
                if (robotDto.is_remote()) {
                    robotInfoDTo.setStrategyName("临时任务");
                }else {
                    if (strategy != null) {
                        robotInfoDTo.setStrategyName(strategy.getsName());
                    } else {
                        robotInfoDTo.setStrategyName("闲置");
                    }
                    if (robotDto.getTaskItemid() == -1) {
                        robotInfoDTo.setStatus("充电中");
                    } else {
                        taskItem = taskService.GetTaskItem(robotDto.getTaskItemid());
                        if (taskItem != null) {
                            if (taskItem.getTaskType() == 1) {
                                robotInfoDTo.setStatus("巡逻中");
                            } else if (taskItem.getTaskType() == 0) {
                                robotInfoDTo.setStatus("站岗中");
                            }
                        } else {
                            robotInfoDTo.setStatus("闲置");
                        }
                    }
                }
            } else {
                robotInfoDTo.setStrategyName("离线");
            }
            robotInfos.add(robotInfoDTo);
        }
        GraphDto graphDto = mapService.GetGraphDto(villageId);
        graphDto.setImgUrl(mapDto.getMapSrc());
        result.addAttribute(robotInfos);
        result.addAttribute(graphDto);
        return new SuccessApiResult(result);
    }
    /**
     * 获取机器人位置
     * */
    @RequestMapping(value = "/gainRobotPosition/{robotId}", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult gainRobotPosition(@PathVariable("robotId") String robotId){
        ModelMap result = new ModelMap();
        JSONObject jsonObject =  HttpClient.httpGet(HttpClient.url + "adminapi/robot/" + robotId);
        Object object = jsonObject.get("data");
        JSONObject jsonObject1 = JSONObject.fromObject(object);
        RobotDto  robotDto = (RobotDto) JSONObject.toBean(jsonObject1,RobotDto.class);
        double x1 = robotDto.getGps_position().getX();
        double y1 = robotDto.getGps_position().getY();
        GPSNode gpsNode = new GPSNode(x1,y1);
        return new SuccessApiResult(gpsNode);
    }
    /**机器人手动/自动切换
     * robotId(机器人Id)
     * state(操作方式 0-自动  1-手动)
     * subsequentActions(转自动的后续模式)
     * */
    @RequestMapping(value = "/checkController/{robotId}/{state}/{subsequentActions}", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult checkController(@PathVariable("robotId") String robotId ,@PathVariable("state") String state,@PathVariable("subsequentActions") String subsequentActions){
        JSONObject jsonObject =  HttpClient.httpGet(HttpClient.url + "adminapi/robot/" + robotId + "/modeswitch/" + state + "/" + subsequentActions);
        if(jsonObject.get("code").equals("-1")){
            return new SuccessApiResult(jsonObject.get("msg"));
        }
        ModelMap result = new ModelMap();
        Object object = jsonObject.get("data");
        JSONObject jsonObject1 = JSONObject.fromObject(object);
        RobotDto  robotDto = (RobotDto) JSONObject.toBean(jsonObject1,RobotDto.class);
        if(robotDto.getStrategy()!=null&&String.valueOf(robotDto.getTaskItemid())!=null){
            TaskItem taskItem = taskService.GetTaskItem(robotDto.getTaskItemid());
            result.addAttribute(taskItem);
        }
        if(robotDto.getStrategy()==null){
            robotDto.setClearTask(true);
        }
        result.addAttribute(robotDto);
        return new SuccessApiResult(result);
    }
    /**
     * 实时操控机器人
     *
     * */
    @RequestMapping(value = "/control/{robotId}", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public ApiResult control(@PathVariable("robotId") String robotId,@RequestBody RemoteControl remoteControl){
        remoteControl.setRobotId(robotId);
        JSONObject param = JSONObject.fromObject(remoteControl);
        JSONObject jsonObject =  HttpClient.httpPost(HttpClient.url +"adminapi/robot/" + robotId+"/control",param);
        Object object = jsonObject.get("data");
        JSONObject jsonObject1 = JSONObject.fromObject(object);
        RobotDto  robotDto = (RobotDto) JSONObject.toBean(jsonObject1,RobotDto.class);
        return new SuccessApiResult(robotDto);
    }
}
