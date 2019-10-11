package com.jia.ds;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jia on 2019/10/11 20:54
 **/
public class StackTest {

    @Test
    public void testInt(){
        Stack<Integer> stack = new Stack<>();

        for(int i = 0; i < 10; i++){
            stack.push(RandomUtils.nextInt(0, 31));
        }

        test(stack);
    }

    @Test
    public void testCollection(){
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 10; i++){
            list.add(RandomUtils.nextInt(0, 31));
        }

        Stack<Integer> stack = new Stack<>(list);

        test(stack);
    }

    private void print(Stack stack){
        System.out.println(stack.toString());
    }

    private void test(Stack stack){
        print(stack);

        System.out.println("pop: " + stack.pop());
        print(stack);

        System.out.println("peek: " + stack.peek());
        print(stack);

        System.out.println("size: " +stack.size());

        System.out.println("isEmpty: " + stack.isEmpty());

        while(!stack.isEmpty()){
            System.out.println(stack.pop());
        }

        System.out.println("isEmpty: " + stack.isEmpty());
    }
}
 