package com.maxrocky.gundam.domain.task.repository;


import com.maxrocky.gundam.domain.task.model.TaskInfo;

import java.util.List;

/**
 * Created by yuer5 on 16/6/9.
 */
public interface TaskInfoRepository {
    public boolean Create(TaskInfo entity);

    public void Update(TaskInfo entity);

    public boolean Delete(int key);
    public boolean updateTaskInfo(TaskInfo taskInfo);

    public void BatchDelete(int mapId);

    public TaskInfo Retrive(int key);

    public List<TaskInfo> ListByMap(int id);

    public List<TaskInfo> ListByVillageId(String villageId);
    public List<TaskInfo> ListByItem(String villageId,int startRow);

    public int totalByVillageId(String villageId);

    public List<TaskInfo> ListByIds(List<Integer> taskIds);
}
