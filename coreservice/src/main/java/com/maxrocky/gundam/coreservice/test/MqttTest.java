package com.maxrocky.gundam.coreservice.test;

/**
 * Created by yuer5 on 16/5/10.
 */

import com.maxrocky.gundam.commons.model.biz.Graph;
import com.maxrocky.gundam.commons.model.biz.GraphEdge;
import com.maxrocky.gundam.commons.model.biz.GraphNode;
import org.eclipse.paho.client.mqttv3.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MqttTest {


    public static void main(String[] args) throws MqttException, InterruptedException {

        Calendar calendarCurr = Calendar.getInstance();
        calendarCurr.setTime(new Date());

        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(calendarCurr.getTime());
//        calendarStart.set(Calendar.YEAR, calendarCurr.get(Calendar.YEAR));
//        calendarStart.set(Calendar.MONTH, calendarCurr.get(Calendar.MONTH));
//        calendarStart.set(Calendar.DATE, calendarCurr.get(Calendar.DATE));
//
//        Calendar calendarEnd = Calendar.getInstance();
//        calendarEnd.setTime(calendarStart.getTime());
//        calendarEnd.add(Calendar.MINUTE, 15);
        Date date = calendarStart.getTime();
        System.out.println(date);

//        Robet robet = new RobotSnapshotServiceImpl().GetRobetByID(1);
//        robet.Move("aaaa");

//        do {
//            Thread.sleep(1000);
//        }while (true);

//        System.exit(0);

//        AlgDijkstra algo = new AlgDijkstra();
//        algo.Do();

// Create intersector.

//        ArrayList<Double> a = new ArrayList<>();
//        Matrix ccc = new DefaultDenseDoubleMatrix2D(2, 3);
//        a.add(1.0);
//        a.add(2.0);
//        a.add(3.0);
//        a.add(4.0);
//        a.add(5.0);
//        a.add(6.0);
//
//        Matrix aaa = Matrix.Factory.importFromArray(a.toArray());
//        ccc.importFrom() = aaa.allValues();

//        System.out.println(theta.toHtml());

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
        graph.getNodes().add(new GraphNode(7, "G", "gpsInfo - G", 5, 0));  //7
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
