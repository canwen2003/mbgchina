package com.mbg.module.common.core.net.manager;


import com.mbg.module.common.core.net.tool.DnsUtils;
import com.mbg.module.common.core.net.wrapper.request.HttpRequest;
import com.mbg.module.common.core.net.wrapper.request.OkHttpRequest;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/***
 * created by Gap
 */
public class OkHttpManager {
    private static volatile OkHttpManager instance;
    private final OkHttpClient mOkHttpClient;

    private OkHttpManager(){
        mOkHttpClient = new OkHttpClient.Builder()
                .dns(DnsUtils.getDns())
                .build();
    }

    public static OkHttpManager get() {
        if (instance == null) {
            synchronized (OkHttpManager.class) {
                if (instance ==null) {
                    instance=new OkHttpManager();
                }
            }
        }

        return instance;
    }


    public Call newCall(Request request){

        return mOkHttpClient.newCall(request);
    }

    public  void execute(HttpRequest requestWrapper) {
        Call httpCall = new OkHttpRequest().request(requestWrapper);
        requestWrapper.setCall(httpCall);
    }

    /**
     * 停止所有的请求
     */
    public void stopAll() {
        synchronized (OkHttpManager.class) {
            mOkHttpClient.dispatcher().cancelAll();
        }
    }
}
