package com.maxrocky.gundam.service.task.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxrocky.gundam.common.result.ApiResult;
import com.maxrocky.gundam.common.result.SuccessApiResult;
import com.maxrocky.gundam.commons.algorithm.GraphManager;
import com.maxrocky.gundam.commons.configs.Enums;
import com.maxrocky.gundam.commons.model.biz.Graph;
import com.maxrocky.gundam.commons.model.biz.GraphEdge;
import com.maxrocky.gundam.commons.model.biz.GraphNode;
import com.maxrocky.gundam.commons.utility.ArrayStringConverter;
import com.maxrocky.gundam.domain.map.model.BussinessMap;
import com.maxrocky.gundam.domain.map.model.MapInfo;
import com.maxrocky.gundam.domain.map.repository.BussinessMapRepository;
import com.maxrocky.gundam.domain.map.repository.MapInfoRepository;
import com.maxrocky.gundam.domain.strategy.repository.StrategyTasksRepository;
import com.maxrocky.gundam.domain.task.dto.SaveTaskDto;
import com.maxrocky.gundam.domain.task.dto.TaskInfoDto;
import com.maxrocky.gundam.domain.task.model.TaskInfo;
import com.maxrocky.gundam.domain.task.model.TaskItem;
import com.maxrocky.gundam.domain.task.repository.TaskInfoRepository;
import com.maxrocky.gundam.domain.task.repository.TaskItemRepository;
import com.maxrocky.gundam.service.task.inf.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

//import ma.glasnost.orika.MapperFacade;

/**
 * Created by yuer5 on 16/6/9.
 */
@Repository
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskItemRepository itemsRepository;
    @Autowired
    private TaskInfoRepository tasksRepository;
    @Autowired
    private StrategyTasksRepository strategyTasksRepository;
    @Autowired
    private BussinessMapRepository mapRepository;
    @Autowired
    private MapInfoRepository mapInfoRepository;
