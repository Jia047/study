package com.jia.clone;

import lombok.Data;
import org.junit.Test;

import java.io.*;

/**
 * Created by jia on 2019/10/25 21:53
 **/
public class DeepClone {


    /**
     * 直接调用 super.clone() 属于浅拷贝
     */
    @Test
    public void testShallow() throws CloneNotSupportedException {
        Dog d1 = new Dog("A");
        Person p1 = new Person(d1, "jack");

        Person p2 = p1.clone();

        // p1 != p2，p2 是 p1 的浅拷贝对象
        // p1.name == p2.name ，由于是浅拷贝，对象的内部成员是共用的
        // 同理 p1.dog == p2.dog、 p1.dog.name == p2.dog.name
        compare(p1, p2);
    }

    /**
     * 深度拷贝
     */
    @Test
    public void testDeep() throws CloneNotSupportedException {
        Dog d1 = new Dog("B");
        DipPerson p1 = new DipPerson(d1, "pony");
        DipPerson p2 = p1.clone();

        // p1 != p2，p2 是 p1 的深度拷贝对象
        // p1.name == p2.name、p1.dog != p2.dog、p1.dog.name == p2.dog.name
        compare(p1, p2);
    }

    /**
     * 完全地、彻底的拷贝，效果就像两个 new 出来的对象
     */
    @Test
    public void testCompletely() throws Exception {
        // 涉及到的字段都要是可序列化的，如果 Dog，没实现 Serializable 接口，照样会报错
        Dog d1 = new Dog("C");
        DipPerson dp1 = new DipPerson(d1, "lee");
        DipPerson dp2 = CloneUtil.clone(dp1);
        // 各个字段均不相等
        compare(dp1, dp2);

        System.out.println("===============");

        Person p1 = new Person(d1, "lee");
        Person p2 = CloneUtil.clone(p1);
        // 各个字段均不相等
        compare(p1, p2);
    }

    /**
     * 两个对象的字段比较
     */
    private void compare(Person p1, Person p2){
        System.out.println("p1 = p2 :" + (p1 == p2));
        System.out.println("p1.dog = p2.dog : " + (p1.dog == p2.dog));
        // 这里不能用 String 的 equals 方法，否则即使字符串的引用不同，但是内容相同，也会返回 true
        System.out.println("p1.name = p2.name :" + (p1.name == p2.name));
        System.out.println("p1.dog.name = p2.dog.name :" + (p1.dog.name == p2.dog.name));
    }
}

/**
 * 对象克隆，被克隆的对象需要实现 Serializable 接口，或者其父类实现该接口也可以
 * 如果对象不可序列化，那么会抛出 NotSerializableException。
 * 另外的，如果对象有其他成员变量，这个成员变量也需要实现 Serializable 接口
 */
class CloneUtil{

    /**
     * 对象克隆
     */
    @SuppressWarnings("unchecked")
    public static <T> T clone(T obj) throws IOException, ClassNotFoundException {
        // 字节数组，将对象（即输入数据）的非静态方法、非静态字段写入此字节数组
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bout);
        // 将对象序列化后写入一个字节流中
        oos.writeObject(obj);

        // 将字节数组转换为输入流
        ByteArrayInputStream bis = new ByteArrayInputStream(bout.toByteArray());
        // 获取到字节流中的字节数组并反序列化为对象
        ObjectInputStream ois = new ObjectInputStream(bis);

        return (T)ois.readObject();

    }
}

/**
 * 重写 clone()， 浅度拷贝
 */
@Data
class Person implements Cloneable, Serializable{
    Dog dog;
    String name;

    public Person(Dog dog, String name){
        this.dog = dog;
        this.name = name;
    }

    public Person(){

    }

    @Override
    public Person clone() throws CloneNotSupportedException {
        return (Person) super.clone();
    }
}

/**
 * 重写 clone() ,深度克隆
 */
class DipPerson extends Person implements Cloneable {

    public DipPerson(Dog dog, String name){
        this.dog = dog;
        this.name = name;
    }

    public DipPerson(){}

    @Override
    public DipPerson clone() throws CloneNotSupportedException{
        DipPerson dipPerson = new DipPerson();
        dipPerson.dog = this.dog.clone();
        dipPerson.name = this.name;
        return  dipPerson;
    }
}


/**
 * 浅度拷贝
 */
@Data
class Dog implements Cloneable, Serializable{
    String name;

    public Dog(String name){
        this.name = name;
    }

    @Override
    public Dog clone() throws CloneNotSupportedException {
        return (Dog) super.clone();
    }
}
 