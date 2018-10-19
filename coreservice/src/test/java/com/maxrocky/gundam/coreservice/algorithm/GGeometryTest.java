package com.maxrocky.gundam.coreservice.algorithm;

import com.maxrocky.gundam.commons.algorithm.GGeometry;
import com.maxrocky.gundam.commons.algorithm.GPoint;
import org.junit.Before;
import org.junit.Test;
import weka.core.matrix.LinearRegression;
import weka.core.matrix.Matrix;
//import org.ujmp.core.Matrix;
//import org.ujmp.core.calculation.Calculation;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by yuer5 on 16/5/26.
 */
public class GGeometryTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGetGeoDistance() throws Exception {
        List<GPoint> fromGPoints = new ArrayList<>();
        fromGPoints.add(new GPoint(0, 0));
        fromGPoints.add(new GPoint(2, 1));
        fromGPoints.add(new GPoint(5, 5));

        List<GPoint> toPoints = new ArrayList<>();
        toPoints.add(new GPoint(0, 2)); //2
        toPoints.add(new GPoint(2, 4)); //3
        toPoints.add(new GPoint(11, 13)); //10

        List<Double> distance = GGeometry.GetGeoDistance(fromGPoints, toPoints);

        assertEquals(distance.get(0), 2, 0.01);
        assertEquals(distance.get(1), 3, 0.01);
        assertEquals(distance.get(2), 10, 0.01);

    }

    @Test
    public void testGetGpsDistince() throws Exception {

        List<GPoint> froms = new ArrayList<>();

        List<GPoint> tos = new ArrayList<>();
        froms.add(new GPoint(116.41742, 39.966452));   //199
        tos.add(new GPoint(116.417492, 39.968235));

        froms.add(new GPoint(116.417492, 39.968235));  //256
        tos.add(new GPoint(116.420497, 39.968223));
        List<Double> distance = GGeometry.GetGpsDistince(froms, tos);
        List<Double> distance2 = GGeometry.GetGpsDistinceByEsri(froms, tos);

        assertEquals(distance.get(0), 199, 1);
        assertEquals(distance.get(1), 256, 1);

        assertEquals(distance2.get(0), 199, 1);
        assertEquals(distance2.get(1), 256, 1);
    }

    @Test
    public void testGetGpsFootprints() throws Exception {

        List<GPoint> points = new ArrayList<>();

        points.add(new GPoint(116.41742, 39.966452));
        points.add(new GPoint(116.417492, 39.968235));  //199 - 39个点
        points.add(new GPoint(116.420497, 39.968223));     //256 － 51个点
        List<GPoint> footprints = GGeometry.GetGpsFootprints(points, 5);

        assertEquals(footprints.size(), 90, 3);
    }


    @Test
    public void testMatrixDevide() throws Exception {

//        List<GPoint> trans = new ArrayList<>();
//
//        Matrix mapXx = Matrix.Factory.ones(3, 2);
//        mapXx.setAsDouble(3, 0, 0);
//        mapXx.setAsDouble(2, 1, 0);
//        mapXx.setAsDouble(1, 2, 0);
//
//        Matrix thetax = Matrix.Factory.ones(1, 2);
//        thetax.setAsDouble(1, 0, 0);
//        thetax.setAsDouble(2, 0, 1);
//        Matrix mapyx = mapXx.mtimes(thetax.transpose());
//
//
//        Matrix gpsx = Matrix.Factory.ones(3, 1);
//        gpsx.setAsDouble(5, 0, 0);
//        gpsx.setAsDouble(4, 1, 0);
//        gpsx.setAsDouble(3, 2, 0);
//
//        Matrix test2 = gpsx.minus(thetax.getAsDouble(0, 1)).divide(thetax.getAsDouble(0, 0));
//
//////        AX = B -> X = (A^-1) * B
//////        XA = B -> X = B * (A^-1)
//
//        Matrix test3 = gpsx.solve(mapXx);
//        Matrix test4 = mapXx.solve(gpsx);
//        Matrix test6 = gpsx.solve(thetax);
//        Matrix test5 = thetax.solve(gpsx);
//
////        Matrix test4 = mapXx.solve(thetax);
//
//
//////        Xt = A0 + A1*Xs + A2*Ys
//////        Yt = B0 + B1*Xs + B2*Ys
////        if(originGPoints.size() != transGPoints.size() || originGPoints.size() != 3) {
////            System.out.println("Error");
////            return null;
////        }
////
////        Matrix aAxis = Matrix.Factory.ones(3, 3);
////        Matrix xt = Matrix.Factory.ones(3, 1);
////        Matrix yt = Matrix.Factory.ones(3, 1);
////        for (int i=0; i<3; i++){
////            aAxis.setAsDouble(originGPoints.get(i).x, i, 1);
////            aAxis.setAsDouble(originGPoints.get(i).y, i, 2);
////            xt.setAsDouble(transGPoints.get(i).x, i, 0);
////            yt.setAsDouble(transGPoints.get(i).y, i, 0);
////        }
////
//////        AX = B -> X = (A^-1) * B
//////        XA = B -> X = B * (A^-1)
////
////        Matrix result1 = aAxis.solve(xt).transpose();
////        Matrix result2 = aAxis.solve(yt).transpose();
////
////        return result1.appendVertically(Calculation.Ret.NEW, result2);
//
//
//
//
////        Matrix mapMaxtrix = Matrix.Factory.zeros(3, 2);
////        mapMaxtrix.setAsDouble(10, 0, 1);
////        mapMaxtrix.setAsDouble(9, 1, 1);
////        mapMaxtrix.setAsDouble(4, 2, 1);
////
////        Matrix theta = Matrix.Factory.zeros(2, 2);
////        theta.setAsDouble(1, 0, 0);
////        theta.setAsDouble(2, 0, 1);
////        theta.setAsDouble(1, 1, 0);
////        theta.setAsDouble(3, 1, 1);
////
////        Matrix gpsMatrix = mapMaxtrix.times(theta);
//
//
//
//        assertEquals(90, 90, 3);
//        assertEquals(mapyx.getAsDouble(0,0), gpsx.getAsDouble(0,0), 1);
////        assertEquals(test.getAsDouble(0,0), 3, 1);
    }






}