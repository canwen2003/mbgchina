package com.mbg.mbgsupport.app.startup;

import android.content.Context;

import com.mbg.module.common.util.LogUtils;
import com.rousetime.android_startup.AndroidStartup;
import com.rousetime.android_startup.Startup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SampleFourthStartup extends AndroidStartup<Object> {
    @Nullable
    @Override
    public Object create(@NotNull Context context) {
        LogUtils.d("create:"+getClass().getSimpleName());
        return null;
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
        dependencies.add(SampleThirdStartup.class);
        return dependencies;
    }
}
