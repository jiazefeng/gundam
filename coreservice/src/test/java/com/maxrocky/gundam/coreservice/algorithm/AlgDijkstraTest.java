package com.maxrocky.gundam.coreservice.algorithm;

import com.maxrocky.gundam.commons.algorithm.GraphManager;
import com.maxrocky.gundam.commons.model.biz.Graph;
import com.maxrocky.gundam.commons.model.biz.GraphEdge;
import com.maxrocky.gundam.commons.model.biz.GraphNode;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuer5 on 16/6/20.
 */
public class AlgDijkstraTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testDoFindPath() throws Exception {

        Graph graph = MockGenerateGraph();
        GraphManager graphManager = new GraphManager();
        List<GraphNode> list = graphManager.DoFindPath(graph, graph.getNodes().get(0),
                graph.getNodes().get(12));
        for(GraphNode n :list){
            System.out.println(n.getId() + ":" + n.getName());
        }
    }

    @Test
    public void testDoFindPath2() throws Exception {

        Graph graph = MockGenerateGraph();
        GraphManager graphManager = new GraphManager();

        GraphNode start = new GraphNode(14,"start","start",3,10);
        GraphNode end = new GraphNode(15,"end","end",9,5);
        List<GraphNode> list = graphManager.DoFindPathByExternalPoint(graph, start,
                end);

        for(GraphNode n :list){
            System.out.println(n.getId() + ":" + n.getName());
        }
    }


    private static Graph MockGenerateGraph() {

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
