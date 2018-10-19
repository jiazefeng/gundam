package com.maxrocky.gundam.commons.utility;

/**
 * Created by yuer5 on 16/6/9.
 */
public class ArrayStringConverter {

    public static String convertToString(double[][] array, int row, int col) {
        String str = "";
        String tempStr = null;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                tempStr = String.valueOf(array[i][j]);
                str = str + tempStr + ",";
            }
        }
        return str;
    }

    public static double[][] convertToArray(String str, int row, int col){
        double[][] arrayConvert = new double[row][col];
        int count = 0;
        String[] strArray = str.split(",");
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++) {
                arrayConvert[i][j] = Double.parseDouble(strArray[count]);
                ++count;
            }
        }
        return arrayConvert;
    }
}
