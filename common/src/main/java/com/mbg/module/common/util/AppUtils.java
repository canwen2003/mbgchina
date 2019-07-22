package com.mbg.module.common.util;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;


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

}
