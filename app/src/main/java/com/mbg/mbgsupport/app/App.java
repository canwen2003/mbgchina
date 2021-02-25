package com.mbg.mbgsupport.app;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.mbg.mbgsupport.app.startup.ImageLoaderFirstStartup;
import com.mbg.mbgsupport.app.startup.SampleFourthStartup;
import com.mbg.mbgsupport.app.startup.PreferencesSecondStartup;
import com.mbg.mbgsupport.app.startup.SampleThirdStartup;
import com.mbg.module.common.util.LogUtils;
import com.rousetime.android_startup.StartupListener;
import com.rousetime.android_startup.StartupManager;
import com.rousetime.android_startup.model.CostTimesModel;
import com.rousetime.android_startup.model.LoggerLevel;
import com.rousetime.android_startup.model.StartupConfig;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        StartupConfig startupConfig = new StartupConfig.Builder()
                .setAwaitTimeout(12000)
                .setLoggerLevel(LoggerLevel.DEBUG)
                .setListener(new StartupListener() {
            @Override
            public void onCompleted(long totalMainThreadCostTime, @NotNull List<CostTimesModel> list) {
                LogUtils.d("totalMainThreadCostTime:"+totalMainThreadCostTime);
                for (CostTimesModel costTimesModel :list) {
                    LogUtils.d("getName=" + costTimesModel.getName());
                    LogUtils.d("getCallOnMainThread=" + costTimesModel.getCallOnMainThread());
                    LogUtils.d("getWaitOnMainThread=" + costTimesModel.getWaitOnMainThread());
                    LogUtils.d("getStartTime=" + costTimesModel.getStartTime());
                    LogUtils.d("getEndTime=" + costTimesModel.getEndTime()+"\r\n");
                }
            }
        }).build();
        //初始化启动项
        new StartupManager.Builder()
                .addStartup(new ImageLoaderFirstStartup())
                .addStartup(new PreferencesSecondStartup())
                .addStartup(new SampleThirdStartup())
                .addStartup(new SampleFourthStartup())
                .setConfig(startupConfig)
                .build(this)
                .start()
                .await();
    }

}
