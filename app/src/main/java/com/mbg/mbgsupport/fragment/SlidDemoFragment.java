package com.mbg.mbgsupport.fragment;


import android.widget.TextView;

import com.mbg.mbgsupport.R;
import com.mbg.module.common.util.LogUtils;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.view.listener.OnSlidListener;
import com.mbg.module.ui.view.common.SlideDirection;

public class SlidDemoFragment extends BaseFragment implements OnSlidListener {

    TextView textView;
    String mTitle="";
    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_slid_demo;
    }

    @Override
    protected void initView() {
        textView=findViewById(R.id.tv_title);
    }

    /**
     * 滑动开始，当前视图将要可见
     * 可以在该回调中实现数据与视图的绑定，比如显示占位的图片
     */
    @Override
    public void startVisible(SlideDirection direction) {
        textView.setBackgroundResource(R.color.color_6596ff);
        LogUtils.e(":"+mTitle);
    }

    /**
     * 滑动完成，当前视图完全可见
     * 可以在该回调中开始主业务，比如开始播放视频，比如广告曝光统计
     */
    @Override
    public void completeVisible(SlideDirection direction) {
        textView.setBackgroundResource(R.color.color_14c7de);
        textView.setText(mTitle);
        LogUtils.e(":"+mTitle);
    }

    /**
     * 滑动完成，当前视图完全不可见
     * 可以在该回调中做一些清理工作，比如关闭播放器
     */
    @Override
    public void invisible(SlideDirection direction) {
        LogUtils.e(":"+mTitle);
        textView.setText("");
    }

    /**
     * 已经完成了一次 direction 方向的滑动，用户很可能会在这个方向上继续滑动
     * 可以在该回调中实现下一次滑动的预加载，比如开始下载下一个视频或者准备好封面图
     */
    @Override
    public void preload(SlideDirection direction) {
        LogUtils.e(":"+mTitle);
    }

    public void setTitle(String title){
        mTitle=title;
    }
}
