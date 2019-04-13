package com.jia.stream;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 分别使用传统的字节流和NIO进行文件复制
 * try-catch使用TWR风格
 */
public class CopyFile {

    @Test
    public void fileCopyTest(){
        System.out.println("开始复制...");
        fileCopy("/home/jis/IdeaProjects/study/src/main/java/com/jia/stream/source.txt"
                , "/home/jis/IdeaProjects/study/src/main/java/com/jia/stream/target.txt");
        System.out.println("复制结束...");
    }

    @Test
    public void fileCopyNIOTest(){
        System.out.println("开始复制...");
        fileCopyNIO("/home/jis/IdeaProjects/study/src/main/java/com/jia/stream/sourceNIO.txt"
                , "/home/jis/IdeaProjects/study/src/main/java/com/jia/stream/targetNIO.txt");
        System.out.println("复制结束...");
    }

    private void fileCopy(String source, String target){
        try(InputStream in = new FileInputStream(source)){
            try(OutputStream out = new FileOutputStream(target)){
                byte[] buffer = new byte[2048];
                int byteToRead;
                while((byteToRead = in.read()) != -1){
                    out.write(buffer, 0, byteToRead);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fileCopyNIO(String source, String target){
        try(FileInputStream in = new FileInputStream(source)){
            try(FileOutputStream out = new FileOutputStream(target)){
                FileChannel inChannel = in.getChannel();
                FileChannel outChannel = out.getChannel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
                while(inChannel.read(byteBuffer) != -1){
                    byteBuffer.flip();
                    outChannel.write(byteBuffer);
                    byteBuffer.clear();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
