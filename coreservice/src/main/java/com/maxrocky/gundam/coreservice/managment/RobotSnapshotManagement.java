package com.maxrocky.gundam.coreservice.managment;

import com.maxrocky.gundam.commons.model.biz.RemoteControl;
import com.maxrocky.gundam.coreservice.model.biz.RobetUpStreamInfo;
import com.maxrocky.gundam.coreservice.core.RobotSnapshot;
import com.maxrocky.gundam.coreservice.model.biz.RobotDownStreamInfo;
import com.maxrocky.gundam.commons.model.biz.RobotSimpleInfo;

import java.util.Date;
import java.util.List;

/**
 * Created by yuer5 on 16/5/15.
 */
public interface RobotSnapshotManagement {

    RobotSnapshot GetSnapshot(String Id);

    void UpdateMap(String villageId);

    void UpdateReport(RobetUpStreamInfo report);

    void UpdateStrategy(String strategyId) throws Exception;

    void UpdateSingleRobotStrategy(String robotId);

    RobotDownStreamInfo GetDownStream(String robotId);

    List<RobotSimpleInfo> GetAllRobot(String VillageId);

    RobotSimpleInfo GetRobot(String robotid);

    void ModeSwitch(String robotid, int incontrol, int subsequentActions) throws Exception;

    void RemoteControl(RemoteControl controlInfo);

    void AddImmediatelyTaskForTest(String strategyId, String robotId);

    void SetImmediatelyStartegyExecuteStatue(String strategyId, int executeStatue);
    //修改策略完成时间
    void SetStartegyCompleteTime(String strategyId,Date completeTime);

}
