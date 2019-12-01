package com.mbg.module.ui.view.scrollview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;


import androidx.core.widget.NestedScrollView;

public class ZoomScrollView extends NestedScrollView {
    //缩放控件
    private View mZoomView;
    private int mZoomViewWidth;
    private int mZoomViewHeight;

    //缩放信息
    private float mStartYPos;//记录第一次按下的Y值的位置
    private boolean mScaling;//是否正在缩放
    private float mScrollRate = 0.3f;//缩放系数，缩放系数越大，变化的越大
    private float mReplyRate = 0.5f;//回调系数，越大，回调越慢
    private float mMovingThreshold;//滑动阀值

    public ZoomScrollView(Context context) {
        super(context);
    }

    public ZoomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZoomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setZoomView(View mZoomView) {
        this.mZoomView = mZoomView;
    }

    public void setScrollRate(float mScrollRate) {
        this.mScrollRate = mScrollRate;
    }

    public void setReplyRate(float mReplyRate) {
        this.mReplyRate = mReplyRate;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        mMovingThreshold= ViewConfiguration.get(getContext()).getScaledTouchSlop();
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mZoomView==null){
            return super.onTouchEvent(ev);
        }
        if (mZoomViewWidth <= 0 || mZoomViewHeight <= 0) {
            mZoomViewWidth = mZoomView.getMeasuredWidth();
            mZoomViewHeight = mZoomView.getMeasuredHeight();
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 滚动到顶部时记录位置，否则正常返回
                if (getScrollY() == 0) {
                    mStartYPos = ev.getY();
                }
                break;
            case MotionEvent.ACTION_UP:
                //手指离开后恢复图片
                mScaling = false;
                replyImage();
                break;
            case MotionEvent.ACTION_MOVE:
                float dy=ev.getY() - mStartYPos;
                if (!mScaling) {
                    if (getScrollY() == 0) {
                        //如果不是向下滑动或非滑动，正常返回
                        if (Math.abs(dy)<=mMovingThreshold||dy<0){
                            break;
                        }
                    } else {
                        break;
                    }
                }
                // 处理放大
                mScaling = true;
                setZoom(dy * mScrollRate);
                return true; // 返回true表示已经完成触摸事件，不再处理
        }
        return super.onTouchEvent(ev);
    }
    //回弹动画
    private void replyImage() {
        if (mZoomView==null){
            return;
        }
        float distance = mZoomView.getMeasuredWidth() - mZoomViewWidth;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(distance, 0f).setDuration((long) (distance * mReplyRate));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setZoom((Float) animation.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }

    /***
     * 缩放函数
     * @param zoom 缩放值 0不缩放、负数为缩小、正数为放大
     */
    public void setZoom(float zoom) {
        if (mZoomView==null||mZoomViewWidth <= 0 || mZoomViewHeight <= 0) {
            return;
        }
        ViewGroup.LayoutParams lp = mZoomView.getLayoutParams();
        lp.width = (int) (mZoomViewWidth + zoom);
        lp.height = (int) (mZoomViewHeight * ((mZoomViewWidth + zoom) / mZoomViewWidth));
        mZoomView.setLayoutParams(lp);
    }

}
