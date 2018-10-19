package com.maxrocky.gundam.commons.algorithm;

import com.esri.core.geometry.*;

import com.maxrocky.gundam.commons.model.biz.Graph;
import com.maxrocky.gundam.commons.model.biz.GraphEdge;
import com.maxrocky.gundam.commons.model.biz.GraphNode;

import java.util.*;

/**
 * Created by yuer5 on 16/5/16.
 */
public class GraphManager {

    private AlgDijkstra algo;
    private Graph graph;

    public GraphManager(){

    }

    public List<GraphNode> DoFindPathByExternalPoint(Graph graph, GraphNode start, GraphNode end){

        this.graph = graph;

        int maxid = graph.getNodes().stream().max((p1, p2) -> (p1.getId() - p2.getId())).get().getId();
        double startx = start.getX();
        double starty = start.getY();
        Optional<GraphNode> startNodeInGraph = graph.getNodes().stream().filter(a -> a.getX() == startx && a.getY() == starty).findFirst();
        if(!startNodeInGraph.isPresent()) {
            start.setName("start");
            start.setId(++maxid);
            this.graph.getNodes().add(start);

        }else {
            start = startNodeInGraph.get();
        }

        double endx = end.getX();
        double endy = end.getY();
        Optional<GraphNode> endNodeInGraph = graph.getNodes().stream().filter(a->a.getX() == endx && a.getY() == endy).findFirst();
        if(!endNodeInGraph.isPresent()) {
            end.setName("end");
            end.setId(++maxid);
            this.graph.getNodes().add(end);

        } else {
            end = endNodeInGraph.get();
        }

        AddPointToGraph(start, maxid++);
        AddPointToGraph(end, maxid++);

        algo = new AlgDijkstra(GraphConverter());

        return GetPath(start, end);
    }

    public List<GraphNode> DoFindPath(Graph graph, GraphNode start, GraphNode end) {

        this.graph = graph;

        algo = new AlgDijkstra(GraphConverter());

        return GetPath(start, end);
    }

    private List<GraphNode> GetPath(GraphNode start, GraphNode end){

        List<GraphNode> ret = new ArrayList<GraphNode>();

        Stack<String> stack = algo.DoFindPath(String.valueOf(start.getId()), String.valueOf(end.getId()));
        while (!stack.isEmpty())
        {
            String idKey = stack.pop();
            GraphNode sourceNode = this.graph.getNodes().stream().filter(f->f.getId() == Integer.valueOf(idKey).intValue()).findFirst().get();
            ret.add(sourceNode);
        }
        ret.add(end);
        return ret;
    }

    private void AddPointToGraph(GraphNode start, int maxid) {
        GraphEdge closestEdge = findClosestEdge(start);
        GraphNode vertexA = graph.getNodes().stream().filter(a ->a.getId() == closestEdge.getNodeFrom()).findFirst().get();
        GraphNode vertexB = graph.getNodes().stream().filter(a ->a.getId() == closestEdge.getNodeTo()).findFirst().get();

        GraphNode crossPoint = GetPoint(start, closestEdge);
        double x = crossPoint.getX();
        double y = crossPoint.getY();

        Optional<GraphNode> crossPointNodeInGraph = graph.getNodes()
                .stream()
                .filter(a -> a.getX() == x && a.getY() == y).findFirst();
        if(!crossPointNodeInGraph.isPresent()) {
            crossPoint.setName("Joined(" + start.getName() + ")");
            crossPoint.setId(++maxid);
            graph.getNodes().add(crossPoint);
        }else {
            crossPoint = crossPointNodeInGraph.get();
        }

        graph.getEdges().add(new GraphEdge("[" + start.getName() + "]-[" + crossPoint.getName()+"]",
                start.getId(), crossPoint.getId(), start.MapDistance(crossPoint)));
        graph.getEdges().add(new GraphEdge("[" + crossPoint.getName() + "]-[" + start.getName()+"]",
                crossPoint.getId(), start.getId(), start.MapDistance(crossPoint)));

        graph.getEdges().add(new GraphEdge("[" + crossPoint.getName() + "]-[" + vertexA.getName()+"]",
                crossPoint.getId(), vertexA.getId(), vertexA.MapDistance(crossPoint)));
        graph.getEdges().add(new GraphEdge("[" + vertexA.getName() + "]-[" + crossPoint.getName()+"]",
                vertexA.getId(), crossPoint.getId(), vertexA.MapDistance(crossPoint)));

        graph.getEdges().add(new GraphEdge("[" + crossPoint.getName() + "]-[" + vertexB.getName()+"]",
                crossPoint.getId(), vertexB.getId(), vertexB.MapDistance(crossPoint)));
        graph.getEdges().add(new GraphEdge("[" + vertexB.getName() + "]-[" + crossPoint.getName()+"]",
                vertexB.getId(), crossPoint.getId(), vertexB.MapDistance(crossPoint)));
    }

