package com.maxrocky.gundam.coreservice.core;

import com.maxrocky.gundam.commons.algorithm.GraphManager;
import com.maxrocky.gundam.commons.configs.Enums;
import com.maxrocky.gundam.commons.model.biz.Graph;
import com.maxrocky.gundam.commons.model.biz.GraphNode;
import com.maxrocky.gundam.commons.utility.GraphUtility;
import com.maxrocky.gundam.coreservice.managment.RobotSnapshotManagement;
import com.maxrocky.gundam.coreservice.model.biz.*;

import java.util.*;

/**
 * Created by yuer5 on 16/6/12.
 */
public class RobotSnapshot {

    private String robotId;
    private String deviceid;

    private Date lastSyncTime;                  //同步信息 － 数据上行时间
    private RobetUpStreamInfo upStreamInfo;     //同步信息 － 上行数据

    private Graph map;                          //地图寻址 － 图结构描述
    private double[][] theta;                   //地图寻址 － map->gps坐标转换参数
    private int printSize = 2;                  //地图寻址 － TODO:测试用地图的GPS地址超出范围，正式环境需去掉这项

    private int workMode;                       //工作模式 － 见枚举Enums.RobotWorkMode

    private GraphNode taskPendingLocation;      //切换到手动模式 － 任务挂起之前的gps地址
    private int taskPendingWorkMode;            //切换到手动模式 － 任务挂起之前的工作模式
    private RobotTask remoteTask;     //切换到手动模式 － 手动模式包含到任务项列表
    private int directionX;                     //切换到手动模式 － 手动模式前进后退
    private int directionY;                     //切换到手动模式 － 手动模式左转右转

    private List<RobotStrategyInfo> strategyList;       //策略/任务相关 － 当前robot的策略列表
    private String currStrategyId;                      //策略/任务相关 － 机器人策略Id
    private int taskItemPointer;                        //策略/任务相关 － 任务指针，标示当前运行到哪个任务项

    public RobotSnapshot() {
    }

    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public Date getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(Date lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }

    public int getTaskItemPointer() {
        return taskItemPointer;
    }

    public void setTaskItemPointer(int taskItemPointer) {
        this.taskItemPointer = taskItemPointer;
    }

    public int getWorkMode() {
        return workMode;
    }

    public void setWorkMode(int workMode) {
        this.workMode = workMode;
    }

    public GraphNode getTaskPendingLocation() {
        return taskPendingLocation;
    }

    public void setTaskPendingLocation(GraphNode taskPendingLocation) {
        this.taskPendingLocation = taskPendingLocation;
    }

    public int getTaskPendingWorkMode() {
        return taskPendingWorkMode;
    }

    public void setTaskPendingWorkMode(int taskPendingWorkMode) {
        this.taskPendingWorkMode = taskPendingWorkMode;
    }

    public RobotTask getRemoteTask() {
        return remoteTask;
    }

    public void setRemoteTask(RobotTask remoteTask) {
        this.remoteTask = remoteTask;
    }

    public int getDirectionX() {
        return directionX;
    }

    public void setDirectionX(int directionX) {
        this.directionX = directionX;
    }

    public int getDirectionY() {
        return directionY;
    }

    public void setDirectionY(int directionY) {
        this.directionY = directionY;
    }

    public Graph getMap() {
        return map;
    }

    public void setMap(Graph map) {
        this.map = map;
    }

    public double[][] getTheta() {
        return theta;
    }

    public void setTheta(double[][] theta) {
        this.theta = theta;
    }

    public List<RobotStrategyInfo> getStrategyList() {
        return strategyList;
    }

    public void setStrategyList(List<RobotStrategyInfo> strategyList) {
        this.strategyList = strategyList;
    }

    public String getCurrStrategyId() {
        return currStrategyId;
    }

    public void setCurrStrategyId(String currStrategyId) {
        this.currStrategyId = currStrategyId;
    }

