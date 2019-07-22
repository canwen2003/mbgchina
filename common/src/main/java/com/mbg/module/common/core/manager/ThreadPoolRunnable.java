package com.mbg.module.common.core.manager;


import com.mbg.module.common.util.LogUtils;

public class ThreadPoolRunnable implements Runnable {
    private String threadName;
    private ThreadPoolPriority threadPriority = ThreadPoolPriority.THREAD_PRIORITY_NORMAL;
    private boolean isCancelOthers = false;

    private ThreadPoolRunnable() {}

    public ThreadPoolRunnable(String threadName) {
        this.threadName = threadName;
    }

    public ThreadPoolRunnable(String threadName, ThreadPoolPriority priority) {
        this.threadName = threadName;
        this.threadPriority = priority;
    }

    /*
    isCancelPrev:是否取消线程池中尚未被执行的其他同一线程
     */
    public ThreadPoolRunnable(String threadName, ThreadPoolPriority priority, boolean isCancelPrev) {
        this.threadName = threadName;
        this.threadPriority = priority;
        this.isCancelOthers = isCancelPrev;
    }

    public String getThreadName() {
        return this.threadName;
    }

    public ThreadPoolPriority getThreadPriority() {
        return this.threadPriority;
    }

    public boolean isCancelOthers() {
        return this.isCancelOthers;
    }

    @Override
    public String toString() {
        return "[threadName: " + threadName + ", priority: " + threadPriority.name()
                + ", isCancel: " + isCancelOthers + "]";
    }

    @Override
    public void run() {
        LogUtils.i(toString() + " start to run");
    }
}
