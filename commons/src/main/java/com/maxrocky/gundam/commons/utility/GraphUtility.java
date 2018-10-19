package com.maxrocky.gundam.commons.utility;

import com.maxrocky.gundam.commons.algorithm.CoordinateConvert;
import com.maxrocky.gundam.commons.algorithm.GPoint;
import com.maxrocky.gundam.commons.algorithm.GraphManager;
import com.maxrocky.gundam.commons.model.biz.Graph;
import com.maxrocky.gundam.commons.model.biz.GraphEdge;
import com.maxrocky.gundam.commons.model.biz.GraphNode;
import com.maxrocky.gundam.commons.algorithm.GGeometry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuer5 on 16/6/27.
 */
public class GraphUtility {
    /**
     * 形成转换后的GPS图，
     * 1、所有图节点转换为GPS坐标
     * 2、设置双向边，并且计算权重
     * 3、所有标记点转换为GPS坐标
     * @param picGraph  图片点形成的Graph
     * @param theta     转换参数theta
     * @param gpsGraph  转换成的GPS Graph
     */
    public static void fillGpsMapInfo(Graph picGraph, double[][] theta, Graph gpsGraph){


        //获得转换后的GPS点
        List<GPoint> originPoints = new ArrayList<>();
        for(GraphNode n : picGraph.getNodes()){
            originPoints.add(new GPoint(n.getX(), n.getY()));
        }
        CoordinateConvert convert = new CoordinateConvert();
        List<GPoint> transPoints = convert.GetTransPointsByGD(originPoints, theta);

        //Hash[id, Point]利于后面的计算
        Map<Integer,GPoint> pointsHash = new HashMap<Integer,GPoint>();
        List<GraphNode> gpsNodes = gpsGraph.getNodes();
        List<GraphNode> picNodes = picGraph.getNodes();
        for(int i=0; i< picNodes.size(); i++){
            gpsNodes.add(new GraphNode(picNodes.get(i).getId(),
                    picNodes.get(i).getName(),
                    picNodes.get(i).getName(),
                    transPoints.get(i).x,
                    transPoints.get(i).y));
            pointsHash.put(picNodes.get(i).getId(), transPoints.get(i));
        }

        //生成特标记点GPS坐标
        originPoints = new ArrayList<>();
        for(GraphNode n : picGraph.getNodes()){
            originPoints.add(new GPoint(n.getX(), n.getY()));
        }
        List<GPoint> transLabelPoints = convert.GetTransPointsByGD(originPoints, theta);
        List<GraphNode> picLabelNodes = picGraph.getLabeledNodes();
        List<GraphNode> gpsLabelNodes = gpsGraph.getLabeledNodes();
        for(int i=0; i< picLabelNodes.size(); i++){
            gpsLabelNodes.add(new GraphNode(picLabelNodes.get(i).getId(),
                    picLabelNodes.get(i).getName(),
                    picLabelNodes.get(i).getName(),
                    transLabelPoints.get(i).x,
                    transLabelPoints.get(i).y));
        }

        //生成GPS图的双向边及边权重
        int fromPointId;
        int toPointId;
        double distance;

        //doubleSideEdges 用于生成双向边，辅助去重
        Map<String,GraphEdge> doubleSideEdges = new HashMap<String,GraphEdge>();
        List<GraphEdge> picEdges = picGraph.getEdges();
        for(int i=0; i< picEdges.size(); i++){
            fromPointId = picEdges.get(i).getNodeFrom();
            toPointId = picEdges.get(i).getNodeTo();
            distance = pointsHash.get(fromPointId).GpsDistance(pointsHash.get(toPointId));
            doubleSideEdges.put(fromPointId + "-" + toPointId,
                    new GraphEdge(picEdges.get(i).getName(), fromPointId, toPointId, distance));
            doubleSideEdges.put(toPointId + "-" + fromPointId,
                    new GraphEdge(picEdges.get(i).getName(), toPointId, fromPointId, distance));
        }

        //加入双向边
        for (Map.Entry<String, GraphEdge> entry : doubleSideEdges.entrySet()) {
            gpsGraph.getEdges().add(entry.getValue());
        }

    }

    /**
     * 形成转换后的GPS图，
     * 1、设置双向边，
     * 2、计算权重
     * @param picGraph  图片点形成的Graph
     */
    public static Graph regularisationMapInfo(Graph picGraph){

        //Hash[id, Point]利于后面的计算
        Map<Integer,GraphNode> pointsHash = new HashMap<Integer,GraphNode>();

        for(GraphNode n : picGraph.getNodes()){
            pointsHash.put(n.getId(), n);
        }

        //生成GPS图的双向边及边权重
        int fromPointId;
        int toPointId;
        double distance;
        //doubleSideEdges 用于生成双向边，辅助去重
        Map<String,GraphEdge> doubleSideEdges = new HashMap<String,GraphEdge>();
        for(GraphEdge edge : picGraph.getEdges()){
            fromPointId = edge.getNodeFrom();
            toPointId = edge.getNodeTo();
            distance = pointsHash.get(fromPointId).MapDistance(pointsHash.get(toPointId));
            doubleSideEdges.put(fromPointId + "-" + toPointId,
                    new GraphEdge(edge.getName(), fromPointId, toPointId, distance));
            doubleSideEdges.put(toPointId + "-" + fromPointId,
                    new GraphEdge(edge.getName(), toPointId, fromPointId, distance));
        }

        List<GraphEdge> edges = new ArrayList<>();
        //加入双向边
        for (Map.Entry<String, GraphEdge> entry : doubleSideEdges.entrySet()) {
            edges.add(entry.getValue());
        }
        picGraph.setEdges(edges);

        return picGraph;

    }

