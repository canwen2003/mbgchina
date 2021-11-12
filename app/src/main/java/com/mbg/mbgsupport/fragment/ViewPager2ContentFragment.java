package com.mbg.mbgsupport.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mbg.mbgsupport.R;

import com.mbg.module.common.util.LogUtils;
import com.mbg.module.ui.fragment.BaseFragment;


public class ViewPager2ContentFragment extends BaseFragment {

    TextView textView;
    String mTitle="Default";
    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_slid_demo;
    }

    @Override
    protected void initView() {
        textView=findViewById(R.id.tv_title);
        int color= Color.RED;
        Bundle bundle=getArguments();
        if (bundle!=null){
            mTitle=bundle.getString("titile","");
            color=bundle.getInt("color",Color.GRAY);
        }
        textView.setText(mTitle);

        View rootView=findViewById(R.id.root_view);
        rootView.setBackgroundColor(color);
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.d("OnResume:"+mTitle);
    }
}
