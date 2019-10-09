package com.mbg.module.ui.view.listener;

import com.mbg.module.ui.view.common.SlideDirection;

public interface OnSlidListener {

    /**
     * 滑动开始，当前视图将要可见
     * 可以在该回调中实现数据与视图的绑定，比如显示占位的图片
     */
    void startVisible(SlideDirection direction);

    /**
     * 滑动完成，当前视图完全可见
     * 可以在该回调中开始主业务，比如开始播放视频，比如广告曝光统计
     */
    void completeVisible(SlideDirection direction);

    /**
     * 滑动完成，当前视图完全不可见
     * 可以在该回调中做一些清理工作，比如关闭播放器
     */
    void invisible(SlideDirection direction);

    /**
     * 已经完成了一次 direction 方向的滑动，用户很可能会在这个方向上继续滑动
     * 可以在该回调中实现下一次滑动的预加载，比如开始下载下一个视频或者准备好封面图
     */
    void preload(SlideDirection direction);
}