    /**
     * 坐标转换，map点转化为GPS点
     * @param mapNodes
     * @param factors theta
     * @return
     */
    public static List<GraphNode> TransferNodeToGps(List<GraphNode> mapNodes, double[][] factors){

        CoordinateConvert convert = new CoordinateConvert();

        List<GPoint> originGPoints = new ArrayList<>();
        for (GraphNode node: mapNodes){
            originGPoints.add(new GPoint(node.getX(), node.getY()));
        }

        List<GPoint> transPoins = convert.GetTransPointsByGD(originGPoints, factors);

        List<GraphNode> gpsNodess = new ArrayList<>();

        for(int i=0; i<mapNodes.size(); i++) {
            gpsNodess.add(new GraphNode(mapNodes.get(i).getId(), mapNodes.get(i).getName(),
                    mapNodes.get(i).getLabel(), transPoins.get(i).x, transPoins.get(i).y));
        }
        return gpsNodess;

    }

    public static GraphNode TransferNodeToGps(GraphNode mapNode, double[][] factors){

        CoordinateConvert convert = new CoordinateConvert();

        GPoint originGPoint = new GPoint(mapNode.getX(), mapNode.getY());


        GPoint transPoint = convert.GetTransPointsByGD(originGPoint, factors);

        List<GraphNode> gpsNodess = new ArrayList<>();


        return new GraphNode(mapNode.getId(), mapNode.getName(),
                    mapNode.getLabel(), transPoint.x, transPoint.y);

    }

    public static GraphNode TransferNodeToMap(GraphNode gpsNode, double[][] theta){
        GraphNode ret = new GraphNode(0, gpsNode.getName(), gpsNode.getLabel(),
                (gpsNode.getX()-theta[0][1])/theta[0][0],
                (gpsNode.getY()-theta[1][1])/theta[1][0]);
        return ret;
    }

    /**
     * 把折线转换为一系列的点，点的间隔距离阈值不超过printSize
     * @param gpsNodes  折线的各个顶点
     * @param printSize 点的间隔距离
     * @return
     */
    public static List<GraphNode> GetGpsNodeFootprint(List<GraphNode> gpsNodes, int printSize){
        List<GraphNode> rets = new ArrayList<>();
        List<GPoint> points = new ArrayList<>();
        for(GraphNode node: gpsNodes){
            points.add(new GPoint(node.getX(), node.getY()));
        }
        List<GPoint> prints = GGeometry.GetGpsFootprints(points, printSize);
        int i = 1;
        for(GPoint node: prints){
            rets.add(new GraphNode(i, "" +i, "fp-" + i, node.x, node.y));
            i++;
        }
        return rets;
    }

    public static double getDistance(Graph map, double[][] theta, GraphNode startNode, GraphNode endNode){
        GraphManager graphManager = new GraphManager();

        GraphNode start = new GraphNode(0, "taskItem"+startNode.getId()+"Start", "taskItem"+startNode.getId()+ "Start",
                startNode.getX(), startNode.getY());
        GraphNode end = new GraphNode(0, "taskItem"+endNode.getId()+"End", "taskItem"+endNode.getId()+"End",
                endNode.getX(), endNode.getY());


        List<GraphNode> list = graphManager.DoFindPathByExternalPoint(map, start, end);
        List<GraphNode> gpslist = GraphUtility.TransferNodeToGps(list, theta);
        double distantSum = 0;
        for(int i =0; i<gpslist.size()-1;i++){
            GraphNode from = gpslist.get(i);
            GraphNode to = gpslist.get(i+1);
            double dis = from.GpsDistanceOnWGS84(to);
            distantSum = distantSum + dis;
        }

        return distantSum;

    }

    public static List<GraphNode> GetGPSPath(Graph map, double[][] theta, GraphNode startNode, GraphNode endNode){

        GraphManager graphManager = new GraphManager();

        GraphNode start = new GraphNode(0, "taskItem"+startNode.getId()+"Start", "taskItem"+startNode.getId()+ "Start",
                startNode.getX(), startNode.getY());
        GraphNode end = new GraphNode(0, "taskItem"+endNode.getId()+"End", "taskItem"+endNode.getId()+"End",
                endNode.getX(), endNode.getY());


        List<GraphNode> list = graphManager.DoFindPathByExternalPoint(map, start, end);
        List<GraphNode> gpslist = GraphUtility.TransferNodeToGps(list, theta);

        return gpslist;
    }

}
