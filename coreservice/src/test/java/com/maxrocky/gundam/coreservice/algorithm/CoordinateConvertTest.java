package com.maxrocky.gundam.coreservice.algorithm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxrocky.gundam.commons.algorithm.CoordinateConvert;
import com.maxrocky.gundam.commons.algorithm.GPoint;
import com.maxrocky.gundam.commons.core.MapCorrectionPoints;
import com.maxrocky.gundam.commons.core.PointPair;
import org.junit.Before;
import org.junit.Test;
import org.ujmp.core.Matrix;
import weka.core.matrix.LinearRegression;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by yuer5 on 16/5/23.
 */
public class CoordinateConvertTest {

    CoordinateConvert convert;

    @Before
    public void setUp() throws Exception {
        convert = new CoordinateConvert();

    }

    @Test
    public void testGetFactorsByEquations() throws Exception {

        List<GPoint> originGPoints = GetOriginal(2);
        List<GPoint> transGPoints = GetTrans(2, false);

        Matrix theta = convert.GetFactorsByEquations(originGPoints.subList(0,3), transGPoints.subList(0,3));

        List<Double> params = new ArrayList<>();
        Iterator values= theta.allValues().iterator();
        while(values.hasNext()){
            @SuppressWarnings("unchecked")
            Double val=(Double)values.next();
            params.add(val);
        }
        params.forEach(System.out::println);

        Matrix tempOrigin = Matrix.Factory.ones(1,3);
        tempOrigin.setAsDouble(originGPoints.get(3).x, 0, 1);
        tempOrigin.setAsDouble(originGPoints.get(3).y, 0, 2);
        Matrix tempPic = tempOrigin.mtimes(theta.transpose());
        assertEquals(tempPic.getAsDouble(0, 0), transGPoints.get(3).x, 0.01);
        assertEquals(tempPic.getAsDouble(0, 1), transGPoints.get(3).y, 0.01);

    }

    @Test
    public void testGetFactorsByGradintDescent() throws Exception {

        long begin, end;
        begin = System.currentTimeMillis();
        Matrix theta = convert.GetFactorsByGradintDescent(GetOriginal(30), GetTrans(30, true));
        end = System.currentTimeMillis();
        System.out.println("计算耗时" + (end - begin) + "ms");

        List<Double> params = new ArrayList<>();
        Iterator values= theta.allValues().iterator();
        while(values.hasNext()){
            @SuppressWarnings("unchecked")
            Double val=(Double)values.next();
            params.add(val);
        }
        params.forEach(System.out::println);


        assertEquals(params.size(), 4);
        assertEquals(params.get(0), -2.00, 0.01);
        assertEquals(params.get(1), 1.0, 0.01);
        assertEquals(params.get(2), 20000.0, 1);
        assertEquals(params.get(3), 1000.0, 0.01);

//        (380,366)->(116.418714,39.96721)

    }


    @Test
    public void testGetTransPointsByGDFromBaiduMap() throws Exception {

        List<GPoint> originGPoints = new ArrayList<>();
        originGPoints.add(new GPoint(380,366));

        Matrix theta = convert.GetFactorsByGradintDescent(GetOriginalFromMap(), GetTargetFromGPS());

        List<Double> params = new ArrayList<>();
        Iterator values= theta.allValues().iterator();
        while(values.hasNext()){
            @SuppressWarnings("unchecked")
            Double val=(Double)values.next();
            params.add(val);
        }

        double[][] factors = new double[2][2];
        factors[0][0] = params.get(0); factors[0][1] = params.get(1);
        factors[1][0] = params.get(2); factors[1][1] = params.get(3);

        List<GPoint> verifyPoints = new ArrayList<>();
        verifyPoints.add(new GPoint(116.418714, 39.96721));

        List<GPoint> transPoins = convert.GetTransPointsByGD(originGPoints, factors);

        for(int i=0; i<originGPoints.size(); i++) {
//            assertEquals(transPoins.get(i).x, verifyPoints.get(i).x, 0.01 );
//            assertEquals(transPoins.get(i).y, verifyPoints.get(i).y, 0.01 );
        }
    }

