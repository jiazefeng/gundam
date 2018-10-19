package com.maxrocky.gundam.coreservice.repository;

import com.maxrocky.gundam.coreservice.core.RobotSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuer5 on 16/7/18.
 */
@Repository
public class SnapshotRepositoryImpl implements SnapshotRepository {
    // 注入实际的模板
    @Autowired
    private RedisTemplate<String, RobotSnapshot> template;


    @Override
    public void add(String robotId, RobotSnapshot snapshot) {
        template.opsForValue().set(robotId, snapshot);
    }

    @Override
    public RobotSnapshot get(String robotId) {
        RobotSnapshot temp = template.opsForValue().get(robotId);
        return temp;
    }

    @Override
    public List<RobotSnapshot> getAll(String robotId) {
        return new ArrayList<>();
    }


    @Override
    public void ClearAllInCache(){

    }
}
