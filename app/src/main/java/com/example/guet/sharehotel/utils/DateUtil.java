package com.example.guet.sharehotel.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @auth ${user}
 * time 2018/5/25 16:23
 */
public class DateUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static String getTomorro() {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String getDayNextMonth() {
        Date date = new Date();//取时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.DATE + 1, 1);
        return sdf.format(date);
    }


}
