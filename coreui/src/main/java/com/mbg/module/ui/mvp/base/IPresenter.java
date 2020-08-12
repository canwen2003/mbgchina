package com.mbg.module.ui.mvp.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * 定义基础的Presenter
 * created by Gap
 */
public interface IPresenter<T extends IView> {

    /**
     * 初始化Presenter
     * @param  context Activity或Application的context
     *
     */
    void init(@NonNull Context context);

    /***
     * 注册view 对象
     * @param view view对象
     */
    void register(@NonNull T view, @Nullable Bundle params);


    /***
     * 解除注册view 对象
     * @param view view对象
     */
    void unRegister(@NonNull T view);


    /***
     * 向Presenter请求相关的数据信息
     * @param requestCode 请求数据的相关Id
     * @param params 请求数据的相关的参数
     */
    void requestData(int requestCode, @Nullable Object... params);


    /***
     *
     * 获取已经缓存的数据的大小：通过这个接口可以判断是否Presenter是否有Cache数据和cache数据的大小
     * @return 缓存的数据大小
     */
    int getCachedDataSize();

    /****
     *
     * 获取已经缓存的数据，本地内存缓存
     */
    void getCachedData();

    /****
     *
     * 清除缓存中的所有数据
     */
    void clearCachedData();

    /****
     *
     *  @param item 要清除的item
     * 清除缓存中的数据
     */
    void clearCachedData(Object item);

    /**
     * 检测View层是否是活动状态，主要是Presenter内部使用、这个接口主要是规范Presenter内的函数
     * @return true 处在活动状态，false 非活动状态
     */
    boolean isViewActive();
}
