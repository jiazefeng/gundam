package com.maxrocky.gundam.service.robotAllot.inf;

import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.domain.robot.dto.RobotStrategyDto;
import com.maxrocky.gundam.domain.robot.model.RobotStrategy;

import java.util.List;

/**
 * Created by maxrocky on 2016/06/23.
 */
public interface RobotAllotService {
    /**
     * 机器人分配
     *
     * @return
     */
    public RobotStrategy addRobotStrategy(RobotStrategy robotStrategy);

    /**
     * 查询机器人执行的方案
     *
     * @param id
     * @return
     */
    ApiResult getRobotStrategyById(String id, String villageId);

    /**
     * 查询机器人全部策略
     *
     * @param id
     * @return
     */
    ApiResult searchStrategyById(String id);
    //检索当前时间未执行完成
    ApiResult searchStrategyListByRobotId(String id);
    //已执行完成的策略
    public List<RobotStrategyDto> searchStrategyListById(String id);
    //未执行完成的策略
    public List<RobotStrategyDto> searchStrategyListByRId(String id);

    boolean deleteRobotStrategy(RobotStrategy robotStrategy);
}
