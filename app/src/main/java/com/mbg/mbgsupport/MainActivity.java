package com.mbg.mbgsupport;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mbg.mbgsupport.databinding.ActivityMainBinding;
import com.mbg.mbgsupport.fragment.AnimsFragment;
import com.mbg.mbgsupport.fragment.ConstraintLayoutFragment;
import com.mbg.mbgsupport.fragment.ShapeFragment;
import com.mbg.mbgsupport.fragment.ShimmerFragment;
import com.mbg.mbgsupport.fragment.SkeletonFragment;
import com.mbg.mbgsupport.fragment.appbar.AppBarLayoutFragment;
import com.mbg.mbgsupport.fragment.BigImageLoaderFragment;
import com.mbg.mbgsupport.fragment.BubbleViewFragment;
import com.mbg.mbgsupport.fragment.DragFragment;
import com.mbg.mbgsupport.fragment.FlowLayoutFragment;
import com.mbg.mbgsupport.fragment.ImageLoaderFragment;
import com.mbg.mbgsupport.fragment.PuddingFragment;
import com.mbg.mbgsupport.fragment.SlidingFragment;
import com.mbg.mbgsupport.fragment.SnapShotFragment;
import com.mbg.mbgsupport.fragment.SupperButtonFragment;
import com.mbg.mbgsupport.fragment.SystemFlowLayoutFragment;
import com.mbg.mbgsupport.fragment.TextBannerFragment;
import com.mbg.mbgsupport.fragment.TimeLineFragment;
import com.mbg.mbgsupport.fragment.UtilsDemoFragment;
import com.mbg.mbgsupport.fragment.ViewPager2Fragment;
import com.mbg.mbgsupport.fragment.seekbar.SeekBarFragment;
import com.mbg.mbgsupport.fragment.tab.CommonTabFragment;
import com.mbg.mbgsupport.fragment.tab.SegmentTabFragment;
import com.mbg.mbgsupport.fragment.tab.SlidingTabFragment;
import com.mbg.mbgsupport.viewmodel.LoadingStateViewModel;
import com.mbg.module.common.util.LogUtils;
import com.mbg.module.ui.activity.BaseViewBindingActivity;

public class MainActivity extends BaseViewBindingActivity<ActivityMainBinding> {

    private Context context;

    protected void  onDataLoadingStart(){
        LogUtils.d("LoadingStateViewModel:onDataLoadingStart");
    }

    protected void  onDataLoadingFinish(){
        LogUtils.d("LoadingStateViewModel:onDataLoadingFinish");
    }

    @Override
    public void initView(){
        context=this;
        new ViewModelProvider(this).get(LoadingStateViewModel.class).getLoadingState().observe(this, new Observer<LoadingStateViewModel.LoadingState>() {
            @Override
            public void onChanged(LoadingStateViewModel.LoadingState loadingState) {
                if (loadingState!=null){
                    switch (loadingState){
                        case START:
                            onDataLoadingStart();
                            break;
                        case FINISH:
                            onDataLoadingFinish();
                            break;
                    }
                }
            }
        });

        mViewBinding.btnDragView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DragFragment.show(context);
            }
        });

        mViewBinding.btnImageloader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageLoaderFragment.show(context);
            }
        });

        mViewBinding.btnSnapshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SnapShotFragment.show(context);
            }
        });

        mViewBinding.btnTextBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextBannerFragment.show(context);
            }
        });

        mViewBinding.btnUtils.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilsDemoFragment.show(context);
            }
        });

        mViewBinding.btnSupper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SupperButtonFragment.show(context);
            }
        });

        mViewBinding.btnBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BubbleViewFragment.show(context);
            }
        });

        mViewBinding.btnPudding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PuddingFragment.show(context);
            }
        });

        mViewBinding.btnBigImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BigImageLoaderFragment.show(context);
            }
        });

        mViewBinding.btnSlid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SlidingFragment.show(context);
            }
        });

        mViewBinding.btnViewpager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPager2Fragment.show(context);
            }
        });

        mViewBinding.btnTimeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeLineFragment.show(context);
            }
        });

        mViewBinding.btnFlowlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FlowLayoutFragment.show(context);
            }
        });
        mViewBinding.btnSystemFlowlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SystemFlowLayoutFragment.show(context);
            }
        });

        findViewById(R.id.btn_common_tab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonTabFragment.show(context);
            }
        });

        mViewBinding.btnSegment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SegmentTabFragment.show(context);
            }
        });

        mViewBinding.btnSliding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SlidingTabFragment.show(context);
            }
        });
        mViewBinding.btnAppBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppBarLayoutFragment.show(context);
            }
        });

        mViewBinding.btnSeekBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SeekBarFragment.show(context);
            }
        });
        mViewBinding.btnShape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShapeFragment.show(context);
            }
        });

        mViewBinding.btnShimmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShimmerFragment.show(context);
            }
        });

        mViewBinding.btnSkeleton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SkeletonFragment.show(context);
            }
        });

        mViewBinding.btnLottie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimsFragment.show(context);
            }
        });

        mViewBinding.btnKotlin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,KotlinMain.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        mViewBinding.btnConstraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayoutFragment.show(context);
            }
        });
    }
}
