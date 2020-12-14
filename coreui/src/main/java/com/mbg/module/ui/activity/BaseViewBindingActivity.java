package com.mbg.module.ui.activity;




import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewbinding.ViewBinding;

import com.jaeger.library.StatusBarUtil;
import com.mbg.module.common.util.ClassUtils;
import com.mbg.module.common.util.LocaleUtils;
import com.mbg.module.ui.fragment.BaseViewBindingFragment;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayDeque;

public abstract class BaseViewBindingActivity<T extends ViewBinding> extends AppCompatActivity {
    protected T mViewBinding;
    protected static final String ARG_FRAGMENT_CLASS_NAME="arg_fragment_class_name";
    protected static final String ARG_FRAGMENT_ARGS="arg_fragment_args";
    private final ArrayDeque<BaseViewBindingFragment> mFragments = new ArrayDeque<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleUtils.setLocale(this);
        mViewBinding=getBinding();
        setContentView(mViewBinding.getRoot());

        Intent intent=getIntent();
        Class<? extends BaseViewBindingFragment> fragmentClass=null;
        Bundle bundle=null;
        if (intent!=null){

            String fragmentName=intent.getStringExtra(ARG_FRAGMENT_CLASS_NAME);
            if (fragmentName!=null){
                try {
                    fragmentClass=(Class<? extends BaseViewBindingFragment>)getClassLoader().loadClass(fragmentName);
                }catch (Exception e){
                    e.printStackTrace();
                    finish();
                }
            }

            bundle=intent.getBundleExtra(ARG_FRAGMENT_ARGS);
        }

        if (fragmentClass!=null) {
            showContent(fragmentClass, bundle);
        }

        initView();
    }

    public abstract void initView();

    public void setStatusBarColor(int color, int statusBarAlpha){
        StatusBarUtil.setColor(this,color,statusBarAlpha);
    }

    public void showContent(Class<? extends BaseViewBindingFragment> target) {
        showContent(target, null);
    }

    public void showContent(Class<? extends BaseViewBindingFragment> target, Bundle bundle) {
        try {
            BaseViewBindingFragment fragment = target.newInstance();
            if (bundle != null) {
                fragment.setArguments(bundle);
            }
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.add(android.R.id.content, fragment);
            mFragments.push(fragment);
            fragmentTransaction.addToBackStack("");
            fragmentTransaction.commit();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (!mFragments.isEmpty()) {
            BaseViewBindingFragment fragment = mFragments.getFirst();
            if (!fragment.onBackPressed()) {
                mFragments.removeFirst();
                super.onBackPressed();
                if (mFragments.isEmpty()) {
                    finish();
                }
            }
        } else {
            super.onBackPressed();
        }
    }

    public void doBack(BaseViewBindingFragment fragment) {
        if (mFragments.contains(fragment)) {
            mFragments.remove(fragment);
            FragmentManager fm = getSupportFragmentManager();
            fm.popBackStack();
            if (mFragments.isEmpty()) {
                finish();
            }
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
    protected void onDestroy() {
        super.onDestroy();
        mViewBinding=null;
    }
}
