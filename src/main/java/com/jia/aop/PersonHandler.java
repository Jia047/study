package com.jia.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class PersonHandler implements InvocationHandler {
    Person person;

    public PersonHandler(Person person){
        this.person = person;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("方法开始。。。");
        Object result = method.invoke(person, args);
        System.out.println("方法结束");
        return result;
    }
}
