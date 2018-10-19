package com.maxrocky.gundam.commons.algorithm;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation;
import org.ujmp.core.doublematrix.DoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.DefaultDenseDoubleMatrix2D;
import weka.core.matrix.LinearRegression;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * Created by yuer5 on 16/5/21.
 */
public class CoordinateConvert {

    //线性方程解坐标转换系数
    public Matrix GetFactorsByEquations(List<GPoint> originGPoints, List<GPoint> transGPoints){

//        Xt = A0 + A1*Xs + A2*Ys
//        Yt = B0 + B1*Xs + B2*Ys
        if(originGPoints.size() != transGPoints.size() || originGPoints.size() != 3) {
            System.out.println("Error");
            return null;
        }

        Matrix aAxis = Matrix.Factory.ones(3, 3);
        Matrix xt = Matrix.Factory.ones(3, 1);
        Matrix yt = Matrix.Factory.ones(3, 1);
        for (int i=0; i<3; i++){
            aAxis.setAsDouble(originGPoints.get(i).x, i, 1);
            aAxis.setAsDouble(originGPoints.get(i).y, i, 2);
            xt.setAsDouble(transGPoints.get(i).x, i, 0);
            yt.setAsDouble(transGPoints.get(i).y, i, 0);
        }

//        AX = B -> X = (A^-1) * B
//        XA = B -> X = B * (A^-1)

        Matrix result1 = aAxis.solve(xt).transpose();
        Matrix result2 = aAxis.solve(yt).transpose();

        return result1.appendVertically(Calculation.Ret.NEW, result2);

    }

    //梯度下降解坐标转换系数
    public Matrix GetFactorsByGradintDescent(List<GPoint> originGPoints, List<GPoint> targetGPoints){

        List<Double[]> X1 = new ArrayList<>();
        List<Double[]> X2 = new ArrayList<>();
        for (GPoint p : originGPoints){
            X1.add(new Double[]{p.x});
            X2.add(new Double[]{p.y});
        }
        List y1 = targetGPoints
                .stream()
                .map(a -> a.x)
                .collect(Collectors.toList());
        List y2 = targetGPoints
                .stream()
                .map(a -> a.y)
                .collect(Collectors.toList());

        CostFunctionArgs arg1 = GetCostFunctionArgs(X1, y1);
        CostFunctionRets ret1 = CosFunction(arg1);
        CostFunctionArgs arg2 = GetCostFunctionArgs(X2, y2);
        CostFunctionRets ret2 = CosFunction(arg2);

//        double lambda1 = 0.000001;
//        double lambda2 = 0.000001;

        double lambda1 = 0.0000007;
        double lambda2 = 0.0000007;

        for(int i=0; i<1000000; i++) {
            ret1 = CosFunction(arg1);
            arg1.theta = arg1.theta.minus(ret1.grad.times(lambda1));
            if(i % 1000 == 0)
                System.out.println(arg1.theta.getAsDouble(0,0) + " " + arg1.theta.getAsDouble(1,0));

            ret2 = CosFunction(arg2);
            arg2.theta = arg2.theta.minus(ret2.grad.times(lambda2));
        }

        return arg1.theta.appendVertically(Calculation.Ret.NEW, arg2.theta);
    }

    public double[][] GetFactorsByLinearRegression(List<GPoint> originGPoints, List<GPoint> targetGPoints){

        //线性回归，计算x'=a*x+b的拟合因子
        //下面为对输入x进行设定
        //考虑偏移量，将式子改写为x'=a*x0+b*x1，其中的x1一直为1，所以，有以下赋值
        weka.core.matrix.Matrix Xx = new weka.core.matrix.Matrix(originGPoints.size(), 2, 1);
        weka.core.matrix.Matrix Xy = new weka.core.matrix.Matrix(originGPoints.size(), 2, 1);
        for(int i=0; i<originGPoints.size(); i++) {
            Xx.set(i, 0, originGPoints.get(i).x);
            Xy.set(i, 0, originGPoints.get(i).y);
        }

        //下面为对输出x'进行设定
        weka.core.matrix.Matrix yx = new weka.core.matrix.Matrix(targetGPoints.size(), 1);
        weka.core.matrix.Matrix yy = new weka.core.matrix.Matrix(targetGPoints.size(), 1);
        for(int i=0; i<targetGPoints.size(); i++) {
            yx.set(i, 0, targetGPoints.get(i).x);
            yy.set(i, 0, targetGPoints.get(i).y);
        }

        LinearRegression lrx = new LinearRegression(Xx,yx,0);
        LinearRegression lry = new LinearRegression(Xy,yy,0);

        //得到a为10，b为30
        double[] coefficients=lrx.getCoefficients();
        double[] coefficientsy=lry.getCoefficients();

        double[][] factors = new double[2][2];
        factors[0] = coefficients;
        factors[1] = coefficientsy;

        return factors;
    }

