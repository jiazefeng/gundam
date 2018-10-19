package com.maxrocky.gundam.domain.robot.repository;


import com.maxrocky.gundam.domain.robot.model.RobotInfo;
import com.maxrocky.gundam.hibernate.BaseRepository;

import java.util.List;

/**
 * Created by jiazefeng on 2016/5/26.
 */
public interface RobotRepository extends BaseRepository<RobotInfo> {

    /**
     * 添加机器人
     *
     * @param robotInfo
     * @return
     */
    public boolean addRobot(RobotInfo robotInfo);

    /**
     * 检索全部机器人信息
     *
     * @return
     */
    public List<RobotInfo> getRobotList();

    /**
     * 按条件检索机器人信息
     *
     * @param alarmId 报警信息ID
     * @return
     */
    public List<RobotInfo> getRobotList(String alarmId);

    /**
     * 按视频ID检索对应的机器人信息
     *
     * @param videoId
     * @return
     */
    public List<RobotInfo> getRobotListByVieoId(String videoId);

    /**
     * 按 图片id 检索机器人信息
     *
     * @param pictureId 图片ID
     * @return
     */
    public List<RobotInfo> getRobotByPicId(String pictureId);


    /**
     * 按 小区id 检索机器人信息
     *
     * @param villageId 小区Id
     * @return
     */
    public List<RobotInfo> getRobotByVillageId(String villageId);


    /**
     * 按照 小区ID 查询机器人总条数
     *
     * @param villageId
     * @return
     */
    public int getRobotTotalByVillageId(String villageId);

    /**
     * 按条件检索机器人列表
     *
     * @param startRow
     * @return
     */
    public List<RobotInfo> getStrategyListByItem(RobotInfo strategy, int startRow);

    /**
     * 按id 检索机器人信息
     *
     * @param id
     * @return
     */
    public RobotInfo getRobotInfoById(String id);

    //修改机器人
    public boolean updateRobot(RobotInfo robotInfo);
}
