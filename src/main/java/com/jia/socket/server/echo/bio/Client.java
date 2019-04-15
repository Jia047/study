package com.jia.socket.server.echo.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Client {

    public static void main(String[] args) throws IOException {
        // 本机请求与 host：port 相连接
        String host = "192.168.1.198";
        Integer port = 2019;
        Socket client = new Socket(host, port);
        // 10秒超时，避免盲目等待
        client.setSoTimeout(10 * 1000);

        // 从客户端终端输入信息
        BufferedReader terminal = new BufferedReader(new InputStreamReader(System.in));
        // 向服务器传送数据
        PrintStream out = new PrintStream(client.getOutputStream());
        // 从服务器获得信息
        BufferedReader bf = new BufferedReader(new InputStreamReader(client.getInputStream()));

        boolean flag = true;
        while(flag){
            // 从终端获取信息
            System.out.print("client： ");
            String input = terminal.readLine();
            // 将信息传出
            out.println(input);
            // 发出信息后，等待服务器将回应
            try{
                // 在超时允许的时间内等待，客户端将会阻塞在这里，直到服务器回应或者超时
                String message = bf.readLine();
                System.out.println(message);
            }catch (SocketTimeoutException e){
                System.out.println("Time out, No response");
                flag = false;
            }
            if("bye".equals(input)){
                flag = false;
            }
        }
        // 关闭资源流
        out.close();
        bf.close();
        client.close();
    }
}
