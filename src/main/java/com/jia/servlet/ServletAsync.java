package com.jia.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * servlet对请求的异步处理 3.0 及以上版本
 */
@WebServlet(name = "asyncServlet", urlPatterns = "/async", asyncSupported = true)
public class ServletAsync extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 开启tomcat的异步处理
        req.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);

        // 启动异步处理上下文
        final AsyncContext ctx = req.startAsync();
        // 开启一个新的线程
        ctx.start(() -> {
            // TODO
            // 异步处理代码

            // 处理完成
            ctx.complete();
        });
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
