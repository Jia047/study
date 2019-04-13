package com.jia.stream;

import org.junit.Test;

import java.io.*;

/**
 * 统计一个单词在一个文件中出现的次数
 */
public class WordCount {

    @Test
    public void wordCountTest(){
        String word = "int";
        String source = "/home/jis/IdeaProjects/study/src/main/java/com/jia/stream/source.txt";

        System.out.print("统计" + word + "在" + source + "中的次数：");
        System.out.println(wordCountInFile(source, word));
    }

    private int wordCountInFile(String source, String word){
        int count = 0;
        try(FileReader fr = new FileReader(source)){
            try(BufferedReader bf = new BufferedReader(fr)){
                String line ;
                while((line = bf.readLine()) != null){
                    int index ;
                    while(line.length() >= word.length() && (index = line.indexOf(word)) >= 0){
                        count++;
                        line = line.substring(index + word.length());
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return count;
    }
}
