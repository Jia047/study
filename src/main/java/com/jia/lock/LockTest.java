package com.jia.lock;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author jia
 * @date 2019/12/11 7:07
 **/
public class LockTest {
    static L l = new L();

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();
        System.out.println(ClassLayout.parseInstance(l).toPrintable());
        System.out.println("=====================");

        System.out.println(ClassLayout.parseInstance(new int[13]).toPrintable());
    }
}
