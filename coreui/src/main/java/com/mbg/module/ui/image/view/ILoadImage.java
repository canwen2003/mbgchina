package com.mbg.module.ui.image.view;

import android.support.annotation.NonNull;

import com.mbg.module.ui.image.cache.engine.LoadOptions;

public interface ILoadImage {
    void loadImage(String uri);
    void loadImage(String uri, @NonNull LoadOptions options);
}
