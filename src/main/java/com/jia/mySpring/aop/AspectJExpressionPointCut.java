package com.jia.mySpring.aop;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;
import org.aspectj.weaver.tools.ShadowMatch;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * 方法或者类的拦截的规则表达式
 */

public class AspectJExpressionPointCut implements PointCut, ClassFilter, MethodMatcher{

    /**
     * 拦截规则表达式字符串
     */
    private String expression;

    /**
     * 切点分析器
     */
    private PointcutParser pointcutParser;

    /**
     * 切点表达式, 提供了匹配方法
     */
    private PointcutExpression pointcutExpression;

    /**
     *
     */
    private static final Set<PointcutPrimitive> DEFAULT_SUPPORTED_PRIMITIVES = new HashSet<>(16);

    static {
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.ARGS);
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.REFERENCE);
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.THIS);
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.TARGET);
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.WITHIN);
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ANNOTATION);
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_WITHIN);
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ARGS);
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_TARGET);
    }

    public AspectJExpressionPointCut(){
        this(this.DEFAULT_SUPPORTED_PRIMITIVES);
    }

    public AspectJExpressionPointCut(Set<PointcutPrimitive> defaultSupportedPrimitives) {
        pointcutParser =
                PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingContextClassloaderForResolution(defaultSupportedPrimitives);
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public Boolean matchers(Class beanClass) throws Exception {
        checkReadyToMatcher();

        return pointcutExpression.couldMatchJoinPointsInType(beanClass);
    }

    @Override
    public Boolean matchers(Method method, Class targetClass) throws Exception {
        checkReadyToMatcher();
        ShadowMatch shadowMatch = pointcutExpression.matchesMethodExecution(method);

        if(shadowMatch.alwaysMatches()){
            return true;
        }else if(shadowMatch.neverMatches()){
            return false;
        }
        return false;
    }

    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }

    private void checkReadyToMatcher(){
        if(getExpression() == null){
            throw new IllegalArgumentException("There is no exprssion!");
        }
        if(pointcutExpression == null){
            pointcutExpression = pointcutParser.parsePointcutExpression(expression);
        }
    }

}
