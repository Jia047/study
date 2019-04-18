package com.jia.aop;

import java.lang.reflect.Proxy;

public class JdkProxy{

    public Object getProxy(Person person){
        return Proxy.newProxyInstance(person.getClass().getClassLoader(), person.getClass().getInterfaces(), new PersonHandler(person));
    }
}
