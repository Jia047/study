package com.jia.mySpring.aop;

import org.aopalliance.aop.Advice;

public interface Advisor {

    /**
     * 获取通知,即在连接点将要发送的动作
     */
    Advice getAdvice();
}
