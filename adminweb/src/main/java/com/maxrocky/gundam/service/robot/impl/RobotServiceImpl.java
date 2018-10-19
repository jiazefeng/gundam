package com.maxrocky.gundam.service.robot.impl;

import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.common.result.SuccessApiResult;
import com.maxrocky.gundam.common.util.IdGen;
import com.maxrocky.gundam.domain.robot.dto.RobotInfoDTo;
import com.maxrocky.gundam.domain.robot.model.Function;
import com.maxrocky.gundam.domain.robot.model.RobotFunction;
import com.maxrocky.gundam.domain.robot.model.RobotInfo;
import com.maxrocky.gundam.domain.robot.model.RobotStrategy;
import com.maxrocky.gundam.domain.robot.repository.FunctionRepository;
import com.maxrocky.gundam.domain.robot.repository.RobotFunctionRepository;
import com.maxrocky.gundam.domain.robot.repository.RobotRepository;
import com.maxrocky.gundam.domain.robot.repository.RobotStratrgyRepository;
import com.maxrocky.gundam.domain.strategy.model.Strategy;
import com.maxrocky.gundam.domain.strategy.repository.StrategyRepository;
import com.maxrocky.gundam.service.robot.inf.RobotService;
import com.maxrocky.gundam.service.robotAllot.inf.RobotAllotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import java.util.*;

/**
 * Created by maxrocky on 2016/06/23.
 */
@Repository
public class RobotServiceImpl implements RobotService {
    @Autowired
    private RobotRepository robotRepository;
    @Autowired
    private StrategyRepository strategyRepository;
    @Autowired
    private RobotStratrgyRepository robotStratrgyRepository;
    @Autowired
    private FunctionRepository functionRepository;
    @Autowired
    private RobotFunctionRepository robotFunctionRepository;

    @Override
    public List<RobotInfoDTo> getRobotListByVillageId(String villageId) {
        List<RobotInfo> robotInfoList = robotRepository.getRobotByVillageId(villageId);
        List<RobotInfoDTo> robotInfoDToList = new ArrayList<RobotInfoDTo>();
        if (robotInfoList != null && robotInfoList.size() > 0) {

            for (RobotInfo robotInfo : robotInfoList) {
                RobotInfoDTo robotInfoDTo = new RobotInfoDTo();
                robotInfoDTo.setRobotId(robotInfo.getRobotId());
                robotInfoDTo.setRobotName(robotInfo.getRobotName());
                robotInfoDTo.setRobotNumber(robotInfo.getRobotNumber());
                robotInfoDTo.setElectricity(robotInfo.getElectricity());
                robotInfoDToList.add(robotInfoDTo);
            }
        }
//        int total = robotRepository.getRobotTotalByVillageId(villageId);
//        result.addAttribute("count", total);
//        result.addAttribute("robotList", robotInfoDToList);
//        return new SuccessApiResult(result);
        return robotInfoDToList;
    }

    @Override
    public List<RobotInfoDTo> getRobotDtoList(String villageId) {
        List<RobotInfo> robotInfoList = robotRepository.getRobotByVillageId(villageId);
        List<RobotInfoDTo> robotInfoDToList = new ArrayList<RobotInfoDTo>();
        if (robotInfoList != null && robotInfoList.size() > 0) {

            for (RobotInfo robotInfo : robotInfoList) {
                RobotInfoDTo robotInfoDTo = new RobotInfoDTo();
                robotInfoDTo.setRobotId(robotInfo.getRobotId());
                robotInfoDTo.setRobotName(robotInfo.getRobotName());
                robotInfoDTo.setRobotNumber(robotInfo.getRobotNumber());
                robotInfoDTo.setElectricity(robotInfo.getElectricity());
//                RobotStrategy robotStrategy = robotStratrgyRepository.getStrategyId(robotInfo.getRobotId());
//                if (robotStrategy != null) {
//                    if (robotStrategy.getStrategyId() != null && !robotStrategy.getStrategyId().equals("")) {
//                        Strategy strategy = strategyRepository.getStrategyById(robotStrategy.getStrategyId());
//                        robotInfoDTo.setStrategyName(strategy.getsName());
//                    }
//                } else {
//                    robotInfoDTo.setStrategyName("闲置");
//                }
                robotInfoDToList.add(robotInfoDTo);
            }
        }
        return robotInfoDToList;
    }

    @Override
    public List<RobotInfoDTo> getRobot(String villageId) {
        List<RobotInfoDTo> robotInfoDToList = new ArrayList<RobotInfoDTo>();
        List<RobotInfo> robotInfoList = robotRepository.getRobotByVillageId(villageId);
        for (RobotInfo robotInfo : robotInfoList) {
            RobotInfoDTo robotInfoDTo = new RobotInfoDTo();
            robotInfoDTo.setRobotId(robotInfo.getRobotId());
            robotInfoDTo.setRobotName(robotInfo.getRobotName());
            robotInfoDToList.add(robotInfoDTo);
        }
        return robotInfoDToList;
    }

