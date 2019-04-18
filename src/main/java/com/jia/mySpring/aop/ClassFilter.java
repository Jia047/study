package com.jia.mySpring.aop;

public interface ClassFilter {

    /**
     * 判断一个类是否是切点声明的类
     * 判断一个类是否是要织入通知的对象
     */
    Boolean matchers(Class beanClass) throws Exception;
}
