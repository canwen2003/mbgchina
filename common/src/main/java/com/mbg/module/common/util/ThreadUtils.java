package com.mbg.module.common.util;

import android.os.Handler;
import android.os.Looper;


import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/***
 * 线程管理类
 * created by gap
 */
public class ThreadUtils {
    private ThreadUtils(){}
    // 单线程池
    private static ExecutorService mSingleExecutorService=new ThreadPoolExecutor(1,2,60, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(),new DefaultThreadFactory());
    private static final Handler mMainThread=new Handler(Looper.getMainLooper());

    /**
     * 在子线程中运行
     * @param runnable 对象
     */
    public static void postInThread(@NonNull Runnable runnable) {
        if (isOnBackgroundThread()) {
            runnable.run();
        } else {
            mSingleExecutorService.execute(runnable);
        }
    }

    /**
     * 在UI线程中运行
     * @param runnable 对象
     */
    public static void postInUIThread(@NonNull Runnable runnable) {
        if (isOnMainThread()) {
            runnable.run();
        } else {
            mMainThread.post(runnable);
        }
    }

    /**
     * 延迟在UI线程中运行
     * @param runnable 对象
     * @param delayMillis 延迟运行时间
     */
    public static void postInUIThreadDelayed(@NonNull Runnable runnable, long delayMillis) {
        mMainThread.postDelayed(runnable, delayMillis);
    }

    /***
     * 是否在UI线程中运行
     * @return true 在UI线程中，否则在子线程中
     */
    public static boolean isOnMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    /***
     * 是否子线程中运行
     * @return true 在UI线程中，否则在子线程中
     */
    public static boolean isOnBackgroundThread() {
        return !isOnMainThread();
    }

    /***
     * 是否在UI线程中运行，如果不在主线程则抛出异常
     *
     */
    public static void assertMainThread() {
        if (!isOnMainThread()) {
            throw new IllegalArgumentException("You must call this method on the main thread");
        }
    }

    /***
     * 是否在子线程中运行，如果不在子线程则抛出异常
     *
     */
    public static void assertBackgroundThread() {
        if (!isOnBackgroundThread()) {
            throw new IllegalArgumentException("You must call this method on a background thread");
        }
    }

    private static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        DefaultThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "module-" + poolNumber.getAndIncrement() + "-location-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }
}
