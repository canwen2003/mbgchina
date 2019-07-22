package com.mbg.module.common.util;

import android.util.Log;

/***
 * Created by Gap
 * 点击事件防重复点击
 */
public class ClickUtils {
    private static final String TAG=ClickUtils.class.getSimpleName();
    private static final long THRESHOLD_REPEAT_ENTER = 1000;//默认是1.0s

    private static long lastClickTime = 0;
    private static int lastViewId = -1;

    /**
     * 判断两次点击的间隔，如果小于1000，则认为是多次无效点击
     *
     * @return true,表示快速点击
     */
    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(-1, THRESHOLD_REPEAT_ENTER);
    }

    /**
     * 判断两次点击的间隔，如果小于1000，则认为是多次无效点击
     * @param viewId 点击对象Id
     * @return true,表示快速点击
     */
    public static boolean isFastDoubleClick(int viewId) {
        return isFastDoubleClick(viewId, THRESHOLD_REPEAT_ENTER);
    }

    /**
     * 判断两次点击的间隔，如果小于diff，则认为是多次无效点击
     * @param viewId 点击对象Id
     * @param diff 两次点击的最小时间差
     * @return true,表示快速点击
     */
    public static boolean isFastDoubleClick(int viewId, long diff) {
        long timeDiff = System.currentTimeMillis() - lastClickTime;
        if (lastViewId == viewId && lastClickTime > 0 && timeDiff < diff) {
            Log.d(TAG, "短时间内按钮多次触发");
            return true;
        }
        lastClickTime = System.currentTimeMillis();
        lastViewId = viewId;
        return false;
    }

}
