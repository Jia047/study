package com.jia.clone;

import org.junit.Test;

public class CloneTest {

    @Test
    public void shallowTest() throws CloneNotSupportedException {
        ShallowPerson person = new ShallowPerson("jack", 15);
        ShallowPerson personClone = (ShallowPerson)person.clone();

        System.out.println(person);
        System.out.println(personClone);
        System.out.println("person.age == personClone.age: " + (person.age == personClone.age));
        System.out.println("person.name == personClone.name: " + (person.name == personClone.name));
    }

    @Test
    public void deepTest() throws CloneNotSupportedException {
        DeepPerson person = new DeepPerson(new Phone("vivo"), 15);
        DeepPerson personClone = (DeepPerson)person.clone();

        System.out.println(person);
        System.out.println(personClone);
        System.out.println("person.age == personClone.age: " + (person.age == personClone.age));
        System.out.println(person.phone);
        System.out.println(personClone.phone);
        System.out.println("person.phone == personClone.phone: " + (person.phone == personClone.phone));
    }
}
