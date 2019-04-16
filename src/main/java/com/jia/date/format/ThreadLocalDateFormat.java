package com.jia.date.format;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 通过 ThreadLocal 为每个线程做一份变量副本，实现线程安全
 */
public class ThreadLocalDateFormat {

    /**
     * ThreadLocal 提供一种 lombda 构造方式
     * 返回此线程局部变量的当前线程的“初始值”。线程第一次使用 get() 方法访问变量时将调用此方法，
     * 但如果线程之前调用了 set(T) 方法，则不会对该线程再调用 initialValue 方法。通常，此方法
     * 对每个线程最多调用一次，但如果在调用 get() 后又调用了 remove()，则可能再次调用此方法。
     */
    private static ThreadLocal<DateFormat> threadLocal
            = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    public static Date parse(String date) throws ParseException {
        System.out.println(date);
        return threadLocal.get().parse(date);
    }

    public static String format(Date date){
        return threadLocal.get().format(date);
    }
}