    //初始化任务状态
    public void InitWorkMode(){
        //当前时间为策略启动时间，且策略里有任务项，把机器人设置为计划执行模式
//        if(isInActiveTime() && this.taskItemList.size() != 0)

//        if (this.taskItemList.size() != 0)
//            this.workMode = Enums.RobotWorkMode.OnSchedule.ordinal();
//        else
//            this.workMode = Enums.RobotWorkMode.Idle.ordinal();

        RobotStrategyInfo curr = RobotStrategyInfo.GetCurrStrategy(this.strategyList);
        if (curr != null) {
            this.currStrategyId = curr.getStrategyId();
            this.taskItemPointer = 0;
            if (curr.getTaskItemList().size() != 0) {
                this.workMode = Enums.RobotWorkMode.OnSchedule.ordinal();
                return;
            }
        }
        this.setIdleMode();
    }


    public RobetUpStreamInfo getUpStreamInfo() {
        return upStreamInfo;
    }

    /**
     * 接收到机器人上行数据后的处理逻辑
     * @param upStreamInfo
     */
    public void setUpStreamInfo(RobotSnapshotManagement robotManager,  RobetUpStreamInfo upStreamInfo) {

        //更新快照中保存的上行数据
        this.upStreamInfo = upStreamInfo;

        if(upStreamInfo == null)
            return;
        this.lastSyncTime = upStreamInfo.getReportTime();

        if(workMode == Enums.RobotWorkMode.OnSchedule.ordinal()) {
            //任务执行中，评估当前任务项的完成情况
            this.CalcFootprintStack(robotManager);
        }else if(workMode == Enums.RobotWorkMode.OnFinishRemote.ordinal()){
            if(taskPendingLocation == null){
                this.remoteTask = null;
            }
            //远程任务结束中（手动转自动模式，回去执行自动任务前调整机器人的状态）
            double distance = this.upStreamInfo.GetGpsNode("CurrLocation").GpsDistanceOnWGS84(this.taskPendingLocation);
            if(distance<1){
                //如果离挂起点距离<1m,视为已完成状态调整，
                this.workMode = Enums.RobotWorkMode.OnSchedule.ordinal();
                this.taskPendingLocation = null;
                this.remoteTask = null;
            }
        }else if(workMode == Enums.RobotWorkMode.RemoteTask.ordinal()) {
            //远程任务模式
            if(this.remoteTask == null)
                return;
            Boolean isFinishTask = this.remoteTask.IsFinishTask(this.upStreamInfo, this.theta);
            if (isFinishTask)
                this.remoteTask = null;
        }else if(workMode == Enums.RobotWorkMode.Idle.ordinal()){
            //空闲状态，询问是否进行下一轮策略任务
            this.InitWorkMode();
        }
    }

    /**
     * 添加远程任务，移动到某个点
     * @param ToNode
     */
    public void AddRemoteTask(GraphNode ToNode){

        this.workMode = Enums.RobotWorkMode.RemoteTask.ordinal();

        if(ToNode == null) {
            return;
        }
        //robot gps->map
        GraphNode start = this.upStreamInfo.GetMapAxisValue(this.theta, "RemoteTaskStart");
        GraphNode end = new GraphNode(0, "RemoteTaskEnd", "RemoteTaskEnd",
                ToNode.getX(), ToNode.getY());

        RobotTask remoteTask = new RobotTaskMove(1, start, end);
        this.remoteTask = remoteTask;
    }

    /**
     * 结束手动控制模式，后续操作设置
     * @param subsequentActions
     */
    private void EndRemoteMode(int subsequentActions){

        //如果多次操作，其中点过清空任务，挂起点的gps地址为空，机器人直接原地待命
        if(this.taskPendingLocation == null){
            this.setIdleMode();
            return;
        }

        if(subsequentActions == Enums.RobotReleaseSubsequentActions.ContinueTaskByPendingLocation.ordinal()){
            //回到挂起点继续执行任务
            this.workMode = Enums.RobotWorkMode.OnFinishRemote.ordinal();
            this.remoteTask = null;
        }else if (subsequentActions == Enums.RobotReleaseSubsequentActions.ContinueTaskByCurrLocation.ordinal()){
            //原地继续执行任务
            this.workMode = Enums.RobotWorkMode.OnSchedule.ordinal();
            this.taskPendingLocation = null;
            this.remoteTask = null;
        }else if (subsequentActions == Enums.RobotReleaseSubsequentActions.ClearTaskAndWaitCommand.ordinal()){
            //清空任务原地待命
            this.setIdleMode();
        }
    }

