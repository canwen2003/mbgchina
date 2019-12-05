package com.mbg.mbgsupport.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;

import com.mbg.mbgsupport.R;
import com.mbg.module.common.util.LogUtils;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.view.banner.ITextBannerItemClickListener;
import com.mbg.module.ui.view.banner.TextBannerView;
import com.mbg.module.ui.view.showmore.ShowMoreTextView;

import java.util.ArrayList;
import java.util.List;

public class TextBannerFragment extends BaseFragment{

    private TextBannerView textBannerView1;
    private TextBannerView textBannerView2;
    private TextBannerView textBannerView3;
    private TextBannerView textBannerView4;
    private ShowMoreTextView showMoreTextView;
    public static void show(Context context){
        TerminalActivity.show(context, TextBannerFragment.class,null);
    }
    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_textbanner;
    }

    @Override
    protected void initView() {
        textBannerView1=findViewById(R.id.tb_left_right);
        textBannerView2=findViewById(R.id.tb_right_left);
        textBannerView3=findViewById(R.id.tb_top_bottom);
        textBannerView4=findViewById(R.id.tb_bottom_top);
        showMoreTextView=findViewById(R.id.tv_showmore);

        showMoreTextView.setText("7777\n3ddd3333\ndddddddd33333333\nuuu滚滚滚uu\nh多个\n44444");


        textBannerView1.setItemOnClickListener(new ITextBannerItemClickListener() {
            @Override
            public void onItemClick(String data, int position) {
                LogUtils.v("data="+data+" position="+position);
            }
        });

        textBannerView2.setItemOnClickListener(new ITextBannerItemClickListener() {
            @Override
            public void onItemClick(String data, int position) {
                LogUtils.v("data="+data+" position="+position);
            }
        });

        textBannerView3.setItemOnClickListener(new ITextBannerItemClickListener() {
            @Override
            public void onItemClick(String data, int position) {
                LogUtils.v("data="+data+" position="+position);
            }
        });

        textBannerView4.setItemOnClickListener(new ITextBannerItemClickListener() {
            @Override
            public void onItemClick(String data, int position) {
                LogUtils.v("data="+data+" position="+position);
            }
        });

        initData();
    }


    private void initData() {
        List<String> mList = new ArrayList<>();
        mList.add("第一个Banner显示");
        mList.add("走遍天下都不怕！！！！！");
        mList.add("不是我吹，就怕你做不到，哈哈");
        mList.add("第4个Banner显示");
        mList.add("你是最棒的，奔跑吧孩子！");
        /**
         * 设置数据，方式一
         */
        textBannerView1.setDatas(mList);
        textBannerView2.setDatas(mList);
        textBannerView3.setDatas(mList);
        textBannerView4.setDatas(mList);

        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        /**
         * 设置数据（带图标的数据），方式二
         */
        //第一个参数：数据 。第二参数：drawable.  第三参数drawable尺寸。第四参数图标位置
        textBannerView2.setDatasWithDrawableIcon(mList,drawable,18, Gravity.LEFT);

    }

    @Override
    public void onResume() {
        super.onResume();
        onStartBanner();
    }

    @Override
    public void onStop() {
        super.onStop();
        onStopBanner();
    }

    private void onStartBanner(){
        textBannerView1.startViewAnimator();
        textBannerView2.startViewAnimator();
        textBannerView3.startViewAnimator();
        textBannerView4.startViewAnimator();
    }

    private void onStopBanner(){
        textBannerView1.stopViewAnimator();
        textBannerView2.stopViewAnimator();
        textBannerView3.stopViewAnimator();
        textBannerView4.stopViewAnimator();
    }



}
