package com.mbg.mbgsupport.fragment;

import android.content.Context;

import com.mbg.mbgsupport.R;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;

public class SupperButtonFragment extends BaseFragment{



    public static void show(Context context){
        TerminalActivity.show(context, SupperButtonFragment.class,null);
    }
    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_super_button;
    }

    @Override
    protected void initView() {


        initData();
    }


    private void initData() {


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
