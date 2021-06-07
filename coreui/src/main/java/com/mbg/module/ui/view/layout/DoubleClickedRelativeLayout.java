package com.mbg.module.ui.view.layout;

import android.content.Context;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;

import com.mbg.module.ui.view.listener.OnMultiClickedListener;


public class DoubleClickedRelativeLayout extends RelativeLayout {
    private final TouchEventCountThread mTouchEventCount = new TouchEventCountThread(); // 统计500ms内的点击次数
    private OnMultiClickedListener mMultiClickedListener;
    private final int mDoubleTapTimeout=ViewConfiguration.getDoubleTapTimeout();
    public DoubleClickedRelativeLayout(Context context) {
        super(context);
    }

    public DoubleClickedRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DoubleClickedRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mMultiClickedListener!=null) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // 第一次按下时,开始统计
                    if (0 == mTouchEventCount.touchCount) {
                        postDelayed(mTouchEventCount, mDoubleTapTimeout);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    // 一次点击事件要有按下和抬起, 有抬起必有按下, 所以只需要在ACTION_UP中处理
                    mTouchEventCount.touchCount++;
                    if (mTouchEventCount.isLongClick) {
                        mTouchEventCount.touchCount = 0;
                        mTouchEventCount.isLongClick = false;
                    }
                    break;
                default:
                    break;
            }
        }

        return true;
    }

    public void setMultiClickedListener(OnMultiClickedListener mMultiClickedListener) {
        this.mMultiClickedListener = mMultiClickedListener;
    }

    private class TouchEventCountThread implements Runnable {
        public int touchCount = 0;
        public boolean isLongClick = false;

        @Override
        public void run() {
            if(0 == touchCount){ // long click
                isLongClick = true;
                if (mMultiClickedListener!=null){
                    mMultiClickedListener.onLongClicked(DoubleClickedRelativeLayout.this);
                }
            } else {
                if (mMultiClickedListener!=null){
                    if (touchCount>1) {
                        mMultiClickedListener.onDoubleClicked(DoubleClickedRelativeLayout.this);
                    }else {
                        mMultiClickedListener.onClicked(DoubleClickedRelativeLayout.this);
                    }
                }
                touchCount = 0;
            }
        }
    }

}
