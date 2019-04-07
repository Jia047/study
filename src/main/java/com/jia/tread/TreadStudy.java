package com.jia.tread;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * 创建线程的四种方法,但不管是哪种方法，最后都是要使用 Thread 类来执行start()方法
 * 1、采用普通的Thread类实例来创建线程
 * 2、采用实现Runnable接口的方式来来决定线程的动作
 * 3、采用实现Callable接口是实例化 FutureTask 决定线程的动作
 * 4、采用线程池的方式来创建线程，Runnable接口来决定线程的动作
 */
public class TreadStudy {

    @Test
    public void first(){
        Thread thread = new Thread();
        thread.setName("first");
        thread.start();
        System.out.println(thread.getName());
    }

    @Test
    public void second(){
        Thread thread = new Thread(new Runnable() {
            public void run() {
                System.out.println("我是在Runnable里面实现的run()");
            }
        });
        thread.setName("second");
        thread.start();
        // 在这里实现打印了线程的名字，再执行run，
        // 我猜想是因为run所在的线程还没创建好，或者刚创建好，
        // 所以相对于一开始就在执行的主线程，代码执行的顺序后稍后
        System.out.println(thread.getName());
    }

    /**
     * 使用 Callable 接口实现线程
     * 1、创建一个实现 Callable 接口的实现类 O
     * 2、将 Obj 作为FutureTask类的构造函数的参数，设入FutureTask的实例 F
     * 3、将 F 作为 Thread 类的参数，设入 Thread类的实例 T
     * 4、T.start()
     * 5、到第四步时，我们是看不到 Callable 接口中 call() 方法的执行的，我们需要调用 FutureTask中的 get() 方法
     */
    @Test
    public void third() throws ExecutionException, InterruptedException {
        // 这里的Callable接口需要指向的是一个实现该接口的实现类，这里采用匿名内部实现类
        Callable<Object> callable = new Callable<Object>() {
            public Object call() throws Exception {
                System.out.println("我是在Callable中实现的call()");
                return "call";
            }
        };
        FutureTask<Object> futureTask = new FutureTask<Object>(callable);
        Thread thread = new Thread(futureTask);
        thread.setName("third");
        thread.start();
        // 调用了 get() 方法后，才有call方法的执行，
        // 一开始没get()，我看不见任何输出，
        // 貌似是只有 FutureTask 的实例对象被调用了，才会出发call()方法, 而get(）的返回值是call的返回值
        System.out.println(futureTask.get());
        System.out.println(thread.getName());
    }

    /**
     * 使用线程池来实现线程
     */
    @Test
    public void fourth() throws InterruptedException {
        int nThreads = 5;
        // 采用默认的线程池生成，并制定线程池大小为5
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);

        for(int i = 0; i < nThreads + 5; i++){
            Thread.sleep(1000);
            executorService.execute(new RunnableImpl());
        }
        // 销毁线程池
        executorService.shutdown();
    }

    class RunnableImpl implements Runnable{

        public void run() {
            System.out.println("我是在RunnableImpl里的run() : " + Thread.currentThread().getName());
        }
    }
}
