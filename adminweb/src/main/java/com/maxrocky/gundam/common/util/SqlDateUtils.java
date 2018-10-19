package com.maxrocky.gundam.common.util;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by HaiXiang Yu on 2015/9/6.
 * java.Sql.Date Time 获取时间
 */
public class SqlDateUtils {

    /**
     * 获取sql日期 yyyy-m-d
     * @return
     */
    public static Date getDate(){
        Date date=new Date(System.currentTimeMillis());
        return date;
    }

    /**
     * String To Date
     * @param str
     * @return
     */
    public static Date getDate(String str){

        try {
            return Date.valueOf(str);
        }
        catch (Exception ex){
            return null;
        }
    }

    /**
     * 获取sql时间 hh-mm-ss
     * @return
     */
    public static Time getTime(){
        Time time = new Time(System.currentTimeMillis());
        return time;
    }
}
