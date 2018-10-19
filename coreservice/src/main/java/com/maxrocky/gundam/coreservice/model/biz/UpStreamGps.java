package com.maxrocky.gundam.coreservice.model.biz;

/**
 * Created by yuer5 on 16/6/26.
 */
public class UpStreamGps {

    private double x;
    private double y;
    private double z;
    private int starNum;

    public UpStreamGps() {
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public int getStarNum() {
        return starNum;
    }

    public void setStarNum(int starNum) {
        this.starNum = starNum;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
