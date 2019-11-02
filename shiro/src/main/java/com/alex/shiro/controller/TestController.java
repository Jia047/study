package com.alex.shiro.controller;

import com.alex.shiro.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Controller
public class TestController {

    /**
     * 跳转到login页面
     */
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 访问成功的页面
     */
    @RequestMapping("/unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        if(subject != null) {
            subject.logout();
        }
        return "login";
    }

    @RequestMapping("/admin")
    @ResponseBody
    public String admin() {
        return "admin success";
    }

    @RequestMapping("/edit")
    @ResponseBody
    public String edit() {
        return "edit success";
    }

    /**
     * 登陆处理的接口
     */
    @RequestMapping("/loginUser")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            HttpSession session) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        // shiro 认证逻辑
        try {
            subject.login(token);
            User user = (User) subject.getPrincipal();
            // 将主体存入 session 中
            session.setAttribute("user", user);
            return "index";
        } catch (Exception e){
            return "login";
        }
    }
}

