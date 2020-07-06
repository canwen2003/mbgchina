package com.mbg.mbgsupport.fragment;

import android.animation.ValueAnimator;
import android.content.Context;

import com.mbg.mbgsupport.R;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.view.layout.shimmer.ShimmerLayout;
import com.mbg.module.ui.view.shimmer.Shimmer;
import com.mbg.module.ui.view.shimmer.ShimmerFrameLayout;


public class ShimmerFragment extends BaseFragment{
    public static void show(Context context){
        TerminalActivity.show(context, ShimmerFragment.class,null);
    }
    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_facebook_shimmer;
    }

    @Override
    protected void initView() {
        ShimmerFrameLayout shimmerLayout = findViewById(R.id.shimmer_layout);
        Shimmer.AlphaHighlightBuilder alphaHighlightBuilder=new Shimmer.AlphaHighlightBuilder();
        //Shimmer.ColorHighlightBuilder alphaHighlightBuilder= new Shimmer.ColorHighlightBuilder();
        alphaHighlightBuilder.setAutoStart(false);
        alphaHighlightBuilder.setDirection(Shimmer.Direction.LEFT_TO_RIGHT);
        alphaHighlightBuilder.setClipToChildren(false);
        alphaHighlightBuilder.setRepeatCount(ValueAnimator.INFINITE);
        alphaHighlightBuilder.setRepeatMode(ValueAnimator.RESTART);
        alphaHighlightBuilder.setBaseAlpha(0.7f);//设置基础View的透明度
        alphaHighlightBuilder.setHighlightAlpha(1.0f);
        alphaHighlightBuilder.setDropoff(0.6f).setTilt(30f);
        alphaHighlightBuilder.setShape(Shimmer.Shape.LINEAR);
        shimmerLayout.setShimmer(alphaHighlightBuilder.build());


        shimmerLayout.startShimmer();
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
