package com.maxrocky.gundam.coreservice.algorithm;

/**
 * Created by yuer5 on 16/5/26.
 */
public class GpsPoint {
//    LonLat.lon表示经度，LonLat.lat表示纬度
    public double lon1;  //y
    public double lat1;  //x

    public GpsPoint(double lon, double lat){
        this.lat1 = lat;
        this.lon1 = lon;
    }

    public double Distance(GpsPoint p){
        double PI = 3.14159265358979323; //圆周率
        double R = 6371229;             //地球的半径
        double x, y;
        x = (p.lon1 - this.lon1) * PI * R * Math.cos(((this.lat1 + p.lat1) / 2) * PI / 180) / 180;
        y = (p.lat1 - this.lat1) * PI * R / 180;
        return Math.hypot(x, y);
    }
}
