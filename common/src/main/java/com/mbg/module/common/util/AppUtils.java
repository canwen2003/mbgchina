package com.mbg.module.common.util;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AppUtils {
    private AppUtils(){}

    private static Application sApplication;

    /**
     * 设置全局 Application
     */
    public static void setApplication(@NonNull Application application) {
        sApplication = application;
    }

    /**
     * 获取全局 Application
     */
    public static Application getApplication() {
        //如果没有设置则需要通过反射的方式来获取
        if (sApplication==null){
            try {
                Class<?> activityThread = Class.forName("android.app.ActivityThread");
                Method m_currentActivityThread = activityThread.getDeclaredMethod("currentActivityThread");
                Field f_mInitialApplication = activityThread.getDeclaredField("mInitialApplication");
                f_mInitialApplication.setAccessible(true);
                Object current = m_currentActivityThread.invoke(null);
                Object app = f_mInitialApplication.get(current);
                sApplication = (Application) app;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return sApplication;
    }


    /**
     * 获取 App 的版本名
     *
     * @return app的版本名 默认为""
     */
    public static String getPackageName() {
        Context context = getApplication();
        return context.getPackageName();
    }

    /**
     * 获取 App 的版本名
     *
     * @return app的版本名 默认为""
     */
    public static String getVersionName() {
        Context context = getApplication();
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取 App 的版本号
     *
     * @return 获取版本号 默认为0
     */
    public static int getVersionCode() {
        Context context = getApplication();
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @return App 是否可以 debug
     */
    public static boolean isAppDebugable() {
        try {
            ApplicationInfo info = getApplication().getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
