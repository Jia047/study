package com.eric.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    public SpringSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    /**
     * 基于内存的验证，用户信息存储
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 添加用户，存在于内存中
        // 如果密码那里不加 {noop} 会报错 java.lang.IllegalArgumentException: There is no MyPasswordEncoder mapped for the id "null"
        // 添加密码存储格式，纯文本，添加{noop}
        // 在Spring Security 5.0之前的默认PasswordEncoder是NoOpPasswordEncoder其所需的明文密码。
        // 在Spring Security 5，默认的是DelegatingPasswordEncoder，这需要密码存储格式。
//        auth.inMemoryAuthentication().withUser("admin").password("{noop}123").roles("ADMIN");
//        auth.inMemoryAuthentication().withUser("jack").password("{noop}123").roles("USER");
        // 指定 userService 来管理用户, 指定密码加密方式
        auth.userDetailsService(userService).passwordEncoder(new MyPasswordEncoder());
        // 数据库验证
        auth.jdbcAuthentication().usersByUsernameQuery("").authoritiesByUsernameQuery("").passwordEncoder(new MyPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 项目主路径不需要验证，项目的其他路径需要验证
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout().permitAll()
                .and()
                .formLogin();
        // 关闭 csrf
        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 忽略静态资源，不做检查
        web.ignoring().antMatchers("/js/**", "/css/**", "/img/**");
    }
}
