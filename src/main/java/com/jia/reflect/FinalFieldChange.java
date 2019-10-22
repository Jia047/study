package com.jia.reflect;

import org.junit.Test;

import java.lang.reflect.Field;

/**
 * Created by jia on 2019/10/22 19:45
 *
 * 通过反射改变 final 字段的值
 **/
public class FinalFieldChange {

    class Man{
        private final StringBuilder name = new StringBuilder("default");
        private final String hobbit = "make_love";

        public void print(){
            System.out.println("name -> " + name);
            System.out.println("hobbit -> " + hobbit);
            System.out.println("===================");
        }
    }

    /**
     * 这段用来展示java编译器对final字段的优化
     *
     *   class Man {
     *       private final StringBuilder name = new StringBuilder("default");
     *       private final String hobbit = "make_love";
     *
     *       Man() {
     *       }
     *
     *       public void print() {
     *       System.out.println(this.name);
     *       System.out.println("make_love");
     *       }
     *   }
     *
     *    @Test
     *    public void testString() throws NoSuchFieldException, IllegalAccessException {
     *        FinalFieldChange.Man m = new FinalFieldChange.Man();
     *        m.print();
     *        Field hb = m.getClass().getDeclaredField("hobbit");
     *        hb.setAccessible(true);
     *        hb.set(m, "ml");
     *        m.print();
     *        System.out.println("m.hobbit -> " + "make_love");
     *        System.out.println("hb.get(m) -> " + hb.get(m));
     *    }
     *
     *  编译器对String类型的字段进行优化，直接将字符串写死再调用处，所以不能改变 print() 的输出
     *  但是可以通过 Field 类的 get(obj) 方法，来获取某个对象的某个字段值。从结果来看，hobbit 字段
     *  是被修改了的
     */
    @Test
    public void testString() throws NoSuchFieldException, IllegalAccessException {
        Man m = new Man();
        m.print();

        Field hb = m.getClass().getDeclaredField("hobbit");

        hb.setAccessible(true);
        hb.set(m, "ml");

        m.print();
        System.out.println("m.hobbit -> " + m.hobbit);
        System.out.println("hb.get(m) -> " + hb.get(m));

        /*
            输出结果
            name -> default
            hobbit -> make_love
                    ===================
            name -> default
            hobbit -> make_love
                    ===================
            m.hobbit -> make_love
            hb.get(m) -> ml
        */
    }

    /**
     *     @Test
     *     public void testStringBuilder() throws NoSuchFieldException, IllegalAccessException {
     *         FinalFieldChange.Man m = new FinalFieldChange.Man();
     *         m.print();
     *         Field nf = m.getClass().getDeclaredField("name");
     *         nf.setAccessible(true);
     *         nf.set(m, new StringBuilder("jack"));
     *         System.out.println(m.name);
     *     }
     *
     * 这个是用来验证final修改的字段不能更改其指向的地址引用，但是可以更改地址引用的内容
     */
    @Test
    public void testStringBuilder() throws NoSuchFieldException, IllegalAccessException {
        Man m = new Man();
        m.print();

        Field nf = m.getClass().getDeclaredField("name");

        nf.setAccessible(true);
        nf.set(m, new StringBuilder("jack"));

        System.out.println(m.name);

        /*
            输出结果
            name -> default
            hobbit -> make_love
            ===================
            jack
         */
    }
}
 