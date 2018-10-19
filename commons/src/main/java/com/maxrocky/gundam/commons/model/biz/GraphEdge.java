package com.maxrocky.gundam.commons.model.biz;

/**
 * Created by yuer5 on 16/5/17.
 */

public class GraphEdge {

    private String name;
    private int nodeFrom;
    private int nodeTo;
    private double weight;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNodeFrom() {
        return nodeFrom;
    }

    public void setNodeFrom(int nodeFrom) {
        this.nodeFrom = nodeFrom;
    }

    public int getNodeTo() {
        return nodeTo;
    }

    public void setNodeTo(int nodeTo) {
        this.nodeTo = nodeTo;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public GraphEdge(){}

    public GraphEdge(String name, int nodeFrom, int nodeTo, double weight){
        this.name = name;
        this.nodeFrom = nodeFrom;
        this.nodeTo = nodeTo;
        this.weight = weight;
    }

}
