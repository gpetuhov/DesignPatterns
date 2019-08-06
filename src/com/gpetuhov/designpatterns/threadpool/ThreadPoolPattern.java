package com.gpetuhov.designpatterns.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Thread Pool

// Task
class MyThread implements Runnable {

    private String name;

    public MyThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Start. Name = " + name);

        // Imitate calculations
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " End.");
    }
}


public class ThreadPoolPattern {

    public static void main(String[] args) {
        // Create simple thread pool with 5 threads
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        // Put 10 tasks into thread pool.
        // Thread pool has only 5 threads, so 5 tasks will start executing immediately,
        // and 5 will wait for available threads.
        // As soon as any of the threads becomes available, it will be used by next waiting task.
        for (int i = 0; i < 10; i++) {
            Runnable myThread = new MyThread("" + (i+1));
            executorService.execute(myThread);
        }

        // Previously submitted tasks are executed, no new tasks are accepted
        executorService.shutdown();

        // Wait for all previously submitted tasks to finish
        while (!executorService.isTerminated()) {
        }

        System.out.println("Finished all threads");
    }
}
