package com.maxrocky.gundam.service;

import com.maxrocky.gundam.domain.robot.model.RobotInfo;
import com.maxrocky.gundam.domain.robot.repository.RobotRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by yuer5 on 16/6/25.
 */
public interface RobotService {

    RobotInfo Get(String Id);

    List<RobotInfo> GetByMap(String villageId);

    List<String> GetRobotIdsByStratgy(String strategyId);

}
