package com.mbg.module.common.util;

import android.content.Context;
import android.content.SharedPreferences;

/***
 *
 * 定义系统需要SharedPreferences相关的文件存储
 * created by Gap
 */
public class SharedPreferencesUtils {
    //定Preferences文件名
    private static final String MBG_SHARED_PREFERENCES_SETTING = "mbg_sf_setting";//系统设置
    private static final String MBG_SHARED_PREFERENCES_COMMON = "mbg_sf_common";//公用设置
    private static final String MBG_SHARED_PREFERENCES_USER= "mbg_sf_user";//用户设置
    private static final String MBG_SHARED_PREFERENCES_NET= "mbg_sf_net";//网络配置
    private static final String MBG_SHARED_PREFERENCES_TIPS= "mbg_sf_tips";//一次性提醒设置

    //定义Preferences对象引用
    private static SharedPreferences sSettingSharedPreferences;
    private static SharedPreferences sCommonSharedPreferences;
    private static SharedPreferences sUserSharedPreferences;
    private static SharedPreferences sNetSharedPreferences;
    private static SharedPreferences sTipsSharedPreferences;


    public static SharedPreferences getSettingSharedPreferences() {
        if (sSettingSharedPreferences == null) {
            sSettingSharedPreferences = AppUtils.getApplication().getSharedPreferences(MBG_SHARED_PREFERENCES_SETTING, Context.MODE_PRIVATE);
        }
        return sSettingSharedPreferences;
    }

    public static SharedPreferences getCommonSharedPreferences() {
        if (sCommonSharedPreferences == null) {
            sCommonSharedPreferences = AppUtils.getApplication().getSharedPreferences(MBG_SHARED_PREFERENCES_COMMON, Context.MODE_PRIVATE);
        }
        return sCommonSharedPreferences;
    }

    public static SharedPreferences getUserSharedPreferences() {
        if (sUserSharedPreferences == null) {
            sUserSharedPreferences = AppUtils.getApplication().getSharedPreferences(MBG_SHARED_PREFERENCES_USER, Context.MODE_PRIVATE);
        }
        return sUserSharedPreferences;
    }

    public static SharedPreferences getNetSharedPreferences() {
        if (sNetSharedPreferences == null) {
            sNetSharedPreferences = AppUtils.getApplication().getSharedPreferences(MBG_SHARED_PREFERENCES_NET, Context.MODE_PRIVATE);
        }
        return sNetSharedPreferences;
    }

    public static SharedPreferences getTipsSharedPreferences() {
        if (sTipsSharedPreferences == null) {
            sTipsSharedPreferences = AppUtils.getApplication().getSharedPreferences(MBG_SHARED_PREFERENCES_TIPS, Context.MODE_PRIVATE);
        }
        return sTipsSharedPreferences;
    }
}
