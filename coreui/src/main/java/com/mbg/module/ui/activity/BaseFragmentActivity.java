package com.mbg.module.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.mbg.module.ui.fragment.BaseFragment;

import java.util.ArrayDeque;


public abstract class BaseFragmentActivity extends BaseActivity {

    protected static final String ARG_FRAGMENT_CLASS_NAME="arg_fragment_class_name";
    protected static final String ARG_FRAGMENT_ARGS="arg_fragment_args";
    private ArrayDeque<BaseFragment> mFragments = new ArrayDeque<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent=getIntent();

        Class<? extends BaseFragment> fragmentClass=null;
        Bundle bundle=null;
        if (intent!=null){

            String fragmentName=intent.getStringExtra(ARG_FRAGMENT_CLASS_NAME);
            if (fragmentName!=null){
                try {
                    fragmentClass=(Class<? extends BaseFragment>)getClassLoader().loadClass(fragmentName);
                }catch (Exception e){
                    e.printStackTrace();
                    finish();
                }
            }

            bundle=intent.getBundleExtra(ARG_FRAGMENT_ARGS);
        }

        if (fragmentClass!=null) {
            showContent(fragmentClass, bundle);
        }else {
            finish();
        }
    }

    public void showContent(Class<? extends BaseFragment> target) {
        showContent(target, null);
    }

    public void showContent(Class<? extends BaseFragment> target, Bundle bundle) {
        try {
            BaseFragment fragment = target.newInstance();
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
            BaseFragment fragment = mFragments.getFirst();
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

    public void doBack(BaseFragment fragment) {
        if (mFragments.contains(fragment)) {
            mFragments.remove(fragment);
            FragmentManager fm = getSupportFragmentManager();
            fm.popBackStack();
            if (mFragments.isEmpty()) {
                finish();
            }
        }
    }
}
