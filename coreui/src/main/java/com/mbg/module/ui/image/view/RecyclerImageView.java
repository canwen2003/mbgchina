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
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.mbg.module.ui.R;
import com.mbg.module.ui.image.cache.engine.LoadOptions;
import com.mbg.module.ui.image.cache.engine.factory.DisplayImageOptionsFactory;
import com.mbg.module.ui.image.cache.engine.imageloader.ImageLoader;


public class RecyclerImageView extends AppCompatImageView implements ILoadImage {
    public static final int DEFAULT_RADIUS = 0;
    public static final int DEFAULT_BORDER = 0;
    public static final int DEFAULT_BORDER_COLOR = Color.BLACK;

    private int mCornerRadius;
    private int mBorderWidth;
    private int mBorderColor;
    private boolean roundBackground;

	public RecyclerImageView(Context context) {
        super(context);
        mCornerRadius=DEFAULT_RADIUS;
        mBorderWidth=DEFAULT_BORDER;
        mBorderColor=DEFAULT_BORDER_COLOR;
    }

    public RecyclerImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public RecyclerImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundedImageView, defStyle, 0);

        mCornerRadius = a.getDimensionPixelSize(R.styleable.RoundedImageView_corner, -1);
        mBorderWidth = a.getDimensionPixelSize(R.styleable.RoundedImageView_border_width, -1);
        if (mCornerRadius < 0) {
            mCornerRadius = DEFAULT_RADIUS;
        }
        if (mBorderWidth < 0) {
            mBorderWidth = DEFAULT_BORDER;
        }

        mBorderColor = a.getColor(R.styleable.RoundedImageView_border_color, DEFAULT_BORDER_COLOR);

        roundBackground = a.getBoolean(R.styleable.RoundedImageView_round_background, false);

        a.recycle();
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
            imageLoader.displayImage(uri, this, DisplayImageOptionsFactory.get().getImageOptions(options,mCornerRadius));
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
