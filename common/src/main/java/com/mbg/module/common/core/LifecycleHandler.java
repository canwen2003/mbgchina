package com.mbg.module.common.core;


import android.os.Looper;
import android.os.Handler;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;


public final class LifecycleHandler extends Handler implements LifecycleObserver {
    private LifecycleOwner mLifecycleOwner;

    public LifecycleHandler(final LifecycleOwner lifecycleOwner) {
        this.mLifecycleOwner = lifecycleOwner;
        addObserver();
    }

    public LifecycleHandler(final Callback callback, final LifecycleOwner lifecycleOwner) {
        super(callback);
        this.mLifecycleOwner = lifecycleOwner;
        addObserver();
    }

    public LifecycleHandler(final Looper looper, final LifecycleOwner lifecycleOwner) {
        super(looper);
        this.mLifecycleOwner = lifecycleOwner;
        addObserver();
    }

    public LifecycleHandler(final Looper looper, final Callback callback, final LifecycleOwner lifecycleOwner) {
        super(looper, callback);
        this.mLifecycleOwner = lifecycleOwner;
        addObserver();
    }

    private void addObserver() {
        if (mLifecycleOwner!=null) {
            mLifecycleOwner.getLifecycle().addObserver(this);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onDestroy() {
        removeCallbacksAndMessages(null);
        if (mLifecycleOwner!=null) {
            mLifecycleOwner.getLifecycle().removeObserver(this);
        }
    }

}
