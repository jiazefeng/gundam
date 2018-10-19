package com.maxrocky.gundam.service.robotAllot.impl;

import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.common.result.SuccessApiResult;
import com.maxrocky.gundam.common.util.DateUtils;
import com.maxrocky.gundam.common.util.IdGen;
import com.maxrocky.gundam.domain.robot.dto.RobotStrategyDto;
import com.maxrocky.gundam.domain.robot.model.RobotInfo;
import com.maxrocky.gundam.domain.robot.model.RobotStrategy;
import com.maxrocky.gundam.domain.robot.repository.RobotRepository;
import com.maxrocky.gundam.domain.robot.repository.RobotStratrgyRepository;
import com.maxrocky.gundam.domain.strategy.model.Strategy;
import com.maxrocky.gundam.domain.strategy.model.StrategyTasks;
import com.maxrocky.gundam.domain.strategy.repository.StrategyRepository;
import com.maxrocky.gundam.service.robotAllot.inf.RobotAllotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by maxrocky on 2016/06/23.
 */
@Repository
public class RobotAllotServiceImpl implements RobotAllotService {
    @Autowired
    private RobotStratrgyRepository robotStratrgyRepository;
    @Autowired
    private RobotRepository robotRepository;
    @Autowired
    private StrategyRepository strategyRepository;

    @Override
    public RobotStrategy addRobotStrategy(RobotStrategy robotStrategy) {
        if (robotStrategy != null) {
//            List<LinkedHashMap<String, String>> strategyList = robotStrategy.getStrategyList();
            RobotStrategy robotStrategy1 = new RobotStrategy();
            robotStrategy1.setId(IdGen.uuid());
            robotStrategy1.setStatus(robotStrategy.getStatus());
            if (robotStrategy.getStatus() == 0) {
                List<RobotStrategy> robotStrategies = robotStratrgyRepository.GetUnfinishedStrategy(robotStrategy.getRobotId());
                for (RobotStrategy robotStrategy2 : robotStrategies) {
                    robotStrategy2.setExecutionState(1);
                    robotStratrgyRepository.updateRobotStrategy(robotStrategy2);
                }
                robotStrategy1.setStrategyId(robotStrategy.getStrategyId());
                robotStrategy1.setRobotId(robotStrategy.getRobotId());
                robotStrategy1.setCreateDate(new Date());
                robotStrategy1.setStartTime(new Time(new Date().getTime()));
                robotStrategy1.setExecutionState(0);
            } else {
                robotStrategy1.setRobotId(robotStrategy.getRobotId());
                robotStrategy1.setStrategyId(robotStrategy.getStrategyId());
                robotStrategy1.setCreateDate(new Date());
                robotStrategy1.setStartTime(robotStrategy.getStartTime());
                robotStrategy1.setRepeatStatus(robotStrategy.getRepeatStatus());
            }
            if (robotStratrgyRepository.addRobotStrategy(robotStrategy1)) {
                return robotStrategy;
            }

//            if (strategyList != null && strategyList.size() > 0) {
//                for (int i = 0; i < strategyList.size(); i++) {
//
//                    RobotStrategy robotStrategy1 = new RobotStrategy();
//
//                    robotStrategy1.setId(IdGen.uuid());
//                    robotStrategy1.setOrder(i);
//                    robotStrategy1.setStatus(robotStrategy.getStatus());
//                    LinkedHashMap<String, String> map = strategyList.get(i);
////                    RobotStrategy robotStrategy2 = robotStratrgyRepository.getStrategyByStrategyId(map.get("id"));
//                    if (robotStrategy.getStatus() == 0) {
//                        List<RobotStrategy> robotStrategyList = robotStratrgyRepository.getByStrategyAndRobot(robotStrategy.getStrategyId(), robotStrategy.getRobotId());
//                        if (robotStrategyList != null && robotStrategyList.size() > 0) {
//                            for (RobotStrategy robotStrategy2 : robotStrategyList) {
////                                robotStrategy2.setExecutionState(0);
//                                if (robotStratrgyRepository.deleteRobotStrategy(robotStrategy2)) {
//                                    robotStrategy1.setRobotId(robotStrategy.getRobotId());
//                                    robotStrategy1.setStrategyId(map.get("id"));
//                                    robotStrategy1.setCreateDate(new Date());
//                                    robotStrategy1.setStartTime(new Date());
//                                    robotStrategy1.setExecutionState(1);
//                                }
//                            }
//                        } else {
//                            robotStrategy1.setRobotId(robotStrategy.getRobotId());
//                            robotStrategy1.setStrategyId(map.get("id"));
//                            robotStrategy1.setCreateDate(new Date());
//                            robotStrategy1.setStartTime(new Date());
//                            robotStrategy1.setExecutionState(1);
//                        }
//
//                    } else {
//                        robotStrategy1.setRobotId(robotStrategy.getRobotId());
//                        robotStrategy1.setStrategyId(map.get("id"));
//                        robotStrategy1.setCreateDate(new Date());
//                        robotStrategy1.setStartTime(robotStrategy.getStartTime());
//                        robotStrategy1.setExecutionState(1);
//                    }
//                    list.add(robotStrategy1);
//                }
//                if (robotStratrgyRepository.addRobotStrategy(list)) {
//                    return robotStrategy;
//                }
//            }
        }
        return null;
    }

