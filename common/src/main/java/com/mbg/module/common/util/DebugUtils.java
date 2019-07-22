package com.mbg.module.common.util;

import android.os.StrictMode;

public class DebugUtils {
    private DebugUtils(){}

    public static void startStrictModeVmPolicy(){
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectActivityLeaks()//检测Activity内存泄露
                .detectLeakedClosableObjects()//检测未关闭的Closable对象
                .detectLeakedSqlLiteObjects() //检测Sqlite对象是否关闭
                /*也可以采用detectAll()来检测所有想检测的东西*/
                .penaltyLog().build());
    }
    public static void startStrictModeThreadPolicy(){
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()//磁盘读取操作检测
                .detectDiskWrites()//检测磁盘写入操作
                .detectNetwork() //检测网络操作
                /*也可以采用detectAll()来检测所有想检测的东西*/
                .penaltyLog().build());
    }
}
