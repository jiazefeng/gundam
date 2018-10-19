package com.maxrocky.gundam.domain.robot.repository;

import com.maxrocky.gundam.domain.robot.model.RobotStrategy;
import com.maxrocky.gundam.domain.strategy.model.StrategyTasks;

import java.util.List;

/**
 * Created by maxrocky on 2016/06/23.
 */
public interface RobotStratrgyRepository {
    /**
     * 机器人分配
     *
     * @param robotStrategyList
     * @return
     */
    public boolean addRobotStrategy(List<RobotStrategy> robotStrategyList);

    public boolean addRobotStrategy(RobotStrategy robotStrategy);

    /**
     * 按照 机器人ID 查询策略
     *
     * @param robotId
     * @return
     */
    public RobotStrategy getStrategyId(String robotId);
//    /**
//     * 按照 策略ID 查询信息
//     *
//     * @param strategyId
//     * @return
//     */
//    public RobotStrategy getStrategyByStrategyId(String strategyId);

    /**
     * 删除 机器人与策略的关系
     *
     * @param robotStrategy
     * @return
     */
    public boolean deleteRobotStrategy(RobotStrategy robotStrategy);

    /**
     * 修改 机器人与策略的关系
     *
     * @param robotStrategy
     * @return
     */
    public boolean updateRobotStrategy(RobotStrategy robotStrategy);
    List<RobotStrategy> getByStrategy(String strategyId);
    List<RobotStrategy> getByStrategyAndRobot(String strategyId,String robotId);
    List<RobotStrategy> getByRobot(String robotId);
    //检索全部已执行完的策略
    List<RobotStrategy> getStrategyByRobotId(String robotId);
    //检索全部未执行完的策略
    List<RobotStrategy> searStrategyByRobotId(String robotId);
    //检索当前时间未执行完成
    List<RobotStrategy> searchStrategyList(String robotId);
    List<RobotStrategy> getRobotStrategy(RobotStrategy robotStrategy);

    RobotStrategy getById(String robotStrategyId);
    //获取单个机器人的所有未完成的即时任务
    List<RobotStrategy> GetUnfinishedStrategy(String robotId);
}
