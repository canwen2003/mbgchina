package com.mbg.module.ui.mvp;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;


import com.mbg.module.common.util.LogUtils;
import com.mbg.module.ui.mvp.base.IView;
import com.mbg.module.ui.mvp.base.IPresenter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * 定义基础具有缓存功能的Presenter
 * created by Gap
 */
public abstract class MvpPresenter<T extends IView> implements IPresenter {
    protected Context mContext;
    private String presenterId;
    private Set<T> mViewPool= Collections.synchronizedSet(new HashSet<T>());

    /**
     * 初始化Presenter
     * @param  context Activity或Application的context
     *
     */
    @Override
    public void init(@NonNull Context context) {
        this.mContext=context;
        presenterId=getClass().getSimpleName();
    }

    /***
     * 注册view 对象
     * @param view view对象
     */
    @Override
    public void register(@NonNull IView view, @Nullable Bundle params) {
       final T  mvpView=(T)view;
        mViewPool.add(mvpView);

        mvpView.getLifecycle().addObserver(new LifecycleObserver() {
            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            public void onOwnerStart() {
                LogUtils.d("onOwnerStart ： "+mvpView.toString());
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            public void onOwnerDestroy() {
                LogUtils.d("onOwnerDestroy ："+mvpView.toString());
            }
        });
    }

    @Override
    public void unRegister(@NonNull IView view) {
        if (mViewPool.contains((view))){
            mViewPool.remove(view);
        }
    }

    @Override
    public void requestData(int requestCode, @Nullable Object... params) {

    }

    public String getPresenterId(){
        return getPresenterId();
    }

    public void setPresenterId(String presenterId){
        this.presenterId=presenterId;
    }

    /***
     *
     * 获取已经缓存的数据的大小：通过这个接口可以判断是否Presenter是否有Cache数据和cache数据的大小
     * @return 缓存的数据大小
     */
    @Override
    public int getCachedDataSize() {
        return 0;
    }

    /****
     *
     * 获取已经缓存的数据，本地内存缓存
     */
    @Override
    public void getCachedData() {

    }

    /****
     *
     * 清除缓存中的所有数据
     */
    @Override
    public void clearCachedData() {

    }

    /****
     *
     *  @param item 要清除的item
     * 清除缓存中的数据
     */
    @Override
    public void clearCachedData(Object item) {

    }

    /***
     *用来返回Presenter处理后返回给View的数据
     * @param resultCode 用来指示该函数的主功能
     * @param params 用来传递功能数据
     */
    void onPresenterResult(int resultCode, Object... params){
        for (T view:mViewPool) {
            if (view.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED)){
               view.onPresenterResult(resultCode,params);
            }
        }
    }

    /***
     *
     * 处理加载更多、Presenter告诉View层是否还有更多的数据，这个时候View层可以设置ListView是否可以加载更多
     * @param isHasMore 是否还有数据
     */
    void onLoadMoreView(boolean isHasMore){
        for (T view:mViewPool) {
            if (view.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED)){
                view.onLoadMoreView(isHasMore);
            }
        }
    }

    /***
     * Presenter 返回数据加载完成、这个接口要实现数据加载完成后UI的更新
     */
    void onLoadFinish(){
        for (T view:mViewPool) {
            if (view.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED)){
                view.onLoadFinish();
            }
        }
    }

    /**
     * 检测View层是否是活动状态，主要是Presenter内部使用、这个接口主要是规范Presenter内的函数
     * @return true 处在活动状态，false 非活动状态
     */
    @Override
    public boolean isViewActive() {
        for (T view:mViewPool) {
            if (view.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED)){
                return true;
            }
        }
        return false;
    }
}
