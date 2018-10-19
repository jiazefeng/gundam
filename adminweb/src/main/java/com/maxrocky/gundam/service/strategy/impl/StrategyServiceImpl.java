package com.maxrocky.gundam.service.strategy.impl;


import com.maxrocky.gundam.common.util.HttpClient;
import com.maxrocky.gundam.domain.robot.model.RobotInfo;
import com.maxrocky.gundam.domain.robot.model.RobotStrategy;
import com.maxrocky.gundam.domain.robot.repository.RobotRepository;
import com.maxrocky.gundam.domain.robot.repository.RobotStratrgyRepository;
import com.maxrocky.gundam.domain.strategy.dto.SchemeDto;
import com.maxrocky.gundam.domain.strategy.dto.StrategyTaskDTO;
import com.maxrocky.gundam.domain.strategy.model.Strategy;
import com.maxrocky.gundam.domain.strategy.model.StrategyTasks;
import com.maxrocky.gundam.domain.strategy.repository.StrategyRepository;
import com.maxrocky.gundam.domain.strategy.repository.StrategyTasksRepository;
import com.maxrocky.gundam.service.strategy.inf.StrategyService;

import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.common.result.SuccessApiResult;
import com.maxrocky.gundam.common.util.IdGen;
import com.maxrocky.gundam.domain.strategy.dto.StrategyDTO;
import com.maxrocky.gundam.domain.task.model.TaskInfo;
import com.maxrocky.gundam.service.strategy.inf.StrategyService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jiazefeng on 16/6/13.
 */
@Repository
public class StrategyServiceImpl implements StrategyService {

    @Autowired
    private StrategyRepository strategyRepository;
    @Autowired
    private StrategyTasksRepository strategyTasksRepository;
    @Autowired
    private RobotRepository robotRepository;
    @Autowired
    private RobotStratrgyRepository robotStratrgyRepository;

    @Override
    public ApiResult getStrategyList(String villageId) {
        int mapId = 0;
        Strategy strategy1 = new Strategy();
        strategy1.setVillageId(villageId);
//        if (villageId != null && !villageId.equals("")) {
//            mapId = strategyRepository.getMapIdByVillageId(villageId);
//            strategy1.setMapId(mapId);
//        }
        int count = strategyRepository.getStrategyTotal(strategy1);
        List<Strategy> strategyList = strategyRepository.getStrategyListByVillage(villageId);
        List<StrategyDTO> strategyDTOList = new ArrayList<StrategyDTO>();
        if (strategyList != null && strategyList.size() > 0) {
            for (Strategy strategy : strategyList) {
                StrategyDTO strategyDTO = new StrategyDTO();
                strategyDTO.setStrategyId(strategy.getsId());
                strategyDTO.setStrategyName(strategy.getsName());
                strategyDTO.setCreateTime(strategy.getCreateOn().getTime());
                strategyDTO.setUpdateTime(strategy.getModifyOn().getTime());
                strategyDTO.setET(strategy.getET());

                List<StrategyTasks> strategyTasksList = strategyTasksRepository.getStrategyTasks(strategy.getsId());
                String taskName = "";
                if (strategyTasksList != null && strategyTasksList.size() > 0) {
                    for (StrategyTasks strategyTasks : strategyTasksList) {
                        if (strategyTasks.getTaskId() == -1) {
                            taskName += "充电+";
                        } else {
                            TaskInfo taskInfo = strategyTasksRepository.getTaskInfoById(strategyTasks.getTaskId());
                            if (taskInfo != null) {
                                taskName += taskInfo.getName() + "+";
                            }
                        }
                    }
                    strategyDTO.setTaskName(taskName.substring(0, taskName.length() - 1));
                }
//                List<TaskInfo> taskInfoList = strategyTasksRepository.getTaskInfoList(strategy.getsId());
//                if (taskInfoList != null && taskInfoList.size() > 0) {
//                    String taskName = "";
//                    for (TaskInfo taskInfo : taskInfoList) {
//                        taskName += taskInfo.getName() + "+";
//                    }
//                    strategyDTO.setTaskName(taskName.substring(0, taskName.length() - 1));
//                }
                strategyDTOList.add(strategyDTO);
            }
        }
        ModelMap result = new ModelMap();
        result.addAttribute("count", count);
        result.addAttribute("strategyList", strategyDTOList);
        return new SuccessApiResult(result);
    }

