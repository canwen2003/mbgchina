package com.mbg.module.ui.view.viewPager.holder;


import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class FragmentViewHolder extends SlideViewHolder {
    public Fragment fragment;

    public FragmentViewHolder(ViewGroup v, Fragment fragment) {
        super(v);
        this.fragment = fragment;
    }
}
