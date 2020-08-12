package com.mbg.module.ui.mvp.base;


import androidx.lifecycle.LifecycleOwner;



/***
 *
 * 定义基础的MVP 的View层相关的接口
 * created by gap
 */

public interface IView extends LifecycleOwner {

    /***
     *用来返回Presenter处理后返回给View的数据
     * @param resultCode 用来指示该函数的主功能
     * @param params 用来传递功能数据
     */
    void onPresenterResult(int resultCode, Object... params);

    /***
     *
     * 处理加载更多、Presenter告诉View层是否还有更多的数据，这个时候View层可以设置ListView是否可以加载更多
     * @param isHasMore 是否还有数据
     */
    void onLoadMoreView(boolean isHasMore);

    /***
     * Presenter 返回数据加载完成、这个接口要实现数据加载完成后UI的更新
     */
    void onLoadFinish();

}