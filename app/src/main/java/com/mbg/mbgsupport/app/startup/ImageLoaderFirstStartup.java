package com.mbg.mbgsupport.app.startup;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.mbg.mbgsupport.R;
import com.mbg.module.common.core.manager.ThreadPoolManager;
import com.mbg.module.common.util.LogUtils;
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
import com.rousetime.android_startup.AndroidStartup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class ImageLoaderFirstStartup extends AndroidStartup<String> {

    @Nullable
    @Override
    public String create(@NotNull Context context) {
        LogUtils.d("create:"+getClass().getSimpleName());
        initImageLoader(context);
        return ImageLoaderFirstStartup.class.getSimpleName();
    }

    @Override
    public boolean callCreateOnMainThread() {
        return true;
    }

    @Override
    public boolean waitOnMainThread() {
        return false;
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.

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
                .memoryCacheExtraOptions(UiUtils.getScreenWidth(context), UiUtils.getScreenHeight(context)) // 即保存的每个缓存文件的最大长宽
                .diskCacheExtraOptions(UiUtils.getScreenWidth(context), UiUtils.getScreenHeight(context), null)//
                .taskExecutor(ThreadPoolManager.get().getMainExecutor())
                .taskExecutorForCachedImages(ThreadPoolManager.get().getMainExecutor())
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()////拒绝缓存多个图片。
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))////缓存策略你可以通过自己的内存缓存实现 ，这里用弱引用，缺点是太容易被回收了，不是很好！
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