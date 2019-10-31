package com.mbg.module.ui.activity;




import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jaeger.library.StatusBarUtil;
import com.mbg.module.common.util.LocaleUtils;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleUtils.setLocale(this);
    }

    public void setStatusBarColor(int color, int statusBarAlpha){
        StatusBarUtil.setColor(this,color,statusBarAlpha);
    }

}
