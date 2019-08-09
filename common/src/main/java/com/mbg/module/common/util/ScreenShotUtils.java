package com.mbg.module.common.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

/***
 * created by Gap
 * 屏幕截取工具类
 */
public class ScreenShotUtils {
    private ScreenShotUtils() {
    }

    /***
     * 截取屏幕带StatusBar
     * @param activity 对象
     * @return 截取后的图片
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = UiUtils.getScreenWidth(activity);
        int height = UiUtils.getScreenHeight(activity);
        Bitmap bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /***
     * 截取屏幕不带StatusBar
     * @param activity 对象
     * @return 截取后的图片
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        Rect frame = new Rect();
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();

        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);

        int statusBarHeight = frame.top;
        int width = UiUtils.getScreenWidth(activity);
        int height = UiUtils.getScreenHeight(activity);
        Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return bp;

    }


    /**
     * 获取长截图
     * @return 获取的BitMap
     */
    public static Bitmap snapShotByViwGroup(ViewGroup viewGroup) {
        int h = 0;
        Bitmap bitmap;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            h += viewGroup.getChildAt(i).getHeight();
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(viewGroup.getWidth(), h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        viewGroup.draw(canvas);

        return bitmap;
    }
}
