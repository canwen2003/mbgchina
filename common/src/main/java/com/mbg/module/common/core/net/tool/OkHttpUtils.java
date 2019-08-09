package com.mbg.module.common.core.net.tool;

import android.telecom.Call;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.WeakHashMap;

import okhttp3.OkHttpClient;

public class OkHttpUtils {

    private static OkHttpClient okHttpClient = null;

    public static void init() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .dns(DnsUtils.getDns())
                    .build();
        }
    }

    private static final WeakHashMap<Integer, List<WeakReference<Call>>> requestMap = new WeakHashMap<>();

    public static void execute() {

    }
}
