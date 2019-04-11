package com.jia.staticBlock;

import org.junit.Test;

/**
 * 代码块的执行顺序， 静态代码块-非静态代码块-（构造方法）- 方法代码块
 */
public class StaticBlock {

    {
        System.out.println("Hello world 2");
    }

    static {
        System.out.println("Hello world");
    }

    @Test
    public void staticTest(){
        System.out.println("Hello world 3");
    }
}
