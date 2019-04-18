package com.jia.mySpring.aop;


public abstract class AbstractAopProxy implements AopProxy {

    protected AdvisorSupport advisorSupport;

    public AbstractAopProxy(AdvisorSupport advisorSupport){
        this.advisorSupport = advisorSupport;
    }

}