    @Override
    public ApiResult getRobotStrategyById(String id, String villageId) {
        ModelMap result = new ModelMap();
        List<RobotStrategy> robotStrategyList = robotStratrgyRepository.getByRobot(id);
        RobotInfo robotInfo = robotRepository.getRobotInfoById(id);
        List<Strategy> strategyList = strategyRepository.getStrategyListByVillage(villageId);
        result.addAttribute("robotInfo", robotInfo);
        result.addAttribute("strategyList", strategyList);
        result.addAttribute("robotStrategyList", robotStrategyList);
        return new SuccessApiResult(result);
    }

    @Override
    public ApiResult searchStrategyById(String id) {
        //已执行完的策略
        List<RobotStrategyDto> robotStrategyDtoList = searchStrategyListById(id);

        //未执行完的策略
        List<RobotStrategyDto> robotStrategyDtoList1 = searchStrategyListByRId(id);

        ModelMap result = new ModelMap();
        result.addAttribute("offRobotStrategyDtoList", robotStrategyDtoList);
        result.addAttribute("noRobotStrategyDtoList", robotStrategyDtoList1);
        return new SuccessApiResult(result);
    }

    @Override
    public ApiResult searchStrategyListByRobotId(String id) {
        List<RobotStrategy> robotStrategyList = robotStratrgyRepository.searchStrategyList(id);
        List<RobotStrategyDto> robotStrategyDtoList = new ArrayList<RobotStrategyDto>();
        if (robotStrategyList != null && robotStrategyList.size() > 0) {
            for (RobotStrategy robotStrategy : robotStrategyList) {
                RobotStrategyDto robotStrategyDto = new RobotStrategyDto();
                Strategy strategy = strategyRepository.getStrategyById(robotStrategy.getStrategyId());
                robotStrategyDto.setStrategyId(robotStrategy.getStrategyId());
                robotStrategyDto.setStrategyName(strategy.getsName());
                robotStrategyDto.setsTime(robotStrategy.getStartTime());
                if (robotStrategy.getRepeatStatus() == 0) {
                    robotStrategyDto.setRepeatState("从不");
                } else {
                    robotStrategyDto.setRepeatState("重复");
                }
                robotStrategyDtoList.add(robotStrategyDto);
            }
        }
        return new SuccessApiResult(robotStrategyDtoList);
    }

    @Override
    public List<RobotStrategyDto> searchStrategyListById(String id) {
        List<RobotStrategy> robotStrategyList = robotStratrgyRepository.getStrategyByRobotId(id);
        List<RobotStrategyDto> robotStrategyDtoList = new ArrayList<RobotStrategyDto>();
        if (robotStrategyList != null && robotStrategyList.size() > 0) {
            for (RobotStrategy robotStrategy : robotStrategyList) {
                RobotStrategyDto robotStrategyDto = new RobotStrategyDto();
                Strategy strategy = strategyRepository.getStrategyById(robotStrategy.getStrategyId());
                robotStrategyDto.setStrategyId(robotStrategy.getStrategyId());
                robotStrategyDto.setStrategyName(strategy.getsName());
                robotStrategyDto.setsTime(robotStrategy.getStartTime());
                if (robotStrategy.getRepeatStatus() == 0) {
                    robotStrategyDto.setRepeatState("从不");
                } else {
                    robotStrategyDto.setRepeatState("重复");
                }
                robotStrategyDtoList.add(robotStrategyDto);
            }
        }
        return robotStrategyDtoList;
    }

    @Override
    public List<RobotStrategyDto> searchStrategyListByRId(String id) {
        //未执行完的策略
        List<RobotStrategy> robotStrategyList = robotStratrgyRepository.searchStrategyList(id);
        List<RobotStrategyDto> robotStrategyDtoList = new ArrayList<RobotStrategyDto>();
        if (robotStrategyList != null && robotStrategyList.size() > 0) {
            for (RobotStrategy robotStrategy : robotStrategyList) {
                RobotStrategyDto robotStrategyDto = new RobotStrategyDto();
                Strategy strategy = strategyRepository.getStrategyById(robotStrategy.getStrategyId());
                robotStrategyDto.setStrategyId(robotStrategy.getStrategyId());
                robotStrategyDto.setStrategyName(strategy.getsName());
                robotStrategyDto.setsTime(robotStrategy.getStartTime());
                if (robotStrategy.getRepeatStatus() == 0) {
                    robotStrategyDto.setRepeatState("从不");
                } else {
                    robotStrategyDto.setRepeatState("重复");
                }
                robotStrategyDtoList.add(robotStrategyDto);
            }
        }
        return robotStrategyDtoList;
    }

    @Override
    public boolean deleteRobotStrategy(RobotStrategy robotStrategy) {
        List<RobotStrategy> robotStrategyList = robotStratrgyRepository.getRobotStrategy(robotStrategy);
        if (robotStrategyList != null && robotStrategyList.size() > 0) {
            for (RobotStrategy robotStrategy1 : robotStrategyList) {
                if (robotStratrgyRepository.deleteRobotStrategy(robotStrategy1)) {
                    return true;
                }
            }
        }
        return false;
    }
}
