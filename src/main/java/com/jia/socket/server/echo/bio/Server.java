package com.jia.socket.server.echo.bio;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 单线程 echo 服务器
 */
public class Server {

    public static void main(String[] args) throws IOException {
        // 开启端口2019作为echo服务器的监听端口
        ServerSocket server = new ServerSocket(2019);
        Socket client = null;
        boolean flag = true;
        PrintStream out;
        BufferedReader bf;

        try {
            // 阻塞在此处，等待服务器连接
            client = server.accept();
            // 输出流，通过 out 将字节流输出到客户端
            out = new PrintStream(client.getOutputStream());
            // 输入流，通过 bf 获得客户端传送过来的字节流
            bf = new BufferedReader(new InputStreamReader(client.getInputStream()));
            while(flag) {
                String message = bf.readLine();
                if (StringUtils.isEmpty(message)) {
                    flag = false;
                } else {
                    if ("bye".equals(message)) {
                        out.println("echo: bye");
                        flag = false;
                    } else {
                        out.println("echo: " + message);
                    }
                }
            }
            out.close();
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
