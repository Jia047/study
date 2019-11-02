package com.alex.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 *   shiro 配置
 *      系统启动后,先实例化ShiroCofiguration,
 *      然后实例化 ShiroFilterFactoryBean,ShiroFilterFactoryBean 需要 SecurityManager
 *      然后实例化 SecurityManager, SecurityManager 需要 AuthReaml
 *      然后实例化 AuthReaml, AuthReaml 需要一个 CredentialMatcher
 *      然后实例化 CredentialMatcher
 *      这些组件都是我们自定义的,由此将shiro的身份验证和权限管理按照我们的意思构建出来
 *   shiro 与 spring 之间的关联
 */
@Configuration
public class ShiroConfiguration  {

    /**
     * 上下文的Shiro Filter
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter (@Qualifier("securityManager")SecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);

        // 定义登录的 url
        bean.setLoginUrl("/login");
        // 成功登录后跳转的 url
        bean.setSuccessUrl("/index");
        // 无权限访问时跳转的url
        bean.setUnauthorizedUrl("/unauthorized");

        //////////
        // 重点 //
        /////////
        // 某些请求该如何拦截
        // key 代表请求, value 代表拦截器
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/index", "authc");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/loginUser", "anon");
        filterChainDefinitionMap.put("/admin", "roles[admin]");
        filterChainDefinitionMap.put("/edit", "perms[edit]");
        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/**", "user");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return bean;
    }

    @Bean("securityManager")
    public SecurityManager securityManager (@Qualifier("authRealm") AuthReaml authReaml) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(authReaml);
        return manager;
    }

    /**
     * 为 authrealm 配置密码校验器
     */
    @Bean("authRealm")
    public AuthReaml authReaml (@Qualifier("credentialMatcher") CredentialMatcher matcher) {
        AuthReaml authReaml = new AuthReaml();
        authReaml.setCredentialsMatcher(matcher);

        return authReaml;
    }

    /**
     * 校验规则,密码校验器
     */
    @Bean("credentialMatcher")
    public CredentialMatcher credentialMatcher() {
        return new CredentialMatcher();
    }

    // shiro 与 spring 之间的关联

    /**
     *  spring 在对 shiro 进行处理的时候使用的 SecurityManager 就是我们自定义的 SecurityManager
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor (@Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);

        return advisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator () {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);

        return creator;
    }
}
