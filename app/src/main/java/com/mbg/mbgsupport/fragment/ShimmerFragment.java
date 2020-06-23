package com.mbg.mbgsupport.fragment;

import android.content.Context;

import com.mbg.mbgsupport.R;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.view.layout.shimmer.ShimmerLayout;


public class ShimmerFragment extends BaseFragment{
    public static void show(Context context){
        TerminalActivity.show(context, ShimmerFragment.class,null);
    }
    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_shimmer;
    }

    @Override
    protected void initView() {
        ShimmerLayout shimmerLayout = findViewById(R.id.shimmer_layout);
        shimmerLayout.startShimmerAnimation();
    }

    @Override
    public void onResume() {
        super.onResume();
        super.onResume();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
