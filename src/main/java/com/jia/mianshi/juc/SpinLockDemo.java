package com.jia.mianshi.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁 demo
 */
public class SpinLockDemo {
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    private void myLock(){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "\t come in");
        while(!atomicReference.compareAndSet(null, thread));
    }

    private void myUnlock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(thread.getName() + "\t invoke myUnlock()");
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        // 线程AA拿到锁之后，暂停 3s
        new Thread(()->{
            spinLockDemo.myLock();

            try{
                TimeUnit.SECONDS.sleep(3);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            spinLockDemo.myUnlock();
        }, "AA").start();
        // 线程 BB 尝试拿到锁，如果拿不到，就自旋直到拿到锁
        new Thread(()->{
            spinLockDemo.myLock();
            try{
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            spinLockDemo.myUnlock();
        }, "BB").start();

    }
}