    @Override
    public List<StrategyDTO> getStrategyDTOList(String villageId) {
        Strategy strategy1 = new Strategy();
        strategy1.setVillageId(villageId);
        List<Strategy> strategyList = strategyRepository.getStrategyListByVillage(villageId);

        List<StrategyDTO> strategyDTOList = new ArrayList<StrategyDTO>();
        if (strategyList != null && strategyList.size() > 0) {
            for (Strategy strategy : strategyList) {
                StrategyDTO strategyDTO = new StrategyDTO();
                strategyDTO.setStrategyId(strategy.getsId());
                strategyDTO.setStrategyName(strategy.getsName());
                strategyDTO.setCreateTime(strategy.getCreateOn().getTime());
                strategyDTO.setUpdateTime(strategy.getModifyOn().getTime());
                strategyDTO.setET(strategy.getET());

                List<StrategyTasks> strategyTasksList = strategyTasksRepository.getStrategyTasks(strategy.getsId());
                String taskName = "";
                if (strategyTasksList != null && strategyTasksList.size() > 0) {
                    for (StrategyTasks strategyTasks : strategyTasksList) {
                        if (strategyTasks.getTaskId() == -1) {
                            taskName += "充电+";
                        } else {
                            TaskInfo taskInfo = strategyTasksRepository.getTaskInfoById(strategyTasks.getTaskId());
                            if (taskInfo != null) {
                                taskName += taskInfo.getName() + "+";
                            }
                        }
                    }
                    strategyDTO.setTaskName(taskName.substring(0, taskName.length() - 1));
                }
                strategyDTOList.add(strategyDTO);
            }
        }
        return strategyDTOList;
    }

    @Override
    public ApiResult getStrategy(String villageId) {
        ModelMap modelMap = new ModelMap();
        List<Strategy> strategyes = strategyRepository.getStrategyListByVillage(villageId);//按小区ID查询方案列表

        List<RobotInfo> robotInfoList = robotRepository.getRobotByVillageId(villageId);
        modelMap.addAttribute("strategyes", strategyes);
        modelMap.addAttribute("robot", robotInfoList);
        return new SuccessApiResult(modelMap);
    }

    @Override
    public ApiResult getStrategyList(StrategyDTO strategyDTO) {
        Strategy strategy1 = new Strategy();
        strategy1.setsId(strategyDTO.getStrategyId());
        int count = strategyRepository.getStrategyTotal(strategy1);
        List<Strategy> strategyList = strategyRepository.getStrategyList(strategy1, strategyDTO.getVillage(), strategyDTO.getIndex());
        List<StrategyDTO> strategyDTOList = new ArrayList<StrategyDTO>();
        if (strategyList != null && strategyList.size() > 0) {
            for (Strategy strategy : strategyList) {
                StrategyDTO strategyDTO1 = new StrategyDTO();
                strategyDTO1.setStrategyId(strategy.getsId());
                strategyDTO1.setStrategyName(strategy.getsName());
                strategyDTO1.setCreateTime(strategy.getCreateOn().getTime());
                strategyDTO1.setUpdateTime(strategy.getModifyOn().getTime());
                strategyDTO1.setET(strategy.getET());
                strategyDTO1.setOperator(strategy.getModifyBy());

                strategyDTO1.setOverState(strategy.getOverStatus());
                if (strategy.getOverStatus() == 1) {
                    strategyDTO1.setStatues("重复执行");
                }
                if (strategy.getOverStatus() == 2) {
                    Strategy scheme = strategyRepository.getStrategyById(strategy.getSchemeId());
                    strategyDTO1.setStatues("执行方案【"+scheme.getsName()+"】");
                }
                if (strategy.getOverStatus() == 3) {
                    strategyDTO1.setStatues("返回物业");
                }
                if (strategy.getOverStatus() == 4) {
                    strategyDTO1.setStatues("原地待命");
                }
                List<StrategyTasks> strategyTasksList = strategyTasksRepository.getStrategyTasks(strategy.getsId());
                String taskName = "";
                if (strategyTasksList != null && strategyTasksList.size() > 0) {
                    for (StrategyTasks strategyTasks : strategyTasksList) {
                        if (strategyTasks.getTaskId() == -1) {
                            taskName += "充电+";
                        } else {
                            TaskInfo taskInfo = strategyTasksRepository.getTaskInfoById(strategyTasks.getTaskId());
                            if (taskInfo != null) {
                                taskName += taskInfo.getName() + "+";
                            }
                        }
                    }
                    strategyDTO1.setTaskName(taskName.substring(0, taskName.length() - 1));
                }
                strategyDTOList.add(strategyDTO1);
            }
        }
        ModelMap result = new ModelMap();
        result.addAttribute("count", count);
        result.addAttribute("strategyList", strategyDTOList);
        result.addAttribute("page", strategyDTO.getIndex());
        return new SuccessApiResult(result);
    }

