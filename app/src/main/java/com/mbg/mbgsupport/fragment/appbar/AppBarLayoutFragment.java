package com.mbg.mbgsupport.fragment.appbar;


import android.content.Context;

import com.mbg.mbgsupport.R;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;


public class AppBarLayoutFragment extends BaseFragment {

    public static void show(Context context){
        TerminalActivity.show(context, AppBarLayoutFragment.class,null);
    }

    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_app_bar;
    }

    @Override
    protected void initView() {

    }
}
