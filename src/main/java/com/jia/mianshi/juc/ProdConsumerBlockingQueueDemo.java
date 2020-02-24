package com.jia.mianshi.juc;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyResource {
    private volatile boolean flag = true;
    private AtomicInteger atomicInteger = new AtomicInteger();
    private BlockingQueue<String> blockingQueue = null;

    public MyResource(BlockingQueue<String> blockingQueue){
        this.blockingQueue = blockingQueue;
        // 记录传入的阻塞队列类型
        System.out.println(blockingQueue.getClass().getName());
    }

    public void prod() throws InterruptedException {
        String data = null;
        while(flag){
            data = atomicInteger.incrementAndGet() + "";
            blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            System.out.println(Thread.currentThread().getName() + "\t 生产 " + data);

            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName() + "\t 生产叫停");
    }

    public void consume() throws InterruptedException {
        String result = null;
        while(flag) {
            result = blockingQueue.poll(2L, TimeUnit.SECONDS);
            // 如果队列已经空了，取不到数据了，那么就返回
            if (StringUtils.isEmpty(result)) {
                flag = false;
                System.out.println(Thread.currentThread().getName() + "\t 没有可消费的元素，返回");
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t 消费 " + result);
        }
    }

    public void stop(){
        this.flag = false;
    }
}

/**
 * 使用消息队列解决生产者和消费者问题
 * volatile, CAS, BlockingQueue, 线程交互，原子引用
 */
public class ProdConsumerBlockingQueueDemo {
    public static void main(String[] args) {
        MyResource myResource = new MyResource(new ArrayBlockingQueue<String>(10));

        new Thread(()->{
            try {
                myResource.prod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Prod").start();

        new Thread(()->{
            try {
                myResource.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Consumer").start();

        try{ TimeUnit.SECONDS.sleep(5);}catch (InterruptedException e) {e.printStackTrace();}
        System.out.println("5 秒时间到，停止生产");
        myResource.stop();
    }
}
