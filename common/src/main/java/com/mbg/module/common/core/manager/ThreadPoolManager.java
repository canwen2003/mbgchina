package com.mbg.module.common.core.manager;

import android.os.Build;


import com.mbg.module.common.util.LogUtils;

import java.util.Comparator;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class ThreadPoolManager {
    private static ThreadPoolManager mInstance;
    private static final Long KEEP_ALIVE_TIME=10L;//线程最大闲置销毁时间 10s
    private ThreadPoolExecutor mExecutorService;//高优先级和普通优先级线程池
    private final ExecutorService mSingleExecutorService;//单线程池
    private final ExecutorService mLogExecutorService;//埋点信息上传

    private ThreadPoolManager(){
        int corePoolSize=2;
        int maximumPoolSize=4;

        //根据处理器确认同时可执行的线程数和最大能同时创建的线程数
        if (Runtime.getRuntime().availableProcessors()>4){
            corePoolSize=Runtime.getRuntime().availableProcessors()>>1;
            maximumPoolSize=Runtime.getRuntime().availableProcessors();
        }else if (Build.VERSION.SDK_INT>=26){// 8.0以上的手机，多分配线程池
            corePoolSize=4;
            maximumPoolSize=8;
        }

        //多线程池执行器
        try {
            mExecutorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                    KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                    new PriorityBlockingQueue<>(20, new Comparator<Runnable>(){
                        @Override
                        public int compare(Runnable o1, Runnable o2) {
                            int priority1;
                            int priority2;
                            if(o1 instanceof ThreadPoolRunnable) {
                                priority1 = ((ThreadPoolRunnable) o1).getThreadPriority().ordinal();
                            } else {
                                priority1 = ThreadPoolPriority.THREAD_PRIORITY_NORMAL.ordinal();
                            }
                            if(o2 instanceof ThreadPoolRunnable) {
                                priority2 = ((ThreadPoolRunnable) o2).getThreadPriority().ordinal();
                            } else {
                                priority2 = ThreadPoolPriority.THREAD_PRIORITY_NORMAL.ordinal();
                            }
                            if (priority1 > priority2) {
                                return -1;
                            } else if (priority1 == priority2) {
                                return 0;
                            } else {
                                return 1;
                            }
                        }
                    }),new DefaultThreadFactory("multi"), new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    LogUtils.i("rejectedExecution: " + r.toString());
                }
            });

            //核心线程池在空闲后允许释放
            mExecutorService.allowCoreThreadTimeOut(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //单线程池执行器
        mSingleExecutorService=new ThreadPoolExecutor(1, 2,
                KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),new DefaultThreadFactory("single"));

        mLogExecutorService=new ThreadPoolExecutor(1, 2,
                KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),new DefaultThreadFactory("log"));
    }

    public static ThreadPoolManager get(){
        if (mInstance==null){
            synchronized (ThreadPoolManager.class){
                if (mInstance==null) {
                    mInstance = new ThreadPoolManager();
                }
            }
        }

        return mInstance;
    }

    /**
     * 在多线程池中执行
     * @param runnable 执行的Runnable对象
     */
    public void start(ThreadPoolRunnable runnable){
        if (runnable!=null && mExecutorService != null){
            mExecutorService.execute(runnable);
        }
    }

    /**
     * 在多线程池中执行
     * @param runnable 执行的Runnable对象
     */
    public void start(Runnable runnable){
        if (runnable!=null && mExecutorService != null){
            mExecutorService.execute(runnable);
        }
    }

    /**
     *
     * 埋点信息线程池，用于埋点信息上传
     * @param runnable 执行的Runnable对象
     */
    public void startLogThread(Runnable runnable){
        if (runnable!=null&&mLogExecutorService!=null){
            mLogExecutorService.execute(runnable);
        }
    }

    /**
     *
     * 在单线程池中执行，按照队列的方式，先到先执行
     * @param runnable 执行的Runnable对象
     */
    public void startInSingleThread(Runnable runnable){
        if (runnable!=null&&mSingleExecutorService!=null){
            mSingleExecutorService.execute(runnable);
        }
    }

    public Executor getMainExecutor(){
        return mExecutorService;
    }

    public Executor getSingleExecutor(){
        return mSingleExecutorService;
    }

    private static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        DefaultThreadFactory(String tag) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "mbg-" +
                    poolNumber.getAndIncrement() +
                    "-"+tag +"-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

}
