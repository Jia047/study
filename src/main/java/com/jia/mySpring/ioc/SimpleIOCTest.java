package com.jia.mySpring.ioc;

import org.junit.Test;

public class SimpleIOCTest {

    @Test
    public void test() throws Exception {
//        String xmlPath = SimpleIOC.class.getClassLoader().getResource("beans.xml").getFile();
//        SimpleIOC ioc = new SimpleIOC(xmlPath);
        SimpleIOC ioc =
                new SimpleIOC("/home/jis/IdeaProjects/study/src/main/java/com/jia/mySpring/ioc/beans.xml");

        Wheel wheel = (Wheel)ioc.getBean("wheel");
        System.out.println(wheel);
        Car car = (Car)ioc.getBean("car");
        System.out.println(car);
    }
}
