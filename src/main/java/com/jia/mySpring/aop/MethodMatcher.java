package com.jia.mySpring.aop;


import java.lang.reflect.Method;

public interface MethodMatcher {

    /**
     * 判断一个方法是否是切入点声明的方法
     */
    Boolean matchers(Method method, Class targetClass) throws Exception;

}
