package com.maxrocky.gundam.coreservice.model.biz;

/**
 * Created by yuer5 on 16/6/28.
 */
public class DownStreamMetadata {

    private double x;
    private double y;
    private double z;

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

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public DownStreamMetadata(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
