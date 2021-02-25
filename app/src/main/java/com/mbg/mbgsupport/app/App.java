package com.mbg.mbgsupport.app;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.mbg.mbgsupport.app.startup.ImageLoaderFirstStartup;
import com.mbg.mbgsupport.app.startup.SampleFourthStartup;
import com.mbg.mbgsupport.app.startup.PreferencesSecondStartup;
import com.mbg.mbgsupport.app.startup.SampleThirdStartup;
import com.rousetime.android_startup.StartupManager;


public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化启动项
        new StartupManager.Builder()
                .addStartup(new ImageLoaderFirstStartup())
                .addStartup(new PreferencesSecondStartup())
                .addStartup(new SampleThirdStartup())
                .addStartup(new SampleFourthStartup())
                .build(this)
                .start()
                .await();
    }

}
