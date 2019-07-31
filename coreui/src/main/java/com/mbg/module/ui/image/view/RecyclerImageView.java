/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mbg.module.ui.image.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.mbg.module.ui.image.cache.engine.LoadOptions;
import com.mbg.module.ui.image.cache.engine.factory.DisplayImageOptionsFactory;
import com.mbg.module.ui.image.cache.engine.imageloader.ImageLoader;


public class RecyclerImageView extends AppCompatImageView implements ILoadImage {
	public RecyclerImageView(Context context) {
        super(context);
    }

    public RecyclerImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public RecyclerImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
    }

    /**
     * @see ImageView#onDetachedFromWindow()
     */
    @Override
    protected void onDetachedFromWindow() {
    	if (getDrawable() instanceof IRecyclerDrawable) {
    		setImageDrawable(null);
    	}

        super.onDetachedFromWindow();
    }
    /**
     * @see ImageView#setImageDrawable(Drawable)
     */
    @Override
	public void setImageDrawable(Drawable drawable) {
		// Keep hold of previous Drawable
		final Drawable previousDrawable = getDrawable();

		super.setImageDrawable(drawable);

		notifyDrawable(drawable, true);
		notifyDrawable(previousDrawable, false);
	}

    /**
     * Notifies the drawable that it's displayed state has changed.
     */
    private static void notifyDrawable(Drawable drawable, final boolean isDisplayed) {
        if (drawable instanceof IRecyclerDrawable) {
            IRecyclerDrawable recyclingDrawable = (IRecyclerDrawable)drawable;
            recyclingDrawable.displayed(isDisplayed);
        } else if (drawable instanceof LayerDrawable) {
            LayerDrawable layerDrawable = (LayerDrawable) drawable;
            for (int i = 0, z = layerDrawable.getNumberOfLayers(); i < z; i++) {
                notifyDrawable(layerDrawable.getDrawable(i), isDisplayed);
            }
        }
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
    }

    @Override
    public void loadImage(String uri) {
        ImageLoader imageLoader=ImageLoader.getInstance();
        if (imageLoader!=null) {
            imageLoader.displayImage(uri, this);
        }
    }

    @Override
    public void loadImage(String uri, @NonNull LoadOptions options) {
        //如果为空直接显示默认图片
        if (TextUtils.isEmpty(uri)){
            setImageResource(options.getDefaultImageResId());
            return;
        }

        ImageLoader imageLoader=ImageLoader.getInstance();
        if (imageLoader!=null) {
            imageLoader.displayImage(uri, this, DisplayImageOptionsFactory.get().getImageOptions(options));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
    	try {
    		super.onDraw(canvas);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

}
