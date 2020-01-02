package com.mbg.mbgsupport.fragment.seekbar;


import android.content.Context;

import com.mbg.mbgsupport.R;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;


public class VerticalSeekBarFragment extends BaseFragment {

    public static void show(Context context){
        TerminalActivity.show(context, VerticalSeekBarFragment.class,null);
    }

    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_vertical;
    }

    @Override
    protected void initView() {

    }
}
