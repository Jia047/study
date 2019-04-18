package com.jia.mySpring.aop;

/**
 * 过滤要拦截的方法或者类
 */
public interface PointCutAdvisor extends Advisor{

    /**
     * 提供切点规则
     */
    PointCut getPointCut();
}
