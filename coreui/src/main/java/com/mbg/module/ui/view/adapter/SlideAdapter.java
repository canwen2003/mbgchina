package com.mbg.module.ui.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mbg.module.ui.view.common.SlideDirection;
import com.mbg.module.ui.view.holder.SlideViewHolder;

/**
 * 适配 [SlidFrameLayout] 以及布局中滑动的 [View] 。
 * <p>
 * 假如首次初始化页面【A】，触发的回调是：
 * - onCreateViewHolder(context, inflater)
 * - onViewComplete(viewHolder【A】)
 * <p>
 * 假如从页面【A】滑动下一个页面【B】，触发的回调将会是：
 * <p>
 * - canSlideTo(SlideDirection.Next)
 * - onCreateViewHolder(context, inflater) (如果是首次滑动)
 * - onBindView(viewHolder【B】, SlideDirection.Next)
 * - onViewDismiss(viewHolder【A】, SlideDirection.Next)
 * - onViewComplete(viewHolder【B】)
 * - finishSlide(SlideDirection.Next)
 * <p>
 * 假如再从页面【B】 滑动回上一个页面 【A】，触发的回调是：
 * <p>
 * - canSlideTo(SlideDirection.Prev)
 * - onBindView(viewHolder【A】, SlideDirection.Prev)
 * - onViewDismiss(viewHolder【B】, SlideDirection.Prev)
 * - onViewComplete(viewHolder【A】)
 * - finishSlide(SlideDirection.Prev)
 * <p>
 * 假如从页面【A】试图滑动到页面【B】，但距离或者速度不够，所以放手后回弹到【A】，触发的回调是：
 * <p>
 * - canSlideTo(SlideDirection.Next)
 * - onBindView(viewHolder【B】, SlideDirection.Next)
 * - onViewDismiss(viewHolder【B】, SlideDirection.Next)
 * - finishSlide(SlideDirection.Origin)
 */
public interface SlideAdapter <ViewHolder extends SlideViewHolder>{
    /**
     * 能否向 [direction] 的方向滑动。
     *
     * @param direction 滑动的方向
     * @return 返回 true 表示可以滑动， false 表示不可滑动。
     * 如果有嵌套其他外层滑动布局（比如下拉刷新），当且仅当返回 false 时会触发外层的嵌套滑动。
     */
    boolean canSlideTo(SlideDirection direction);

    /**
     * 创建持有 [View] 的 [SlideViewHolder] 。
     * 一般来说，该方法会在 [SlidFrameLayout.setAdapter] 方法调用时触发一次，创建当前显示的 [View]，
     * 会在首次开始滑动时触发第二次，创建滑动目标的 [View]。
     */
    ViewHolder onCreateViewHolder(Context context, ViewGroup parent, LayoutInflater inflater);

    /**
     * 当 [View] 开始滑动到可见时触发，在这个方法中实现数据和 [View] 的绑定。
     *
     * @param viewHolder 持有 [View] 的 [SlideViewHolder]
     * @param direction  滑动的方向
     */
    void onBindView(ViewHolder viewHolder, SlideDirection direction);

    /**
     * 当 [View] 完全出现时触发。
     * 这个时机可能是 [SlidFrameLayout.setAdapter] 后 [View] 的第一次初始化，
     * 也可能是完成一次滑动，在 [finishSlide] 后 **而且** 滑到了一个新的 [View]。
     * <p>
     * 也就是说，如果 [finishSlide] 的 [SlideDirection] 是 [SlideDirection.Origin] ，
     * 也就是滑动回弹到本来的界面上，是不会触发 [onViewComplete] 的。
     * <p>
     * 在这个方法中实现当 [View] 第一次完全出现时才做的业务。比如开始播放视频。
     *
     * @param viewHolder 持有 [View] 的 [SlideViewHolder]
     */
    void onViewComplete(ViewHolder viewHolder, SlideDirection direction);

    /**
     * 当滑动完成时，离开的 [View] 会触发，在这个方法中实现对 [View] 的清理。
     *
     * @param viewHolder 持有 [View] 的 [SlideViewHolder]
     * @param direction  滑动的方向
     */
    void onViewDismiss(ViewHolder viewHolder, ViewGroup parent, SlideDirection direction);

    /**
     * 当滑动完成时触发。
     *
     * @param direction 滑动的方向
     */
    void finishSlide(ViewHolder dismissViewHolder, ViewHolder visibleViewHolder, SlideDirection direction);
}
