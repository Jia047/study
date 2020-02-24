package com.jia.mianshi.juc;

import java.util.concurrent.CountDownLatch;

/**
 * 先开6个线程，然后主线程等六个线程全部完成后，再进行操作
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch count = new CountDownLatch(6);

        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + "\t 同学离开教室");
                count.countDown();
            }, String.valueOf(i)).start();
        }

        count.await();
        System.out.println(Thread.currentThread().getName() + "\t 班长最后关教室");
    }
}
