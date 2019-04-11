package com.jia.util;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试添加元素
 */
public class Add {

    @Test
    public void listAdd(){
        int N = 10000000;
        ArrayList<Integer> list1 = new ArrayList<Integer>();

        long start1 = System.currentTimeMillis();
        for(int i = 0; i < N; i++){
            list1.add(i);
        }
        long end1 = System.currentTimeMillis();
        System.out.println("没有使用 ensureCapacity : " + (end1 - start1));

        ArrayList<Integer> list2 = new ArrayList<Integer>();

        long star2 = System.currentTimeMillis();
        // 添加大量元素之前，先扩容
        list2.ensureCapacity(N);
        for(int i = 0; i < N; i++){
            list2.add(i);
        }
        long end2 = System.currentTimeMillis();
        System.out.println("使用 ensureCapacity : " + (end2 - star2));
    }
}
