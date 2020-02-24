package com.jia.mianshi.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
// 资源类
class ShareData {
    private int num = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() throws Exception{
        lock.lock();
        try{
            while(num != 0){
                condition.await();
            }
            num++;
            System.out.println(Thread.currentThread().getName() + "\t " + num);
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }
    public void decrement() throws Exception{
        lock.lock();
        try{
            while(num == 0){
                condition.await();
            }
            num--;
            System.out.println(Thread.currentThread().getName() + "\t " + num);
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }
}

/**
 * 传统版本的生产者消费者版本
 */
public class ProdConsumerTraditionDemo {

    public static void main(String[] args) {
        ShareData shareData = new ShareData();

        new Thread(()-> {
            for (int i = 0; i < 5; i++) {
                try{
                    shareData.increment();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, "AA").start();

        new Thread(()-> {
            for (int i = 0; i < 5; i++) {
                try{
                    shareData.decrement();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, "BB").start();
    }
}
