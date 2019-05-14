package com.jia.clone;

public class ShallowPerson implements Cloneable{
    public String name;
    public int age;

    public ShallowPerson(String name, int age){
        this.name = name;
        this.age = age;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
