package com.maxrocky.gundam.domain.robot.repository;

import com.maxrocky.gundam.domain.robot.model.RobotFunction;
import com.maxrocky.gundam.domain.robot.model.RobotStrategy;

import java.util.List;

/**
 * Created by maxrocky on 2016/06/23.
 */
public interface RobotFunctionRepository {
    /**
     * 添加机器人功能
     *
     * @param robotFunctionList
     * @return
     */
    public boolean addRobotFunction(List<RobotFunction> robotFunctionList);

    /**
     * 按照 机器人ID 查询功能
     *
     * @param robotId
     * @return
     */
    public RobotFunction getFunctionId(String robotId);

    /**
     * 删除 机器人与功能的关系
     *
     * @param robotFunction
     * @return
     */
    public boolean deleteRobotFunction(List<RobotFunction> robotFunction);

    List<RobotFunction> getByFunction(String robotId);
}
