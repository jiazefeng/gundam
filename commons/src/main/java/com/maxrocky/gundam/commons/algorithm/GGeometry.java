package com.maxrocky.gundam.commons.algorithm;

import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuer5 on 16/5/26.
 */
public class GGeometry {

    public static List<Double> GetGeoDistance(List<GPoint> froms, List<GPoint> tos){

//        List<Double> distance = new ArrayList<>();
//        Matrix startMaxtrix = Matrix.Factory.zeros(froms.size(), 2);
//        Matrix endMaxtrix = Matrix.Factory.zeros(froms.size(), 2);
//
//        for (int i=0; i<froms.size(); i++){
//            startMaxtrix.setAsDouble(froms.get(i).xValue, i, 0);
//            startMaxtrix.setAsDouble(froms.get(i).yValue, i, 1);
//            endMaxtrix.setAsDouble(tos.get(i).xValue, i, 0);
//            endMaxtrix.setAsDouble(tos.get(i).yValue, i, 1);
//        }
//
//        Matrix distanceMatrix = startMaxtrix.minus(endMaxtrix).power(Calculation.Ret.ORIG, 2).sum(Calculation.Ret.NEW, 1, true).sqrt(Calculation.Ret.ORIG);
//
//        Iterator values= distanceMatrix.allValues().iterator();
//        while(values.hasNext()){
//            @SuppressWarnings("unchecked")
//            Double val=(Double)values.next();
//            distance.add(val);
//        }
//
//        return distance;

        List<Double> distance = new ArrayList<>();
        double d;

        for (int i=0; i<froms.size(); i++) {
            d = Math.hypot((froms.get(i).x - tos.get(i).x), ((froms.get(i).y - tos.get(i).y)));
            distance.add(d);
        }

        return distance;
    }

    public static List<GPoint> GetGpsFootprints(List<GPoint> points, int meterPitch){
        List<GPoint> footprints = new ArrayList<>();
        for(int i=0; i<points.size()-1; i++){
            Point pointFrom = new Point(points.get(i).x, points.get(i).y);
            Point pointTo = new Point(points.get(i+1).x, points.get(i+1).y);
            double distance = GeometryEngine.geodesicDistanceOnWGS84(pointFrom, pointTo);
            int partitionCount = (int)Math.floor(distance / meterPitch);
            footprints.add(points.get(i));
            for (int j=0; j<partitionCount; j++){
                double x = points.get(i).x + (points.get(i+1).x-points.get(i).x)/(partitionCount+1)*(j+1);
                double y = points.get(i).y + (points.get(i+1).y-points.get(i).y)/(partitionCount+1)*(j+1);
                footprints.add(new GPoint(x,y));
            }
        }
        footprints.add(points.get(points.size()-1));
        return footprints;
    }

    public static List<Double> GetGpsDistince(List<GPoint> froms, List<GPoint> tos){

        List<Double> distanceList = new ArrayList<>();

        double PI = 3.14159265358979323; //圆周率
        double R = 6371229;             //地球的半径
        double x, y, d;

//        for (int i=0; i<froms.size(); i++){
//            x = (tos.get(i).lon - froms.get(i).lon) * PI * R * Math.cos(((froms.get(i).lat + tos.get(i).lat) / 2) * PI / 180) / 180;
//            y = (tos.get(i).lat - froms.get(i).lat) * PI * R / 180;
//            d = Math.hypot(x, y);
//            distanceList.add(d);
//        }
        for (int i=0; i<froms.size(); i++){
            x = (tos.get(i).x - froms.get(i).x) * PI * R * Math.cos(((froms.get(i).y + tos.get(i).y) / 2) * PI / 180) / 180;
            y = (tos.get(i).y - froms.get(i).y) * PI * R / 180;
            d = Math.hypot(x, y);
            distanceList.add(d);
        }

        return distanceList;
    }

    public static List<Double> GetGpsDistinceByEsri(List<GPoint> froms, List<GPoint> tos){

        List<Double> distanceList = new ArrayList<>();

        for (int i=0; i<froms.size(); i++){

            Point pointFrom = new Point(froms.get(i).x, froms.get(i).y);
            Point pointTo = new Point(tos.get(i).x, tos.get(i).y);
            distanceList.add(GeometryEngine.geodesicDistanceOnWGS84(pointFrom, pointTo));
        }
        return distanceList;
    }

    public static double GetGpsDistince(GPoint from, GPoint to) {

        double PI = 3.14159265358979323; //圆周率
        double R = 6371229;             //地球的半径
        double x, y, d;

//        x = (to.lon - from.lon) * PI * R * Math.cos(((from.lat + to.lat) / 2) * PI / 180) / 180;
//        y = (to.lat - from.lat) * PI * R / 180;
        x = (to.x - from.x) * PI * R * Math.cos(((from.y + to.y) / 2) * PI / 180) / 180;
        y = (to.y - from.y) * PI * R / 180;
        d = Math.hypot(x, y);

        return d;
    }



    /**
     * 计算GPS两点间的距离[单位为:米]
     * @param center GPS当前数据(LonLat对象表示,LonLat.lon表示经度，LonLat.lat表示纬度)
     * @param turnPoint 转向点经纬度对象
     * @return
     */
    private double gpsDistance( GPoint center, GPoint turnPoint )
    {
//        double distance = 0;
//        double lonRes = 102900, latRes = 110000;
//        distance = Math.sqrt(
//                        Math.abs( center.lat - turnPoint.lat ) * latRes *
//                                Math.abs( center.lat - turnPoint.lat ) * latRes +
//                        Math.abs( center.lon - turnPoint.lon ) * lonRes *
//                                Math.abs( center.lon - turnPoint.lon ) * lonRes );
//        return distance;
        double distance = 0;
        double lonRes = 102900, latRes = 110000;
        distance = Math.sqrt(
                Math.abs( center.y - turnPoint.y ) * latRes *
                        Math.abs( center.y - turnPoint.y ) * latRes +
                        Math.abs( center.x - turnPoint.x ) * lonRes *
                                Math.abs( center.x - turnPoint.x ) * lonRes );
        return distance;
    }
}
