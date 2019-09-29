package com.mbg.module.common.core.net.wrapper.request;

import com.mbg.module.common.core.net.common.HttpType;
import com.mbg.module.common.core.net.wrapper.response.AbstractResponse;

import java.util.HashMap;
import java.util.Map;

public interface IRequest {
    /***
     * 设置请求类型
     * @param type 请求类型
     * @return 返回对象本身
     */
    IRequest setType(HttpType type);

    /**
     * 获取请求类型
     * @return 请求类型
     */
    HttpType getType();


    /***
     * 设置请求的Url
     * @param url 获取 请求的Url
     * @return 对象本身
     */
    IRequest setUrl(String url);


    /***
     * 获取URL
     * @return url
     */
    String getUrl();

    /***
     * 设置Http的内容格式
     * @param contentType 内容格式
     * @return 对象本身
     */
    IRequest setContentType(String contentType);


    /***
     * 获取Http的内容格式
     * @return 内容格式
     */
    String getContentType();

    /***
     * 添加请求的参数
     * @param key 字段name
     * @param value 字段data
     * @return 对象本身
     */
    IRequest addRequestParams(String key, String value);


    /***
     * 添加请求的参数
     * @param params 参数
     * @return 对象本身
     */
    IRequest addRequestParams(Map<String, String> params);


    /***
     * 获取请求的参数
     * @return 参数对象
     */
    RequestParams getRequestParams();

    /***
     * 设置请求的头部信息
     * @param headers
     * @return
     */
    IRequest setHeaders(HashMap<String, String> headers);


    /***
     * 添加请求的头部信息
     * @param key 字段name
     * @param value 字段value
     * @return 对象本身
     */
    IRequest addHeader(String key, String value);

    /***
     * 添加请求的头部信息
     * @param headers 头部信息
     * @return 对象本身
     */
    IRequest addHeader(Map<String, String> headers);


    /**
     * 设置请求体
     * @param body 请求体
     * @return 对象本身
     */
    IRequest setBody(String body);

    /**
     * 设置请求体
     * @param body 请求体
     * @return 对象本身
     */
    IRequest setBody(Map<String, String> body);

    /***
     * 设置请求体
     * @param bytesBody 请求体
     * @return 对象本身
     */
    IRequest setBody(byte[] bytesBody);

    /**
     * 获取头部信息
     * @return 头部信息
     */
    Map<String, String> getHeaders();

    /***
     * 设置返回的对象
     * @param responseHandler 返回对象
     * @return 对象本身
     */
    IRequest setResponseHandler(AbstractResponse<?> responseHandler);

    /***
     * 获取返回对象
     * @return 返回对象
     */
    AbstractResponse<?> getResponseHandler();

    /***
     * 设置是否启用HttpDns
     * @param enable true，启用，false 不启用
     * @return 对象本身
     */
    IRequest setHttpDnsEnable(boolean enable);

    /**
     * 获取HttpDns的状态
     * @return 是否启用了httpDns
     */
    boolean getHttpDnsEnable();

    /***
     * 异步执行请求
     * @return 对比本身
     */
    IRequest execute();

    /***
     * 取消请求
     */
    void cancel();

    int getRequestHashCode();
}
