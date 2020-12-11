package com.mbg.module.common.core.net.tool;


import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import okhttp3.Dns;

public class DnsUtils {
    private static Dns mDns;
    private DnsUtils(){ }

    public static Dns getDns(){
        if (mDns==null){
            synchronized (DnsUtils.class){
                if (mDns==null) {
                    mDns = new Dns() {
                        @NotNull
                        @Override
                        public List<InetAddress> lookup(@NotNull String hostname) throws UnknownHostException {

                            //如果返回null，走系统DNS服务解析域名
                            return Dns.SYSTEM.lookup(hostname);
                        }
                    };
                }
            }
        }
        return mDns;
    }
}
