package com.mbg.module.common.util;

import android.util.Log;

import com.mbg.module.common.BuildConfig;

/**
 * Log打印类，用户log打印
 * created by gap
 */
public class LogUtil {
    private LogUtil(){}

    private final static String TAG="Logger";
    private final static boolean mIsLoggable= BuildConfig.DEBUG;
    public static int v(String msg) {
        return mIsLoggable?Log.w(TAG,getTracePrefix("v")+msg):0;
    }

    public static int d(String msg) {
        return mIsLoggable?Log.w(TAG,getTracePrefix("d")+msg):0;
    }

    public static int i(String msg) {
        return mIsLoggable?Log.w(TAG,getTracePrefix("i")+msg):0;
    }

    public static int w(String msg) {
        return mIsLoggable?Log.w(TAG,getTracePrefix("w")+msg):0;
    }

    public static int e(String msg) {
        return mIsLoggable?Log.e(TAG,getTracePrefix("e")+msg):0;
    }

    private static String getTracePrefix(String logLevel) {
        StackTraceElement[] sts = new Throwable().getStackTrace();
        StackTraceElement st = null;
        for (int i = 0; i < sts.length; i++) {
            if (sts[i].getMethodName().equalsIgnoreCase(logLevel)
                    && i + 2 < sts.length) {

                if (sts[i + 1].getMethodName().equalsIgnoreCase(logLevel)) {
                    st = sts[i + 2];
                    break;
                } else {
                    st = sts[i + 1];
                    break;
                }
            }
        }
        if (st == null) {
            return "";
        }

        String clsName = st.getClassName();
        if (clsName.contains("$")) {
            clsName = clsName.substring(clsName.lastIndexOf(".") + 1,
                    clsName.indexOf("$"));
        } else {
            clsName = clsName.substring(clsName.lastIndexOf(".") + 1);
        }
        return clsName + "->" + st.getMethodName() + "()"+"("+st.getLineNumber()+"):";
    }
}
