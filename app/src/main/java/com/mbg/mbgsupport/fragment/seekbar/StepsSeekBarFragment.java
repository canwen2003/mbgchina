package com.mbg.mbgsupport.fragment.seekbar;


import android.content.Context;

import com.mbg.mbgsupport.R;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;


public class StepsSeekBarFragment extends BaseFragment {

    public static void show(Context context){
        TerminalActivity.show(context, StepsSeekBarFragment.class,null);
    }

    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_step;
    }

    @Override
    protected void initView() {

    }
}
