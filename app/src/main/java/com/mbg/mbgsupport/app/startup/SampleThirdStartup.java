package com.mbg.mbgsupport.app.startup;

import android.content.Context;

import com.mbg.module.common.util.LogUtils;
import com.rousetime.android_startup.AndroidStartup;
import com.rousetime.android_startup.Startup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SampleThirdStartup  extends AndroidStartup<Long> {
    @Nullable
    @Override
    public Long create(@NotNull Context context) {
        LogUtils.d("create:"+getClass().getSimpleName());
        return 20L;
    }

    @Override
    public boolean callCreateOnMainThread() {
        return false;
    }

    @Override
    public boolean waitOnMainThread() {
        return false;
    }

    @Nullable
    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        List<Class<? extends Startup<?>>> dependencies=new ArrayList<>();
        dependencies.add(ImageLoaderFirstStartup.class);
        dependencies.add(PreferencesSecondStartup.class);
        return dependencies;
    }

    @Override
    public void onDependenciesCompleted(@NotNull Startup<?> startup, @Nullable Object result) {
        super.onDependenciesCompleted(startup, result);
        LogUtils.d("onDependenciesCompleted:"+startup.getClass().getSimpleName()+",result="+result);
    }
}
