package com.mbg.module.ui.view.viewPager.transformer;

import android.view.View;

import com.mbg.module.common.util.LogUtils;

/**
 * 缩放
 */
public class PageTransformerTandem extends BasePageTransformer {
    private float mMaxScale = 0.75f;

    public PageTransformerTandem() {
    }
    public PageTransformerTandem(float maxRotation) {
        setMaxAlpha(maxRotation);
    }

    @Override
    public void touch2Left(View view, float position) {

        position =1-Math.abs(position)<mMaxScale?mMaxScale:1-Math.abs(position);
            view.setScaleX(position);
            view.setScaleY(position);

    }

    @Override
    public void touch2Right(View view, float position) {

        position =1-Math.abs(position)<mMaxScale?mMaxScale:1-Math.abs(position);
        view.setScaleX(position);
        view.setScaleY(position);
    }

    @Override
    public void other(View view, float position) {
        view.setScaleX(mMaxScale);
        view.setScaleY(mMaxScale);
        LogUtils.d("other="+position);
    }

    public void setMaxAlpha(float mMaxScale) {
        if (mMaxScale >= 0.0f && mMaxScale <= 1.0f) {
            this.mMaxScale = mMaxScale;
        }
    }

}