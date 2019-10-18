package com.mbg.mbgsupport.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.mbg.mbgsupport.R;
import com.mbg.module.common.util.LogUtils;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPager2Fragment extends BaseFragment {
    private ViewPager2 viewPager2;

    public static void show(Context context) {
        TerminalActivity.show(context, ViewPager2Fragment.class, null);
    }

    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_vewpager2;
    }

    @Override
    protected void initView() {
        List<String> list = new ArrayList<>();
        list.add("页面一");
        list.add("页面二");
        list.add("页面三");
        list.add("页面四");


        viewPager2=findViewById(R.id.viewPager);
        //ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getContext(),list);
        MyFragmentStateAdapter fragmentStateAdapter=new MyFragmentStateAdapter(getChildFragmentManager(),getLifecycle(),list);
        viewPager2.setAdapter(fragmentStateAdapter);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewPager2.setCurrentItem(2,false);//true 可以看到滑动效果
        //viewPager2.setOffscreenPageLimit(1);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
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


    private class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {
        private List<String> mData;
        private LayoutInflater mInflater;


        private int[] colorArray = new int[]{android.R.color.holo_blue_bright, android.R.color.holo_blue_dark, android.R.color.holo_green_dark, android.R.color.holo_red_dark};


        public ViewPagerAdapter(Context context, List<String> data) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
        }

        @NonNull
        @Override
        public ViewPagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(mInflater.inflate(R.layout.item_viewpager_demo, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewPagerAdapter.ViewHolder holder, int position) {
            String animal = mData.get(position);
            holder.myTextView.setText(animal);
            holder.relativeLayout.setBackgroundResource(colorArray[position]);
            LogUtils.d("BindData:"+animal);

        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView myTextView;
            RelativeLayout relativeLayout;

            ViewHolder(View itemView) {
                super(itemView);
                myTextView = itemView.findViewById(R.id.tvTitle);
                relativeLayout = itemView.findViewById(R.id.container);
            }
        }
    }

    private class MyFragmentStateAdapter extends FragmentStateAdapter{

        private List<String> mData;
        public MyFragmentStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle,List<String> list) {
            super(fragmentManager, lifecycle);
            mData=list;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            ViewPager2ContentFragment slidDemoFragment=new ViewPager2ContentFragment();
            Bundle bundle=new Bundle();
            bundle.putString("titile",mData.get(position));
            slidDemoFragment.setArguments(bundle);
            LogUtils.d("createFragment:"+mData.get(position));
            return slidDemoFragment;
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

}
