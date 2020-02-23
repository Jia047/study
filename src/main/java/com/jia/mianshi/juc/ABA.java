package com.jia.mianshi.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 使用带版本号的原子引用来解决ABA问题
 */
public class ABA {
    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {

        System.out.println("模仿 ABA 问题的产生");
        // t1 将变量 从 100 改到 101 ，然后再从 101 改到 100，即 ABA
        new Thread(() -> {
            atomicReference.compareAndSet(100, 101);
            atomicReference.compareAndSet(101, 100);
        }, "T1").start();

        // t2 暂停 3s，让 t1 完成 ABA 操作，然后再去更新变量的值
        new Thread(()->{
            try{ TimeUnit.SECONDS.sleep(1); }
            catch (InterruptedException e){ e.printStackTrace(); }

            System.out.println(Thread.currentThread().getName() + "\t " +
                    atomicReference.compareAndSet(100, 101) + "\t" +
                    atomicReference.get());
        }, "T2").start();


        try{ TimeUnit.SECONDS.sleep(3); }
        catch (InterruptedException e){ e.printStackTrace(); }
        System.out.println("模仿ABA问题的解决");

        // 同样让 t3 完成一次 ABA 操作
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t 第1次版本号：" + stamp);
            // 暂停 1s ，让 t4 能够获取版本号
            try{ TimeUnit.SECONDS.sleep(1); }
            catch (InterruptedException e){ e.printStackTrace(); }

            atomicStampedReference.compareAndSet(100, 101, stamp, stamp + 1);
            stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t 第2次版本号：" + stamp);

            atomicStampedReference.compareAndSet(101, 100, stamp, stamp + 1);
            stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t 第3次版本号：" + stamp);
        }, "T3").start();

        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t 第1次版本号：" + stamp);
            // 先暂停 3s 让 T3 完成操作
            try{ TimeUnit.SECONDS.sleep(3); }
            catch (InterruptedException e){ e.printStackTrace(); }

            System.out.println(Thread.currentThread().getName() + "\t 当前版本号：" + atomicStampedReference.getStamp());
            boolean b = atomicStampedReference.compareAndSet(100, 2020,
                    stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName() + "\t 更换结果：" + b +
                    "\t 变量实际最新值：" + atomicStampedReference.getReference());
        }, "T4").start();
    }
}
