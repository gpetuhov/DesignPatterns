package com.gpetuhov.designpatterns.threadpool2;

import java.util.concurrent.*;

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

    @Override
    public String toString() {
        return this.name;
    }
}

// Handles tasks, that can't fit into queue
class MyRejectedExecutionHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println(r.toString() + " is rejected");
    }
}

// Monitors state of the thread pool executor
class MyMonitor implements Runnable {

    // Keeps reference to the executor
    private ThreadPoolExecutor executor;

    // This monitoring task will run until run is true
    private boolean run = true;

    public MyMonitor(ThreadPoolExecutor executor) {
        this.executor = executor;
    }

    public void shutdown() {
        this.run = false;
    }

    // Print thread pool monitor information every 1 second
    @Override
    public void run() {
        while (run) {
            System.out.println(
                    String.format("[monitor] [%d/%d] Active: %d, Completed: %d, Task: %d, isShutdown: %s, isTerminated: %s",
                            this.executor.getPoolSize(),
                            this.executor.getCorePoolSize(),
                            this.executor.getActiveCount(),
                            this.executor.getCompletedTaskCount(),
                            this.executor.getTaskCount(),
                            this.executor.isShutdown(),
                            this.executor.isTerminated())
            );

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ThreadPoolPattern2 {

    public static void main(String[] args) {
        MyRejectedExecutionHandler rejectionHandler = new MyRejectedExecutionHandler();

        // Get default thread factory to use in thread pool
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        // Create thread pool.
        // Initial number of threads = 2
        // Maximum number of threads = 4
        // Extra threads will wait for new tasks for 10 seconds before terminating
        // Queue will hold 2 tasks before they are executed. Extra incoming tasks will be rejected
        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2), threadFactory, rejectionHandler);

        // Start the monitoring thread
        MyMonitor myMonitor = new MyMonitor(executor);
        Thread monitorThread = new Thread(myMonitor);
        monitorThread.start();

        // Create 10 tasks and submit them into thread pool.
        // Maximum number of threads is 4 and size of queue is 2,
        // so 4 tasks will start executing immediately,
        // 2 tasks will wait in the queue,
        // and other 4 tasks will be rejected.
        for (int i = 0; i < 10; i++) {
            executor.execute(new MyThread("" + (i+1)));
        }

        // Take time to see all of the monitoring output info
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Previously submitted tasks are executed, no new tasks are accepted
        executor.shutdown();

        // Take time to see all of the monitoring output info
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Shutdown the monitor
        myMonitor.shutdown();
    }
}
