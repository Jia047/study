package com.jia.mianshi.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程之间按照顺序调用，实现 A->B->C三个线程启动，要求如下：
 * AA 打印 1 次，BB 打印 2次，CC打印 3次
 * 来3轮
 */
class ShareResource {
    /**
     * flag = 1，线程AA启动
     * flag = 2，线程BB启动
     * flag = 3，线程CC启动
     */
    private int flag = 1;
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print1(){
        lock.lock();
        try{
            // 1. 判断
            while(flag != 1){
                c1.await();
            }
            // 2. 干活
            print(Thread.currentThread().getName(), 1);
            // 3. 通知
            flag = 2;
            c2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print2(){
        lock.lock();
        try{
            // 1. 判断
            while(flag != 2){
                c2.await();
            }
            // 2. 干活
            print(Thread.currentThread().getName(), 2);
            // 3. 通知
            flag = 3;
            c3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void print3(){
        lock.lock();
        try{
            // 1. 判断
            while(flag != 3){
                c3.await();
            }
            // 2. 干活
            print(Thread.currentThread().getName(), 3);
            // 3. 通知
            flag = 1;
            c1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    private void print(String name, int count){
        for(int i = 0; i < count; i++){
            System.out.println(name + "\t " + i);
        }
    }
}

public class SyncAndReentrantLockDemo {
    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();

        new Thread(()->{
            for (int i = 0; i < 3; i++) {
                shareResource.print1();
            }
        }, "AA").start();
        new Thread(()->{
            for (int i = 0; i < 3; i++) {
                shareResource.print2();
            }
        }, "BB").start();
        new Thread(()->{
            for (int i = 0; i < 3; i++) {
                shareResource.print3();
            }
        }, "CC").start();
    }
}
