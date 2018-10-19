package com.maxrocky.gundam.service.impl;

import com.maxrocky.gundam.commons.model.biz.Graph;
import com.maxrocky.gundam.commons.utility.GraphUtility;
import com.maxrocky.gundam.coreservice.core.CoreCache;
import com.maxrocky.gundam.coreservice.core.RobotSnapshot;
import com.maxrocky.gundam.coreservice.model.biz.RobotTask;
import com.maxrocky.gundam.coreservice.repository.SnapshotRepository;
import com.maxrocky.gundam.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yuer5 on 16/6/26.
 */
@Repository
public class ChacheServiceImpl implements CacheService {

    static CoreCache cache = CoreCache.getInstance();

    @Autowired
    private SnapshotRepository snapshotRepository;

    @Override
    public void UpdateTheta(String robotId, double[][] theta) {
        RobotSnapshot snapshot = snapshotRepository.get(robotId);
//        RobotSnapshot snapshot = CoreCache.getInstance().getData(robotId);
        snapshot.setTheta(theta);
        snapshotRepository.add(snapshot.getRobotId(), snapshot);
    }

//    @Override
//    public void UpdateTaskItem(String robotId, List<RobotTask> taskItems) {
//        RobotSnapshot snapshot = snapshotRepository.get(robotId);
////        RobotSnapshot snapshot = CoreCache.getInstance().getData(robotId);
//        if(snapshot == null || taskItems == null)
//            return;
//        if(snapshot.getTaskItemList() != null){
//            List<RobotTask> orginalTaskItems = snapshot.getTaskItemList();
//            if(!orginalTaskItems.equals(taskItems)){
//                snapshot.setTaskItemList(taskItems);
//            }
//        }else {
//            snapshot.setTaskItemList(taskItems);
//        }
//        snapshotRepository.add(snapshot.getRobotId(), snapshot);
//    }

    @Override
    public RobotSnapshot GetRobotSnapshot(String robotId) {
        return snapshotRepository.get(robotId);
//        return CoreCache.getInstance().getData(robotId);
    }

    @Override
    public void SetRobotSnapshot(RobotSnapshot robot) {
//        CoreCache.getInstance().setData(robot.getRobotId(), robot);
        snapshotRepository.add(robot.getRobotId(), robot);
    }

    @Override
    public void UpdateMap(List<String> robotId, Graph graph) {
        for(String id : robotId){
//            RobotSnapshot snapshot = cache.getData(id);
            RobotSnapshot snapshot = snapshotRepository.get(id);;
            if(snapshot!=null) {
                snapshot.setMap(GraphUtility.regularisationMapInfo(graph));
            }
        }
    }
    @Override
    public void ClearCache(){

    }
}
