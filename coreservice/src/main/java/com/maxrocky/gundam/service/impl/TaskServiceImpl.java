package com.maxrocky.gundam.service.impl;

import com.maxrocky.gundam.commons.configs.Enums;
import com.maxrocky.gundam.commons.model.biz.GraphNode;
import com.maxrocky.gundam.coreservice.model.biz.*;
import com.maxrocky.gundam.domain.map.repository.BussinessMapRepository;
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
import com.maxrocky.gundam.service.TaskService;
//import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by yuer5 on 16/6/9.
 */
@Repository
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskItemRepository itemsRepository;
    @Autowired
    private TaskInfoRepository tasksRepository;
    @Autowired
    private StrategyTasksRepository strategyTasksRepository;
    @Autowired
    private BussinessMapRepository mapRepository;
//    @Autowired
//    private MapperFacade mapper = null;
    @Autowired
    private RobotStratrgyRepository robotStratrgyRepository;

    @Autowired
    private StrategyRepository strategyRepository;

    @Override
    public List<RobotTask> getAllTasks(String strategyId) {
        return this.getTaskItemByStrategy(strategyId);
    }


    private List<RobotTask> getTaskItemByStrategy(String strategyId) {
        List<RobotTask> rets = new ArrayList<>();
        List<StrategyTasks> tasks = strategyTasksRepository.getStrategyTasks(strategyId);
        Comparator<StrategyTasks> c = (p, o) -> {return p.getOrderly()- o.getOrderly();};
        tasks.sort(c);
        int itemid = 1;
        for(int i=0; i<tasks.size(); i++){
            if(tasks.get(i).getTaskId() != -1) {
                TaskInfo detail = this.GetTask(tasks.get(i).getTaskId());
                for (TaskItem item : detail.getTaskitems()) {
                    if (item.getTaskType() == Enums.TaskItemType.Move.ordinal()) {
                        rets.add(new RobotTaskMove(itemid++, item.getTaskId(), item.getId(),
                                item.getMapPointFromX(), item.getMapPointFromY(),
                                item.getMapPointToX(), item.getMapPointToY()));
                    } else if (item.getTaskType() == Enums.TaskItemType.Gurad.ordinal()) {
                        rets.add(new RobotTaskGuard(itemid++, item.getTaskId(), item.getId(),
                                item.getMapPointToX(), item.getMapPointToY(),
                                item.getStayMinutes()));
                    } else if (item.getTaskType() == Enums.TaskItemType.Charging.ordinal()) {
                        rets.add(new RobotTaskCharging(itemid++, item.getTaskId(), item.getId()));
                    }
                }
            }else{
                rets.add(new RobotTaskMoveToChargingSite(itemid++));
                rets.add(new RobotTaskCharging(itemid++, -1, -1));
                if(i<tasks.size()-1){
                    TaskInfo detailnext = this.GetTask(tasks.get(i+1).getTaskId());
                    rets.add(new RobotTaskMove(itemid++, null,
                            new GraphNode(1, "1", "1",
                                    detailnext.getTaskitems().get(0).getMapPointFromX(),
                                    detailnext.getTaskitems().get(0).getMapPointFromY())));
                }
            }
        }
        return rets;
    }

    @Override
    public TaskInfo GetTask(int taskId) {
        TaskInfo ret = tasksRepository.Retrive(taskId);
        ret.setTaskitems(itemsRepository.List(taskId));
        return ret;
    }

    @Override
    public List<String> getRobotIdsOfStrategy(String strategyId) {
        List<RobotStrategy> rs = robotStratrgyRepository.getByStrategy(strategyId);
        List<String> robotIds = rs.stream().map(a -> a.getRobotId()).collect(Collectors.toList());
        return robotIds;
    }

    @Override
    public Date GetStrategyStartTime(String robotId) {

        List<RobotTask> tasks = new ArrayList<>();

        RobotStrategy robotStrategy = robotStratrgyRepository.getStrategyId(robotId);

        return robotStrategy.getStartTime();
    }

    @Override
    public Strategy getStrategy(String strategyId) {
        return strategyRepository.getStrategyById(strategyId);
    }

//    @Override
//    public RobotStrategy GetCurrStrategy(String robotId) {
//
//        List<RobotStrategy> robotStrategyList = robotStratrgyRepository.getByRobot(robotId);
//        RobotStrategy curr = null;
//        for(RobotStrategy rs : robotStrategyList){
//            if(isInActive(rs)){
//                curr = rs;
//            }
//        }
//        return curr;
//    }
    @Override
    public List<RobotStrategy> GetAllStrategy(String robotId){

        //过滤掉已经执行的任务
        List<RobotStrategy> strategies = robotStratrgyRepository.getByRobot(robotId);
        List<RobotStrategy> rets = new ArrayList<>();
        if(strategies!=null && strategies.size()>0){
            for(RobotStrategy rs: strategies){
//                if(rs.getStatus() == Enums.RobotStrategyType.Scheduled.ordinal()
//                    || (rs.getStatus() == Enums.RobotStrategyType.Immediately.ordinal()
//                            && rs.getExecutionState() != Enums.RobotStrategyExecuteStatus.Finish.ordinal())){
//                    rets.add(rs);
//                }
                if(rs.getStatus() == Enums.RobotStrategyType.Scheduled.ordinal()&&rs.getExecutionState()!=Enums.RobotStrategyExecuteStatus.Finish.ordinal()
                        || (rs.getStatus() == Enums.RobotStrategyType.Immediately.ordinal()
                        && rs.getExecutionState() != Enums.RobotStrategyExecuteStatus.Finish.ordinal())){
                    rets.add(rs);
                }
            }
        }
        return rets;
    }


    public void SetImmediatelyStartegyExecuteStatue(String robotStrategyId, int executeStatue) {
        RobotStrategy rs = robotStratrgyRepository.getById(robotStrategyId);
        if (rs.getStatus() == Enums.RobotStrategyType.Immediately.ordinal()||rs.getRepeatStatus() == Enums.repeatStatus.NoRepeat.ordinal()) {
            rs.setExecutionState(executeStatue);
            robotStratrgyRepository.updateRobotStrategy(rs);
        }
    }
    //修改策略完成时间
    public void  SetStartegyCompleteTime(String robotStrategyId,Date completeTime){
        RobotStrategy rs = robotStratrgyRepository.getById(robotStrategyId);
        if (rs.getStatus() == Enums.RobotStrategyType.Immediately.ordinal()||rs.getRepeatStatus() == Enums.repeatStatus.NoRepeat.ordinal()) {
            rs.setCompleteTime(completeTime);
            robotStratrgyRepository.updateRobotStrategy(rs);
        }
    }
    public void AddImmediatelyTaskForTest(String strategyId, String robotId){
        RobotStrategy newItem = new RobotStrategy();
        newItem.setCreateDate(new Date());
        newItem.setStatus(Enums.RobotStrategyType.Immediately.ordinal());
        newItem.setStrategyId(strategyId);
        newItem.setRobotId(robotId);
        newItem.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        newItem.setExecutionState(0);
        newItem.setOrder(0);
        newItem.setStartTime(new Time(new Date().getTime()));
        robotStratrgyRepository.addRobotStrategy(newItem);

    }

}
