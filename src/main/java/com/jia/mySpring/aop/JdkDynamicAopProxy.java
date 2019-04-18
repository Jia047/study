package com.jia.mySpring.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkDynamicAopProxy extends AbstractAopProxy implements InvocationHandler {

    public JdkDynamicAopProxy(AdvisorSupport advisorSupport){
        super(advisorSupport);
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(getClass().getClassLoader(), advisorSupport.getTargetSource().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MethodMatcher methodMatcher = advisorSupport.getMethodMatcher();

        // 判断该方法是否符合匹配规则
        if(methodMatcher != null && methodMatcher.matchers(method, advisorSupport.getTargetSource().getClass())){
            advisorSupport.getMethodInterceptor().invoke(
                    new ReflectiveMethodInvocation(advisorSupport.getTargetSource().getTarget(), method, args));
        }

        return method.invoke(advisorSupport.getTargetSource().getTarget(), args);
    }
}
