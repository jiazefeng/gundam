package com.maxrocky.gundam.service;

import com.maxrocky.gundam.commons.model.biz.Graph;
import com.maxrocky.gundam.coreservice.core.RobotSnapshot;
import com.maxrocky.gundam.coreservice.model.biz.RobotTask;

import java.util.List;

/**
 * Created by yuer5 on 16/6/26.
 */
public interface CacheService {

//    void UpdateTaskItem(String robotId, List<RobotTask> taskItems);

    void UpdateTheta(String robotId, double[][] theta);

    RobotSnapshot GetRobotSnapshot(String robotId);

    void SetRobotSnapshot(RobotSnapshot robot);

    void UpdateMap(List<String> robotId, Graph graph);

    void ClearCache();
}
