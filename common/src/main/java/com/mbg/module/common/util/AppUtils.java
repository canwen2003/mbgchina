package com.mbg.module.common.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.mbg.module.common.util.consts.UtilsConsts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class AppUtils {
    private AppUtils() {
    }

    private static Application sApplication;
    private static boolean sAppForeground=false;

    static {
        //App 前后台切换监控
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new AppLifecycleObserver());
    }

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
        if (sApplication == null) {
            try {
                @SuppressLint("PrivateApi")
                Class<?> activityThread = Class.forName("android.app.ActivityThread");
                Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
                Object app = activityThread.getMethod("getApplication").invoke(thread);
                sApplication=(Application) app;
            } catch (Exception e) {
                try {
                    @SuppressLint("PrivateApi")
                    Class<?> activityThread = Class.forName("android.app.ActivityThread");
                    Method m_currentActivityThread = activityThread.getDeclaredMethod("currentActivityThread");
                    Field f_mInitialApplication = activityThread.getDeclaredField("mInitialApplication");
                    f_mInitialApplication.setAccessible(true);
                    Object current = m_currentActivityThread.invoke(null);
                    Object app = f_mInitialApplication.get(current);
                    sApplication = (Application) app;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
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
     * 获取 App PackageManager
     *
     * @return PackageManager对象
     */
    public static PackageManager getPackageManager() {
        Context context = getApplication();
        return context.getPackageManager();
    }

    /**
     * 获取 App Resources
     *
     * @return Resources对象
     */
    public static Resources getResources() {
        Context context = getApplication();
        return context.getResources();
    }

    /**
     * 获取 App Resources
     *
     * @return Resources对象
     */
    public static AssetManager getAssetManager() {
        Context context = getApplication();
        return context.getAssets();
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
    public static boolean isAppDebuggable() {
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
     * @return 进程名
     */
    public static String getProcessName() {
        String processName=null;
        processName=getCurrentProcessNameByFile();
        if (StringUtils.isEmpty(processName)){
            processName=getCurrentProcessNameByAms();
        }
        if (StringUtils.isEmpty(processName)){
            processName=getCurrentProcessNameByReflect();
        }
        return processName;
    }

    /**
     *
     * @return 判断App是否在前台
     */
    public static boolean isAppForeground(){
        return sAppForeground;
    }

    /**
     * 解决输入法内存泄漏的问题
     * @param window 窗口
     */
    public static void fixSoftInputLeaks(final Window window) {
        InputMethodManager imm = (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        String[] leakViews = new String[]{"mLastSrvView", "mCurRootView", "mServedView", "mNextServedView"};
        for (String leakView : leakViews) {
            try {
                Field leakViewField = InputMethodManager.class.getDeclaredField(leakView);
                if (leakViewField == null) continue;
                if (!leakViewField.isAccessible()) {
                    leakViewField.setAccessible(true);
                }
                Object obj = leakViewField.get(imm);
                if (!(obj instanceof View)) continue;
                View view = (View) obj;
                if (view.getRootView() == window.getDecorView().getRootView()) {
                    leakViewField.set(imm, null);
                }
            } catch (Throwable ignore) {/**/}
        }
    }

    /**
     * 获取当前的启动Launcher Activity
     * @return  Launcher Activity name。
     */
    public static String getLauncherActivity() {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setPackage(getApplication().getPackageName());
        PackageManager pm = getApplication().getPackageManager();
        List<ResolveInfo> info = pm.queryIntentActivities(intent, 0);
        ResolveInfo next = info.iterator().next();
        if (next != null) {
            return next.activityInfo.name;
        }
        return null;
    }

    /**
     * 重启系统让配置生效
     * @param homeActivity 主页
     */
    public void rebootApplication(Class<Activity> homeActivity){
        Intent intent = new Intent(AppUtils.getApplication(),homeActivity);
        intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
        );
        AppUtils.getApplication().startActivity(intent);
    }


    private static class AppLifecycleObserver implements LifecycleObserver{

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        public void onAppStart () {
            sAppForeground=true;
            LiveEventBus.get(UtilsConsts.KEY_MSG_APP_STATUS_CHANGE).post(true);
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        public void onAppStop () {
            sAppForeground=false;
            LiveEventBus.get(UtilsConsts.KEY_MSG_APP_STATUS_CHANGE).post(false);
        }
    }

    private static String getCurrentProcessNameByFile() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String getCurrentProcessNameByAms() {
        ActivityManager am = (ActivityManager) AppUtils.getApplication().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return "";
        List<ActivityManager.RunningAppProcessInfo> info = am.getRunningAppProcesses();
        if (info == null || info.size() == 0) return "";
        int pid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (aInfo.pid == pid) {
                if (aInfo.processName != null) {
                    return aInfo.processName;
                }
            }
        }
        return "";
    }

    private static String getCurrentProcessNameByReflect() {
        String processName = "";
        try {
            Application app = AppUtils.getApplication();
            Field loadedApkField = app.getClass().getField("mLoadedApk");
            loadedApkField.setAccessible(true);
            Object loadedApk = loadedApkField.get(app);

            Field activityThreadField = loadedApk.getClass().getDeclaredField("mActivityThread");
            activityThreadField.setAccessible(true);
            Object activityThread = activityThreadField.get(loadedApk);

            Method getProcessName = activityThread.getClass().getDeclaredMethod("getProcessName");
            processName = (String) getProcessName.invoke(activityThread);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return processName;
    }


}
