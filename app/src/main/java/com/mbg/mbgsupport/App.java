package com.mbg.mbgsupport;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.mbg.module.ui.image.cache.common.Md5FileNameGenerator;
import com.mbg.module.ui.image.cache.common.QueueProcessingType;
import com.mbg.module.ui.image.cache.engine.imageloader.ImageLoader;
import com.mbg.module.ui.image.cache.engine.imageloader.ImageLoaderConfiguration;


public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //
        initImageLoader(this);

    }


    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

}
