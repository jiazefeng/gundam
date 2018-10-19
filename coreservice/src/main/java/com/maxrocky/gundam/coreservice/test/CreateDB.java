package com.maxrocky.gundam.coreservice.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxrocky.gundam.commons.model.biz.Graph;
import com.maxrocky.gundam.commons.model.biz.GraphEdge;
import com.maxrocky.gundam.commons.model.biz.GraphNode;
import com.maxrocky.gundam.domain.map.model.BussinessMap;
import com.maxrocky.gundam.domain.robot.model.RobotInfo;
import com.maxrocky.gundam.domain.robot.model.RobotStrategy;
import com.maxrocky.gundam.domain.robot.repository.RobotRepository;
import com.maxrocky.gundam.domain.robot.repository.RobotStratrgyRepository;
import com.maxrocky.gundam.domain.strategy.model.Strategy;
import com.maxrocky.gundam.domain.strategy.model.StrategyTasks;
import com.maxrocky.gundam.domain.strategy.repository.StrategyTasksRepository;
import com.maxrocky.gundam.domain.task.model.TaskInfo;
import com.maxrocky.gundam.domain.task.model.TaskItem;
import com.maxrocky.gundam.domain.task.repository.TaskInfoRepository;
import com.maxrocky.gundam.domain.task.repository.TaskItemRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
//import org.hibernate.boot.Metadata;
//import org.hibernate.boot.MetadataSources;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.service.ServiceRegistry;
//import org.hibernate.tool.hbm2ddl.SchemaExport;
//import org.hibernate.tool.schema.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

/**
 * Created by yuer5 on 16/5/13.
 */
