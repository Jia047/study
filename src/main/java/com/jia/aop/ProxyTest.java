package com.jia.aop;

import org.junit.Test;

public class ProxyTest {
    @Test
    public void proxyTest(){
        Person student = new Student();
        Person teacher = new Teacher();
        student.say();

        JdkProxy jdkProxy = new JdkProxy();
        Person proxy = (Person)jdkProxy.getProxy(student);
        proxy.say();
        proxy.run();

        Person teacherProxy = (Person)jdkProxy.getProxy(teacher);
        teacherProxy.run();
        teacherProxy.say();
    }
}
