package com.mbg.module.common.util;

import android.widget.Toast;


public class ToastUtils {
    private ToastUtils(){}

    private static final Toast sToast;

    static {
        sToast = Toast.makeText(AppUtils.getApplication(), "", Toast.LENGTH_SHORT);
    }

    /**
     * 弹 Toast , 复用一个单例，解决多次弹 Toast 的问题。
     * @param msg
     */
    public static void show(final CharSequence msg) {
        ThreadUtils.postInUIThread(new Runnable() {
            @Override
            public void run() {
                try {
                    sToast.setText(msg);
                    sToast.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 在 App 处于 Debug 的环境下弹 Toast。
     * @param msg 显示的内容
     */
    public static void debugShow(final CharSequence msg) {
        if (AppUtils.isAppDebugable()) {
            show(msg);
        }
    }
}
