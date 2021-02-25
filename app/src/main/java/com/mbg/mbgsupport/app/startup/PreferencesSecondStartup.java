package com.mbg.mbgsupport.app.startup;

import android.content.Context;

import com.mbg.module.common.core.manager.ThreadPoolManager;
import com.mbg.module.common.core.sharedpreference.FastSharedPreferences;
import com.mbg.module.common.util.LogUtils;
import com.rousetime.android_startup.AndroidStartup;
import com.rousetime.android_startup.Startup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class PreferencesSecondStartup extends AndroidStartup<Boolean> {
    @Nullable
    @Override
    public Boolean create(@NotNull Context context) {
        FastSharedPreferences.init(context);
        LogUtils.d("create:"+getClass().getSimpleName());
        return true;
    }

    @Nullable
    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        List<Class<? extends Startup<?>>> dependencies=new ArrayList<>();
        dependencies.add(ImageLoaderFirstStartup.class);
        return dependencies;
    }

    @NotNull
    @Override
    public Executor createExecutor() {
        return ThreadPoolManager.get().getMainExecutor();
    }

    @Override
    public void onDependenciesCompleted(@NotNull Startup<?> startup, @Nullable Object result) {
        super.onDependenciesCompleted(startup, result);
        LogUtils.d("onDependenciesCompleted:"+startup.getClass().getSimpleName()+",result="+result);
    }

    @Override
    public boolean callCreateOnMainThread() {
        return false;
    }

    @Override
    public boolean waitOnMainThread() {
        return true;
    }
}