    @Override
    public ApiResult searchStrategyById(String id, String villageId) {
        ModelMap modelMap = new ModelMap();

        Strategy strategy = strategyRepository.getStrategyById(id);//按方案ID查询信息
        SchemeDto schemeDto = new SchemeDto();
        if (strategy != null && strategy.getSchemeId() != null && !strategy.getSchemeId().equals("")) {
            Strategy strategy1 = strategyRepository.getStrategyById(strategy.getSchemeId());
            schemeDto.setsId(strategy1.getsId());
            schemeDto.setsName(strategy1.getsName());
        }
//        List<Strategy> strategyList = strategyRepository.getStrategyList(id);//按方案ID查询方案列表
        List<Strategy> strategyes = strategyRepository.getStrategyListByVillage(villageId);//按小区ID查询方案列表

        List<TaskInfo> taskInfos = strategyTasksRepository.getTaskInfoByVillageId(villageId);//按小区ID查询任务列表
        List<StrategyTasks> strategyTasksList = strategyTasksRepository.getStrategyTasks(id);//按方案ID查询任务列表
        List<StrategyTaskDTO> taskInfoList = new ArrayList<StrategyTaskDTO>();
        if (strategyTasksList != null && strategyTasksList.size() > 0) {
            for (StrategyTasks strategyTasks : strategyTasksList) {
                StrategyTaskDTO strategyTaskDTO = new StrategyTaskDTO();
                if (strategyTasks.getTaskId() == -1) {
                    strategyTaskDTO.setId(strategyTasks.getTaskId());
                    strategyTaskDTO.setName("充电");
                } else {
                    TaskInfo taskInfo = strategyTasksRepository.getTaskInfoById(strategyTasks.getTaskId());
                    if (taskInfo != null) {
                        strategyTaskDTO.setId(strategyTasks.getTaskId());
                        strategyTaskDTO.setName(taskInfo.getName());
                    }
                }
                taskInfoList.add(strategyTaskDTO);
            }
        }

        modelMap.addAttribute("strategy", strategy);
//        modelMap.addAttribute("strategyList", strategyList);
        modelMap.addAttribute("scheme", schemeDto);
        modelMap.addAttribute("strategyes", strategyes);

        modelMap.addAttribute("taskInfoList", taskInfoList);
        modelMap.addAttribute("taskInfos", taskInfos);


        return new SuccessApiResult(modelMap);
    }

    @Override
    public Strategy GetStrategy(String strategyId) {
        Strategy strategy = strategyRepository.getStrategyById(strategyId);//按方案ID查询信息
        return strategy;
    }

    @Override
    public void SaveStrategy(Strategy strategy) {
//        if (strategy.getId() == 0) {
//            strategyRepository.Create(strategy);
//        } else {
//            strategyRepository.Update(strategy);
//            strategyTasksRepository.BatchDelete(strategy.getId());
//        }
//        if(strategy.getTaskIds().size()>0){
//            int i=0;
//            for(int taskId : strategy.getTaskIds()){
//                StrategyTasks st = new StrategyTasks();
//                st.setTaskId(taskId);
//                st.setVillageId(strategy.getVillageId());
//                st.setMapId(strategy.getMapId());
//                st.setOrderly(i++);
//                st.setStrategyBid(strategy.getStrategyBid());
//                st.setStrategyId(strategy.getId());
//                strategyTasksRepository.Create(st);
//            }
//        }
    }

    @Override
    public List<Strategy> GetStrategyList(int mapId) {
        return strategyRepository.List(mapId);
    }

    @Override
    public void DeleteStrategyById(String strategyId) {
        strategyRepository.Delete(strategyId);
        strategyTasksRepository.BatchDelete(strategyId);
    }

    @Override
    public void DelteStrategyByMap(int mapId) {
        strategyRepository.BatchDelete(mapId);
        strategyTasksRepository.BatchDeleteByMap(mapId);

    }

