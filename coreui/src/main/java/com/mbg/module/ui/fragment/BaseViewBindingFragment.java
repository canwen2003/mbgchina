package com.mbg.module.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.jaeger.library.StatusBarUtil;
import com.mbg.module.common.util.ClassUtils;
import com.mbg.module.common.util.LogUtils;
import com.mbg.module.ui.activity.BaseViewBindingActivity;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;



public abstract class BaseViewBindingFragment<T extends ViewBinding> extends Fragment {
    private T mViewBinding;

    public void showContent(Class<? extends BaseViewBindingFragment> fragmentClass) {
        showContent(fragmentClass, null);
    }

    public void showContent(Class<? extends BaseViewBindingFragment> fragmentClass, Bundle bundle) {
        BaseViewBindingActivity activity = (BaseViewBindingActivity) getActivity();
        if (activity != null) {
            activity.showContent(fragmentClass, bundle);
        }
    }

    public void setStatusBarColor(int color,int statusBarAlpha){
        StatusBarUtil.setColor(getActivity(),color,statusBarAlpha);
    }

    @LayoutRes
    protected abstract int onRequestLayout();

    protected abstract void initView();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                   @Nullable Bundle savedInstanceState) {
        mViewBinding=getBinding();
        if (mViewBinding==null){
            LogUtils.d("mViewBinding==null");
            return null;
        }
        if (mViewBinding.getRoot()!=null&&mViewBinding.getRoot().getParent() != null) {
            ((ViewGroup) mViewBinding.getRoot().getParent()).removeView(mViewBinding.getRoot());
        }
        initView();
        if (interceptTouchEvents()) {
            if (mViewBinding.getRoot() != null) {
                mViewBinding.getRoot().setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
            }
        }
        return mViewBinding.getRoot();
    }

    protected boolean interceptTouchEvents() {
        return false;
    }


    public boolean onBackPressed() {
        return false;
    }

    public void finish() {
        BaseViewBindingActivity activity = (BaseViewBindingActivity) getActivity();
        if (activity != null) {
            activity.doBack(this);
        }
    }


    protected T getBinding(){
        try {
            Type superClass = getClass().getGenericSuperclass();
            Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
            Class<?> clazz = ClassUtils.getRawType(type);
            Method method = clazz.getMethod("inflate", LayoutInflater.class);
            return (T) method.invoke(null, getLayoutInflater());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewBinding=null;
    }
}