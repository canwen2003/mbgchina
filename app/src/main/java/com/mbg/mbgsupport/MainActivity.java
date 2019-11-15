package com.mbg.mbgsupport;


import android.content.Context;
import android.os.Bundle;
import android.view.View;

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
import com.mbg.mbgsupport.fragment.tab.CommonTabFragment;
import com.mbg.mbgsupport.fragment.tab.SegmentTabFragment;
import com.mbg.mbgsupport.fragment.tab.SlidingTabFragment;
import com.mbg.module.ui.activity.BaseActivity;

public class MainActivity extends BaseActivity {

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setStatusBarColor(getResources().getColor(R.color.design_default_color_primary),0);
        context=this;
        initView();
    }

    private void initView(){
        findViewById(R.id.btn_drag_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DragFragment.show(context);
            }
        });

        findViewById(R.id.btn_imageloader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageLoaderFragment.show(context);
            }
        });

        findViewById(R.id.btn_snapshot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SnapShotFragment.show(context);
            }
        });

        findViewById(R.id.btn_text_banner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextBannerFragment.show(context);
            }
        });

        findViewById(R.id.btn_utils).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilsDemoFragment.show(context);
            }
        });

        findViewById(R.id.btn_supper).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SupperButtonFragment.show(context);
            }
        });

        findViewById(R.id.btn_bubble).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BubbleViewFragment.show(context);
            }
        });

        findViewById(R.id.btn_pudding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PuddingFragment.show(context);
            }
        });

        findViewById(R.id.btn_big_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BigImageLoaderFragment.show(context);
            }
        });

        findViewById(R.id.btn_slid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SlidingFragment.show(context);
            }
        });

        findViewById(R.id.btn_viewpager).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPager2Fragment.show(context);
            }
        });

        findViewById(R.id.btn_timeline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeLineFragment.show(context);
            }
        });

        findViewById(R.id.btn_flowlayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FlowLayoutFragment.show(context);
            }
        });
        findViewById(R.id.btn_system_flowlayout).setOnClickListener(new View.OnClickListener() {
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

        findViewById(R.id.btn_segment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SegmentTabFragment.show(context);
            }
        });

        findViewById(R.id.btn_sliding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SlidingTabFragment.show(context);
            }
        });
    }
}
