package com.mbg.module.ui.view.viewPager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mbg.module.ui.view.viewPager.OnSlidListener;
import com.mbg.module.ui.view.viewPager.common.SlideDirection;
import com.mbg.module.ui.view.viewPager.holder.SlideViewHolder;

public abstract class SlideViewAdapter implements SlideAdapter<SlideViewHolder> {

    /**
     * 创建 [View] 。
     * 一般来说，该方法会在 [SlidFrameLayout.setAdapter] 方法调用时触发一次，创建当前显示的 [View]，
     * 会在首次开始滑动时触发第二次，创建滑动目标的 [View]。
     */
    protected abstract View onCreateView(Context context, ViewGroup parent, LayoutInflater inflater);

    /**
     * 当 [view] 开始滑动到可见时触发，在这个方法中实现数据和 [view] 的绑定。
     *
     * @param direction 滑动的方向
     */
    protected abstract void onBindView(View view, SlideDirection direction);

    /**
     * 当滑动完成时触发。
     *
     * @param direction 滑动的方向
     */
    protected void finishSlide(SlideDirection direction) {
    }

    /**
     * 当滑动完成时触发。
     *
     * @param direction 滑动的方向
     */
    protected void finishSlide(View dismissView, View visibleView, SlideDirection direction) {
    }

    /**
     * 当滑动完成时，离开的 [view] 会触发，在这个方法中实现对 [view] 的清理。
     *
     * @param direction 滑动的方向
     */
    protected void onViewDismiss(View view, ViewGroup parent, SlideDirection direction) {
        parent.removeView(view);
    }

    /**
     * 当 [view] 完全出现时触发。
     * 这个时机可能是 [SlidFrameLayout.setAdapter] 后 [view] 的第一次初始化，
     * 也可能是完成一次滑动，在 [finishSlide] 后 **而且** 滑到了一个新的 [view]。
     * <p>
     * 也就是说，如果 [finishSlide] 的 [SlideDirection] 是 [SlideDirection.Origin] ，
     * 也就是滑动回弹到本来的界面上，是不会触发 [onViewComplete] 的。
     * <p>
     * 在这个方法中实现当 [view] 第一次完全出现时才做的业务。比如开始播放视频。
     */
    protected void onViewComplete(View view, SlideDirection direction) {
    }

    public final SlideViewHolder onCreateViewHolder(Context context, ViewGroup parent, LayoutInflater inflater) {
        return new SlideViewHolder(onCreateView(context, parent, inflater));
    }

    @Override
    public final void onBindView(SlideViewHolder viewHolder, SlideDirection direction) {
        View v = viewHolder.view;
        onBindView(v, direction);
        if (v instanceof OnSlidListener) {
            ((OnSlidListener) v).startVisible(direction);
        }
    }

    @Override
    public final void onViewDismiss(SlideViewHolder viewHolder, ViewGroup parent, SlideDirection direction) {
        View v = viewHolder.view;
        if (v instanceof OnSlidListener) {
            ((OnSlidListener) v).invisible(direction);
        }
        onViewDismiss(v, parent, direction);
    }

    @Override
    public final void onViewComplete(SlideViewHolder viewHolder, SlideDirection direction) {
        View v = viewHolder.view;
        onViewComplete(v, direction);
        if (v instanceof OnSlidListener) {
            ((OnSlidListener) v).completeVisible(direction);
        }
    }

    @Override
    public final void finishSlide(SlideViewHolder dismissViewHolder, SlideViewHolder visibleViewHolder, SlideDirection direction) {
        finishSlide(direction);
        finishSlide(dismissViewHolder.view, visibleViewHolder.view, direction);
        if (dismissViewHolder.view instanceof OnSlidListener) {
            ((OnSlidListener) dismissViewHolder.view).preload(direction);
        }
    }
}