//    @Autowired
//    private MapperFacade mapper = null;

    @Override
    public TaskInfoDto GetTask(int taskId) {
        TaskInfo ret = tasksRepository.Retrive(taskId);
        TaskInfoDto taskInfoDto = null;
        if (ret != null) {
            taskInfoDto = new TaskInfoDto();
            taskInfoDto.setTaskId(ret.getId());
            taskInfoDto.setName(ret.getName());
            taskInfoDto.setModifyOn(ret.getModifyOn());
            taskInfoDto.setModifyBy(ret.getModifyBy());
            List<TaskItem> itemList = itemsRepository.List(ret.getId());
            taskInfoDto.setTaskitems(itemList);
            int j = 0;
            if (itemList != null && itemList.size() > 0) {
                String taskName = "";
                List<GraphNode> graphNodes = new ArrayList<GraphNode>();
                for (TaskItem taskItem : itemList) {
                    if (taskItem.getTaskType() == 0) {
                        taskName += "站岗" + "-";
                    }
                    if (taskItem.getTaskType() == 1) {
                        taskName += "巡逻" + "-";
                    }
                    j++;
                    graphNodes.add(new GraphNode(j, "", "", taskItem.getMapPointFromX(), taskItem.getMapPointFromY()));
                    graphNodes.add(new GraphNode(j + 1, "", "", taskItem.getMapPointToX(), taskItem.getMapPointToY()));
                    j++;
                }
                String mapStr = ret.getGraphJson();
                ObjectMapper mapper = new ObjectMapper();
                SaveTaskDto saveTaskDto = new SaveTaskDto();
                try {
                    saveTaskDto = mapper.readValue(mapStr, SaveTaskDto.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                taskInfoDto.setContent(taskName.substring(0, taskName.length() - 1));
//                taskInfoDto.setNodes(saveTaskDto.getNodes());
                taskInfoDto.setNodes(graphNodes);
                taskInfoDto.setEdges(saveTaskDto.getEdges());
            }
        }
        //ret.setTaskitems(itemsRepository.List(taskId));
        return taskInfoDto;
    }

    @Override
    public ApiResult SaveTask(TaskInfo taskInfo) {
//        GenerateGPSInfo(taskInfo);
//        BussinessMap map = mapRepository.GetByVillageId(taskInfo.getVillageId());

        ModelMap result = new ModelMap();
        if (taskInfo != null) {
            //MapInfo mapInfo = mapInfoRepository.findMapByVillageId(taskInfo.getVillageId());
            List<MapInfo> mapInfoList = mapInfoRepository.getMapInfoByVillageId(taskInfo.getVillageId());
            MapInfo mapInfo = mapInfoList.get(0);
            if (mapInfo != null) {
                taskInfo.setMapId(mapInfo.getMapId());
            }
            if (tasksRepository.Create(taskInfo)) {
                if (taskInfo.getTaskitems() != null && taskInfo.getTaskitems().size() > 0) {
                    for (int i = 0; i < taskInfo.getTaskitems().size(); i++) {
                        TaskItem item = taskInfo.getTaskitems().get(i);
                        item.setTaskId(taskInfo.getId());
                        item.setMapId(mapInfo.getMapId());
                        item.setOrderly(i);
                        item.setVillageId(taskInfo.getVillageId());
                        if (itemsRepository.Create(item)) {
                            result.addAttribute("success", "添加成功");
                        } else {
                            result.addAttribute("error", "添加失败");
                        }
                    }
                }
            } else {
                result.addAttribute("error", "添加失败");
            }
        } else {
            result.addAttribute("error", "参数错误");
        }
        return new SuccessApiResult(result);
//        if (taskInfo.getId() == 0) {
//            tasksRepository.Create(taskInfo);
//        } else {
//            tasksRepository.Update(taskInfo);
//            itemsRepository.BatchDeleteByTask(taskInfo.getId());
//        }
//        if (taskInfo.getTaskitems() != null && taskInfo.getTaskitems().size() > 0) {
////            taskInfo.getTaskitems().forEach(itemsRepository::Create);
//            for (int i = 0; i < taskInfo.getTaskitems().size(); i++) {
//                TaskItem item = taskInfo.getTaskitems().get(i);
//                item.setTaskId(taskInfo.getId());
//                itemsRepository.Create(item);
//            }
//        }
    }

    @Override
    public ApiResult UpdateTask(SaveTaskDto saveTaskDto) throws Exception {
        ModelMap result = new ModelMap();
        TaskInfo taskInfo = tasksRepository.Retrive(saveTaskDto.getId());
        if (taskInfo != null) {
            ObjectMapper mapper = new ObjectMapper();
            if (saveTaskDto.getNodes() == null) {
                saveTaskDto.setNodes(new ArrayList<GraphNode>());
            }
            if (saveTaskDto.getEdges() == null) {
                saveTaskDto.setEdges(new ArrayList<GraphEdge>());
            }
            String json = mapper.writeValueAsString(saveTaskDto);
            taskInfo.setTaskitems(saveTaskDto.getTaskitems());
            taskInfo.setName(saveTaskDto.getName());
            taskInfo.setGraphJson(json);
            taskInfo.setModifyOn(Calendar.getInstance());
            if (tasksRepository.updateTaskInfo(taskInfo)) {
                List<MapInfo> mapInfoList = mapInfoRepository.getMapInfoByVillageId(taskInfo.getVillageId());
                MapInfo mapInfo = mapInfoList.get(0);
                List<TaskItem> taskItemList = itemsRepository.List(taskInfo.getId());
                if (taskItemList != null && taskItemList.size() > 0) {
                    if (itemsRepository.BatchDeleteByTask(taskInfo.getId())) {
                        if (taskInfo.getTaskitems() != null && taskInfo.getTaskitems().size() > 0) {
                            for (int i = 0; i < taskInfo.getTaskitems().size(); i++) {
                                TaskItem item = taskInfo.getTaskitems().get(i);
                                item.setTaskId(taskInfo.getId());
                                item.setMapId(mapInfo.getMapId());
                                item.setOrderly(i);
                                item.setVillageId(taskInfo.getVillageId());
                                if (itemsRepository.Create(item)) {
                                    result.addAttribute("success", "修改成功");
                                } else {
                                    result.addAttribute("error", "修改失败");
                                }

                            }
                        }
                    }
                } else {
                    if (taskInfo.getTaskitems() != null && taskInfo.getTaskitems().size() > 0) {
                        for (int i = 0; i < taskInfo.getTaskitems().size(); i++) {
                            TaskItem item = taskInfo.getTaskitems().get(i);
                            item.setTaskId(taskInfo.getId());
                            item.setMapId(mapInfo.getMapId());
                            item.setOrderly(i);
                            item.setVillageId(taskInfo.getVillageId());
                            if (itemsRepository.Create(item)) {
                                result.addAttribute("success", "修改成功");
                            } else {
                                result.addAttribute("error", "修改失败");
                            }

                        }
                    }
                }
            } else {
                result.addAttribute("error", "修改失败");
            }
        } else {
            result.addAttribute("error", "参数错误");
        }
        return new SuccessApiResult(result);
    }

    @Override
    public ApiResult GetTasksListByVillage(String villageId) {
        List<TaskInfo> ret = tasksRepository.ListByVillageId(villageId);
        List<TaskInfoDto> taskInfoDtos = new ArrayList<TaskInfoDto>();
        if (ret != null && ret.size() > 0) {
            for (TaskInfo taskInfo : ret) {
                TaskInfoDto taskInfoDto = new TaskInfoDto();
                taskInfoDto.setTaskId(taskInfo.getId());
                taskInfoDto.setName(taskInfo.getName());
                taskInfoDto.setCreateOn(taskInfo.getCreateOn());
                taskInfoDto.setModifyOn(taskInfo.getModifyOn());
                List<TaskItem> itemList = itemsRepository.List(taskInfo.getId());
                if (itemList != null && itemList.size() > 0) {
                    String taskName = "";
                    for (TaskItem taskItem : itemList) {
                        if (taskItem.getTaskType() == 0) {
                            taskName += "站岗" + "-";
                        }
                        if (taskItem.getTaskType() == 1) {
                            taskName += "巡逻" + "-";
                        }
                    }
                    taskInfoDto.setContent(taskName.substring(0, taskName.length() - 1));
                }
                taskInfoDtos.add(taskInfoDto);
            }
        }
//        if (ret.size() > 0) {
//            for (TaskInfo task : ret) {
//                task.setTaskitems(itemsRepository.List(task.getId()));
//            }
//        }
        int total = tasksRepository.totalByVillageId(villageId);
        ModelMap result = new ModelMap();
        result.addAttribute("count", total);
        result.addAttribute("taskInfoList", taskInfoDtos);
        return new SuccessApiResult(result);
    }

    @Override
    public ApiResult GetTasksListByItem(TaskInfoDto taskInfoDto) {
        List<TaskInfo> ret = tasksRepository.ListByItem(taskInfoDto.getVillageId(), taskInfoDto.getIndex());
//        if (ret.size() > 0) {
//            for (TaskInfo task : ret) {
//                task.setTaskitems(itemsRepository.List(task.getId()));
//            }
//        }
        List<TaskInfoDto> taskInfoDtos = new ArrayList<TaskInfoDto>();
        if (ret != null && ret.size() > 0) {
            for (TaskInfo taskInfo : ret) {
                TaskInfoDto taskInfoDto1 = new TaskInfoDto();
                taskInfoDto1.setTaskId(taskInfo.getId());
                taskInfoDto1.setName(taskInfo.getName());
                taskInfoDto1.setCreateOn(taskInfo.getCreateOn());
                taskInfoDto1.setModifyOn(taskInfo.getModifyOn());
                List<TaskItem> itemList = itemsRepository.List(taskInfo.getId());
                if (itemList != null && itemList.size() > 0) {
                    String taskName = "";
                    for (TaskItem taskItem : itemList) {
                        if (taskItem.getTaskType() == 0) {
                            taskName += "站岗" + "-";
                        }
                        if (taskItem.getTaskType() == 1) {
                            taskName += "巡逻" + "-";
                        }
                    }
                    taskInfoDto1.setContent(taskName.substring(0, taskName.length() - 1));
                }
                taskInfoDtos.add(taskInfoDto1);
            }
        }
        int total = tasksRepository.totalByVillageId(taskInfoDto.getVillageId());
        ModelMap result = new ModelMap();
        result.addAttribute("count", total);
        result.addAttribute("taskInfoList", taskInfoDtos);
        result.addAttribute("page", taskInfoDto.getIndex());
        return new SuccessApiResult(result);
    }

    @Override
    public List<TaskInfo> GetTasksListByMapid(int id) {
        List<TaskInfo> ret = tasksRepository.ListByMap(id);
        if (ret.size() > 0) {
            for (TaskInfo task : ret) {
                task.setTaskitems(itemsRepository.List(task.getId()));
            }
        }
        return ret;
    }

    @Override
    public TaskItem GetTaskItem(int itemid) {
        return itemsRepository.Retrive(itemid);
    }

    @Override
    public void SaveTaskItem(TaskItem taskItem) throws Exception {

        BussinessMap map = mapRepository.GetByVillageId(taskItem.getVillageId());

        double[][] theta = new double[2][2];
        theta = ArrayStringConverter.convertToArray(map.getTheta(), 2, 2);

        taskItem.setVillageId(map.getVillageId());
        taskItem.setMapId(map.getId());
        if (taskItem.getTaskType() == Enums.TaskItemType.Move.ordinal()) {
            taskItem.setGpsPointFromLat(theta[0][0] + taskItem.getMapPointFromX() * theta[0][1]);
            taskItem.setGpsPointFromLon(theta[1][0] + taskItem.getMapPointFromY() * theta[1][1]);
            taskItem.setGpsPointToLat(theta[0][0] + taskItem.getMapPointToX() * theta[0][1]);
            taskItem.setGpsPointToLon(theta[1][0] + taskItem.getMapPointToY() * theta[1][1]);
        }

        //最短路径
        String mapStr = map.getGraphJson();
        ObjectMapper mapper = new ObjectMapper();
        Graph graph = null;
        GraphManager graphManager = new GraphManager();
        graph = mapper.readValue(mapStr, Graph.class);
        GraphNode start = new GraphNode(graph.getNodes().size() + 1, "start", "start", taskItem.getMapPointFromX(), taskItem.getMapPointFromY());
        GraphNode end = new GraphNode(graph.getNodes().size() + 2, "end", "end", taskItem.getMapPointFromX(), taskItem.getMapPointFromY());
        List<GraphNode> list = graphManager.DoFindPathByExternalPoint(graph, start, end);
        String json = mapper.writeValueAsString(list);
        taskItem.setGpsPathWay(json);

        //格点化路径
        itemsRepository.Create(taskItem);
    }

    private void GenerateGPSInfo(TaskInfo taskInfo) throws IOException {

        BussinessMap map = mapRepository.GetByVillageId(taskInfo.getVillageId());
        taskInfo.setVillageId(map.getVillageId());
        taskInfo.setMapId(map.getId());
        ObjectMapper mapper = new ObjectMapper();

        double[][] theta = new double[2][2];
        theta = ArrayStringConverter.convertToArray(map.getTheta(), 2, 2);
        GraphManager graphManager = new GraphManager();

        if (taskInfo.getTaskitems() != null && taskInfo.getTaskitems().size() > 0) {
            for (int i = 0; i < taskInfo.getTaskitems().size(); i++) {
                TaskItem item = taskInfo.getTaskitems().get(i);
                item.setVillageId(map.getVillageId());
                item.setMapId(map.getId());

                if (item.getTaskType() == Enums.TaskItemType.Move.ordinal()) {
                    item.setGpsPointFromLat(theta[0][0] + item.getMapPointFromX() * theta[0][1]);
                    item.setGpsPointFromLon(theta[1][0] + item.getMapPointFromY() * theta[1][1]);
                    item.setGpsPointToLat(theta[0][0] + item.getMapPointToX() * theta[0][1]);
                    item.setGpsPointToLon(theta[1][0] + item.getMapPointToY() * theta[1][1]);

                    //最短路径
                    String mapStr = map.getGraphJson();
                    Graph graph = null;
                    graph = mapper.readValue(mapStr, Graph.class);
                    GraphNode start = new GraphNode(graph.getNodes().size() + 1, "start", "start", item.getMapPointFromX(), item.getMapPointFromY());
                    GraphNode end = new GraphNode(graph.getNodes().size() + 2, "end", "end", item.getMapPointFromX(), item.getMapPointFromY());
                    List<GraphNode> list = graphManager.DoFindPathByExternalPoint(graph, start, end);
                    String json = mapper.writeValueAsString(list);
                    item.setGpsPathWay(json);
                }

            }
        }

    }

    @Override
    public ApiResult DeleteTask(int taskId) {
        ModelMap result = new ModelMap();
        if (strategyTasksRepository.IsRemainTask(taskId)) {
            result.addAttribute("error", "方案引用中，不可删除");
        } else {
            if (tasksRepository.Delete(taskId)) {
                if (itemsRepository.BatchDeleteByTask(taskId)) {
                    result.addAttribute("success", "删除成功");
                } else {
                    result.addAttribute("error", "删除失败");
                }
            } else {
                result.addAttribute("error", "删除失败");
            }
        }
        return new SuccessApiResult(result);
    }

    @Override
    public void DeleteAllTaskinMap(int mapId) throws Exception {

        List<TaskInfo> tasks = tasksRepository.ListByMap(mapId);
        List<Integer> takids = tasks.stream().map(a -> a.getId()).collect(Collectors.toList());
        if (strategyTasksRepository.IsRemainTaskIds(takids))
            throw new Exception();
        tasksRepository.BatchDelete(mapId);
        itemsRepository.BatchDeleteByMap(mapId);
    }

    @Override
    public void DeleteTaskTtem(int itemId) throws Exception {
        itemsRepository.Delete(itemId);
    }

    @Override
    public void DeleteTaskTteminTask(int taskId) throws Exception {
        itemsRepository.BatchDeleteByTask(taskId);
    }


}
