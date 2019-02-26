package org.jeecgframework.core.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);
    
    public static void execute(Runnable command){
        executorService.execute(command);
    }
}