    @Test
    public void testWeka()throws Exception{
        //线性回归，计算x'=a*x+b的拟合因子
        //下面为对输入x进行设定
        //考虑偏移量，将式子改写为x'=a*x0+b*x1，其中的x1一直为1，所以，有以下赋值
        weka.core.matrix.Matrix inputMatrix=new weka.core.matrix.Matrix(5,2);
        inputMatrix.set(0,0,10);
        inputMatrix.set(0,1,1);
        inputMatrix.set(1,0,30);
        inputMatrix.set(1,1,1);
        inputMatrix.set(2,0,70);
        inputMatrix.set(2,1,1);
        inputMatrix.set(3,0,0);
        inputMatrix.set(3,1,1);
        inputMatrix.set(4,0,100);
        inputMatrix.set(4,1,1);

        //下面为对输出x'进行设定
        weka.core.matrix.Matrix outputMatrix=new weka.core.matrix.Matrix(5,1);
        outputMatrix.set(0,0,100+30);
        outputMatrix.set(1,0,300+30);
        outputMatrix.set(2,0,700+30);
        outputMatrix.set(3,0,0+30);
        outputMatrix.set(4,0,1000+30);
//        outputMatrix.set(1,1,300);
        LinearRegression lr = new LinearRegression(inputMatrix,outputMatrix,0);

        //得到a为10，b为30
        double[] coefficients=lr.getCoefficients();

        assertEquals(90, 90, 3);

        System.out.print(coefficients);
    }

    private weka.core.matrix.Matrix GetInputXMax(List<GPoint> gPoints, int index){
        weka.core.matrix.Matrix inputMatrix = new weka.core.matrix.Matrix(gPoints.size(), 2, 1);
        for(int i=0; i<gPoints.size(); i++){
            if(index == 0)
                inputMatrix.set(i, 0, gPoints.get(i).x);
            else
                inputMatrix.set(i, 0, gPoints.get(i).y);
        }
        return inputMatrix;
    }

    private weka.core.matrix.Matrix GetOutputyMax(List<GPoint> gPoints, int index){
        weka.core.matrix.Matrix inputMatrix = new weka.core.matrix.Matrix(gPoints.size(), 1);
        for(int i=0; i<gPoints.size(); i++){
            if(index == 0)
                inputMatrix.set(i, 0, gPoints.get(i).x);
            else
                inputMatrix.set(i, 0, gPoints.get(i).y);
        }
        return inputMatrix;
    }


    @Test
    public void testWeka1()throws Exception{

        //线性回归，计算x'=a*x+b的拟合因子
        //下面为对输入x进行设定
        //考虑偏移量，将式子改写为x'=a*x0+b*x1，其中的x1一直为1，所以，有以下赋值
        List<GPoint> originals = GetOriginalFromMap();
        weka.core.matrix.Matrix Xx = GetInputXMax(originals, 0);
        weka.core.matrix.Matrix Xy = GetInputXMax(originals, 1);

        //下面为对输出x'进行设定
        List<GPoint> gps = GetTargetFromGPS();
        weka.core.matrix.Matrix yx = GetOutputyMax(gps, 0);
        weka.core.matrix.Matrix yy = GetOutputyMax(gps, 1);
        LinearRegression lrx = new LinearRegression(Xx,yx,0);
        LinearRegression lry = new LinearRegression(Xy,yy,0);

        //得到a为10，b为30
        double[] coefficients=lrx.getCoefficients();
        double[] coefficientsy=lry.getCoefficients();

        assertEquals(90, 90, 3);

        //(380,366)->(116.418714,39.96721)
        double transx = 380*coefficients[0] + coefficients[1];
        double transy = 366*coefficientsy[0] + coefficientsy[1];
        assertEquals(transx, 116.418714, 0.01);
        assertEquals(transy, 39.96721, 0.01);

        GPoint gpsReal = new GPoint(116.418714,39.96721);
        GPoint gpsTrans = new GPoint(transx,transy);
        double distance = gpsReal.GpsDistanceOnWGS84(gpsTrans);


        System.out.print(coefficients);
    }


    @Test
    public void testGetTransPointsByGD() throws Exception {

        List<GPoint> originGPoints = new ArrayList<>();
        originGPoints.add(new GPoint(0, 0));
        originGPoints.add(new GPoint(2, 1));
        originGPoints.add(new GPoint(5, 5));

        double[][] factors = new double[2][2];
        factors[0][0] = 2; factors[0][1] = 1;
        factors[1][0] = 2; factors[1][1] = 2;

        List<GPoint> verifyPoints = new ArrayList<>();
        verifyPoints.add(new GPoint(2, 2));
        verifyPoints.add(new GPoint(4, 4));
        verifyPoints.add(new GPoint(7, 12));

        List<GPoint> transPoins = convert.GetTransPointsByGD(originGPoints, factors);

//        for(int i=0; i<originGPoints.size(); i++) {
//            assertEquals(transPoins.get(i).x, verifyPoints.get(i).x, 0.01 );
//            assertEquals(transPoins.get(i).y, verifyPoints.get(i).y, 0.01 );
//        }

    }

