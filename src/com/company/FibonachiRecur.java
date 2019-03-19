package com.company;

import java.util.concurrent.RecursiveTask;

public class FibonachiRecur extends RecursiveTask<Integer> {
    final int n;

    public FibonachiRecur(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n <= 1)
            return n;
        FibonachiRecur f1 = new FibonachiRecur(n - 1);
        f1.fork();
        FibonachiRecur f2 = new FibonachiRecur(n - 2);
        return f2.compute() + f1.join();
    }
}
