package com.jia.aop;

public class Student implements Person {
    @Override
    public void say() {
        System.out.println("I am a student.");
    }

    @Override
    public void run() {
        System.out.println("I am running.");
    }
}
