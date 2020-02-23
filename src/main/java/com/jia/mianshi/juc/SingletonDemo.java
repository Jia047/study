package com.jia.mianshi.juc;

/**
 * 单例模式的实现
 */
public class SingletonDemo {
    // 使用 volatile 关键字来禁止指令重排序
    private static volatile SingletonDemo instance = null;

    private SingletonDemo(){
        System.out.println(Thread.currentThread().getName() + "\t 实例化 SingletonDemo");
    }

    // DCL (double check lock 双端检锁机制）单例模式
    // 企业用得比较多的一种方式
    public static SingletonDemo getInstance(){
        if(instance == null){
            synchronized (SingletonDemo.class) {
                if(instance == null){
                    instance = new SingletonDemo();
                }
            }
        }

        return instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(SingletonDemo::getInstance, String.valueOf(i)).start();
        }
    }
}
