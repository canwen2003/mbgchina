package com.mbg.module.common.util;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

/***
 * created by  Gap
 * 播放系统默认的铃声
 */
public class RingtoneUtils {
    private RingtoneUtils(){}

    public static void PlayNotification(){
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);//系统自带提示音
        Ringtone rt = RingtoneManager.getRingtone(AppUtils.getApplication(), uri);
        rt.play();
    }

}