    @Override
    public RobotInfo addRobot(RobotInfoDTo robotInfoDTo) {
        ModelMap result = new ModelMap();
        if (robotInfoDTo != null) {
            RobotInfo robotInfo = new RobotInfo();
            robotInfo.setRobotId(IdGen.uuid());
            robotInfo.setRobotName(robotInfoDTo.getRobotName());
            robotInfo.setRobotNumber(robotInfoDTo.getRobotNumber());
            robotInfo.setStatus(1);
            robotInfo.setStartTime(new Date());
            robotInfo.setCreateOn(Calendar.getInstance());
            robotInfo.setModifyOn(Calendar.getInstance());
            robotInfo.setVillageId(robotInfoDTo.getVillageId());
            if (robotRepository.addRobot(robotInfo)) {
                List<LinkedHashMap<String, String>> funList = robotInfoDTo.getFunList();
                List<RobotFunction> list = new ArrayList<RobotFunction>();
                if (funList != null && funList.size() > 0) {
                    for (int i = 0; i < funList.size(); i++) {
                        RobotFunction robotFunction = new RobotFunction();
                        robotFunction.setId(IdGen.uuid());
                        robotFunction.setrId(robotInfo.getRobotId());

                        LinkedHashMap<String, String> map = funList.get(i);
                        robotFunction.setfId(map.get("id"));
                        list.add(robotFunction);
                    }
                    if (robotFunctionRepository.addRobotFunction(list)) {
                        return robotInfo;
//                        result.addAttribute("success", "添加成功");
//
//                        int total = robotRepository.getRobotTotalByVillageId(robotInfoDTo.getVillageId());
//                        List<RobotInfoDTo> robotInfoDToList = this.getRobotDtoList(robotInfoDTo.getVillageId());
//                        result.addAttribute("count", total);
//                        result.addAttribute("robotList", robotInfoDToList);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public List<RobotInfoDTo> getRobotListByItem(RobotInfoDTo robotInfoDTo) {
        RobotInfo robotInfo1 = new RobotInfo();
        robotInfo1.setVillageId(robotInfoDTo.getVillageId());
        List<RobotInfo> robotInfoList = robotRepository.getStrategyListByItem(robotInfo1, robotInfoDTo.getIndex());
        List<RobotInfoDTo> robotInfoDToList = new ArrayList<RobotInfoDTo>();
        if (robotInfoList != null && robotInfoList.size() > 0) {
            for (RobotInfo robotInfo : robotInfoList) {
                RobotInfoDTo robotInfoDTo1 = new RobotInfoDTo();
                robotInfoDTo1.setRobotId(robotInfo.getRobotId());
                robotInfoDTo1.setRobotName(robotInfo.getRobotName());
                robotInfoDTo1.setRobotNumber(robotInfo.getRobotNumber());
                robotInfoDTo1.setElectricity(robotInfo.getElectricity());
                robotInfoDToList.add(robotInfoDTo1);
            }
        }
//        int total = robotRepository.getRobotTotalByVillageId(robotInfoDTo.getVillageId());
//        result.addAttribute("count", total);
//        result.addAttribute("robotList", robotInfoDToList);
//        result.addAttribute("page", robotInfoDTo.getIndex());
        return robotInfoDToList;
    }

    @Override
    public ApiResult getRobotById(String id) {
        ModelMap result = new ModelMap();
        RobotInfo robotInfo = robotRepository.getRobotInfoById(id);
        RobotInfoDTo robotInfoDTo = new RobotInfoDTo();
        if (robotInfo != null) {
            robotInfoDTo.setRobotId(robotInfo.getRobotId());
            robotInfoDTo.setRobotName(robotInfo.getRobotName());
            robotInfoDTo.setRobotNumber(robotInfo.getRobotNumber());
            robotInfoDTo.setElectricity(robotInfo.getElectricity());
            robotInfoDTo.setCertificate(robotInfo.getCertificate());
//            robotInfoDTo.setRobotType(robotInfo.getRobotType());

//            if (robotInfo.getRobotType().equals("patrol")) {
//                robotInfoDTo.setFunction("巡逻");
//            }
//            if (robotInfo.getRobotType().equals("standGuard")) {
//                robotInfoDTo.setFunction("站岗");
//            }
//            if (robotInfo.getRobotType().equals("HCI")) {
//                robotInfoDTo.setFunction("人机交互");
//            }
//            if (robotInfo.getRobotType().equals("explain")) {
//                robotInfoDTo.setFunction("迎宾讲解");
//            }
            List<RobotFunction> robotFunctionList = robotFunctionRepository.getByFunction(robotInfo.getRobotId());
            if (robotFunctionList != null && robotFunctionList.size() > 0) {
                String fName = "";
                for (RobotFunction robotFunction : robotFunctionList) {
                    Function function = functionRepository.getFunctionById(robotFunction.getfId());
                    fName += function.getfName() + "+";
                }
                robotInfoDTo.setFunction(fName.substring(0, fName.length() - 1));
            }
            RobotStrategy robotStrategy = robotStratrgyRepository.getStrategyId(robotInfo.getRobotId());
            if (robotStrategy != null) {
                if (robotStrategy.getStrategyId() != null && !robotStrategy.getStrategyId().equals("")) {
                    Strategy strategy = strategyRepository.getStrategyById(robotStrategy.getStrategyId());
                    robotInfoDTo.setStrategyName(strategy.getsName());
                }
            } else {
                robotInfoDTo.setStrategyName("闲置");
            }
        }
        result.addAttribute("robot", robotInfoDTo);
        return new SuccessApiResult(result);
    }

    @Override
    public RobotInfo updateRobot(RobotInfoDTo robotInfoDTo) {
        ModelMap result = new ModelMap();
        if (robotInfoDTo != null) {
            RobotInfo robotInfo = robotRepository.getRobotInfoById(robotInfoDTo.getRobotId());
            if (robotInfo != null) {
                robotInfo.setRobotName(robotInfoDTo.getRobotName());
                robotInfo.setRobotNumber(robotInfoDTo.getRobotNumber());
                robotInfo.setModifyOn(Calendar.getInstance());
                if (robotRepository.updateRobot(robotInfo)) {
                    List<RobotFunction> robotFunctionList = robotFunctionRepository.getByFunction(robotInfoDTo.getRobotId());
                    if (robotFunctionList != null && robotFunctionList.size() > 0) {
                        if (robotFunctionRepository.deleteRobotFunction(robotFunctionList)) {
                            List<LinkedHashMap<String, String>> funList = robotInfoDTo.getFunList();
                            List<RobotFunction> list = new ArrayList<RobotFunction>();
                            if (funList != null && funList.size() > 0) {
                                for (int i = 0; i < funList.size(); i++) {
                                    RobotFunction robotFunction = new RobotFunction();
                                    robotFunction.setId(IdGen.uuid());
                                    robotFunction.setrId(robotInfo.getRobotId());

                                    LinkedHashMap<String, String> map = funList.get(i);
                                    robotFunction.setfId(map.get("id"));
                                    list.add(robotFunction);
                                }
                                if (robotFunctionRepository.addRobotFunction(list)) {
                                    return robotInfo;
                                }
                            }
                        }
                    }else {
                        List<LinkedHashMap<String, String>> funList = robotInfoDTo.getFunList();
                        List<RobotFunction> list = new ArrayList<RobotFunction>();
                        if (funList != null && funList.size() > 0) {
                            for (int i = 0; i < funList.size(); i++) {
                                RobotFunction robotFunction = new RobotFunction();
                                robotFunction.setId(IdGen.uuid());
                                robotFunction.setrId(robotInfo.getRobotId());

                                LinkedHashMap<String, String> map = funList.get(i);
                                robotFunction.setfId(map.get("id"));
                                list.add(robotFunction);
                            }
                            if (robotFunctionRepository.addRobotFunction(list)) {
                                return robotInfo;
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    @Override
    public boolean deleteRobot(String id) {
        RobotInfo robotInfo = robotRepository.getRobotInfoById(id);
        if (robotInfo != null) {
            robotInfo.setStatus(0);
            if (robotRepository.updateRobot(robotInfo)) {
                List<RobotFunction> robotFunctionList = robotFunctionRepository.getByFunction(robotInfo.getRobotId());
                if (robotFunctionList != null && robotFunctionList.size() > 0) {
                    robotFunctionRepository.deleteRobotFunction(robotFunctionList);
                }
                RobotStrategy robotStrategy = robotStratrgyRepository.getStrategyId(robotInfo.getRobotId());
                if (robotStrategy != null) {
                    robotStratrgyRepository.deleteRobotStrategy(robotStrategy);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public ApiResult getReadyDataById(String id) {
        ModelMap result = new ModelMap();
        RobotInfo robotInfo = robotRepository.getRobotInfoById(id);//根据id查询机器人信息
        if (robotInfo != null) {
            result.addAttribute("robot", robotInfo);
            List<RobotFunction> robotFunctionList = robotFunctionRepository.getByFunction(id);
            List<Function> functionList = new ArrayList<Function>();
            if (robotFunctionList != null && robotFunctionList.size() > 0) {
                for (RobotFunction robotFunction : robotFunctionList) {
                    Function function = new Function();
                    function = functionRepository.getFunctionById(robotFunction.getfId());
                    functionList.add(function);
                }
            }
            result.addAttribute("functionListById", functionList);
        }
        List<Function> functionList = functionRepository.getFunctionList();//得到全部功能
//        result.addAttribute("robot", robotInfo);
        result.addAttribute("functionList", functionList);


        return new SuccessApiResult(result);
    }

    @Override
    public int getRobotTotalByVillageId(String villageId) {
        return robotRepository.getRobotTotalByVillageId(villageId);
    }

    @Override
    public RobotInfo getRobotByRobotId(String id) {
        RobotInfo robotInfo = robotRepository.getRobotInfoById(id);//根据id查询机器人信息
        return robotInfo;
    }

}
