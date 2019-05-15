package com.lacesar.thirdApi.jindee.core.backupdata.forkjoin;

import com.lacesar.thirdApi.jindee.core.backupdata.forkjoin.strategy.ForkJoinContext;
import com.lacesar.thirdApi.jindee.core.common.exceptions.ErpException;
import com.lacesar.thirdApi.jindee.core.common.pool.FrokJoinPoolSingletonEnum;
import com.lacesar.thirdApi.jindee.core.common.state.exceptions.ErpExceptionEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ListForkJoinTask extends RecursiveTask<Integer> {

    private static Log log = LogFactory.getLog(ListForkJoinTask.class);

    /**
     * 阀值
     */
    private static int THRESHOLD = 400;

    private final static ThreadLocal<List> THREAD_LOCAL_LIST = new ThreadLocal<>();

    private final static ThreadLocal<ForkJoinContext> THREAD_LOCAL_CONTEXT = new ThreadLocal<>();

    public ListForkJoinTask() {
    }

    private ListForkJoinTask(List list, ForkJoinContext forkJoinContext) {

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

        ListForkJoinTask task1 = new ListForkJoinTask(list.subList(0, size / 2), forkJoinContext);
        ListForkJoinTask task2 = new ListForkJoinTask(list.subList(size / 2, size), forkJoinContext);

        //两个任务并发执行起来
        invokeAll(task1, task2);

        return task1.join() + task2.join();
    }

    /**
     * 执行 forkjoin
     *
     * @param list
     * @param forkJoinContext
     * @return
     * @throws Exception
     */
    public int execute(List list, ForkJoinContext forkJoinContext) throws Exception {

        if (list == null) {
            log.error("【新增ts_manage_task】集合为空");
            throw new ErpException(ErpExceptionEnum.FORK_JOIN_LIST_IS_NULL);
        }

        ForkJoinPool forkJoinPool = FrokJoinPoolSingletonEnum.get();

        ListForkJoinTask listForkJoinTask = new ListForkJoinTask(list, forkJoinContext);


        ForkJoinTask<Integer> tasks = forkJoinPool.submit(listForkJoinTask);

        int i = tasks.get();

        return i;


    }


}
