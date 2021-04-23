package com.mbg.module.ui.mvp.holder;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;


import com.mbg.module.ui.mvp.MvpPresenter;
import com.mbg.module.ui.mvp.base.IView;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Presenter管理类，用来保存presenter
 */
public final class PresenterHolder {
    private static volatile PresenterHolder holder;
    private final Map<String, MvpPresenter<? extends IView>> pool;//存储Presenter

    private PresenterHolder() {
        pool = new ConcurrentHashMap<>();
    }

    public static PresenterHolder get() {
        if (holder == null) {
            synchronized (PresenterHolder.class) {
                if (holder == null) {
                    holder = new PresenterHolder();
                }
            }
        }

        return holder;
    }


    /**
     * 保存presenter
     *
     * @param presenter p
     * @param lifecycle l
     */
    public void addPresenter(final MvpPresenter<? extends IView> presenter, final Lifecycle lifecycle) {

        if (pool.containsKey(presenter.getPresenterId())) {
            pool.remove(presenter.getPresenterId());
        }
        pool.put(presenter.getPresenterId(), presenter);

        lifecycle.addObserver(new LifecycleObserver() {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            public void onDestroy() {
                pool.remove(presenter.getPresenterId());
            }
        });

    }

    /**
     * 获取presenter
     *
     * @param presenterId id
     */
    public MvpPresenter<? extends IView> getPresenter(final String presenterId) {
        return pool.get(presenterId);
    }
}
