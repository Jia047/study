package com.jia.socket.server.echo.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 基于多线程的 echo 服务器
 */
public class MultiThreadServer {

    public static void main(String[] args) throws IOException {
        // 开启端口2019作为echo服务器的监听端口
        ServerSocket server = new ServerSocket(2019);
        Socket client = null;

        while(true){
            System.out.println("Waiting for connection from client");
            client = server.accept();
            System.out.println("Client connected.");
            new EchoHandler(client).start();
        }
    }

}

