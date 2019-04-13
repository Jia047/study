package com.jia.thread;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;

/**
 * 自己实现一个ThreadLocal
 */
public class MyThreadLocal<T> {
    @Test
    public void myThreadLocalTest() throws ExecutionException, InterruptedException {
    // 测试所用的代码
    MyThreadLocal<Long> local = new MyThreadLocal<>();

    class MyTask implements Callable{
        @Override
        public Object call() throws Exception {
            long time = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + ": " + time);
            local.set(time);
            return Thread.currentThread().getName();
        }
    }
        ExecutorService service =  Executors.newFixedThreadPool(5);
        List<Future> futureList = new ArrayList<>(5);

        for(int i = 0; i < 5; i++){
            futureList.add(service.submit(new MyTask()));
        }
        for(Future f : futureList){
            while(!f.isDone());
            String threadName = (String)f.get();
            System.out.println("show " + threadName + ": " + local.get(threadName));
        }

    }

    private Map<String, T> map = Collections.synchronizedMap(new HashMap<>());

    public void set(T value){
        map.put(Thread.currentThread().getName(), value);
    }

    public T get(String threadName){
        return map.get(threadName);
    }

    public void remove(String threadName){
        map.remove(threadName);
    }

}

