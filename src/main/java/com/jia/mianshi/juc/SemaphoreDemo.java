package com.jia.mianshi.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 使用 Semaphore 来模拟共享资源的占用与释放
 * @see Semaphore
 */
public class SemaphoreDemo {

    /**
     * 模仿打印机的占用与释放
     */
    public static void main(String[] args) {
        // 3 台打印机
        Semaphore semaphore = new Semaphore(3);

        // 6 台电脑连接到打印机
        for (int i = 0; i < 6; i++) {
            new Thread(()-> {
                try {
                    String threadName = Thread.currentThread().getName();
                    semaphore.acquire();
                    System.out.println(threadName + "\t 电脑连接到打印机");
                    // 每台电脑使用打印机 3 秒
                    try{
                        TimeUnit.SECONDS.sleep(3);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    System.out.println(threadName + "\t 电脑释放打印机");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }
}
