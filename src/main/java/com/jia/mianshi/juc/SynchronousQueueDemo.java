package com.jia.mianshi.juc;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * SynchronousQueue 队列中只存储一个元素，
 * 队列满时要等到元素被消费才能插入第二个
 *
 * @see java.util.concurrent.SynchronousQueue
 */
public class SynchronousQueueDemo {

    public static void main(String[] args) {
        BlockingQueue<String> synchronousQueue = new SynchronousQueue<>();
        // AAA 线程依次向队列中插入 a b c
        new Thread(() -> {
            String name = Thread.currentThread().getName();
            try {
                System.out.println(name + "\t 添加 a");
                synchronousQueue.put("a");

                System.out.println(name + "\t 添加 b");
                synchronousQueue.put("b");

                System.out.println(name + "\t 添加 c");
                synchronousQueue.put("c");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AAA").start();
        // BBB 线程每隔 5s 就从队列中消费一个元素
        new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
                    System.out.println(Thread.currentThread().getName() + "\t 消费" + synchronousQueue.take());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "BBB").start();
    }
}
