package com.jia.socket.server.echo.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 基于线程池的 echo 服务器
 */
public class ThreadPoolServer {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(2019);
        Socket client;
        // 创建一个线程池，大小为 2
        ExecutorService service = Executors.newFixedThreadPool(2);

        while(true){
            System.out.println("Waiting for connection from client");
            client = server.accept();
            System.out.println("Client connected");
            //
            service.execute(new EchoHandler(client));
        }
    }
}
