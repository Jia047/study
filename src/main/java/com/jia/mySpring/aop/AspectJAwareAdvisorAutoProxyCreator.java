package com.jia.mySpring.aop;

import com.jia.mySpring.ioc.factory.BeanFactory;
import com.jia.mySpring.ioc.factory.BeanFactoryAware;
import com.jia.mySpring.ioc.factory.BeanPostProcessor;
import com.jia.mySpring.ioc.xml.XmlBeanFactory;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.List;

public class AspectJAwareAdvisorAutoProxyCreator implements BeanPostProcessor, BeanFactoryAware {
    private XmlBeanFactory xmlBeanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.xmlBeanFactory = (XmlBeanFactory)beanFactory;
    }

    @Override
    public Object postProcessorBeforeBeanInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }

    @Override
    public Object postProcessorAfterBeanInitialization(Object bean, String beanName) throws Exception {

        if(bean instanceof AspectJExpressionPointCutAdvisor){
            return bean;
        }
        if(bean instanceof MethodInterceptor){
            return bean;
        }

        // 1.  从 BeanFactory 查找 AspectJExpressionPointcutAdvisor 类型的对象
        List<AspectJExpressionPointCutAdvisor> advisors =
                xmlBeanFactory.getBeansForType(AspectJExpressionPointCutAdvisor.class);
        for (AspectJExpressionPointCutAdvisor advisor : advisors) {

            // 2. 使用 Pointcut 对象匹配当前 bean 对象
            if (advisor.getPointCut().getClassFilter().matchers(bean.getClass())) {
                ProxyFactory advisedSupport = new ProxyFactory();
                advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
                advisedSupport.setMethodMatcher(advisor.getPointCut().getMethodMatcher());

                TargetSource targetSource = new TargetSource(bean, bean.getClass(), bean.getClass().getInterfaces());
                advisedSupport.setTargetSource(targetSource);

                // 3. 生成代理对象，并返回
                return advisedSupport.getProxy();
            }
        }

        return bean;
    }
}
