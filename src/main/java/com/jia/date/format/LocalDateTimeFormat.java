package com.jia.date.format;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by jia on 2019/10/29 21:43
 *
 * @see LocalDateTime 的格式化
 **/
public class LocalDateTimeFormat {

    @Test
    public void test(){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.now();
        String localTime = df.format(time);
        System.out.println("LocalDateTime转成String类型的时间："+localTime);

        LocalDateTime ldt = LocalDateTime.parse("2017-09-28 17:07:05",df);
        System.out.println("String类型的时间转成LocalDateTime："+ldt);
    }
}
 