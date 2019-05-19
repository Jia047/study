package com.jia.interfaces;

import org.apache.commons.lang3.ClassUtils;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;

public class InterfaceTest {

    @Test
    public void interfaceTest(){
        AaImplV1 v1 = new AaImplV1();
        AaImplV2 v2 = new AaImplV2();

        System.out.println("V1: " + Arrays.toString(v1.getClass().getInterfaces()));
        System.out.println("V2: " + Arrays.toString(v2.getClass().getInterfaces()));

        // 这种机制不影响 instanceof
        System.out.println("V1 instanceof A: " + (v1 instanceof A));

        // 使用 Apache Common Lang 包的类来获取该类实现的所有接口
        System.out.println("V1: " + ClassUtils.getAllInterfaces(v1.getClass()));
        System.out.println("V2: " + ClassUtils.getAllInterfaces(v2.getClass()));
    }

}