    @Test
    public void testGetTransPointsByEquations() throws Exception {

        List<GPoint> originGPoints = new ArrayList<>();
        originGPoints.add(new GPoint(0, 0));
        originGPoints.add(new GPoint(4, 0));
        originGPoints.add(new GPoint(0, 4));

        List<GPoint> verifyPoints = new ArrayList<>();
        verifyPoints.add(new GPoint(4, 4));
        verifyPoints.add(new GPoint(8, 4));
        verifyPoints.add(new GPoint(4, 8));

        List<double[]> factors = new ArrayList<>();
        factors.add(new double[]{4, 1, 0});
        factors.add(new double[]{4, 0, 1});

        List<GPoint> transPoins = convert.GetTransPointsByEquations(originGPoints, factors);

        for(int i=0; i<originGPoints.size(); i++) {
            assertEquals(transPoins.get(i).x, verifyPoints.get(i).x, 0.01);
            assertEquals(transPoins.get(i).y, verifyPoints.get(i).y, 0.01);
        }
    }

    private static List<GPoint> GetOriginal(int length){

        List<GPoint> points = new ArrayList<>();
        for(int i=0; i<length; i++) {
            points.add(new GPoint(i, 2 * i));
            points.add(new GPoint(2 * i + 1, i));
        }
        return points;
    }

    private static List<GPoint> GetTrans(int length, boolean withDeviation){

        Random a = new Random();
        a.nextDouble();
        List<GPoint> points = new ArrayList<>();
        for(int i=0; i<length; i++) {
            if(withDeviation){
                points.add(new GPoint(i - 2, 2000 * i + 20000 + a.nextDouble()));
                points.add(new GPoint(2 * i - 1, 1000 * i + 20000 + a.nextDouble()));

            }else {
                points.add(new GPoint(i - 2, 2000 * i + 20000));
                points.add(new GPoint(2 * i - 1, 1000 * i + 20000));
            }
        }
        return points;
    }

    private static List<GPoint> GetOriginalFromMap(){
        List<GPoint> points = new ArrayList<>();
        points.add(new GPoint(136, 52));
        points.add(new GPoint(120, 575));
        points.add(new GPoint(338,579));
        points.add(new GPoint(338,619));
        points.add(new GPoint(558,616));
        points.add(new GPoint(557,483));
        points.add(new GPoint(689,483));
        points.add(new GPoint(685,277));
        points.add(new GPoint(424,286));
        points.add(new GPoint(430,481));
        points.add(new GPoint(426,383));
        points.add(new GPoint(686,381));
        return points;
    }

    private static List<GPoint> GetTargetFromGPS(){
        List<GPoint> points = new ArrayList<>();
        points.add(new GPoint(116.417618,39.968295));
        points.add(new GPoint(116.417541,39.966488));
        points.add(new GPoint(116.418525,39.966474));
        points.add(new GPoint(116.418525,39.966339));
        points.add(new GPoint(116.419513,39.966346));
        points.add(new GPoint(116.419509,39.966806));
        points.add(new GPoint(116.420101,39.966806));
        points.add(new GPoint(116.420083,39.967518));
        points.add(new GPoint(116.418916,39.967483));
        points.add(new GPoint(116.418938,39.966813));
        points.add(new GPoint(116.41892,39.967152));
        points.add(new GPoint(116.420092,39.967162));

        return points;
    }

    @Test
    public void testTemp(){

        MapCorrectionPoints mappings = new MapCorrectionPoints();
        mappings.setMapId(1);


        List<PointPair> mpoints = new ArrayList<>();
        List<GPoint> originals = GetOriginal(30);
        List<GPoint> trans = GetTrans(30, true);
        for(int i=0; i<30 ;i++){
            mpoints.add(new PointPair(originals.get(i), trans.get(i)));
        }
        mappings.setPoints(mpoints);

        Matrix theta = convert.GetFactorsByGradintDescent(GetOriginal(30), GetTrans(30, true));

        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(mappings);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        json +=" ";

    }

}