public class CreateDB {
    public static void main(String[] args) {
        //装载配置文件
//        Configuration cfg = new Configuration().configure();
//        SchemaExport export = new SchemaExport(cfg);
//        export.create(true, true);


//        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
//        Metadata metadata = new MetadataSources(serviceRegistry).buildMetadata();
//        SchemaExport schemaExport = new SchemaExport();
//        schemaExport.create(EnumSet.of(TargetType.DATABASE), metadata);

        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext("db-hbn.xml");
        SessionFactory sf = context.getBean("sessionFactory", SessionFactory.class);

        Session s = sf.openSession();
//        BussinessMap map = new BussinessMap();
//        String a = getGraphStr();
//        map.setGraphJson(a);
//        map.setIsDelete(0);
//        map.setIsMappingCoordinate(1);
//        map.setMapTitle("Test Map");
//        map.setVillageId("Map001-GUID");
//        s.save(map);

//        Strategy strategy = new Strategy();
//        strategy.setMapId(1);
//        strategy.setET("ET");
//        strategy.setOverStatus(0);
//        strategy.setsId("Map001-Stratge001-GUID");
//        strategy.setsName("Map001-Stratge001-GUID");
//        s.save(strategy);
//
//        TaskInfo taskInfo = new TaskInfo();
//        taskInfo.setMapId(1);
//        taskInfo.setName("Map001-Task001");
//        taskInfo.setId(1);
//        taskInfo.setVillageId("Map001-GUID");
//        s.save(taskInfo);
//
//        taskInfo = new TaskInfo();
//        taskInfo.setMapId(1);
//        taskInfo.setName("Map001-Task002");
//        taskInfo.setId(2);
//        taskInfo.setVillageId("Map001-GUID");
//        s.save(taskInfo);
//
//        taskInfo = new TaskInfo();
//        taskInfo.setMapId(1);
//        taskInfo.setName("Map001-Task003");
//        taskInfo.setId(3);
//        taskInfo.setVillageId("Map001-GUID");
//        s.save(taskInfo);
//
//        taskInfo = new TaskInfo();
//        taskInfo.setMapId(1);
//        taskInfo.setName("Map001-Task004");
//        taskInfo.setId(4);
//        taskInfo.setVillageId("Map001-GUID");
//        s.save(taskInfo);
//
//        TaskItem item = new TaskItem();
//        item.setId(1);
//        item.setOrderly(1);
//        item.setMapId(1);
//        item.setTaskId(1);
//        item.setTaskType(1);
//        item.setVillageId("Map001-GUID");
//        item.setMapPointFromX(3);
//        item.setMapPointFromY(10);
//        item.setMapPointToX(9);
//        item.setMapPointToY(11);
//        s.save(item);
//
//        item = new TaskItem();
//        item.setId(2);
//        item.setOrderly(2);
//        item.setMapId(1);
//        item.setTaskId(1);
//        item.setTaskType(1);
//        item.setVillageId("Map001-GUID");
//        item.setMapPointFromX(9);
//        item.setMapPointFromY(11);
//        item.setMapPointToX(8);
//        item.setMapPointToY(4);
//        s.save(item);
//
//        item = new TaskItem();
//        item.setId(3);
//        item.setOrderly(3);
//        item.setMapId(1);
//        item.setTaskId(1);
//        item.setTaskType(0);
//        item.setVillageId("Map001-GUID");
//        item.setStayMinutes(5);
//        item.setMapPointFromX(8);
//        item.setMapPointFromY(4);
//        item.setMapPointToX(8);
//        item.setMapPointToY(4);
//        s.save(item);
//
//        item = new TaskItem();
//        item.setId(4);
//        item.setOrderly(1);
//        item.setMapId(1);
//        item.setTaskId(2);
//        item.setTaskType(1);
//        item.setVillageId("Map001-GUID");
//        item.setMapPointFromX(8);
//        item.setMapPointFromY(4);
//        item.setMapPointToX(7);
//        item.setMapPointToY(1);
//        s.save(item);
//
//        item = new TaskItem();
//        item.setId(5);
//        item.setOrderly(2);
//        item.setMapId(1);
//        item.setTaskId(2);
//        item.setTaskType(1);
//        item.setVillageId("Map001-GUID");
//        item.setMapPointFromX(7);
//        item.setMapPointFromY(1);
//        item.setMapPointToX(2);
//        item.setMapPointToY(1);
//        s.save(item);
//
//        item = new TaskItem();
//        item.setId(6);
//        item.setOrderly(1);
//        item.setMapId(1);
//        item.setTaskId(3);
//        item.setTaskType(1);
//        item.setVillageId("Map001-GUID");
//        item.setMapPointFromX(2);
//        item.setMapPointFromY(1);
//        item.setMapPointToX(3);
//        item.setMapPointToY(10);
//        s.save(item);
//
//        item = new TaskItem();
//        item.setId(7);
//        item.setOrderly(1);
//        item.setMapId(1);
//        item.setTaskId(4);
//        item.setTaskType(1);
//        item.setVillageId("Map001-GUID");
//        item.setMapPointFromX(3);
//        item.setMapPointFromY(10);
//        item.setMapPointToX(5);
//        item.setMapPointToY(11);
//        s.save(item);
//
//        item = new TaskItem();
//        item.setId(8);
//        item.setOrderly(2);
//        item.setMapId(1);
//        item.setTaskId(4);
//        item.setTaskType(3);    //充电
//        item.setVillageId("Map001-GUID");
//        item.setMapPointFromX(5);
//        item.setMapPointFromY(11);
//        item.setMapPointToX(5);
//        item.setMapPointToY(11);
//        item.setStayMinutes(20);
//        s.save(item);
//
//        item = new TaskItem();
//        item.setId(9);
//        item.setOrderly(3);
//        item.setMapId(1);
//        item.setTaskId(4);
//        item.setTaskType(3);
//        item.setVillageId("Map001-GUID");
//        item.setMapPointFromX(5);
//        item.setMapPointFromY(11);
//        item.setMapPointToX(3);
//        item.setMapPointToY(10);
//        s.save(item);

//        StrategyTasks st = new StrategyTasks();
//        st.setOrderly(1);
//        st.setTaskId(1);
//        st.setCurrStrategyId("Map001-Stratge001-GUID");
//        st.setId("Map001-Stratge001-Task001");
//        s.save(st);
//
//        st = new StrategyTasks();
//        st.setOrderly(2);
//        st.setTaskId(2);
//        st.setCurrStrategyId("Map001-Stratge001-GUID");
//        st.setId("Map001-Stratge001-Task002");
//        s.save(st);
//
//        st = new StrategyTasks();
//        st.setOrderly(3);
//        st.setTaskId(3);
//        st.setCurrStrategyId("Map001-Stratge001-GUID");
//        st.setId("Map001-Stratge001-Task003");
//        s.save(st);
//
//        st = new StrategyTasks();
//        st.setOrderly(3);
//        st.setTaskId(3);
//        st.setCurrStrategyId("Map001-Stratge001-GUID");
//        st.setId("Map001-Stratge001-Task004");
////        st.setId(UUID.randomUUID().toString().replaceAll("-", ""));
//        s.save(st);

        RobotInfo ri = new RobotInfo();
        ri.setRobotId(UUID.randomUUID().toString().replaceAll("-", ""));
        ri.setRobotName("Robot-001-Name");
        ri.setRobotNumber("Robot-001-Number");
        s.save(ri);
//
//        RobotStrategy rs = new RobotStrategy();
//        rs.setId(UUID.randomUUID().toString().replaceAll("-", ""));
//        rs.setCurrStrategyId("Map001-Stratge001-GUID");
//        rs.setRobotId("Robot-001");
//        rs.setId("Robot-001");
//        rs.setCreateDate(new Date());
//        s.save(rs);

        s.close();
        System.out.println(sf);

    }

