package com.mbg.module.ui.view.drawable;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class LayerBuilder {
    private final LayerDrawable drawable;
    private final List<MarginEntity> mDrawableMargins=new ArrayList<>();
    public LayerBuilder(@NonNull Drawable[] drawables) {
        drawable = new LayerDrawable(drawables);
        for (int i=0;i<drawables.length;i++){
            mDrawableMargins.add(new MarginEntity());
        }
    }

    public static LayerBuilder create(@NonNull Drawable... drawables) {
        return new LayerBuilder(drawables);
    }

    public LayerBuilder marginLeft(int index, int left) {
        mDrawableMargins.get(index).left=left;
        return this;
    }

    public LayerBuilder marginTop(int index, int top) {
        mDrawableMargins.get(index).top=top;
        return this;
    }

    public LayerBuilder marginRight(int index, int right) {
        mDrawableMargins.get(index).right=right;
        return this;
    }

    public LayerBuilder marginBottom(int index, int bottom) {
        mDrawableMargins.get(index).bottom=bottom;
        return this;
    }

    public LayerBuilder setMargin(int index, int left, int top, int right, int bottom) {
        MarginEntity margin=mDrawableMargins.get(index);
        margin.left=left;
        margin.right=right;
        margin.top=top;
        margin.bottom=bottom;
        return this;
    }

    public void build(View view) {
        build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        }else {
            view.setBackgroundDrawable(drawable);
        }
    }

    public Drawable build() {
        MarginEntity margin;
        for (int i=0;i<mDrawableMargins.size();i++){
            margin=mDrawableMargins.get(i);
            drawable.setLayerInset(i, margin.left, margin.top, margin.right, margin.bottom);
        }
        return drawable;
    }

    private static final class MarginEntity{
        private int left;
        private int right;
        private int top;
        private int bottom;
    }
}