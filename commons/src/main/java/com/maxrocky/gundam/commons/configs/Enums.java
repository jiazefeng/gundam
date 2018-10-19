package com.maxrocky.gundam.commons.configs;

/**
 * Created by yuer5 on 16/6/9.
 */
public class Enums {

    public enum TaskItemType {
        Gurad,  //站岗
        Move,   //移动
        Charging,   //充电
        MoveToNearestChargeSite,   //移动
        OnService   //服务任务
    }

    public enum RobotWorkMode {
        RealtimeControl, //实时控制
        RemoteTask,      //远程任务
        OnSchedule,      //正常任务
        Idle,            //空闲状态
        OnFinishRemote   //结束远程控制状态（状态切换中）
    }

    public enum RobotReleaseSubsequentActions {
        ContinueTaskByPendingLocation,  //返回任务中断地址继续执行任务
        ContinueTaskByCurrLocation,     //原地继续执行任务
        ClearTaskAndWaitCommand         //清空任务原地等待
    }

    public enum RemoteControlType{
        MoveTask,   //任务移动
        Adjustment  //姿态调整
    }

    public enum ControlSwitch {
        Release,    //自动
        OnControl  //手动
    }

    public enum RobotStrategyType{
        Immediately, //立即执行
        Scheduled    //周期执行
    }

    public enum RobotStrategyExecuteStatus{
        WaitForRun, //等待执行
        Finish,     //执行结束
        Runing      //正在执行
    }
    public enum repeatStatus{
        NoRepeat,    //只执行一次
        Repeat //循环执行
    }
}
