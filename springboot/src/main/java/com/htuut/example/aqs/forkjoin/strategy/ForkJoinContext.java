package com.lacesar.thirdApi.jindee.core.backupdata.forkjoin.strategy;

public class ForkJoinContext {

    private CollectionForkJoinTaskStrategy collectionForkJoinTaskStrategy;

    public ForkJoinContext(CollectionForkJoinTaskStrategy collectionForkJoinTaskStrategy) {
        this.collectionForkJoinTaskStrategy = collectionForkJoinTaskStrategy;
    }

    public int execute() {

        if (collectionForkJoinTaskStrategy != null) {
            int i = collectionForkJoinTaskStrategy.execute();

            return i;
        }

        return 0;
    }
}
