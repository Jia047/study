package com.jia;

import com.jia.initial.A;
import com.jia.initial.B;

public class Start {
    public static void main(String[] args) {
        // 先初始化静态代码块
        // new B 时，因为A作为父类还未被初始化，所以要先初始化，执行 A 的构造函数
        A ab = new B();
        // 同理
        ab = new B();
    }
}
