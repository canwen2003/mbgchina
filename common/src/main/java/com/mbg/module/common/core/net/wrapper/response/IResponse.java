package com.mbg.module.common.core.net.wrapper.response;

/***
 * created by Gap
 * Http返回的主要回掉函数,都在UI线程中返回
 */
public interface IResponse<D> {
    void onUIStart();//开始请求数据
    void onUIUpdate(D data);//返回数据
    void onUICache(D data);//返回缓存数据
    void onUIError(Exception error);//返回错误信息
    void onUIFinish();//请求结束
}
