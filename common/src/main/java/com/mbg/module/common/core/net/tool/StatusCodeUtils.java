package com.mbg.module.common.core.net.tool;

import com.mbg.module.common.core.net.common.NetStatus;
import com.mbg.module.common.core.net.manager.HttpManager;
import com.mbg.module.common.util.LogUtils;
import com.mbg.module.common.util.StringUtils;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.InterruptedIOException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.HttpRetryException;
import java.net.NoRouteToHostException;
import java.net.PortUnreachableException;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import javax.net.ssl.SSLException;

/***
 * 解析常用的Exeception
 * created by Gap
 */
public class StatusCodeUtils {

    public static int getStatusCode(Exception e) {
        if (null != e) {
            if (e instanceof SocketException) {
                if (e instanceof ConnectException) {
                    return NetStatus.CONNECT_ERROR;
                } else if (e instanceof NoRouteToHostException) {
                    return NetStatus.ROUTE_ERROR;
                } else if (e instanceof PortUnreachableException) {
                    return NetStatus.PORT_ERROR;
                } else if (e instanceof BindException) {
                    return NetStatus.BIND_ERROR;
                }
                return NetStatus.SOCKET_ERROR;
            } else if (e instanceof InterruptedIOException) {
                if (e instanceof SocketTimeoutException) {
                    return NetStatus.TIMEOUT_ERROR;
                }
                return NetStatus.IO_INTERRUPTERD_ERROR;
            } else if (e instanceof SSLException) {
                return NetStatus.SSL_ERROR;
            } else if (e instanceof EOFException) {
                return NetStatus.EOF_ERROR;
            } else if (e instanceof FileNotFoundException) {
                return NetStatus.FILE_NOT_FOUND_ERROR;
            } else if (e instanceof UnknownHostException) {
                return NetStatus.UNKNOWN_HOST_ERROR;
            } else if (e instanceof UnknownServiceException) {
                return NetStatus.UNKNOWN_SERVICE_ERROR;
            } else if (e instanceof HttpRetryException) {
                return NetStatus.RETRY_ERROR;
            } else if (e instanceof ProtocolException) {
                return NetStatus.PROTOCOL_ERROR;
            }
        }
        return NetStatus.IO_ERROR;
    }

    public static void pareHttpFailure(Exception error, int statusCode, String content,String url){
        if (HttpManager.isDebug()){
            if (!StringUtils.isEmpty(content)){
                LogUtils.e("status:"+statusCode+"url:"+url+"message:"+content);
            } else if (error!=null){
                LogUtils.e("status:"+statusCode+"url:"+url+"message:"+error.getMessage());
            }else {
                LogUtils.e("status:"+statusCode+"url:"+url+"message: unknown");
            }

        }
    }

}
