package com.jia.mySpring.aop;

public class TargetSource {

    private Object target;

    private Class<?> aClass;

    private Class<?> [] interfaces;


    public TargetSource(Object bean, Class<?> aClass, Class<?>... interfaces){
        this.target = bean;
        this.aClass = aClass;
        this.interfaces = interfaces;
    }

    public Object getTarget() {
        return target;
    }

    public Class<?> getaClass() {
        return aClass;
    }

    public Class<?>[] getInterfaces() {
        return interfaces;
    }
}
