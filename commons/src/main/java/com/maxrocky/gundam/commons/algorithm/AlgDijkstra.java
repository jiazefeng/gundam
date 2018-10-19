package com.maxrocky.gundam.commons.algorithm;

import java.util.*;

/**
 * Created by yuer5 on 16/5/16.
 */
public class AlgDijkstra {

    //源点集合
    public Set<GVNode> sNodes;
    //最小距离优先队列 V[G]
    Map<GVNode, Double> qDistance;

    public GVNode sourceNode = null;
    public Set<GVNode> nodes = null;

    public AlgDijkstra(Set<GVNode> graphStructs){
        nodes = graphStructs;
    }
    
    public Stack<String> DoFindPath(String startNodeName, String endNodeName){

        this.CalculateSSSP(startNodeName);

        GVNode targetNode = this.FindNode(endNodeName);

        Stack<String> sk = new Stack<String>();

        while (targetNode.getPiNode()!= null)
        {
            sk.push(targetNode.getPiNode().getName());
            targetNode = targetNode.getPiNode();
        }

//        System.out.println(nodes.size());
        return sk;

    }

    //计算单源最短路径（single-source shortest paths）
    private void CalculateSSSP(String startNodeName){

        sourceNode = FindNode(startNodeName);
        sourceNode.setDistance(0);

        sNodes = new HashSet<>();
        qDistance = this.initDistance(nodes, sourceNode);

        while (qDistance.size() > 0) {
            GVNode newNode = ExtractMin(qDistance);
            if(newNode != null) {
                qDistance.remove(newNode);
                sNodes.add(newNode);
                Relax(newNode, qDistance);
            }
        }
    }

    private GVNode FindNode(String startNodeName){

        GVNode sourceNode = nodes.stream().filter(f -> startNodeName.equals(f.getName())).findFirst().get();

        return sourceNode;
    }

    //初始化最小距离优先队列 V[G]
    private Map<GVNode,Double> initDistance(Set<GVNode> nodes, GVNode s){
        Map<GVNode,Double> qDistance = new HashMap<GVNode,Double>();
        for (GVNode node : nodes) {
            if(s == node)
                qDistance.put(node, 0.0);
            else
                qDistance.put(node, Integer.MAX_VALUE*1.0);
        }
        return qDistance;
    }

    private GVNode ExtractMin(Map<GVNode,Double> qDistance){

        GVNode MinNode = null;
        double MinDistance = Integer.MAX_VALUE;
        for (Map.Entry<GVNode, Double> entry : qDistance.entrySet()) {
            if(MinDistance > entry.getValue()){
                MinDistance = entry.getValue();
                MinNode = entry.getKey();
            }
        }
        return MinNode;
    }

    private void Relax(GVNode sourceNode, Map<GVNode,Double> distance) {

//        Map<GVNode,Double> neighbor = sourceNode.getNeighbor();

        for (GVNode n : sourceNode.getNeighbor().keySet()) {
            if(distance.keySet().contains(n)) {
                Double newCompute = sourceNode.getDistance() + sourceNode.getNeighbor().get(n);
                if(distance.get(n) > newCompute) {
                    distance.put(n, newCompute);
                    n.setPiNode(sourceNode);
                    n.setDistance(newCompute);
                }
            }
        }

    }



}
