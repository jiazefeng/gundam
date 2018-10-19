package com.maxrocky.gundam.coreservice.managment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxrocky.gundam.commons.algorithm.GPoint;
import com.maxrocky.gundam.commons.configs.Enums;
import com.maxrocky.gundam.commons.model.biz.GraphNode;
import com.maxrocky.gundam.commons.model.biz.RemoteControl;
import com.maxrocky.gundam.commons.model.biz.RobotSimpleInfo;
import com.maxrocky.gundam.commons.utility.GraphUtility;
import com.maxrocky.gundam.coreservice.model.biz.*;
import com.maxrocky.gundam.coreservice.core.RobotSnapshot;
import com.maxrocky.gundam.domain.robot.model.RobotStrategy;
import com.maxrocky.gundam.domain.strategy.model.Strategy;
import com.maxrocky.gundam.service.*;
import com.maxrocky.gundam.domain.robot.model.RobotInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.socket.TextMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yuer5 on 16/5/12.
 */
@Repository
public class RobotSnapshotManagementImpl implements RobotSnapshotManagement {

    @Autowired
    private MapService mapService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RobotService robotService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private WebSocketService webSocketService;

    /**
     * 获取机器人快照信息。
     * cache有从cache取，cache没命中从db组织
     * @param robotId
     * @return
     */
    @Override
    public RobotSnapshot GetSnapshot(String robotId) {

        RobotSnapshot snapshot = cacheService.GetRobotSnapshot(robotId);
        if (snapshot == null) {
            snapshot = this.InitRobotSnapInfo(robotId);
            cacheService.SetRobotSnapshot(snapshot);
        }
        return snapshot;
    }

    /**
     * 更新图信息
     * @param villageId
     */
    @Override
    public void UpdateMap(String villageId) {

        List<RobotInfo> robots = robotService.GetByMap(villageId);
        List<String> robotIds = robots.stream().map(a -> a.getRobotId()).collect(Collectors.toList());
        cacheService.UpdateMap(robotIds, mapService.GetGraph(villageId));
    }

    /**
     * 策略更新
     * @param strategyId
     * @throws Exception
     */
    @Override
    public void UpdateStrategy(String strategyId) throws Exception {

        Strategy strategy = taskService.getStrategy(strategyId);
        String villageId = strategy.getVillageId();

        //检查该策略下所有机器人，任务执行中异常
        List<RobotSimpleInfo> robotSimples = this.GetAllRobot(villageId);
//        for(RobotSimpleInfo info: robotSimples){
//            if(info.isOnline() && info.getWork_mode()== Enums.RobotWorkMode.OnSchedule.ordinal()
//                    && info.getStrategy().equals(strategyId)){
//                throw new Exception("机器人" + info.getRobotid() + "正在执行任务");
//            };
//        }

        //逐个机器人更新策略信息
        for (RobotSimpleInfo info : robotSimples) {
            UpdateSingleRobotStrategy(info.getRobotid());
            this.GetDownStream(info.getRobotid());
        }
    }

    /**
     * 更新单个机器人策略信息
     * @param robotId
     */
    @Override
    public void UpdateSingleRobotStrategy(String robotId) {

        RobotSnapshot snapshot = cacheService.GetRobotSnapshot(robotId);

        if (snapshot == null) {
            snapshot = this.InitRobotSnapInfo(robotId);
            cacheService.SetRobotSnapshot(snapshot);
            return;
        }

        //load Strategy and Task from DB
        snapshot.setStrategyList(this.GetAllStrategy(robotId));
        snapshot.InitWorkMode();
        //如果当前任务是即时任务，设置任务为开始执行
        RobotStrategyInfo curr = RobotStrategyInfo.GetCurrStrategy(snapshot.getStrategyList());
        if(curr != null && curr.getStategyType() == Enums.RobotStrategyType.Immediately.ordinal()
                && curr.getStatus() == 0){
            curr.setStatus(Enums.RobotStrategyExecuteStatus.Runing.ordinal());
            taskService.SetImmediatelyStartegyExecuteStatue(curr.getRobotStrategyId(),
                    Enums.RobotStrategyExecuteStatus.Runing.ordinal());
        }
        cacheService.SetRobotSnapshot(snapshot);
        this.GetDownStream(snapshot.getRobotId());
    }

    /**
     * 接收上行数据
     * @param report
     */
    @Override
    public void UpdateReport(RobetUpStreamInfo report) {
        String robotId = report.getId().getRobetID();
        RobotSnapshot snapshot = cacheService.GetRobotSnapshot(robotId);
        if (snapshot == null) {
            snapshot = this.InitRobotSnapInfo(robotId);
        }
        snapshot.setUpStreamInfo(this, report);
        cacheService.SetRobotSnapshot(snapshot);
    }

