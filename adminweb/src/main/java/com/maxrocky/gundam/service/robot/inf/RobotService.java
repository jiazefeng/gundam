package com.maxrocky.gundam.service.robot.inf;


import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.domain.robot.dto.RobotInfoDTo;
import com.maxrocky.gundam.domain.robot.model.RobotInfo;
import com.maxrocky.gundam.domain.robot.model.RobotStrategy;

import java.awt.*;
import java.util.List;

/**
 * Created by jiazefeng on 2016/5/26.
 */
public interface RobotService {
    /**
     * 按照小区id 检索机器人信息
     *
     * @param villageId
     * @return
     */
    public List<RobotInfoDTo> getRobotListByVillageId(String villageId);

    /**
     * 检索机器人信息
     * @param villageId
     * @return
     */
    public List<RobotInfoDTo> getRobotDtoList(String villageId);

    /**
     * 按照小区id 检索机器人
     *
     * @param villageId
     * @return
     */
    public List<RobotInfoDTo> getRobot(String villageId);

    /**
     * 添加机器人
     *
     * @param robotInfoDTo
     * @return
     */
    public RobotInfo addRobot(RobotInfoDTo robotInfoDTo);

    /**
     * 按条件 检索机器人信息
     *
     * @param robotInfoDTo
     * @return
     */
    public List<RobotInfoDTo> getRobotListByItem(RobotInfoDTo robotInfoDTo);

    /**
     * 按照id查看机器人信息
     *
     * @param id
     * @return
     */
    public ApiResult getRobotById(String id);

    /**
     * 修改机器人
     *
     * @param robotInfoDTo
     * @return
     */
    public RobotInfo updateRobot(RobotInfoDTo robotInfoDTo);

    /**
     * 删除机器人信息
     *
     * @param id
     * @return
     */
    public boolean deleteRobot(String id);

    /**
     * 准备数据
     *
     * @param id
     * @return
     */
    public ApiResult getReadyDataById(String id);
    /**
     * 通过小区ID查机器人数量
     * */
    public int getRobotTotalByVillageId(String villageId);

    //通过机器人ID查询机器人
    public RobotInfo getRobotByRobotId(String id);
}
