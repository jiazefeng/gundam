package com.maxrocky.gundam.service.impl;

import com.maxrocky.gundam.domain.robot.model.RobotInfo;
import com.maxrocky.gundam.domain.robot.model.RobotStrategy;
import com.maxrocky.gundam.domain.robot.repository.RobotRepository;
import com.maxrocky.gundam.domain.robot.repository.RobotStratrgyRepository;
import com.maxrocky.gundam.service.RobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yuer5 on 16/6/25.
 */
@Repository
public class RobotServiceImpl implements RobotService{
    @Autowired
    private RobotRepository robotRepository;

    @Autowired
    private RobotStratrgyRepository robotStratrgyRepository;

    @Override
    public RobotInfo Get(String Id){
        return robotRepository.getRobotInfoById(Id);
    }

    @Override
    public List<RobotInfo> GetByMap(String villageId) {
        return robotRepository.getRobotByVillageId(villageId);
    }

    @Override
    public List<String> GetRobotIdsByStratgy(String strategyId) {

        List<RobotStrategy> relations = robotStratrgyRepository.getByStrategy(strategyId);
        List<String> robotIds = relations.stream().map(a -> a.getRobotId()).collect(Collectors.toList());
        return robotIds;
    }
}
