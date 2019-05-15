package com.lacesar.thirdApi.jindee.core.backupdata.forkjoin;

import com.lacesar.thirdApi.jindee.core.backupdata.forkjoin.strategy.ForkJoinContext;

import java.util.List;
import java.util.concurrent.RecursiveTask;

public class CollectionForkJoinTask extends RecursiveTask<Integer> {


    private static int THRESHOLD = 400;

    private final static ThreadLocal<List> THREAD_LOCAL_LIST = new ThreadLocal<>();

    private final static ThreadLocal<ForkJoinContext> THREAD_LOCAL_CONTEXT = new ThreadLocal<>();


    public CollectionForkJoinTask(List list, ForkJoinContext forkJoinContext) {

        THREAD_LOCAL_LIST.set(list);

        THREAD_LOCAL_CONTEXT.set(forkJoinContext);

    }

    @Override
    protected Integer compute() {

        if (THREAD_LOCAL_LIST.get().size() <= THRESHOLD) {
            THREAD_LOCAL_CONTEXT.get().execute();
        }

        int size = THREAD_LOCAL_LIST.get().size();
        List list = THREAD_LOCAL_LIST.get();
        ForkJoinContext forkJoinContext = THREAD_LOCAL_CONTEXT.get();

        CollectionForkJoinTask task1 = new CollectionForkJoinTask(list.subList(0, size / 2), forkJoinContext);
        CollectionForkJoinTask task2 = new CollectionForkJoinTask(list.subList(size / 2, size), forkJoinContext);

        //两个任务并发执行起来
        invokeAll(task1, task2);

        return task1.join() + task2.join();
    }


}
