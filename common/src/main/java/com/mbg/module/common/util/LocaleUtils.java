package com.mbg.module.common.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.webkit.WebView;


import java.util.Locale;

public class LocaleUtils {
    private static final String TAG = "LocaleUtils";
    private static final String SHARED_KEY_LOCALE = "_shared_key_locale_";
    private static final Locale defaultLocale = new Locale(Language.EN.getCode(),CountryArea.America.getCode());


    /**
     * 根据sp数据设置本地化语言
     *
     * @param context 设置语言的context
     */
    public static void setLocale(Context context) {
        setLocale(context,false);
    }

    /**
     * 根据sp数据设置本地化语言
     *
     * @param context 设置语言的context
     * @param isFollowSystem 是否和系统语言一致，true系统语言，false App自己的语言
     */
    public static void setLocale(Context context,boolean isFollowSystem) {

        if (isFollowSystem) {
            Locale sysLocale = Resources.getSystem().getConfiguration().locale;
            updateLanguage(AppUtils.getApplication(), sysLocale);
            updateLanguage(context, sysLocale);
            return;
        }

        Locale locale = getLocale();
        updateLanguage(AppUtils.getApplication(), locale);
        updateLanguage(context, locale);
    }

    /**
     * 设置本地化语言
     *
     * @param context 设置语言的context
     * @param locale 要设置本地语言
     */
    public static void setLocale(Context context, Locale locale) {
        if (locale==null||context==null){
            return;
        }
        updateLanguage(AppUtils.getApplication(), locale);
        updateLanguage(context, locale);
    }

    /**
     * 判断是否是相同语言
     *
     * @param context contex
     * @return 语言是否相同
     */
    public static boolean isSameLanguage(Context context) {
        Locale type = getLocale();
        return isSameLanguage(context, type);
    }

    /**
     * 判断是否是相同语言
     *
     * @param context context
     * @param locale 对象
     * @return 是否相同
     */
    public static boolean isSameLanguage(Context context, Locale locale) {
        Locale appLocale = context.getResources().getConfiguration().locale;
        boolean equals = appLocale.equals(locale);
        Log.d(TAG, "isSameLanguage: " + locale.toString() + " / " + appLocale.toString() + " / " + equals);
        return equals;
    }

    /**
     * sp存储本地语言类型
     *
     * @param locale 存储的locale
     */
    private static void putLocale(Locale locale) {
        if (locale==null){
            return;
        }
        SharedPreferencesUtils.getSettingSharedPreferences().edit().putString(SHARED_KEY_LOCALE,localeToJson(locale)).apply();
    }

    /**
     * sp获取本地存储语言类型
     * @return Locale
     */
    private static Locale getLocale() {
        String type = SharedPreferencesUtils.getSettingSharedPreferences().getString(SHARED_KEY_LOCALE,"");
        if (StringUtils.isEmpty(type)){
            return defaultLocale;
        }
        return jsonToLocale(type);
    }

    private static void updateLanguage(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        Locale contextLocale = config.locale;

        if (StringUtils.equals(contextLocale.getLanguage(), locale.getLanguage())
                && StringUtils.equals(contextLocale.getCountry(), locale.getCountry())) {
            return;
        }

        // 解决webview所在的activity语言没有切换问题
        new WebView(context).destroy();

        DisplayMetrics dm = resources.getDisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
            context.createConfigurationContext(config);
        } else {
            config.locale = locale;
        }
        resources.updateConfiguration(config, dm);


        putLocale(locale);
    }


    /***
     * 定义语言
     */
    public enum Language{
        ZH("zh"),//中文
        EN("en"),//英文
        JA("ja"),//日文
        KO("ko"),//韩文
        TH("th"),//泰文
        FR("FR"),//法文
        ES("es"),//西班牙文
        PT("pt"),//葡萄牙文
        IN("in"),//印度尼西亚文
        VI("vi"),//越南文
        RU("ru");//俄罗斯文

        Language(String code){
            this.code=code;
        }
        private final String code;
        public String getCode(){
            return code;
        }
    }

    //定义国家或地区
    public enum CountryArea{
        China("CN"),//中国大陆
        Taiwan("TW"),//中国台湾或香港地区
        America("US"),//美国
        Japan("JP"),//日本
        Korea("KR"),//韩国
        Thailand("TH"),//泰国
        France("FR"),//法国
        Spain("ES"),//西班牙
        Portugal("PT"),//葡萄牙
        Indonesia("ID"),//印尼
        Vietnam("VN"),//越南
        Russia("RU"),//俄罗斯
        India("IN");//印度
        CountryArea(String code){
            this.code=code;
        }
        private final String code;

        public String getCode() {
            return code;
        }
    }

    private static String localeToJson(Locale locale) {
        return JsonUtils.toJson(locale);
    }

    private static Locale jsonToLocale(String json) {
        return JsonUtils.fromJson(json,Locale.class);
    }
}
