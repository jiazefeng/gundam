package com.maxrocky.gundam.commons.model.biz;

import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;

/**
 * Created by yuer5 on 16/5/17.
 */
public class GraphNode {
    private int id;
    private double x;
    private double y;
    private String name;
    private String label;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public GraphNode(){}

    public GraphNode(int id, String name, String label, double x, double y){
        this.id = id;
        this.name = name;
        this.label = label;
        this.x = x;
        this.y = y;
    }

    public double GpsDistance(GraphNode p){
        //lon纬度 -- y
        //lat经度 -- x
        double PI = 3.14159265358979323; //圆周率
        double R = 6371229;             //地球的半径
        double x, y;
        x = (p.y - this.y) * PI * R * Math.cos(((this.x + p.x) / 2) * PI / 180) / 180;
        y = (p.x - this.x) * PI * R / 180;
        return Math.hypot(x, y);
    }

    public double GpsDistanceOnWGS84(GraphNode p){
        Point pointFrom = new Point(this.x, this.y);
        Point pointTo = new Point(p.getX(), p.getY());
        return GeometryEngine.geodesicDistanceOnWGS84(pointFrom, pointTo);
    }

    public double MapDistance(GraphNode p){

        return Math.hypot(this.x - p.x, this.y - p.y);
    }

}
