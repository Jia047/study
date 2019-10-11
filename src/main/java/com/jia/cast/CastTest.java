package com.jia.cast;

/**
 * Created by jia on 2019/9/14 22:28
 **/
public class CastTest {

    public static void main(String[] args) {
        Object o = String.valueOf(124);
        Integer i = cast(Integer.class, o);
        System.out.println(i);
        String s = cast(String.class, o);
        System.out.println(s);
    }

    private static <T> T cast(Class<T> clazz, Object o){
        if(clazz.isInstance(o)){
           return clazz.cast(o);
        }

        return null;
    }
}
 