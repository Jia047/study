package com.jia.mySpring.aop;

import com.jia.initial.A;
import org.aopalliance.aop.Advice;

public class AspectJExpressionPointCutAdvisor implements PointCutAdvisor {

    private AspectJExpressionPointCut aspectJExpressionPointCut = new AspectJExpressionPointCut();

    private Advice advice;

    public void setExpression(String expression){
        this.aspectJExpressionPointCut.setExpression(expression);
    }

    public void setAdvice(Advice advice){
        this.advice = advice;
    }

    @Override
    public PointCut getPointCut() {
        return aspectJExpressionPointCut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }
}
