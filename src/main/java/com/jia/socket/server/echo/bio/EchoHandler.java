package com.jia.socket.server.echo.bio;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * 处理客户端连接的线程
 */
public class EchoHandler extends Thread{
    private Socket client;
    private PrintStream out;
    private BufferedReader bf;

    public EchoHandler(Socket client){
        this.client = client;
        try{
            out = new PrintStream(client.getOutputStream());
            bf = new BufferedReader(new InputStreamReader(client.getInputStream()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void run(){
        try{
            while(true) {
                String message = bf.readLine();
                if (StringUtils.isEmpty(message)) {
                    break;
                } else {
                    if ("bye".equals(message)) {
                        out.println("echo: bye");
                        break;
                    } else {
                        out.println("echo: " + message);
                    }
                }
            }
            out.close();
            bf.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
