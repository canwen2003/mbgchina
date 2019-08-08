package com.mbg.module.common.core.net.common;

/***
 * created by Gap
 * Http请求类型
 */
public enum HttpType {
    GET,//请求指定的页面信息，并返回实体主体
    POST,//请求服务器接受所指定的文档作为对所标识的URI的新的从属实体
    PUT,//从客户端向服务器传送的数据取代指定的文档的内容。
    DELETE,//请求服务器删除指定的页面。
    TRACE,//请求服务器在响应中的实体主体部分返回所得到的内容。
    HEAD //向服务器索要与GET请求相一致的响应，只不过响应体将不会被返回。这一方法可以在不必传输整个响应内容的情况下，就可以获取包含在响应消息头中的元信息。
}
