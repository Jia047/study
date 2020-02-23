package com.jia.mianshi.juc;

public class T1 {

    volatile int num = 0;

    public void addPlusPlus(){
        this.num++;
    }
}
