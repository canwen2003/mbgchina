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
import com.mbg.module.common.util.ClickUtils;
import com.mbg.module.common.util.LogUtils;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.image.cache.engine.LoadOptions;
import com.mbg.module.ui.image.view.RecyclerImageView;
import com.mbg.module.ui.image.view.RoundedImageView;
import com.mbg.module.ui.view.scrollview.ZoomScrollView;

import java.util.ArrayList;
import java.util.List;

public class DragFragment extends BaseFragment implements View.OnClickListener{

    private RecyclerImageView imgView;
    private ZoomScrollView zoomScrollView;
    private ViewPager2 viewPager2;

    public static void show(Context context){
        TerminalActivity.show(context,DragFragment.class,null);
    }
    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_dragview_test;
    }

    @Override
    protected void initView() {
        imgView=findViewById(R.id.img_test);
        RecyclerImageView imgView1=findViewById(R.id.img_test1);
        RecyclerImageView imgView2=findViewById(R.id.img_test2);
        String uri="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1564406714258&di=3a57d4b83d48df3194bad83a74fe81ad&imgtype=0&src=http%3A%2F%2Fimg.book118.com%2Fsr1%2FM00%2F0B%2F17%2FwKh2Al0LMjeIW64PAAEqr-JkQOQAAeGqwD9g2gAASrH405.jpg";
        String uri1="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1564406714640&di=208a8f28127ab3aa7e4e96eee8f9a537&imgtype=0&src=http%3A%2F%2Fimg246.ph.126.net%2FH0reIPvlHNk5nR-2mhXhew%3D%3D%2F2234348365130522269.gif";
        String uri2="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1564406714639&di=70771c60403e8e7292ea67336b6f26fb&imgtype=0&src=http%3A%2F%2Fdtimage.oss-cn-shanghai.aliyuncs.com%2F2017%2F0226%2F26014658p0tt.jpg";
        LoadOptions options= LoadOptions.get().setEnableCache(true).setDefaultImageResId(R.drawable.icon_common_empty).setImageOnFail(R.drawable.icon_common_empty);
        imgView.loadImage(uri,options);
        imgView1.loadImage(uri1,options);
        imgView2.loadImage(uri,options);


        RoundedImageView roundedImageView1=findViewById(R.id.img_test3);
        RoundedImageView roundedImageView2=findViewById(R.id.img_test4);
        RoundedImageView roundedImageView3=findViewById(R.id.img_test5);
        RoundedImageView roundedImageView4=findViewById(R.id.img_test6);
        final String uri3="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1564565361478&di=81576c9c9acbdda02e967b26f7488807&imgtype=0&src=http%3A%2F%2Ftvax4.sinaimg.cn%2Fcrop.0.0.512.512.1024%2F007azY3dly8fykbthnagdj30e80e80tm.jpg%3FExpires%3D1562678507%26ssig%3DEcWVulc6xh%26KID%3Dimgbed%2Ctva";
        roundedImageView1.loadImage(uri);
        roundedImageView2.loadImage(uri1);
        roundedImageView3.loadImage(uri2);
        roundedImageView4.loadImage(uri3);

        zoomScrollView =findViewById(R.id.head_scrollview);
        zoomScrollView.setZoomView(imgView);


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
            return new ViewPagerAdapter.ViewHolder(mInflater.inflate(R.layout.item_viewpager_demo, parent, false));
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

    private class MyFragmentStateAdapter extends FragmentStateAdapter {

        private List<String> mData;
        public MyFragmentStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<String> list) {
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


    @Override
    public void onClick(View view) {
        int viewId=view.getId();
        if(ClickUtils.isFastDoubleClick(viewId)) {
            return;
        }

    }
}
