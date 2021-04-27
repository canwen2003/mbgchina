package com.mbg.module.ui.mvp.kotlin.base

import android.content.Context
import android.os.Bundle

interface IntPresenter <out T:IntView>{

    /**
     * 初始化Presenter
     * @param  context Activity或Application的context
     */
    fun init(context: Context)

    /***
     * 注册view 对象
     * @param view view对象
     */
    fun register(view: IntView, params: Bundle?)


    /***
     * 解除注册view 对象
     * @param view view对象
     */
    fun unRegister(view: IntView)


    /***
     * 向Presenter请求相关的数据信息
     * @param requestCode 请求数据的相关Id
     * @param params 请求数据的相关的参数
     */
    fun requestData(requestCode: Int, vararg params: Any?)


    /***
     *
     * 获取已经缓存的数据的大小：通过这个接口可以判断是否Presenter是否有Cache数据和cache数据的大小
     * @return 缓存的数据大小
     */
    fun getCachedDataSize(): Int

    /****
     *
     * 获取已经缓存的数据，本地内存缓存
     */
    fun getCachedData()

    /****
     *
     * 清除缓存中的所有数据
     */
    fun clearCachedData()

    /****
     *
     * @param item 要清除的item
     * 清除缓存中的数据
     */
    fun clearCachedData(item: Any?)

    /**
     * 检测View层是否是活动状态，主要是Presenter内部使用、这个接口主要是规范Presenter内的函数
     * @return true 处在活动状态，false 非活动状态
     */
    fun isViewActive(): Boolean
}