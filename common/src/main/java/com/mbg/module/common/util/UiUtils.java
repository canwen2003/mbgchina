package com.mbg.module.common.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;

import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;


import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class UiUtils {
    private UiUtils() {}

    /**
     * 将dp转换为px
     * @param dpValue dp值
     * @return px值
     */
    public static int dip2px(float dpValue) {
        float scale = AppUtils.getApplication().getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5F);
    }

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
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dip(float pxValue) {
        float scale = AppUtils.getApplication().getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5F);
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
     * @param pxValue px值
     * @return sp值
     */
    public static int px2sp(float pxValue) {
        float fontScale = AppUtils.getApplication().getResources().getDisplayMetrics().scaledDensity;
        return (int)(pxValue / fontScale + 0.5F);
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
     * @param spValue sp 值
     * @return px值
     */
    public static int sp2px(float spValue) {
        float fontScale = AppUtils.getApplication().getResources().getDisplayMetrics().scaledDensity;
        return (int)(spValue * fontScale + 0.5F);
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


    /***
     * 通过文件名获取资源id 例子：getResId("icon", R.drawable.class);
     * @param variableName 资源名称
     * @param c 资源类名
     * @return 资源Id
     */
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /***
     * * 全局显示一个View
     *
     * @param activity 活动的activity
     * @param view 需要显示的View
     * @param widgetId ViewID
     * @param fadeDuration 消失的渐变时间
     * @param viewCallback 重复View的处理策略回调
     */
    public static void showGlobal(@NonNull Activity activity, View view, int widgetId, int fadeDuration, ViewCallback viewCallback) {
        if (view != null) {//删除View期间不能添加View
            //activity.getWindow().getDecorView获取DecorView是顶级View，它是一个FrameLayout布局，内部有titlebar和contentParent两个子元素。
            //titlebar：顶级标题栏，如果Activity设置了FEATURE_NO_ACTIONBAR，这个view就会消失，那么DecorView就只有mContentParent一个子View
            //contentParent：id是content，可以通过topActivity.findViewById(android.R.id.content)获取这个View，设置的Activity的setContentView(R.layout.main)的main.xml布局则是contentParent里面的一个子元素
            //如果我们添加的View想要忽略titlebar的话就需要用decorView来添加，不忽略的话可用contentParent添加
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();

            //如果View存在就删除，防止重复添加
            View oldView = decorView.findViewById(widgetId);
            //是否要用户自己处理，true的话这里就不用处理了，外部处理就行
            boolean outerHandler = false;
            if (oldView != null) {
                outerHandler = onView(viewCallback, oldView);
            }
            if(!outerHandler){
                //ViewGroup contentView = topActivity.findViewById(android.R.id.content);
                //FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                //view.setLayoutParams(params);
                if (oldView != null) {
                    decorView.removeView(oldView);
                }
                view.setId(widgetId);
                decorView.addView(view);
                //是否需要渐变
                if (fadeDuration > 0) {
                    view.setVisibility(View.INVISIBLE);
                    //渐变显示
                    ViewAnimUtil.showFade(view, 0, 1, fadeDuration);
                }
            }
        }
    }

    /**
     * 隐藏全局弹窗
     * @param activity 活动的activity
     * @param widgetID ViewID
     * @param fadeDuration 消失的渐变时间
     */
    public static void hideGlobal(@NonNull Activity activity, int widgetID, int fadeDuration) {
        //ViewGroup contentView = activity.findViewById(android.R.id.content);
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        //如果我们addView是通过contentView添加的，则decorView和contentView都能找到这个View
        //如果我们addView是通过decorView添加的，只有decorView才能找到这个View
        //所以删除的话只需要用decorView删除即可
        //View contentGlobalView = contentView.findViewById(GLOBAL_WIDGET_ID);
        View view = decorView.findViewById(widgetID);
        if (view != null) {
            //是否需要渐变
            if (fadeDuration > 0) {
                //渐变显示
                ViewAnimUtil.hideFade(view, 1, 0, fadeDuration);
            }
            decorView.removeView(view);
        }
    }

    /**
     * 返回旧的View，用户决定他的操作
     */
    private static boolean onView(ViewCallback viewCallback, View view) {
        if (viewCallback != null) {
            return viewCallback.onView(view);
        }
        return false;
    }

    /**
     * GlobalView回调,返回旧的View，用户决定他的操作
     */
    public interface ViewCallback {

        /**
         * GlobalView回调,返回旧的View，用户决定他的操作
         * @param view 重复的View
         * @return true:外部自行处理；false:内部处理
         */
        boolean onView(View view);
    }

}
