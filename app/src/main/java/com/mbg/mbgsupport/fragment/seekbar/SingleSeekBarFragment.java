package com.mbg.mbgsupport.fragment.seekbar;


import android.content.Context;

import com.mbg.mbgsupport.R;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;


public class SingleSeekBarFragment extends BaseFragment {

    public static void show(Context context){
        TerminalActivity.show(context, SingleSeekBarFragment.class,null);
    }

    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_single;
    }

    @Override
    protected void initView() {

    }
}
