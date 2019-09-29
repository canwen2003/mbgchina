package com.mbg.module.common.core.net.tool;


import com.mbg.module.common.core.net.wrapper.request.HttpRequest;
import com.mbg.module.common.core.net.wrapper.request.OkHttpRequest;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import java.util.WeakHashMap;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/***
 * created by Gap
 */
public class OkHttpUtils {
    private static volatile OkHttpClient sOkHttpClient = null;
    private static WeakHashMap<Integer, List<WeakReference<Call>>> requestMap;

    public static void init() {
        if (sOkHttpClient == null) {
            synchronized (OkHttpUtils.class) {
                if (sOkHttpClient ==null) {
                    sOkHttpClient = new OkHttpClient.Builder()
                            .dns(DnsUtils.getDns())
                            .build();

                    requestMap = new WeakHashMap<>();
                }
            }
        }
    }


    public static Call newCall(Request request){
        if (sOkHttpClient ==null){
            init();
        }
        return sOkHttpClient.newCall(request);
    }

    public static void execute(HttpRequest requestWrapper) {
        if (sOkHttpClient ==null){
            init();
        }
        if (sOkHttpClient != null) {
            Call httpCall = new OkHttpRequest().request(requestWrapper);
            requestWrapper.setCall(httpCall);

            int hashCode = requestWrapper.getRequestHashCode();
            if (hashCode != 0 && httpCall != null) {
                List<WeakReference<Call>> requestList = requestMap.get(hashCode);
                if (requestList == null) {
                    requestList = new LinkedList<>();
                    requestMap.put(hashCode, requestList);
                }

                requestList.add(new WeakReference<>(httpCall));
            }
        }
    }

    /**
     * 取消对应hashCode的Request
     * @param hashcode Request对应的Request
     */
    public static void cancelRequest(int hashcode) {
        if (sOkHttpClient!=null) {
            synchronized (OkHttpUtils.class) {
                List<WeakReference<Call>> httpCallList = requestMap.get(hashcode);
                if (httpCallList != null) {
                    for (WeakReference<Call> httpCallRef : httpCallList) {
                        Call httpCall = httpCallRef.get();
                        if (httpCall != null) {
                            httpCall.cancel();
                        }
                    }
                }
                requestMap.remove(hashcode);
            }
        }
    }

    /**
     * 停止所有的请求
     */
    public static void stopAll() {
        if (sOkHttpClient!=null) {
            synchronized (OkHttpUtils.class) {
                sOkHttpClient.dispatcher().cancelAll();
                requestMap.clear();
            }
        }
    }
}
