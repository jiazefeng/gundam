package com.maxrocky.gundam.service.task.inf;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.domain.task.dto.SaveTaskDto;
import com.maxrocky.gundam.domain.task.dto.TaskInfoDto;
import com.maxrocky.gundam.domain.task.model.TaskInfo;
import com.maxrocky.gundam.domain.task.model.TaskItem;

import java.io.IOException;
import java.util.List;

/**
 * Created by yuer5 on 16/6/9.
 */
public interface TaskService {
    /**
     * 按照id查询任务组信息
     *
     * @param taskId
     * @return
     */
    public TaskInfoDto GetTask(int taskId);

    /**
     * 添加任务组
     *
     * @param taskInfo
     * @return
     * @throws IOException
     */
    public ApiResult SaveTask(TaskInfo taskInfo);
    /**
     * 修改任务组
     *
     * @param saveTaskDto
     * @return
     * @throws IOException
     */
    public ApiResult UpdateTask(SaveTaskDto saveTaskDto) throws Exception;

    /**
     * 初始化任务组查询
     *
     * @param villageId
     * @return
     */
    ApiResult GetTasksListByVillage(String villageId);

    ApiResult GetTasksListByItem(TaskInfoDto taskInfoDto);

    List<TaskInfo> GetTasksListByMapid(int id);

    TaskItem GetTaskItem(int itemid);

    void SaveTaskItem(TaskItem taskItem) throws Exception;

    ApiResult DeleteTask(int taskId);

    void DeleteAllTaskinMap(int mapId) throws Exception;

    void DeleteTaskTtem(int itemId) throws Exception;

    void DeleteTaskTteminTask(int taskId) throws Exception;

}
