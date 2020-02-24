package com.jia.mianshi.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class  MyCache{
    private volatile Map<String, Object> cache = new HashMap<>();
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void put(String key, Object value){
        rwLock.writeLock().lock();
        try{
            System.out.println(Thread.currentThread().getName() + "\t 正在写入：" + key);
            // 暂停一下线程，模拟写入的时间
            try{
                TimeUnit.MICROSECONDS.sleep(300);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            cache.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t 写入完成");

        }finally {
            rwLock.writeLock().unlock();
        }
    }

    public void get(String key){
        rwLock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName() + "\t 正在获取");
            // 暂停一下线程，模拟读取的时间
            try{
                TimeUnit.MICROSECONDS.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            Object result = cache.get(key);
            System.out.println(Thread.currentThread().getName() + "\t 获取成功：" + result);
        }finally {
            rwLock.readLock().unlock();
        }
    }
}

/**
 * 手写读写锁
 *
 * 模拟缓存的读写
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache cache = new MyCache();

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(()->{
                cache.put(finalI +"", finalI +"");
            }, String.valueOf(i)).start();
        }

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(()->{
                cache.get(finalI + "");
            }, String.valueOf(i)).start();
        }
    }
}