    @Override
    public boolean deleteStrategy(String id) {
        if (strategyRepository.deleteStrategy(id)) {
            List<StrategyTasks> strategyTasksList = strategyTasksRepository.getStrategyTasks(id);
            if (strategyTasksList != null && strategyTasksList.size() > 0) {
                strategyTasksRepository.deleteStrategyTask(strategyTasksList);
            }
//            RobotStrategy robotStrategy = robotStratrgyRepository.getStrategyByStrategyId(id);
            List<RobotStrategy> robotStrategyList = robotStratrgyRepository.getByStrategy(id);
            if (robotStrategyList != null && robotStrategyList.size() > 0) {
                for (RobotStrategy robotStrategy : robotStrategyList) {
                    robotStratrgyRepository.deleteRobotStrategy(robotStrategy);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Strategy addStrategy(StrategyDTO strategyDTO) {
        Strategy strategy = new Strategy();
//        int mapId = 0;
//        if (strategyDTO.getVillage() != null && !strategyDTO.getVillage().equals("")) {
//            mapId = strategyRepository.getMapIdByVillageId(strategyDTO.getVillage());
//        }
        if (strategyDTO != null) {
            strategy.setsId(IdGen.uuid());
            strategy.setsName(strategyDTO.getStrategyName());
            strategy.setET("12");
            strategy.setState(1);
            strategy.setOverStatus(strategyDTO.getOverState());
            strategy.setSchemeId(strategyDTO.getSchemeId());
            strategy.setVillageId(strategyDTO.getVillage());
            strategy.setCreateBy(strategyDTO.getOperator());
            strategy.setModifyBy(strategyDTO.getOperator());
            strategy.setCreateOn(Calendar.getInstance());
            strategy.setModifyOn(Calendar.getInstance());

        }
        if (strategyRepository.addStrategy(strategy)) {
            List<LinkedHashMap<Integer, Integer>> taskList = strategyDTO.getTaskId();
            List<StrategyTasks> list = new ArrayList<StrategyTasks>();
            if (taskList != null && taskList.size() > 0) {
                for (int i = 0; i < taskList.size(); i++) {
                    StrategyTasks strategyTasks = new StrategyTasks();
                    strategyTasks.setId(IdGen.uuid());
                    strategyTasks.setStrategyId(strategy.getsId());
                    strategyTasks.setOrderly(i);
                    LinkedHashMap<Integer, Integer> map = taskList.get(i);
                    strategyTasks.setTaskId(Integer.valueOf(map.get("id")).intValue());
                    list.add(strategyTasks);
                }
                if (strategyTasksRepository.addStrategyTask(list)) {
                    return strategy;
                }
            }
        }
        return null;
//        return new SuccessApiResult(result);
    }

    @Override
    public Strategy updateStrategy(StrategyDTO strategyDTO) {
        Strategy strategy = strategyRepository.getStrategyById(strategyDTO.getStrategyId());
        if (strategy != null) {
            strategy.setsName(strategyDTO.getStrategyName());
            strategy.setOverStatus(strategyDTO.getOverState());
            strategy.setSchemeId(strategyDTO.getSchemeId());
            strategy.setModifyBy(strategyDTO.getOperator());
            strategy.setModifyOn(Calendar.getInstance());

            if (strategyRepository.updateStrategy(strategy)) {
                List<StrategyTasks> strategyTasksList = strategyTasksRepository.getStrategyTasks(strategy.getsId());
                if (strategyTasksList != null && strategyTasksList.size() > 0) {
                    if (strategyTasksRepository.deleteStrategyTask(strategyTasksList)) {
                        List<LinkedHashMap<Integer, Integer>> taskList = strategyDTO.getTaskId();
                        List<StrategyTasks> list = new ArrayList<StrategyTasks>();
                        if (taskList != null && taskList.size() > 0) {
                            for (int i = 0; i < taskList.size(); i++) {
                                StrategyTasks strategyTasks = new StrategyTasks();
                                strategyTasks.setId(IdGen.uuid());
                                strategyTasks.setStrategyId(strategy.getsId());
                                strategyTasks.setOrderly(i);
                                LinkedHashMap<Integer, Integer> map = taskList.get(i);
                                strategyTasks.setTaskId(Integer.valueOf(map.get("id")).intValue());
                                list.add(strategyTasks);
                            }
                            if (strategyTasksRepository.addStrategyTask(list)) {
                                return strategy;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public int GetCountByVillageId(String villageId) {
        int count = strategyRepository.getStrategyTotalByVillageId(villageId);
        return count;
    }
}
