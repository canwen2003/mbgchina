package com.mbg.module.ui.view.viewPager.transformer;

import android.view.View;

import com.mbg.module.common.util.LogUtils;

/**
 * 外侧3D旋转
 */
public class PageTransformer extends BasePageTransformer {

    public PageTransformer() { }

    @Override
    public void touch2Left(View view, float position) {
        //设置旋转中心点

    }

    @Override
    public void touch2Right(View view, float position) {
        //设置旋转中心点
    }

    @Override
    public void other(View view, float position) {
        LogUtils.d("other="+position);
    }

}