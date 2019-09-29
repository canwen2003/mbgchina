package com.mbg.module.common.core.net.common;


/***
 * 定义常用的一些网络错误
 */
public interface NetStatus {
    int SUCCESS_STATUS = 0;
    int COMMON_ERROR = -101; //通用Exception，除可识别的IOException之外的其他未识别的Exception;
    int IO_ERROR = -111; //IOException，除可识别的IOException外的其他IOException；
    int FILE_NOT_FOUND_ERROR =-112;// FileNotFoundException, 尝试打开指定路径名的文件失败。
    int UNKNOWN_HOST_ERROR =-113;// UnknownHostException, 无法确定主机的IP地址
    int SOCKET_ERROR =-114; // SocketException, 底层协议中存在错误，例如TCP错误
    int IO_INTERRUPTERD_ERROR =-115; //InterruptedIOException, 表示I/O操作已被中断，由于执行输入或输出传输的线程被中断而终止了输入或输出传输。
    int SSL_ERROR =-116;// SSLException, ssl相关操作异常
    int EOF_ERROR =-117;// EOFException, 输入期间意外地到达文件的末尾或流的末尾。
    int RETRY_ERROR =-118;//HttpRetryException, HTTP请求需要重试，但不能自动重试，因为启用了流模式。
    int TIMEOUT_ERROR = -119;// SocketTimeoutException，socket连接超时，InterruptedIOException的子类
    int CONNECT_ERROR =-120;// ConnectException，socket连接到远程地址和端口时发生错误。可能是远程拒绝连接(例如，没有进程监听此远程地址或端口)或连接超时。
    int UNKNOWN_SERVICE_ERROR = -121;// UnknownServiceException，未知服务异常，URL连接返回的MIME类型没有意义，或者应用程序试图写入只读URL连接。
    int ROUTE_ERROR =-122;//  NoRouteToHostException，socket连接到远程地址和端口时发生错误的信号。通常，由于防火墙或中间路由器宕机，无法访问远程主机。
    int PORT_ERROR =-123;// PortUnreachableException，已连接的数据报上接收到ICMP端口不可到达消息。
    int PROTOCOL_ERROR =-124;//  ProtocolException，底层协议中存在错误。
    int BIND_ERROR = -125;// BindException，绑定到地址和端口时发生错误。

    int RESPONSE_EMPTY=-201;//服务器返回的数据为空
    int RESPONSE_BODY_EMPTY=-202;//服务器返回的数据的Body为空
    int PARSEDATA_ERROR=-203;//服务器返回数据解析错误
}
