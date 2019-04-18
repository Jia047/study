package com.jia.aop;

public class Teacher implements Person {
    @Override
    public void say() {
        System.out.println("I am a teacher.");
    }

    @Override
    public void run() {
        System.out.println("I am run faster than the student.");
    }
}