    private GraphEdge findClosestEdge(GraphNode node){
        GraphEdge closestEdge = graph.getEdges().get(0);
        double distance = Double.MAX_VALUE;
        for(GraphEdge edge : graph.getEdges()){
            double temp = this.GetDistance(node, edge);
            if(temp < distance) {
                distance = temp;
                closestEdge = edge;
            }
        }
        return closestEdge;
    }

    private GraphNode GetPoint(GraphNode node, GraphEdge edge){

        GraphNode ret;
        GraphNode fromnode = graph.getNodes().stream().filter(a ->a.getId() == edge.getNodeFrom()).findFirst().get();
        GraphNode tonode = graph.getNodes().stream().filter(a ->a.getId() == edge.getNodeTo()).findFirst().get();
        Polyline line = new Polyline();
        line.startPath(fromnode.getX(), fromnode.getY());
        line.lineTo(tonode.getX(), tonode.getY());

        Point point = new Point(node.getX(), node.getY());

        Point joinPoint = GeometryEngine.getNearestCoordinate(line, point, false).getCoordinate();
        ret = new GraphNode(Integer.MAX_VALUE, "joinPoint", "joinPoint", joinPoint.getX(), joinPoint.getY());
        return ret;
    }

    private double GetDistance(GraphNode node, GraphEdge edge){

        GraphNode fromnode = graph.getNodes().stream().filter(a ->a.getId() == edge.getNodeFrom()).findFirst().get();
        GraphNode tonode = graph.getNodes().stream().filter(a ->a.getId() == edge.getNodeTo()).findFirst().get();

        Polyline line = new Polyline();
        line.startPath(fromnode.getX(), fromnode.getY());
        line.lineTo(tonode.getX(), tonode.getY());

        Point point = new Point(node.getX(), node.getY());

        return GeometryEngine.distance(line, point, null);

    }

    private double GetGPSDistance(GraphNode fromnode, GraphNode tonode ){

        Point pointFrom = new Point(fromnode.getX(), fromnode.getY());
        Point pointTo = new Point(tonode.getX(), tonode.getY());
        return GeometryEngine.geodesicDistanceOnWGS84(pointFrom, pointTo);

    }

    public static Graph MockGenerateGraph() {

        Graph graph = new Graph();

        graph.setNodes(new ArrayList<GraphNode>() {});

        graph.getNodes().add(new GraphNode(1, "A", "gpsInfo - A", 0, 0));  //1
        graph.getNodes().add(new GraphNode(2, "B", "gpsInfo - B", 0, 0));  //2
        graph.getNodes().add(new GraphNode(3, "C", "gpsInfo - C", 0, 0));  //3
        graph.getNodes().add(new GraphNode(4, "D", "gpsInfo - D", 0, 0));  //4
        graph.getNodes().add(new GraphNode(5, "E", "gpsInfo - E", 0, 0));  //5
        graph.getNodes().add(new GraphNode(6, "F", "gpsInfo - F", 0, 0));  //6
        graph.getNodes().add(new GraphNode(7, "G", "gpsInfo - G", 0, 0));  //7
        graph.getNodes().add(new GraphNode(8, "H", "gpsInfo - H", 0, 0));  //8
        graph.getNodes().add(new GraphNode(9, "I", "gpsInfo - I", 0, 0));  //9
        graph.getNodes().add(new GraphNode(10, "J", "gpsInfo - J", 0, 0));  //10
        graph.getNodes().add(new GraphNode(11, "K", "gpsInfo - K", 0, 0));  //11
        graph.getNodes().add(new GraphNode(12, "L", "gpsInfo - L", 0, 0));  //12
        graph.getNodes().add(new GraphNode(13, "M", "gpsInfo - M", 0, 0));  //13

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

    private Set<GVNode> GraphConverter(){

        Map<Integer, GVNode> nodeList = new HashMap<Integer, GVNode>();

        for(GraphNode n : graph.getNodes()){
            nodeList.put(n.getId(), new GVNode(String.valueOf(n.getId())));
        }

        for(GraphEdge edge : graph.getEdges()){
            GVNode nFrom = nodeList.get(edge.getNodeFrom());
            GVNode nTo = nodeList.get(edge.getNodeTo());
            nFrom.getNeighbor().put(nTo, edge.getWeight());
        }
        Set<GVNode> nodes = new HashSet<>();

        for(GVNode n : nodeList.values()) {
            nodes.add(n);
        }

        return nodes;

    }

}
