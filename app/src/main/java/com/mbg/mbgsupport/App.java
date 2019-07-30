package com.mbg.mbgsupport;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.multidex.MultiDex;

import com.mbg.module.common.util.StorageUtils;
import com.mbg.module.common.util.UiUtils;
import com.mbg.module.ui.image.cache.common.HashCodeFileNameGenerator;
import com.mbg.module.ui.image.cache.common.ImageScaleType;
import com.mbg.module.ui.image.cache.common.QueueProcessingType;
import com.mbg.module.ui.image.cache.decode.BaseImageDecoder;
import com.mbg.module.ui.image.cache.disk.UnlimitedDiskCache;
import com.mbg.module.ui.image.cache.display.SimpleBitmapDisplayer;
import com.mbg.module.ui.image.cache.download.BaseImageDownloader;
import com.mbg.module.ui.image.cache.engine.DisplayImageOptions;
import com.mbg.module.ui.image.cache.engine.imageloader.ImageLoader;
import com.mbg.module.ui.image.cache.engine.imageloader.ImageLoaderConfiguration;
import com.mbg.module.ui.image.cache.memory.impl.LruMemoryCache;

import java.io.File;


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


     /*   ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
*/

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.icon_common_loading) // resource or drawable
                .showImageForEmptyUri(R.drawable.icon_common_empty) // resource or drawable
                .showImageOnFail(R.drawable.icon_common_failed) // resource or drawable
                .resetViewBeforeLoading(false)  // default
                .delayBeforeLoading(1000)
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                //.preProcessor(...)
		//.postProcessor(...)
		//.extraForDownloader(...)
		.considerExifParams(false) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
               // .decodingOptions(...)
		.displayer(new SimpleBitmapDisplayer()) // default
                .handler(new Handler()) // default
                .build();


        File cacheDir = StorageUtils.getCacheDirectory(context);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(UiUtils.getScreenWidth(context), UiUtils.getScreenHeight(context)) // default = device screen dimensions
                .diskCacheExtraOptions(UiUtils.getScreenWidth(context), UiUtils.getScreenHeight(context), null)
                //.taskExecutor(...)
		//.taskExecutorForCachedImages(...)
		        .threadPoolSize(3) // default
                .threadPriority(Thread.NORM_PRIORITY - 2) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .diskCache(new UnlimitedDiskCache(cacheDir)) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(context)) // default
                .imageDecoder(new BaseImageDecoder(true)) // default
                .defaultDisplayImageOptions(options) // default
                .writeDebugLogs()
                .build();

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

}
