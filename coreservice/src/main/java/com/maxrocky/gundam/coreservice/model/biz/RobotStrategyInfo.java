package com.maxrocky.gundam.coreservice.model.biz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maxrocky.gundam.commons.configs.Enums;
import com.maxrocky.gundam.commons.model.biz.Graph;
import com.maxrocky.gundam.commons.model.biz.GraphNode;
import com.maxrocky.gundam.domain.robot.model.RobotStrategy;

import java.util.*;

/**
 * Created by yuer5 on 16/7/19.
 */
public class RobotStrategyInfo {

    private String robotStrategyId;
    private String strategyId;
    private Date startTime;
    private int status;         //执行状态
    private int stategyType;    //任务类型
    private int repeatStatus;   //循环类型

    private List<RobotTask> taskItemList;

    @JsonIgnore
    private boolean canActive;

    public RobotStrategyInfo() { }

    public RobotStrategyInfo(String robotStrategyId, String strategyId, Date startTime, int status, int stategyType,int repeatStatus) {
        this.robotStrategyId = robotStrategyId;
        this.strategyId = strategyId;
        this.startTime = startTime;
        this.status = status;
        this.stategyType = stategyType;
        this.repeatStatus = repeatStatus;
    }

    public String getRobotStrategyId() {
        return robotStrategyId;
    }

    public void setRobotStrategyId(String robotStrategyId) {
        this.robotStrategyId = robotStrategyId;
    }

    public String getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }

    public Date getStartTime() {
        if(this.getStategyType() == Enums.RobotStrategyType.Scheduled.ordinal()) {
            this.startTime = this.SetToTody(this.startTime);
        }
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStategyType() {
        return stategyType;
    }

    public void setStategyType(int stategyType) {
        this.stategyType = stategyType;
    }

    public int getRepeatStatus() {
        return repeatStatus;
    }

    public void setRepeatStatus(int repeatStatus) {
        this.repeatStatus = repeatStatus;
    }

    public boolean isCanActive() {
        return canActive;
    }

    public void setCanActive(boolean canActive) {
        this.canActive = canActive;
    }

    public List<RobotTask> getTaskItemList() {
        return taskItemList;
    }

    public void setTaskItemList(List<RobotTask> taskItemList) {
        this.taskItemList = taskItemList;
    }

    private Date SetToTody(Date date){

        Calendar calendarCurr = Calendar.getInstance();
        calendarCurr.setTime(new Date());

        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(date);

        calendarStart.set(Calendar.YEAR, calendarCurr.get(Calendar.YEAR));
        calendarStart.set(Calendar.MONTH, calendarCurr.get(Calendar.MONTH));
        calendarStart.set(Calendar.DATE, calendarCurr.get(Calendar.DATE));
        date =calendarStart.getTime();

        return date;
    }

    /**
     * 计算是否策略启动的时间，
     * if（ StartTime < 当前时间 < StartTime+15min )
     *      return true;
     * else
     *      return false;
     * @return
     */
    private static boolean isInActiveTime(RobotStrategyInfo robotStrategy){

        Calendar calendarCurr = Calendar.getInstance();
        calendarCurr.setTime(new Date());

//        TimeZone tzInShanghai = TimeZone.getTimeZone("Asia/Shanghai");
//        Calendar calendarStart = Calendar.getInstance(tzInShanghai);
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(robotStrategy.getStartTime());

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(calendarStart.getTime());
        calendarEnd.add(Calendar.MINUTE, 15);

        if(calendarCurr.after(calendarEnd) || calendarCurr.before(calendarStart))
            return false;
        else
            return true;
    }

    //计算出当前执行任务
    public static RobotStrategyInfo GetCurrStrategy(List<RobotStrategyInfo> list) {

        if(list== null || list.size() ==0)
            return null;

        RobotStrategyInfo ret = null;

        //如果有正在执行的任务
        Optional<RobotStrategyInfo> searchResult = list.stream()
                .filter(a -> a.getStatus() == Enums.RobotStrategyExecuteStatus.Runing.ordinal()
                        && a.getStategyType() == Enums.RobotStrategyType.Immediately.ordinal())
                .findFirst();
        if(searchResult.isPresent())
            return searchResult.get();

        //如果有立即执行的任务
        searchResult = list.stream()
                .filter(a -> a.getStatus() == Enums.RobotStrategyExecuteStatus.WaitForRun.ordinal()
                        && a.getStategyType() == Enums.RobotStrategyType.Immediately.ordinal())
                .findFirst();
        if(searchResult.isPresent())
            return searchResult.get();

        //查找最近的周期任务
        Date minDate = null;
        int index = -1;
        for(int i=0; i<list.size(); i++){
//            Calendar calendarStart = Calendar.getInstance();
//            calendarStart.setTime(list.get(i).getStartTime());
//            if(list.get(i).getStatus() == Enums.RobotStrategyType.Scheduled.ordinal()) {
//                calendarStart.set(Calendar.YEAR, calendarCurr.get(Calendar.YEAR));
//                calendarStart.set(Calendar.MONTH, calendarCurr.get(Calendar.MONTH));
//                calendarStart.set(Calendar.DATE, calendarCurr.get(Calendar.DATE));
//                list.get(i).setStartTime(calendarStart.getTime());
//            }

            boolean CanActive = RobotStrategyInfo.isInActiveTime(list.get(i));
            if( list.get(i).getStategyType() == Enums.RobotStrategyType.Scheduled.ordinal()
                    && CanActive
                    && list.get(i).getStatus() != Enums.RobotStrategyExecuteStatus.Finish.ordinal()
                    && (minDate == null || minDate.after(list.get(i).getStartTime()))){
                index = i;
                minDate = list.get(i).getStartTime();
            }
        }
        if(index != -1) {
            ret = list.get(index);
        }
        return ret;
    }

    public static List<RobotStrategyInfo> Convert(List<RobotStrategy> list){
        List<RobotStrategyInfo> rets = new ArrayList<>();
        for (RobotStrategy r : list){
            rets.add(new RobotStrategyInfo(r.getId() ,r.getStrategyId(), r.getStartTime(), r.getExecutionState(), r.getStatus(),r.getRepeatStatus()));
        }
        return rets;
    }

    public static RobotStrategyInfo Convert(RobotStrategy item){
        return new RobotStrategyInfo(item.getId(), item.getStrategyId(), item.getStartTime(), item.getExecutionState(), item.getStatus(),item.getRepeatStatus());
    }


    /**
     * 把任务项转化为一系列的GPS坐标点
     * @param taskIndex
     * @return
     */
    public List<GraphNode> GetFootprintsByTaskItemIndex(int taskIndex, Graph map, double[][] theta, GraphNode startNode){

        this.taskItemList.get(taskIndex).SetPathAndFootprint(map, theta, startNode);
        return this.taskItemList.get(taskIndex).getFootPrints();
    }

}
