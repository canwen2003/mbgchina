package com.mbg.module.ui.mvp.kotlin

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.mbg.module.common.util.LogUtils
import com.mbg.module.ui.mvp.kotlin.base.IntPresenter
import com.mbg.module.ui.mvp.kotlin.base.IntView
import java.util.*

abstract class MvpPresenter<T : IntView>:IntPresenter<T> {
    protected var mContext: Context? = null
    private var presenterId: String="MvpPresenter_init"
    private val mViewPool = Collections.synchronizedSet(HashSet<T>())

    /**
     * 初始化Presenter
     * @param  context Activity或Application的context
     */
    override fun init(context: Context) {
        mContext = context
    }

    /***
     * 注册view 对象
     * @param view view对象
     */
    override fun register(view: IntView, params: Bundle?) {
        mViewPool.add(view as T)
        view.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onOwnerStart() {
                LogUtils.d("onOwnerStart ： $view")
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onOwnerDestroy() {
                LogUtils.d("onOwnerDestroy ：$view")
            }
        })
    }

    override fun unRegister(view: IntView) {
        if (mViewPool.contains(view)) {
            mViewPool.remove(view)
        }
    }

    override fun requestData(requestCode: Int, vararg params: Any?) {}

    open fun getPresenterId()=presenterId

    open fun setPresenterId(presenterId: String) {
        this.presenterId = presenterId
    }

    /***
     *
     * 获取已经缓存的数据的大小：通过这个接口可以判断是否Presenter是否有Cache数据和cache数据的大小
     * @return 缓存的数据大小
     */
    override fun getCachedDataSize(): Int {
        return 0
    }

    /****
     *
     * 获取已经缓存的数据，本地内存缓存
     */
    override fun getCachedData() {}

    /****
     *
     * 清除缓存中的所有数据
     */
    override fun clearCachedData() {}

    /****
     *
     * @param item 要清除的item
     * 清除缓存中的数据
     */
    override fun clearCachedData(item: Any?) {}

    /***
     * 用来返回Presenter处理后返回给View的数据
     * @param resultCode 用来指示该函数的主功能
     * @param params 用来传递功能数据
     */
    open fun onPresenterResult(resultCode: Int, vararg params: Any?) {
        for (view in mViewPool) {
            if (view.lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
                view.onPresenterResult(resultCode, *params)
            }
        }
    }

    /***
     *
     * 处理加载更多、Presenter告诉View层是否还有更多的数据，这个时候View层可以设置ListView是否可以加载更多
     * @param isHasMore 是否还有数据
     */
    open fun onLoadMoreView(isHasMore: Boolean) {
        for (view in mViewPool) {
            if (view.lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
                view.onLoadMoreView(isHasMore)
            }
        }
    }

    /***
     * Presenter 返回数据加载完成、这个接口要实现数据加载完成后UI的更新
     */
    open fun onLoadFinish() {
        for (view in mViewPool) {
            if (view.lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
                view.onLoadFinish()
            }
        }
    }

    /**
     * 检测View层是否是活动状态，主要是Presenter内部使用、这个接口主要是规范Presenter内的函数
     * @return true 处在活动状态，false 非活动状态
     */
    override fun isViewActive(): Boolean {
        for (view in mViewPool) {
            if (view.lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
                return true
            }
        }
        return false
    }

}