package com.maxrocky.gundam.domain.strategy.repository;

import com.maxrocky.gundam.domain.strategy.model.StrategyTasks;
import com.maxrocky.gundam.domain.task.model.TaskInfo;

import java.util.List;

/**
 * Created by yuer5 on 16/6/9.
 */
public interface StrategyTasksRepository {
    /**
     * 通过ID得到任务组信息
     *
     * @param id
     * @return
     */
    public TaskInfo getTaskInfoById(int id);
    /**
     * 任务组信息
     *
     * @return
     */
    public List<TaskInfo> getTaskInfoByVillageId(String villageId);
    /**
     * 通过 方案ID 查询任务组
     *
     * @param strategyId 方案ID
     * @return
     */
    public List<TaskInfo> getTaskInfoList(String strategyId);

    /**
     * 通过 方案ID 查询 方案与任务组对应信息
     *
     * @param strategyId
     * @return
     */
    public List<StrategyTasks> getStrategyTasks(String strategyId);

    /**
     * 添加方案和任务组对应
     *
     * @param strategyTaskses
     * @return
     */
    public boolean addStrategyTask(List<StrategyTasks> strategyTaskses);

    /**
     * 修改方案和任务组对应
     *
     * @param strategyTaskses
     * @return
     */
    public boolean updateStrategyTask(List<StrategyTasks> strategyTaskses);
    /**
     * 删除方案和任务组对应
     *
     * @param strategyTaskses
     * @return
     */
    public boolean deleteStrategyTask(List<StrategyTasks> strategyTaskses);

    void Create(StrategyTasks entity);

    void Update(StrategyTasks entity);

    void Delete(int key);

    void BatchDelete(String StrategyId);

    void BatchDeleteByMap(int mapId);

    StrategyTasks Retrive(int key);

    List<StrategyTasks> List(int StrategyId);

    boolean IsRemainTask(int taskId);

    boolean IsRemainTaskIds(List<Integer> taskIds);
}
