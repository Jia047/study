package com.jia.mianshi.juc;

public class StringTest {

    public static void main(String[] args) {
        String str = "abc";
        changeStr(str);
        System.out.println(str);
    }

    private static void changeStr(String str){
        str = "xxx";
    }
}
