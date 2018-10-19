package com.maxrocky.gundam.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.common.result.ErrorApiResult;
import com.maxrocky.gundam.common.result.SuccessApiResult;
import com.maxrocky.gundam.commons.model.biz.GraphEdge;
import com.maxrocky.gundam.commons.model.biz.GraphNode;
import com.maxrocky.gundam.domain.map.dto.GraphDto;
import com.maxrocky.gundam.domain.map.dto.MapDto;
import com.maxrocky.gundam.domain.map.model.MapInfo;
import com.maxrocky.gundam.domain.task.dto.SaveTaskDto;
import com.maxrocky.gundam.domain.task.dto.TaskInfoDto;
import com.maxrocky.gundam.domain.task.model.TaskInfo;
import com.maxrocky.gundam.domain.task.model.TaskItem;
import com.maxrocky.gundam.domain.user.model.UserInfo;
import com.maxrocky.gundam.service.map.inf.MapService;
import com.maxrocky.gundam.service.task.inf.TaskService;
import com.maxrocky.gundam.service.user.inf.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by yuer5 on 16/6/9.
 */
@RestController
@RequestMapping(value = "/task")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    @Autowired
    private MapService mapService;

    /**
     * 按任务组ID 检索任务组信息
     *
     * @param taskid
     * @return
     */
    @RequestMapping(value = "/{taskid}", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap GetTask(@PathVariable int taskid, @CookieValue(value = "villageId", required = false) String villageId) {
        ModelMap result = new ModelMap();
        try {
            GraphDto graphDto = mapService.GetGraphDto(villageId);
            result.addAttribute(graphDto);
            TaskInfoDto taskInfoDto = taskService.GetTask(taskid);
            result.addAttribute(taskInfoDto);
            return new SuccessApiResult(result);
        } catch (Exception e) {
            return new ErrorApiResult("-1", e.getMessage(), e);
        }

    }

    /**
     * 添加任务组
     *
     * @param saveTaskDto
     * @param tokenId
     * @param villageId
     * @return
     */
    @RequestMapping(value = "/SaveTask", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ApiResult SaveTask(@RequestBody SaveTaskDto saveTaskDto, @CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) throws Exception {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登录");
        }
        ObjectMapper mapper = new ObjectMapper();
        if (saveTaskDto.getNodes() == null) {
            saveTaskDto.setNodes(new ArrayList<GraphNode>());
        }
        if (saveTaskDto.getEdges() == null) {
            saveTaskDto.setEdges(new ArrayList<GraphEdge>());
        }
        String json = mapper.writeValueAsString(saveTaskDto);
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setTaskitems(saveTaskDto.getTaskitems());
        taskInfo.setName(saveTaskDto.getName());
        taskInfo.setGraphJson(json);
        taskInfo.setVillageId(villageId);
        taskInfo.setCreateOn(Calendar.getInstance());
        taskInfo.setCreateBy(userInfo.getuName());
        taskInfo.setModifyOn(Calendar.getInstance());
        taskInfo.setModifyBy(userInfo.getuName());
        return taskService.SaveTask(taskInfo);
    }

    @RequestMapping(value = "/UpdateTask", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ModelMap UpdateTask(@RequestBody SaveTaskDto saveTaskDto,@CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) throws Exception {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登录");
        }

        return taskService.UpdateTask(saveTaskDto);
    }

    /**
     * 初始化检索任务组信息
     *
     * @param tokenId
     * @param villageId
     * @return
     */
    @RequestMapping(value = "/filterbyvillage", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public ApiResult GetTaskListByVillage(@CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登录");
        }
        return taskService.GetTasksListByVillage(villageId);
    }

    /**
     * 按条件检索任务组信息
     *
     * @param taskInfoDto
     * @param tokenId
     * @param villageId
     * @return
     */
    @RequestMapping(value = "/filterbyItem", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public ApiResult GetTaskListByItem(@RequestBody TaskInfoDto taskInfoDto, @CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登录");
        }
        taskInfoDto.setVillageId(villageId);
        return taskService.GetTasksListByItem(taskInfoDto);
    }

    @RequestMapping(value = "/filterbymap/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<TaskInfo> GetTaskList(@PathVariable int id) {
        return taskService.GetTasksListByMapid(id);
    }

    @RequestMapping(value = "/item/{itemid}", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap GetTaskItem(@PathVariable int itemid) {
        try {
            taskService.GetTaskItem(itemid);
            return new SuccessApiResult();
        } catch (Exception e) {
            return new ErrorApiResult("-1", e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{taskid}/item", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ModelMap SaveTaskItem(@RequestBody TaskItem taskItem) {
        try {
            taskService.SaveTaskItem(taskItem);
            return new SuccessApiResult();
        } catch (Exception e) {
            return new ErrorApiResult("-1", e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{taskid}/item", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    public ModelMap UpdateTaskItem(@RequestBody TaskItem taskItem) {
        try {
            taskService.SaveTaskItem(taskItem);
            return new SuccessApiResult();
        } catch (Exception e) {
            return new ErrorApiResult("-1", e.getMessage(), e);
        }
    }

    /**
     * 删除任务组
     *
     * @param taskid
     * @return
     */
    @RequestMapping(value = "/{taskid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ApiResult DeleteTaskById(@PathVariable int taskid) {
        return taskService.DeleteTask(taskid);
    }

    @RequestMapping(value = "/item/{itemid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ModelMap DeleteTaskItem(@PathVariable int itemid) {
        try {
            taskService.DeleteTaskTtem(itemid);
            return new SuccessApiResult();
        } catch (Exception e) {
            return new ErrorApiResult("-1", e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/deletebymap/{mapId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ModelMap DeleteTaskByMap(@PathVariable int mapId) {
        try {
            taskService.DeleteAllTaskinMap(mapId);
            return new SuccessApiResult();
        } catch (Exception e) {
            return new ErrorApiResult("-1", e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{taskid}/clearitem", method = RequestMethod.DELETE)
    @ResponseBody
    public ModelMap DeleteTaskItemByTaskid(@PathVariable int taskid) {
        try {
            taskService.DeleteTaskTteminTask(taskid);
            return new SuccessApiResult();
        } catch (Exception e) {
            return new ErrorApiResult("-1", e.getMessage(), e);
        }
    }


    /**
     * 初始化查询地图
     *
     * @param tokenId
     * @param villageId
     * @return
     */
    @RequestMapping(value = "/findMapByVillageId", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult findMapByVillageId(@CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        ModelMap result = new ModelMap();
        MapDto mapDto = mapService.GetMap(villageId);
        if (mapDto == null) {
            return new SuccessApiResult("error");
        } else {
            GraphDto graphDto = mapService.GetGraphDto(villageId);
            result.addAttribute(graphDto);
            result.addAttribute(mapService.findMapByVillageId(villageId));
            return new SuccessApiResult(result);
        }
    }

    /**
     * 初始化编辑任务组页面
     */
    @RequestMapping(value = "/initUpdateGroup/{taskId}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult initUpdateGroup(@PathVariable int taskId, @CookieValue(value = "vestaToken", required = false) String tokenId, @CookieValue(value = "villageId", required = false) String villageId) {
        UserInfo userInfo = userService.GetUserInfoByTokenValue(tokenId);
        if (userInfo == null) {
            return new SuccessApiResult("当前未登陆");
        }
        ModelMap result = new ModelMap();
        MapDto mapDto = mapService.GetMap(villageId);
        if (mapDto == null) {
            return new SuccessApiResult("error");
        } else {
            GraphDto graphDto = mapService.GetGraphDto(villageId);
            result.addAttribute(graphDto);
            result.addAttribute(mapService.findMapByVillageId(villageId));
            TaskInfoDto taskInfoDto = taskService.GetTask(taskId);
            result.addAttribute(taskInfoDto);
            return new SuccessApiResult(result);
        }
    }

}
