package com.jia.thread;

import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * 使用 callable 接口创建线程，并利用返回值
 */

class MyTask implements Callable<Integer>{

    private int bonds;

    public MyTask(int bonds){
        this.bonds = bonds;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + ", rand = " + bonds);
        int sum = 0;
        for(int i = 0; i < bonds; i++){
            sum += i;
        }
        System.out.println(Thread.currentThread().getName() + ", sum = " + sum);
        return sum;
    }
}

public class CallableTest {

    @Test
    public void callableTest() throws ExecutionException, InterruptedException {
        int n = 5;
        ArrayList<Future<Integer>> list = new ArrayList<>(n);
        ExecutorService service = Executors.newFixedThreadPool(n);

        for(int i = 0; i < 10; i++){
            int rand = (int)(Math.random() * 100);
            list.add(service.submit(new MyTask(rand)));
        }

        int sum = 0;
        for(Future<Integer> f : list){
            // 线程运行结束返回 true，无论是正常结束还是异常结束
            while (!f.isDone());
            sum += f.get();
        }

        System.out.println(sum);
    }
}
