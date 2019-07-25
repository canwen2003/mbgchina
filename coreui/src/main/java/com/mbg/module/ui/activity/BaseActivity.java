package com.mbg.module.ui.activity;


import android.support.v7.app.AppCompatActivity;

import com.jaeger.library.StatusBarUtil;

public abstract class BaseActivity extends AppCompatActivity {

    public void setStatusBarColor(int color,int statusBarAlpha){
        StatusBarUtil.setColor(this,color,statusBarAlpha);
    }

}
