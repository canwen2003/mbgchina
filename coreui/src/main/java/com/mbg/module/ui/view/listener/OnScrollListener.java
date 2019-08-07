package com.mbg.module.ui.view.listener;

/***
 * 控件滑动监控类
 * created by Gap
 */
public interface OnScrollListener {
    /***
     * 开始滑动
     * @param isVerticalScroll 是否是垂直方向滑动
     */
    void onScrollStart(boolean isVerticalScroll);

    /***
     * 垂直方向滑动
     * @param distance 滑动距离px
     */
    void onVerticalScroll(float distance);

    /***
     * 水平方向滑动
     * @param distance 滑动距离px
     */
    void onHorizontalScroll(float distance);

    /**
     * 滑动结束，已经抬手
     */
    void onScrollStop();
}
