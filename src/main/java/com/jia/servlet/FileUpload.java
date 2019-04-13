package com.jia.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

/**
 * 使用servlet 3.0 提供的文件上传功能
 */
@WebServlet(name = "fileUploadServlet", urlPatterns = {"/fileUpload"})
@MultipartConfig
public class FileUpload extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 假设有 <input type = "file" name = "photo"/>
        // 可以用 req.getPart("photo") 获取名为photo的文件
        // 也可以用 req.getParts() 获取多文件上传，然后循环处理
        Part part = req.getPart("photo");
        if(part != null && part.getSubmittedFileName().length() > 0){
            // 获取项目中 /upload 文件夹的真是路径
            String savePath = req.getServletContext().getRealPath("/upload");
            // 使用 part 对象的 getSubmittedFileName() 可以获得上传文件的文件名
            part.write(savePath + "/" + part.getSubmittedFileName());
            req.setAttribute("hint", "file upload success");
        }else{
            req.setAttribute("hint", "file upload failed");
        }
    }
}