    public static String getGraphStr(){

        Graph graph = InitGraphData();

        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(graph);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static Graph InitGraphData() {

        Graph graph = new Graph();

        graph.setNodes(new ArrayList<GraphNode>() {});

        graph.getNodes().add(new GraphNode(1, "A", "gpsInfo - A", 2, 1));  //1
        graph.getNodes().add(new GraphNode(2, "B", "gpsInfo - B", 2, 4));  //2
        graph.getNodes().add(new GraphNode(3, "C", "gpsInfo - C", 2, 9));  //3
        graph.getNodes().add(new GraphNode(4, "D", "gpsInfo - D", 5, 4));  //4
        graph.getNodes().add(new GraphNode(5, "E", "gpsInfo - E", 5, 6));  //5
        graph.getNodes().add(new GraphNode(6, "F", "gpsInfo - F", 5, 9));  //6
        graph.getNodes().add(new GraphNode(7, "G", "gpsInfo - G", 5, 11));  //7
        graph.getNodes().add(new GraphNode(8, "H", "gpsInfo - H", 7, 1));  //8
        graph.getNodes().add(new GraphNode(9, "I", "gpsInfo - I", 7, 6));  //9
        graph.getNodes().add(new GraphNode(10, "J", "gpsInfo - J", 7, 9));  //10
        graph.getNodes().add(new GraphNode(11, "K", "gpsInfo - K", 7, 10));  //11
        graph.getNodes().add(new GraphNode(12, "L", "gpsInfo - L", 10, 6));  //12
        graph.getNodes().add(new GraphNode(13, "M", "gpsInfo - M", 10, 10));  //13

        graph.setEdges(new ArrayList<GraphEdge>(){});

        graph.getEdges().add(new GraphEdge("A - B", 1, 2, 3));
        graph.getEdges().add(new GraphEdge("A - H", 1, 8, 5));

        graph.getEdges().add(new GraphEdge("B - A", 2, 1, 3));
        graph.getEdges().add(new GraphEdge("B - C", 2, 3, 5));
        graph.getEdges().add(new GraphEdge("B - D", 2, 4, 3));

        graph.getEdges().add(new GraphEdge("C - B", 3, 2, 5));
        graph.getEdges().add(new GraphEdge("C - F", 3, 6, 3));

        graph.getEdges().add(new GraphEdge("D - B", 4, 2, 3));
        graph.getEdges().add(new GraphEdge("D - E", 4, 5, 2));

        graph.getEdges().add(new GraphEdge("E - D", 5, 4, 2));
        graph.getEdges().add(new GraphEdge("E - F", 5, 6, 3));
        graph.getEdges().add(new GraphEdge("E - I", 5, 9, 2));

        graph.getEdges().add(new GraphEdge("F - C", 6, 3, 3));
        graph.getEdges().add(new GraphEdge("F - E", 6, 5, 3));
        graph.getEdges().add(new GraphEdge("F - G", 6, 7, 2));
        graph.getEdges().add(new GraphEdge("F - J", 6, 10, 2));

        graph.getEdges().add(new GraphEdge("G - F", 7, 6, 2));

        graph.getEdges().add(new GraphEdge("H - A", 8, 1, 5));
        graph.getEdges().add(new GraphEdge("H - I", 8, 9, 5));

        graph.getEdges().add(new GraphEdge("I - E", 9, 5, 2));
        graph.getEdges().add(new GraphEdge("I - H", 9, 8, 5));
        graph.getEdges().add(new GraphEdge("I - L", 9, 12, 3));

        graph.getEdges().add(new GraphEdge("J - F", 10, 6, 2));
        graph.getEdges().add(new GraphEdge("J - K", 10, 11, 1));

        graph.getEdges().add(new GraphEdge("K - J", 11, 10, 1));
        graph.getEdges().add(new GraphEdge("K - M", 11, 13, 3));

        graph.getEdges().add(new GraphEdge("L - I", 12, 9, 3));
        graph.getEdges().add(new GraphEdge("L - M", 12, 13, 4));

        graph.getEdges().add(new GraphEdge("M - L", 13, 12, 4));
        graph.getEdges().add(new GraphEdge("M - K", 13, 11, 3));

        return graph;

    }
}