    //坐标转换By梯度下降系数
    public List<GPoint> GetTransPointsByGD(List<GPoint> originGPoints, double[][] factors) {

        List<GPoint> trans = new ArrayList<>();
        Matrix mfactors = Matrix.Factory.ones(1, 2);

        Matrix Xx = Matrix.Factory.ones(originGPoints.size(), 2);
        Matrix Xy = Matrix.Factory.ones(originGPoints.size(), 2);
        for(int i=0; i<originGPoints.size(); i++) {
            Xx.setAsDouble(originGPoints.get(i).x, i, 0);
            Xy.setAsDouble(originGPoints.get(i).y, i, 0);
        }

        mfactors.setAsDouble(factors[0][0], 0, 0);
        mfactors.setAsDouble(factors[0][1], 0, 1);
        Matrix yx = Xx.mtimes(mfactors.transpose());

        mfactors.setAsDouble(factors[1][0], 0, 0);
        mfactors.setAsDouble(factors[1][1], 0, 1);
        Matrix yy = Xy.mtimes(mfactors.transpose());

        for(int i=0; i<originGPoints.size(); i++) {
            trans.add(new GPoint(yx.getAsDouble(i,0), yy.getAsDouble(i,0)));
        }
        return trans;
    }

    //坐标转换By梯度下降系数
    public GPoint GetTransPointsByGD(GPoint originGPoints, double[][] factors) {
        GPoint retPoint = new GPoint();
        retPoint.x = factors[0][1] + originGPoints.x * factors[0][0];
        retPoint.y = factors[1][1] + originGPoints.y * factors[1][0];
        return retPoint;
    }


    public List<GPoint> GetTransPointsByEquations(List<GPoint> originGPoints, List<double[]> factors) {

        List<GPoint> trans = new ArrayList<>();
        Matrix mfactors = Matrix.Factory.ones(2, 3);

        Matrix X = Matrix.Factory.ones(originGPoints.size(), 3);

        for(int i=0; i<originGPoints.size(); i++) {
            X.setAsDouble(originGPoints.get(i).x, i, 1);
            X.setAsDouble(originGPoints.get(i).y, i, 2);
        }

        mfactors.setAsDouble(factors.get(0)[0], 0, 0);
        mfactors.setAsDouble(factors.get(0)[1], 0, 1);
        mfactors.setAsDouble(factors.get(0)[2], 0, 2);
        mfactors.setAsDouble(factors.get(1)[0], 1, 0);
        mfactors.setAsDouble(factors.get(1)[1], 1, 1);
        mfactors.setAsDouble(factors.get(1)[2], 1, 2);

        Matrix y = X.mtimes(mfactors.transpose());

        for(int i=0; i<originGPoints.size(); i++) {
            trans.add(new GPoint(y.getAsDouble(i,0), y.getAsDouble(i,1)));
        }
        return trans;
    }

    private CostFunctionArgs GetCostFunctionArgs(List<Double[]> XValues, List<Double> yValues){

        CostFunctionArgs arg = new CostFunctionArgs();

        DoubleMatrix2D X = new DefaultDenseDoubleMatrix2D(XValues.size(), XValues.get(0).length + 1);

        Matrix y = Matrix.Factory.ones(yValues.size(), 1);

        for(int i=0; i<X.getRowCount(); i++){
            X.setAsDouble(1, i, 0);
            for (int j=1; j<X.getColumnCount(); j++)
            {
                X.setAsDouble(XValues.get(i)[j-1], i, j);
            }
            y.setAsDouble(yValues.get(i), i, 0);
        }

        arg.X = X;
        arg.y = y;
        arg.theta = Matrix.Factory.zeros(X.getColumnCount(), 1);

        return arg;

    }

    private CostFunctionRets CosFunction(CostFunctionArgs args){

        Matrix X = args.X;
        Matrix y = args.y;
        Matrix theta = args.theta;

        CostFunctionRets rets = new CostFunctionRets();

        long m = y.getSize()[1];

        Matrix htheta_y = X.mtimes(theta).minus(y);

//        Matrix grad = X.mtimes(theta).minus(y).transpose().mtimes(X).transpose();
        rets.grad = (X.transpose()).mtimes(htheta_y).divide(m);
        rets.Jtheta = htheta_y.power(Calculation.Ret.NEW, 2).getValueSum()/(2*m);

        return rets;

//        m = length(y);
//        J = -(y'*log(sigmoid(X*theta)) + (1-y)'*log(1-sigmoid(X*theta)))/2m;
//        grad = X'*(X*theta-y)/m;

    }

}