    /**
     * 构造下行数据
     * @return
     */
    public RobotDownStreamInfo ContractorDownStream(RobotSnapshotManagement manager) {
        RobotDownStreamInfo downStreamInfo = new RobotDownStreamInfo();
        downStreamInfo.setType(this.workMode);

        if(workMode == Enums.RobotWorkMode.RealtimeControl.ordinal()){
            downStreamInfo.setType(0);
            DownStreamCommand command = new DownStreamCommand();
            command.setDirection(new DownStreamMetadata(this.directionX, 0, 0));
            command.setSpeed(new DownStreamMetadata(0, 0, this.directionY));
            downStreamInfo.setCommand(command);
        }else{
            downStreamInfo.setType(1);
            DownStreamTasks tasks = downStreamInfo.getTasks();
            if (tasks == null)
                tasks = new DownStreamTasks();
            tasks.setType(0);
            List<GraphNode> taskSource = new ArrayList<>();
            if (workMode == Enums.RobotWorkMode.RemoteTask.ordinal()) {
                if(this.remoteTask != null) {
                    this.remoteTask.SetPathAndFootprint(this.map, this.theta, this.upStreamInfo.GetMapAxisValue(this.theta, "robotCurrPosition"));
                    taskSource = remoteTask.getFootPrints();
                }else{
                    taskSource.add(this.upStreamInfo.GetGpsNode("robotCurrPosition"));
                }
            } else if (workMode == Enums.RobotWorkMode.OnSchedule.ordinal()) {
//                RobotStrategyInfo currStrategy = this.strategyList
//                            .stream()
//                            .filter(a -> a.getStrategyId().equals(currStrategyId))
//                            .findFirst()
//                            .get();
                RobotStrategyInfo currStrategy = RobotStrategyInfo.GetCurrStrategy(this.strategyList);

                if(currStrategy != null) {
                    if (currStrategy.getTaskItemList().get(this.getTaskItemPointer()).getTaskType()
                            == Enums.TaskItemType.Charging.ordinal()) {
                        downStreamInfo.setType(0);
                        DownStreamCommand command = new DownStreamCommand();
                        command.setDirection(new DownStreamMetadata(0, 0, 0));
                        command.setSpeed(new DownStreamMetadata(0, 0, 0));
                        command.setCharging(true);
                        downStreamInfo.setCommand(command);
                    } else {
                        taskSource = this.CalcFootprintStack(manager);
//                        RobotTask currTask = currStrategy.getTaskItemList().get(this.getTaskItemPointer());
//                        tasks.setTaskCategory(currTask.getTaskType());
                    }
                }
            } else if(workMode == Enums.RobotWorkMode.OnFinishRemote.ordinal()) {
                taskSource = this.getEndRemoteTaskPoints();
            }
            for (GraphNode node : taskSource) {
                tasks.getPositions().add(new DownStreamMetadata(node.getX(), node.getY(), 0));
            }
            downStreamInfo.setTasks(tasks);
        }

        return downStreamInfo;
    }

    public void SetControlMode(int controlMode,int subsequentActions) {

        //释放控制，切换到自动模式
        if (controlMode == Enums.ControlSwitch.Release.ordinal()
                && (this.workMode == Enums.RobotWorkMode.RemoteTask.ordinal()
                || this.workMode == Enums.RobotWorkMode.RealtimeControl.ordinal())) {
            this.EndRemoteMode(subsequentActions);
            return;
        }

        //切换到手动模式
        if (controlMode == Enums.ControlSwitch.OnControl.ordinal()) {
            //如果正在执行自动任务，挂起任务位置和任务指针
            if(this.workMode == Enums.RobotWorkMode.OnSchedule.ordinal()) {
                this.taskPendingWorkMode = this.workMode;
                this.taskPendingLocation = new GraphNode(0, "Pending", "Pending",
                        this.getUpStreamInfo().getGps().getX(), this.getUpStreamInfo().getGps().getY());
            }else if(this.workMode == Enums.RobotWorkMode.OnFinishRemote.ordinal()){

            }
            this.workMode = Enums.RobotWorkMode.RealtimeControl.ordinal();
        }
    }


