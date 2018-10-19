package com.maxrocky.gundam.commons.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuer5 on 16/5/16.
 */
public class GVNode {

    private String name;

    private Map<GVNode,Double> neighbor = new HashMap<GVNode,Double>();    //edge with weight;

    private Double distance;

    private GVNode piNode;

    public GVNode(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<GVNode, Double> getNeighbor() {
        return neighbor;
    }

    public void setNeighbor(Map<GVNode, Double> neighbor) {
        this.neighbor = neighbor;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public GVNode getPiNode() {
        return piNode;
    }

    public void setPiNode(GVNode piNode) {
        this.piNode = piNode;
    }
}
