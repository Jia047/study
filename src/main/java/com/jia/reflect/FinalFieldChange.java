package com.jia.reflect;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

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

    /**
     * class Dog {
     *     private static final Integer age = 10;
     *
     *     Dog() {
     *     }
     *
     *     public void print() {
     *         System.out.println("age -> " + age);
     *     }
     * }
     * 这一段展示 static final 也能通过反射改变属性值的。只不过程复杂一些
     * 需要先获取 Modifier 类的实例（它的作用是返回一个类或者其成员的访问修饰符的int 类型常量）
     * 然后更改字段的修饰符，改为非 final
     *
     */
    @Test
    public void testStaticFinal() throws NoSuchFieldException, IllegalAccessException {
        Dog d = new Dog();

        d.print();

        Field af = d.getClass().getDeclaredField("age");

        af.setAccessible(true);
        // 这一步会报错
        // java.lang.IllegalAccessException:
        // Can not set static final java.lang.Integer field com.jia.reflect.Dog.age
        // to java.lang.Integer
//        af.set(d, 20);

        // 这样不会报错
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(af, af.getModifiers() & ~Modifier.FINAL);
        af.set(d, 20);

        d.print();
        System.out.println("d.age -> " + af.get(d));

        modifiersField.setInt(af, af.getModifiers() & ~Modifier.FINAL);

        af.set(d, 50);

    }
}

class Dog{
    private static final Integer age = 10;

    public void print(){
        System.out.println("age -> " + age);
    }

}