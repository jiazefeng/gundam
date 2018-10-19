package com.maxrocky.gundam.commons.algorithm;

import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;

/**
 * Created by yuer5 on 16/5/21.
 */
public class GPoint {

    public double x;
    public double y;

    public GPoint(){

    }

    public GPoint(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double GpsDistance(GPoint p){
        //lon纬度 -- y
        //lat经度 -- x
        double PI = 3.14159265358979323; //圆周率
        double R = 6371229;             //地球的半径
        double x, y;
        x = (p.x - this.y) * PI * R * Math.cos(((this.x + p.x) / 2) * PI / 180) / 180;
        y = (p.x - this.x) * PI * R / 180;
        return Math.hypot(x, y);
    }

    public double GpsDistanceOnWGS84(GPoint p){
        Point pointFrom = new Point(this.x, this.y);
        Point pointTo = new Point(p.x, p.y);
        return GeometryEngine.geodesicDistanceOnWGS84(pointFrom, pointTo);
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
}
