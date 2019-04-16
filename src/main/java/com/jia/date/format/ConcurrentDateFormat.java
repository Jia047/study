package com.jia.date.format;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 通过 synchronized 做一个线程安全的DataFormat
 */
public class ConcurrentDateFormat {
    /**
     * 依旧只创建一个 SimpleFormat 对象
     */
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 采用加锁的方式，防止同步问题
    public static String format(Date date){
        synchronized (sdf){
            return sdf.format(date);
        }
    }

    public static Date parse(String date) throws ParseException {
        synchronized (sdf){
            return sdf.parse(date);
        }
    }

}
