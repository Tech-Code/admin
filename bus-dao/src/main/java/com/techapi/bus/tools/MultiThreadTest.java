package com.techapi.bus.tools;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.*;

/**
 * Created by xuefei on 8/18/14.
 */
public class MultiThreadTest {

        // =============== 测试1（start） ===============
        // 实现Runnable接口
        private class MyRunnable implements Runnable {

            private int index = 0;

            public MyRunnable(int i) {
                this.index = i;
            }

            @Override
            public void run() {
                try {
                    System.out.println("thread [" + this.index + "] start....");
                    Thread.sleep((int) (Math.random() * 3000));
                    System.out.println("thread [" + this.index + "] end.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // 通过ExecutorService调用多线程
        public void doExecutorService_excute() {
            ExecutorService executorService = Executors.newFixedThreadPool(3);
            for (int i = 1; i <= 10; i++) {
                executorService.execute(new MyRunnable(i));
            }
            executorService.shutdown();
        }

        // =============== 测试1（end） ===============

        // =============== 测试2（start） ===============
        // 实现
        private class MyCallable implements Callable<String> {
            private int index = 0;

            public MyCallable(int i) {
                this.index = i;
            }

            @Override
            public String call() throws Exception {
                System.out.println("thread [" + this.index + "] start....");
                Thread.sleep((int) (Math.random() * 3000));
                return "thread [" + this.index + "] end.";
            }
        }

        // 通过ExecutorService调用多线程
        public void doExecutorService_submit() throws InterruptedException, ExecutionException {
            ExecutorService executorService = Executors.newFixedThreadPool(3);
            Deque<Future<?>> futures = new ArrayDeque<Future<?>>();
            for (int i = 1; i <= 10; i++) {
                futures.add(executorService.submit(new MyCallable(i)));
            }
            System.out.println("show return str:");
            for (Future<?> future : futures) {
                System.out.println(future.get().toString());
            }
            executorService.shutdown();
        }
        // =============== 测试2（end） ===============

        //执行测试
        public static void main(String[] args) throws InterruptedException, ExecutionException {
            //new ExecutorTest().doExecutorService_excute();
            new MultiThreadTest().doExecutorService_submit();
        }
}

