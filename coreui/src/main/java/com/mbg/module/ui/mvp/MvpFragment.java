package com.mbg.module.ui.mvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.mvp.base.IView;
import com.mbg.module.ui.mvp.holder.PresenterHolder;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class MvpFragment<T extends MvpPresenter<? extends IView>> extends BaseFragment implements IView {
    private final String TAG = "_MVP_" + MvpFragment.this.getClass().getSimpleName();
    private boolean mIsVisibleToUser = false;
    private boolean mViewInitialized = false;
    private boolean mMvpRegistered = false;
    protected T mPresenter;
    private Unbinder mBinder;



    protected void onCreateConfigured() {}


    @CallSuper
    protected void onInitView(@Nullable Bundle savedInstanceState) {
        mBinder = ButterKnife.bind(this, mRootView);
    }

    @CallSuper
    protected void onReleaseView() {
        mIsVisibleToUser = false;

        mRootView = null;
        mViewInitialized = false;

        if (mBinder != null) {
            mBinder.unbind();
            mBinder = null;
        }
    }

    protected String getPresenterId() {
        Class presenterClass = getPresenterClass();
        if (presenterClass != null) {
            return presenterClass.getSimpleName() + "_" + System.currentTimeMillis();
        } else {
            return getClass().getSimpleName()+ "_" + System.currentTimeMillis();
        }

    }


    protected T onCreatePresenter() {
        String presenterId = getPresenterId();
        T mvpPresenter = (T) PresenterHolder.get().getPresenter(presenterId);
        if (mvpPresenter == null) {
            Class<T> clazz = getPresenterClass();
            if (clazz != null) {
                try {
                    mvpPresenter = clazz.newInstance();
                    mvpPresenter.setPresenterId(presenterId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (mvpPresenter != null) {
                PresenterHolder.get().addPresenter(mvpPresenter, this.getActivity().getLifecycle());
            }
        }

        return mvpPresenter;
    }

    private Class<T> getPresenterClass() {
        try {
            // 通过反射获取model的真实类型
            Type superClass = this.getClass().getGenericSuperclass();
            if (superClass instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
                Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];
                return clazz;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return null;
    }


    @NonNull
    public T getPresenter() {
        return mPresenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, " onCreate()");
        onCreateConfigured();
        mPresenter = onCreatePresenter();
        if (mPresenter != null) {
            mPresenter.init(getActivity());
        }
    }


    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView=super.onCreateView(inflater,container,savedInstanceState);
        int id = onRequestLayout();
        if (id > 0&&mRootView==null) {
            mRootView = inflater.inflate(id, container, false);
            initView();
        }
        if (mRootView == null) {
            mRootView = onCreateView(savedInstanceState);
            initView();
        }else if (mRootView.getParent()!=null){
            ((ViewGroup) mRootView.getParent()).removeView(mRootView);
        }
        if (interceptTouchEvents()) {
            if (mRootView != null) {
                mRootView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
            }
        }
        return mRootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.v(TAG, " onViewCreated()");
        if (!mViewInitialized) {
            onInitView(savedInstanceState);
            mViewInitialized = true;
        }
        if (!mMvpRegistered) {
            mMvpRegistered = true;
            if (mPresenter != null) {
                mPresenter.register(this,getArguments());
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (!onCacheView()) {
            // MVP解注册
            if (mPresenter != null) {
                mPresenter.unRegister(this);
            }
            onReleaseView();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisibleToUser = isVisibleToUser;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, " onDestroy()");
        mMvpRegistered = false;
    }


    /**
     * 是否缓存View对象
     */
    protected boolean onCacheView() {
        return false;
    }


    /**
     * finish属主Activity
     */
    public final void finish() {
        Activity activity = getActivity();
        if (activity != null) {
            activity.finish();
        }
    }

    /**
     * 对属主Activity设置Result
     * @param resultCode
     */
    public final void setResult(int resultCode) {
        Activity activity = getActivity();
        if (activity != null) {
            activity.setResult(resultCode);
        }
    }

    /**
     * 对属主Activity设置Result
     * @param resultCode
     * @param data
     */
    public final void setResult(int resultCode, Intent data) {
        Activity activity = getActivity();
        if (activity != null) {
            activity.setResult(resultCode, data);
        }
    }


    // 判断父fragment是否可见
    private boolean isParentVisible() {
        Fragment fragment = getParentFragment();
        return !(fragment instanceof MvpFragment) ||  (((MvpFragment<T>) fragment).mIsVisibleToUser);
    }

}
