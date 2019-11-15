package com.mbg.module.common.util;

import android.view.View;

import androidx.core.view.ViewCompat;

import com.mbg.module.common.util.config.ShadowConfig;

public final class ShadowUtils {

    public static void setViewShadow(View view, ShadowConfig.Builder config) {
        //关闭硬件加速
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        ViewCompat.setBackground(view, config.builder());
    }
}
