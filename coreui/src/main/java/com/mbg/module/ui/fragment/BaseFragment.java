package com.mbg.module.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.jaeger.library.StatusBarUtil;
import com.mbg.module.ui.activity.BaseFragmentActivity;


public abstract class BaseFragment extends Fragment {
    public View mRootView;
    private int mContainer;


    public void showContent(Class<? extends BaseFragment> fragmentClass) {
        showContent(fragmentClass, null);
    }

    public void showContent(Class<? extends BaseFragment> fragmentClass, Bundle bundle) {
        BaseFragmentActivity activity = (BaseFragmentActivity) getActivity();
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

    public final  <T extends View> T findViewById(@IdRes int id) {
        return mRootView.findViewById(id);
    }

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                   @Nullable Bundle savedInstanceState) {
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
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tryGetContainerId();
        try {
            if (view.getContext() instanceof Activity) {
                ((Activity) view.getContext()).getWindow().getDecorView().requestLayout();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tryGetContainerId() {
        if (mRootView != null) {
            View parent = (View) mRootView.getParent();
            if (parent != null) {
                mContainer = parent.getId();
            }
        }
    }

    protected View onCreateView(Bundle savedInstanceState) {
        return mRootView;
    }

    protected boolean interceptTouchEvents() {
        return false;
    }

    public int getContainer() {
        if (mContainer == 0) {
            tryGetContainerId();
        }
        return mContainer;
    }

    public boolean onBackPressed() {
        return false;
    }

    public void finish() {
        BaseFragmentActivity activity = (BaseFragmentActivity) getActivity();
        if (activity != null) {
            activity.doBack(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}