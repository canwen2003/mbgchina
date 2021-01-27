package com.mbg.module.ui.view.inflate;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface OnInflateFinishedListener {
    void onInflateFinished(@NonNull View view, @LayoutRes int resId, @Nullable ViewGroup parent);
}