    //把当前任务转化为机器人行走的GPS地址列表
    private List<GraphNode> CalcFootprintStack(RobotSnapshotManagement robotManager){
        if(this.theta == null)
            return new ArrayList<>();

        this.EstimateCurrentTaskIndex(robotManager);

        if(this.workMode != Enums.RobotWorkMode.OnSchedule.ordinal())
            return new ArrayList<>();

        //计算任务项
        List<GraphNode> ret = this.GetFootprintsByTaskItemIndex(this.taskItemPointer, this.upStreamInfo.GetMapAxisValue(this.theta, "robotCurrPosition"));

        return ret;
    }

    //把远程任务转换为机器人足迹GPS列表
    private List<GraphNode> getEndRemoteTaskPoints() {

        //robot gps->map
        GraphNode start = this.upStreamInfo.GetMapAxisValue(this.theta, "RemoteTaskStart");
        GraphNode end = GraphUtility.TransferNodeToMap(this.taskPendingLocation, this.theta);

        GraphManager graphManager = new GraphManager();
        List<GraphNode> list = graphManager.DoFindPathByExternalPoint(this.map, start, end);
        List<GraphNode> gpslist = GraphUtility.TransferNodeToGps(list, this.theta);

        List<GraphNode> footprints = GraphUtility.GetGpsNodeFootprint(gpslist, this.printSize);
        for (GraphNode n : footprints) {
            n.setLabel("RemoteTask");
        }

        return footprints;
    }

    //评估当前任务项完成情况并设置任务指针
    private void EstimateCurrentTaskIndex(RobotSnapshotManagement robotManager) {

        RobotStrategyInfo currStrategy = RobotStrategyInfo.GetCurrStrategy(this.strategyList);

        if(currStrategy == null)
            return;

        RobotTask task = currStrategy.getTaskItemList().get(this.taskItemPointer);

        Boolean isFinishTask = task.IsFinishTask(this.upStreamInfo, this.theta);
        if (isFinishTask) {
            if(this.taskItemPointer < currStrategy.getTaskItemList().size() - 1)
                this.TaskMoveNext();
            else {
                //如果是立即执行和单次循环将其执行状态改成已结束
                if(currStrategy.getStategyType() == Enums.RobotStrategyType.Immediately.ordinal()||currStrategy.getRepeatStatus()==Enums.repeatStatus.NoRepeat.ordinal()){
                    currStrategy.setStatus(Enums.RobotStrategyExecuteStatus.Finish.ordinal());
                    robotManager.SetImmediatelyStartegyExecuteStatue(currStrategy.getRobotStrategyId(), Enums.RobotStrategyExecuteStatus.Finish.ordinal());
                }
                    robotManager.SetStartegyCompleteTime(currStrategy.getRobotStrategyId(),new Date());
                this.setIdleMode();
            }
        }
    }

    private void setIdleMode(){
        this.workMode = Enums.RobotWorkMode.Idle.ordinal();
        this.currStrategyId = null;
        this.taskItemPointer = 0;
        this.taskPendingLocation = null;
        this.remoteTask = null;
    }

    //下一项TaskItem
    private void TaskMoveNext(){

        int index = this.taskItemPointer+1;

        RobotStrategyInfo currStrategy = RobotStrategyInfo.GetCurrStrategy(this.strategyList);

        if(currStrategy!=null &&
                ( currStrategy.getTaskItemList().get(index).getTaskType() == Enums.TaskItemType.Gurad.ordinal()
                    || currStrategy.getTaskItemList().get(index).getTaskType() == Enums.TaskItemType.Charging.ordinal()
                )) {
            currStrategy.getTaskItemList().get(index).StartTask();
        }
        this.taskItemPointer = index;
    }

    /**
     * 把任务项转化为一系列的GPS坐标点
     * @param taskIndex
     * @return
     */
    private List<GraphNode> GetFootprintsByTaskItemIndex(int taskIndex, GraphNode startNode){

        RobotStrategyInfo currStrategy = RobotStrategyInfo.GetCurrStrategy(this.strategyList);
        if(currStrategy != null)
            return currStrategy.GetFootprintsByTaskItemIndex(taskIndex, this.map, theta, startNode);
        else
            return null;

    }
}
