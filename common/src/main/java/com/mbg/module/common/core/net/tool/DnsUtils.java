package com.mbg.module.common.core.net.tool;


import com.mbg.module.common.core.net.manager.HttpManager;
import com.mbg.module.common.core.net.tool.dnscache.DNSCache;
import com.mbg.module.common.core.net.tool.dnscache.DomainInfo;
import com.mbg.module.common.util.LogUtils;
import com.mbg.module.common.util.StringUtils;

import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
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
                            DomainInfo[] domainInfos=DNSCache.getInstance().getDomainServerIp(hostname);
                            if(domainInfos != null) {
                                String ip=domainInfos[0].host;
                                if (!StringUtils.isEmpty(ip)) {
                                    List<InetAddress> inetAddresses = Arrays.asList(InetAddress.getAllByName(ip));
                                    if (HttpManager.isDebug()) {
                                        LogUtils.d("DnsUtils lookup, hostname:" + hostname + ", netAddresses:" + inetAddresses);
                                    }
                                    return inetAddresses;
                                }
                            }


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
