package com.jia.initial;

/**
 * 验证类的初始化过程
 */
public class A {

    static {
        System.out.println("1");
    }

    public A(){
        System.out.println("A");
    }
}

