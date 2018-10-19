package com.maxrocky.gundam.coreservice.controller;

import com.maxrocky.gundam.commons.configs.Enums;
import com.maxrocky.gundam.commons.model.biz.RemoteControl;
import com.maxrocky.gundam.commons.model.biz.RobotSimpleInfo;
import com.maxrocky.gundam.coreservice.common.ErrorApiResult;
import com.maxrocky.gundam.coreservice.common.SuccessApiResult;
import com.maxrocky.gundam.coreservice.model.biz.*;
import com.maxrocky.gundam.coreservice.core.RobotSnapshot;
import com.maxrocky.gundam.coreservice.managment.RobotSnapshotManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by yuer5 on 16/5/16.
 */
@RestController
@RequestMapping(value="/adminapi")
public class AdminApiController {

    @Autowired
    private RobotSnapshotManagement manager;

    /**
     * 更新小区Map，
     * 管理后台更新GraphJson的时候需要调用这个接口，更新机器人快照里的地图
     * @param villageId 小区ID
     * @return
     */
    @RequestMapping(value="/map/{villageId}/refresh", method= RequestMethod.GET)
    @ResponseBody
    public ModelMap MapRefresh(@PathVariable String villageId) {
        try {
            manager.UpdateMap(villageId);
            return new SuccessApiResult();
        } catch (Exception e) {
            return new ErrorApiResult("-1",e.getMessage(), e);
        }
    }

    /**
     * 更新策略，
     * 管理后台更新策略的时候需要调用这个接口，用于更新机器人的任务项
     * @param strategyId
     * @return
     */
    @RequestMapping(value="/strategy/{strategyId}/refresh", method= RequestMethod.GET)
    @ResponseBody
    public ModelMap StragegyRefresb(@PathVariable String strategyId) {
        try {
            manager.UpdateStrategy(strategyId);
            return new SuccessApiResult();
        } catch (Exception e) {
            return new ErrorApiResult("-1",e.getMessage(), e);
        }
    }

    /**
     * 更新机器人信息
     * 管理后台更新机器人信息的时候需要调用这个接口，用于更新机器人信息
     * @param robotId
     * @return
     */
    @RequestMapping(value="/robot/{robotId}/refresh", method= RequestMethod.GET)
    @ResponseBody
    public ModelMap RobotTrigger(@PathVariable String robotId) {
        try {
            manager.UpdateSingleRobotStrategy(robotId);
            return new SuccessApiResult();
        } catch (Exception e) {
            return new ErrorApiResult("-1",e.getMessage(), e);
        }
    }

    /**
     * 获取地图上的机器人信息列表
     * @param villageId
     * @return
     */
    @RequestMapping(value="/robotall/map/{villageId}", method= RequestMethod.GET)
    @ResponseBody
    public ModelMap GetAllRobot(@PathVariable String villageId) {
        try {
            return new SuccessApiResult(manager.GetAllRobot(villageId));
        } catch (Exception e) {
            return new ErrorApiResult("-1",e.getMessage(), e);
        }
    }


    /**
     * 获取单个机器人的信息
     * @param robotid
     * @return
     */
    @RequestMapping(value="/robot/{robotid}", method= RequestMethod.GET)
    @ResponseBody
    public ModelMap GetRobot(@PathVariable String robotid) {
        try {
            return new SuccessApiResult(manager.GetRobot(robotid));
        } catch (Exception e) {
            return new ErrorApiResult("-1",e.getMessage(), e);
        }
    }

    /**
     * 机器人模式切换
     * @param robotid
     * @param workmode
     *      0 - release,
     *      1 - remoteControl
     * @param subsequentActions
     *      0 - ContinueTaskByPendingLocation,  //返回任务中断地址继续执行任务
     *      1 - ContinueTaskByCurrLocation,     //原地继续执行任务
     *      2 - ClearTaskAndWaitCommand         //清空任务原地等待
     * @return
     */
    @RequestMapping(value="/robot/{robotid}/modeswitch/{workmode}/{subsequentActions}", method= RequestMethod.GET)
    @ResponseBody
    public ModelMap ModeSwitch(@PathVariable String robotid, @PathVariable int workmode, @PathVariable int subsequentActions) {
        try {
            if(workmode > Enums.ControlSwitch.values().length-1){
                throw new Exception("错误的控制类型");
            }
            if(workmode == Enums.ControlSwitch.Release.ordinal()){
                if(subsequentActions > Enums.RobotReleaseSubsequentActions.values().length-1 ){
                    throw new Exception("手动状态结束错误的后续操作");
                }
            }
            manager.ModeSwitch(robotid, workmode, subsequentActions);
            return new SuccessApiResult(manager.GetRobot(robotid));
        } catch (Exception e) {
            return new ErrorApiResult("-1",e.getMessage(), e);
        }
    }

    /**
     * 机器人远程控制
     * @param robotid
     * @return
     */
    @RequestMapping(value="/robot/{robotid}/control", method= RequestMethod.POST)
    @ResponseBody
    public ModelMap RemoteTask(@PathVariable String robotid, @RequestBody RemoteControl controlInfo) {
        try {
            RobotSimpleInfo robotSimpleInfo = manager.GetRobot(robotid);
            if(!robotSimpleInfo.getIs_remote()) {
                throw new Exception("机器人不是手动控制模式");
            }
            if(controlInfo.getControl_type() > Enums.RemoteControlType.values().length-1){
                throw new Exception("错误的控制类型");
            }
            manager.RemoteControl(controlInfo);
            return new SuccessApiResult(manager.GetRobot(robotid));
        } catch (Exception e) {
            return new ErrorApiResult("-1",e.getMessage(), e);
        }
    }

}
