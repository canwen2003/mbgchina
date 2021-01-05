package com.mbg.mbgsupport.fragment;

import android.content.Context;

import com.mbg.mbgsupport.R;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;

public class ConstraintLayoutFragment extends BaseFragment{


    public static void show(Context context){
        TerminalActivity.show(context, ConstraintLayoutFragment.class,null);
    }
    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_constrainst_layout;
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
