package com.maxrocky.gundam.domain.task.repository;

import com.maxrocky.gundam.domain.task.model.TaskItem;

import java.util.List;

/**
 * Created by yuer5 on 16/6/9.
 */
public interface TaskItemRepository {
    /**
     * 添加任务项
     *
     * @param entity
     */
    public boolean Create(TaskItem entity);

    public void Update(TaskItem entity);

    public void Delete(int key);

    public void DeleteByTaskId(int taskId);

    public void BatchDeleteByMap(int mapId);

    public boolean BatchDeleteByTask(int taskId);

    public TaskItem Retrive(int key);

    public List<TaskItem> List(int taskId);

}
