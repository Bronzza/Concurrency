package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) {
        Integer integer = 10;
        ExecutorService executor = Executors.newFixedThreadPool(2);
        ScheduledExecutorService scheduledService = Executors.newScheduledThreadPool(5);
        List<Future<Integer>> list = new ArrayList<>();
        List<ScheduledFuture<Integer>> listSchedeled = new ArrayList<>();
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);

        Callable<Integer> callable = new ConcurrentCounter(integer, 50);
        for (int i = 0; i < 10; i++) {
            Future<Integer> future = executor.submit(callable);
            ScheduledFuture<Integer> scheduledFuture = scheduledService.schedule(callable, 3, TimeUnit.SECONDS);
            list.add(future);
            listSchedeled.add(scheduledFuture);
        }
        for (Future<Integer> fut : list) {
            try {
                Thread.sleep(1000);
                System.out.println("Our integer after increment: " + fut.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            executor.shutdown();
        }
        for (ScheduledFuture schedFuture : listSchedeled) {
            try {
                Thread.sleep(500);
                System.out.println("Our integer after increment in scheduled services: " + schedFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        scheduledService.shutdown();
        int fibonachi = 15;
        System.out.println("Number Fibonachi of " + fibonachi + " is: " +
                forkJoinPool.invoke(new FibonachiRecur(15)));
    }
}
