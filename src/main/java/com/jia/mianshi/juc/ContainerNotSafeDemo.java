package com.jia.mianshi.juc;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 集合不安全问题
 */
public class ContainerNotSafeDemo {

    public static void main(String[] args) {
//        listNotSafe();
//        setNotSafe();
        mapNotSafe();

    }
    private static void listNotSafe() {
//        List<String> list = new ArrayList<>();
//        List<String> list = Collections.synchronizedList(new ArrayList<>());
        List<String> list = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            }).start();
        }
        /**
         * 1. 故障现象
         * java.util.ConcurrentModificationException
         *
         * 2. 导致原因
         *
         * 3. 解决方案
         *    3.1 new Vector(); -- 不可用
         *    3.2 Collections.synchronizedList(new ArrayList());
         *    3.3 new CopyOnWriteArrayList();
         *
         * 4. 优化建议
         */
    }

    private static void mapNotSafe() {
//        Map<String, String> map = new HashMap<>();
//        Map<String, String> map = Collections.synchronizedMap(new HashMap<>());
        Map<String, String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                map.put(Thread.currentThread().getName(),
                        UUID.randomUUID().toString().substring(0,8));
                System.out.println(map);
            }).start();
        }
    }

    private static void setNotSafe() {
//        Set<String> set = new HashSet<>();
//        Set<String> set = Collections.synchronizedSet(new HashSet<>());
        Set<String> set = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                set.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(set);
            }).start();
        }
    }

}