    /**
     * 获得下行数据
     * @param robotId
     * @return
     */
    @Override
    public RobotDownStreamInfo GetDownStream(String robotId) {

        RobotSnapshot snapshot = this.GetSnapshot(robotId);
        RobotDownStreamInfo downStreamInfo = snapshot.ContractorDownStream(this);

        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(downStreamInfo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        webSocketService.sendMessageToRobot(robotId, new TextMessage(json.getBytes()));

        return downStreamInfo;
    }

    /**
     * 获得地图上所有的机器人
     * @param VillageId
     * @return
     */
    @Override
    public List<RobotSimpleInfo> GetAllRobot(String VillageId) {
        List<RobotInfo> robots = robotService.GetByMap(VillageId);
        List<RobotSimpleInfo> rets = new ArrayList<>();
        for (RobotInfo robot : robots) {
            rets.add(this.GetRobot(robot.getRobotId()));
        }
        return rets;
    }

    @Override
    public RobotSimpleInfo GetRobot(String robotid) {

        RobotSimpleInfo summary = new RobotSimpleInfo();

        RobotSnapshot snapshot = this.GetSnapshot(robotid);
        summary.setDeviceid(snapshot.getDeviceid());
        summary.setRobotid(snapshot.getRobotId());

        if (snapshot.getUpStreamInfo() != null) {
            if(snapshot.getWorkMode() == Enums.RobotWorkMode.RemoteTask.ordinal()){
                if(snapshot.getRemoteTask() != null){
                    summary.setRemote_target_map_point(new GPoint(
                            snapshot.getRemoteTask().getMapPointToX(),
                            snapshot.getRemoteTask().getMapPointToY()));
                }
                summary.setIs_remote(true);
            }else if(snapshot.getWorkMode() == Enums.RobotWorkMode.RealtimeControl.ordinal()) {
                summary.setIs_remote(true);
            }else if(snapshot.getWorkMode() == Enums.RobotWorkMode.Idle.ordinal()){
                //空闲模式为手动   2016-07-28李志鹏修改
                summary.setIs_remote(true);
            } else if(snapshot.getWorkMode() == Enums.RobotWorkMode.OnFinishRemote.ordinal()){
                summary.setIs_remote(false);
                if(snapshot.getTaskPendingLocation() != null) {
                    summary.setRelease_target_map_point(new GPoint(
                            (snapshot.getTaskPendingLocation().getX() - snapshot.getTheta()[0][1]) / snapshot.getTheta()[0][0],
                            (snapshot.getTaskPendingLocation().getY() - snapshot.getTheta()[1][1]) / snapshot.getTheta()[1][0]));
                }
            } else{
                summary.setIs_remote(false);
            }
            summary.setWork_mode(snapshot.getWorkMode());

            String currStrategyId = snapshot.getCurrStrategyId();
            if (currStrategyId != null && !currStrategyId.isEmpty()) {
                RobotStrategyInfo currStrategy = RobotStrategyInfo.GetCurrStrategy(snapshot.getStrategyList());
                if(currStrategy != null && currStrategy.getStrategyId().equals(currStrategyId)){
                    summary.setStrategy(currStrategyId);
                    summary.setStrategyStartTime(currStrategy.getStartTime());
                    if (currStrategy.getTaskItemList().size() != 0) {
                        RobotTask task = currStrategy.getTaskItemList().get(snapshot.getTaskItemPointer());
                        summary.setCurrTaskItemType(task.getTaskType());
                        summary.setTaskId(task.getTaskInfoId());
                        summary.setTaskItemid(task.getTaskItemId());
                    }
                }
            }

//          summary.setAlarm(new int[1]{snapshot.getUpStreamInfo().getStatus().getAlert()});
            summary.setPower(snapshot.getUpStreamInfo().getStatus().getPower());
            summary.setLatest_report(snapshot.getLastSyncTime());
            summary.setPose(snapshot.getUpStreamInfo().getPose());
            summary.setDirection(snapshot.getDirectionY());
            summary.setGps_position(new GPoint(snapshot.getUpStreamInfo().getGps().getX(),
                    snapshot.getUpStreamInfo().getGps().getY()));
            summary.setMap_position(new GPoint(
                    (snapshot.getUpStreamInfo().getGps().getX() - snapshot.getTheta()[0][1]) / snapshot.getTheta()[0][0],
                    (snapshot.getUpStreamInfo().getGps().getY() - snapshot.getTheta()[1][1]) / snapshot.getTheta()[1][0]));
            Date d1 = new Date();
            long diff = d1.getTime() - snapshot.getLastSyncTime().getTime();
            long minuts = diff / (1000 * 60);
            summary.setIsOnline(minuts < 1);


//                if (snapshot.getWorkMode() == Enums.RobotWorkMode.OnSchedule.ordinal()){
//                    summary.setCurrTaskItemType(snapshot.getTaskItemList().get(snapshot.getTaskItemPointer()).getTaskType());
//                }

//                if(snapshot.getTaskItemList().size() != 0){
//                    RobotTask task = snapshot.getTaskItemList().get(snapshot.getTaskItemPointer());
//                    summary.setTaskId(task.getTaskInfoId());
//                    summary.setTaskItemid(task.getTaskItemId());
//                }
        }
        return summary;
    }


    /**
     * 机器人模式切换
     * @param robotid － 机器人Id
     * @param incontrol － 控制模式（手动／自动）
     * @param subsequentActions － 后续操作模式
     * @throws Exception
     */
    @Override
    public void ModeSwitch(String robotid, int incontrol, int subsequentActions) throws Exception {

        RobotSnapshot snapshot = this.GetSnapshot(robotid);

        //异常处理，机器人不在线和机器人状态同步超时
        if(snapshot.getUpStreamInfo() == null) {
            throw new Exception("机器人不在线");
        }else{
            Date d1 = new Date();
            long diff = d1.getTime() - snapshot.getLastSyncTime().getTime();
            long minuts = diff / (1000 * 60);
            if(minuts>5){
                throw new Exception("机器信息同步超时");
            }
        }
        //释放控制,如果是即时执行任务，修改db状态
        if (incontrol == Enums.ControlSwitch.Release.ordinal()
                && subsequentActions == Enums.RobotReleaseSubsequentActions.ClearTaskAndWaitCommand.ordinal()) {
            RobotStrategyInfo currStrategy = RobotStrategyInfo.GetCurrStrategy(snapshot.getStrategyList());
            if (currStrategy !=null && currStrategy.getStategyType() == Enums.RobotStrategyType.Immediately.ordinal()) {
                currStrategy.setStatus(Enums.RobotStrategyExecuteStatus.Finish.ordinal());
                this.SetImmediatelyStartegyExecuteStatue(currStrategy.getRobotStrategyId(), Enums.RobotStrategyExecuteStatus.Finish.ordinal());
            }
        }

        snapshot.SetControlMode(incontrol, subsequentActions);
        cacheService.SetRobotSnapshot(snapshot);
        this.GetDownStream(snapshot.getRobotId());
    }

    /**
     * 从DB初始化机器人快照
     * @param robotId
     * @return
     */
    private RobotSnapshot InitRobotSnapInfo(String robotId) {

        RobotSnapshot snapshot = new RobotSnapshot();
        RobotInfo robot = robotService.Get(robotId);
        String villageId = robot.getVillageId();
        snapshot.setRobotId(robotId);
        snapshot.setDeviceid(robot.getRobotNumber());

        //load MapGraph and Theta from DB
        snapshot.setMap(GraphUtility.regularisationMapInfo(mapService.GetGraph(villageId)));
        snapshot.setTheta(mapService.GetTheta(villageId));

        //load Strategy and Task from DB
        snapshot.setStrategyList(this.GetAllStrategy(robotId));
        snapshot.InitWorkMode();
        //如果当前任务是即时任务，
        RobotStrategyInfo curr = RobotStrategyInfo.GetCurrStrategy(snapshot.getStrategyList());
        if(curr != null && curr.getStategyType() == Enums.RobotStrategyType.Immediately.ordinal()
                && curr.getStatus() == 0){
            curr.setStatus(Enums.RobotStrategyExecuteStatus.Runing.ordinal());
            taskService.SetImmediatelyStartegyExecuteStatue(curr.getRobotStrategyId(),
                    Enums.RobotStrategyExecuteStatus.Runing.ordinal());
        }

        return snapshot;
    }

    public void SetImmediatelyStartegyExecuteStatue(String robotStrategyId, int executeStatue){
        taskService.SetImmediatelyStartegyExecuteStatue(robotStrategyId, executeStatue);
    }
    public void SetStartegyCompleteTime(String robotStrategyId,Date completeTime){
        taskService.SetStartegyCompleteTime(robotStrategyId, completeTime);
    }

    private List<RobotStrategyInfo> GetAllStrategy(String robotId){
        //load Strategy and Task from DB
        List<RobotStrategy> robotStrategies = taskService.GetAllStrategy(robotId);
        List<RobotStrategyInfo> strategyInfos = null;
        if(robotStrategies != null){
            strategyInfos = RobotStrategyInfo.Convert(robotStrategies);
            for(RobotStrategyInfo strategy : strategyInfos){
                strategy.setTaskItemList(taskService.getAllTasks(strategy.getStrategyId()));
            }
        }
        return strategyInfos;
    }

    /**
     * 手动控制
     * @param controlInfo
     */
    @Override
    public void RemoteControl(RemoteControl controlInfo) {

        RobotSnapshot snapshot = this.GetSnapshot(controlInfo.getRobotId());
        if(controlInfo.getControl_type() == Enums.RemoteControlType.MoveTask.ordinal()){
            snapshot.AddRemoteTask(new GraphNode(0, "RemoteTask", "RemoteTask", controlInfo.getMap_position().x, controlInfo.getMap_position().y));
        }
        if(controlInfo.getControl_type() == Enums.RemoteControlType.Adjustment.ordinal()){
            snapshot.setDirectionX((int)controlInfo.getDirection().x);
            snapshot.setDirectionY((int)controlInfo.getDirection().y);
        }
        cacheService.SetRobotSnapshot(snapshot);
        this.GetDownStream(controlInfo.getRobotId());
    }

    @Override
    public void AddImmediatelyTaskForTest(String strategyId, String robotId){

        taskService.AddImmediatelyTaskForTest(strategyId, robotId);

    }

}
