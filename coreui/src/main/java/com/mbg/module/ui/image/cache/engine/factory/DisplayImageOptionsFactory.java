package com.mbg.module.ui.image.cache.engine.factory;

import android.graphics.Bitmap;
import android.os.Handler;

import com.mbg.module.ui.image.cache.common.BitmapDisplayer;
import com.mbg.module.ui.image.cache.common.ImageScaleType;
import com.mbg.module.ui.image.cache.display.SimpleBitmapDisplayer;
import com.mbg.module.ui.image.cache.engine.DisplayImageOptions;
import com.mbg.module.ui.image.cache.engine.LoadOptions;

public class DisplayImageOptionsFactory {
    private static volatile DisplayImageOptionsFactory instance;
    private Handler mLoadingHandler;
    private Bitmap.Config mBitmapConfig;
    private BitmapDisplayer mBitmapDisplayer;

    private DisplayImageOptionsFactory(){
        mLoadingHandler=new Handler();
        mBitmapConfig=Bitmap.Config.ARGB_8888;
        mBitmapDisplayer=new SimpleBitmapDisplayer();
    }

    public static DisplayImageOptionsFactory get(){
        if (instance==null){
            synchronized (DisplayImageOptionsFactory.class){
                if (instance==null){
                    instance=new DisplayImageOptionsFactory();
                }
            }
        }

        return instance;
    }


    public DisplayImageOptions getImageOptions(LoadOptions options){
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(options.getDefaultImageResId())
                .showImageForEmptyUri(options.getDefaultImageResId())
                .showImageOnFail(options.getImageOnFail())
                .resetViewBeforeLoading(false)
                .delayBeforeLoading(0)
                .cacheInMemory(options.isEnableCache())
                .cacheOnDisk(options.isEnableCache()) // default
                .considerExifParams(false) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                .bitmapConfig(mBitmapConfig) // default
                .displayer(mBitmapDisplayer) // default
                .handler(mLoadingHandler) // default
                .build();
    }
}
