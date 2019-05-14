package com.jia.clone;

public class DeepPerson implements Cloneable {
    public Phone phone;
    public int age;

    public DeepPerson(Phone phone, int age){
        this.phone = phone;
        this.age = age;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        DeepPerson person = new DeepPerson((Phone)phone.clone(), age);
        return person;
    }
}

class Phone implements Cloneable{
    public String name;

    public Phone(String name){
        this.name = name;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
