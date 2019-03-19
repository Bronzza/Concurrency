package com.company;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConcurrentCounter implements Callable<Integer> {

    private int counter;
    private Integer usersNumber;
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private final Lock writeLock = reentrantReadWriteLock.writeLock();


    public ConcurrentCounter(Integer initialValue, int counter) {
        usersNumber = initialValue;
        this.counter = counter;
    }

    @Override
    public Integer call() throws Exception {
        Thread.sleep(100);
        writeLock.lock();
        for (int i = 0; i < counter; i++) {
            usersNumber++;
        }
        writeLock.unlock();
        return usersNumber;
    }
}
