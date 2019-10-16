package com.mbg.module.ui.view.viewPager.holder;

import android.support.v4.app.Fragment;
import android.view.ViewGroup;

public class FragmentViewHolder extends SlideViewHolder {
    public Fragment fragment;

    public FragmentViewHolder(ViewGroup v, Fragment fragment) {
        super(v);
        this.fragment = fragment;
    }
}
