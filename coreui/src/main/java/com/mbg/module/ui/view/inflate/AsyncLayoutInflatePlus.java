package com.mbg.module.ui.view.inflate;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.util.Pools;
import androidx.core.view.LayoutInflaterCompat;

import com.mbg.module.common.core.manager.ThreadPoolManager;



public final class AsyncLayoutInflatePlus {
    private static final String TAG = "AsyncLayoutInflatePlus";
    private final Pools.SynchronizedPool<InflateRequest>  mRequestPool = new Pools.SynchronizedPool<>(10);
    private final LayoutInflater mInflater;
    private final Handler mHandler;

    public AsyncLayoutInflatePlus(@NonNull Context context) {
        mInflater = new BasicInflater(context);
        mHandler = new Handler(mHandlerCallback);
    }

    @UiThread
    public void inflate(@LayoutRes int resId, @Nullable ViewGroup parent, @NonNull OnInflateFinishedListener callback) {
        if (callback == null) {
            throw new NullPointerException("callback argument may not be null!");
        }
        InflateRequest request = obtainRequest();
        request.inflater = this;
        request.resId = resId;
        request.parent = parent;
        request.callback = callback;
        ThreadPoolManager.getInstance().start(new InflateRunnable(request));
    }

    private Handler.Callback mHandlerCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            InflateRequest request = (InflateRequest) msg.obj;
            if (request.view == null) {
                request.view = mInflater.inflate(request.resId, request.parent, false);
            }
            request.callback.onInflateFinished(request.view, request.resId, request.parent);
            releaseRequest(request);
            return true;
        }
    };

    private static class InflateRequest {
        AsyncLayoutInflatePlus inflater;
        ViewGroup parent;
        int resId;
        View view;
        OnInflateFinishedListener callback;

        InflateRequest() {
        }
    }


    private static class BasicInflater extends LayoutInflater {
        private static final String[] sClassPrefixList = {
                "android.widget.",
                "android.webkit.",
                "android.app."
        };

        BasicInflater(Context context) {
            super(context);
            if (context instanceof AppCompatActivity) {
                // 手动setFactory2，兼容AppCompatTextView等控件
                AppCompatDelegate appCompatDelegate = ((AppCompatActivity) context).getDelegate();
                if (appCompatDelegate instanceof Factory2) {
                    LayoutInflaterCompat.setFactory2(this, (Factory2) appCompatDelegate);
                }
            }
        }

        @Override
        public LayoutInflater cloneInContext(Context newContext) {
            return new BasicInflater(newContext);
        }

        @Override
        protected View onCreateView(String name, AttributeSet attrs) throws ClassNotFoundException {
            for (String prefix : sClassPrefixList) {
                try {
                    View view = createView(name, prefix, attrs);
                    if (view != null) {
                        return view;
                    }
                } catch (ClassNotFoundException e) {
                    // In this case we want to let the base class take a crack
                    // at it.
                }
            }

            return super.onCreateView(name, attrs);
        }
    }


    private static class InflateRunnable implements Runnable {
        private final InflateRequest request;
        private boolean isRunning;

        public InflateRunnable(InflateRequest request) {
            this.request = request;
        }

        @Override
        public void run() {
            isRunning = true;
            try {
                request.view = request.inflater.mInflater.inflate(
                        request.resId, request.parent, false);
            } catch (RuntimeException ex) {
                // Probably a Looper failure, retry on the UI thread
                Log.w(TAG, "Failed to inflate resource in the background! Retrying on the UI" + " thread", ex);
            }
            Message.obtain(request.inflater.mHandler, 0, request).sendToTarget();
        }

        public boolean isRunning() {
            return isRunning;
        }
    }


    public InflateRequest obtainRequest() {
        InflateRequest obj = mRequestPool.acquire();
        if (obj == null) {
            obj = new InflateRequest();
        }
        return obj;
    }

    public void releaseRequest(InflateRequest obj) {
        obj.callback = null;
        obj.inflater = null;
        obj.parent = null;
        obj.resId = 0;
        obj.view = null;
        mRequestPool.release(obj);//将空对象加入数组便于复用
    }


    public void cancel() {
        mHandler.removeCallbacksAndMessages(null);
        mHandlerCallback = null;
    }
}
