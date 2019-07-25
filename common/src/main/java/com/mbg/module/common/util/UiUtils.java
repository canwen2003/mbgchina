package com.mbg.module.common.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;


import java.lang.reflect.Method;

public class UiUtils {
    private UiUtils() {}

    /**
     * 将dp转换为px
     * @param context 对象
     * @param dpValue dp值
     * @return px值
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5F);
    }

    /**
     * 将px转换为dp
     * @param context 对象
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5F);
    }

    /**
     * 将px转换为sp
     * @param context 对象
     * @param pxValue px值
     * @return sp值
     */
    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(pxValue / fontScale + 0.5F);
    }

    /**
     * 将sp转换为px
     * @param context 对象
     * @param spValue sp 值
     * @return px值
     */
    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(spValue * fontScale + 0.5F);
    }

    /**
     * 获取StatusBar高度
     * @param activity 对象
     * @return StatusBar高度
     */
    public static int getStatusHeight(Activity activity) {
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        int statusHeight = localRect.top;

        if (0 == statusHeight) {
            Resources resources = activity.getResources();
            int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
            statusHeight= resources.getDimensionPixelSize(resourceId);
        }

        return statusHeight;
    }


    public static float getDensity(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager == null) {
            return 0;
        }
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.density;
    }

    public static int getDensityDpi(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager == null) {
            return 0;
        }
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.densityDpi;
    }

    public static int getWidthPixels(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager == null) {
            return 0;
        }
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static int getHeightPixels(Activity context) {
        return getRealHeightPixels(context) - getStatusHeight(context);
    }

    public static int getRealHeightPixels(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = 0;
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        Class c;
        try {
            c = Class.forName("android.view.Display");
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            height = dm.heightPixels;
        } catch (Exception e) {
            LogUtils.d( e.toString());
        }
        return height;
    }

    /**
     * 获得屏幕高度
     *
     * @param context 对象
     *
     * @return 平面的宽度
     */
    public static int getScreenWidth(@NonNull Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context 对象
     *
     * @return 屏幕的高度
     */
    public static int getScreenHeight(@NonNull Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获取toolbar的高度
     *
     * @param context 对象
     *
     * @return 默认为0
     */
    public static int getToolbarHeight(@NonNull Context context) {
        TypedValue typedValue = new TypedValue();

        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValue, true)) {
            return TypedValue.complexToDimensionPixelSize(typedValue.data, context.getResources().getDisplayMetrics());
        }

        return 0;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private int getSoftButtonsBarHeight(@NonNull Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics metrics = new DisplayMetrics();

        wm.getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;

        wm.getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;

        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }


    /**
     * 获取当前屏幕截图 包含状态栏
     *
     * @param activity 对象
     *
     * @return 截屏图片
     */
    public static Bitmap snapShotWithStatusBar(@NonNull Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图 但不包含状态栏
     *
     * @param activity 对象
     *
     * @return 返回截屏内容
     */
    public static Bitmap snapShotWithoutStatusBar(@NonNull Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        view.getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }

    /***
     * 设置截屏和录屏的权限
     * @param activity 对象
     * @param enable 是否可以录截屏
     */
    public void setScreenSnapShot(Activity activity,boolean enable) {
        if (activity==null){
            return;
        }

        if (enable){
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }else {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        }
    }
}
