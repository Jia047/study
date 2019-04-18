package com.jia.mySpring.aop;

import org.aspectj.apache.bcel.util.ClassPath;

/**
 * 切点
 */
public interface PointCut {

    /**
     * 获取类过滤的规则
     * @return
     */
    ClassFilter getClassFilter();

    /**
     * 过去方法识别的规则
     * @return
     */
    MethodMatcher getMethodMatcher();
}
