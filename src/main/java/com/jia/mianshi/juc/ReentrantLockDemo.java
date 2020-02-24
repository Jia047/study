package com.jia.mianshi.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

class Phone {
    ReentrantLock lock = new ReentrantLock();

    public synchronized void sendMS() throws Exception{
        System.out.println(Thread.currentThread().getId() + "\t invoke sendMS()");
        sendEmail();
    }

    public synchronized void sendEmail() throws Exception{
        System.out.println(Thread.currentThread().getId() + "\t ###invoke sendEmail()");
    }

    public void get() throws Exception{
        lock.lock();
        // lock() 方法是可以多次调用的，但是要保证每个 lock() 方法都有 unlock() 方法对应
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getId() + "\t invoke get()");
            set();
        }finally {
            lock.unlock();
            lock.unlock();
        }
    }

    public void set() throws Exception{
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getId() + "\t ###invoke set()");
        }finally {
            lock.unlock();
        }
    }
}

public class ReentrantLockDemo {

    public static void main(String[] args) {
        Phone phone = new Phone();
        // 验证 synchronized 是可重入锁
        /**
         * 12	 invoke sendMS()
         * 12	 ###invoke sendEmail()
         * 13	 invoke sendMS()
         * 13	 ###invoke sendEmail()
         */
        new Thread(()->{
            try{
                phone.sendMS();
            }catch (Exception e){
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(()->{
            try{
                phone.sendMS();
            }catch (Exception e){
                e.printStackTrace();
            }
        }, "t2").start();

        // 验证 ReentrantLock 是可重入锁
        /**
         * 14	 invoke get()
         * 14	 ###invoke set()
         * 15	 invoke get()
         * 15	 ###invoke set()
         */
        try{
            TimeUnit.SECONDS.sleep(2);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        new Thread(()->{
            try {
                phone.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t3").start();

        new Thread(()->{
            try {
                phone.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t4").start();
    }

}
