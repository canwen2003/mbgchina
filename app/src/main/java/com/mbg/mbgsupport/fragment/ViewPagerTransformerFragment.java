package com.mbg.mbgsupport.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.mbg.mbgsupport.R;
import com.mbg.module.common.util.LogUtils;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.view.viewPager.transformer.MyTransformer;
import com.mbg.module.ui.view.viewPager.transformer.TransType;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerTransformerFragment extends BaseFragment {

    public static void show(Context context){
        TerminalActivity.show(context, ViewPagerTransformerFragment.class,null);
    }

    private ViewPager mViewPager;
    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_transformer_demo;
    }

    @Override
    protected void initView() {
        List<String> list = new ArrayList<>();
        list.add("页面一");
        list.add("页面二");
        list.add("页面三");
        list.add("页面四");

        mViewPager=findViewById(R.id.view_pager);
        MyFragmentStateAdapter fragmentStateAdapter=new MyFragmentStateAdapter(getChildFragmentManager(),list);
        mViewPager.setAdapter(fragmentStateAdapter);
        mViewPager.setPageTransformer(false, MyTransformer.getMyTransformer(TransType.DEFAULT));
        mViewPager.setCurrentItem(2,false);//true 可以看到滑动效果
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        findViewById(R.id.tv_default).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setPageTransformer(false, MyTransformer.getMyTransformer(TransType.DEFAULT));
            }
        });


        findViewById(R.id.tv_h3d).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setPageTransformer(false, MyTransformer.getMyTransformer(TransType.H3D));
            }
        });

        findViewById(R.id.tv_h3dinfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setPageTransformer(false, MyTransformer.getMyTransformer(TransType.H3DINTO));
            }
        });

        findViewById(R.id.tv_fadein).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setPageTransformer(false, MyTransformer.getMyTransformer(TransType.FADEIN));
            }
        });
        findViewById(R.id.tv_overlap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setPageTransformer(false, MyTransformer.getMyTransformer(TransType.OVERLAP));
            }
        });
        findViewById(R.id.tv_tandem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setPageTransformer(false, MyTransformer.getMyTransformer(TransType.TANDEM));
            }
        });
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

    private class MyFragmentStateAdapter extends FragmentPagerAdapter {

        private List<String> mData;
        private List<Integer> mColor;
        public MyFragmentStateAdapter(@NonNull FragmentManager fragmentManager, List<String> list) {
            super(fragmentManager);
            mData=list;
            mColor=new ArrayList<>();
            mColor.add(Color.RED);
            mColor.add(Color.YELLOW);
            mColor.add(Color.BLUE);
            mColor.add(Color.GRAY);
            mColor.add(Color.GREEN);
            mColor.add(Color.DKGRAY);

        }

        @NonNull
        @NotNull
        @Override
        public Fragment getItem(int position) {
            ViewPager2ContentFragment slidDemoFragment=new ViewPager2ContentFragment();
            Bundle bundle=new Bundle();
            bundle.putString("titile",mData.get(position));
            bundle.putInt("color", mColor.get(position));
            slidDemoFragment.setArguments(bundle);
            LogUtils.d("createFragment:"+mData.get(position));
            return slidDemoFragment;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

    }
}
