package com.mbg.module.common.util;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;


/***
 * 线程管理类
 * created by gap
 */
public class ThreadUtils {
    private ThreadUtils(){}

    private static final Handler mMainThread=new Handler(Looper.getMainLooper());

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
}
