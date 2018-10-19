package com.maxrocky.gundam.coreservice.repository;

import com.maxrocky.gundam.coreservice.core.RobotSnapshot;

import java.util.List;

/**
 * Created by yuer5 on 16/7/18.
 */
public interface SnapshotRepository {

    void add(String robotId, RobotSnapshot snapshot);

    RobotSnapshot get(String robotId);

    List<RobotSnapshot> getAll(String robotId);

    void ClearAllInCache();
}
