package com.maxrocky.gundam.service;



import com.maxrocky.gundam.commons.configs.Enums;
import com.maxrocky.gundam.coreservice.model.biz.RobotTask;
import com.maxrocky.gundam.domain.robot.model.RobotStrategy;
import com.maxrocky.gundam.domain.strategy.model.Strategy;
import com.maxrocky.gundam.domain.task.model.TaskInfo;
import com.maxrocky.gundam.domain.task.model.TaskItem;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by yuer5 on 16/6/9.
 */
public interface TaskService {


    List<String> getRobotIdsOfStrategy(String strategyId);

    List<RobotTask> getAllTasks(String strategyId);

    TaskInfo GetTask(int taskId);

    Date GetStrategyStartTime(String robotId);

    List<RobotStrategy> GetAllStrategy(String robotId);

//    RobotStrategy GetCurrStrategy(String robotId);

    Strategy getStrategy(String strategyId);

    void SetImmediatelyStartegyExecuteStatue(String robotStrategyId, int executeStatue);

    void AddImmediatelyTaskForTest(String strategyId, String robotId);

    void SetStartegyCompleteTime(String robotStrategyId, Date completeTime);

}
