package com.jia.date;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * - 如何取得年月日、小时分钟秒？
 * - 如何取得从1970年1月1日0时0分0秒到现在的毫秒数？
 * - 如何取得某月的最后一天？
 * - 如何格式化日期？
 */
public class GetTime {

    @Test
    public void getTime(){
        Calendar calendar = Calendar.getInstance();

        // 如何取得年月日、小时分钟秒？
        System.out.println("如何取得年月日、小时分钟秒？");
        System.out.println("Year : " + calendar.get(Calendar.YEAR));
        System.out.println("Month : " + (calendar.get(Calendar.MONTH) + 1));
        System.out.println("Day : " + calendar.get(Calendar.DATE));
        System.out.println("Hour : " + calendar.get(Calendar.HOUR));
        System.out.println("Hour of day : " + calendar.get(Calendar.HOUR_OF_DAY));
        System.out.println("Minute : " + calendar.get(Calendar.MINUTE));
        System.out.println("Second : " + calendar.get(Calendar.SECOND));

        // 如何取得从1970年1月1日0时0分0秒到现在的毫秒数？
        System.out.println("如何取得从1970年1月1日0时0分0秒到现在的毫秒数？");
        System.out.println(Calendar.getInstance().getTimeInMillis());
        System.out.println(System.currentTimeMillis());
        System.out.println(Clock.systemDefaultZone().millis());

        // 如何取得某月的最后一天？
        System.out.println("如何取得某月的最后一天？");
        System.out.println(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        // 格式化日期
        System.out.println("格式化日期");
        SimpleDateFormat format = new SimpleDateFormat("YYYY/MM/dd");
        Date date= new Date();
        System.out.println(format.format(date));


    }
}
