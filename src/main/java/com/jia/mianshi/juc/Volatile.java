package com.jia.mianshi.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyData{
    volatile int num = 0;
    //
    AtomicInteger atomicNum = new AtomicInteger(0);

    public void addTo60(){ this.num = 60; }
    public void addPlusPlus(){
        this.num++;
    }
    public void atomicAddPlusPlus(){
        // 相当于 ++i
//        this.atomicNum.incrementAndGet();
        // 相当于 i++
         this.atomicNum.getAndIncrement();
    }
}
/**
 * 验证 volatile 关键字的可见性
 *  1.1 假如 num 字段没有添加 volatile 关键字，没有可见性
 *  1.2 添加 volatile 修饰，可以解决可见性问题
 *
 *  验证 volatile 关键字不保证原子性
 *  2.1 原子性指的是几个动作，要么全部完成，要不全部不完成
 *  2.2 volatile 不保证原子性
 *
 *  如何解决volatile不保证原子性的问题
 *  1. 使用 synchronized  ===> 开销太大
 *  2. 使用原子类
 */
public class Volatile {

    public static void main(String[] args) {
//        testVisible();
        testAtomic();
    }

    /**
     * 验证原子性
     */
    private static void testAtomic() {
        MyData myData = new MyData();
        // 对于myData的 num，使用20个线程，每个线程执行 addPlusPlus 1000 次
        // 如果 volatile能保证原子性，那么num应该是 20 * 1000 = 20000
        for (int i = 0; i < 20; i++) {
            new Thread(()-> {
                for (int j = 0; j < 1000; j++) {
                   myData.addPlusPlus();
                   myData.atomicAddPlusPlus();

                }
            }, String.valueOf(i)).start();
        }
        // 需要等待上面20个线程执行完成之后再用main线程取得结果
        while(Thread.activeCount() > 2){
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName()
                + "\t finally num value: " + myData.num + "\t atomicNum value: " + myData.atomicNum);
    }

    /**
     * 验证可见性
     */
    private static void testVisible(){
        MyData myData = new MyData();
        // 线程 A 在睡眠3秒后将myData的 num 改为60
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            try{
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addTo60();
            System.out.println(Thread.currentThread().getName() + "\t updated number:" + myData.num);
        }, "A").start();

        while(myData.num == 0){
            // 当main线程检测到num不为0时，再退出循环
            // 如果main线程检测不到num的改变，那么程序就会一直卡在这里
            // 说明其他线程对 num 的改变对其他线程不可见

            // 当使用 volatile 修饰该变量时，程序的运行结果不一样
        }

        System.out.println(Thread.currentThread().getName() + "\t mission is over，main get num: " + myData.num);
    }
}
